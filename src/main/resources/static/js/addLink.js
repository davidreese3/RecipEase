document.addEventListener('DOMContentLoaded', function () {
    const linkList = document.querySelector('#linkList');
    const addLinkBtn = document.querySelector('#addLinkBtn');

    addLinkBtn.addEventListener('click', function () {
        addLinkField();
    });

    function addLinkField() {
        const linkDiv = document.createElement('div');
        linkDiv.classList.add('linkEntry');

        const linkInput = document.createElement('input');
        linkInput.setAttribute('type', 'text');
        linkInput.setAttribute('name', `links[${linkList.children.length}].link`);
        linkInput.setAttribute('placeholder', 'Link');
        linkInput.setAttribute('maxlength', '125');

        const removeButton = document.createElement('button');
        removeButton.textContent = 'Remove';
        removeButton.setAttribute('type', 'button');
        removeButton.addEventListener('click', function () {
            linkDiv.remove();
        });

        linkDiv.appendChild(linkInput);
        linkDiv.appendChild(removeButton);
        linkList.appendChild(linkDiv);
    }
});
