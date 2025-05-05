const mongoose = require('mongoose');

const ChapterSchema = new mongoose.Schema({
    title: { type: String, required: true },
    order: { type: Number, required: true },
    content: { type: String, required: true },
    createdAt: { type: Date, default: Date.now },
    updatedAt: { type: Date, default: Date.now },
});

const BookSchema = new mongoose.Schema({
    title: { type: String, required: true },
    author: { type: String, required: true },
    category: { type: String },
    description: { type: String },
    coverUrl: { type: String },
    genre: [String],
    publishedYear: Number,
    pages: Number,
    rating: Number,
    isFavorite: Boolean,
    chapters: [ChapterSchema]
}, { timestamps: true });

module.exports = mongoose.model('Book', BookSchema);
