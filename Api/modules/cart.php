<?php

/*
 * Aktionen: GET / SET / CREATE / DELETE / APPEND
 * 
 * Parameter:
 *   GET:
 *      <1> * / ID / KUNDENNUMMER / INHALT
 *      [2] sql-bedingung (z.B. WHERE `XY`='ABC' oder SORTBY `XY` DESC)
 *
 *   SET:
 *      <1> ID>WERT / KUNDENNUMMER>WERT / INHALT>WERT
 *      <2> sql-bedingung
 * 
 *   CREATE:
 *      <1> KUNDENNUMMER
 *      <2> INHALT
 *   DELETE:
 *      <1> bedingung (z.B. `name`='TEST')
 *   APPEND:
 *      <1> KUNDENNUMMER
 *      <2> INHALT
 *   REMOVE
 *      <1> KUNDENNUMMER
 *      <2> ARTIKELNUMMER
 * 
 */

function exec_cart($action, $params) {
    switch (strtolower($action)) {
        case 'get':
            if (sizeof($params) > 0) {
                $cond = '';
                if ($params[1]) {
                    $cond = ' ' . $params[1];
                }
                $sql = "SELECT " . buildValuesSELECT($params[0]) . " FROM `carts`" . $cond;
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
                $sql = "UPDATE `carts` SET  " . buildValuesUPDATE($params[0]) . "" . $cond;
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
            if (sizeof($params) == 2) {
                $cond = '';
                $sql = "INSERT INTO `carts`(`kundennummer`,`inhalt`) VALUES (" . $params[0] . ",'" . $params[1] . "')";
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
                $sql = "DELETE FROM `carts` WHERE " . $params[0];
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
        case 'append':
            if (sizeof($params) == 2) {
                $sql = "SELECT `inhalt` FROM `carts` WHERE `kundennummer`=" . $params[0];
                $result = query($sql);
                if ($result === null) {
                    return array(
                        'error' => true,
                        'code' => 386,
                        'description' => 'Datenbankfehler: Keine Verbindung'
                    );
                } else {
                    $row = mysqli_fetch_row($result);
//                    print_r($row);
                    $inh = $row[0];
                    $inh_obj = json_decode($inh);
                    array_push($inh_obj, buildAppendProduct($params[1]));
                    $sql = "UPDATE `carts` SET `inhalt`='" . json_encode($inh_obj) . "' WHERE `kundennummer`=" . $params[0];
                    echo $sql;
                    $result = query($sql);
                    if ($result == 1) {
                        return array(
                            'error' => false,
                            'code' => 0,
                            'description' => 'Hinzufuegen erfolgreich'
                        );
                    } else {
                        return array(
                            'error' => true,
                            'code' => 385,
                            'description' => 'Datenbankfehler'
                        );
                    }
                }
            } else {
                return array(
                    'error' => true,
                    'code' => 106,
                    'description' => 'Ungueltige Aktion'
                );
            }
            break;
        case 'remove':
            if (sizeof($params) == 2) {
                $sql = "SELECT `inhalt` FROM `carts` WHERE `kundennummer`=" . $params[0];
                $result = query($sql);
                if ($result === null) {
                    return array(
                        'error' => true,
                        'code' => 386,
                        'description' => 'Datenbankfehler: Keine Verbindung'
                    );
                } else {
                    $row = mysqli_fetch_row($result);
                    $inh = $row[0];
                    $inh_obj = json_decode($inh);
                    $newArr = array();
                    for ($tmp214 = 0; $tmp214 < sizeof($inh_obj); $tmp214++) {
                        try {
                            if ($inh_obj[$tmp214]->artikelnummer != intval($params[1])) {
                                array_push($newArr, $inh_obj[$tmp214]);
                            }
                        } catch (Exception $exc) { }
                    }
                    $newInh = json_encode($newArr);
                    if (!$newInh || $newInh == 'null') {
                        $newInh = '[]';
                    }
                    $sql = "UPDATE `carts` SET `inhalt`='" . $newInh . "' WHERE `kundennummer`=" . $params[0];
                    echo $sql;
                    $result = query($sql);
                    if ($result == 1) {
                        return array(
                            'error' => false,
                            'code' => 0,
                            'description' => 'Entfernen erfolgreich'
                        );
                    } else {
                        return array(
                            'error' => true,
                            'code' => 385,
                            'description' => 'Datenbankfehler'
                        );
                    }
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

function buildAppendProduct($param) {
    $arr = explode('|', $param);
    $ret = array(
        'artikelnummer' => 0,
        'name' => 'null',
        'menge' => 0
    );
    foreach ($arr as $item) {
        $itemName = strtolower(explode('>', $item)[0]);
        $itemValue = explode('>', $item)[1];
        if ($itemName == 'artikelnummer') {
            $ret['artikelnummer'] = (int) $itemValue;
        } else if ($itemName == 'name') {
            $ret['name'] = $itemValue;
        } else if ($itemName == 'menge') {
            $ret['menge'] = (int) $itemValue;
        }
    }
    return $ret;
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
//    echo(mysqli_error($mysql));
    mysqli_close($mysql);
    return $result;
}
