import React, { useEffect, useState } from 'react';
import axios from "axios";
import '../components/login.css';

const Login = () => {
    const [formData, setFormData] = useState({
        name: '',
        mobile: '',
        email: '',
        password: '',
    });
    const [response, setResponse] = useState(null);
    const [error, setError] = useState(null);

    useEffect(() => {
        const url = "http://localhost:8080/user/insert";
        axios.post(url, { data: formData })
            .then(response => {
                setResponse(response.data);
            })
            .catch(error => {
                setError(error);
            });
    }, []);

    const [errors, setErrors] = useState({});

    const handleChange = (e) => {
        const { name, value } = e.target;
        setFormData({ ...formData, [name]: value });
    };

    const validate = () => {
        const errors = {};
        if (!formData.name) errors.name = 'Name is required';
        if (!formData.mobile || !/^\d{10}$/.test(formData.mobile)) errors.mobile = 'Mobile number must be 10 digits';
        if (!formData.email || !/\S+@\S+\.\S+/.test(formData.email)) errors.email = 'Email is invalid';
        if (!formData.password || formData.password.length < 6) errors.password = 'Password must be at least 6 characters long';
        return errors;
    };

    const handleSubmit = (e) => {
        e.preventDefault();
        const validationErrors = validate();
        if (Object.keys(validationErrors).length === 0) {
            // Handle successful form submission here
            console.log('Form data submitted:', formData);
        } else {
            setErrors(validationErrors);
        }
    };

    return (
        <div id='background' style={{ position: "fixed" }}>
            <div id='regForm'>
                <form onSubmit={handleSubmit} noValidate>
                    <table>
                        <tbody>
                            <tr>
                                <td>Name</td>
                                <td>:</td>
                                <td>
                                    <input
                                        type="text"
                                        name="name"
                                        value={formData.name}
                                        onChange={handleChange}
                                        className='formInput'
                                    />
                                </td>
                            </tr>
                            <tr>
                                <td></td>
                                <td></td>
                                <td className='errMsg' style={{ color: "red" }}>{errors.name && <span className="error">{errors.name}</span>}</td>
                            </tr>
                            <tr>
                                <td>Mobile Number</td>
                                <td>:</td>
                                <td>
                                    <input
                                        type="text"
                                        name="mobile"
                                        value={formData.mobile}
                                        onChange={handleChange}
                                        className='formInput'
                                    />
                                </td>
                            </tr>
                            <tr>
                                <td></td>
                                <td></td>
                                <td>{errors.mobile && <span className="error" style={{ color: "red" }}>{errors.mobile}</span>}</td>
                            </tr>
                            <tr>
                                <td>Email</td>
                                <td>:</td>
                                <td>
                                    <input
                                        type="email"
                                        name="email"
                                        value={formData.email}
                                        onChange={handleChange}
                                        className='formInput'
                                    />
                                </td>
                            </tr>
                            <tr>
                                <td></td>
                                <td></td>
                                <td>{errors.email && <span style={{ color: "red" }} className="error">{errors.email}</span>}</td>
                            </tr>
                            <tr>
                                <td>Password</td>
                                <td>:</td>
                                <td>
                                    <input
                                        type="password"
                                        name="password"
                                        value={formData.password}
                                        onChange={handleChange}
                                        className='formInput'
                                    />
                                </td>
                            </tr>
                            <tr>
                                <td></td>
                                <td></td>
                                <td>{errors.password && <span style={{ color: "red" }} className="error">{errors.password}</span>}</td>
                            </tr>
                        </tbody>
                    </table>
                    <div className="d-flex justify-content-center">
                        <button type="submit" className='btn btn-success'>Register</button>
                    </div>
                </form>
            </div>
        </div>
    );
};

export default Login;
