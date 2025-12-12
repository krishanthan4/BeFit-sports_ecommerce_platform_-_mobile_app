function closeModal() {
    $("#verificationModal").addClass("hidden");
}
function openModal() {
    $("#verificationModal").removeClass("hidden");
}

async function adminSignin() {
    const email = $("#adminEmail").val();

    try {
        const response = await api.post('/admin/login', { email });
        if (response.success) {
            $("#msgToast").removeClass("hidden");
            $("#msg").html("Please take a look at your email to find the VERIFICATION CODE.");
            $("#msgToast").addClass("border-green-500");
            $("#msgIcon").addClass("bg-green-500");
            setTimeout(() => {
                $("#msgToast").addClass("hidden");
                openModal();
            }, 2000);
        }
    } catch (error) {
        $("#msgToast").removeClass("hidden");
        $("#msg").html(error.message);
        setTimeout(() => {
            $("#msgToast").addClass("hidden");
        }, 2500);
    }
}

async function verifyAdminCode() {
    const verifyCode = $("#verifyCode").val();
    const rememberMe = $("#rememberMe").is(":checked");

    try {
        const response = await AuthService.verify(verifyCode);
        if (response.success) {
            $("#msgToast").removeClass("hidden");
            $("#msg").html("Successfully Verified!");
            $("#msgToast").addClass("border-green-500");
            $("#msgIcon").addClass("bg-green-500");
            setTimeout(() => {
                closeModal();
                window.location = "/adminDashboard";
            }, 1000);
        }
    } catch (error) {
        $("#msgToast").removeClass("hidden");
        $("#msg").html(error.message);
        $("#msgIcon").removeClass("bg-green-500").addClass("bg-red-500");
        $("#msgToast").removeClass("border-green-500").addClass("border-red-500");
        setTimeout(() => {
            $("#msgToast").addClass("hidden");
            $("#msgIcon").removeClass("bg-red-500").addClass("bg-green-500");
            $("#msgToast").removeClass("border-red-500").addClass("border-green-500");
        }, 2500);
            }
        }
