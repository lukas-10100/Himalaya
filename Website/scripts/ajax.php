<?php

$key = $_GET['key'];
$module = $_GET['mod'];
$action = $_GET['action'];
$parameters = $_GET['prm'];

$function = $_GET['fnc'];

$key = str_replace('[KEY]', '<key>', $key);
$parameters = str_replace('[KEY]', '<key>', $parameters);


// Art der Anfrage ermitteln
// Funktionsaufruf oder API-Request
if (empty($function)) {
    include_once './utils.php';
    // PHP-Funktion aufrufen und die Daten als JSON-Text ausgeben
    echo json_encode(performApiRequest($key, $module, $action, $parameters));
} else {
    include_once './utils.php';
    // Pruefen, ob angefragte Funktion existiert
    if(function_exists($function)){
        // Wenn ja, diese Funktion aufrufen und das angegebene Array als Parameter uebergeben
        // Den Rueckgabewerd der Funktion ausgeben
        echo json_encode(call_user_func_array($function, explode(';', $parameters)));
    }else{
        // Wenn nicht, Fehler ausgeben
        return json_encode(array(
            'error' => true,
            'code' => 0,
            'description' => 'Ungültige Funktion'
        ));
    }
}