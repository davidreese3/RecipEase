document.addEventListener('DOMContentLoaded', function () {
    const userSubList = document.querySelector('#userSubList');
    const addUserSubBtn = document.querySelector('#addUserSubBtn');

    // Reattach remove button functionality to existing rows
    repopulateRemoveButtons();

    addUserSubBtn.addEventListener('click', function () {
        addSubstitutionFields(measurementOptions, preparationOptions);
    });

    userSubList.addEventListener('click', function (event) {
        if (event.target.classList.contains('removeButton')) {
            event.target.closest('tr').remove();
        }
    });

    const measurementOptions = [
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
        { value: 'Unprepared', text: 'Unprepared' },
        { value: 'Whisked', text: 'Whisked' },
        { value: 'Zested', text: 'Zested' }
    ];
});

function addSubstitutionFields(measurementOptions, preparationOptions) {
    const row = document.createElement('tr');
    row.classList.add('substitutionEntry');

    // Original ingredient fields
    const originalComponentInput = createInput('text', `userSubstitutionEntries[${userSubList.children.length}].originalComponent`, 'Original Ingredient Name', 45, true);
    const originalQuantityInput = createInput('text', `userSubstitutionEntries[${userSubList.children.length}].originalQuantity`, 'Original Quantity', null, false);
    const originalMeasurementSelect = createSelect(`userSubstitutionEntries[${userSubList.children.length}].originalMeasurement`, measurementOptions, true);
    const originalPreparationSelect = createSelect(`userSubstitutionEntries[${userSubList.children.length}].originalPreparation`, preparationOptions, true);

    // Substituted ingredient fields
    const substitutedComponentInput = createInput('text', `userSubstitutionEntries[${userSubList.children.length}].substitutedComponent`, 'Substituted Ingredient Name', 45, true);
    const substitutedQuantityInput = createInput('text', `userSubstitutionEntries[${userSubList.children.length}].substitutedQuantity`, 'Substituted Quantity', null, false);
    const substitutedMeasurementSelect = createSelect(`userSubstitutionEntries[${userSubList.children.length}].substitutedMeasurement`, measurementOptions, true);
    const substitutedPreparationSelect = createSelect(`userSubstitutionEntries[${userSubList.children.length}].substitutedPreparation`, preparationOptions, true);

    // Remove button
    const removeButtonCell = document.createElement('td');
    const removeButton = document.createElement('button');
    removeButton.textContent = 'Remove';
    removeButton.setAttribute('type', 'button');
    removeButton.addEventListener('click', function () {
        row.remove();
    });

    removeButtonCell.appendChild(removeButton);

    // Append fields to row
    row.appendChild(createCell(originalComponentInput));
    row.appendChild(createCell(originalQuantityInput));
    row.appendChild(createCell(originalMeasurementSelect));
    row.appendChild(createCell(originalPreparationSelect));
    row.appendChild(createCell(substitutedComponentInput));
    row.appendChild(createCell(substitutedQuantityInput));
    row.appendChild(createCell(substitutedMeasurementSelect));
    row.appendChild(createCell(substitutedPreparationSelect));
    row.appendChild(removeButtonCell);

    // Append the row to the userSubList table
    userSubList.appendChild(row);
}

function createInput(type, name, placeholder, maxLength, required) {
    const input = document.createElement('input');
    input.setAttribute('type', type);
    input.setAttribute('name', name);
    input.setAttribute('placeholder', placeholder);
    if (maxLength) input.setAttribute('maxlength', maxLength);
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

function createCell(element) {
    const cell = document.createElement('td');
    cell.appendChild(element);
    return cell;
}

function repopulateRemoveButtons() {
    document.querySelectorAll('.removeButton').forEach(button => {
        button.addEventListener('click', function () {
            this.closest('tr').remove();
        });
    });
}
