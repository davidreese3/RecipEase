document.addEventListener('DOMContentLoaded', function () {
    const allergenList = document.querySelector('#allergenList');
    const addAllergenBtn = document.querySelector('#addAllergenBtn');

    addAllergenBtn.addEventListener('click', function () {
        addAllergenField();
    });

    function addAllergenField() {
        const allergenDiv = document.createElement('div');
        allergenDiv.classList.add('allergen-entry');

        const allergenSelect = document.createElement('select');
        allergenSelect.setAttribute('name', `allergens[${allergenList.children.length}].allergen`);

        const allergenOptions = [
            { value: '', text: 'Select an allergy' },
            { value: 'Dairy-Free', text: 'Dairy-Free' },
            { value: 'Egg-Free', text: 'Egg-Free' },
            { value: 'Fish-Free', text: 'Fish-Free' },
            { value: 'Gluten-Free', text: 'Gluten-Free' },
            { value: 'Peanut-Free', text: 'Peanut-Free' },
            { value: 'Sesame-Free', text: 'Sesame-Free' },
            { value: 'Shellfish-Free', text: 'Shellfish-Free' },
            { value: 'Soy-Free', text: 'Soy-Free' },
            { value: 'Tree Nut-Free', text: 'Tree Nut-Free' },
            { value: 'Other', text: 'Other' }
        ];

        allergenOptions.forEach(option => {
            const opt = document.createElement('option');
            opt.value = option.value;
            opt.textContent = option.text;
            allergenSelect.appendChild(opt);
        });

        const removeButton = document.createElement('button');
        removeButton.textContent = 'Remove';
        removeButton.setAttribute('type', 'button');
        removeButton.addEventListener('click', function () {
            allergenDiv.remove();
        });

        allergenDiv.appendChild(allergenSelect);
        allergenDiv.appendChild(removeButton);
        allergenList.appendChild(allergenDiv);
    }
});
