document.addEventListener('DOMContentLoaded', function () {
    document.querySelectorAll('.removeButton').forEach(button => {
        button.addEventListener('click', function () {
        // removes the button and its parent div
            this.parentElement.remove();
        });
    });
});
