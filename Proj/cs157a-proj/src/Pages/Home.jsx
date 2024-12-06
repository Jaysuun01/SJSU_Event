import React, { useState } from "react";
import Navbar from "../Navbar"; // Adjust the path based on your file structure
import EventPage from "./Events";

function Home({ onLogout }) {
  const [tab, setTab] = useState("");

  return (
    <div className="Home">
      {/* Pass the onLogout handler to the Navbar */}
      <Navbar onLogout={onLogout} setTab={(tabName) => setTab(tabName)} />
      <main className="p-6 bg-gray-100 min-h-screen">
        {tab !== "Events" && (
          <div className="text-center">
            <h1>Welcome to the Event Management System!</h1>
            <p>Manage your events effectively and efficiently here.</p>
          </div>
        )}
        {tab === "Events" && <EventPage />}
      </main>
    </div>
  );
}

export default Home;
