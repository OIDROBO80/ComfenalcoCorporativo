var password = document.getElementById("password")
, confirmPassword = document.getElementById("confirmPassword");

function validatePassword(){
if(password.value != confirmPassword.value) {
	confirmPassword.setCustomValidity("Las contraseñas no coinciden!");
} else {
	confirmPassword.setCustomValidity('');
}
}

password.onchange = validatePassword;
confirmPassword.onkeyup = validatePassword;
