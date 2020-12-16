import { CheckLastSearched } from "./modules/LocationSearch.js";
import { CreateGenericModal } from "./modules/GenericModal.js";
import { FormValidator } from "./modules/FormValidator.js"

$(document).ready(function(){
    // import FormValidator object
    const formValidator = new FormValidator();
    // set on click event for sign up modal
    const modalTrigger = document.querySelector(".modalTrigger");

    if (modalTrigger) {
        modalTrigger.addEventListener("click", function() {
            CreateGenericModal(this);
            formValidator.SignupValidator();
        });
    }

    formValidator.ZipcodeSearchValidator();
});

// add event handler to persist last searched zipcode until tab close
// this is placed outside ajax function above because it expects to be called from an event
document.addEventListener("DOMContentLoaded", CheckLastSearched);
