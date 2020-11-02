<html>

<%-- include head --%>
<%@ include file = "/jsp/head.jsp" %>

<body class="bg-blue-light">

    <noscript>enable javascript to get full functionality!</noscript>

    <header>
        <%-- include nav bar --%>
        <%@ include file = "/jsp/navbar.jsp" %>
    </header>

    <main class="container-fluid p-0">
        <div class="col-12 p-0 pr-md-3 mt-5">
            <%-- search bar --%>
            <section class="bg-navy text-white d-flex flex-column shadow rounded px-3 py-1">
                <section class="d-flex flex-wrap align-items-end text-center">
                    <h2 class="col-12">Plans Tonight?</h2>
                    <div class="col-12">
                        <div class="d-flex flex-row justify-content-center my-1">
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
                        <h3 class="col-12 searchSubText">See whats going on and who's there!</h3>
                    </div>
                </section>
                <form id="locationSearchForm" class="d-flex flex-wrap align-items-center m-0">

                    <div class="col-10 px-1">
                        <input type="text" aria-label="zipcode" name="location" placeholder="WHERE YOU AT: enter zipcode"
                               class="border border-white rounded white-placeholder col-12 text-center text-white transparent">
                    </div>

                    <button type="submit" id="locationSearch" class="btn bg-dark-navy btn-outline-light col-2">Go</button>

                    <small id="searchZipcodeError" class="error text-danger text-center col-12 hidden">Must enter a valid 5 digit zip code!</small>
                </form>
            </section>
        </div>

        <div class="col-12 pt-3 px-0">
            <div id="resultsContainer" class="hidden d-flex flex-column bg-dark rounded m-0 text-white p-3 opacity-medium">
                <h4 class="text-center">Search in <span id="searchedLocation"></span></h4>
                <div id="results" class="d-flex flex-wrap justify-content-around card-deck"></div>
                <%-- template used by js to build location cards --%>
                <template id="template-card">
                    <div class="col-12 col-sm-5 col-lg-4 col-xl-3">
                        <div class="card bg-navy text-white rounded border border-light shadow">
                            <div class="card-body d-flex flex-wrap pb-1">
                                <figure class="col-4 m-0 d-flex flex-column align-items-center">
                                    <img src="" alt="card-image" class="locationSearchImage">
                                    <ul class="star-rating d-flex flex-row list-unstyled mt-auto align-self-end"></ul>
                                </figure>
                                <section class="d-flex flex-column col-8">
                                    <h4></h4>
                                    <ul class="list-unstyled py-2 addressList">
                                        <li></li>
                                        <li></li>
                                        <li class="py-2"></li>
                                    </ul>
                                </section>
                            </div>
                        </div>
                    </div>
                </template>
            </div>
        </div>
    </main>

    <script src="js/landingPage/LandingPage.js" type="module"></script>
</body>

</html>
