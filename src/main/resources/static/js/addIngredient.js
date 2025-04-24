document.addEventListener('DOMContentLoaded', function () {
    const ingredientList = document.querySelector('#ingredientList');
    const addButton = document.querySelector('#addIngredientBtn');

    // Reattach remove button functionality to existing rows
    repopulateRemoveButtons();

    if (ingredientList.children.length === 0) {
            addIngredientFields();
    }

    addButton.addEventListener('click', function () {
        addIngredientFields();
    });

    ingredientList.addEventListener('click', function (event) {
        if (event.target.classList.contains('removeButton')) {
            event.target.closest('tr').remove();
        }
    });
});

function addIngredientFields() {
    const rowCount = ingredientList.children.length;
    const tableRow = document.createElement('tr');

    const componentInput = document.createElement('input');
    componentInput.setAttribute('type', 'text');
    componentInput.setAttribute('name', `ingredients[${rowCount}].component`);
    componentInput.setAttribute('maxlength', '45');
    componentInput.required = true;

    const quantity = document.createElement('input');
    quantity.setAttribute('type', 'text');
    quantity.setAttribute('name', `ingredients[${rowCount}].quantity`);
    quantity.setAttribute('maxlength', '45');

    const measurementSelect = document.createElement('select');
    measurementSelect.setAttribute('name', `ingredients[${rowCount}].measurement`);
    measurementSelect.required = true;
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
    measurementOptions.forEach(optionData => {
        const option = document.createElement('option');
        option.value = optionData.value;
        option.textContent = optionData.text;
        measurementSelect.appendChild(option);
    });

    const preparationSelect = document.createElement('select');
    preparationSelect.setAttribute('name', `ingredients[${rowCount}].preparation`);
    preparationSelect.required = true;
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
        { value: 'Juiced', text: 'Juiced' },
        { value: 'Julienned', text: 'Julienned' },
        { value: 'Mashed', text: 'Mashed' },
        { value: 'Melted', text: 'Melted' },
        { value: 'Minced', text: 'Minced' },
        { value: 'Peeled', text: 'Peeled' },
        { value: 'Roasted', text: 'Roasted' },
        { value: 'Shaken', text: 'Shaken' },
        { value: 'Sifted', text: 'Sifted' },
        { value: 'Sliced', text: 'Sliced' },
        { value: 'Stirred', text: 'Stirred' },
        { value: 'Toasted', text: 'Toasted' },
        { value: 'Unprepared', text: 'Unprepared' },
        { value: 'Whisked', text: 'Whisked' },
        { value: 'Zested', text: 'Zested' }
    ];
    preparationOptions.forEach(optionData => {
        const option = document.createElement('option');
        option.value = optionData.value;
        option.textContent = optionData.text;
        preparationSelect.appendChild(option);
    });

    const removeButton = document.createElement('button');
    removeButton.textContent = 'Remove';
    removeButton.setAttribute('type', 'button');
    removeButton.classList.add('removeButton');
    removeButton.addEventListener('click', function () {
        tableRow.remove();
    });

    tableRow.appendChild(createCell(componentInput));
    tableRow.appendChild(createCell(quantity));
    tableRow.appendChild(createCell(measurementSelect));
    tableRow.appendChild(createCell(preparationSelect));
    tableRow.appendChild(createCell(removeButton));

    ingredientList.appendChild(tableRow);
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
