"use client";
import Image from 'next/image';
import { useEffect, useState } from 'react';
import axios from 'axios';
import styles from '../../public/static/css/restrauntDetails.module.css';
import Link from 'next/link';
import { useSearchParams } from 'next/navigation';

export default function RestaurantDetails() {
    const searchParams = useSearchParams();
    const [menuList, setMenuList] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    useEffect(() => {
        const id = searchParams.get('restid');
        const image = searchParams.get('image');
        const title = searchParams.get('title');
        const price = searchParams.get('price');
        console.log("search params : ", id, image, title, price);

        async function fetchMenuData() {
            try {
                const response = await axios.get(`http://localhost:8080/rest/${id}/menus`);
                console.log("Response Menu : ", response.data);
                setMenuList(response.data);
            } catch (error) {
                console.error('Error fetching menu data:', error);
                setError('Failed to fetch menu data.');
            } finally {
                setLoading(false);
            }
        }

        fetchMenuData();
    }, [searchParams]); // Depend on searchParams to re-run effect when params change

    if (loading) return <p>Loading...</p>;
    if (error) return <p>{error}</p>;

    // Extract the image URL and title from searchParams
    const image = searchParams.get('image');
    const searchTitle = searchParams.get('title'); // Extracted title from URL params

    return (
        <section>
            <div className={styles.imageContainer}>
                <Image
                    src={image}
                    alt="Restaurant Image"
                    layout="responsive"
                    width={100} // Adjust as needed
                    height={100} // Adjust as needed
                    className={styles.itemImage}
                />
            </div>

            <div className={styles.menuList}>
                {menuList.map((item, index) => (
                    <div key={index} className={styles.restrauntDetails}>
                        {/* <h1>Restaurant id : {id}</h1> */}
                        <div className={styles.card}>
                            <div className={styles.imgBox}>
                                <Image
                                    src={item.imageurl}
                                    alt={item.title}
                                    width={200} // Adjust as needed
                                    height={200} // Adjust as needed
                                    className={styles.itemImage}
                                />
                            </div>
                            <div className={`card-body ${styles.descColor}`}>
                                <h5 className={`card-title ${styles.title}`}>
                                    {searchTitle || item.title} {/* Use searchTitle if available */}
                                </h5>
                                <strong>Price: {item.price}</strong>
                                <p className="card-text">{item.description}</p>
                                <Link
                                    href={`/cart?image=${encodeURIComponent(item.imageurl)}
                                    &restId=${encodeURIComponent(searchParams.get('restid'))}
                                    &title=${encodeURIComponent(item.name)}
                                    &price=${encodeURIComponent(item.price)}`}
                                    className="btn btn-primary"
                                >Add To Cart</Link>
                            </div>
                        </div>
                    </div>
                ))}
            </div>
        </section>
    );
}
