// src/App.jsx
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';

// Pages
import HomePage from './pages/HomePage.jsx';
import Dashboard from './pages/Dashboard.jsx';
import CreateReport from './pages/CreateReport.jsx';
import ReportDetails from './pages/ReportDetails.jsx';
import Login from './pages/Login.jsx';

// Components
import Header from './components/Header.jsx';
import Footer from './components/Footer.jsx';
import Sidebar from './components/Sidebar.jsx';

// Styles
import './App.css';

function App() {
  return (
    <Router>
      {/* Header is shown on all pages */}
      <Header />

      <div className="main-layout">
        <Routes>
          {/* Dashboard and related routes with sidebar */}
          <Route
            path="/dashboard/*"
            element={
              <div className="layout-with-sidebar">
                <Sidebar />
                <div className="page-content">
                  <Routes>
                    <Route path="" element={<Dashboard />} />
                    <Route path="create-report" element={<CreateReport />} />
                    <Route path="reports/:id" element={<ReportDetails />} />
                  </Routes>
                </div>
              </div>
            }
          />

          {/* Public routes */}
          <Route path="/" element={<HomePage />} />
          <Route path="/login" element={<Login />} />
        </Routes>
      </div>

      <Footer />
    </Router>
  );
}

export default App;
