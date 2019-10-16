var username;
var password;

// var btnLogin = document.getElementById('btn');

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

function goToPage() {
  var url = 'https://localhost:8443/API/all';
  console.log(url);
  document.location.href = url;
}