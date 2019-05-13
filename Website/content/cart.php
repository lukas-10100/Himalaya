<style>
    .btn-delete{}
    .txt-amount{}
</style>

<script>

    function recalc() {
        // Variable zum Zaehlen der Summe
        var c = 0;
        // Alle Elemente mit entsprechender Klasse
        var x = document.getElementsByClassName("form-control txt-amount");
        for (i = 0; i < x.length; i++) {
            var item = x[i];
            // Artikelnummer aus DOM ID
            pid = item.id;
            pid = pid.replace("amount_", "");
            var price = document.getElementById("price_" + pid).innerHTML;
            price = price.replace("EUR", "").replace(",", ".").trim();
            var amount = item.value;
            if (amount > 0) {
                // Preis * Menge zur Summe addieren
                c = c + (amount * price);
            }
        }
        // Summe in Seite eintragen und in Datenbank speichern
        document.getElementById("sum").innerHTML = formatPrice(c).replace(".", ",");
        saveCart();
    }


    function saveCart() {
        // Funktion aufrufen
        callFunct("getCartByCustomerID", [<?php
if (isLoggedIn()) {
    echo $_SESSION['customerID'];
} else {
    echo '-1';
}
?>, "[KEY]"], function () {
            if (this.readyState === 4 && this.status === 200) {
                // Vorherige Produkte im Warenkorb auslesen
                var cart = JSON.parse(this.responseText);
                var content = JSON.parse(cart.inhalt);
                for (i = 0; i < content.length; i++) {
                    // Neue Menge setzen
                    var nr = content[i].artikelnummer;
                    var inp = document.getElementById("amount_" + nr);
                    content[i].menge = parseInt(inp.value);
                }
                // Neuen Inhalt fuer Warenkorb speichern
                performApiRequest("[KEY]", 2, "SET", "KUNDENNUMMER>" + cart.kundennummer + "|INHALT>'" + JSON.stringify(content) + "';WHERE `kundennummer`=" + cart.kundennummer, function () {
                    if (this.readyState === 4 && this.status === 200) {
                        // nothing.
                    }
                });
            }
        });
    }


</script>

<div class="container-fluid">

    <?php
    if (!isLoggedIn()) {
        echo '<center><div class="w-50 alert alert-danger" role="alert">Sie müssen angemeldet sein, um diese Seite sehen zu können</div></center>';
    }
    ?>

    <div class="well" <?php if (!isLoggedIn()) {
        echo 'hidden';
    } ?> style="width: 90%; margin-left: 5%">
        <div class="panel panel-default">
            <div class="panel-heading" style="color: #000000; background-color: #ffffff; font-size: 16pt"><span class="glyphicon glyphicon-lock" style="margin-right: 10px" accesskey=""aria-hidden="true"></span><strong>Einkaufwagen</strong></div>
            <table class="table table">
                <tbody>
                    <tr>
                        <td class="f-wind col-md-8" style="margin-right: 10px"><strong>Produkt</strong></td>
                        <td class="col-md-2 text-right" style="min-width: 150px; margin-right: 10px"><strong>Preis</strong></td>
                        <td class="col-md-2 text-right" style="min-width: 150px; margin-right: 10px"><strong>Menge</strong></td>
                    </tr>
                    <?php
                    // Produkte laden
                    if (isLoggedIn()) {
                        $cartProducts = json_decode(getCartByCustomerID($_SESSION['customerID'], $key)->inhalt);
                        $sum = 0;
                        foreach ($cartProducts as $prod) {
                            // Einzelnes Produkt ausgeben
                            $price = product_getPrice($prod->artikelnummer, $key);
                            echo '<tr id="entr_' . $prod->artikelnummer . '">
                        <td>
                            <img class="img-responsive" style="max-height: 60px; float: left; margin-right: 10px; border: solid; border-width: 1px; border-color: #cccccc; border-radius: 2px" src="' . product_getImage($prod->artikelnummer, $key) . '" alt="?"/>

<button id="del_' . $prod->artikelnummer . '" class="btn btn-default btn-delete" style="float: right;border-color: grey; color: #ff00cc; font-size: 7pt;
    background-color: white">Löschen</button>

                            <h4>' . product_getName($prod->artikelnummer, $key) . '</h4>
                            Art.Nr.: ' . $prod->artikelnummer . '

                        </td>
                        <td class="text-right" id="price_' . $prod->artikelnummer . '">EUR ' . formatPrice($price) . '</td>
                        <td class="text-right">
                            <input type="number" min="1" onchange="recalc()" data-product="' . $prod->artikelnummer . '" class="form-control txt-amount" id="amount_' . $prod->artikelnummer . '" value="' . $prod->menge . '">
                    </tr>';
                            // Preise summieren
                            $sum = $sum + (product_getPrice($prod->artikelnummer, $key) * $prod->menge);
                        }
                    }
                    ?>
                    <tr>
                        <td></td>
                        <td style="font-size: 14pt; margin-right: 10px"><strong>Summe:    </strong></td>
                        <td class=" text-right" style="font-size: 14pt; margin-right: 10px"><strong>EUR <span id="sum"><?php echo formatPrice($sum); ?></span></strong></td>
                    </tr>
                </tbody>
            </table>
        </div>
        <div>
            <a class="btn btn-default" href="?p=pay" style="font-size: 20pt; background-color: #ff00cc; color: white; border-color: #cccccc; border-width: 2px">Bezahlen</a>
        </div>
    </div>
</div>

<script>

    function remove(product) {
        // Funktion aufrufen
        callFunct("removeFromCart", [<?php
                    if (isLoggedIn()) {
                        echo $_SESSION['customerID'];
                    } else {
                        echo '-1';
                    }
                    ?>, product, "[KEY]"], function () {
            if (this.status === 200 && this.readyState === 4) {

                if (this.responseText === "true") {
                    var b = document.getElementById("entr_" + product);
                    // Listeneintrag aus DOM entfernen und Preis neu berechnen
                    b.parentNode.removeChild(b);
                    recalc();
                } else {
                    alert("Fehler beim Entfernen des Produkts");
                }

            }
        });
    }

// Alle Loeschen-Buttons (nach CSS-Klasse) aus DOM lesen
    var btns = document.getElementsByClassName("btn btn-default btn-delete");
    for (i = 0; i < btns.length; i++) {
        // Neues onclick-Event zuweisen
        btns[i].onclick = function () {
            var pid = this.id;
            pid = pid.replace("del_", "");
            // Zeile entfernen
            remove(pid);
        };
    }



</script>