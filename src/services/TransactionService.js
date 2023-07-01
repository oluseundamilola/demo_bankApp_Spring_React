import axios from "axios";
import Cookies from "js-cookie";

const Transaction_BASE_URL = "http://localhost:8080/api/transaction/";

class TransactionService {
  getSessionInfo() {
    const tokenString = Cookies.get("_auth");
    return axios.post(
      Transaction_BASE_URL + "check_session",
      {},
      {
        headers: { Authorization: `Bearer ${tokenString}` },
        "Content-Type": "application/json",
      }
    );
  }

  sendMoney(sendMoneyRequest) {
    const tokenString = Cookies.get("_auth");
    return axios.post(Transaction_BASE_URL + "send_money", sendMoneyRequest, {
      headers: { Authorization: `Bearer ${tokenString}` },
      "Content-Type": "application/json",
    });
  }

  getUserTransactions() {
    const tokenString = Cookies.get("_auth");
    return axios.get(
      Transaction_BASE_URL + "transactions",
      {
        headers: { Authorization: `Bearer ${tokenString}` },
        "Content-Type": "application/json",
      }
    );
  }
}
export default new TransactionService();
