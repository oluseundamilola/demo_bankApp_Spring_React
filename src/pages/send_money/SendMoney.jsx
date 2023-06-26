import Navbar from "../../components/navbar/Navbar";
import Sidebar from "../../components/sidebar/Sidebar";
import "./sendMoney.scss";

const SendMoney = () => {
  return (
    <div className="sendMoneyPage">
      <Navbar type="afterLogin" />
      <div className="pageLayout">
        <Sidebar />
        <div className="pageContainer">
          <div className="sendMoneyBox">
            <div className="sendTop">
              <div className="fromGrayox">
                <p>Transfer From:</p>
              </div>
              <div className="userInfo">
                <p className="detailName">OLUWADAMILOLA OLUSEUN</p>
                <p className="detailAcc">Saving Account: 1222380928</p>
              </div>
            </div>
            <div className="sendButtom">
              <div className="transferTo">
                <p>Transfer To:</p>
              </div>
              <div className="beneficiaryDetailsBox">
                <div className="inputSide">
                  <span className="inputLabel">Account Number</span>
                  <input type="text" className="accNumber" />
                </div>
                <span className="beneficiaryName">OLUSEUN OLANIKE</span>
              </div>
            </div>

            <div className="under">
              <div className="amountInput">
                <div className="naira">Enter Amount NGN</div>
                <input type="text" className="amount"/>
              </div>
              <div className="narrationSide">
              <div className="narration">Narration</div>
              <input type="text" className="narrationInput" />
              </div>
              <button className="sendButton">Transfer</button>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default SendMoney;
