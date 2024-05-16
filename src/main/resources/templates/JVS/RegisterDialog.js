
var dialog = document.getElementById('registerDialog');

var registerLink = document.getElementById('registerLink');

registerLink.addEventListener('click', function() {
    dialog.style.display = 'block'; // Hiển thị dialog
});

var closeButton = dialog.querySelector('.close');
closeButton.addEventListener('click', function() {
    dialog.style.display = 'none'; // Ẩn dialog
});

window.addEventListener('click', function(event) {
    if (event.target == dialog) {
        dialog.style.display = 'none'; // Ẩn dialog
    }
});
