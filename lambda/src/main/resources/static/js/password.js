document.addEventListener("DOMContentLoaded", function () {

    const passwordInput = document.getElementById("password");
    const toggleButton = document.getElementById("togglePassword");
    const eyeIcon = document.getElementById("passwordEye");

    if (!passwordInput || !toggleButton || !eyeIcon) {
        console.error("Password toggle elements not found!");
        return;
    }

    toggleButton.addEventListener("click", function () {

        if (passwordInput.type === "password") {

            passwordInput.type = "text";

            eyeIcon.classList.remove("fa-eye");
            eyeIcon.classList.add("fa-eye-slash");

            toggleButton.setAttribute("aria-label", "Hide password");

        } else {

            passwordInput.type = "password";

            eyeIcon.classList.remove("fa-eye-slash");
            eyeIcon.classList.add("fa-eye");

            toggleButton.setAttribute("aria-label", "Show password");
        }

    });
	
	

});
