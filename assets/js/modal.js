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
        
        if (subject == '') {
            subject = 'without subject';          
        }
        closeNewLetterModalWindow();
        
    } else {
        alert('attention! some fields are empty!')
    }

    let rec = recipient;
    let sub = subject;
    let tex = text;

    console.log('saved');
    console.log(rec + '\n' + sub + '\n' + tex);
    
}