import axios from "axios";
// import Cookies from "js-cookie";

const LOGIN_BASE_URL = "http://localhost:8080/api/login/"

class LoginService{
    loginUser(loginDetails){
        return axios.post(LOGIN_BASE_URL + "authenticate", loginDetails)
    }

}

export default new LoginService()