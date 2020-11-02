class LocalStorage {
    constructor(mainUser) {
        this.key = mainUser;
        this.appStorage = sessionStorage.getItem(this.key) ? JSON.parse(sessionStorage.getItem(this.key)) : {
            lastZipcodeSearched: null
            //add additional default properties/arrays/objects
        }
    }

    updateSessionStorage = newData => {
        this.appStorage = newData;
        sessionStorage.setItem(this.key, JSON.stringify(newData));
    };

    getStorage = () => this.appStorage;
}

const appStorageObject = new LocalStorage("socialite");

const appData = appStorageObject.getStorage();