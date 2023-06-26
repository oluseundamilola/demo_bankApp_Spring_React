import "./sidebar.scss"
import CreditCardIcon from '@mui/icons-material/CreditCard';
import PaidIcon from '@mui/icons-material/Paid';
import DevicesIcon from '@mui/icons-material/Devices';
import ShoppingBasketIcon from '@mui/icons-material/ShoppingBasket';
import AccountBalanceWalletIcon from '@mui/icons-material/AccountBalanceWallet';
import AccountBalanceIcon from '@mui/icons-material/AccountBalance';
import WidgetsIcon from '@mui/icons-material/Widgets';
import MovieCreationIcon from '@mui/icons-material/MovieCreation';
import FlightIcon from '@mui/icons-material/Flight';
import SportsVolleyballIcon from '@mui/icons-material/SportsVolleyball';
import DiamondIcon from '@mui/icons-material/Diamond';
import ExitToAppIcon from '@mui/icons-material/ExitToApp';


const Sidebar = () => {
  return (
    <div className='sideBar'>
      <div className="sideBarContainerTop">
        <div className="itemup">
          <ul>
          <p className="sideTitle">BANKING</p>
          <li>
            <CreditCardIcon className="icon" />
            <span>Send Money</span>
          </li>
          <li>
            <PaidIcon className="icon"/>
            <span>Receive Money</span>
          </li>
          <li>
            <DevicesIcon className="icon"/>
            <span>Web Top-up</span>
          </li>
          <li>
            <AccountBalanceWalletIcon className="icon"/>
            <span>Payments</span>
          </li>
          <li>
            <AccountBalanceIcon className="icon"/>
            <span>Loans</span>
          </li>
          <li>
            <WidgetsIcon className="icon"/>
            <span>Others</span>
          </li>
          <p className="sideTitle">LIFESTYLE</p>
          <li>
            <MovieCreationIcon className="icon"/>
            <span>Movies</span>
          </li>
          <li>
            <FlightIcon className="icon"/>
            <span>Flight Payment</span>
          </li>
          <li>
            <SportsVolleyballIcon className="icon"/>
            <span>Sports & Gamming</span>
          </li>
          <li>
            <ShoppingBasketIcon className="icon"/>
            <span>Food</span>
          </li>
          <li>
            <DiamondIcon className="icon"/>
            <span>Premium Lifestyle</span>
          </li>
          </ul>
        </div>
      </div>
      <div className="sideBarContainerUnder">
        <div className="signoutBox">
          <div className="iconCircle">
          <ExitToAppIcon className="icon" />
          </div>
          <span className="signOutText">SignOut</span>
        </div>
      </div>
    </div>
  )
}

export default Sidebar
