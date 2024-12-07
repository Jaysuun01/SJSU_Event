import React from 'react';
import { useNavigate } from 'react-router-dom';

export default function Navbar({ setTab }) {
  const navigate = useNavigate();

  // Logout function
  const logOut = () => {
    // Clear authentication token from localStorage (if it's stored there)
    localStorage.removeItem("authToken");

    // Redirect to login page
    navigate("/login"); // Adjust the path if needed
  };

  return (

    <nav className="bg-blue-600 text-white p-4 flex justify-between">
      <div className="text-xl font-bold">SJSU Event Management</div>
      <ul className="flex space-x-4">
        <li><a onClick={() => setTab("Home")} className="hover:underline">Home</a></li>
        <li><a onClick={() => setTab("Events")} className="hover:underline">Events</a></li>
        {/* Logout button */}
        <li><button onClick={logOut} className="bg-red-500 text-white px-4 py-1 rounded-md hover:bg-red-600">Log Out</button></li>
      </ul>
    </nav>
  );
}
