import React, { useState, useEffect } from "react";
import api from "../api";
import { useNavigate } from "react-router-dom";

const EventPage = () => {
  const [eventList, setEventList] = useState([]);
  const [searchId, setSearchId] = useState(""); // State to store the search query
  const [error, setError] = useState(""); // State to store error message
  const navigate = useNavigate();

  // Fetch all events or a single event based on searchId
  const getEvents = async () => {
    const authToken = localStorage.getItem("authToken");
    try {
      if (searchId) {
        // Search for a single event by eventId
        const response = await api.get(`/api/events/${searchId}`);
        setEventList([response.data.result]); // Set the result as a single event
      } else {
        // Fetch all events
        const response = await api.get(`/api/events/member/${authToken}`);
        // Sort events by showDate in descending order
        const sortedEvents = response.data.result.sort((a, b) => {
          return new Date(b.showDate) - new Date(a.showDate);
        });
        setEventList(sortedEvents);
      }
    } catch (error) {
      console.error("Error fetching events:", error);
      setError("Event not found.");
    }
  };

  // Delete event
  const deleteEvent = async (eventId) => {
    const authToken = localStorage.getItem("authToken");
    try {
      await api.delete(`/api/events/${eventId}/member/${authToken}`);
      getEvents(); // Refresh the list after deletion
    } catch (error) {
      console.error("Error deleting event:", error);
    }
  };

  // Navigate to EditForm with event data
  const editEvent = (event) => {
    navigate("/editForm", { state: { event } }); // Pass the event data to EditForm
  };

  // Fetch events when the component mounts or when searchId changes
  useEffect(() => {
    getEvents();
  }, [searchId]);

  return (
    <>
      {/* Search Bar */}
      <div className="mb-4">
        <input
          type="text"
          placeholder="Search by Event ID"
          value={searchId}
          onChange={(e) => setSearchId(e.target.value)}
          className="p-2 border border-gray-300 rounded-md"
        />
      </div>

      {/* Error Message */}
      {error && <p className="text-red-500">{error}</p>} {/* Display error message if no event is found */}

      {/* Button to create event */}
      <button
        onClick={() => navigate("/createEvent")}
        className="mb-4 p-2 bg-green-600 text-white rounded-md"
      >
        Create Event
      </button>

      {/* Table of events */}
      <table className="min-w-full table-auto">
        <thead>
          <tr>
            <th className="border px-4 py-2">Event ID</th>
            <th className="border px-4 py-2">Show Date</th>
            <th className="border px-4 py-2">Start Time</th>
            <th className="border px-4 py-2">End Time</th>
            <th className="border px-4 py-2">Entrance Fee</th>
            <th className="border px-4 py-2">Max Audience</th>
            <th className="border px-4 py-2">Actions</th>
          </tr>
        </thead>
        <tbody>
          {eventList.map((event) => (
            <tr key={event.id} className="text-center">
              <td className="border px-4 py-2">{event.id}</td>
              <td className="border px-4 py-2">{event.showDate}</td>
              <td className="border px-4 py-2">{event.startTime}</td>
              <td className="border px-4 py-2">{event.endTime}</td>
              <td className="border px-4 py-2">{event.entranceFee}</td>
              <td className="border px-4 py-2">{event.maxAudience}</td>
              <td className="border px-4 py-2 flex justify-center gap-2">
                <button
                  onClick={() => deleteEvent(event.id)}
                  className="bg-red-500 text-white px-4 py-1 rounded-md"
                >
                  Delete
                </button>
                <button
                  onClick={() => editEvent(event)}
                  className="bg-blue-500 text-white px-4 py-1 rounded-md"
                >
                  Edit
                </button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </>
  );
};

export default EventPage;
