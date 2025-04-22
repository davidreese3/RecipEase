document.addEventListener("DOMContentLoaded", function () {

    document.getElementById("printWithImages").addEventListener("click", function () {
        window.print();
    });

    document.getElementById("printWithoutImages").addEventListener("click", function () {
        document.body.classList.add("hide");
        window.print();

        setTimeout(() => {
            document.body.classList.remove("hide");
        }, 500);
    });
});
