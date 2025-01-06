document.addEventListener('DOMContentLoaded', function () {
    const directionList = document.querySelector('#directionList'); // Reference the directionList div
    const addDirectionButton = document.querySelector('#addDirectionBtn'); // Reference the Add Direction button

    // Add event listener to the button
    addDirectionButton.addEventListener('click', function () {
        addDirectionFields();
    });

    function addDirectionFields() {
        // Create a container for this direction's inputs
        const directionDiv = document.createElement('div');
        directionDiv.classList.add('direction-entry');

        // Textarea for direction step description
        const directionTextarea = document.createElement('textarea');
        directionTextarea.setAttribute('name', `directions[${directionList.children.length}].direction`);
        directionTextarea.setAttribute('rows', '2');
        directionTextarea.setAttribute('cols', '50');
        directionTextarea.setAttribute('placeholder', 'Step Description');
        directionTextarea.required = true;

        // Dropdown for method
        const methodSelect = document.createElement('select');
        methodSelect.setAttribute('name', `directions[${directionList.children.length}].method`);

        const methodOptions = [
                { value: '', text: 'Method' },
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
        tempInput.setAttribute('name', `directions[${directionList.children.length}].temp`);
        tempInput.setAttribute('placeholder', 'Temperature (°F)');
        tempInput.setAttribute('min', '0');

        // Dropdown for level
        const levelSelect = document.createElement('select');
        levelSelect.setAttribute('name', `directions[${directionList.children.length}].level`);

        const levelOptions = [
            { value: '', text: 'Heat Level' },
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

        // Remove button for this direction
        const removeButton = document.createElement('button');
        removeButton.textContent = 'Remove';
        removeButton.setAttribute('type', 'button');
        removeButton.addEventListener('click', function () {
            directionDiv.remove();
        });

        // Append all inputs and the remove button to the direction div
        directionDiv.appendChild(directionTextarea);
        directionDiv.appendChild(document.createElement('br'));
        directionDiv.appendChild(methodSelect);
        directionDiv.appendChild(tempInput);
        directionDiv.appendChild(levelSelect);
        directionDiv.appendChild(removeButton);

        // Append the direction div to the directionList
        directionList.appendChild(directionDiv);
    }
});
