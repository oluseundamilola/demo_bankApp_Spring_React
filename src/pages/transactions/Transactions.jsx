import { useEffect, useState } from "react";
import Navbar from "../../components/navbar/Navbar";
import "./transaction.scss";
import TransactionService from "../../services/TransactionService";

const Transactions = () => {
    const [transactionsData, setTransactionData] = useState([])
    const [amountClassName, setAmountClassName] = useState("")

    useEffect(() => {
        const loadUserTransactions = () => {
            TransactionService.getUserTransactions()
            .then((response) => {
                setTransactionData(response.data)
            })
            .catch((error) => {
                console.log(error)
            })
        }
        loadUserTransactions()
    },[])

  return (
    <div className="transactionPage">
      <Navbar type="afterLogin" />
      <div className="tPagelayout">
        {/* <Sidebar /> */}
        <div className="tPageContainer">
          <h2 className="topic">Transaction History</h2>
          {transactionsData.map( (transactions) => (
              <div className="transactions">
              <div className="boxTop">
                <div className="dateSide">{transactions.date} | {transactions.time}</div>
                <div className={transactions.type}>NGN ({transactions.amount})</div>
              </div>
              <div className="boxMiddle">
                <div className="leftSide">
                  <div className="status">{transactions.status}</div>
                  <div className="detail">{transactions.details}</div>
                </div>
                <div className="rightSide">
                  <span className={transactions.type}>{transactions.type}</span>
                </div>
              </div>
              <div className="boxDown">
                  <div className="narration">
                  Reference:
                  {transactions.transactionRef}
                  </div>
              </div>
            </div>
          ) )}
        </div>
      </div>
    </div>
  );
};

export default Transactions;
