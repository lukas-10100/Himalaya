<div class="container-fluid w-100">


    <div class="well" style="width: 90%; foont-size: 20pt; margin-left:  5%">
        <div class="row">
            <div class="col-md-6">
                <div class="row">
                    <div class="col-md-6">
                        <div class="btn-group">
                            <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                <span id="anrede">Anrede</span><span class="caret"></span>
                            </button>
                            <ul class="dropdown-menu">
                                <li><a id="anredeHerr">Herr</a></li>
                                <li><a id="anredeFrau">Frau</a></li>
                            </ul>
                            <br/>
                        </div>
                        <br/>
                        <?php $addr = user_getAddress($_SESSION['customerID'], $key); ?>
                        <form>
                            <div class="form-group">
                            </div>
                            <div class="form-group">
                                <label for="beispielFeldPasswort1" style="margin-top: 5px">Vorname</label>
                                <input type="text" class="form-control" id="beispielFeldPasswort1" placeholder="..." <?php
                                if (isLoggedIn()) {
                                    echo 'value="' . split(',', user_getName($_SESSION['customerID'], $key))[1] . trim() . '"';
                                }
                                ?>>
                            </div>
                            <div class="form-group">
                                <label for="beispielFeldPasswort1">Familienname</label>
                                <input type="text" class="form-control" id="beispielFeldPasswort1" placeholder="..." <?php
                                if (isLoggedIn()) {
                                    echo 'value="' . split(',', user_getName($_SESSION['customerID'], $key))[0] . trim() . '"';
                                }
                                ?>>
                            </div>
                            <div class="f-wind" style="font-size: 20pt"><strong>Rechnugsadresse</strong></div>
                            <div class="row">
                                <div class="form-group col-md-9">
                                    <label for="beispielFeldPasswort1">Straße</label>
                                    <input type="text" class="form-control" id="beispielFeldPasswort1" placeholder="..." <?php
                                    if (isLoggedIn()) {
                                        echo 'value="' . $addr->line1 . '"';
                                    }
                                    ?>>
                                </div>
                                <div class="form-group col-md-3">
                                    <label for="beispielFeldPasswort1">Nr.</label>
                                    <input type="text" class="form-control" id="beispielFeldPasswort1" placeholder="..." <?php
                                    if (isLoggedIn()) {
                                        echo 'value="' . $addr->line2 . '"';
                                    }
                                    ?>>
                                </div>
                            </div>
                            <div class="row">
                                <div class="form-group col-md-4">
                                    <label for="beispielFeldPasswort1">PLZ</label>
                                    <input type="text" class="form-control" id="beispielFeldPasswort1" placeholder="..." <?php
                                    if (isLoggedIn()) {
                                        echo 'value="' . $addr->postalCode . '"';
                                    }
                                    ?>>
                                </div>
                                <div class="form-group col-md-8">
                                    <label for="beispielFeldPasswort1">Ort</label>
                                    <input type="text" class="form-control" id="beispielFeldPasswort1" placeholder="..." <?php
                                    if (isLoggedIn()) {
                                        echo 'value="' . $addr->city . '"';
                                    }
                                    ?>>
                                </div>
                            </div>
                        </form>
                    </div>
                    <div class="col-md-6">
                        <form id="form_lieferaddr">
                            <div class="form-group">
                            </div>
                            <div class="f-wind" style="font-size: 20pt"><strong>Lieferadresse</strong></div>
                            <div class="checkbox">
                                <label>
                                    <input type="checkbox" style="font-size: 15pt" id="chbxEquals"> Lieferadresse entspricht Rechnugsadresse
                                </label>
                            </div>
                            <div class="form-group">
                                <label for="beispielFeldPasswort1">Vorname</label>
                                <input type="text" class="form-control" id="beispielFeldPasswort1" placeholder="...">
                            </div>
                            <div class="form-group">
                                <label for="beispielFeldPasswort1">Familienname</label>
                                <input type="text" class="form-control" id="beispielFeldPasswort1" placeholder="...">
                            </div>
                            <div class="row">
                                <div class="form-group col-md-9">
                                    <label for="beispielFeldPasswort1">Straße</label>
                                    <input type="text" class="form-control" id="beispielFeldPasswort1" placeholder="...">
                                </div>
                                <div class="form-group col-md-3">
                                    <label for="beispielFeldPasswort1">Nr.</label>
                                    <input type="text" class="form-control" id="beispielFeldPasswort1" placeholder="...">
                                </div>
                            </div>
                            <div class="row">
                                <div class="form-group col-md-4">
                                    <label for="beispielFeldPasswort1">PLZ</label>
                                    <input type="text" class="form-control" id="beispielFeldPasswort1" placeholder="...">
                                </div>
                                <div class="form-group col-md-8">
                                    <label for="beispielFeldPasswort1">Ort</label>
                                    <input type="text" class="form-control" id="beispielFeldPasswort1" placeholder="...">
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
            <div class="col-md-6">

                <div class="f-wind" style="font-size: 20pt"><strong>Zahlungsart</strong></div>
                <br/>
                <img src="/media/paypal.png" height="32px" style="margin-right: 5%"/> 
                <img src="/media/mastercard.png" height="32px"/> 
                <img src="/media/visa.png" height="32px" style="margin-right: 5%"/> 
                <img src="/media/amazon-payments.png" height="32px"/> 
                <br/><br/>
                <div class="btn-group btn-group-justified" role="group" aria-label="...">
                    <div class="btn-group" role="group">
                        <button type="button" class="btn btn-default active" selected id="btnPaypal">Paypal</button>
                    </div>
                    <div class="btn-group" role="group">
                        <button type="button" class="btn btn-default" id="btnKreditkarte">Kreditkarte</button>
                    </div>
                    <div class="btn-group" role="group">
                        <button type="button" class="btn btn-default" id="btnRechnung">Rechnung</button>
                    </div>
                    <div class="btn-group" role="group">
                        <button type="button" class="btn btn-default" id="btnAmazonPayment">Amazon-Payment</button>
                    </div>
                </div>
                <br/>
                <div class="f-wind" style="font-size: 20pt"><strong>Versandart</strong></div>
                <br/>
                <div class="btn-group btn-group-vertical" style="width: 100%" role="group" aria-label="...">
                    <div class="btn-group" role="group">
                        <button type="button" class="btn btn-default" id="btnNormal">
                            <strong>Normal</strong>
                            <br/>
                            9-7 Werktage Lieferzeitraum / Kostenlos
                        </button>
                    </div>
                    <div class="btn-group" role="group">
                        <button type="button" class="btn btn-default active" selected id="btnPremium">
                            <strong>Permium</strong>
                            <br/>
                            2-3 Werktage Lieferzeitraum / 3,40€
                        </button>
                    </div>
                    <div class="btn-group" role="group">
                        <button type="button" class="btn btn-default" id="btnSPremium">
                            <strong>Sup-Premium</strong>
                            <br/>
                            1-2 Werktage Lieferzeitraum / 5,90€
                        </button>
                    </div>
                </div>
            </div>
        </div>    
    </div>

    <div class="text-center"> 
        <div class="checkbox">
            <label>
                <input type="checkbox" style="font-size: 15pt"> Akzeptieren der allg. Geschätsbedingungen 
            </label>
        </div>
        <button data-toggle="modal" data-target="#modal_done" class="btn btn-default" style="font-size: 20pt; background-color: #ff00cc; color: white">Bestellen</button>
    </div>

    <br/>


