document.addEventListener('DOMContentLoaded', function () {
    const dietTypeList = document.querySelector('#dietTypeList'); // Reference the dietTypeList div
    const addButton = document.querySelector('#addDietTypeBtn'); // Reference the Add Diet Type button

    addButton.addEventListener('click', function () {
        addDietTypeField();
    });

    function addDietTypeField() {
        // Create a container for this diet type's inputs
        const dietTypeDiv = document.createElement('div');
        dietTypeDiv.classList.add('diet-type-entry');

        // Input for diet type
        const componentInput = document.createElement('input');
        componentInput.setAttribute('type', 'text');
        componentInput.setAttribute('name', `dietTypes[${dietTypeList.children.length}].dietType`);
        componentInput.setAttribute('placeholder', 'Diet Type');
        componentInput.required = false;

        // Remove button for this diet type
        const removeButton = document.createElement('button');
        removeButton.textContent = 'Remove';
        removeButton.setAttribute('type', 'button');
        removeButton.addEventListener('click', function () {
            dietTypeDiv.remove();
        });

        // Append all inputs and the remove button to the diet type div
        dietTypeDiv.appendChild(componentInput);
        dietTypeDiv.appendChild(removeButton);

        // Append the diet type div to the dietTypeList
        dietTypeList.appendChild(dietTypeDiv);
    }
});
