import React from 'react';
import { Link } from 'react-router-dom';
import './ReportCard.css';

export default function ReportCard({ report }) {
  const statusColors = {
    NEW: '#1976d2',
    IN_PROGRESS: '#ffa500',
    RESOLVED: '#4caf50',
    CLOSED: '#9e9e9e',
  };

  const priorityColors = {
    LOW: '#4caf50',
    MEDIUM: '#ffa500',
    HIGH: '#f44336',
  };

  return (
    <div className="report-card">
      <h3>{report.title}</h3>
      <p>
        <strong>Status:</strong>{' '}
        <span style={{ color: statusColors[report.status] || '#000' }}>
          {report.status}
        </span>
      </p>
      <p>
        <strong>Priority:</strong>{' '}
        <span style={{ color: priorityColors[report.priority] || '#000' }}>
          {report.priority}
        </span>
      </p>
      <p>
        <strong>Location:</strong> {report.location}
      </p>
      <p>
        <strong>Citizen:</strong> {report.citizenName || 'Unknown'}
      </p>
      <p>
        <strong>Department:</strong> {report.departmentName || 'Unknown'}
      </p>
      <Link to={`/reports/${report.id}`} className="btn-details">
        View Details
      </Link>
    </div>
  );
}
