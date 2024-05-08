fetch('/showAllUsers')
    .then(response => response.json())
    .then(users => {
        const userListDiv = document.getElementById('userList');
        const tableBody = userListDiv.querySelector('table tbody');

        users.forEach(user => {
            const row = document.createElement('tr');
            row.innerHTML = `
                <td>${user.userNo}</td>
                <td>${user.fullName}</td>
                <td>${user.hireDate}</td>
                <td>${user.positionName}</td>
            `;
            tableBody.appendChild(row);
        });
    })
    .catch(error => console.error('Error fetching users:', error));

    //
    //
    //
    function myFunction() {
    var x = document.getElementById("/index.html");
    if (x.style.display === "block") {
    x.style.display = "none";
} else {
    x.style.display = "block";
}
}
    function addUser() {
    var user = {
    fullName: "John Doe",
};

    fetch('/api/user/addUser', {
    method: 'POST',
    headers: {
    'Content-Type': 'application/json'
},
    body: JSON.stringify(user)
})
    .then(response => {
    if (!response.ok) {
    throw new Error('Network response was not ok');
}
    return response.json();
})
    .then(data => {
    console.log('User added successfully:', data);
})
    .catch(error => {
    console.error('There was a problem adding the user:', error);
});
}



    function submitForm() {
    document.getElementById("addUserForm").submit();
    window.location.href = "/index.html";

}

