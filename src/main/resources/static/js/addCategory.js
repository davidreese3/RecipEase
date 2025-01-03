document.addEventListener('DOMContentLoaded', function () {
    const ingredientList = document.querySelector('#holidayList'); // Reference the ingredientList div
    const addButton = document.querySelector('#addHolidayBtn'); // Reference the Add Ingredient button

    addButton.addEventListener('click', function () {
            addHolidayField();
    });

    function addHolidayField() {
            // Create a container for this ingredient's inputs
            const holidayDiv = document.createElement('div');
            holidayDiv.classList.add('holiday-entry');

            // Input for component (ingredient name)
            const componentInput = document.createElement('input');
            componentInput.setAttribute('type', 'text');
            componentInput.setAttribute('name', `holidays[${holidayList.children.length}].holiday`);
            componentInput.setAttribute('placeholder', 'Holiday');
            componentInput.required = false;

            // Remove button for this ingredient
            const removeButton = document.createElement('button');
            removeButton.textContent = 'Remove';
            removeButton.setAttribute('type', 'button');
            removeButton.addEventListener('click', function () {
                holidayDiv.remove();
            });

            // Append all inputs and the remove button to the ingredient div
            holidayDiv.appendChild(componentInput);
            holidayDiv.appendChild(removeButton);

            // Append the ingredient div to the ingredientList
            holidayList.appendChild(holidayDiv);
        }
    });