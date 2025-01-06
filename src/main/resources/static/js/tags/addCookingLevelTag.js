document.addEventListener('DOMContentLoaded', function () {
    const cookingLevelList = document.querySelector('#cookingLevelList');
    const addCookingLevelBtn = document.querySelector('#addCookingLevelBtn');

    addCookingLevelBtn.addEventListener('click', function () {
        addCookingLevelField();
    });

    function addCookingLevelField() {
        const cookingLevelDiv = document.createElement('div');
        cookingLevelDiv.classList.add('cooking-level-entry');

        const cookingLevelSelect = document.createElement('select');
        cookingLevelSelect.setAttribute('name', `cookingLevels[${cookingLevelList.children.length}].cookingLevel`);

        const cookingLevelOptions = [
            { value: '', text: 'Select a cooking level' },
            { value: 'Novice', text: 'Novice' },
            { value: 'Beginner', text: 'Beginner' },
            { value: 'Intermediate', text: 'Intermediate' },
            { value: 'Advanced', text: 'Advanced' },
            { value: 'Expert', text: 'Expert' },
            { value: 'Chef-Level', text: 'Chef-Level' },
            { value: 'Other', text: 'Other' }
        ];

        cookingLevelOptions.forEach(option => {
            const opt = document.createElement('option');
            opt.value = option.value;
            opt.textContent = option.text;
            cookingLevelSelect.appendChild(opt);
        });

        const removeButton = document.createElement('button');
        removeButton.textContent = 'Remove';
        removeButton.setAttribute('type', 'button');
        removeButton.addEventListener('click', function () {
            cookingLevelDiv.remove();
        });

        cookingLevelDiv.appendChild(cookingLevelSelect);
        cookingLevelDiv.appendChild(removeButton);
        cookingLevelList.appendChild(cookingLevelDiv);
    }
});
