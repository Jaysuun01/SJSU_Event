import React from 'react';

export default function EventCard({ event }) {
  return (
    <div className="border rounded-lg shadow-md p-4 bg-white">
      <h3 className="text-lg font-bold">{event.title}</h3>
      <p className="text-gray-600">{event.date} at {event.time}</p>
      <p className="text-gray-800">{event.location}</p>
      <p className="text-sm text-gray-500">{event.description}</p>
      <button className="bg-blue-600 text-white mt-2 px-4 py-2 rounded hover:bg-blue-500">
        View Details
      </button>
    </div>
  );
}
