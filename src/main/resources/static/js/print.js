document.addEventListener("DOMContentLoaded", function () {

    document.getElementById("printWithImages").addEventListener("click", function () {
        addPrintHeader();
        window.print();
        removePrintHeader();
    });

    document.getElementById("printWithoutImages").addEventListener("click", function () {
        addPrintHeader();
        document.body.classList.add("hideImages");
        window.print();

        setTimeout(() => {
            document.body.classList.remove("hideImages");
            removePrintHeader();
        }, 500);
    });

    function addPrintHeader() {
        let header = document.createElement("h1");
        header.id = "print-header";
        header.innerText = "Recipe from RecipEase";
        document.body.prepend(header);
    }

    function removePrintHeader() {
        let header = document.getElementById("print-header");
        if (header) {
            header.remove();
        }
    }
});
