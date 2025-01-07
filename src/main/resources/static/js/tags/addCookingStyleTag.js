document.addEventListener('DOMContentLoaded', function () {
    const cookingStyleList = document.querySelector('#cookingStyleList');
    const addCookingStyleBtn = document.querySelector('#addCookingStyleBtn');

    addCookingStyleBtn.addEventListener('click', function () {
        addCookingStyleField();
    });

    function addCookingStyleField() {
        const cookingStyleDiv = document.createElement('div');
        cookingStyleDiv.classList.add('cooking-style-entry');

        const cookingStyleSelect = document.createElement('select');
        cookingStyleSelect.setAttribute('name', `cookingStyles[${cookingStyleList.children.length}].field`);

        const cookingStyleOptions = [
            { value: '', text: 'Select a cooking style' },
            { value: '30-Minute Meals', text: '30-Minute Meals' },
            { value: 'Air Fryer Recipes', text: 'Air Fryer Recipes' },
            { value: 'Baking', text: 'Baking' },
            { value: 'Comfort Food', text: 'Comfort Food' },
            { value: 'Freezer-Friendly', text: 'Freezer-Friendly' },
            { value: 'Grilled Recipes', text: 'Grilled Recipes' },
            { value: 'Healthy', text: 'Healthy' },
            { value: 'Kid-Friendly', text: 'Kid-Friendly' },
            { value: 'Make-Ahead Recipes', text: 'Make-Ahead Recipes' },
            { value: 'Meal Prep Friendly', text: 'Meal Prep Friendly' },
            { value: 'No-Bake Recipes', text: 'No-Bake Recipes' },
            { value: 'One-Pot Recipes', text: 'One-Pot Recipes' },
            { value: 'Party Food', text: 'Party Food' },
            { value: 'Quick & Easy', text: 'Quick & Easy' },
            { value: 'Sheet Pan Meals', text: 'Sheet Pan Meals' },
            { value: 'Slow Cooker Recipes', text: 'Slow Cooker Recipes' },
            { value: 'Other', text: 'Other' }
        ];

        cookingStyleOptions.forEach(option => {
            const opt = document.createElement('option');
            opt.value = option.value;
            opt.textContent = option.text;
            cookingStyleSelect.appendChild(opt);
        });

        const removeButton = document.createElement('button');
        removeButton.textContent = 'Remove';
        removeButton.setAttribute('type', 'button');
        removeButton.addEventListener('click', function () {
            cookingStyleDiv.remove();
        });

        cookingStyleDiv.appendChild(cookingStyleSelect);
        cookingStyleDiv.appendChild(removeButton);
        cookingStyleList.appendChild(cookingStyleDiv);
    }
});
