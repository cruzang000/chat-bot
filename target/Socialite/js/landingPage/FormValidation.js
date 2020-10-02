/**
 * takes form data for house options and sets in constructor
 * when validateForm is called form data is validated and error messages are either shown or hidden
 * a list of the validation results is returned
 */
export default class FormValidation {
    constructor(formData) {
        this.zipcode = formData.zipcode;
    }

    //stories is an option in storyPrices object
    validateZipcode = zipcode => !isNaN(zipcode) && zipcode > 10000 && zipcode < 99950 ? this.hideError("searchZipcode") :
        this.showError("searchZipcode");

    //calls sub validation methods and returns array of true or false values for each result
     validateForm = () => {
        return [
            this.validateZipcode(this.zipcode),
        ];
    }

    //show matching error for element
    showError = element => {
        document.getElementById(element + "Error").style.visibility = "visible";

        return false;
    }

    //show matching error for element
    hideError = element => {
        document.getElementById(element + "Error").style.visibility = "hidden";

        return true;
    }

}



