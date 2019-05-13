<div class="container-fluid w-100">
    <div class="row himalaya-bg">
        <div class="col-md-4 f-wind" style="color: white; font-size: 32pt">
            <img src="/media/himalaya.png" height="80px" style="margin: 5px; filter: brightness(1.6)"/>
        </div>
        <div class="col-md-5">
            <?php
            // Ggf. Fehlermeldung anzeigen
            $color = 'danger';
            $text = '<b>Login Fehlgeschlagen:</b> Ungültige Anmeldedaten';
            if ($_GET['t'] == '130') {
                $text = '<b>Registrieren Fehlgeschlagen:</b> Ungültige Anmeldedaten';
            }
            if ($_GET['err'] == '2') {
                $color = 'success';
                if ($_GET['t'] == '120') {
                    $text = '<b>Login Erfolgreich</b> ';
                } else if ($_GET['t'] == '130') {
                    $text = '<b>Registrieren Erfolgreich</b> ';
                }
            }
            if (!empty($_GET['err'])) {
                echo ' <div class="alert alert-' . $color . ' alert-dismissible shadow" role="alert">
                 <button type="button" class="close" data-dismiss="alert" aria-label="Schließen"><span aria-hidden="true">&times;</span></button>
                 ' . $text . '</div>';
            }
            ?>
        </div>
        <div class="col-md-3">
            <form class="form-inline" style="float: right; margin-top: 25px" action="index.php" method="GET">
                <div class="form-group">
                    <label class="sr-only" for="search_query">Suchbegriff</label>
                    <div class="input-group">
                        <input type="text" class="form-control" id="search_query" name="q" value="<?php echo $_GET['q'];?>" placeholder="Suchbegriff">
                        <div class="input-group-addon" style=" vertical-align: central; background-color: #3333ff "><button type="submit" style="background: transparent; border: none"><span class="glyphicon glyphicon-search" style="color: #ffffff "aria-hidden="true"></span></button></div>
                    </div>
                </div>
            </form>
        </div>
    </div>
    <div class="row">
        <div class="" style="background-color: black; width: 100%; height: 3px"></div>
    </div>
</div>

<nav class="navbar navbar-default">
    <div class="container-fluid">
        <!-- Titel und Schalter werden für eine bessere mobile Ansicht zusammengefasst -->
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
                <span class="sr-only">Navigation ein-/ausblenden</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="btn btn-default bg-pink" href="?p=home" style="font-size: 20pt; margin-top: 6px; color: #ffffff" type="button">Startseite</a>
        </div>

        <!-- Alle Navigationslinks, Formulare und anderer Inhalt werden hier zusammengefasst und können dann ein- und ausgeblendet werden -->
        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
            <ul class="nav navbar-nav">
                <li class="dropdown" style="margin-left: 25px">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" style="font-size: 20pt" aria-expanded="false"><span class="glyphicon glyphicon-option-vertical" aria-hidden="true"></span>Produkte <span class="caret"></span></a>
                    <ul class="dropdown-menu">
                        <li><a href="?p=home">Alle Produkte</a></li>
                        <li role="separator" class="divider"></li>
                        <li><a href="#">Bestseller</a></li>
                        <li role="separator" class="divider"></li>
                        <li><a href="#">Hit der Woche</a></li>
                    </ul>
                </li>
                <li class="dropdown" style="margin-left: 25px">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" style="font-size: 20pt" aria-expanded="false"><span class="glyphicon glyphicon-tags" aria-hidden="true"></span>  Angebote <span class="caret"></span></a>
                    <ul class="dropdown-menu">
                        <li><a href="#">Tiefpreisschlager</a></li>
                    </ul>
                </li>
            </ul>

            <ul class="nav navbar-nav navbar-right">
                <li><a href="?p=cart"><span class="glyphicon glyphicon-shopping-cart" style="font-size: 20pt<?php
                        if ($page == 'cart') {
                            echo '; color: #ff00cc';
                        }
                        ?>" aria-hidden="true"></span></a></li>

                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" style="font-size: 20pt" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false"> <span class="glyphicon glyphicon-user" aria-hidden="true"></span>
                    </a>
                    <ul class="dropdown-menu">
                        <?php
                        if (!isLoggedIn()) {
                            // HTML fuer Login anzeigen
                            echo '<li class="dropdown-header" style="font-size: 12pt">Log in</li>
                        <li><a>
                                <form class="form" action="/scripts/login.php" method="POST">
                                    <div class="form-group">
                                        <div class="input-group">
                                            <input type="email" class="form-control" id="email" name="email" placeholder="E-Mail">
                                            <br/><br/>
                                            <input type="password" class="form-control" id="pw" name="pw" placeholder="Passwort">
                                        </div>
                                    </div>
                                    <center><button class="btn btn-default btn-sm" type="submit">Anmelden</button></center>
                                </form>
                            </a></li>
                        <li role="separator" class="divider"></li>
                        <li><a data-toggle="modal" data-target="#modal_resetpw">Passwort vergessen?</a></li>
                        <li><a data-toggle="modal" data-target="#modal_login">Registrieren</a></li>';
                        } else {
                            // HTML zum Abmelden anzeigen
                            echo '<center>
                            <li class="dropdown-header" style="font-size: 12pt">' . user_getName($_SESSION['customerID'], $key) . '</li>
                            <li>' . user_getEMail($_SESSION['customerID'], $key) . '</li>
                            <li role="separator" class="divider"></li>
                            <a class="btn btn-danger btn-sm" href="/scripts/logout.php">Abmelden</a>
                        </center>';
                        }
                        ?>

                    </ul>
                </li>

                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" style="font-size: 20pt" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">Hilfe <span class="glyphicon glyphicon-question-sign" aria-hidden="true"></span></a>
                    <ul class="dropdown-menu">
                        <li><a href="?p=legal">Kontakt</a></li>
                        <li><a href="?p=faq">Häufige Fragen</a></li>
                        <li><a data-toggle="modal" data-target="#modal_repair">Automatische Reperatur</a></li>
                        <li role="separator" class="divider"></li>
                        <li><a href="?p=legal">Impressum</a></li>
                    </ul>
                </li>
            </ul>
        </div><!-- /.navbar-collapse -->
    </div><!-- /.container-fluid -->
</nav>