document.addEventListener('DOMContentLoaded', function () {
    const ingredientList = document.querySelector('#ingredientList'); // Reference the ingredientList div
    const addButton = document.querySelector('#addIngredientBtn'); // Reference the Add Ingredient button

    // Add event listener to the button
    addButton.addEventListener('click', function () {
        addIngredientFields();
    });

    function addIngredientFields() {
        // Create a container for this ingredient's inputs
        const ingredientDiv = document.createElement('div');
        ingredientDiv.classList.add('ingredient-entry');

        // Input for component
        const componentInput = document.createElement('input');
        componentInput.setAttribute('type', 'text');
        componentInput.setAttribute('name', `ingredients[${ingredientList.children.length}].component`);
        componentInput.setAttribute('placeholder', 'Ingredient Name');
        componentInput.required = true;

        // Input for quantity
        const quantityInput = document.createElement('input');
        quantityInput.setAttribute('type', 'number');
        quantityInput.setAttribute('name', `ingredients[${ingredientList.children.length}].quantity`);
        quantityInput.setAttribute('step', '.1');
        quantityInput.setAttribute('min', '0');
        quantityInput.setAttribute('placeholder', 'Quantity');
        quantityInput.required = true;

        // Dropdown for measurement
        const measurementSelect = document.createElement('select');
        measurementSelect.setAttribute('name', `ingredients[${ingredientList.children.length}].measurement`);
        measurementSelect.required = true;

        const measurementOptions = [
            { value: '', text: 'Select a measurement' },
            // Volume
            { value: 'Teaspoon', text: 'Teaspoon (tsp)' },
            { value: 'Tablespoon', text: 'Tablespoon (tbsp)' },
            { value: 'Cup', text: 'Cup' },
            { value: 'Milliliter', text: 'Milliliter (ml)' },
            { value: 'Liter', text: 'Liter (L)' },
            { value: 'FluidOunce', text: 'Fluid Ounce (fl oz)' },
            // Weight
            { value: 'Ounce', text: 'Ounce (oz)' },
            { value: 'Pound', text: 'Pound (lb)' },
            { value: 'Gram', text: 'Gram (g)' },
            { value: 'Kilogram', text: 'Kilogram (kg)' },
            // Other
            { value: 'Pinch', text: 'Pinch' },
            { value: 'Dash', text: 'Dash' },
            { value: 'Whole', text: 'Whole' }
        ];

        measurementOptions.forEach(optionData => {
            const option = document.createElement('option');
            option.value = optionData.value;
            option.textContent = optionData.text;
            measurementSelect.appendChild(option);
        });

        // Dropdown for preparation
        const preparationSelect = document.createElement('select');
        preparationSelect.setAttribute('name', `ingredients[${ingredientList.children.length}].preparation`);
        preparationSelect.required = true;

        const preparationOptions = [
            { value: '', text: 'Select preparation' },
            // Cutting
            { value: 'Diced', text: 'Diced' },
            { value: 'Chopped', text: 'Chopped' },
            { value: 'Minced', text: 'Minced' },
            { value: 'Sliced', text: 'Sliced' },
            { value: 'Julienned', text: 'Julienned' },
            { value: 'Cubed', text: 'Cubed' },
            // Grinding & Crushing
            { value: 'Crushed', text: 'Crushed' },
            { value: 'Ground', text: 'Ground' },
            { value: 'Mashed', text: 'Mashed' },
            // Liquids
            { value: 'Shaken', text: 'Shaken' },
            { value: 'Stirred', text: 'Stirred' },
            { value: 'Whisked', text: 'Whisked' },
            { value: 'Beaten', text: 'Beaten' },
            // Other
            { value: 'Peeled', text: 'Peeled' },
            { value: 'Grated', text: 'Grated' },
            { value: 'Zested', text: 'Zested' },
            { value: 'Toasted', text: 'Toasted' },
            { value: 'Boiled', text: 'Boiled' },
            { value: 'Roasted', text: 'Roasted' }
        ];

        preparationOptions.forEach(optionData => {
            const option = document.createElement('option');
            option.value = optionData.value;
            option.textContent = optionData.text;
            preparationSelect.appendChild(option);
        });

        // Remove button for this ingredient
        const removeButton = document.createElement('button');
        removeButton.textContent = 'Remove';
        removeButton.setAttribute('type', 'button');
        removeButton.addEventListener('click', function () {
            ingredientDiv.remove();
        });

        // Append all inputs and the remove button to the ingredient div
        ingredientDiv.appendChild(componentInput);
        ingredientDiv.appendChild(quantityInput);
        ingredientDiv.appendChild(measurementSelect);
        ingredientDiv.appendChild(preparationSelect);
        ingredientDiv.appendChild(removeButton);

        // Append the ingredient div to the ingredientList
        ingredientList.appendChild(ingredientDiv);
    }
});
