import React, { useEffect, useState } from 'react';
import axios from 'axios';

const CoordinatesAroundCurrentLocation = () => {
  const [currentLocation, setCurrentLocation] = useState(null);
  const [coordinatesList, setCoordinatesList] = useState([
    // { latitude: 18.68310073772088, longitude: 73.81970158265351 }, // Example coordinates
    // { latitude: 18.650728695793003, longitude: 73.74979885126332 },
    // { latitude: 40.7128, longitude: -74.0060 }
    // Add more coordinates as needed
  ]); 
  
  const radiusInKm = 1; // Radius in kilometers
  
  useEffect(() => {
    //function to fetch geo location from restaurents database
    const getAllRestaurents = async () => {
      try {
        const response = await axios.get('http://localhost:5296/api/Restaurent'); 
        console.log(response.data);
        response.data.map(item => {
          // console.log("data : ", item);
          const {latitude, longitude} = item;
          setCoordinatesList([...coordinatesList,{latitude:latitude, longitude:longitude}])
          // console.log("Latitude : ",latitude, "longitude : ", longitude);
        })

        // setData(response.data); // Assuming the response contains an array of data
      } catch (error) {
        console.error('Error fetching data: ', error);
      }
    };
    getAllRestaurents();
    // Function to get current location using browser's geolocation API
    const getCurrentLocation = () => {
      if (navigator.geolocation) {
        navigator.geolocation.getCurrentPosition(
          position => {
            const { latitude, longitude } = position.coords;
            setCurrentLocation({ latitude, longitude });
          },
          error => {
            console.error('Error getting current location:', error);
          }
        );
      } else {
        console.error('Geolocation is not supported by this browser.');
      }
    };

    getCurrentLocation();
  }, []);

  // Function to calculate distance using Haversine formula
  const calculateDistance = (lat1, lon1, lat2, lon2) => {
    const R = 6371; // Radius of the Earth in kilometers
    const dLat = (lat2 - lat1) * Math.PI / 180;
    const dLon = (lon2 - lon1) * Math.PI / 180;
    const a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
              Math.cos(lat1 * Math.PI / 180) * Math.cos(lat2 * Math.PI / 180) *
              Math.sin(dLon / 2) * Math.sin(dLon / 2);
    const c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
    const distance = R * c; // Distance in kilometers
    return distance;
  };

  // Filter coordinates within the specified radius from current location
  const filteredCoordinates = coordinatesList.filter(coord => {
    if (!currentLocation) return false; // Skip if current location is not available
    const distance = calculateDistance(
      currentLocation.latitude,
      currentLocation.longitude,
      coord.latitude,
      coord.longitude
    );
    return distance <= radiusInKm;
  });

  return (
    <div>
      <h2>Coordinates Around Current Location</h2>
      {currentLocation && (
        <p>Current Location: {currentLocation.latitude}, {currentLocation.longitude}</p>
      )}
      <h3>Coordinates within {radiusInKm} km:</h3>
      <ul>
        {filteredCoordinates.map((coord, index) => (
          <li key={index}>
            Latitude: {coord.latitude}, Longitude: {coord.longitude}
          </li>
        ))}
      </ul>
    </div>
  );
};

export default CoordinatesAroundCurrentLocation;
