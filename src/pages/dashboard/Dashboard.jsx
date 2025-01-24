import Sidebar from "../../components/sidebar/Sidebar";
import Navbar from "../../components/navbar/Navbar";
import AddCircleIcon from "@mui/icons-material/AddCircle";
import PaidIcon from "@mui/icons-material/Paid";
import CreditCardIcon from "@mui/icons-material/CreditCard";
import LanguageIcon from "@mui/icons-material/Language";
import AccountBalanceIcon from "@mui/icons-material/AccountBalance";
import AccountBalanceWalletIcon from "@mui/icons-material/AccountBalanceWallet";
import "./dashboard.scss";
import { useEffect, useState } from "react";
import AccountService from "../../services/AccountService";
import { Link } from "react-router-dom";

const Dashboard = () => {
  const [userData, setUserData] = useState({});


  function addCommas(number) {
    // Convert the number to a string
    var numberString = String(number);

    // Split the number into integer and decimal parts (if any)
    var parts = numberString.split(".");

    // Get the integer part
    var integerPart = parts[0];

    // Add commas every three digits in the integer part
    var formattedInteger = integerPart.replace(/\B(?=(\d{3})+(?!\d))/g, ",");

    // Combine the formatted integer part with the decimal part (if any)
    var formattedNumber = parts.length > 1 ? formattedInteger + "." + parts[1] : formattedInteger;

    return formattedNumber;
  }

  useEffect(() => {
    const loadUserData = () => {
      AccountService.getAccountInfo()
        .then((response) => {
          setUserData(response.data);
        })
        .catch((error) => {
          console.log(error);
        });
    };
    loadUserData();
  }, []);

  return (
    <div className="dashboardPage">
      <Link to="/chat">
        <div className="botBody">
          <div className="botContainer">
          </div>
          <span className="botMessage">chat with me</span>
        </div>
      </Link>
      <Navbar type="afterLogin" />
      <div className="pageLayout">
        <Sidebar />
        <div className="dashboardContainer">
          <div className="dashboardTop">
            <div className="top">
              <div className="accountDetails">
                <span>
                  {userData.firstName} {userData.lastName}
                </span>
                <div className="amount">
                  <h2>NGN</h2>
                  <p>{addCommas(userData.balance)}</p>
                </div>
              </div>
            </div>
            <div className="buttom">
              <div className="buttomleft">
                <div className="accoutType">Savings Account:</div>
                <p className="accountNum">{userData.accountNumber}</p>
              </div>
              <div className="buttomRight">
                <AddCircleIcon className="icon" />
              </div>
            </div>
          </div>
          <div className="dashboardButtom">
            <Link to="/send_money">
              <div className="box" style={{ backgroundColor: "#4ac95f" }}>
                <CreditCardIcon className="icon" style={{ color: "#266831" }} />
                <div className="text" style={{ color: "#225c2c" }}>
                  Send Money
                </div>
              </div>
            </Link>
            <div className="box" style={{ backgroundColor: "#abfcff" }}>
              <PaidIcon className="icon" style={{ color: "#61bfc2" }} />
              <div className="text" style={{ color: "#428688" }}>
                Receive Money
              </div>
            </div>
            <div className="box" style={{ backgroundColor: "#f5a8ff" }}>
              <LanguageIcon className="icon" style={{ color: "#a04cac" }} />
              <div className="text" style={{ color: "#5c2b63" }}>
                Web Top-up
              </div>
            </div>
            <div className="box" style={{ backgroundColor: "#ffcfcf" }}>
              <AccountBalanceWalletIcon
                className="icon"
                style={{ color: "#be8484" }}
              />
              <div className="text" style={{ color: "#815a5a" }}>
                Payment
              </div>
            </div>
            <div className="box" style={{ backgroundColor: "#9db7ff" }}>
              <AccountBalanceIcon
                className="icon"
                style={{ color: "#425eaa" }}
              />
              <div className="text" style={{ color: "#2c4075" }}>
                Loans
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default Dashboard;
