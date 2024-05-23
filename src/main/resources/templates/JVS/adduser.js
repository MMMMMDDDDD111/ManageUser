document.getElementById('addUserForm').addEventListener('submit', function(event) {
  event.preventDefault();

  const userNo = document.getElementById('userNo').value;
  const fullName = document.getElementById('fullName').value;
  const hireDate = document.getElementById('hireDate').value;
  const positionName = document.getElementById('positionName').value;

  const user = {
    userNo: userNo,
    fullName: fullName,
    hireDate: hireDate,
    position: {
      positionName: positionName
    }
  };

  fetch('http://localhost:6060/api/user/addUser', {
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
      alert(data.message);
      // Reset form fields
      document.getElementById('userNo').value = '';
      document.getElementById('fullName').value = '';
      document.getElementById('hireDate').value = '';
      document.getElementById('positionName').value = '';
    })
    .catch(error => {
      console.error('Error adding user:', error);
      alert('Error adding user. Please try again.');
    });
});