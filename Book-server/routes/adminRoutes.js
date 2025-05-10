const express = require('express');
const router = express.Router();
const Admin = require('../models/Admin');
const bcrypt = require('bcrypt');

router.get('/', async (req, res) => {
    try {
        const admins = await Admin.find();
        res.json(admins);
    } catch (err) {
        res.status(500).json({ error: err.message });
    }
});

router.post('/login', async (req, res) => {
    const { email, password } = req.body;
    try {
        const admin = await Admin.findOne({ email });
        if (!admin) {
            return res.status(401).json({ error: 'Admin not found' });
        }
        const isMatch = await require('bcrypt').compare(password, admin.password);
        if (!isMatch) {
            return res.status(401).json({ error: 'Invalid password' });
        }
        res.json({
            message: 'Login successful',
            admin: {
                id: admin._id,
                email: admin.email,
                role: admin.role
            }
        });
    } catch (err) {
        res.status(500).json({ error: err.message });
    }
});

// Admin registration route
router.post('/register', async (req, res) => {
    const { email, password, fullName } = req.body;
    try {
        // Check if admin already exists
        const existingAdmin = await Admin.findOne({ email });
        if (existingAdmin) {
            return res.status(400).json({ error: 'Admin already exists' });
        }
        // Hash the password
        const hashedPassword = await bcrypt.hash(password, 10);
        // Create new admin
        const newAdmin = new Admin({
            email,
            password: hashedPassword,
            fullName,
            role: 'admin',
            isVerified: true
        });
        await newAdmin.save();
        res.status(201).json({ message: 'Admin registered successfully' });
    } catch (err) {
        res.status(500).json({ error: err.message });
    }
});

module.exports = router;
