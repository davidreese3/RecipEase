document.addEventListener('DOMContentLoaded', function () {
    const cookingLevelList = document.querySelector('#cookingLevelList'); // Reference the cookingLevelList div
    const addButton = document.querySelector('#addCookingLevelBtn'); // Reference the Add Cooking Level button

    addButton.addEventListener('click', function () {
        addCookingLevelField();
    });

    function addCookingLevelField() {
        // Create a container for this cooking level's inputs
        const cookingLevelDiv = document.createElement('div');
        cookingLevelDiv.classList.add('cooking-level-entry');

        // Input for cooking level
        const componentInput = document.createElement('input');
        componentInput.setAttribute('type', 'text');
        componentInput.setAttribute('name', `cookingLevels[${cookingLevelList.children.length}].cookingLevel`);
        componentInput.setAttribute('placeholder', 'Cooking Level');
        componentInput.required = false;

        // Remove button for this cooking level
        const removeButton = document.createElement('button');
        removeButton.textContent = 'Remove';
        removeButton.setAttribute('type', 'button');
        removeButton.addEventListener('click', function () {
            cookingLevelDiv.remove();
        });

        // Append all inputs and the remove button to the cooking level div
        cookingLevelDiv.appendChild(componentInput);
        cookingLevelDiv.appendChild(removeButton);

        // Append the cooking level div to the cookingLevelList
        cookingLevelList.appendChild(cookingLevelDiv);
    }
});
