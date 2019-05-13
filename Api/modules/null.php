<?php

function exec_null($action, $params) {
    return array(
        'error' => true,
        'code' => 404,
        'description' => 'Ungueltige Anfrage'
    );
}
