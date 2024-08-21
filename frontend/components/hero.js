import React from 'react'

export default function Hero() {
    return (
        <>
            <div className="hero_area">
                <div className="bg-box backgroundImg">
                    <div className='overlay'>
                    </div>
                </div>
                <header className="header_section">
                    <div className="container ">
                        {/* <nav className="navbar navbar-expand-lg custom_nav-container sticky-top">
              <a className="navbar-brand" href="/">
                <span>Foodies Corner</span>
              </a>

              <button className="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
                <span></span>
              </button>

              <div className="collapse navbar-collapse" id="navbarSupportedContent">
                <ul className="navbar-nav mx-auto">
                  <li className="nav-item active">
                    <Link className="nav-link" href="/">Home</Link>
                  </li>
                  <li className="nav-item">
                    <Link className="nav-link" href="#restaurents">Menu</Link>
                  </li>
                  <li className="nav-item">
                    <Link className="nav-link" href="/about">About</Link>
                  </li>
                  <li className="nav-item">
                    <Link className="nav-link" href="/book">Book Table</Link>
                  </li>
                </ul> */}
                        {/* <div className="user_option">
                  <Link href="#" className="user_link">
                    <i className="fa fa-user" aria-hidden="true"></i>
                  </Link>
                  <Link className="cart_link" href="#"> */}
                        {/* SVG code here */}
                        {/* </Link>
                  <form className="form-inline">
                    <button className="btn my-2 my-sm-0 nav_search-btn" type="submit">
                      <i className="fa fa-search" aria-hidden="true"></i>
                    </button>
                  </form>
                  <Link href="#" className="order_online">Order Online</Link>
                </div> */}
                        {/* </div>
            </nav> */}
                    </div>
                </header>

                <section className="slider_section">
                    <div id="customCarousel1" className="carousel slide" data-bs-ride="carousel">
                        <div className="carousel-inner">
                            <div className="carousel-item active">
                                <div className="container">
                                    <div className="row">
                                        <div className="col-md-7 col-lg-6">
                                            <div className="detail-box">
                                                <h1>Fast Food Restaurant</h1>
                                                <p>
                                                    Doloremque, itaque aperiam facilis rerum, commodi, temporibus sapiente ad mollitia laborum quam quisquam esse error unde. Tempora ex doloremque, labore, sunt repellat dolore, iste magni quos nihil ducimus libero ipsam.
                                                </p>
                                                <div className="btn-box">
                                                    <a href="#" className="btn1">Order Now</a>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            {/* Repeat for other carousel items */}
                        </div>
                        {/* <div className="container">
              <ol className="carousel-indicators">
                <li data-bs-target="#customCarousel1" data-bs-slide-to="0" className="active"></li>
                <li data-bs-target="#customCarousel1" data-bs-slide-to="1"></li>
                <li data-bs-target="#customCarousel1" data-bs-slide-to="2"></li>
              </ol>
            </div> */}
                    </div>
                </section>
            </div>
        </>
    )
}
