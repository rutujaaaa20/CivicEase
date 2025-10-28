import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import './CreateReport.css';

export default function CreateReport() {
  const navigate = useNavigate();

  const [citizens, setCitizens] = useState([]);
  const [departments, setDepartments] = useState([]);
  const [formData, setFormData] = useState({
    title: '',
    description: '',
    location: '',
    priority: 'LOW',
    citizenId: '',
    departmentId: ''
  });
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const fetchData = async () => {
      try {
        const resCitizens = await fetch('http://localhost:8080/api/citizens');
        const citizensData = await resCitizens.json();
        setCitizens(citizensData);

        const resDepartments = await fetch('http://localhost:8080/api/departments');
        const departmentsData = await resDepartments.json();
        setDepartments(departmentsData);

        setLoading(false);
      } catch (error) {
        console.error('Error fetching data:', error);
        setLoading(false);
      }
    };
    fetchData();
  }, []);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData(prev => ({ ...prev, [name]: value }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    if (!formData.citizenId || !formData.departmentId) {
      alert('Please select a citizen and a department.');
      return;
    }

    try {
      const res = await fetch('http://localhost:8080/api/reports', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(formData),
      });

      if (res.ok) {
        alert('Report created successfully!');
        navigate('/dashboard');
      } else {
        const errorData = await res.json();
        alert('Error creating report: ' + (errorData.message || JSON.stringify(errorData)));
      }
    } catch (error) {
      console.error(error);
      alert('Error creating report.');
    }
  };

  if (loading) return <p>Loading form data...</p>;

  return (
    <div className="create-report">
      <h1>Create New Report</h1>
      <form onSubmit={handleSubmit}>
        <label>
          Title:
          <input type="text" name="title" value={formData.title} onChange={handleChange} required />
        </label>

        <label>
          Description:
          <textarea name="description" value={formData.description} onChange={handleChange} required />
        </label>

        <label>
          Location:
          <input type="text" name="location" value={formData.location} onChange={handleChange} required />
        </label>

        <label>
          Priority:
          <select name="priority" value={formData.priority} onChange={handleChange}>
            <option value="LOW">Low</option>
            <option value="MEDIUM">Medium</option>
            <option value="HIGH">High</option>
          </select>
        </label>

        <label>
          Citizen:
          <select name="citizenId" value={formData.citizenId} onChange={handleChange} required>
            <option value="">Select Citizen</option>
            {citizens.map(c => (
              <option key={c.id} value={c.id}>
                {c.firstName} {c.lastName}
              </option>
            ))}
          </select>
        </label>

        <label>
          Department:
          <select name="departmentId" value={formData.departmentId} onChange={handleChange} required>
            <option value="">Select Department</option>
            {departments.map(d => (
              <option key={d.id} value={d.id}>
                {d.name}
              </option>
            ))}
          </select>
        </label>

        <button type="submit">Create Report</button>
      </form>
    </div>
  );
}
