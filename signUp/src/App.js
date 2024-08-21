// import logo from './logo.svg';
import 'bootstrap/dist/css/bootstrap.min.css';
import { Route, Routes } from 'react-router-dom';
import './App.css';
import Header from "./components/layout/header";
import Footer from "./components/layout/footer";
import Home from "./components/home";
import Login from "./components/login";

function App() {
  return (
    <>
      <Header />
      <Routes>
        <Route path='/' element={<Home />} />
        <Route path='/register' element={<Login /> } />
      </Routes>
      <Footer />
    </>
  );
}

export default App;
