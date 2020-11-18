import {LocationSearch} from "./LocationSearch.js";

export class FormValidator {

    SignupValidator() {
        $("form[name='signup-form']").validate({
            // Specify validation rules
            rules: {
                firstName: {
                    required: true
                },
                lastName: {
                    required: true },
                email: {
                    required: true,
                    email: true
                },
                dateOfBirth: {
                    required: true,
                    minlength: 5
                },
                username: {
                    required: true,
                },
                password: {
                    required: true,
                },
                retypePassword: {
                    required: true,
                    equalTo: "#password"
                }
            },
            // Specify validation error messages
            messages: {
                firstname: { required: "Must Enter First Name." },
                lastname: { required: "Must Enter Last Name." },
                email: {
                    required: "Must enter an email address",
                    email: "Invalid Email."
                },
                dateOfBirth: {
                    required: "Must enter a date of birth",
                },
                username: { required: "Must Enter First Name." },
                password: {
                    required: "Must enter password",
                },
                retypePassword: {
                    required: "Must retype password",
                    equalTo: "Passwords do not match."
                }
            },
            // Make sure the form is submitted to the destination defined
            // in the "action" attribute of the form when valid
            submitHandler: function(form) {
                form.submit();
            }
        });
    }


    ZipcodeSearchValidator() {
        $("form[name='location-search-form']").validate({
            // Specify validation rules
            rules: {
                location: {
                    required: true,
                    minlength: 5,
                    min: 500
                }
            },
            //specify error element
            errorElement: "small",
            // Specify validation error messages
            messages: {
                location: {
                    required: "Must Enter a zipcode.",
                    minlength: "Must be a valid 5 digit zipcode",
                    min: "Must be a valid 5 digit zipcode"
                },
            },
            submitHandler: function(form) {
                LocationSearch();
            }
        });
    }
}