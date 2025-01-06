document.addEventListener('DOMContentLoaded', function () {
    const holidayList = document.querySelector('#holidayList');
    const addHolidayBtn = document.querySelector('#addHolidayBtn');

    addHolidayBtn.addEventListener('click', function () {
        addHolidayField();
    });

    function addHolidayField() {
        const holidayDiv = document.createElement('div');
        holidayDiv.classList.add('holiday-entry');

        const holidaySelect = document.createElement('select');
        holidaySelect.setAttribute('name', `holidays[${holidayList.children.length}].holiday`);

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
