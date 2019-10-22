var username;
var password;

function saveUsername() {
  username = document.getElementById('user').value;
  console.log('username -> ' + username);
}

function savePassword() {
  password = document.getElementById('pass').value;
  console.log('password -> ' + password);
}

function login() {
  if (username != 123 || password != qwerty) {
    alert('error input');
  }
}
