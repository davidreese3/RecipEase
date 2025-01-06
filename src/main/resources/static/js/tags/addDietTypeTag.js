document.addEventListener('DOMContentLoaded', function () {
    const dietTypeList = document.querySelector('#dietTypeList');
    const addDietTypeBtn = document.querySelector('#addDietTypeBtn');

    addDietTypeBtn.addEventListener('click', function () {
        addDietTypeField();
    });

    function addDietTypeField() {
        const dietTypeDiv = document.createElement('div');
        dietTypeDiv.classList.add('diet-type-entry');

        const dietTypeSelect = document.createElement('select');
        dietTypeSelect.setAttribute('name', `dietTypes[${dietTypeList.children.length}].dietType`);

        const dietTypeOptions = [
            { value: '', text: 'Select a diet type' },
            { value: 'Keto', text: 'Keto' },
            { value: 'Low-Carb', text: 'Low-Carb' },
            { value: 'Low-Fat', text: 'Low-Fat' },
            { value: 'Paleo', text: 'Paleo' },
            { value: 'Pescatarian', text: 'Pescatarian' },
            { value: 'Vegetarian', text: 'Vegetarian' },
            { value: 'Vegan', text: 'Vegan' },
            { value: 'Other', text: 'Other' }
        ];

        dietTypeOptions.forEach(option => {
            const opt = document.createElement('option');
            opt.value = option.value;
            opt.textContent = option.text;
            dietTypeSelect.appendChild(opt);
        });

        const removeButton = document.createElement('button');
        removeButton.textContent = 'Remove';
        removeButton.setAttribute('type', 'button');
        removeButton.addEventListener('click', function () {
            dietTypeDiv.remove();
        });

        dietTypeDiv.appendChild(dietTypeSelect);
        dietTypeDiv.appendChild(removeButton);
        dietTypeList.appendChild(dietTypeDiv);
    }
});
