<?php

$firstName = $_POST['reg_firstName'];
$name = $_POST['reg_name'];
$email = $_POST['reg_email'];
$password1 = $_POST['reg_pw1'];
$password2 = $_POST['reg_pw2'];
$country = $_POST['reg_country'];
$agb = $_POST['reg_agb'] == 'on';
$key = '<key>';

if ($agb) {
    if (!empty($firstName) && !empty($name) && !empty(email) && !empty(password1) && !empty(password2) && !empty($country)) {
        include_once './utils.php';
        registerUser($key, $name, $firstName, $email, $password1, $password2, $country);
        header('Location: /index.php?err=2&t=130');
        die();
    } else {
        header('Location: /index.php?err=1&t=130');
        die();
    }
} else {
    header('Location: /index.php?err=1&t=130');
    die();
}