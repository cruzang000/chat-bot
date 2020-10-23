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
        requestLocations(formData.zipcode);
    }

    return false;
};

const requestLocations = (zipcode) => {
    const url = "/socialite/api/geoLocations"; //+ zipcode;

    fetch(url, {
        headers: {'Content-type': 'application/json; charset=UTF-8', 'Accept': 'application/json'}
    }).then((response) => {
        if (response.ok) {
            console.log(response);
            console.log(response.text());
            console.log(response.json());
        }
    }).catch((error) => {
        alert("Something went wrong! Try again.")
    });
}

document.querySelector('#locationSearchForm').addEventListener('submit', LocationSearch);
