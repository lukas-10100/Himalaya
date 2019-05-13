<!DOCTYPE html> 
<html lang="de">
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">

        <?php
        // API KEY: <key>
        ?>

        <script>

            function setCookie(cname, cvalue, exp) {
                var d = new Date();
                d.setTime(d.getTime() + (exp * 24 * 60 * 60 * 1000));
                var expires = "expires=" + d.toUTCString();
                document.cookie = cname + "=" + cvalue + ";" + expires + ";path=/";
            }
            function getCookie(cname) {
                var name = cname + "=";
                var decodedCookie = decodeURIComponent(document.cookie);
                var ca = decodedCookie.split(';');
                for (var i = 0; i < ca.length; i++) {
                    var c = ca[i];
                    while (c.charAt(0) == ' ') {
                        c = c.substring(1);
                    }
                    if (c.indexOf(name) == 0) {
                        return c.substring(name.length, c.length);
                    }
                }
                return "";
            }


            function acceptCookies() {
               setCookie("accept_cookies", "true", 600);
               document.getElementById("cookieBanner").hidden = true;
            }

        </script>

        <!-- Titel der Seite -->
        <title>Himalaya</title>

        <!-- Bootstrap und weiteres CSS-->
        <link href="css/bootstrap121.css" rel="stylesheet">
        <link href="css/additions.css" rel="stylesheet">
        <link href="css/fonts.css" rel="stylesheet">
        <link href="css/animation.css" rel="stylesheet">

        <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
        <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
        <!--[if lt IE 9]>
          <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
          <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
        <![endif]-->

        <!-- JavaScript fuer API-Zugriff mit Ajax -->
        <script>
            function performApiRequest(key, module, action, parameters, fnct) {
                var xhttp = new XMLHttpRequest();
                xhttp.onreadystatechange = fnct;
                xhttp.open("GET", "/scripts/ajax.php?key=" + encodeURIComponent(key) + "&mod=" + encodeURIComponent(module) + "&action=" + encodeURIComponent(action) + "&prm=" + encodeURIComponent(parameters), true);
                xhttp.send();
            }

            function callFunct(functionName, parameters, fnct) {
                var prm = "";
                for (var i = 0; i < parameters.length; i++) {
                    prm += parameters[i] + ";";
                }
                prm = prm.substr(0, prm.length - 1);
                var xhttp = new XMLHttpRequest();
                xhttp.onreadystatechange = fnct;
                xhttp.open("GET", "/scripts/ajax.php?fnc=" + encodeURIComponent(functionName) + "&prm=" + encodeURIComponent(prm), true);
                xhttp.send();
            }

            function formatPrice(price) {
                return parseFloat(Math.round(price * 100) / 100).toFixed(2);
            }
        </script>

        <!-- PHP-Code, bevor die Seite laedt (Nicht direkt im Browser sichtbar) -->
        <?php
        // Einbinden der Skriptdatei fuer API Zugriffe
        include_once './scripts/utils.php';

        // Session starten, damit auf Werte zugegriffen werden kann
        session_start();
        global $key;
        $key = '<key>';

        // Seitenauswahl

        $page = $_GET['p'];
        if (empty($page)) {
            $page = 'home';
        }
        if (!file_exists('content/' . $page . '.php')) {
            $page = 'notfound';
        }

        function formatPrice($price) {
            return number_format((float) $price, 2, ',', '');
        }

        // Login

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

        function hasInCart($cart, $product) {
            foreach ($cart as $c) {
                if ($c->artikelnummer == $product) {
                    return true;
                }
            }
            return false;
        }
        ?>
        <!-- Ende PHP -->

        <style>
            .himalaya-bg{
                background-image: url("./media/header.png");
                background-position: center;
                background-repeat: no-repeat;
                background-size: cover;
            }
        </style>

    </head>
    <body>

        <!-- Beginn der tatsaechlichen Internetseite -->

        <?php include 'navbar.php'; ?>

        <?php include_once 'content/' . $page . '.php'; ?>

        <!-- Modal -->
        <div class="modal fade" id="modal_login" tabindex="-1" role="dialog" aria-labelledby="modal_login_l">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <form id="form_reg" action="/scripts/register.php" method="post">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-label="Schließen"><span aria-hidden="true">&times;</span></button>
                            <h4 class="modal-title" id="modal_login_l">Registrieren</h4>
                        </div>
                        <div class="modal-body">
                            <div class="form-group">
                                <label for="reg_firstName">Vorname</label>
                                <input type="text" class="form-control" id="reg_firstName" name="reg_firstName">
                            </div>
                            <div class="form-group">
                                <label for="reg_name">Nachname</label>
                                <input type="text" class="form-control" id="reg_name" name="reg_name">
                            </div>
                            <div class="form-group">
                                <label for="reg_email">E-Mail</label>
                                <input type="email" class="form-control" id="reg_email" name="reg_email">
                            </div>
                            <div class="form-group">
                                <label for="reg_pw1">Passwort</label>
                                <input type="password" class="form-control" id="reg_pw1" name="reg_pw1">
                            </div>
                            <div class="form-group">
                                <label for="reg_pw2">Passwort wiederholen</label>
                                <input type="password" class="form-control" id="reg_pw2" name="reg_pw2">
                            </div>
                            <div class="form-group">
                                <label for="reg_country">Land</label>
                                <select id="reg_country" class="form-control" name="reg_country">
                                    <option>NONE</option>
                                </select>
                            </div>
                            <div class="checkbox">
                                <label>
                                    <input id="reg_agb" name="reg_agb" type="checkbox"> Ich habe die Datenschutzbestimmung und AGB <b>gelesen</b> und stimme diesen zu
                                </label>
                            </div>
                        </div>
                        <div class="modal-footer">
                            <button type="submit" class="btn btn-success">Registrieren</button>
                            <button type="button" class="btn btn-danger" data-dismiss="modal">Abbrechen</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>

        <script>

            var form_reg = document.getElementById("form_reg");
            var txtFirstName = document.getElementById("reg_firstName");
            var txtName = document.getElementById("reg_name");
            var txtEmail = document.getElementById("reg_email");
            var txtPW1 = document.getElementById("reg_pw1");
            var txtPW2 = document.getElementById("reg_pw2");
            var txtCountry = document.getElementById("reg_country");
            var chbxAgb = document.getElementById("reg_agb");

            function errorElement(element) {
                // CSS-Klasse hinzufuegen (Markierung eines Fehlers)
                if (!element.parentNode.className.includes("has-error")) {
                    if (element.type === "checkbox") {
                        element.parentNode.parentNode.className += " has-error";
                    } else {
                        element.parentNode.className += " has-error";
                    }
                }
            }
            function unerrorElement(element) {
                // CSS-Klasse fuer Fehler entfernen
                if (element.parentNode.className.includes("has-error")) {
                    element.parentNode.className = element.parentNode.className.replace(" has-error", "");
                    ;
                }
            }
            form_reg.onsubmit = function () {
                // Eingabe pruefen und ggf. Fehlerklasse hinzufuegen
                var sc = true;
                if (txtFirstName.value.trim() === "") {
                    errorElement(txtFirstName);
                    sc = false;
                }
                if (txtName.value.trim() === "") {
                    errorElement(txtName);
                    sc = false;
                }
                if (txtEmail.value.trim() === "") {
                    errorElement(txtEmail);
                    sc = false;
                }
                if (txtPW1.value.trim() === "") {
                    errorElement(txtPW1);
                    sc = false;
                }
                if (txtPW2.value.trim() === "") {
                    errorElement(txtPW2);
                    sc = false;
                }
                if (txtCountry.value.trim() === "") {
                    errorElement(txtCountry);
                    sc = false;
                }
                if (chbxAgb.checked === false) {
                    errorElement(chbxAgb);
                    sc = false;
                }

                if (txtPW1.value !== txtPW2.value) {
                    errorElement(txtPW2);
                    sc = false;
                }

                return sc;
            };

            // Bei erneuter Eingabe Fehlerklasse entfernen
            txtFirstName.oninput = function () {
                unerrorElement(txtFirstName);
            };
            txtName.oninput = function () {
                unerrorElement(txtName);
            };
            txtEmail.oninput = function () {
                unerrorElement(txtEmail);
            };
            txtPW1.oninput = function () {
                unerrorElement(txtPW1);
            };
            txtPW2.oninput = function () {
                unerrorElement(txtPW2);
            };
            txtCountry.oninput = function () {
                unerrorElement(txtCountry);
            };
        </script>

        <!-- Modal -->
        <div class="modal fade" id="modal_repair" tabindex="-1" role="dialog">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-body">
                        <center>
                            <h1 class="border border-success text-success" style="width: 60px; height: 60px; font-size: 50px; border-radius: 50%; border: 1px solid #28a745; padding-top: 5px">&#10003;</h1><h2>Reparatur erfolgreich!</h2><span id="errors">0</span> Viren und gefährliche Dateien wurden entfernt
                        </center>
                    </div>
                    <div class="modal-footer">
                        <center>
                            <button type="button" class="btn btn-default" data-dismiss="modal">Ok</button>
                        </center>
                    </div>
                </div>
            </div>
        </div>

        <!-- Modal -->
        <div class="modal fade" id="modal_resetpw" tabindex="-1" role="dialog" aria-labelledby="modal_login_l">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <form id="form_reg" action="/scripts/register.php" method="post">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-label="Schließen"><span aria-hidden="true">&times;</span></button>
                            <h4 class="modal-title" id="modal_login_l">Registrieren</h4>
                        </div>
                        <div class="modal-body">

                            <div class="form-group">
                                <label for="reg_email">Reset-Link an folgende E-Mail Adresse senden:</label>
                                <input type="email" class="form-control" id="reg_email" name="reg_email">
                            </div>
                        </div>
                        <div class="modal-footer">
                            <button type="submit" class="btn btn-success">Senden</button>
                            <button type="button" class="btn btn-danger" data-dismiss="modal">Abbrechen</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>

        <div id="cookieBanner" hidden="true" class="w-100 bg-darker text-light" style="height: 70px; text-align: center; padding-top: 20px; position: fixed; bottom: 0px; z-index: 9999; box-shadow: 0px -4px 8px rgba(10, 10, 10, 0.3)">
            <span style="font-size: 16pt">Cookies helfen uns bei der Bereitstellung unserer Dienste. Mit der Nutzung der Seite stimmen Sie der Verwendung von Cookies zu. <a>Mehr Erfahren</a></span>
            <button class="btn btn-warning" type="button" onclick="acceptCookies()" style="margin-left: 10px">OK</button>
        </div>

        <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>

        <script>
                $('#modal_repair').on('shown.bs.modal', function () {
                    // Automatische Reparatur: Zufaellige Fehlerzahl generieren und einfuegen
                    document.getElementById("errors").innerHTML = Math.floor((Math.random() * 19281) + 103);
                })
                
                window.onload = function (){
                    if(getCookie("accept_cookies")!="true"){
                        document.getElementById("cookieBanner").hidden = false;
                    }
                }

        </script>

        <!-- Include all compiled plugins (below), or include individual files as needed -->
        <script src="js/bootstrap.min.js"></script>
    </body>
</html>
