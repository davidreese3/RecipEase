document.addEventListener('DOMContentLoaded', function () {
    const cuisineList = document.querySelector('#cuisineList'); // Reference the cuisineList div
    const addButton = document.querySelector('#addCuisineBtn'); // Reference the Add Cuisine button

    addButton.addEventListener('click', function () {
        addCuisineField();
    });

    function addCuisineField() {
        // Create a container for this cuisine's inputs
        const cuisineDiv = document.createElement('div');
        cuisineDiv.classList.add('cuisine-entry');

        // Input for cuisine
        const componentInput = document.createElement('input');
        componentInput.setAttribute('type', 'text');
        componentInput.setAttribute('name', `cuisines[${cuisineList.children.length}].cuisine`);
        componentInput.setAttribute('placeholder', 'Cuisine');
        componentInput.required = false;

        // Remove button for this cuisine
        const removeButton = document.createElement('button');
        removeButton.textContent = 'Remove';
        removeButton.setAttribute('type', 'button');
        removeButton.addEventListener('click', function () {
            cuisineDiv.remove();
        });

        // Append all inputs and the remove button to the cuisine div
        cuisineDiv.appendChild(componentInput);
        cuisineDiv.appendChild(removeButton);

        // Append the cuisine div to the cuisineList
        cuisineList.appendChild(cuisineDiv);
    }
});
