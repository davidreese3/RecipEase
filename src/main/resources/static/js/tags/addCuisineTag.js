document.addEventListener('DOMContentLoaded', function () {
    const cuisineList = document.querySelector('#cuisineList');
    const addCuisineBtn = document.querySelector('#addCuisineBtn');

    addCuisineBtn.addEventListener('click', function () {
        addCuisineField();
    });

    function addCuisineField() {
        const cuisineDiv = document.createElement('div');
        cuisineDiv.classList.add('cuisine-entry');

        const cuisineSelect = document.createElement('select');
        cuisineSelect.setAttribute('name', `cuisines[${cuisineList.children.length}].field`);

        const cuisineOptions = [
            { value: '', text: 'Select a cuisine' },
            { value: 'African', text: 'African' },
            { value: 'American', text: 'American' },
            { value: 'Caribbean', text: 'Caribbean' },
            { value: 'Chinese', text: 'Chinese' },
            { value: 'French', text: 'French' },
            { value: 'Greek', text: 'Greek' },
            { value: 'Indian', text: 'Indian' },
            { value: 'Italian', text: 'Italian' },
            { value: 'Japanese', text: 'Japanese' },
            { value: 'Mediterranean', text: 'Mediterranean' },
            { value: 'Mexican', text: 'Mexican' },
            { value: 'Middle Eastern', text: 'Middle Eastern' },
            { value: 'Spanish', text: 'Spanish' },
            { value: 'Thai', text: 'Thai' },
            { value: 'Vietnamese', text: 'Vietnamese' },
            { value: 'Other', text: 'Other' }
        ];

        cuisineOptions.forEach(option => {
            const opt = document.createElement('option');
            opt.value = option.value;
            opt.textContent = option.text;
            cuisineSelect.appendChild(opt);
        });

        const removeButton = document.createElement('button');
        removeButton.textContent = 'Remove';
        removeButton.setAttribute('type', 'button');
        removeButton.addEventListener('click', function () {
            cuisineDiv.remove();
        });

        cuisineDiv.appendChild(cuisineSelect);
        cuisineDiv.appendChild(removeButton);
        cuisineList.appendChild(cuisineDiv);
    }
});
