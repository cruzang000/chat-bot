<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">

    <!--  boostrap 4  -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>

    <link rel="stylesheet" href="css/styles.css">

    <title>Document</title>
</head>
<body>
<noscript>enable javascript to get full functionality!</noscript>

<div>
    <form id="locationSearchForm">
        <label>zipcode:
            <input type="text" name="location">
        </label>

        <small id="searchZipcodeError" class="error text-danger">Must enter a valid 5 digit zip code!</small>

        <button type="submit" id="locationSearch">search</button>
    </form>
</div>

<script src="js/landingPage/LocationSearch.js" type="module"></script>
</body>

</html>
