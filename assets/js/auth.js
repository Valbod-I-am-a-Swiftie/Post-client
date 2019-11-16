function signIn() {
    let check = document.getElementById('check');
    let username = document.getElementById('user');
    let password = document.getElementById('pass');

    let divUsername = document.getElementById('div-us');
    let divPassword = document.getElementById('div-pass');

    if (username.value === '' || password.value === '') {
        
        if (username.value === '' && password.value !== '') {
            divUsername.style.borderColor = 'rgb(251, 127, 119)';
            divPassword.style.borderColor = '#19c125';
        }

        if (password.value === '' && username.value !== '') {
            divPassword.style.borderColor = 'rgb(251, 127, 119)';
            divUsername.style.borderColor = '#19c125';
        }

        if (username.value === '' && password.value === '') {
            divUsername.style.borderColor = 'rgb(251, 127, 119)';
            divPassword.style.borderColor = 'rgb(251, 127, 119)';
        }

    } else {
        divUsername.style.borderColor = '#19c125';
        divPassword.style.borderColor = '#19c125';

        if (check.checked === true) {      
            localStorage.setItem('username', username.value);
            localStorage.setItem('password', password.value);
        } 

        //sign in here

    }  
}

function signUp() {
    let check = document.getElementById('check');
    let username = document.getElementById('user');
    let password1 = document.getElementById('pass1');
    let password2 = document.getElementById('pass2');

    let divUsername = document.getElementById('div-us');
    let divPassword1 = document.getElementById('div-pass1');
    let divPassword2 = document.getElementById('div-pass2'); 

    if (username.value === '' || password1.value === '' || password2.value === '') {

        if (username.value === '') {
            divUsername.style.borderColor = 'rgb(251, 127, 119)';
        } else {
            divUsername.style.borderColor = '#19c125';
        }

        if (password1.value === '') {
            divPassword1.style.borderColor = 'rgb(251, 127, 119)';
        } else {
            divPassword1.style.borderColor = '#19c125';
        }

        if (password2.value === '') {
            divPassword2.style.borderColor = 'rgb(251, 127, 119)';
        } else {
            divPassword2.style.borderColor = '#19c125';
        }

    } else {

        if (password1.value === password2.value) {
            console.log('password confirmed');

            divPassword1.style.borderColor = '#19c125';
            divPassword2.style.borderColor = '#19c125';

            if (check.checked === true) {      

                localStorage.setItem('username', username.value);
                localStorage.setItem('password', password1.value);
            } 

            //sign up here

        } else {
            alert('Password is not correct!')
            divUsername.style.borderColor = '#19c125';
            divPassword1.style.borderColor = 'rgb(251, 127, 119)';
            divPassword2.style.borderColor = 'rgb(251, 127, 119)';
        }
    }
}

function showPass() {   
    let pass = document.getElementById('pass');
    let eye = document.getElementById('eye-icon');  
    
    if (pass.type === 'password') {
        pass.type = 'text';     
        eye.className = 'fas fa-eye';
        eye.style.color = '#19c125';
    } else {
        pass.type = 'password';       
        eye.className = 'fas fa-eye-slash';
        eye.style.color = '#999';
    }
}

function showPass1() {   
    let pass = document.getElementById('pass1');
    let eye = document.getElementById('eye-icon1');    
    
    if (pass.type === 'password') {
        pass.type = 'text';     
        eye.className = 'fas fa-eye';
        eye.style.color = '#19c125';
    } else {
        pass.type = 'password';       
        eye.className = 'fas fa-eye-slash';
        eye.style.color = '#999';
    }
}

function showPass2() {   
    let pass = document.getElementById('pass2');
    let eye = document.getElementById('eye-icon2'); 
    
    if (pass.type === 'password') {
        pass.type = 'text';     
        eye.className = 'fas fa-eye';
        eye.style.color = '#19c125';
    } else {
        pass.type = 'password';       
        eye.className = 'fas fa-eye-slash';
        eye.style.color = '#999';
    }
}

function rememberMe() {    
    let check = document.getElementById('check');
    let icon = document.getElementById('icon');   

    if (check.checked === false) {
        icon.className = 'far fa-check-square';
        check.checked = true;
        
    } else {
        icon.className = 'far fa-square';
        check.checked = false;
    }
}

function toSignIn() {
    let labelIn = document.getElementById('sign-in');
    labelIn.className = 'auth on';

    let labelUp = document.getElementById('sign-up');
    labelUp.className = 'auth off';
    //show elements
    let divPass = document.getElementById('div-pass');
    divPass.className = 'block input-f m-b-12';
    
    let divPass1 = document.getElementById('div-pass1');
    divPass1.className = 'hide input-f m-b-12';

    let spanConfirm = document.getElementById('span-pass2');
    spanConfirm.className = 'hide ttl';

    let divConfirm = document.getElementById('div-pass2');
    divConfirm.className = 'hide input-f m-b-12';

    let footer = document.getElementById('footer');
    footer.className = 'flex full-class';

    let divBtnIn = document.getElementById('div-btn-in');
    divBtnIn.className = 'flex container-form-btn';

    let divBtnUp = document.getElementById('div-btn-up');
    divBtnUp.className = 'hide container-form-btn';
}

function toSignUp() {
    let labelIn = document.getElementById('sign-in');
    labelIn.className = 'auth off';

    let labelUp = document.getElementById('sign-up');
    labelUp.className = 'auth on';
    //show elements
    let divPass = document.getElementById('div-pass');
    divPass.className = 'hide input-f m-b-12';
    
    let divPass1 = document.getElementById('div-pass1');
    divPass1.className = 'block input-f m-b-12';

    let spanConfirm = document.getElementById('span-pass2');
    spanConfirm.className = 'block ttl';

    let divConfirm = document.getElementById('div-pass2');
    divConfirm.className = 'block input-f m-b-12';

    let footer = document.getElementById('footer');
    footer.className = 'hide full-class';

    let divBtnIn = document.getElementById('div-btn-in');
    divBtnIn.className = 'hide container-form-btn';

    let divBtnUp = document.getElementById('div-btn-up');
    divBtnUp.className = 'flex container-form-btn';
}