import {LocationSearch, CheckLastSearched} from "./LocationSearch.js";

$(document).ready(function(){
    /**
     * handles popover event for login button, adds form from loginForm.jsp as content
     */
    $('[data-toggle="loginPopover"]').popover({
        html: true,
        content: function() {
            const template = $(this).data('template-file');

            console.log(template);

            // parse html from login form jsp and return
            return $('<div>').load(template, html => {
                const parser = new DOMParser();
                const doc = parser.parseFromString(html, "text/html");
                return doc.querySelector('form').outerHTML;
            })
        }
    });

    $('html').on('click', function (e) {
        $('.popoverTrigger').each(function () {
            if (!$(this).is(e.target) && $(this).has(e.target).length === 0 && $('.popover').has(e.target).length === 0) {
                $(this).popover('hide');
            }
        });
    });
});

// set submit handler on form
document.querySelector('#locationSearchForm').addEventListener('submit', LocationSearch);

// add event handler to persist last searched zipcode until tab close
document.addEventListener("DOMContentLoaded", CheckLastSearched);
