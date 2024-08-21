"use client"
import Link from 'next/link';

export default function Navbar({ children }) {
  return (
    <>
      <nav className="navbar navbar-expand-lg transparent-navbar sticky-top">
        <div className="container-fluid">
          <Link id='logo' className="navbar-brand" href="/">Foodies Corner</Link>
          <button className="navbar-toggler bg-light icon-link-hover" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNavAltMarkup" aria-controls="navbarNavAltMarkup" aria-expanded="false" aria-label="Toggle navigation">
            <span className="navbar-toggler-icon"></span>
          </button>
          <div className="collapse navbar-collapse justify-content-center" id="navbarNavAltMarkup">
            <div className="navbar-nav">
              <Link className="nav-link text-light mx-4 active" aria-current="page" href="/">Home</Link>
              <Link className="nav-link text-light mx-4" href="/showAll">Restaurants</Link>
              <Link className="nav-link text-light mx-4" href="/aboutus">About</Link>
              {/* <Link className="nav-link text-light disbled" aria-disabled="true">Disabled</Link> */}
            </div>
            <div >
              <Link className="nav-link text-danger mx-4" aria-current="page" href="/login">Login</Link>
            </div>
          </div>
        </div>
      </nav>
      {/* 
      <div className="sample" ></div> */}


    </>
  );
}
