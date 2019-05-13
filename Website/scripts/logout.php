<?php

// Session aufloesen
if (isset($_SESSION)) {
    session_destroy();
}

// Alle Cookies entfernen:
// Leeren Wert zuweisen und das Ablaufdatum in die Vergangenheit setzen
if (isset($_SERVER['HTTP_COOKIE'])) {
    $cookies = explode(';', $_SERVER['HTTP_COOKIE']);
    foreach ($cookies as $cookie) {
        $parts = explode('=', $cookie);
        $name = trim($parts[0]);
        setcookie($name, '', time() - 1000);
        setcookie($name, '', time() - 1000, '/');
    }
    
    $params = session_get_cookie_params();
    setcookie(session_name(), '', 0, $params['path'], $params['domain'], $params['secure'], isset($params['httponly']));
}


// Umleitung auf die Startseite
header("Location: /index.php");
die();
