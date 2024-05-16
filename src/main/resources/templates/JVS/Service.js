const express = require('express');
const bodyParser = require('body-parser');
const mongoose = require('mongoose');
const { Schema } = mongoose;

const app = express();
const PORT = 6060;

mongoose.connect('mongodb+srv://duyvhtgcs190565:123456Aa@cluster0.wdfkxgs.mongodb.net/?retryWrites=true&w=majority&appName=Cluster0', { useNewUrlParser: true, useUnifiedTopology: true });

const userSchema = new Schema({
    userNo: { type: String, unique: true },
    fullName: String,
    hireDate: Date,
    position: { type: Schema.Types.ObjectId, ref: 'Position' }
});

const User = mongoose.model('User', userSchema);

app.use(bodyParser.json());

app.get('/api/user/addUser', async (req, res) => {
    try {
        const users = await User.find();
        res.json(users);
    } catch (error) {
        console.error('Error fetching users:', error);
        res.status(500).json({ error: 'Internal Server Error' });
    }
});

app.post('/api/user/addUser', async (req, res) => {
    try {
        const { userNo, fullName, hireDate, position } = req.body;
        const newUser = new User({ userNo, fullName, hireDate, position });
        const savedUser = await newUser.save();
        res.status(201).json(savedUser);
    } catch (error) {
        console.error('Error adding user:', error);
        res.status(500).json({ error: 'Internal Server Error' });
    }
});

app.listen(PORT, () => {
    console.log(`Server is running on http://localhost:6060`);
});
