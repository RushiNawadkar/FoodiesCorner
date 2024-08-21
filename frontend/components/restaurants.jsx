"use client"
import React, { useEffect, useState } from 'react';
import Image from 'next/image';
import axios from 'axios';
import Link from 'next/link';

export default function Restaurants(prop) {
    const [menuItems, setMenuItems] = useState([]);

    useEffect(() => {
        const getAllMenus = async () => {
            const res = await axios.get("http://localhost:8080/rest/all");
            console.log("Resp : ", res.data,prop.comp);
            if (prop.comp === "Home") {
                if (res.data.length <= 2) {
                    setMenuItems(res.data);
                }
                else {
                    setMenuItems(res.data.slice(0, 2));
                }
            } else if (prop.comp === "showall") {
                setMenuItems(res.data);
            }

        }
        getAllMenus();
    }, []);

    return (
        <>
            {/* Food section */}
            <section id="restaurents" className="food_section layout_padding-bottom">
                <div className="container">
                    <div className="heading_container heading_center">
                        <h2 style={{ marginTop: "60px" }}>Our Restaurants</h2>
                    </div>

                    {/* <ul className="filters_menu">
                        <li className="active" data-filter="*">All</li>
                        <li data-filter=".burger">Burger</li>
                        <li data-filter=".pizza">Pizza</li>
                        <li data-filter=".pasta">Pasta</li>
                        <li data-filter=".fries">Fries</li>
                    </ul> */}

                    <div className="filters-content">
                        <div className="row grid">
                            {menuItems.map(item => (
                                <div key={item.id} className={`col-sm-6 col-lg-4 all ${item.category}`}>
                                    <div className="box" >
                                        <div style={{ height: "400px" }}>
                                            <Link  href={`/restaurantDetails?image=${encodeURIComponent(item.image_url)}
                                                &title=${encodeURIComponent(item.name)}
                                                &price=${encodeURIComponent(item.price)}
                                                &restid=${encodeURIComponent(item.restaurantId
                                                    )}`
                                            
                                            }>
                                                <div className="img-box" style={{padding:"0px"}}>
                                                    <Image
                                                        src={item.image_url}
                                                        alt={item.name}
                                                        width={500}
                                                        height={300}
                                                        style={{
                                                            maxWidth: "100%",
                                                            maxHeight: "100%"
                                                        }}
                                                    />
                                                </div>
                                            </Link >
                                            <div className="detail-box">
                                                <Link className='text-decoration-none text-light fs-4 fw-bolder' 
                                                href={`/restaurantDetails?image=${encodeURIComponent(item.image_url)}
                                                &title=${encodeURIComponent(item.name)}
                                                &price=${encodeURIComponent(item.price)}
                                                &restid=${encodeURIComponent(item.restaurantId
                                                    )}`
                                            
                                            } >{item.name}</Link>
                                                <p><strong>Address :</strong>{item.address}</p>
                                                <p><strong>Opening Hours :</strong>{item.openingHours}</p>
                                                <p><strong>Rating :</strong>{item.rating}</p>
                                                <div className="options">
                                                    <h6></h6>
                                                    {/* <Link href={`/cart?image=${encodeURIComponent(item.imageurl)}&title=${encodeURIComponent(item.name)}&price=${encodeURIComponent(item.price)}`}>
                                                        <svg version="1.1" id="Capa_1" viewBox="0 0 456.029 456.029" style={{ enableBackground: 'new 0 0 456.029 456.029' }}>
                                                            <g>
                                                                <path d="M345.6,338.862c-29.184,0-53.248,23.552-53.248,53.248c0,29.184,23.552,53.248,53.248,53.248 c29.184,0,53.248-23.552,53.248-53.248C398.336,362.926,374.784,338.862,345.6,338.862z" />
                                                            </g>
                                                            <g>
                                                                <path d="M439.296,84.91c-1.024,0-2.56-0.512-4.096-0.512H112.64l-5.12-34.304C104.448,27.566,84.992,10.67,61.952,10.67H20.48 C9.216,10.67,0,19.886,0,31.15c0,11.264,9.216,20.48,20.48,20.48h41.472c2.56,0,4.608,2.048,5.12,4.608l31.744,216.064 c4.096,27.136,27.648,47.616,55.296,47.616h212.992c26.624,0,49.664-18.944,55.296-45.056l33.28-166.4 C457.728,97.71,450.56,86.958,439.296,84.91z" />
                                                            </g>
                                                            <g>
                                                                <path d="M215.04,389.55c-1.024-28.16-24.576-50.688-52.736-50.688c-29.696,1.536-52.224,26.112-51.2,55.296 c1.024,28.16,24.064,50.688,52.224,50.688h1.024C193.536,443.31,216.576,418.734,215.04,389.55z" />
                                                            </g>
                                                        </svg>
                                                    </Link> */}
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            ))}
                        </div>
                    </div>

                </div>
                <div className="btn-box">
                    <a href="/showAll">View More</a>
                </div>
            </section>
        </>
    );
}
