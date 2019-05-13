<div class="container-fluid">


    <div class="row">
        <div class="col-md-3 w-90" style="font-size: 15pt">
            <div class="panel panel-default" style="margin-left: 30px; background-color: #ffffff" >
                <div class="panel-heading" style="font-size: 15pt; color: #ff00cc; font-style: initial">Filter</div>
                <div class="panel-body">
                    Verfügbarkeit
                    <br/>
                    <div class="btn-group-vertical w-50" role="group" aria-label="">
                        <button type="button" id="btn_filter_aufLager" class="btn btn-default">Auf Lager.</button>
                        <button type="button" id="btn_filter_nichtVerfugbar" class="btn btn-default">Nicht Verfügabar.</button>

                    </div>
                </div>
                <div class="panel-body">
                    Preis
                    <form class="form-inline w-50">
                        <div class="form-group">
                            <label class="sr-only" for="txt_filter_von">.</label>
                            <div class="input-group">
                                <input type="number" class="form-control" id="txt_filter_von" placeholder="Von...">
                            </div>
                        </div>
                    </form>

                    <form class="form-inline w-50">
                        <div class="form-group">
                            <label class="sr-only" for="txt_filter_bis">.</label>
                            <div class="input-group">
                                <input type="number" class="form-control" id="txt_filter_bis" placeholder="Bis...">
                            </div>
                        </div>
                    </form>


                </div>
                <div class="panel-body">
                    Farbe
                    <br/>
                    <div class="btn-group">
                        <button type="button" style="background-color: blue; height: 30px; width: 30px" ></button>
                        <button type="button" style="background-color: blueviolet; height: 30px; width: 30px"></button>
                        <button type="button" style="background-color: #ff00cc; height: 30px; width: 30px"></button>
                        <button type="button" style="background-color: red; height: 30px; width: 30px"></button>
                        <button type="button" style="background-color: orange; height: 30px; width: 30px"></button>
                        <button type="button" style="background-color: yellow; height: 30px; width: 30px"></button>
                        <br/>
                        <button type="button" style="background-color: yellowgreen; height: 30px; width: 30px"></button>
                        <button type="button" style="background-color: green; height: 30px; width: 30px"></button>
                        <button type="button" style="background-color: darkgreen; height: 30px; width: 30px"></button>
                        <button type="button" style="background-color: black; height: 30px; width: 30px"></button>
                        <button type="button" style="background-color: grey; height: 30px; width: 30px"></button>
                        <button type="button" style="background-color: lightgrey; height: 30px; width: 30px"></button>

                    </div>
                </div>
                <div class="panel-body">
                    Kategorie
                    <br/>
                    <div class="btn-group-vertical w-50" role="group" aria-label="">
                        <button type="button" id="btn_filter_kPflanzen" class="btn btn-default">Pflanzen</button>
                        <button type="button" id="btn_filter_kGeback" class="btn btn-default">Gebäck</button>
                        <button type="button" id="btn_filter_kBuch" class="btn btn-default">Buch</button>
                        <button type="button" id="btn_filter_kGewurz" class="btn btn-default">Gewürz</button>
                        <button type="button" id="btn_filter_kGarten" class="btn btn-default">Gartenutensilien</button>
                        <button type="button" id="btn_filter_kKuche" class="btn btn-default">Küchengeräte</button>
                        <button type="button" id="btn_filter_kKleidung" class="btn btn-default">Kleidung</button>
                        <button type="button" id="btn_filter_kTier" class="btn btn-default">Tier</button>
                        <button type="button" id="btn_filter_kLebensmittel" class="btn btn-default">Lebensmittel</button>
                    </div>
                    <br/><br/>
                    <button type="button" class="btn" id="btn_filter_anwenden" style="width: 201px; color: white; background-color: #ff00cc">Anwenden</button>
                </div>


            </div>
        </div>

        <div class="col-md-9">
            <div class="row" id="product_container">


                <?php
                // Suchanfrage zusammenstellen
                $search = '';
                if (!empty($_GET['q'])) {
                    $q = urlencode($_GET['q']);
                    $search = ';WHERE name LIKE \'%' . $q . '%\' OR bezeichnung LIKE \'%' . $q . '%\''; // SQL-Query
                }
                // Alle Produkte laden, bei Suche nur nach Bedingung
                $products = performApiRequest('<key>', 0, 'GET', '*' . $search);
                $cid = '';
                if (isLoggedIn()) {
                    $cid = $_SESSION['customerID'];
                }
                foreach ($products as $prod) {
                    // Einzelnes Produkt ausgeben
                    $bestand = '<span class="badge badge-success">Auf Lager</span></small>';
                    if ($prod->bestand == false) {
                        $bestand = '<span class="badge badge-danger">Nicht auf Lager</span></small>';
                    }
                    $isDisabled = '';
                    if ($prod->bestand == false) {
                        $isDisabled = 'disabled';
                    }
                    $buttonText = 'In den Warenkorb';


                    echo ' <div class="col-md-3" style="margin-bottom: 16px">
                            <div class="card" style="height: 100%">
                            
                                <img src="' . $prod->bild . '" alt="" style="max-height: 200px; max-width: 100%; min-width: 50%; position: absolute; z-index: 10">

                                <div style="z-index: 100; background-color: #ffbbee; width: 100px; border-bottom-right-radius: 16px; box-shadow: 2px 2px 2px rgba(10,10,10,0.2)"><span style="font-size: 16pt; margin: 5px">€' . str_replace('.', ',', formatPrice($prod->preis)) . '</span> 
                                </div>    
                                <div class="card-body" style="position: relative; margin-top: calc(200px - 10px - 16pt - 10px)">
                                    <h3 class="card-title">' . $prod->name . '</h3>
                                        <span class="badge badge-secondary">' . $prod->art . '</span>
                                        ' . $bestand . '
                                    <p class="card-text">' . $prod->bezeichnung . '</p>
                                    <button class="btn" style="background-color: transparent"></button><br/><br/>
                                    <a href="/scripts/addcart.php?cid=' . $cid . '&prod=' . $prod->artikelnummer . '" ' . $isDisabled . ' class="btn btn-default ' . $isDisabled . '" style="color: white; background-color: #ff00cc; bottom: 10px; position: absolute" role="button">' . $buttonText . '</a>
                                </div>
                            </div>
                        </div>';
                }
                ?>
            </div>
        </div>

    </div>
