import { FormValidator } from "./modules/FormValidator.js"

$(document).ready(function(){
    // import FormValidator object
    const formValidator = new FormValidator();

    formValidator.SignupValidator();
});