document.getElementById("file").addEventListener("change", function () {
    let fileName = this.files.length > 0 ? this.files[0].name : "Select Image";
    document.getElementById("file-label").textContent = "Current Image: " + fileName;
});