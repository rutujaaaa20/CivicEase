import React from 'react';
import { Link } from 'react-router-dom';
import './Sidebar.css';

export default function Sidebar() {
  return (
    <aside className="sidebar">
      <h2>Menu</h2>
      <nav>
        <ul>
          <li><Link to="/dashboard">Dashboard</Link></li>
          <li><Link to="/reports">Reports</Link></li>
          <li><Link to="/technicians">Technicians</Link></li>
          <li><Link to="/departments">Departments</Link></li>
        </ul>
      </nav>
    </aside>
  );
}
