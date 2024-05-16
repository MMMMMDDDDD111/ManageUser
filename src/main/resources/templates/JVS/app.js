// Function to handle login form submission
async function loginUser(event) {
    event.preventDefault();

    // Get values from login form
    const username = document.getElementById('username').value;
    const password = document.getElementById('password').value;

    // Send login request to the API
    const response = await fetch('/api/login', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            userName: username,
            password: password
        })
    });

    // Check response status
    if (response.ok) {
        // Login successful, redirect to dashboard or perform other actions
        window.location.href = '/dashboard';
    } else if (response.status === 404) {
        // User not found
        alert('User not found');
    } else if (response.status === 401) {
        // Invalid password
        alert('Invalid password');
    } else {
        // Other errors
        alert('Error occurred while logging in');
    }
}

// Add event listener to login form submission
document.getElementById('loginForm').addEventListener('submit', loginUser);