</div>

<!-- Modal -->
<div class="modal fade" id="modal_done" tabindex="-1" role="dialog">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-body">
                <center>
                    <h1 class="border border-success text-success" style="width: 60px; height: 60px; font-size: 50px; border-radius: 50%; border: 1px solid #28a745; padding-top: 5px">&#10003;</h1><h2>Bestellung erfolgreich!</h2>
                </center>
            </div>
            <div class="modal-footer">
                <center>
                    <a type="button" class="btn btn-default" href="?p=home">Ok</a>
                </center>
            </div>
        </div>
    </div>
</div>

<script>
    // Auswahl Anrede
    document.getElementById("anredeHerr").onclick = function () {
        document.getElementById("anrede").innerHTML = "Herr";
    };
    document.getElementById("anredeFrau").onclick = function () {
        document.getElementById("anrede").innerHTML = "Frau";
    };

    // Auswahl Zahlungsart

    var btnPaypal = document.getElementById("btnPaypal");
    var btnKreditkarte = document.getElementById("btnKreditkarte");
    var btnRechnung = document.getElementById("btnRechnung");
    var btnAmazonPayment = document.getElementById("btnAmazonPayment");

    function selectedClass(element, val) {
        if (!val) {
            element.className = element.className.replace("active", "");
        } else {
            if (!element.className.includes("active")) {
                element.className += " active";
            }
        }
    }

    btnPaypal.onclick = function () {
        btnAmazonPayment.selected = false;
        btnKreditkarte.selected = false;
        btnRechnung.selected = false;
        btnPaypal.selected = true;
        selectedClass(btnPaypal, true);
        selectedClass(btnRechnung, false);
        selectedClass(btnKreditkarte, false);
        selectedClass(btnAmazonPayment, false);
    }
    btnRechnung.onclick = function () {
        btnAmazonPayment.selected = false;
        btnKreditkarte.selected = false;
        btnRechnung.selected = true;
        btnPaypal.selected = false;
        selectedClass(btnPaypal, false);
        selectedClass(btnRechnung, true);
        selectedClass(btnKreditkarte, false);
        selectedClass(btnAmazonPayment, false);
    }
    btnKreditkarte.onclick = function () {
        btnAmazonPayment.selected = false;
        btnKreditkarte.selected = true;
        btnRechnung.selected = false;
        btnPaypal.selected = false;
        selectedClass(btnPaypal, false);
        selectedClass(btnRechnung, false);
        selectedClass(btnKreditkarte, true);
        selectedClass(btnAmazonPayment, false);
    }
    btnAmazonPayment.onclick = function () {
        btnAmazonPayment.selected = true;
        btnKreditkarte.selected = false;
        btnRechnung.selected = false;
        btnPaypal.selected = false;
        selectedClass(btnPaypal, false);
        selectedClass(btnRechnung, false);
        selectedClass(btnKreditkarte, false);
        selectedClass(btnAmazonPayment, true);
    }

    // Versandart

    var btnNormal = document.getElementById("btnNormal");
    var btnPremium = document.getElementById("btnPremium");
    var btnSPremium = document.getElementById("btnSPremium");

    btnNormal.onclick = function () {
        btnNormal.selected = true;
        btnPremium.selected = false;
        btnSPremium.selected = false;
        selectedClass(btnNormal, true);
        selectedClass(btnPremium, false);
        selectedClass(btnSPremium, false);
    }
    btnPremium.onclick = function () {
        btnNormal.selected = false;
        btnPremium.selected = true;
        btnSPremium.selected = false;
        selectedClass(btnNormal, false);
        selectedClass(btnPremium, true);
        selectedClass(btnSPremium, false);
    }
    btnSPremium.onclick = function () {
        btnNormal.selected = false;
        btnPremium.selected = false;
        btnSPremium.selected = true;
        selectedClass(btnNormal, false);
        selectedClass(btnPremium, false);
        selectedClass(btnSPremium, true);
    }

</script>