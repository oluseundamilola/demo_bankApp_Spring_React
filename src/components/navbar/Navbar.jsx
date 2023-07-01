import { useEffect, useState } from "react";
import "./navbar.scss";
import AccountCircleIcon from "@mui/icons-material/AccountCircle";
import AccountService from "../../services/AccountService";
import NotificationsIcon from "@mui/icons-material/Notifications";
import { Link } from "react-router-dom";

const Navbar = ({ type }) => {
  const [userData, setUserData] = useState({});

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

  const handleNotification = (e) => {
    e.preventDefault()
    AccountService.clearNotification()
    .then((response) => {
      console.log(response.data)
    })
    .catch((error) => {
      console.log(error)
    })
  }

  return (
    <div className="navbar">
      <div className="logoContainer">
        <h2 className="logo">MaxBank.</h2>
      </div>
      <div className="wordsContainer">
        {type === "beforeLogin" && (
          <>
            <span className="login">Login</span>
            <span className="account">Create Account</span>
          </>
        )}
        {type === "afterLogin" && (
          <div className="userSide">
            <div className="bell" onClick={handleNotification}>
            <Link to="/transactions">
              <NotificationsIcon className="bellIcon"/>
              {
                userData.notification > 0 && (
                  <div className="circle">{userData.notification}</div>
                )
              }
              </Link>
            </div>
            <div className="user">
              <AccountCircleIcon className="icon" />
              <span className="login">
                {userData.firstName} {userData.lastName}
              </span>
            </div>
          </div>
        )}
      </div>
    </div>
  );
};

export default Navbar;
