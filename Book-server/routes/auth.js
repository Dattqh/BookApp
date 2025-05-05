const express = require('express');
const router = express.Router();
const Admin = require('../models/Admin');
const bcrypt = require('bcrypt');
const nodemailer = require('nodemailer');
const crypto = require('crypto');

// Cấu hình email gửi mã
const transporter = nodemailer.createTransport({
  service: 'gmail',
  auth: {
    user: 'dattqh.lms@gmail.com', // 🔒 Gmail của bạn
    pass: 'Dat123'     // 🔐 App password (không phải mật khẩu Gmail thường)
  }
});

// Đăng nhập - bước 1: kiểm tra email và mật khẩu
router.post('/login', async (req, res) => {
  const { email, password } = req.body;

  try {
    const admin = await Admin.findOne({ email });
    if (!admin) return res.status(401).json({ error: 'Invalid email or password' });

    const match = await bcrypt.compare(password, admin.password);
    if (!match) return res.status(401).json({ error: 'Invalid email or password' });

    // Tạo mã xác thực 6 chữ số
    const token = Math.floor(100000 + Math.random() * 900000).toString();
    const expires = new Date(Date.now() + 5 * 60 * 1000); // hết hạn sau 5 phút

    admin.verificationToken = token;
    admin.tokenExpires = expires;
    await admin.save();

    // Gửi email
    await transporter.sendMail({
      from: 'dattqh.lms@gmail.com',
      to: admin.email,
      subject: 'Mã xác thực đăng nhập',
      text: `Mã xác thực của bạn là: ${token}`
    });

    res.json({ message: 'Verification code sent to email' });
  } catch (err) {
    res.status(500).json({ error: err.message });
  }
});

// Bước 2: Xác thực mã
router.post('/verify-token', async (req, res) => {
  const { email, token } = req.body;

  try {
    const admin = await Admin.findOne({ email });
    if (!admin || admin.verificationToken !== token) {
      return res.status(401).json({ error: 'Invalid or expired token' });
    }

    if (admin.tokenExpires < new Date()) {
      return res.status(401).json({ error: 'Token expired' });
    }

    // Xác minh thành công
    admin.verificationToken = null;
    admin.tokenExpires = null;
    admin.isVerified = true;
    await admin.save();

    res.json({ message: 'Login successful', adminId: admin._id });
  } catch (err) {
    res.status(500).json({ error: err.message });
  }
});

module.exports = router;
