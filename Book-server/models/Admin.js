const mongoose = require('mongoose');

const adminSchema = new mongoose.Schema({
  email: {
    type: String,
    required: true,
    unique: true
  },

  password: {
    type: String,
    required: true
  },

  isVerified: {
    type: Boolean,
    default: false
  },

  verificationToken: {
    type: String,
    default: null
  },

  tokenExpires: {
    type: Date,
    default: null
  }
});

module.exports = mongoose.model('Admin', adminSchema);
