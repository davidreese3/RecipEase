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

        // Input for component (ingredient name)
        const componentInput = document.createElement('input');
        componentInput.setAttribute('type', 'text');
        componentInput.setAttribute('name', `ingredients[${ingredientList.children.length}].component`);
        componentInput.setAttribute('placeholder', 'Ingredient Name');
        componentInput.required = true;

        // Input for whole number quantity
        const wholeNumberInput = document.createElement('input');
        wholeNumberInput.setAttribute('type', 'number');
        wholeNumberInput.setAttribute('min', '0');
        wholeNumberInput.setAttribute('name', `ingredients[${ingredientList.children.length}].wholeNumberQuantity`);
        wholeNumberInput.setAttribute('placeholder', 'Whole Number Quantity');

        // Dropdown for fractional quantity
        const fractionSelect = document.createElement('select');
        fractionSelect.setAttribute('name', `ingredients[${ingredientList.children.length}].fractionQuantity`);

        // Fraction options
        const fractionOptions = [
            { value: '0', text: 'Fractional Quantity (if needed)' },
            { value: '0', text: 'No fraction' },
            { value: '1/8', text: '1/8' },
            { value: '1/4', text: '1/4' },
            { value: '3/8', text: '3/8' },
            { value: '1/3', text: '1/3' },
            { value: '1/2', text: '1/2' },
            { value: '5/8', text: '5/8' },
            { value: '2/3', text: '2/3' },
            { value: '3/4', text: '3/4' },
            { value: '1', text: '1' }
        ];

        fractionOptions.forEach(optionData => {
            const option = document.createElement('option');
            option.value = optionData.value;
            option.textContent = optionData.text;
            fractionSelect.appendChild(option);
        });

        // Dropdown for measurement
        const measurementSelect = document.createElement('select');
        measurementSelect.setAttribute('name', `ingredients[${ingredientList.children.length}].measurement`);
        measurementSelect.required = true;

        const measurementOptions = [
            { value: '', text: 'Measurement' },
            { value: 'Cups', text: 'Cups' },
            { value: 'Dashes', text: 'Dashes' },
            { value: 'FluidOunces', text: 'Fluid Ounces (fl oz)' },
            { value: 'Grams', text: 'Grams (g)' },
            { value: 'Kilograms', text: 'Kilograms (kg)' },
            { value: 'Liters', text: 'Liters (L)' },
            { value: 'Milliliters', text: 'Milliliters (ml)' },
            { value: 'Ounces', text: 'Ounces (oz)' },
            { value: 'Pinches', text: 'Pinches' },
            { value: 'Pounds', text: 'Pounds (lb)' },
            { value: 'Tablespoons', text: 'Tablespoons (tbsp)' },
            { value: 'Teaspoons', text: 'Teaspoons (tsp)' },
            { value: 'Whole', text: 'Whole Ingredient' }
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
            { value: '', text: 'Preparation' },
            { value: 'Beaten', text: 'Beaten' },
            { value: 'Boiled', text: 'Boiled' },
            { value: 'Chopped', text: 'Chopped' },
            { value: 'Crushed', text: 'Crushed' },
            { value: 'Cubed', text: 'Cubed' },
            { value: 'Diced', text: 'Diced' },
            { value: 'Grated', text: 'Grated' },
            { value: 'Ground', text: 'Ground' },
            { value: 'Julienned', text: 'Julienned' },
            { value: 'Mashed', text: 'Mashed' },
            { value: 'Melted', text: 'Melted' },
            { value: 'Minced', text: 'Minced' },
            { value: 'Peeled', text: 'Peeled' },
            { value: 'Roasted', text: 'Roasted' },
            { value: 'Shaken', text: 'Shaken' },
            { value: 'Sliced', text: 'Sliced' },
            { value: 'Stirred', text: 'Stirred' },
            { value: 'Toasted', text: 'Toasted' },
            { value: 'Whisked', text: 'Whisked' },
            { value: 'Whole', text: 'Whole Ingredient' },
            { value: 'Zested', text: 'Zested' }
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
        ingredientDiv.appendChild(wholeNumberInput);
        ingredientDiv.appendChild(fractionSelect);
        ingredientDiv.appendChild(measurementSelect);
        ingredientDiv.appendChild(preparationSelect);
        ingredientDiv.appendChild(removeButton);

        // Append the ingredient div to the ingredientList
        ingredientList.appendChild(ingredientDiv);
    }
});
