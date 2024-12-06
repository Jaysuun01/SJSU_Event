import React, { useState } from "react";
import { BrowserRouter as Router, Routes, Route, Navigate } from "react-router-dom";
import Home from "./Pages/Home";
import Login from "./Pages/Login";
import Register from "./Pages/Register";
import EventForm from "./EventForm";
import EditForm from "./EditForm";

function App() {


  return (
    <Router>
      <Routes>
        {/* Default route, redirects to /register when app is first loaded */}
        <Route path="/" element={<Navigate to="/register" />} />
        
        <Route
          path="/login"
          element={<Login />}
        />

        <Route
          path="/dashboard"
          element={<Home />}
        />
        <Route path="/register" element={<Register />} />
        
        <Route path="/createEvent" element={<EventForm />} />
        <Route path="/editForm" element={<EditForm />} />

      </Routes>
    </Router>
  );
}

export default App;
