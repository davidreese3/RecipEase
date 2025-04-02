document.addEventListener("DOMContentLoaded", function () {
    const buttons = document.querySelectorAll("#ratingContainer button");

buttons.forEach((button, index) => {
    button.addEventListener("mouseover", function () {
        buttons.forEach((btn, i) => {
            btn.classList.toggle("active", i <= index);
            });
        });

        button.addEventListener("mouseleave", function () {
            buttons.forEach(btn => btn.classList.remove("active"));
        });
    });
});