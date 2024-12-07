import React, { useState } from "react";
import api from "./api";
import { useNavigate } from "react-router-dom";

export default function EventForm({ memberId, eventId, existingEvent, onSuccess }) {
  const [formData, setFormData] = useState({
    date: existingEvent?.date || "",
    time: existingEvent?.time || "",
    title: existingEvent?.title || "",
    description: existingEvent?.description || "",
    entranceFee: existingEvent?.entranceFee || 0,
    maxAudience: existingEvent?.maxAudience || 0,
    startTime: "00:00",
    endTime: "11:59",
  });

  const [error, setError] = useState(null);
  const [isSubmitting, setIsSubmitting] = useState(false); // Prevent duplicate submissions
  const navigate = useNavigate();

  const handleChange = (e) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError(null);

    // Prevent multiple submissions
    if (isSubmitting) return;
    setIsSubmitting(true);

    try {
      const authToken = localStorage.getItem("authToken");
      await api.post(`/api/events/${authToken}`, {
        title: formData.title,
        maxAudience: formData.maxAudience,
        entranceFee: formData.entranceFee,
        showDate: formData.date,
        startTime: formData.startTime,
        endTime: formData.endTime,
      });

      // Call success callback (if any)
      if (onSuccess) onSuccess();

      // Navigate or provide feedback to the user
      navigate("/dashboard");
    } catch (error) {
      console.error("Error:", error);
      setError(error.response?.data?.message || "Something went wrong.");
    } finally {
      setIsSubmitting(false); // Re-enable submission
    }
  };

  return (
      <div className="bg-white p-4 rounded-lg shadow-md">
        <h2 className="text-2xl font-bold mb-4 text-center">Create New Event</h2>
        {error && <p className="text-red-500 mb-4">{error}</p>}
        <form onSubmit={handleSubmit}>
          <div className="mb-4">
            <label className="block text-gray-700">Title</label>
            <input
                type="text"
                name="title"
                value={formData.title}
                onChange={handleChange}
                className="w-full p-2 border rounded"
                required
            />
          </div>
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
            <label className="block text-gray-700">Audience</label>
            <input
                type="text"
                name="maxAudience"
                value={formData.maxAudience}
                onChange={handleChange}
                className="w-full p-2 border rounded"
                required
            />
          </div>
          <div className="mb-4">
            <label className="block text-gray-700">Entrance Fee</label>
            <textarea
                name="entranceFee"
                value={formData.entranceFee}
                onChange={handleChange}
                className="w-full p-2 border rounded"
                required
            ></textarea>
          </div>
          <div className="flex justify-center space-x-4">
            <button
                type="submit"
                className={`px-4 py-2 rounded ${
                    isSubmitting
                        ? "bg-gray-400 cursor-not-allowed"
                        : "bg-blue-600 text-white hover:bg-blue-500"
                }`}
                disabled={isSubmitting}
            >
              {isSubmitting ? "Submitting..." : "Create Event"}
            </button>
            <button
                type="button"
                onClick={() => navigate("/dashboard")}
                className="bg-gray-600 text-white px-4 py-2 rounded hover:bg-gray-500"
            >
              Back
            </button>
          </div>
        </form>
      </div>
  );
}
