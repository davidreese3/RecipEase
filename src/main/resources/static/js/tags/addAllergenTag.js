document.addEventListener('DOMContentLoaded', function () {
    const allergenList = document.querySelector('#allergenList'); // Reference the allergenList div
    const addButton = document.querySelector('#addAllergenBtn'); // Reference the Add Allergen button

    addButton.addEventListener('click', function () {
        addAllergenField();
    });

    function addAllergenField() {
        // Create a container for this allergen's inputs
        const allergenDiv = document.createElement('div');
        allergenDiv.classList.add('allergen-entry');

        // Input for allergen
        const componentInput = document.createElement('input');
        componentInput.setAttribute('type', 'text');
        componentInput.setAttribute('name', `allergens[${allergenList.children.length}].allergen`);
        componentInput.setAttribute('placeholder', 'Allergen');
        componentInput.required = false;

        // Remove button for this allergen
        const removeButton = document.createElement('button');
        removeButton.textContent = 'Remove';
        removeButton.setAttribute('type', 'button');
        removeButton.addEventListener('click', function () {
            allergenDiv.remove();
        });

        // Append all inputs and the remove button to the allergen div
        allergenDiv.appendChild(componentInput);
        allergenDiv.appendChild(removeButton);

        // Append the allergen div to the allergenList
        allergenList.appendChild(allergenDiv);
    }
});
