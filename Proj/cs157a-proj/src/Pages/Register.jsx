import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom'; // Import useNavigate for navigation
import api from '../api'; // Ensure this is correctly set up for API requests

const Register = () => {
  const [name, setName] = useState('');
  const [role, setRole] = useState('USER'); // Default role is USER
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [message, setMessage] = useState('');
  const [userData, setUserData] = useState(null); // Store user data locally in the component

  const navigate = useNavigate(); // Initialize useNavigate hook

  const handleSubmit = async (e) => {
    e.preventDefault();
  
    try {
      // Post the registration data to the backend
      const response = await api.post(`/api/members/register/user`, {
        name,
        role,
        username,
        password,
      });
  
      // Log response data
      console.log("Response from API:", response);
  
      if (response && response.data) {
        // Assuming backend returns an object with user details
        const newUserData = response.data;
        setMessage(`Registration successful!`);
        setUserData(newUserData); // Save user data in local state

        // Optionally, save the user data to localStorage for persistence
        localStorage.setItem('userData', JSON.stringify(newUserData));
      } else {
        setMessage('Registration failed. Please try again.');
      }
    } catch (error) {
      console.error('Error during registration:', error);
  
      // Log the error details
      if (error.response) {
        console.error('Response error data:', error.response.data);
      } else if (error.request) {
        console.error('Request error data:', error.request);
      } else {
        console.error('Unexpected error:', error.message);
      }
  
      setMessage('Registration failed. Please try again.');
    }
  };

  const handleLoginRedirect = () => {
    navigate('/login'); // Navigate to the login page
  };

  return (
    <div className="min-h-screen bg-blue-50 flex items-center justify-center">
      <div className="bg-white p-6 rounded-lg shadow-lg w-full max-w-md">
        <h2 className="text-2xl font-semibold text-blue-600 mb-6 text-center">Register User</h2>
        <form onSubmit={handleSubmit}>
          <div className="mb-4">
            <label htmlFor="name" className="block text-blue-600">Name</label>
            <input
              id="name"
              type="text"
              value={name}
              onChange={(e) => setName(e.target.value)}
              className="w-full p-3 border border-gray-300 rounded-md"
              required
            />
          </div>
          <div className="mb-4">
            <label htmlFor="username" className="block text-blue-600">Username</label>
            <input
              id="username"
              type="text"
              value={username}
              onChange={(e) => setUsername(e.target.value)}
              className="w-full p-3 border border-gray-300 rounded-md"
              required
            />
          </div>
          <div className="mb-4">
            <label htmlFor="password" className="block text-blue-600">Password</label>
            <input
              id="password"
              type="password"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
              className="w-full p-3 border border-gray-300 rounded-md"
              required
            />
          </div>
          <div className="mb-4">
            <label htmlFor="role" className="block text-blue-600">Role</label>
            <select
              id="role"
              value={role}
              onChange={(e) => setRole(e.target.value)}
              className="w-full p-3 border border-gray-300 rounded-md"
              required
            >
              <option value="USER">USER</option>
              <option value="ADMIN">ADMIN</option>
            </select>
          </div>
          <button type="submit" className="w-full bg-blue-600 text-white p-3 rounded-md hover:bg-blue-500 focus:outline-none">
            Register
          </button>
        </form>

        {message && (
          <p className={`mt-4 text-center ${message.includes('successful') ? 'text-green-600' : 'text-red-600'}`}>
            {message}
          </p>
        )}
        <button
          onClick={handleLoginRedirect}
          className="w-full bg-blue-200 text-blue-600 p-3 rounded-md mt-4 hover:bg-blue-300 focus:outline-none"
        >
          Go to Login
        </button>
      </div>
    </div>
  );
};

export default Register;
