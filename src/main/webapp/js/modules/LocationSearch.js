
/**
 * module controls logic for location search form submission, validates and sends data to java back end api
 * @returns {boolean}
 * @constructor
 * @param zipcode
 */
export const LocationSearch = (zipcode = 0) => {
    event.preventDefault();

    // get search form from dom
    if (zipcode === 0) {
        zipcode = document.getElementById("location-search-form").elements.namedItem("location").value;
    }

    // call request locations
    requestLocations(zipcode);

    return false;
};

/**
 * sends request using url, param passed
 * @param url
 * @param callback
 */
const sendGetRequest = (url, callback) => {
    fetch(url, {headers: {'Content-type': 'application/json; charset=UTF-8', 'Accept': 'application/json'}})
    .then(response => response.json()) // parse response as json
    .then(data => callback(data))// pass data to call back function
    .catch((error) => {
        console.log(error);
        alert("Error sending request, try again." + " url: " + url)
    });
}

/**
 * takes zipcode and sends get request for geo object passing in callback function
 * @param zipcode
 */
const requestLocations = zipcode => {
    // update storage property for last searched zipcode
    appData.lastZipcodeSearched = zipcode;
    appStorageObject.updateSessionStorage(appData);

    sendGetRequest("/socialite/api/geoLocations/zipcode?" + $.param({zipcode: zipcode}), requestFromYelp);
}

/**
 * gets called after geo request takes data and checks if valid longitude and latitude
 * than sends api request for locations passing in parseYelpResponse to handle response
 * @param data
 */
const requestFromYelp = data => {
    //sets to geoObject[latitude,longitude] if lat and lng are available else null
    const geoObject = data[0].map.lat && data[0].map.lng ? data[0].map : null;

    //set search title from object city state and zipcode as string
    setSearchTitle([geoObject.city,geoObject.state,geoObject.zipcode].join(" "));

    //create url with params from geoObject
    const yelpLocationsApi = "/socialite/api/yelpLocations/byGeo/?" + $.param({lat: geoObject.lat, lng: geoObject.lng});
    
    sendGetRequest(yelpLocationsApi, parseYelpResponse);
}

/**
 * takes title and replaces value of search title in dom
 * @param title
 * @returns {Element}
 */
const setSearchTitle = title => document.querySelector("#searchedLocation").textContent = title;


/**
 * gets called after location request to handle results
 * @param data
 */
const parseYelpResponse = data => {

    if (Object.keys(data).length > 0) {
        const results = document.querySelector("#results").cloneNode(true);

        // loop through locations and create card elements
        for (const location of data) { results.appendChild(buildLocationCard(location)); }

        // show results
        const resultsContainer = document.querySelector("#resultsContainer");
        resultsContainer.replaceChild(results, resultsContainer.querySelector("#results"));

        resultsContainer.style.visibility = "visible";
    }
}

/**
 * grabs template and builds location card from location passed in then returns
 * @param location
 * @returns {Node}
 */
const buildLocationCard = location => {
    const locationCard = document.querySelector("#template-card").content.cloneNode(true);

    //location card detail
    const locationImage = locationCard.querySelector("img");
    locationImage.src = location.map["imgURL"];
    locationImage.alt = location.map.name + "-yelp-image";

    const locationName = locationCard.querySelector("h4");
    locationName.textContent = location.map.name;

    const addressList = locationCard.querySelector(".addressList");
    addressList.childNodes[0].textContent = location.map["address-street"];
    addressList.childNodes[1].textContent = location.map["address-city-state"];
    addressList.childNodes[2].textContent = location.map["phone"];

    const locationRating = locationCard.querySelector(".star-rating");

    // loop through rating and output star
    for (let x = location.map.rating; x > 0; x--) {
        let starContainer = document.createElement("li");
        let starIcon = document.createElement("i");

        // set star icon to either full or half depending if half or full number
        starIcon.className = "fas fa-star" + (x - 1 >= .5 ? "" : "-half");

        // add to container
        starContainer.appendChild(starIcon);
        locationRating.appendChild(starContainer);
    }

    return locationCard;
}

/**
 * checks if zipcode is set in appdata and persists on page load
 * @returns {boolean|null}
 */
export const CheckLastSearched = () => {
    if (appData.lastZipcodeSearched !== null) { LocationSearch(appData.lastZipcodeSearched); }
}
