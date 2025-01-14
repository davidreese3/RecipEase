document.addEventListener('DOMContentLoaded', function () {
    const holidayList = document.querySelector('#holidayList');
    const addHolidayBtn = document.querySelector('#addHolidayBtn');

    addHolidayBtn.addEventListener('click', function () {
        addHolidayField();
    });

    function addHolidayField() {
        const holidayDiv = document.createElement('div');
        holidayDiv.classList.add('holidayEntry');

        const holidaySelect = document.createElement('select');
        holidaySelect.setAttribute('name', `holidays[${holidayList.children.length}].field`);

        const holidayOptions = [
            { value: '', text: 'Holiday' },
            { value: 'Christmas', text: 'Christmas' },
            { value: 'Diwali', text: 'Diwali' },
            { value: 'Easter', text: 'Easter' },
            { value: 'Father’s Day', text: 'Father’s Day' },
            { value: 'Halloween', text: 'Halloween' },
            { value: 'Hanukkah', text: 'Hanukkah' },
            { value: 'Independence Day', text: 'Independence Day' },
            { value: 'Kwanzaa', text: 'Kwanzaa' },
            { value: 'Lunar New Year', text: 'Lunar New Year' },
            { value: 'Mother’s Day', text: 'Mother’s Day' },
            { value: 'New Year’s Day', text: 'New Year’s Day' },
            { value: 'Ramadan', text: 'Ramadan' },
            { value: 'St. Patrick’s Day', text: 'St. Patrick’s Day' },
            { value: 'Thanksgiving', text: 'Thanksgiving' },
            { value: 'Valentine’s Day', text: 'Valentine’s Day' },
            { value: 'Other', text: 'Other' }
        ];

        holidayOptions.forEach(option => {
            const opt = document.createElement('option');
            opt.value = option.value;
            opt.textContent = option.text;
            holidaySelect.appendChild(opt);
        });

        const removeButton = document.createElement('button');
        removeButton.textContent = 'Remove';
        removeButton.setAttribute('type', 'button');
        removeButton.addEventListener('click', function () {
            holidayDiv.remove();
        });

        holidayDiv.appendChild(holidaySelect);
        holidayDiv.appendChild(removeButton);
        holidayList.appendChild(holidayDiv);
    }
});

document.addEventListener('DOMContentLoaded', function () {
    const mealTypeList = document.querySelector('#mealTypeList');
    const addMealTypeBtn = document.querySelector('#addMealTypeBtn');

    addMealTypeBtn.addEventListener('click', function () {
        addMealTypeField();
    });

    function addMealTypeField() {
        const mealTypeDiv = document.createElement('div');
        mealTypeDiv.classList.add('mealTypeEntry');

        const mealTypeSelect = document.createElement('select');
        mealTypeSelect.setAttribute('name', `mealTypes[${mealTypeList.children.length}].field`);

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

document.addEventListener('DOMContentLoaded', function () {
    const cuisineList = document.querySelector('#cuisineList');
    const addCuisineBtn = document.querySelector('#addCuisineBtn');

    addCuisineBtn.addEventListener('click', function () {
        addCuisineField();
    });

    function addCuisineField() {
        const cuisineDiv = document.createElement('div');
        cuisineDiv.classList.add('cuisineEntry');

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

document.addEventListener('DOMContentLoaded', function () {
    const allergenList = document.querySelector('#allergenList');
    const addAllergenBtn = document.querySelector('#addAllergenBtn');

    addAllergenBtn.addEventListener('click', function () {
        addAllergenField();
    });

    function addAllergenField() {
        const allergenDiv = document.createElement('div');
        allergenDiv.classList.add('allergenEntry');

        const allergenSelect = document.createElement('select');
        allergenSelect.setAttribute('name', `allergens[${allergenList.children.length}].field`);

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

document.addEventListener('DOMContentLoaded', function () {
    const dietTypeList = document.querySelector('#dietTypeList');
    const addDietTypeBtn = document.querySelector('#addDietTypeBtn');

    addDietTypeBtn.addEventListener('click', function () {
        addDietTypeField();
    });

    function addDietTypeField() {
        const dietTypeDiv = document.createElement('div');
        dietTypeDiv.classList.add('dietTypeEntry');

        const dietTypeSelect = document.createElement('select');
        dietTypeSelect.setAttribute('name', `dietTypes[${dietTypeList.children.length}].field`);

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

document.addEventListener('DOMContentLoaded', function () {
    const cookingLevelList = document.querySelector('#cookingLevelList');
    const addCookingLevelBtn = document.querySelector('#addCookingLevelBtn');

    addCookingLevelBtn.addEventListener('click', function () {
        addCookingLevelField();
    });

    function addCookingLevelField() {
        const cookingLevelDiv = document.createElement('div');
        cookingLevelDiv.classList.add('cookingLevelEntry');

        const cookingLevelSelect = document.createElement('select');
        cookingLevelSelect.setAttribute('name', `cookingLevels[${cookingLevelList.children.length}].field`);

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

document.addEventListener('DOMContentLoaded', function () {
    const cookingStyleList = document.querySelector('#cookingStyleList');
    const addCookingStyleBtn = document.querySelector('#addCookingStyleBtn');

    addCookingStyleBtn.addEventListener('click', function () {
        addCookingStyleField();
    });

    function addCookingStyleField() {
        const cookingStyleDiv = document.createElement('div');
        cookingStyleDiv.classList.add('cookingStyleEntry');

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
