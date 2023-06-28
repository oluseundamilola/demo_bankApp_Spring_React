import { useEffect, useRef, useState } from "react";
import { useNavigate } from "react-router-dom";
import Navbar from "../../components/navbar/Navbar";
import Sidebar from "../../components/sidebar/Sidebar";
import "./sendMoney.scss";
import AccountService from "../../services/AccountService";
import TransactionService from "../../services/TransactionService";
import CheckCircleOutlineIcon from "@mui/icons-material/CheckCircleOutline";
import ErrorIcon from '@mui/icons-material/Error';
import WarningAmberIcon from '@mui/icons-material/WarningAmber';

const SendMoney = () => {
  const navigate = useNavigate();
  const [transferStatus, setTransferStatus] = useState("");
  const [boxDisplay, setBoxDisplay] = useState("none");
  const [beneficiaryAccountNumber, setBeneficiaryAccountNumber] = useState({
    accountNumber: "",
  });

  const [beneficiary, setBeneficiary] = useState({});
  const [beneficiaryStyle, setBeneficiaryStyle] = useState("none");
  const [sendMoneyRequest, setSendMoneyRequest] = useState({
    beneficiaryAccountNumber: "",
    amount: "",
    narration: "",
  });

  const handleChangeBeneficiaryInput = (e) => {
    const value = e.target.value;
    setBeneficiaryAccountNumber({
      accountNumber: value,
    });
  };

  const previousAccountNumber = useRef("");

  useEffect(() => {
    if (
      beneficiaryAccountNumber.accountNumber &&
      beneficiaryAccountNumber.accountNumber.length === 10 &&
      beneficiaryAccountNumber.accountNumber !== previousAccountNumber.current
    ) {
      findBeneficiaryFunction();
      previousAccountNumber.current = beneficiaryAccountNumber.accountNumber;
      setBeneficiaryStyle("");
    }
  }, [beneficiaryAccountNumber, beneficiary]);

  const findBeneficiaryFunction = async () => {
    console.log(beneficiaryAccountNumber);
    await AccountService.getBeneficiaryAccount(beneficiaryAccountNumber)
      .then((response) => {
        console.log(response.data);
        setBeneficiary(response.data);
      })
      .catch((error) => {
        console.log(error);
      });
  };

  const handleChangeOfAmountandNarrationInput = (e) => {
    const value = e.target.value;
    setSendMoneyRequest({ ...sendMoneyRequest, [e.target.id]: value });
    console.log(sendMoneyRequest);
  };

  const handleMultipleChanges = (e) => {
    handleChangeBeneficiaryInput(e); // Call first function
    handleChangeOfAmountandNarrationInput(e); // Call second function
  };

  const tranferButton = (e) => {
    e.preventDefault();
    console.log(sendMoneyRequest);
    TransactionService.sendMoney(sendMoneyRequest)
      .then((response) => {
        console.log(response.data);
        setTransferStatus(response.data);
        setBoxDisplay("");
      })
      .catch((error) => {
        console.log(error);
      });
  };

  const okay = (e) => {
    e.preventDefault();
    setBoxDisplay("none");
    navigate("/dashboard");
  };

  return (
    <div className="sendMoneyPage">
      <Navbar type="afterLogin" />
      <div className="pageLayout">
        <Sidebar />
        <div className="pageContainer">
          {transferStatus === "success" && (
            <div style={{ display: `${boxDisplay}` }} className="messageBox">
              <div className="statusPostion">
                <h2 className="status">Successful</h2>
                <div className="iconContainer">
                  <CheckCircleOutlineIcon className="icon" />
                </div>
              </div>
              <div className="messageSide">
                <p className="message">
                You have successfully transferred NGN { sendMoneyRequest.amount } to
                  { beneficiary.beneficiary }
                </p>
                <button onClick={okay} className="ok">
                  OK
                </button>
              </div>
            </div>
          )}

          {transferStatus === "funds" && (
            <div style={{ display: `${boxDisplay}` }} className="messageBox">
              <div className="statusPostion">
                <h2 className="status">Insufficient Funds</h2>
                <div className="iconContainer">
                  <ErrorIcon className="icon-error" />
                </div>
              </div>
              <div className="messageSide">
                <p className="message">
                  You have successfully transferred NGN 180,000 to Olanike
                  Oluseun
                </p>
                <button onClick={okay} className="ok">
                  OK
                </button>
              </div>
            </div>
          )}

          {transferStatus === "block" && (
            <div style={{ display: `${boxDisplay}` }} className="messageBox">
              <div className="statusPostion">
                <h2 className="status">Successful</h2>
                <div className="iconContainer">
                  <WarningAmberIcon className="icon-warning" />
                </div>
              </div>
              <div className="messageSide">
                <p className="message">
                  You have successfully transferred NGN {sendMoneyRequest.amount} to 
                  { beneficiary.beneficiary }
                </p>
                <button onClick={okay} className="ok">
                  OK
                </button>
              </div>
            </div>
          )}
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
                  <input
                    type="text"
                    className="accNumber"
                    name="accountNumber"
                    id="beneficiaryAccountNumber"
                    onChange={(e) => handleMultipleChanges(e)}
                  />
                </div>
                <span
                  style={{ display: `${beneficiaryStyle}` }}
                  className="beneficiaryName"
                >
                  {beneficiary.beneficiary}
                </span>
              </div>
            </div>

            <div className="under">
              <div className="amountInput">
                <div className="naira">Enter Amount NGN</div>
                <input
                  type="text"
                  className="amount"
                  id="amount"
                  onChange={handleChangeOfAmountandNarrationInput}
                />
              </div>
              <div className="narrationSide">
                <div className="narration">Narration</div>
                <input
                  type="text"
                  className="narrationInput"
                  id="narration"
                  onChange={handleChangeOfAmountandNarrationInput}
                />
              </div>
              <button className="sendButton" onClick={tranferButton}>
                Transfer
              </button>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default SendMoney;
