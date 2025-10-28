-- ==============================
-- Sample Data for cityreportdb
-- ==============================

-- Insertion of sample citizens
INSERT INTO `citizens` (first_name, last_name, email, phone, address, created_at) VALUES
('John', 'Doe', 'john.doe@example.com', '1234567890', '15 Main Street, Cityville', NOW()),
('Jane', 'Smith', 'jane.smith@example.com', '1234567891', '1 Oak Avenue, Townsville', NOW()),
('Alice', 'Brown', 'alice.brown@example.com', '1234567892', '22 Pine Road, Villagetown', NOW());

-- Insertion of sample departments
INSERT INTO `departments` (name, description, contact_email, contact_phone, created_at) VALUES
('Technical Services', 'Responsible for roads, sidewalks, and public lighting.', 'techservices@city.gov', '100200300', NOW()),
('Environmental Department', 'Manages parks, greenery, and waste disposal.', 'environment@city.gov', '100200301', NOW()),
('Municipal Police', 'Handles parking issues and public safety.', 'police@city.gov', '100200302', NOW());

-- Insertion of sample technicians
INSERT INTO `technicians` (first_name, last_name, email, specialization, available, department_id, created_at) VALUES
('Peter', 'Miller', 'peter.miller@city.gov', 'Road Repair', 1, 1, NOW()),
('Martin', 'Clark', 'martin.clark@city.gov', 'Public Lighting', 1, 1, NOW()),
('Susan', 'Green', 'susan.green@city.gov', 'Park Maintenance', 1, 2, NOW()),
('Joseph', 'Wise', 'joseph.wise@city.gov', 'Waste Management', 0, 2, NOW());

-- Insertion of sample reports
INSERT INTO `reports` (title, description, location, status, priority, citizen_id, department_id, created_at, updated_at) VALUES
('Damaged Sidewalk', 'Large pothole on Main Street sidewalk, dangerous for pedestrians.', '15 Main Street, Cityville', 'NEW', 'HIGH', 1, 1, NOW(), NOW()),
('Broken Street Lamp', 'Street lamp at corner of Oak Avenue is not working for a week.', 'Corner of Oak Avenue and Maple Street, Townsville', 'NEW', 'MEDIUM', 2, 1, NOW(), NOW()),
('Overflowing Trash Bins', 'Recycling bins near City Mall are overflowing with garbage.', '10 Market Street, Townsville', 'IN_PROGRESS', 'MEDIUM', 2, 2, NOW(), NOW()),
('Illegal Dumping', 'Someone dumped old furniture behind an apartment building.', '22 Pine Road, Villagetown', 'RESOLVED', 'LOW', 3, 2, NOW(), NOW());

-- Assigning technicians to reports
INSERT INTO `report_technician` (report_id, technician_id) VALUES
(1, 1),  -- Peter Miller assigned to Damaged Sidewalk
(2, 2),  -- Martin Clark assigned to Broken Street Lamp
(3, 3),  -- Susan Green assigned to Overflowing Trash Bins
(4, 4);  -- Joseph Wise assigned to Illegal Dumping
