import React from 'react'
import { Link } from 'react-router-dom'
import "./header.css"

function header() {
    return (
        <header>
            <nav id='nav' className="navbar navbar-expand-lg" style={{backgroundColor:"rgba(0,0,0,0.7"}}>
                <div className="container-fluid ">
                    <Link id='brandName'  to="#">Yummy Bites</Link>
                    <button className="navbar-toggler mt-0" style={{backgroundColor:"whitesmoke "}} type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
                        <span className="navbar-toggler-icon"></span>
                    </button>
                    <div className="collapse navbar-collapse" id="navbarSupportedContent">
                        <ul className="navbar-nav mx-auto mb-2 mb-lg-0">
                            <li className="nav-item mx-2 ">
                                <Link className="nav-Link active text-light text-decoration-none" to="/">Home</Link>
                            </li>
                            <li className="nav-item mx-2">
                                <Link className="nav-Link text-light text-decoration-none" to="/register">Register</Link>
                            </li>
                            <li className="nav-item dropdown mx-2">
                                <Link className="nav-Link text-light dropdown-toggle text-decoration-none" to="#" data-bs-toggle="dropdown">
                                    Dropdown
                                </Link>
                                <ul className="dropdown-menu ">
                                    <li><Link className="dropdown-item" to="#">Action</Link></li>
                                    <li><Link className="dropdown-item" to="#">Another action</Link></li>
                                    <li><hr className="dropdown-divider" /></li>
                                    <li><Link className="dropdown-item" to="#">Something else here</Link></li>
                                </ul>
                            </li>
                            <li className="nav-item">
                                <Link className="nav-Link text-decoration-none text-light disabled " >Disabled</Link>
                            </li>
                        </ul>
                        <form className="d-flex" role="search">
                            <input className="form-control me-2" type="search" placeholder="Search"  />
                                <button className="btn btn-success mt-0" type="submit">Search</button>
                        </form>
                    </div>
                </div>
            </nav>
        </header>
    )
}

export default header