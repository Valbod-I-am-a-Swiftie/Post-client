function openNewLetterModalWindow() {
    let open = document.getElementById('newLetter-modal');
    open.style.display = 'block';
}

function closeNewLetterModalWindow() {
    let close = document.getElementById('newLetter-modal');
    close.style.display = 'none';
}

//onclick -> 'Save changes'
function saveChanges() {
    let recipient = document.getElementById('rec').value;
    let subject = document.getElementById('sub').value;
    let text = document.getElementById('text').value;

    if (recipient !== '' && text !== '') {
        console.log('ok rec & text');
        
        if (subject == '') {
            subject = 'without subject';
            console.log('sub is empty');
            

        }
        closeNewLetterModalWindow();
        console.log('closed');
        
    } else {
        alert('attention! some fields are empty!')
    }
}