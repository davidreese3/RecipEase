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
        wholeNumberInput.setAttribute('placeholder', 'Enter Number Quantity');

        // Dropdown for fractional quantity
        const fractionSelect = document.createElement('select');
        fractionSelect.setAttribute('name', `ingredients[${ingredientList.children.length}].fractionQuantity`);

        // Fraction options
        const fractionOptions = [
            { value: '0', text: 'Select a fractional quantity (if needed)' },
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
            { value: '', text: 'Select a measurement' },
            { value: 'Teaspoons', text: 'Teaspoons (tsp)' },
            { value: 'Tablespoons', text: 'Tablespoons (tbsp)' },
            { value: 'Cups', text: 'Cups' },
            { value: 'Milliliters', text: 'Milliliters (ml)' },
            { value: 'Liters', text: 'Liters (L)' },
            { value: 'FluidOunces', text: 'Fluid Ounces (fl oz)' },
            { value: 'Ounces', text: 'Ounces (oz)' },
            { value: 'Pounds', text: 'Pounds (lb)' },
            { value: 'Grams', text: 'Grams (g)' },
            { value: 'Kilograms', text: 'Kilograms (kg)' },
            { value: 'Pinches', text: 'Pinches' },
            { value: 'Dashes', text: 'Dashes' }
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
            { value: 'Diced', text: 'Diced' },
            { value: 'Chopped', text: 'Chopped' },
            { value: 'Minced', text: 'Minced' },
            { value: 'Sliced', text: 'Sliced' },
            { value: 'Julienned', text: 'Julienned' },
            { value: 'Cubed', text: 'Cubed' },
            { value: 'Crushed', text: 'Crushed' },
            { value: 'Ground', text: 'Ground' },
            { value: 'Mashed', text: 'Mashed' },
            { value: 'Shaken', text: 'Shaken' },
            { value: 'Stirred', text: 'Stirred' },
            { value: 'Whisked', text: 'Whisked' },
            { value: 'Beaten', text: 'Beaten' },
            { value: 'Peeled', text: 'Peeled' },
            { value: 'Grated', text: 'Grated' },
            { value: 'Zested', text: 'Zested' },
            { value: 'Whole', text: 'Whole' },
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
        ingredientDiv.appendChild(wholeNumberInput);
        ingredientDiv.appendChild(fractionSelect);
        ingredientDiv.appendChild(measurementSelect);
        ingredientDiv.appendChild(preparationSelect);
        ingredientDiv.appendChild(removeButton);

        // Append the ingredient div to the ingredientList
        ingredientList.appendChild(ingredientDiv);
    }
});
