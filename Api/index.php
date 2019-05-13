<?php

// Konstanten
$LOCAL_KEY = '<key>';

// GET-Parameter abrufen
$key = $_GET['key'];
$mod = $_GET['mod'];
$action = $_GET['action'];
$params = $_GET['prm'];

// Zugriffspruefung
if ($key == $LOCAL_KEY) {
    // Datei des Moduls einbinden
    include_once './modules/' . translateModule($mod) . '.php';
    // Funktion zum Ausfuehren des Moduls aufrufen ("exec_MODULNAME")
    $json = call_user_func_array('exec_' . translateModule($mod), array($action, explode(';', $params)));
    echo json_encode($json, JSON_HEX_TAG | JSON_HEX_APOS | JSON_HEX_QUOT | JSON_HEX_AMP | JSON_UNESCAPED_UNICODE);
} else {
    echo createError(503, 'Zugriff verweigert');
}

function translateModule($modu) {
    // Zahlenwert in entsprechendes Modul als Text umwandeln
    switch ($modu) {
        case 0:
            return 'produkte';
        case 1:
            return 'nutzer';
        case 2:
            return 'cart';
        default:
            return 'null';
    }
}

function createError($num, $desc) {
    // JSON-Objekt erstellen
    $obj = array(
        'error' => true,
        'code' => $num,
        'description' => $desc
    );

    return json_encode($obj);
}
