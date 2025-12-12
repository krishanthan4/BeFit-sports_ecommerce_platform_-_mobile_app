async function contactAdmin() {
    const email = $("#email").val();
    const subject = $("#subject").val();
    const message = $("#message").val();

    try {
        const response = await api.post('/contact', {
            email,
            subject,
            message
        });
        
        if (response.success) {
            $("#msgToast").removeClass("hidden");
            $("#msg").html("Your message was successfully sent to the Administrator.");
            $("#msgToast").addClass("border-green-500");
            $("#msgIcon").addClass("bg-green-500");
            setTimeout(() => {
                $("#msgToast").addClass("hidden");
                $("#subject").val("");
                $("#message").val("");
            }, 2500);
        }
    } catch (error) {
        $("#msgToast").removeClass("hidden");
        $("#msg").html(error.message);
        setTimeout(() => {
            $("#msgToast").addClass("hidden");
        }, 2500);
    }
}
