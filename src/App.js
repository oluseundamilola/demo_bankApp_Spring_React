import { BrowserRouter, Routes, Route} from "react-router-dom"
import Home from "./pages/home/Home"
import Login from "./pages/login/Login"
import Register from "./pages/register/Register"
import Dashboard from "./pages/dashboard/Dashboard"
import SendMoney from "./pages/send_money/SendMoney"
import Transactions from "./pages/transactions/Transactions"
import Chat from "./pages/chat/Chat"



function App() {
  return (
    <div className="App">
      <BrowserRouter>
      <Routes>
        <Route path="/">
          <Route index element ={<Home/>} />
        </Route>

        <Route path="/login">
          <Route index element ={<Login/>} />
        </Route>

        <Route path="/register">
          <Route index element ={<Register/>} />
        </Route>

        <Route path="/dashboard">
          <Route index element ={<Dashboard/>} />
        </Route>

        <Route path="/send_money">
          <Route index element ={<SendMoney/>} />
        </Route>

        <Route path="/transactions">
          <Route index element={<Transactions />} />
        </Route>

        <Route path="/chat">
          <Route index element={<Chat />} />
        </Route>

      </Routes>
      </BrowserRouter>
    </div>
  );
}

export default App;
