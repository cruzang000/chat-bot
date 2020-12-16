import {UserPlanRequest} from "./UserPlans.js";

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
        alert("Error sending request, try again." + " url: " + error)
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

const planAction = () => {
    const buttonClicked = event.currentTarget;

    if (buttonClicked && buttonClicked.dataset.locationId) {
        const userPlanRequest = new UserPlanRequest();
        const buttonElement = document.querySelector(`#${buttonClicked.id.toString()}`);
        let promise = null;

        switch (buttonClicked.dataset.action) {
            case "add":
                promise = userPlanRequest.addUserPlan(buttonClicked.dataset.locationId).then(
                    data => {
                        if (data.map["wasAdded"]) {
                            buttonElement.className = "planAction btn btn-danger mb-3";
                            buttonElement.dataset.action = "remove";
                            buttonElement.textContent = "remove plan";
                        }
                    }
                );
                break;
            case "remove":
                promise = userPlanRequest.removeUserPlan(buttonClicked.dataset.locationId).then(
                    data => {
                        if (data.map["wasRemoved"]) {
                            buttonElement.className = "planAction btn btn-success mb-3";
                            buttonElement.dataset.action = "add";
                            buttonElement.textContent = "add as plan";
                        }
                    }
                );
                break;
        }
    }
}

/**
 * gets called after location request to handle results
 * @param data
 */
const parseYelpResponse = data => {

    if (Object.keys(data).length > 0) {
        appData.lastSearchedPlans = data;

        const results = document.querySelector("#results");
        results.innerHTML = "";

        const currentUserPlans = user != null ? new UserPlanRequest().getCurrentUserPlanIds() : null;

        // loop through locations and create card elements
        for (const location of data) {
            results.appendChild(buildLocationCard(location, currentUserPlans));
        }

        document.querySelector("#resultsContainer").style.visibility = "visible";
    }
}

/**
 * grabs template and builds location card from location passed in then returns
 * @param location
 * @param currentPlans
 * @returns {Node}
 */
const buildLocationCard = (location, currentPlans) => {
    const locationCard = document.querySelector("#template-card").content.cloneNode(true);

    //location card detail
    const locationImage = locationCard.querySelector("img");
    locationImage.src = location.map["imgURL"];
    locationImage.alt = location.map.name + "-yelp-image";

    const locationName = locationCard.querySelector("h3");
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

    // check if button should be added by checking user variable
    if (currentPlans && user) {
        const button = locationCard.querySelector("button");
        button.className = "planAction mb-3 btn";
        button.style.display = "block";
        button.onclick = () => { planAction() }
        button.style.display = "initial";
        button.dataset.locationId = location.map.locationId;
        button.id = `id_${location.map.locationId}`;

        // check if location id in currentPlans and output appropriate button
        currentPlans.then(currentPlans =>  {
            if (!currentPlans.includes(location.map.locationId)) {
                button.textContent = "add as plan";
                button.className += " btn-success";
                button.dataset.action = "add";

            } else {
                button.textContent = "remove plan";
                button.className += " btn-danger";
                button.dataset.action = "remove";
            }
        });
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
