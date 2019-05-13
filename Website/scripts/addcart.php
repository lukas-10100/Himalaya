<?php

$prod = $_GET['prod'];
$cid = $_GET['cid'];
$key = '<key>';

include_once './utils.php';
session_start();

function isLoggedIn() {
    $cid = $_SESSION['customerID'];
    $sid = $_SESSION['sid'];
    if (empty($cid) || empty($sid)) {
        return false;
    } else {
        if ($sid == hash('md5', user_getPassword($cid, '<key>') . $cid)) {
            return true;
        } else {
            return false;
        }
    }
}

try {
    if (isLoggedIn()) {

        function hasInCart($cart, $product) {
            foreach ($cart as $c) {
                if ($c->artikelnummer == $product) {
                    return true;
                }
            }
            return false;
        }

        $cartContent = getCartContentByCustomerID($cid, $key);
        if (!hasInCart($cartContent, $prod)) {
            addToCart($cid, $prod, 1, $key);
        }
    }
} catch (Exception $e) {
    
}
header('Location: /index.php?p=cart');
die();
