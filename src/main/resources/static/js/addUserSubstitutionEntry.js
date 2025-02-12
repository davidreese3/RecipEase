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
        { value: '', text: '' },
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
        { value: '', text: '' },
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
    const originalLabel = createLabel('Original: ');
    const originalComponentInput = createInput('text', `userSubstitutionEntries[${userSubList.children.length}].originalComponent`, 45, true);
    const originalQuantityInput = createInput('text', `userSubstitutionEntries[${userSubList.children.length}].originalQuantity`, null, false);
    const originalMeasurementSelect = createSelect(`userSubstitutionEntries[${userSubList.children.length}].originalMeasurement`, measurementOptions, true);
    const originalPreparationSelect = createSelect(`userSubstitutionEntries[${userSubList.children.length}].originalPreparation`, preparationOptions, true);

    // Substituted ingredient fields
    const substitutedLabel = createLabel('Substituted: ')
    const substitutedComponentInput = createInput('text', `userSubstitutionEntries[${userSubList.children.length}].substitutedComponent`, 45, true);
    const substitutedQuantityInput = createInput('text', `userSubstitutionEntries[${userSubList.children.length}].substitutedQuantity`, null, false);
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
    row.appendChild(createCellWithBr(originalLabel, substitutedLabel));
    row.appendChild(createCellWithBr(originalComponentInput, substitutedComponentInput));
    row.appendChild(createCellWithBr(originalQuantityInput, substitutedQuantityInput));
    row.appendChild(createCellWithBr(originalMeasurementSelect, substitutedMeasurementSelect));
    row.appendChild(createCellWithBr(originalPreparationSelect, substitutedPreparationSelect));
    row.appendChild(removeButtonCell);

    // Append the row to the userSubList table
    userSubList.appendChild(row);
}

function createLabel(text) {
    const label = document.createElement('label');
    label.textContent = text;
    return label;
}

function createInput(type, name, maxLength, required) {
    const input = document.createElement('input');
    input.setAttribute('type', type);
    input.setAttribute('name', name);
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

function createCellWithBr(originalElement, substitutedElement) {
    const cell = document.createElement('td');
    cell.appendChild(originalElement);
    cell.appendChild(document.createElement('br'));
    cell.appendChild(substitutedElement);
    return cell;
}

function repopulateRemoveButtons() {
    document.querySelectorAll('.removeButton').forEach(button => {
        button.addEventListener('click', function () {
            this.closest('tr').remove();
        });
    });
}
