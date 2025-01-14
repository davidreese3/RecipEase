document.addEventListener('DOMContentLoaded', function () {
    const userSubList = document.querySelector('#userSubList'); // Reference the userSubList div
    const addUserSubBtn = document.querySelector('#addUserSubBtn'); // Reference the Add Substitution button

    // Add event listener to the button
    addUserSubBtn.addEventListener('click', function () {
        addSubstitutionFields();
    });

    function addSubstitutionFields() {
        // Create a container for this substitution's inputs
        const subDiv = document.createElement('div');
        subDiv.classList.add('substitutionEntry');

        // Inputs and select elements for original ingredient
        const originalComponentInput = createInput('text', `userSubstitutionEntries[${userSubList.children.length}].originalComponent`, 'Original Ingredient Name', 45, true);
        const originalWholeNumberInput = createInput('number', `userSubstitutionEntries[${userSubList.children.length}].originalWholeNumberQuantity`, 'Original Whole Number Quantity', null, false, 0);
        const originalFractionSelect = createSelect(`userSubstitutionEntries[${userSubList.children.length}].originalFractionQuantity`, fractionOptions());
        const originalMeasurementSelect = createSelect(`userSubstitutionEntries[${userSubList.children.length}].originalMeasurement`, measurementOptions(), true);
        const originalPreparationSelect = createSelect(`userSubstitutionEntries[${userSubList.children.length}].originalPreparation`, preparationOptions(), true);

        // Inputs and select elements for substituted ingredient
        const substitutedComponentInput = createInput('text', `userSubstitutionEntries[${userSubList.children.length}].substitutedComponent`, 'Substituted Ingredient Name', 45, true);
        const substitutedWholeNumberInput = createInput('number', `userSubstitutionEntries[${userSubList.children.length}].substitutedWholeNumberQuantity`, 'Substituted Whole Number Quantity', null, false, 0);
        const substitutedFractionSelect = createSelect(`userSubstitutionEntries[${userSubList.children.length}].substitutedFractionQuantity`, fractionOptions());
        const substitutedMeasurementSelect = createSelect(`userSubstitutionEntries[${userSubList.children.length}].substitutedMeasurement`, measurementOptions(), true);
        const substitutedPreparationSelect = createSelect(`userSubstitutionEntries[${userSubList.children.length}].substitutedPreparation`, preparationOptions(), true);

        // Remove button for this substitution
        const removeButton = document.createElement('button');
        removeButton.textContent = 'Remove';
        removeButton.setAttribute('type', 'button');
        removeButton.addEventListener('click', function () {
            subDiv.remove();
        });

        // Append all inputs and the remove button to the subDiv
        subDiv.appendChild(originalComponentInput);
        subDiv.appendChild(originalWholeNumberInput);
        subDiv.appendChild(originalFractionSelect);
        subDiv.appendChild(originalMeasurementSelect);
        subDiv.appendChild(originalPreparationSelect);
        subDiv.appendChild(substitutedComponentInput);
        subDiv.appendChild(substitutedWholeNumberInput);
        subDiv.appendChild(substitutedFractionSelect);
        subDiv.appendChild(substitutedMeasurementSelect);
        subDiv.appendChild(substitutedPreparationSelect);
        subDiv.appendChild(removeButton);

        // Append the subDiv to the userSubList
        userSubList.appendChild(subDiv);
    }

    function createInput(type, name, placeholder, maxLength, required, min = null) {
        const input = document.createElement('input');
        input.setAttribute('type', type);
        input.setAttribute('name', name);
        input.setAttribute('placeholder', placeholder);
        if (maxLength) input.setAttribute('maxlength', maxLength);
        if (min !== null) input.setAttribute('min', min);
        if (required) input.required = true;
        return input;
    }

    function createSelect(name, options, required = false) {
        const select = document.createElement('select');
        select.setAttribute('name', name);
        if (required) select.required = true;
        options.forEach(optionData => {
            const option = document.createElement('option');
            option.value = optionData.value;
            option.textContent = optionData.text;
            select.appendChild(option);
        });
        return select;
    }

    function fractionOptions() {
        return [
            { value: '0', text: 'Fractional Quantity (if needed)' },
            { value: '0', text: 'No fraction' },
            { value: '1/8', text: '1/8' },
            { value: '1/4', text: '1/4' },
            { value: '3/8', text: '3/8' },
            { value: '1/3', text: '1/3' },
            { value: '1/2', text: '1/2' },
            { value: '5/8', text: '5/8' },
            { value: '2/3', text: '2/3' },
            { value: '3/4', text: '3/4' }
        ];
    }

    function measurementOptions() {
        return [
            { value: '', text: 'Measurement' },
            { value: 'Cups', text: 'Cups' },
            { value: 'Dashes', text: 'Dashes' },
            { value: 'Fluid Ounces', text: 'Fluid Ounces (fl oz)' },
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
    }

    function preparationOptions() {
        return [
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
            { value: 'Unprepared', text: 'Unprepared' },
            { value: 'Whisked', text: 'Whisked' },
            { value: 'Zested', text: 'Zested' }
        ];
    }
});
