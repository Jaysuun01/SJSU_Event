import React, { useState } from "react";
import { useLocation, useNavigate } from "react-router-dom";
import api from "./api";

const EditForm = () => {
  const navigate = useNavigate();
  const location = useLocation();
  const event = location.state?.event; // Get event data from state

  // Initialize state unconditionally
  const [formData, setFormData] = useState({
    date: event?.showDate || "",
    startTime: event?.startTime || "00:00",
    endTime: event?.endTime || "23:59",
    maxAudience: event?.maxAudience || 0,
    entranceFee: event?.entranceFee || 0,
  });

  const [error, setError] = useState(null);

  // Early return after hooks have been called
  if (!event) {
    return <p>Error: No event data provided.</p>; // Handle missing event data
  }

  const handleChange = (e) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError(null);
  
    // Check if any changes have been made to the form data
    const isDataUnchanged =
      formData.date === event?.showDate &&
      formData.startTime === event?.startTime &&
      formData.endTime === event?.endTime &&
      formData.maxAudience === event?.maxAudience &&
      formData.entranceFee === event?.entranceFee;
  
    if (isDataUnchanged) {
      setError("No changes detected.");
      return; // Exit early if no changes were made
    }
  
    try {
      const authToken = localStorage.getItem("authToken");
  
      // Convert numeric fields and match API schema
      const formattedData = {
        showDate: formData.date, // Map `date` to `showDate`
        startTime: formData.startTime,
        endTime: formData.endTime,
        maxAudience: Number(formData.maxAudience), // Ensure numeric type
        entranceFee: Number(formData.entranceFee), // Ensure numeric type
      };
  
      console.log("Payload sent to API:", formattedData); // Debugging
  
      const response = await api.put(
        `/api/events/${event.id}/member/${authToken}`,
        formattedData
      );
  
      console.log("API response:", response); // Debugging
  
      // Display success message if the response is successful
      if (response.status === 200) {
        setError("Event updated successfully!");
      }
    } catch (error) {
      console.error("Error updating event:", error);
      setError(error.response?.data?.message || "Something went wrong.");
    }
  };
  

  return (
    <div className="bg-white p-4 rounded-lg shadow-md">
      <h2 className="text-2xl font-bold mb-4 text-center">Update Event</h2>
      {error && <p className="text-red-500 mb-4">{error}</p>}

      <form onSubmit={handleSubmit}>
        <div className="mb-4">
          <label className="block text-gray-700">Date</label>
          <input
            type="date"
            name="date"
            value={formData.date}
            onChange={handleChange}
            className="w-full p-2 border rounded"
            required
          />
        </div>
        <div className="mb-4">
          <label className="block text-gray-700">Start Time</label>
          <input
            type="time"
            name="startTime"
            value={formData.startTime}
            onChange={handleChange}
            className="w-full p-2 border rounded"
            required
          />
        </div>
        <div className="mb-4">
          <label className="block text-gray-700">End Time</label>
          <input
            type="time"
            name="endTime"
            value={formData.endTime}
            onChange={handleChange}
            className="w-full p-2 border rounded"
            required
          />
        </div>
        <div className="mb-4">
          <label className="block text-gray-700">Max Audience</label>
          <input
            type="number"
            name="maxAudience"
            value={formData.maxAudience}
            onChange={handleChange}
            className="w-full p-2 border rounded"
            required
          />
        </div>
        <div className="mb-4">
          <label className="block text-gray-700">Entrance Fee</label>
          <input
            type="number"
            name="entranceFee"
            value={formData.entranceFee}
            onChange={handleChange}
            className="w-full p-2 border rounded"
            required
          />
        </div>
        <div className="flex justify-center space-x-4">
          <button
            type="submit"
            className="bg-blue-600 text-white px-4 py-2 rounded hover:bg-blue-500"
          >
            Submit
          </button>
          <button
            onClick={() => navigate("/dashboard")}
            className="bg-gray-600 text-white px-4 py-2 rounded hover:bg-gray-500"
          >
            Back
          </button>
        </div>

        
      </form>
    </div>
  );
};

export default EditForm;
