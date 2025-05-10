const express = require('express');
const mongoose = require('mongoose');
const cors = require('cors');

const Admin = require('./models/Admin');
const Book = require('./models/Book'); 
const authRoutes = require('./routes/auth');
const adminRoutes = require('./routes/adminRoutes');


const app = express();

// === MIDDLEWARE ===
app.use(cors());
app.use(express.json());

// === ROUTES ===
app.use('/api/auth', authRoutes);
app.use('/api/auth', adminRoutes);

// GET tất cả sách
app.get('/books', async (req, res) => {
    try {
        const books = await Book.find();
        res.json(books);
    } catch (err) {
        res.status(500).json({ error: err.message });
    }
});

// GET sách theo ID
app.get('/books/:id', async (req, res) => {
    try {
        const book = await Book.findById(req.params.id);
        if (!book) return res.status(404).json({ error: 'Book not found' });
        res.json(book);
    } catch (err) {
        res.status(500).json({ error: err.message });
    }
});

// POST thêm sách mới
app.post('/books', async (req, res) => {
    try {
        const newBook = new Book(req.body);
        await newBook.save();
        res.status(201).json({ message: 'Book added' });
    } catch (err) {
        res.status(400).json({ error: err.message });
    }
});

// (Tùy chọn) GET danh sách admin
app.get('/api/admins', async (req, res) => {
    try {
        const admins = await Admin.find();
        res.json(admins);
    } catch (err) {
        res.status(500).json({ error: err.message });
    }
});

// POST: Thêm chương mới vào sách
app.post('/books/:id/chapters', async (req, res) => {
    try {
        const { title, order, content } = req.body;
        const book = await Book.findById(req.params.id);
        if (!book) return res.status(404).json({ error: 'Book not found' });

        const newChapter = {
            title,
            order,
            content,
            createdAt: new Date(),
            updatedAt: new Date()
        };

        book.chapters.push(newChapter);
        await book.save();

        res.status(201).json({ message: 'Chapter added', chapter: newChapter });
    } catch (err) {
        res.status(500).json({ error: err.message });
    }
});


// Lấy danh sách chapter của sách
app.get('/books/:id/chapters', async (req, res) => {
    try {
        const book = await Book.findById(req.params.id).select('chapters');
        if (!book) return res.status(404).json({ error: 'Book not found' });

        res.json(book.chapters.sort((a, b) => a.order - b.order));
    } catch (err) {
        res.status(500).json({ error: err.message });
    }
});


// === CONNECT TO DB ===
mongoose.connect('mongodb://localhost:27017/BookApp', {
    useNewUrlParser: true,
    useUnifiedTopology: true,
})
.then(() => console.log('✅ MongoDB Connected'))
.catch((err) => console.log('❌ MongoDB connection error:', err));

// === START SERVER ===
app.listen(3000, '0.0.0.0', () => {
    console.log('🚀 Server running on http://localhost:3000');
});
