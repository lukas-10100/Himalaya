<?php

/**
 * Funktion zum Absenden von API-Anfragen
 * 
 * @param string $key Der API Key
 * @param int $module Das Modul als Zahlenwert (0 - Produkte, 1 - Nutzer, 2 - Warenkoerbe)
 * @param string $action Aktion, Siehe API
 * @param string $parameters Parameter, mit ';' getrennt
 * 
 * @return json_object Ergebnis der Anfrage
 * 
 * */
function performApiRequest($key, $module, $action, $parameters) {
    $result = file_get_contents('http://api.himalaya.lukas-10100.de/?key=' . urlencode($key) . '&mod=' . urlencode($module) . '&action=' . urlencode($action) . '&prm=' . urlencode($parameters));
    // echo 'http://api.himalaya.lukas-10100.de/?key=' . urlencode($key) . '&mod=' . urlencode($module) . '&action=' . urlencode($action) . '&prm=' . urlencode($parameters);
    return json_decode($result);
}

/**
 * Registriert einen Nutzer mit den meisten Absicherungen
 * 
 * @param string $key Der API Key
 * @param string $name Nachname des Accounts
 * @param string $firstName Vorname des Accounts
 * @param string $email E-Mail Adresse
 * @param string $password1 Passwort
 * @param string $password2 Passwort wiederholen
 * @param string $country Land
 * 
 * @return int Rueckgabecode (200 = OK)
 */
function registerUser($key, $name, $firstName, $email, $password1, $password2, $country) {
    // Pruefen, ob Passwoerter identisch sind
    if ($password1 === $password2) {
        $tryAgain = true;
        // Gegebenenfalls wiederholen, sollte die Kundennummer bereits existieren
        while ($tryAgain === true) {
            $tryAgain = false;
            // Zufaellige Kundennummer mit 6 Stellen generieren
            $customerID = rand(100000, 999999);
            // Pruefen, ob alles ausgefuellt ist
            if (!empty($password1) && !empty($firstName) && !empty($name) && !empty($email)) {
                // Nutzer erstellen
                $result1 = performApiRequest($key, 1, 'create', $customerID . ';'
                        . $name . ', ' . $firstName . ';'
                        . $email . ';'
                        . $password1 . ';'
                        . '[];' . '{"city":"-","phone":"-","postalCode":"0","dateOfBirth":"0","state":"-","line3":"-","line2":"-","line1":"-"}' . ';' . $country);
                if ($result1->error == false) {
                    // Korrespondierenden Warenkorb erstellen
                    $result2 = performApiRequest($key, 2, 'create', $customerID . ';[]');
                    if ($result2->error == false) {
                        return 200;
                    } else {
                        return 102;
                    }
                } else {
                    if ($result1->code == 871) {
                        // Kundennummer existiert bereits, nochmal versuchen
                        $tryAgain = true;
                    } else {
                        return 102;
                    }
                }
            } else {
                return 101;
            }
        }
    } else {
        return 100;
    }
}

/**
 * Fragt Produktinformationen mithilfe der Artikelnummer ab
 * 
 * @param int $arg Artikelnummer des anzufragenden Produkts
 * @param string $key Der API Key
 * 
 * @return json_object Produkt als Objekt 
 */
function getProductByNumber($arg, $key) {
    $result = performApiRequest($key, 0, 'GET', '*;WHERE `artikelnummer`=' . $arg);
    if (sizeof($result) > 0) {
        return $result[0];
    } else {
        return null;
    }
}

/**
 * Fragt Produktinformationen mithilfe des Produktnamens ab
 * 
 * @param string $arg Name des anzufragenden Produkts
 * @param string $key Der API Key
 * 
 * @return json_array Array der Produkte
 */
function getProductsByName($arg, $key) {
    $result = performApiRequest($key, 0, 'GET', '*;WHERE `name`=' . $arg);
    if (sizeof($result) > 0) {
        return $result;
    } else {
        return null;
    }
}

/**
 * Fragt den Namen eines Produkts einer bestimmten Artikelnummer ab
 * 
 * @param int $item_number Artikelnummer des Produkts
 * @param string $key Der API Key
 * 
 * @return string Name des Produkts
 */
function product_getName($item_number, $key) {
    $result = performApiRequest($key, 0, 'GET', 'name;WHERE `artikelnummer`=' . $item_number);
    if (sizeof($result) > 0) {
        return $result[0]->name;
    } else {
        return null;
    }
}

/**
 * Fragt das Bild eines Produkts einer bestimmten Artikelnummer ab
 * 
 * @param int $item_number Artikelnummer des Produkts
 * @param string $key Der API Key
 * 
 * @return string URL des Bildes
 */
function product_getImage($item_number, $key) {
    $result = performApiRequest($key, 0, 'GET', 'bild;WHERE `artikelnummer`=' . $item_number);
    if (sizeof($result) > 0) {
        return $result[0]->bild;
    } else {
        return null;
    }
}

