
let totalStars=1;

async function submitReview(product_id) {
    const textContent = document.getElementById("textContent").value;

    try {
        const response = await api.post(`/products/${product_id}/reviews`, {
            rating: totalStars,
            comment: textContent
        });

        if (response.success) {
            $("#msgToast").removeClass("hidden");
            $("#msg").html("Thanks for your valuable feedback!");
            $("#msgToast").removeClass("border-red-500");
            $("#msgToast").addClass("border-green-500");
            $("#msgIcon").removeClass("bg-red-500");
            $("#msgIcon").addClass("bg-green-500");
            setTimeout(function () {
                $("#msgToast").addClass("hidden");
                window.location.href = "/";
            }, 2500);
        }
    } catch (error) {
        $("#msgToast").removeClass("hidden");
        $("#msg").html(error.message);
        setTimeout(function () {
            $("#msgToast").addClass("hidden");
        }, 2500);
    }
}
    function discard(){
    window.location.href="/";
    }
    function startCount (starNumber){
        totalStars=starNumber;
    }