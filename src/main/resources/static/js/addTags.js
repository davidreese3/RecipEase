document.addEventListener('DOMContentLoaded', function () {
    setupDynamicFields('holidayList', 'addHolidayBtn', 'holidays', [
        { value: '', text: 'Holiday' },
        { value: 'Birthday', text: 'Birthday' },
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
    ]);

    setupDynamicFields('mealTypeList', 'addMealTypeBtn', 'mealTypes', [
        { value: '', text: 'Meal type' },
        { value: 'Appetizer', text: 'Appetizer' },
        { value: 'Breakfast', text: 'Breakfast' },
        { value: 'Brunch', text: 'Brunch' },
        { value: 'Dessert', text: 'Dessert' },
        { value: 'Dinner', text: 'Dinner' },
        { value: 'Lunch', text: 'Lunch' },
        { value: 'Snack', text: 'Snack' },
        { value: 'Other', text: 'Other' }
    ]);

    setupDynamicFields('cuisineList', 'addCuisineBtn', 'cuisines', [
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
    ]);

    setupDynamicFields('allergenList', 'addAllergenBtn', 'allergens', [
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
    ]);

    setupDynamicFields('dietTypeList', 'addDietTypeBtn', 'dietTypes', [
        { value: '', text: 'Select a diet type' },
        { value: 'Keto', text: 'Keto' },
        { value: 'Low-Carb', text: 'Low-Carb' },
        { value: 'Low-Fat', text: 'Low-Fat' },
        { value: 'Paleo', text: 'Paleo' },
        { value: 'Pescatarian', text: 'Pescatarian' },
        { value: 'Vegetarian', text: 'Vegetarian' },
        { value: 'Vegan', text: 'Vegan' },
        { value: 'Other', text: 'Other' }
    ]);

    setupDynamicFields('cookingLevelList', 'addCookingLevelBtn', 'cookingLevels', [
        { value: '', text: 'Select a cooking level' },
        { value: 'Novice', text: 'Novice' },
        { value: 'Beginner', text: 'Beginner' },
        { value: 'Intermediate', text: 'Intermediate' },
        { value: 'Advanced', text: 'Advanced' },
        { value: 'Expert', text: 'Expert' },
        { value: 'Chef-Level', text: 'Chef-Level' }
    ]);

    setupDynamicFields('cookingStyleList', 'addCookingStyleBtn', 'cookingStyles', [
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
    ]);
});

function setupDynamicFields(containerId, buttonId, inputName, options) {
    const container = document.querySelector(`#${containerId}`);
    const button = document.querySelector(`#${buttonId}`);

    button.addEventListener('click', function () {
        addField(container, inputName, options, '');
    });
}

function addField(container, inputName, options, selectedValue) {
    const div = document.createElement('div');
    div.classList.add('entry');

    const select = document.createElement('select');
    select.setAttribute('name', `${inputName}[]`);

    options.forEach(option => {
        const opt = document.createElement('option');
        opt.value = option.value;
        opt.textContent = option.text;
        if (option.value === selectedValue) {
            opt.selected = true;
        }
        select.appendChild(opt);
    });

    const removeButton = document.createElement('button');
    removeButton.textContent = 'Remove';
    removeButton.setAttribute('type', 'button');
    removeButton.addEventListener('click', function () {
        div.remove();
    });

    div.appendChild(select);
    div.appendChild(removeButton);
    container.appendChild(div);
}