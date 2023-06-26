import { Link } from "react-router-dom";
import Navbar from "../../components/navbar/Navbar";
import "./home.scss";

const Home = () => {
  return (
    <div className="home">
      <Navbar type="beforeLogin" />
      <div className="homeContainer">
        <div className="left">
          <div className="leftContent">
            <h1 className="title">Banking made Easy</h1>
            <span className="text">
              Lorem ipsum dolor sit amet consectetur, adipisicing elit. Impedit,
              doloremque omnis. Nobis, saepe? Quo, unde nostrum esse facilis
              labore cupiditate aliquid optio deserunt quam iste rerum fugit
              magni odio amet.
            </span>
            <div className="buttonContainer">
              <Link to="/login">
                <button className="homeButton">Get Started</button>
              </Link>
            </div>
          </div>
        </div>
        <div className="right"></div>
      </div>
    </div>
  );
};

export default Home;
