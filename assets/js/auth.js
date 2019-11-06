var username = null;
var password = null;
var shoPass = 0;
var remMe = 0;

function saveUserData() {
    username = document.getElementById('user').value;
    password = document.getElementById('pass').value;

    console.log('username -> ' + username);
    console.log('password -> ' + password);
}

function login() {
}

function goToPage() {
  var url = 'https://localhost:8443/API/all';
  console.log(url);
  document.location.href = url;
}

function showPass1() {
    if (shoPass === 0) {
        document.getElementById('pass1').type = 'text';
        document.getElementById('eye-icon').className = 'fas fa-eye';
        shoPass = 1;
    } else {
        document.getElementById('pass1').type = 'password';
        document.getElementById('eye-icon').className = 'fas fa-eye-slash';
        shoPass = 0;
    }
}

function showPass2() {
    if (shoPass === 0) {
        document.getElementById('pass2').type = 'text';
        document.getElementById('eye-icon').className = 'fas fa-eye';
        shoPass = 1;
    } else {
        document.getElementById('pass2').type = 'password';
        document.getElementById('eye-icon').className = 'fas fa-eye-slash';
        shoPass = 0;
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
    let labelUp = document.getElementById('sign-up');
    let btn = document.getElementById('btn');
    let confirm = document.getElementById('pass2');
    let confirmField = document.getElementById('conf');
    let footer = document.getElementById('hide');
 
    //19c125
    labelIn.className = 'auth on';
    labelUp.className = 'auth off';
    btn.textContent = 'sign in';
    confirm.style.display = 'none';
    confirmField.style.display = 'none';
    footer.style.display = 'flex';
}

function toSignUp() {
    let labelIn = document.getElementById('sign-in');
    let labelUp = document.getElementById('sign-up');
    let btn = document.getElementById('btn');
    let confirm = document.getElementById('pass2');
    let confirmField = document.getElementById('conf');
    let footer = document.getElementById('hide');

    labelIn.className = 'auth off';
    labelUp.className = 'auth on';
    btn.textContent = 'sign up';
    confirm.style.display = 'block';
    confirmField.style.display = 'block';
    footer.style.display = 'none';
}