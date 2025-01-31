document.addEventListener('DOMContentLoaded', function () {
    const linkList = document.querySelector('#linkList');
    const addLinkBtn = document.querySelector('#addLinkBtn');

    // Event listener for adding new link fields
    addLinkBtn.addEventListener('click', function () {
        addLinkField();
    });
});

function addLinkField() {
    const linkDiv = document.createElement('div');
    linkDiv.classList.add('linkEntry');

    // Input for the link
    const linkInput = document.createElement('input');
    linkInput.setAttribute('type', 'text');
    linkInput.setAttribute('name', `links[${linkList.children.length}].link`);
    linkInput.setAttribute('placeholder', 'Link');
    linkInput.setAttribute('maxlength', '125');

    linkDiv.appendChild(linkInput);

    // Remove button (for all links, including the first one)
    const removeButton = document.createElement('button');
    removeButton.textContent = 'Remove';
    removeButton.setAttribute('type', 'button');
    removeButton.classList.add('removeLinkBtn');
    removeButton.addEventListener('click', function () {
        linkDiv.remove();
    });

    linkDiv.appendChild(removeButton);
    linkList.appendChild(linkDiv);
}