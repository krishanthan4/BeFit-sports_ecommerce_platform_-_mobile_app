
async function signInProcess() {
    const signIn_email = document.getElementById("signIn_email").value;
    const signIn_password = document.getElementById("signIn_password").value;

    try {
        const response = await AuthService.login(signIn_email, signIn_password, false);
        if (response.success) {
            alert("Login successful!");
            window.location.href = "/home";
        }
    } catch (error) {
        alert("Credentials not correct: " + error.message);
    }
}