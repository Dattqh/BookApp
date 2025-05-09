const mongoose = require('mongoose');
const bcrypt = require('bcrypt');
const Admin = require('../models/Admin');

// Replace with your MongoDB connection string
const MONGODB_URI = 'mongodb://localhost:27017/your_db_name';

async function createAdmin() {
  const email = 'dattqh.lms@gmail.com'; // Change to your admin email
  const plainPassword = 'Dat123';         // Change to your desired password

  await mongoose.connect(MONGODB_URI);

  // Check if admin already exists
  const existing = await Admin.findOne({ email });
  if (existing) {
    console.log('Admin already exists!');
    process.exit(0);
  }

  // Hash the password
  const hashedPassword = await bcrypt.hash(plainPassword, 10);

  // Create the admin user
  const admin = new Admin({
    email,
    password: hashedPassword,
    isVerified: true
  });

  await admin.save();
  console.log('Admin created successfully!');
  process.exit(0);
}

createAdmin().catch(err => {
  console.error(err);
  process.exit(1);
});
