import axios from "axios";
import Cookies from "js-cookie";

const ACCOUNT_BASE_URL = "http://localhost:8080/api/account/"

class AccountService{
    createNewAccount(accountInfo){
        return axios.post(ACCOUNT_BASE_URL + "add_account", accountInfo)
    }
    getAccountInfo(){
        const tokenString = Cookies.get('_auth')
        return axios.get(ACCOUNT_BASE_URL + "accountDetails",
        {
            headers: { "Authorization" : `Bearer ${tokenString}` }
        }
        )
    }
}

export default new AccountService()