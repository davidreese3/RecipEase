document.addEventListener('DOMContentLoaded', function () {
    const mealTypeList = document.querySelector('#mealTypeList'); // Reference the mealTypeList div
    const addButton = document.querySelector('#addMealTypeBtn'); // Reference the Add Meal Type button

    addButton.addEventListener('click', function () {
        addMealTypeField();
    });

    function addMealTypeField() {
        // Create a container for this meal type's inputs
        const mealTypeDiv = document.createElement('div');
        mealTypeDiv.classList.add('meal-type-entry');

        // Input for meal type
        const componentInput = document.createElement('input');
        componentInput.setAttribute('type', 'text');
        componentInput.setAttribute('name', `mealTypes[${mealTypeList.children.length}].mealType`);
        componentInput.setAttribute('placeholder', 'Meal Type');
        componentInput.required = false;

        // Remove button for this meal type
        const removeButton = document.createElement('button');
        removeButton.textContent = 'Remove';
        removeButton.setAttribute('type', 'button');
        removeButton.addEventListener('click', function () {
            mealTypeDiv.remove();
        });

        // Append all inputs and the remove button to the meal type div
        mealTypeDiv.appendChild(componentInput);
        mealTypeDiv.appendChild(removeButton);

        // Append the meal type div to the mealTypeList
        mealTypeList.appendChild(mealTypeDiv);
    }
});
