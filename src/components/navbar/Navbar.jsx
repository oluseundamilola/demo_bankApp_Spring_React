import { useEffect, useState } from "react";
import "./navbar.scss";
import AccountCircleIcon from '@mui/icons-material/AccountCircle';
import AccountService from "../../services/AccountService";

const Navbar = ({ type }) => {
  const [userData, setUserData] = useState({})

  useEffect(() => {
    const loadUserData = () => {
      AccountService.getAccountInfo()
      .then((response) => {
        console.log(response.data)
        setUserData(response.data)
      })
      .catch((error) => {
        console.log(error)
      })
    }
    loadUserData()
  },[])


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
          <div className="user">
            <AccountCircleIcon className="icon" />
            <span className="login">{userData.firstName} {userData.lastName}</span>
            </div>
        )}
      </div>
    </div>
  );
};

export default Navbar;
