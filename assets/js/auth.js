var username;
var password;
var shoPass = 0;
var remMe = 0;

document.addEventListener("DOMContentLoaded", () => {
    document.getElementById('checkLabel').className = 'input-checkbox-label far fa-square';
});

function saveUserData() {
    username = document.getElementById('user').value;
    password = document.getElementById('pass').value;

    console.log('username -> ' + username);
    console.log('password -> ' + password);
}

// function login() {
//   if (username != 123 || password != qwerty) {
//     alert('error input');
//   }
// }

function goToPage() {
  var url = 'https://localhost:8443/API/all';
  console.log(url);
  document.location.href = url;
}

function showPass() {
    if (shoPass === 0) {
        document.getElementById('pass').type = 'text';
        document.getElementById('eye-icon').className = 'fas fa-eye';
        shoPass = 1;
    } else {
        document.getElementById('pass').type = 'password';
        document.getElementById('eye-icon').className = 'fas fa-eye-slash';
        shoPass = 0;
    }
}

function rememberMe() {
    if (remMe === 1) {
        document.getElementById('checkLabel').className = 'input-checkbox-label far fa-square';
        console.log('dont remember');
        remMe = 0;
    } else {
        document.getElementById('checkLabel').className = 'input-checkbox-label fas fa-check';
        console.log('remember');
        remMe = 1;
        saveUserData();
    }
}
