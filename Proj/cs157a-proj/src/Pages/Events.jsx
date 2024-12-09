import React, { useState, useEffect } from "react";
import api from "../api";
import { useNavigate } from "react-router-dom";

const EventPage = () => {
  const [eventList, setEventList] = useState([]);
  const [ticketStatus, setTicketStatus] = useState({}); // Track purchased tickets per event
  const [searchId, setSearchId] = useState(""); // State to store the search query
  const navigate = useNavigate();

  // Fetch all events or a single event based on searchId
  const getEvents = async () => {
    const authToken = localStorage.getItem("authToken");
    try {
      if (searchId) {
        // Search for a single event by eventId
        const response = await api.get(`/api/events/${searchId}`);
        const result = response.data.result;

        if (!result) {
          setEventList([]); // Clear event list if no event found
        } else {
          setEventList([result]); // Set the result as a single event
        }
      } else {
        // Fetch all events
        const response = await api.get(`/api/events/member/${authToken}`);
        // Sort events by showDate in descending order
        const sortedEvents = response.data.result.sort((a, b) => {
          return new Date(b.showDate) - new Date(a.showDate);
        });
        setEventList(sortedEvents);
      }

      // Fetch the tickets the user has already purchased
      const ticketResponse = await api.get(`/api/tickets/member/${authToken}`);
      const purchasedTickets = ticketResponse.data.result || [];

      // Create a mapping of eventId to ticket status
      const statusMap = {};
      purchasedTickets.forEach((ticket) => {
        statusMap[ticket.event.id] = true; // Mark event as purchased
      });
      setTicketStatus(statusMap);
      // Store ticket status in localStorage
      localStorage.setItem('ticketStatus', JSON.stringify(statusMap));

    } catch (error) {
      console.error("Error fetching events:", error);
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

  // Buy ticket
  const buyTicket = async (eventId) => {
    const authToken = localStorage.getItem("authToken");
    try {
      await api.post(`/api/tickets/event/${eventId}/member/${authToken}`);
      alert("Ticket purchased successfully!"); // Notify the user
      setTicketStatus((prevState) => {
        const newState = { ...prevState, [eventId]: true };
        // Store updated status in localStorage
        localStorage.setItem('ticketStatus', JSON.stringify(newState));
        return newState;
      }); // Update the ticket status
      getEvents(); // Refresh the list after purchase
    } catch (error) {
      console.error("Error purchasing ticket:", error);
      alert("Failed to purchase the ticket. Please try again.");
    }
  };

  // View ticket
  const viewTicket = (eventId) => {
    const authToken = localStorage.getItem("authToken");
    try {
      // Navigate to the Tickets page with the eventId
      navigate(`/tickets/${eventId}`);
    } catch (error) {
      console.error("Error viewing ticket:", error);
      alert("Failed to view the ticket. Please try again.");
    }
  };

  // Fetch events when the component mounts or when searchId changes
  useEffect(() => {
    // Retrieve ticket status from localStorage if available
    const savedTicketStatus = JSON.parse(localStorage.getItem('ticketStatus')) || {};
    setTicketStatus(savedTicketStatus);

    getEvents();
  }, [searchId]);

  return (
      <>
        {/* Search and Create Event in the same line */}
        <div className="mb-4 flex items-center justify-between">
          {/* Search Bar */}
          <input
              type="text"
              placeholder="Search by Event ID"
              value={searchId}
              onChange={(e) => setSearchId(e.target.value)}
              className="p-2 border border-gray-300 rounded-md flex-1 mr-4"
          />

          {/* Button to create event */}
          <button
              onClick={() => navigate("/createEvent")}
              className="p-2 bg-green-600 text-white rounded-md"
          >
            Create Event
          </button>
        </div>

        {/* Table of events */}
        <table className="min-w-full table-auto">
          <thead>
          <tr>
            <th className="border px-4 py-2">Event ID</th>
            <th className="border px-4 py-2">Event Title</th>
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
                <td className="border px-4 py-2">{event.title}</td>
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
                  <button
                      onClick={() => buyTicket(event.id)}
                      className="bg-yellow-500 text-white px-4 py-1 rounded-md"
                      disabled={ticketStatus[event.id]} // Disable if the user has already bought a ticket
                  >
                    {ticketStatus[event.id] ? "Ticket Purchased" : "Buy Ticket"}
                  </button>
                  <button
                      onClick={() => viewTicket(event.id)}
                      className="bg-purple-500 text-white px-4 py-1 rounded-md"
                  >
                    View
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
