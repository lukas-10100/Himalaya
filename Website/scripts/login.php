<?php

$email = $_POST['email'];
$password = $_POST['pw'];
$key = '<key>';
include_once './utils.php';

if (!empty($email) && !empty($password)) {
    // Nutzer laden und auf Existenz pruefen
    $user = getUsersByEmail($email, $key)[0];
    if (!array_key_exists('error', $user)) {
        $pw = $user->passwort;

        if ($pw === $password) {

            // Session beginnen und Werte zuweisen
            session_start();

            $_SESSION['customerID'] = $user->kundennummer;
            $_SESSION['sid'] = hash('md5', $password . $_SESSION['customerID']);

            // Wieder auf die Startseite navigieren
            header('Location: /index.php?err=2&t=120');
            die();
            echo 'Success';
        }
        echo 'Invalid Password';
    }
    echo 'Not Found';
}
echo 'Empty';
header('Location: /index.php?err=1&t=120');
die();