/**
 * Fragt die Kategorie eines Produkts einer bestimmten Artikelnummer ab
 * 
 * @param int $item_number Artikelnummer des Produkts
 * @param string $key Der API Key
 * 
 * @return string Kategorie des Produkts
 */
function product_getType($item_number, $key) {
    $result = performApiRequest($key, 0, 'GET', 'art;WHERE `artikelnummer`=' . $item_number);
    if (sizeof($result) > 0) {
        return $result[0]->art;
    } else {
        return null;
    }
}

/**
 * Fragt den Preis eines Produkts einer bestimmten Artikelnummer ab
 * 
 * @param int $item_number Artikelnummer des Produkts
 * @param string $key Der API Key
 * 
 * @return double Preis des Produkts in EUR
 */
function product_getPrice($item_number, $key) {
    $result = performApiRequest($key, 0, 'GET', 'preis;WHERE `artikelnummer`=' . $item_number);
    if (sizeof($result) > 0) {
        return $result[0]->preis;
    } else {
        return null;
    }
}

/**
 * Fragt die Verfuegbarkeit eines Produkts einer bestimmten Artikelnummer ab
 * 
 * @param int $item_number Artikelnummer des Produkts
 * @param string $key Der API Key
 * 
 * @return boolean Verfuegbarkeit des Produkts
 */
function product_getStock($item_number, $key) {
    $result = performApiRequest($key, 0, 'GET', 'bestand;WHERE `artikelnummer`=' . $item_number);
    if (sizeof($result) > 0) {
        return $result[0]->bestand == '1' ? 'true' : 'false';
    } else {
        return null;
    }
}

/**
 * Fragt die Beschreibung eines Produkts einer bestimmten Artikelnummer ab
 * 
 * @param int $item_number Artikelnummer des Produkts
 * @param string $key Der API Key
 * 
 * @return string Beschreibung des Produkts
 */
function product_getDescription($item_number, $key) {
    $result = performApiRequest($key, 0, 'GET', 'bezeichnung;WHERE `artikelnummer`=' . $item_number);
    if (sizeof($result) > 0) {
        return $result[0]->bezeichnung;
    } else {
        return null;
    }
}

/**
 * Fragt das Gewicht eines Produkts einer bestimmten Artikelnummer ab
 * 
 * @param int $item_number Artikelnummer des Produkts
 * @param string $key Der API Key
 * 
 * @return int Gewicht des Produkts in Gramm
 */
function product_getWeight($item_number, $key) {
    $result = performApiRequest($key, 0, 'GET', 'gewicht;WHERE `artikelnummer`=' . $item_number);
    if (sizeof($result) > 0) {
        return $result[0]->gewicht;
    } else {
        return null;
    }
}

/**
 * 
 * Fragt Nutzerinformationen mithilfe der Kundennummer ab
 * 
 * @param int $arg Kundennummer des Nutzers
 * @param string $key Der API Key
 * 
 * @return json_object Nutzer als Objekt
 */
function getUserByNumber($arg, $key) {
    $result = performApiRequest($key, 1, 'GET', '*;WHERE `kundennummer`=' . $arg);
    if (sizeof($result) > 0) {
        return $result[0];
    } else {
        return null;
    }
}

/**
 * 
 * Fragt Nutzerinformationen mithilfe des Namens ab
 * 
 * @param string $arg Name des Nutzers
 * @param string $key Der API Key
 * 
 * @return json_array Array der Nutzer
 */
function getUsersByName($arg, $key) {
    $result = performApiRequest($key, 1, 'GET', '*;WHERE `name`=' . $arg);
    if (sizeof($result) > 0) {
        return $result;
    } else {
        return null;
    }
}

/**
 * 
 * Fragt Nutzerinformationen mithilfe der E-Mail ab
 * 
 * @param string $arg E-Mail des Nutzers
 * @param string $key Der API Key
 * 
 * @return json_array Array der Nutzer
 */
function getUsersByEmail($arg, $key) {
    $result = performApiRequest($key, 1, 'GET', '*;WHERE `email`=\'' . $arg . '\'');
    if (sizeof($result) > 0) {
        return $result;
    } else {
        return null;
    }
}

/**
 * Fragt den Namen eines Nutzers einer bestimmten Kundennummer ab
 * 
 * @param int $customer_id Kundennummer des Kunden
 * @param string $key Der API Key
 * 
 * @return string Name des Nutzers
 */
function user_getName($customer_id, $key) {
    $result = performApiRequest($key, 1, 'GET', 'name;WHERE `kundennummer`=' . $customer_id);
    if (sizeof($result) > 0) {
        return $result[0]->name;
    } else {
        return null;
    }
}

/**
 * Fragt die E-Mail Adresse eines Nutzers einer bestimmten Kundennummer ab
 * 
 * @param int $customer_id Kundennummer des Kunden
 * @param string $key Der API Key
 * 
 * @return string E-Mail Adresse des Nutzers
 */
function user_getEMail($customer_id, $key) {
    $result = performApiRequest($key, 1, 'GET', 'email;WHERE `kundennummer`=' . $customer_id);
    if (sizeof($result) > 0) {
        return $result[0]->email;
    } else {
        return null;
    }
}

