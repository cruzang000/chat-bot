
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

    /**
     * remove user plan
     * @param locationId
     * @returns {Promise<*>}
     */
    removeUserPlan = async (locationId) => this.userGetRequest(`${this.baseUrl}/manageUserPlan?${$.param({
        locationId: locationId, action: "remove"})}`);


    /**
     * add user plan
     * @returns {Promise<*>}
     * @param locationId
     */
    addUserPlan = async (locationId) => this.userGetRequest(`${this.baseUrl}/manageUserPlan?${$.param({
        locationId: locationId, action: "add"})}`);

    userGetRequest = async (url) => {
        return await fetch(url, {
            headers: {
                'Content-type': 'application/json; charset=UTF-8',
                'Accept': 'application/json'
            }
        }).then(response => response.json())
    }
}


