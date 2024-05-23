//
// Login dialog
//
document.addEventListener("DOMContentLoaded", function() {
    document.getElementById('show-login').addEventListener("click", function (){
        document.querySelector(".popup").classList.add("active");
    });
    document.querySelector(".popup .close-btn").addEventListener("click", function (){
        document.querySelector(".popup").classList.remove("active");
    });
    document.getElementById('login-button').addEventListener("click", async function (){
        const username = document.getElementById('username').value;
        const password = document.getElementById('password').value;
        const loginRequest = {
            username: username,
            password: password
        };

        const response = await fetch('http://localhost:6060/api/login', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(loginRequest)
        });

        if (response.ok) {
            const data = await response.json();
            document.cookie = `jwt=${data.jwt}; path=/`;
            alert('Login successful!');
            window.location.href = 'index.html';
        } else {
            alert('Invalid username or password');
        }
    });
});
//
//LOGIN
//
document.getElementById('loginButton').addEventListener('click', async function () {
    const username = document.getElementById('username').value;
    const password = document.getElementById('password').value;

    const loginRequest = {
        userName: username,
        password: password
    };

    try {
        const response = await fetch('http://localhost:6060/api/login', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(loginRequest)
        });

        if (response.ok) {
            const data = await response.json();
            localStorage.setItem('jwt', data.jwt);
            localStorage.setItem('refreshToken', data.refreshToken);
            alert('Login successful!');
            window.location.href = 'index.html';
        } else {
            alert('Invalid username or password');
        }
    } catch (error) {
        console.error('Error during login:', error);
        alert('An error occurred. Please try again later.');
    }
});
//
//Show allUsers
//
document.addEventListener("DOMContentLoaded", function() {
    fetch('http://localhost:6060/api/user/showAllUsers')
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.json();
        })
        .then(data => displayUsers(data))
        .catch(error => console.error('Error fetching users:', error));
});

function displayUsers(users) {
    const tbody = document.querySelector("#userList tbody");
    tbody.innerHTML = ""; // Clear existing content
    users.forEach(user => {
        const row = document.createElement("tr");
        row.innerHTML = `
            <td>${user.userNo}</td>
            <td>${user.fullName}</td>
            <td>${user.hireDate}</td>
            <td>${user.position.positionName}</td>
        `;
        tbody.appendChild(row);
    });
}



