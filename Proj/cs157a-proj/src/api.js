import axios from "axios";

const api = axios.create({
  baseURL: "http://localhost:8080", // Backend URL
});

// Add token to every request (if logged in)
api.interceptors.request.use((config) => {
  const token = localStorage.getItem("authToken");
  if (token) {
    config.headers.Authorization = `Bearer ${token}`;
  }
  return config;
});

export default api;
