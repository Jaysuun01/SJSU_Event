import React, { useState, useEffect } from "react";
import api from "../api";
import { useNavigate, useParams } from "react-router-dom";

const Tickets = () => {
    const { eventId } = useParams(); // Get eventId from the URL
    const [ticketList, setTicketList] = useState([]);
    const [error, setError] = useState(""); // State to store error message
    const navigate = useNavigate();

    // Fetch all tickets purchased for the specific event
    const getTickets = async () => {
        const authToken = localStorage.getItem("authToken");
        try {
            const response = await api.get(`/api/tickets/event/${eventId}/member/${authToken}`);
            if (response.data.result) {
                setTicketList([response.data.result]); // Wrap the result in an array
            } else {
                setError("No tickets found.");
            }
        } catch (error) {
            console.error("Error fetching tickets:", error);
        }
    };

    // Fetch tickets when the component mounts
    useEffect(() => {
        if (eventId) {
            getTickets();
        }
    }, [eventId]);

    return (
        <>
            {/* Back Button */}
            <button
                onClick={() => navigate("/dashboard")} // Navigate back to the previous page
                className="p-2 bg-blue-500 text-white rounded-md mb-4 w-auto"
            >
                Back
            </button>

            {/* Error Message */}
            {error && <p className="text-red-500">{error}</p>} {/* Display error message if no tickets are found */}

            {/* Table of purchased tickets */}
            <table className="min-w-full table-auto">
                <thead>
                <tr>
                    <th className="border px-4 py-2">Ticket ID</th>
                    <th className="border px-4 py-2">UUID</th>
                    <th className="border px-4 py-2">Due Date</th>
                </tr>
                </thead>
                <tbody>
                {ticketList.length > 0 ? (
                    ticketList.map((ticket) => (
                        <tr key={ticket.ticketId} className="text-center">
                            <td className="border px-4 py-2">{ticket.ticketId}</td>
                            <td className="border px-4 py-2">{ticket.uuid}</td>
                            <td className="border px-4 py-2">{ticket.dueDate}</td>
                        </tr>
                    ))
                ) : (
                    <tr>
                        <td colSpan="3" className="text-center border px-4 py-2">
                            No tickets available.
                        </td>
                    </tr>
                )}
                </tbody>
            </table>
        </>
    );
};

export default Tickets;