</div>

<!-- Ende der Internetseite -->

<script>

    var productContainer = document.getElementById("product_container");

    // FILTER
    var btnFilterAufLager = document.getElementById("btn_filter_aufLager");
    var btnFilterNichtVerfugbar = document.getElementById("btn_filter_nichtVerfugbar");
    var btnFilterK_Pflanzen = document.getElementById("btn_filter_kPflanzen");
    var btnFilterK_Geback = document.getElementById("btn_filter_kGeback");
    var btnFilterK_Buch = document.getElementById("btn_filter_kBuch");
    var btnFilterK_Gewurz = document.getElementById("btn_filter_kGewurz");
    var btnFilterK_Garten = document.getElementById("btn_filter_kGarten");
    var btnFilterK_Kuche = document.getElementById("btn_filter_kKuche");
    var btnFilterK_Kleidung = document.getElementById("btn_filter_kKleidung");
    var btnFilterK_Tier = document.getElementById("btn_filter_kTier");
    var btnFilterK_Lebensmittel = document.getElementById("btn_filter_kLebensmittel");
    var btnFilterAnwenden = document.getElementById("btn_filter_anwenden");

    var loggedIn = <?php
                if (isLoggedIn()) {
                    echo 'true';
                } else {
                    echo 'false';
                }
                ?>

    var search = "<?php
                if (!empty($_GET['q'])) {
                    $q = urlencode($_GET['q']);
                    echo 'name LIKE \'%' . $q . '%\' OR bezeichnung LIKE \'%' . $q . '%\'';
                } else {
                    echo '';
                }
                ?>"

    function constructFiterQuery() {
        var isAufLager = btnFilterAufLager.selected;
        var isNichtVerfugbar = btnFilterNichtVerfugbar.selected;
        var isPflanzen = btnFilterK_Pflanzen.selected;
        var isGeback = btnFilterK_Geback.selected;
        var isBuch = btnFilterK_Buch.selected;
        var isGewurz = btnFilterK_Gewurz.selected;
        var isGarten = btnFilterK_Garten.selected;
        var isKuche = btnFilterK_Kuche.selected;
        var isKleidung = btnFilterK_Kleidung.selected;
        var isTier = btnFilterK_Tier.selected;
        var isLebensmittel = btnFilterK_Lebensmittel.selected;

        if (!isAufLager && !isNichtVerfugbar && !isPflanzen && !isGeback && !isBuch && !isGewurz && !isGarten && !isKuche && !isKleidung && !isTier && !isLebensmittel) {
            return "";
        }

        var theQuery = " WHERE ";
        var aufLagerSet = false;
        // Auf Lager
        if (isAufLager) {
            theQuery = theQuery + "(`bestand`=1 OR ";
            aufLagerSet = true;
        }
        if (isNichtVerfugbar) {
            if (!aufLagerSet) {
                theQuery = theQuery + "(";
            }
            theQuery = theQuery + "`bestand`=0 OR ";
            aufLagerSet = true;
        }
        if (aufLagerSet) {
            theQuery = theQuery.substr(0, theQuery.length - 4) + ") AND (";
        }
        // Kategorie
        if (isPflanzen) {
            theQuery = theQuery + "`art`='Pflanze' OR ";
        }
        if (isGeback) {
            theQuery = theQuery + "`art`='Gebäck' OR ";
        }
        if (isBuch) {
            theQuery = theQuery + "`art`='Buch' OR ";
        }
        if (isGewurz) {
            theQuery = theQuery + "`art`='Gewürz' OR ";
        }
        if (isGarten) {
            theQuery = theQuery + "`art`='Gartenutensilien' OR ";
        }
        if (isKuche) {
            theQuery = theQuery + "`art`='Küchengeräte' OR ";
        }
        if (isKleidung) {
            theQuery = theQuery + "`art`='Kleidung' OR ";
        }
        if (isTier) {
            theQuery = theQuery + "`art`='Tier' OR ";
        }
        if (isLebensmittel) {
            theQuery = theQuery + "`art`='Lebensmittel' OR ";
        }

        var doEnding = true;
        if (theQuery.endsWith("AND ")) {
            theQuery = theQuery.substr(0, theQuery.length - 5);
        } else if (theQuery.endsWith("AND (")) {
            theQuery = theQuery.substr(0, theQuery.length - 6);
            doEnding = false;
        } else {
            theQuery = theQuery.substr(0, theQuery.length - 4);
        }

        if (aufLagerSet && doEnding) {
            theQuery = theQuery + ")";
        }

        if (search.length > 0) {
            theQuery = theQuery + " AND (" + search + ")";
        }

        return theQuery;

    }

    // onclick-Events zum Auswaehlen der Optionen

    btnFilterAufLager.onclick = function () {
        btnFilterAufLager.selected = !btnFilterAufLager.selected;
        if (btnFilterAufLager.selected) {
            btnFilterAufLager.className += " active";
        } else {
            btnFilterAufLager.className = btnFilterAufLager.className.replace(" active", "");
        }
    };

    btnFilterNichtVerfugbar.onclick = function () {
        btnFilterNichtVerfugbar.selected = !btnFilterNichtVerfugbar.selected;
        if (btnFilterNichtVerfugbar.selected) {
            btnFilterNichtVerfugbar.className += " active";
        } else {
            btnFilterNichtVerfugbar.className = btnFilterNichtVerfugbar.className.replace(" active", "");
        }
    };


    btnFilterK_Pflanzen.onclick = function () {
        btnFilterK_Pflanzen.selected = !btnFilterK_Pflanzen.selected;
        if (btnFilterK_Pflanzen.selected) {
            btnFilterK_Pflanzen.className += " active";
        } else {
            btnFilterK_Pflanzen.className = btnFilterK_Pflanzen.className.replace(" active", "");
        }
    };
    btnFilterK_Geback.onclick = function () {
        btnFilterK_Geback.selected = !btnFilterK_Geback.selected;
        if (btnFilterK_Geback.selected) {
            btnFilterK_Geback.className += " active";
        } else {
            btnFilterK_Geback.className = btnFilterK_Geback.className.replace(" active", "");
        }
    };
    btnFilterK_Buch.onclick = function () {
        btnFilterK_Buch.selected = !btnFilterK_Buch.selected;
        if (btnFilterK_Buch.selected) {
            btnFilterK_Buch.className += " active";
        } else {
            btnFilterK_Buch.className = btnFilterK_Buch.className.replace(" active", "");
        }
    };
    btnFilterK_Gewurz.onclick = function () {
        btnFilterK_Gewurz.selected = !btnFilterK_Gewurz.selected;
        if (btnFilterK_Gewurz.selected) {
            btnFilterK_Gewurz.className += " active";
        } else {
            btnFilterK_Gewurz.className = btnFilterK_Gewurz.className.replace(" active", "");
        }
    };
    btnFilterK_Garten.onclick = function () {
        btnFilterK_Garten.selected = !btnFilterK_Garten.selected;
        if (btnFilterK_Garten.selected) {
            btnFilterK_Garten.className += " active";
        } else {
            btnFilterK_Garten.className = btnFilterK_Garten.className.replace(" active", "");
        }
    };
    btnFilterK_Kuche.onclick = function () {
        btnFilterK_Kuche.selected = !btnFilterK_Kuche.selected;
        if (btnFilterK_Kuche.selected) {
            btnFilterK_Kuche.className += " active";
        } else {
            btnFilterK_Kuche.className = btnFilterK_Kuche.className.replace(" active", "");
        }
    };
    btnFilterK_Kleidung.onclick = function () {
        btnFilterK_Kleidung.selected = !btnFilterK_Kleidung.selected;
        if (btnFilterK_Kleidung.selected) {
            btnFilterK_Kleidung.className += " active";
        } else {
            btnFilterK_Kleidung.className = btnFilterK_Kleidung.className.replace(" active", "");
        }
    };
    btnFilterK_Tier.onclick = function () {
        btnFilterK_Tier.selected = !btnFilterK_Tier.selected;
        if (btnFilterK_Tier.selected) {
            btnFilterK_Tier.className += " active";
        } else {
            btnFilterK_Tier.className = btnFilterK_Tier.className.replace(" active", "");
        }
    };
     btnFilterK_Lebensmittel.onclick = function () {
        btnFilterK_Lebensmittel.selected = !btnFilterK_Lebensmittel.selected;
        if (btnFilterK_Lebensmittel.selected) {
            btnFilterK_Lebensmittel.className += " active";
        } else {
            btnFilterK_Lebensmittel.className = btnFilterK_Lebensmittel.className.replace(" active", "");
        }
    };



    btnFilterAnwenden.onclick = filter;

    function filter() {
        // API-Request: Produkte laden
        performApiRequest("[KEY]", 0, "GET", "*;" + constructFiterQuery(), function () {
            if (this.status === 200 && this.readyState === 4) {
                var insertText = "";
                var json = JSON.parse(this.responseText);


                var txtFilterVon = document.getElementById("txt_filter_von");
                var txtFilterBis = document.getElementById("txt_filter_bis");

                var isVonSet = txtFilterVon.value.replace(" ", "").replace(",", ".").replace(".", "").length !== 0;
                var isBisSet = txtFilterBis.value.replace(" ", "").replace(",", ".").replace(".", "").length !== 0;

                // Preis-Filter erst hier
                var von = 0;
                var bis = 0;
                if (isVonSet) {
                    von = parseInt(txtFilterVon.value.trim().replace(",", "."));
                }
                if (isBisSet) {
                    bis = parseInt(txtFilterBis.value.trim().replace(",", "."));
                }

                for (i = 0; i < json.length; i++) {
                    // Einzelnes Produkt ausgeben
                    var obj = json[i];
                    var bestand = '<span class="badge badge-success">Auf Lager</span></small>';
                    if (obj.bestand == false) {
                        bestand = '<span class="badge badge-danger">Nicht auf Lager</span></small>';
                    }
                    var isDisabled = "";
                    if (obj.bestand == false) {
                        isDisabled = "disabled";
                    }
                    var buttonText = "In den Warenkorb";

                    var insert = true;
                    if (isVonSet) {
                        insert = obj.preis >= von;
                    }
                    if (isBisSet && obj.preis > bis) {
                        if (insert) {
                            insert = obj.preis <= bis;
                        }
                    }
                    if (insert) {
                        insertText += "<div class=\"col-md-3\" style=\"margin-bottom: 16px\"> " +
                                "<div class=\"card\" style=\"height: 100%\">" +
                                "<img src=\"" + obj.bild + "\" alt=\"\" style=\"max-height: 200px; max-width: 100%; min-width: 50%; position: absolute; z-index: 10\">" +
                                "<div style=\"z-index: 100; background-color: #ffbbee; width: 100px; border-bottom-right-radius: 16px; box-shadow: 2px 2px 2px rgba(10,10,10,0.2)\"><span style=\"font-size: 16pt; margin: 5px\">€" + formatPrice(obj.preis).replace(".", ",") + "</span>" +
                                "</div> " +
                                "<div class=\"card-body\" style=\"position: relative; margin-top: calc(200px - 10px - 16pt - 10px)\">" +
                                "<h3 class=\"card-title\">" + obj.name + "</h3>" +
                                "<span class=\"badge badge-secondary\">" + obj.art + " </span> " +
                                bestand +
                                "<p class=\"card-text\">" + obj.bezeichnung + "</p>" +
                                "<button class=\"btn\" style=\"background-color: transparent\"></button><br/><br/>" +
                                "<a href=\"/scripts/addcart.php?cid=<?php
                if (isLoggedIn()) {
                    echo $_SESSION['customerID'];
                }
                ?>&prod=" + obj.artikelnummer + "\"" + isDisabled + " class=\"btn btn-default " + isDisabled + "\" style=\"color: white; background-color: #ff00cc; bottom: 10px; position: absolute\" role=\"button\">" + buttonText + "</a>" +
                                "</div>" +
                                "</div>" +
                                "</div>";
                    }
                }
                productContainer.innerHTML = insertText;
                if (insertText.length === 0) {
                    productContainer.innerHTML = "<div class=\"alert alert-danger\" role=\"alert\">Keine Treffer</div>";
                }
            }
        });
    }

</script>
