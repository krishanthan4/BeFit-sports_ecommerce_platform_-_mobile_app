async function signIn() {
    const email = $("#email").val();
    const password = $("#password").val();
    const rememberMe = $("#rememberMe").is(":checked");

    try {
        const response = await AuthService.login(email, password, rememberMe);
        
        if (response.success) {
            window.location.href = "/";
        } else {
            $("#msgToast").removeClass("hidden");
            $("#msg").html(response.message || "Login failed");
            setTimeout(() => {
                $("#msgToast").addClass("hidden");
            }, 2500);
        }
    } catch (error) {
        console.error("Login Error:", error);
        $("#msgToast").removeClass("hidden");
        $("#msg").html("Network error occurred");
        setTimeout(() => {
            $("#msgToast").addClass("hidden");
        }, 2500);
    }
}
