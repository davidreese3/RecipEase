document.addEventListener('DOMContentLoaded', function () {
    const directionList = document.querySelector('#directionList'); // Ensure it's queried
    const addButton = document.querySelector('#addDirectionBtn');

    // Reattach remove button functionality to existing rows
    repopulateRemoveButtons();

    if (directionList.children.length === 0) {
            addDirectionFields();
    }

    addButton.addEventListener('click', function () {
        addDirectionFields();
    });

    directionList.addEventListener('click', function (event) {
        if (event.target.classList.contains('removeButton')) {
            event.target.closest('tr').remove();
        }
    });
});

function addDirectionFields() {

    const rowCount = directionList.children.length; // Get current row count
    const row = document.createElement('tr');
    row.classList.add('directionEntry');

    // Textarea for direction step description
    const directionTextarea = document.createElement('textarea');
    directionTextarea.setAttribute('name', `directions[${rowCount}].direction`);
    directionTextarea.required = true;

    // Dropdown for method
    const methodSelect = document.createElement('select');
    methodSelect.setAttribute('name', `directions[${rowCount}].method`);

    const methodOptions = [
        { value: '', text: '' },
        { value: 'Assembling', text: 'Assembling' },
        { value: 'Baking', text: 'Baking' },
        { value: 'Boiling', text: 'Boiling' },
        { value: 'Frying', text: 'Frying' },
        { value: 'Grilling', text: 'Grilling' },
        { value: 'Mixing', text: 'Mixing' },
        { value: 'Roasting', text: 'Roasting' },
        { value: 'Searing', text: 'Searing' },
        { value: 'Simmering', text: 'Simmering' },
        { value: 'Steaming', text: 'Steaming' },
        { value: 'Stirring', text: 'Stirring' },
        { value: 'Whisking', text: 'Whisking' }
    ];

    methodOptions.forEach(optionData => {
        const option = document.createElement('option');
        option.value = optionData.value;
        option.textContent = optionData.text;
        methodSelect.appendChild(option);
    });

    // Input for temperature
    const tempInput = document.createElement('input');
    tempInput.setAttribute('type', 'number');
    tempInput.setAttribute('name', `directions[${rowCount}].temp`);
    tempInput.setAttribute('min', '0');

    // Dropdown for heat level
    const levelSelect = document.createElement('select');
    levelSelect.setAttribute('name', `directions[${rowCount}].level`);

    const levelOptions = [
        { value: '', text: '' },
        { value: 'Low', text: 'Low' },
        { value: 'Medium', text: 'Medium' },
        { value: 'High', text: 'High' }
    ];

    levelOptions.forEach(optionData => {
        const option = document.createElement('option');
        option.value = optionData.value;
        option.textContent = optionData.text;
        levelSelect.appendChild(option);
    });

    // Remove button
    const removeButton = document.createElement('button');
    removeButton.textContent = 'Remove';
    removeButton.setAttribute('type', 'button');
    removeButton.classList.add('removeButton');

    removeButton.addEventListener('click', function () {
        row.remove();
    });

    // Append all elements using createCell()
    row.appendChild(createCell(directionTextarea));
    row.appendChild(createCell(methodSelect));
    row.appendChild(createCell(tempInput));
    row.appendChild(createCell(levelSelect));
    row.appendChild(createCell(removeButton));

    directionList.appendChild(row);
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