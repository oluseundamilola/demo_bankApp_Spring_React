import { useState } from "react";
import "./login.scss";
import { Link, useNavigate } from "react-router-dom";
import { useSignIn } from "react-auth-kit";
import LoginService from "../../services/LoginService";

const Login = () => {
  const signIn = useSignIn()
  const navigate = useNavigate()
  const [textStyleForAccount, setTextStyleForAccount] = useState("input");
  const [textStyleForPassword, setTextStyleForPassword] = useState("input");

  const [loginDetails, setLoginDetails] = useState({
    accountNumber: "",
    password: "",
  });

  const handleChange = (e) => {
    const value = e.target.value;
    setLoginDetails({ ...loginDetails, [e.target.name]: value });
    console.log(loginDetails);
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    console.log(loginDetails);
    if (loginDetails.accountNumber === "") {
      setTextStyleForAccount("error");
    }
    else{
      setTextStyleForAccount("input")
    }
    if (loginDetails.password === "") {
      setTextStyleForPassword("error");
    }
    else{
      setTextStyleForPassword("input");
    }

    if(loginDetails.accountNumber !== "" && loginDetails.password !== ""){
      await LoginService.loginUser(loginDetails)
      .then( (response) => {
        console.log(response.data)
        signIn({
          token: response.data,
          expiresIn: 3600,
          tokenType: "Bearer",
          authState: {email: loginDetails.accountNumber}
        })
        navigate("/dashboard")
      } )
      
    }
  };

  return (
    <div className="loginpage">
      <div className="loginContainer">
        <div className="insideloginBox">
          <h2 className="title">Login</h2>
          <div className="textSide">
            <span className="label">Account Number: </span>
            <input
              type="text"
              className={textStyleForAccount}
              onChange={(e) => handleChange(e)}
              name="accountNumber"
            /> 
            <span className="label">Password: </span>
            <input
              type="text"
              className={textStyleForPassword}
              onChange={(e) => handleChange(e)}
              name="password"
            />
          </div>
          <div className="buttonSide">
            <button className="signIn" onClick={handleSubmit}>
              Login
            </button>
            <Link to="/register">
              <div className="create">Open Account</div>
            </Link>
          </div>
        </div>
      </div>
    </div>
  );
};

export default Login;