/**
 * Fragt das Passwort eines Nutzers einer bestimmten Kundennummer ab
 * 
 * @param int $customer_id Kundennummer des Kunden
 * @param string $key Der API Key
 * 
 * @return string Passwort des Nutzers in Klartext
 */
function user_getPassword($customer_id, $key) {
    $result = performApiRequest($key, 1, 'GET', 'passwort;WHERE `kundennummer`=' . $customer_id);
    if (sizeof($result) > 0) {
        return $result[0]->passwort;
    } else {
        return null;
    }
}

/**
 * Fragt die Zahlungsmethoden eines Nutzers einer bestimmten Kundennummer ab
 * 
 * @param int $customer_id Kundennummer des Kunden
 * @param string $key Der API Key
 * 
 * @return json_array Array der Zahlungsmethoden als Objekt
 */
function user_getPaymentMehtods($customer_id, $key) {
    $result = performApiRequest($key, 1, 'GET', 'zahlungsmethoden;WHERE `kundennummer`=' . $customer_id);
    if (sizeof($result) > 0) {
        return json_decode($result[0]->zahlungsmethoden);
    } else {
        return null;
    }
}

/**
 * Fragt die Adresse eines Nutzers einer bestimmten Kundennummer ab
 * 
 * @param int $customer_id Kundennummer des Kunden
 * @param string $key Der API Key
 * 
 * @return json_object Adresse des Nutzers als Objekt
 */
function user_getAddress($customer_id, $key) {
    $result = performApiRequest($key, 1, 'GET', 'adresse;WHERE `kundennummer`=' . $customer_id);
    if (sizeof($result) > 0) {
        return json_decode($result[0]->adresse);
    } else {
        return null;
    }
}

/**
 * Fragt das Land eines Nutzers einer bestimmten Kundennummer ab
 * 
 * @param int $customer_id Kundennummer des Kunden
 * @param string $key Der API Key
 * 
 * @return string Land des Nutzers
 */
function user_getCountry($customer_id, $key) {
    $result = performApiRequest($key, 1, 'GET', 'land;WHERE `kundennummer`=' . $customer_id);
    if (sizeof($result) > 0) {
        return $result[0]->land;
    } else {
        return null;
    }
}

/**
 * Fragt den Warenkorb eines Nutzers mithilfe der Kundennummer ab
 * 
 * @param int $arg Kundennummer des Nutzers des anzufragenden Warenkorbs
 * @param string $key Der API Key
 * 
 * @return json_object Warenkorb als Objekt 
 */
function getCartByCustomerID($arg, $key) {
    $result = performApiRequest($key, 2, 'GET', '*;WHERE `kundennummer`=' . $arg);
    if (sizeof($result) > 0) {
        return $result[0];
    } else {
        return null;
    }
}

/**
 * Fragt den Inhalt des Warenkorbs eines Nutzers mithilfe der Kundennummer ab
 * 
 * @param int $arg Kundennummer des Nutzers des anzufragenden Warenkorbinhalts
 * @param string $key Der API Key
 * 
 * @return json_array Array der Warenkorbinhalte als Objekt 
 */
function getCartContentByCustomerID($arg, $key) {
    $result = performApiRequest($key, 2, 'GET', 'inhalt;WHERE `kundennummer`=' . $arg);
    if (sizeof($result) > 0) {
        return json_decode($result[0]->inhalt);
    } else {
        return null;
    }
}

/**
 * Fuegt eine gewisse Anzahl eines Produkts mit einer gewissen Artikelnummer zum Warenkorb eines Nutzers einer bestimmten Kundennummer hinzu
 * 
 * @param int $customerId Kundennummer des Nutzers des Warenkorbs
 * @param int $prod_itemNumber Artikelnummer des hinzuzufuegenden Produkts
 * @param int $prod_amount Menge des hinzuzufuegenden Produkts
 * @param string $key Der API Key
 * 
 * @return boolean Erfolgreicher Abschluss
 */
function addToCart($customerId, $prod_itemNumber, $prod_amount, $key) {
    $result = performApiRequest($key, 2, 'APPEND', $customerId . ';ARTIKELNUMMER>' . $prod_itemNumber . '|NAME>' . product_getName($prod_itemNumber, $key) . '|MENGE>' . $prod_amount);
    if ($result->error == false) {
        return true;
    } else {
        return false;
    }
}

/**
 * Entfernt ein Produkt mit einer gewissen Artikelnummer aus dem Warenkorb eines Nutzers einer bestimmten Kundennummer
 * 
 * @param int $customerId Kundennummer des Nutzers des Warenkorbs
 * @param int $prod_itemNumber Artikelnummer des zu entfernenden Produkts
 * @param string $key Der API Key
 * 
 * @return boolean Erfolgreicher Abschluss
 */
function removeFromCart($customerId, $prod_itemNumber, $key) {
    $result = performApiRequest($key, 2, 'REMOVE', $customerId . ';' . $prod_itemNumber);
    if ($result->error == false) {
        return true;
    } else {
        return false;
    }
}
