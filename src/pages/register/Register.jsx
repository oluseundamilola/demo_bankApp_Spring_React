import { Link } from "react-router-dom";
import "./register.scss";
import { useState } from "react";
import AccountService from "../../services/AccountService"

const Register = () => {
  const [registerDetails, setRegisterDetails] = useState({
    firstName: "",
    lastName: "",
    email: "",
    phone: "",
    password: "",
  });

  const handleChange = (e) => {
    const value = e.target.value;
    setRegisterDetails({ ...registerDetails, [e.target.name]: value });
    console.log(registerDetails);
  };

  const handleSumit = (e) => {
    e.preventDefault()
    console.log(registerDetails)
    AccountService.createNewAccount(registerDetails)
    .then((response) => {
      console.log(response.data)
    })
    .catch((error) => {
      console.log(error)
    })
  }

  return (
    <div className="registerpage">
      <div className="registerContainer">
        <div className="insideRegisterBox">
          <h2 className="registerTitle">Open an account</h2>
          <div className="textSide">
            <span className="label">First Name: </span>
            <input
              type="text"
              className="registerInput"
              name="firstName"
              onChange={(e) => handleChange(e)}
            />

            <span className="label">Last Name: </span>
            <input
              type="text"
              className="registerInput"
              name="lastName"
              onChange={(e) => handleChange(e)}
            />

            <span className="label">Email: </span>
            <input
              type="text"
              className="registerInput"
              name="email"
              onChange={(e) => handleChange(e)}
            />

            <span className="label">Phone: </span>
            <input
              type="text"
              className="registerInput"
              name="phone"
              onChange={(e) => handleChange(e)}
            />

            <span className="label">Password: </span>
            <input
              type="text"
              className="registerInput"
              name="password"
              onChange={(e) => handleChange(e)}
            />
          </div>
          <div className="buttonSide">
            <button className="createAccount" onClick={handleSumit}>Create Account</button>
            <Link to="/login">
              <div className="loginLink">I have an account, Login</div>
            </Link>
          </div>
        </div>
      </div>
    </div>
  );
};

export default Register;
