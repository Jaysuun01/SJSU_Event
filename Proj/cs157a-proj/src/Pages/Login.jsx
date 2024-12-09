import React, { useState, useEffect } from "react";
import api from "../api";
import { useNavigate } from "react-router-dom";

function Login({ onLoginSuccess }) {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [error, setError] = useState("");
  const navigate = useNavigate();

  const handleLogin = async (e) => {
    e.preventDefault();
    try {
      const response = await api.get(`/api/members/login/user?username=${email}&password=${password}`, { email, password });
      const { result } = response.data;
      localStorage.setItem("authToken", result.id); // Save token
      navigate("/dashboard");
    } catch (err) {
      setError("Invalid email or password");
      console.error(err);
    }
  };

  useEffect(() => { 
    const authToken = localStorage.getItem("authToken");
    if (authToken != null) {
      navigate("/dashboard")
    }
  }, []);

  return (
    <div className="flex justify-center items-center min-h-screen bg-blue-100">
      <div className="bg-white p-8 rounded-lg shadow-lg w-96">
        <h1 className="text-3xl font-semibold text-center text-blue-600 mb-6">Login</h1>
        <form onSubmit={handleLogin} className="space-y-4">
          <input
            type="username"
            placeholder="Email"
            value={email}
            onChange={(e) => setEmail(e.target.value)}
            required
            className="w-full p-3 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500"
          />
          <input
            type="password"
            placeholder="Password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
            required
            className="w-full p-3 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500"
          />
          <button
            type="submit"
            className="w-full py-3 bg-blue-600 text-white rounded-md hover:bg-blue-700 focus:outline-none focus:ring-2 focus:ring-blue-500"
          >
            Login
          </button>
        </form>
        {error && <p className="text-red-500 text-center mt-4">{error}</p>}
        <div className="text-center mt-4">
          <button
            onClick={() => navigate("/register")}
            className="w-full py-3 bg-blue-600 text-white rounded-md hover:bg-blue-700 focus:outline-none focus:ring-2 focus:ring-blue-500"
          >
            Register
          </button>
        </div>
      </div>
    </div>
  );
}

export default Login;
