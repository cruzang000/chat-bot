
export class UserPlanRequest {
    constructor(userID) {
        this.userID = userID;
        this.baseUrl = `/socialite/api/userPlans`;
    }

    /**
     * loads in generic modal from dom
     */
    getCurrentUserPlanIds = async () => {
        return this.userGetRequest(this.baseUrl + "/userPlanLocationIds");
    }

    userGetRequest = async (url) => {
        return await fetch(url, {
            headers: {
                'Content-type': 'application/json; charset=UTF-8',
                'Accept': 'application/json'
            }
        }).then(response => response.json())
    }

    userPostRequest = async url => {
        // let response = "";
        //
        // console.log(url + "|?" + locationID)
        // if (new LocalValidator().validationRegex("\d+", locationID)) {
        //
        //     return await fetch(url, {
        //         headers: {'Content-type': 'application/json; charset=UTF-8', 'Accept': 'application/json'},
        //         method: 'post',
        //         body: JSON.stringify({locationId: locationID})
        //     })
        //     .then(response => response.json()) // parse response as json
        //     .then(data => data)
        //     .catch((error) => {
        //         console.log(error);
        //         alert("Error sending request, try again." + " url: " + url)
        //     });
        //
        // }
        //
        // return response;
    }
}


