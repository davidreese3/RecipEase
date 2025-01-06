document.addEventListener('DOMContentLoaded', function () {
    const mealTypeList = document.querySelector('#mealTypeList');
    const addMealTypeBtn = document.querySelector('#addMealTypeBtn');

    addMealTypeBtn.addEventListener('click', function () {
        addMealTypeField();
    });

    function addMealTypeField() {
        const mealTypeDiv = document.createElement('div');
        mealTypeDiv.classList.add('meal-type-entry');

        const mealTypeSelect = document.createElement('select');
        mealTypeSelect.setAttribute('name', `mealTypes[${mealTypeList.children.length}].mealType`);

        const mealTypeOptions = [
            { value: '', text: 'Meal type' },
            { value: 'Appetizer', text: 'Appetizer' },
            { value: 'Breakfast', text: 'Breakfast' },
            { value: 'Brunch', text: 'Brunch' },
            { value: 'Dessert', text: 'Dessert' },
            { value: 'Dinner', text: 'Dinner' },
            { value: 'Lunch', text: 'Lunch' },
            { value: 'Snack', text: 'Snack' },
            { value: 'Other', text: 'Other' }
        ];

        mealTypeOptions.forEach(option => {
            const opt = document.createElement('option');
            opt.value = option.value;
            opt.textContent = option.text;
            mealTypeSelect.appendChild(opt);
        });

        const removeButton = document.createElement('button');
        removeButton.textContent = 'Remove';
        removeButton.setAttribute('type', 'button');
        removeButton.addEventListener('click', function () {
            mealTypeDiv.remove();
        });

        mealTypeDiv.appendChild(mealTypeSelect);
        mealTypeDiv.appendChild(removeButton);
        mealTypeList.appendChild(mealTypeDiv);
    }
});
