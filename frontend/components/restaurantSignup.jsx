'use client';

import { useState } from 'react';
import axios from 'axios';
import styles from "../public/static/css/restaurantSignUp.module.css";
import { useRouter } from 'next/navigation';

const RestaurantSignup = () => {
    const router = useRouter();
    const [formData, setFormData] = useState({
        name: '',
        phone: '',
        email: '',
        password: '',
        address: '',
        district: '',
        pincode: '',
        openingHours: '',
        latitude: 0,
        longitude: 0,
        rating: 0,
        image_url: "",
        state: '',
    });
    const [file, setFile] = useState(null);
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState(null);
    const [success, setSuccess] = useState(null);

    const handleChange = (e) => {
        const { name, value, type, files } = e.target;
        if (type === 'file') {
            setFormData({ ...formData, [name]: "/static/images/" + files[0].name }); // Handle file input
            setFile(e.target.files[0]);
        } else {
            setFormData({ ...formData, [name]: value });
        }
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        setLoading(true);
        setError(null);
        setSuccess(null);

        const formDataToSend = new FormData();
        for (const key in formData) {
            if (formData[key]) {
                formDataToSend.append(key, formData[key]);
            }
        }
        console.log("Form data to send ", formDataToSend);

        const FileFormData = new FormData();
        FileFormData.append('file', file);
        console.log("File form data : ", FileFormData);
        try {
            const response = await axios.post('http://localhost:8080/rest/add', formDataToSend, {
                headers: { "Content-Type": "application/json" }
            });
            try {
                const ImgRes = await axios.post('/api/upload', FileFormData, {
                    headers: {
                        'Content-Type': 'multipart/form-data'
                    }
                });

                console.log("File REsp :",ImgRes.data.message);
                setError('');
            } catch (error) {
                setError(`Error uploading file: ${error.response?.data?.error || error.message}`);
                setSuccess('');
            }
            setSuccess('Signup successful!');
            console.log('Response:', response.data);
            setFormData({
                name: '',
                email: '',
                phone: '',
                password: '',
                address: '',
                openingHours: '',
                latitude: '',
                longitude: '',
                rating: '',
                district: '',
                image_url: "",
                pincode: '',
                state: '',
            });
            router.push("/restaurantLogin");
        } catch (err) {
            setError(err.response?.data?.message || 'An error occurred');
            console.error('Error:', err);
        } finally {
            setLoading(false);
        }
    };

    return (
        <>
            <h2 className='mt-5 ms-3'>Register your Restaurant</h2>
            <hr />
            <div className={`d-flex justify-content-center align-items-cente mb-4 ${styles.background}`}>
                <div className={`card p-4 shadow-sm ${styles.regForm}`} style={{ width: '500px' }}>
                    <h3 className="card-title text-center mb-4 text-light">Register</h3>
                    <form onSubmit={handleSubmit}>
                        <div className="mb-3">
                            <input
                                type="text"
                                id="name"
                                name="name"
                                placeholder='Enter Restaurant Name'
                                className={`form-control ${styles.formInput}`}
                                value={formData.name}
                                onChange={handleChange}
                                required
                            />
                        </div>
                        <div className="mb-3">
                            <input
                                type="text"
                                id="phone"
                                name="phone"
                                placeholder='Enter Mobile Number'
                                className={`form-control ${styles.formInput}`}
                                value={formData.phone}
                                onChange={handleChange}
                                required
                            />
                        </div>
                        <div className="mb-3">
                            <input
                                type="email"
                                id="email"
                                name="email"
                                placeholder='Enter Email'
                                className={`form-control ${styles.formInput}`}
                                value={formData.email}
                                onChange={handleChange}
                                required
                            />
                        </div>
                        <div className="mb-3">
                            <input
                                type="password"
                                id="password"
                                name="password"
                                placeholder='Enter Password'
                                className={`form-control ${styles.formInput}`}
                                value={formData.password}
                                onChange={handleChange}
                                required
                            />
                        </div>
                        <div className="mb-3">
                            <input
                                type="text"
                                id="address"
                                name="address"
                                placeholder='Enter Address'
                                className={`form-control ${styles.formInput}`}
                                value={formData.address}
                                onChange={handleChange}
                                required
                            />
                        </div>
                        <div className="mb-3">
                            <input
                                type="text"
                                id="district"
                                name="district"
                                placeholder='Enter District'
                                className={`form-control ${styles.formInput}`}
                                value={formData.district}
                                onChange={handleChange}
                            />
                        </div>
                        <div className="mb-3">
                            <input
                                type="text"
                                id="pincode"
                                name="pincode"
                                placeholder='Enter Pincode'
                                className={`form-control ${styles.formInput}`}
                                value={formData.pincode}
                                onChange={handleChange}
                            />
                        </div>
                        <div className="mb-3">
                            <input
                                type="text"
                                id="openingHours"
                                name="openingHours"
                                placeholder='Enter Opening Hours'
                                className={`form-control ${styles.formInput}`}
                                value={formData.openingHours}
                                onChange={handleChange}
                                required
                            />
                        </div>
                        <div className="mb-3">
                            <input
                                type="hidden"
                                id="latitude"
                                name="latitude"
                                placeholder='Enter Latitude'
                                className={`form-control ${styles.formInput}`}
                                value={formData.latitude}
                                onChange={handleChange}
                            />
                        </div>
                        <div className="mb-3">
                            <input
                                type="hidden"
                                id="longitude"
                                name="longitude"
                                placeholder='Enter Longitude'
                                className={`form-control ${styles.formInput}`}
                                value={formData.longitude}
                                onChange={handleChange}
                            />
                        </div>
                        <div className="mb-3">
                            <input
                                type="hidden"
                                id="rating"
                                name="rating"
                                placeholder='Enter Rating (0-5)'
                                className={`form-control ${styles.formInput}`}
                                value={formData.rating}
                                onChange={handleChange}
                            />
                        </div>

                        <div className="mb-3">
                            <input
                                type="file"
                                id="image"
                                name="image_url"
                                className={`form-control ${styles.formInput}`}
                                onChange={handleChange}
                            />
                        </div>
                        <div className="mb-3">
                            <input
                                type="text"
                                id="state"
                                name="state"
                                placeholder='Enter State'
                                className={`form-control ${styles.formInput}`}
                                value={formData.state}
                                onChange={handleChange}
                            />
                        </div>
                        {error && <div className="alert alert-danger">{error}</div>}
                        {success && <div className="alert alert-success">{success}</div>}
                        <button type="submit" className={`btn btn-primary w-100 ${styles.button}`} disabled={loading}>
                            {loading ? 'Signing Up...' : 'Sign Up'}
                        </button>
                    </form>
                </div>
            </div>
        </>
    );
};

export default RestaurantSignup;
