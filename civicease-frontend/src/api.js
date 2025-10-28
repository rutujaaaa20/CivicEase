import axios from 'axios';

const API_BASE = 'http://localhost:8080/api';

export const getReports = () => axios.get(`${API_BASE}/reports`);
export const getReportById = (id) => axios.get(`${API_BASE}/reports/${id}`);
export const createReport = (data) => axios.post(`${API_BASE}/reports`, data);
export const updateReport = (id, data) => axios.put(`${API_BASE}/reports/${id}`, data);
export const deleteReport = (id) => axios.delete(`${API_BASE}/reports/${id}`);
export const assignTechnician = (reportId, technicianId) =>
    axios.post(`${API_BASE}/reports/${reportId}/assign-technician/${technicianId}`);
export const getTechnicians = () => axios.get(`${API_BASE}/technicians`);
export const getCitizens = () => axios.get(`${API_BASE}/citizens`);
export const getDepartments = () => axios.get(`${API_BASE}/departments`);
