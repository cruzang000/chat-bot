/**
 * Validation class uses jquery validation to provide various validation methods
 */
export default class LocalValidator {
    constructor() {}

    //stories is an option in storyPrices object
    validateZipcode = (zipcode, errorElement) => {
        !isNaN(zipcode) && zipcode > 10000 && zipcode < 99950 ? this.hideError("searchZipcode") :
            this.showError(errorElement);
    }

    //calls sub validation methods and returns array of true or false values for each result
     validateForm = () => {
        return [
            this.validateZipcode(this.zipcode),
        ];
    }

    //show matching error for element
    showError = element => {
        document.getElementById(element).style.visibility = "visible";

        return false;
    }

    //show matching error for element
    hideError = element => {
        document.getElementById(element).style.visibility = "hidden";

        return true;
    }

}



