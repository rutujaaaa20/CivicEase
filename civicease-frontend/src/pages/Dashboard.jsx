import React, { useEffect, useState } from 'react';
import axios from 'axios';
import ReportCard from '../components/ReportCard';
import './Dashboard.css';

export default function Dashboard() {
  const [reports, setReports] = useState([]);

  useEffect(() => {
    axios.get('http://localhost:8080/api/reports')
      .then(res => setReports(res.data))
      .catch(err => console.error(err));
  }, []);

  return (
    <div className="dashboard-container">
      <h2>All Reports</h2>
      {reports.length === 0 ? (
        <p>No reports available.</p>
      ) : (
        <div className="reports-grid">
          {reports.map(report => (
            <ReportCard key={report.id} report={report} />
          ))}
        </div>
      )}
    </div>
  );
}
