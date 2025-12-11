function contactAdmin() {
    const email = $("#email").val();
    const subject = $("#subject").val();
    const message = $("#message").val();
    
    const formData = new FormData();
    formData.append("email", email);
    formData.append("subject", subject);
    formData.append("message", message);

    $.ajax({
        url: "/processes/contactAdminProcess.php",
        type: "POST",
        data: formData,
        processData: false,
        contentType: false,
        success: function(responseData) {
            if (responseData.trim() === "Success") {
                $("#msgToast").removeClass("hidden");
                $("#msg").html("Your message was successfully sent to the Administrator.");
                $("#msgToast").addClass("border-green-500");
                $("#msgIcon").addClass("bg-green-500");
                setTimeout(() => {
                    $("#msgToast").addClass("hidden");
                    $("#subject").val("");
                    $("#message").val("");
                }, 2500);
            } else {
                $("#msgToast").removeClass("hidden");
                $("#msg").html(responseData);
                setTimeout(() => {
                    $("#msgToast").addClass("hidden");
                }, 2500);
            }
        },
        error: function(jqXHR, textStatus, errorThrown) {
            console.error("AJAX Error:", errorThrown);
        }
    });
}
