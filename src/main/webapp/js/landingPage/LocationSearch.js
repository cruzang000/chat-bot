import FormValidation from "./FormValidation.js";

/**
 * module controls logic for location search form submission, validates and sends data to java back end api
 */
export const LocationSearch = () => {

    event.preventDefault();

    const searchForm = document.getElementById("locationSearchForm").elements;
    const formData = { zipcode:  searchForm.namedItem("location").value };

    //returns true if errors were found else false if validation passed
    const validationResults = new FormValidation(formData).validateForm();

    //if valid results create house object passing in house form data
    if (!(validationResults).includes(false)) {
        const zipcode = formData.zipcode;
        const geoNamesUrl = "/socialite/api/geoLocations/zipcode/?" + $.param({zipcode: zipcode});

        //send request for geo location and pass in callback function to send data to
        sendGetRequest(geoNamesUrl, requestLocations);
    }

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
    .catch((error) => { alert("Error sending request, try again." + " url: " + url)});
}

/**
 * gets called after geo request takes data and checks if valid longitude and latitude
 * than sends api request for locations passing in parseYelpResponse to handle response
 * @param data
 */
const requestLocations = data => {
    //sets to geoObject[latitude,longitude] if lat and lng are available else null
    const geoObject = data[0].map.lat && data[0].map.lng ? data[0].map : null;

    //create url with params from geoObject
    const yelpLocationsApi = "/socialite/api/yelpLocations/byGeo/?" + $.param(geoObject);
    
    sendGetRequest(yelpLocationsApi, parseYelpResponse);
}

/**
 * gets called after location request to handle results
 * @param data
 */
const parseYelpResponse = data => {

    if (Object.keys(data).length > 0) {
        const locationCardTemplate = document.querySelector("#template-card");
        const results = document.createElement("div");
        results.id = "results";
        results.className = "card-deck"

        // loop through locations and create card elements
        for (const location of data) {
            const locationCard = locationCardTemplate.content.cloneNode(true);
            //location card detail
            const locationImage = locationCard.querySelector("img");
            locationImage.src = location.map["imgURL"];
            locationImage.alt = location.map.name + "-yelp-image";
            const locationName = locationCard.querySelector("h3");
            locationName.textContent = location.map.name;
            const addressList = locationCard.querySelector("ul");
            addressList.childNodes[0].textContent = location.map["address-street"];
            addressList.childNodes[1].textContent = location.map["address-city-state"];
            addressList.childNodes[2].textContent = location.map["phone"];
            const locationRating = locationCard.querySelector(".card-footer");
            locationRating.textContent = location.map.rating;

            // add card element to results
            results.appendChild(locationCard);
        }

        // show results
        const resultsContainer = document.querySelector("#resultsContainer");
        resultsContainer.replaceChild(results,resultsContainer.querySelector("#results"));
    }
}

document.querySelector('#locationSearchForm').addEventListener('submit', LocationSearch);
