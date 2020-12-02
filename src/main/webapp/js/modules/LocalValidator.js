/**
 * Validation class uses jquery validation to provide various validation methods
 */
export default class LocalValidator {
    constructor() {}

    //calls sub validation methods and returns array of true or false values for each result
     validationRegex = (regex, value) => new RegExp(regex).test(value);

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



