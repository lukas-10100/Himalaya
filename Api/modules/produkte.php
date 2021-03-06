<?php

/*
 * ?key=...&mod=0&action=GET&prm=ID-ARTIKELNUMMER-NAME
 * ?key=...&mod=0&action=SET&prm=ID:199-ARTIKELNUMMER:9821877-NAME:'NameString'
 * 
 * Aktionen: GET / SET / CREATE
 * 
 * Parameter:
 *   GET:
 *      <1> * / ID / ARTIKELNUMMER / ART / NAME / PREIS / BESTAND / BEZEICHNUNG / BILD / GEWICHT
 *      [2] sql-bedingung (z.B. WHERE `XY`='ABC' oder SORTBY `XY` DESC)
 *
 *   SET:
 *      <1> ID>WERT / ARTIKELNUMMER>WERT / ART>WERT / NAME>WERT / PREIS>WERT / BESTAND>WERT / BEZEICHNUNG>WERT / BILD>WERT / GEWICHT>WERT
 *      <2> sql-bedingung
 * 
 *   CREATE:
 *      <1> ARTIKELNUMMER {int}
 *      <2> ART {string}
 *      <3> NAME {string}
 *      <4> PREIS {float}
 *      <5> BESTAND {1/0}
 *      <6> BILD {string}
 *      <7> BEZEICHNUNG {string}
 *      <8> GEWICHT {int}
 *   DELETE:
 *      <1> bedingung (z.B. `name`='TEST')
 * 
 */

function exec_produkte($action, $params) {
    switch (strtolower($action)) {
        case 'get':
            if (sizeof($params) > 0) {
                $cond = '';
                if ($params[1]) {
                    $cond = ' ' . $params[1];
                }
                $sql = "SELECT " . buildValuesSELECT($params[0]) . " FROM `produkte`" . $cond;
                $result = query($sql);
                if ($result === null) {
                    return array(
                        'error' => true,
                        'code' => 386,
                        'description' => 'Datenbankfehler: Keine Verbindung'
                    );
                }
                $numrows = mysqli_num_rows($result);
                if ($numrows > 0) {
                    $rows = array($numrows);
                    $tmp_1923 = 0;
                    while ($row = mysqli_fetch_assoc($result)) {
                        $rows[$tmp_1923] = $row;
                        $tmp_1923 += 1;
                    }
                    return $rows;
                } else {
                    return array(
                        'error' => true,
                        'code' => 404,
                        'description' => 'Keine Eintraege gefunden'
                    );
                }
            } else {
                return array(
                    'error' => true,
                    'code' => 106,
                    'description' => 'Ungueltige Aktion'
                );
            }
            break;
        case 'set':
            if (sizeof($params) == 2) {
                $cond = '';
                if ($params[1]) {
                    $cond = ' ' . $params[1];
                }
                $sql = "UPDATE `produkte` SET  " . buildValuesUPDATE($params[0]) . "" . $cond;
                $result = query($sql);
                if ($result === null) {
                    return array(
                        'error' => true,
                        'code' => 386,
                        'description' => 'Datenbankfehler: Keine Verbindung'
                    );
                }
                if ($result == 1) {
                    return array(
                        'error' => false,
                        'code' => 0,
                        'description' => 'Aenderung erfolgreich'
                    );
                } else {
                    return array(
                        'error' => true,
                        'code' => 385,
                        'description' => 'Datenbankfehler'
                    );
                }
            } else {
                return array(
                    'error' => true,
                    'code' => 106,
                    'description' => 'Ungueltige Aktion'
                );
            }
            break;
        case 'create':
            if (sizeof($params) == 8) {
                $cond = '';
                $sql = "INSERT INTO `produkte`(`artikelnummer`, `art`, `name`, `preis`, `bestand`, `bild`, `bezeichnung`, `gewicht`) VALUES (".$params[0].",'".$params[1]."','".$params[2]."',".$params[3].",".$params[4].",'".$params[5]."','".$params[6]."',".$params[7].")";
                $result = query($sql);
                if ($result === null) {
                    return array(
                        'error' => true,
                        'code' => 386,
                        'description' => 'Datenbankfehler: Keine Verbindung'
                    );
                }
                if ($result == 1) {
                    return array(
                        'error' => false,
                        'code' => 0,
                        'description' => 'Erstellung erfolgreich'
                    );
                } else {
                    return array(
                        'error' => true,
                        'code' => 385,
                        'description' => 'Datenbankfehler'
                    );
                }
            } else {
                return array(
                    'error' => true,
                    'code' => 106,
                    'description' => 'Ungueltige Aktion'
                );
            }
            break;
        case 'delete':
            if (sizeof($params) == 1) {
                $cond = '';
                $sql = "DELETE FROM `produkte` WHERE " . $params[0];
                $result = query($sql);
                if ($result === null) {
                    return array(
                        'error' => true,
                        'code' => 386,
                        'description' => 'Datenbankfehler: Keine Verbindung'
                    );
                }
                if ($result == 1) {
                    return array(
                        'error' => false,
                        'code' => 0,
                        'description' => 'Loeschung erfolgreich'
                    );
                } else {
                    return array(
                        'error' => true,
                        'code' => 385,
                        'description' => 'Datenbankfehler'
                    );
                }
            } else {
                return array(
                    'error' => true,
                    'code' => 106,
                    'description' => 'Ungueltige Aktion'
                );
            }
            break;
        default:
            return array(
                'error' => true,
                'code' => 106,
                'description' => 'Ungueltige Aktion'
            );
    }
}

function buildValuesSELECT($param) {
    $arr = explode('|', $param);
    $ret = '';
    foreach ($arr as $item) {
        $item = strtolower($item);
        if ($item != '*') {
            $ret .= '`' . $item . '`,';
        } else {
            return '*';
        }
    }
    return substr($ret, 0, strlen($ret) - 1);
}

function buildValuesUPDATE($param) {
    $arr = explode('|', $param);
    $ret = '';
    foreach ($arr as $item) {
        $itemName = strtolower(explode('>', $item)[0]);
        $itemValue = explode('>', $item)[1];

        $ret .= '`' . $itemName . '`=' . $itemValue . ',';
    }
    return substr($ret, 0, strlen($ret) - 1);
}

function query($sql) {
    $db_user = '<user>';
    $db_database = '<db>';
    $db_password = '<password>';
    $db_host = '<host>';
    $mysql = mysqli_connect($db_host, $db_user, $db_password, $db_database);
    if (!$mysql) {
        return null;
    }
    mysqli_query($mysql, "SET NAMES 'utf8'");
    $result = mysqli_query($mysql, $sql);
    //echo $sql;
    mysqli_close($mysql);
    return $result;
}
