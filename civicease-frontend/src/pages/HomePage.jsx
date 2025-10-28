import React from 'react';
import { Link } from 'react-router-dom';
import './HomePage.css';

export default function HomePage() {
  return (
    <div className="home-container">
      <section className="hero">
        <h1>Welcome to CivicEase</h1>
        <p>Report civicsues and track their resolution easily.</p>
        <Link to="/login" className="btn-primary">Get Started</Link>
      </section>

      <section className="features">
        <div className="feature-card">
          <h3>Submit Reports</h3>
          <p>Report potholes, broken lamps, or other issues in your city.</p>
        </div>
        <div className="feature-card">
          <h3>Track Status</h3>
          <p>See the status of your reports and updates from city technicians.</p>
        </div>
        <div className="feature-card">
          <h3>Contact Departments</h3>
          <p>Reach out to the appropriate city department for any concern.</p>
        </div>
      </section>
    </div>
  );
}
