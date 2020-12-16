
export class User {
    constructor() {
        this.baseUrl = `/socialite/api/user`;
    }

    /**
     * loads in generic modal from dom
     */
    updateUserInfo = async (userInfo) => {
        return this.userPostRequest(this.baseUrl + "/update", userInfo);
    }

    userGetRequest = async (url) => {
        return await fetch(url, {
            headers: {
                'Content-type': 'application/json; charset=UTF-8',
                'Accept': 'application/json'
            }
        }).then(response => response.json())
    }

    userPostRequest = async (url, data) => {
        return await fetch(url, {
            headers: {
                'method': 'post',
                'body': JSON.stringify(data),
                'Content-type': 'application/json; charset=UTF-8',
                'Accept': 'application/json'
            }
        }).then(response => response.json())
    }
}


