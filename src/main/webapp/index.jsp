<html>

<%-- include head --%>
<%@ include file = "/jsp/head.jsp" %>

<body>

<noscript>enable javascript to get full functionality!</noscript>

<%-- include nav bar --%>
<%@ include file = "/jsp/header.jsp" %>

<%@ include file="/jsp/alert.jsp" %>


<script>
    let user = "${sessionScope.getOrDefault("user", null)}";
</script>
<main class="container-fluid p-0">
    <div class="col-12 p-0 pr-md-3 mt-5">
        <%-- search bar --%>
        <section class="bg-navy text-white d-flex flex-column shadow rounded p-3">
            <section class="d-flex flex-wrap align-items-end text-center">
                <h2 class="col-12 col-sm-5 col-md-4 col-lg-3 col-xl-2 px-0 text-sm-left">Plans Tonight?</h2>
                <div class="col-12 col-sm-6 px-0">
                    <div class="d-flex flex-row justify-content-center justify-content-sm-start text-left my-1">

                        <div class="searchIcon mx-2">
                            <i class="fas fa-map-marker-alt fa-2x"></i>
                        </div>

                        <div class="searchIcon mx-2">
                            <i class="fas fa-car fa-2x"></i>
                        </div>

                        <div class="searchIcon mx-2">
                            <i class="fas fa-cocktail fa-2x"></i>
                        </div>

                        <div class="searchIcon mx-2">
                            <i class="fas fa-users fa-2x"></i>
                        </div>
                    </div>
                    <h3 class="col-12 searchSubText px-0 text-sm-left">See whats going on and who's there!</h3>
                </div>
            </section>
            <form id="location-search-form" class="d-flex flex-wrap align-items-center m-0" name="location-search-form">

                <div class="col-10 px-1">
                    <input type="text" aria-label="zipcode" name="location" placeholder="WHERE YOU AT: enter zipcode"
                           class="border border-white rounded white-placeholder col-12 text-center text-white transparent">
                </div>

                <button type="submit" id="locationSearch" class="btn bg-dark-navy btn-outline-light col-2">Go</button>
            </form>
        </section>
    </div>

    <div class="col-12 p-3 px-0">
        <div id="resultsContainer" class="hidden d-flex flex-column bg-dark rounded m-0 text-white p-3 opacity-medium">
            <h4 class="text-center bg-black py-2 rounded shadow-lg mb-3">Search in <span id="searchedLocation"></span></h4>
            <div id="results" class="d-flex flex-wrap justify-content-around card-columns"></div>
            <%-- template used by js to build location cards --%>
            <template id="template-card">
                <div class="card bg-navy text-white rounded border border-light shadow d-flex col-12 col-lg-4 col-xl-3">
                    <div class="card-body d-flex flex-wrap pb-1 px-0">
                        <figure class="col-4 m-0 d-flex flex-column align-items-center">
                            <img src="" alt="card-image" class="locationSearchImage img-fluid img-thumbnail">
                            <ul class="star-rating d-flex flex-row list-unstyled mt-auto align-self-end py-2 my-0"></ul>
                        </figure>
                        <section class="d-flex flex-column col-8">
                            <h3></h3>
                            <ul class="list-unstyled py-2 addressList">
                                <li></li>
                                <li></li>
                                <li class="py-2"></li>
                            </ul>
                        </section>
                    </div>
                    <div class="card-footer p-0 m-0">
                        <section class="d-flex justify-content-end">
                            <button class="noDisplay"></button>
                        </section>
                    </div>
                </div>
            </template>
        </div>
    </div>
</main>

<script src="js/LandingPage.js" type="module"></script>
</body>

</html>
