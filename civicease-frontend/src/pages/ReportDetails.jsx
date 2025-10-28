import React, { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import './ReportDetails.css';

export default function ReportDetails() {
  const { id } = useParams();
  const [report, setReport] = useState(null);
  const [technicians, setTechnicians] = useState([]);
  const [selectedTech, setSelectedTech] = useState('');
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const fetchReportDetails = async () => {
      try {
        const resReport = await fetch(`http://localhost:8080/api/reports/${id}`);
        const reportData = await resReport.json();

        const resCitizens = await fetch('http://localhost:8080/api/citizens');
        const citizensData = await resCitizens.json();
        const citizen = citizensData.find(c => c.id === reportData.citizenId);

        const resDepartments = await fetch('http://localhost:8080/api/departments');
        const departmentsData = await resDepartments.json();
        const department = departmentsData.find(d => d.id === reportData.departmentId);

        const resTechnicians = await fetch('http://localhost:8080/api/technicians');
        const techData = await resTechnicians.json();

        setReport({
          ...reportData,
          citizenName: citizen ? `${citizen.firstName} ${citizen.lastName}` : 'Unknown',
          departmentName: department ? department.name : 'Unknown',
        });
        setTechnicians(techData.filter(t => t.departmentId === reportData.departmentId));
        setLoading(false);
      } catch (error) {
        console.error('Error fetching report details:', error);
        setLoading(false);
      }
    };

    fetchReportDetails();
  }, [id]);

  const handleAssignTechnician = async () => {
    if (!selectedTech) return;
    try {
      const res = await fetch(
        `http://localhost:8080/api/reports/${id}/assign-technician/${selectedTech}`,
        { method: 'POST' }
      );
      if (res.ok) {
        const updatedReport = await res.json();
        setReport({
          ...report,
          assignedTechnician: technicians.find(t => t.id === parseInt(selectedTech)),
        });
        alert('Technician assigned successfully!');
      } else {
        alert('Failed to assign technician.');
      }
    } catch (error) {
      console.error(error);
      alert('Error assigning technician.');
    }
  };

  if (loading) return <p>Loading report details...</p>;
  if (!report) return <p>Report not found.</p>;

  return (
    <div className="report-details">
      <h1>{report.title}</h1>
      <p><strong>Description:</strong> {report.description}</p>
      <p><strong>Location:</strong> {report.location}</p>
      <p><strong>Status:</strong> {report.status}</p>
      <p><strong>Priority:</strong> {report.priority}</p>
      <p><strong>Citizen:</strong> {report.citizenName}</p>
      <p><strong>Department:</strong> {report.departmentName}</p>
      <p><strong>Assigned Technician:</strong> {report.assignedTechnician ? `${report.assignedTechnician.firstName} ${report.assignedTechnician.lastName}` : 'None'}</p>

      <div className="assign-tech">
        <label>Assign Technician:</label>
        <select value={selectedTech} onChange={e => setSelectedTech(e.target.value)}>
          <option value="">Select</option>
          {technicians.map(t => (
            <option key={t.id} value={t.id}>
              {t.firstName} {t.lastName} ({t.specialization})
            </option>
          ))}
        </select>
        <button onClick={handleAssignTechnician}>Assign</button>
      </div>
    </div>
  );
}
