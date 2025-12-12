async function removeFromWatchlist(id) {
    try {
        const response = await WishlistService.removeFromWishlist(id);
        if (response.success) {
            $("#msgToast").removeClass("hidden");
            $("#msg").html("Product successfully removed from Wishlist!");
            $("#msgToast").addClass("border-green-500");
            $("#msgIcon").addClass("bg-green-500");
            setTimeout(() => {
                $("#msgToast").addClass("hidden");
                window.location.reload();
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

async function addToCart(id) {
    const qty = document.getElementById("quantity").innerText;

    try {
        const response = await CartService.addToCart(id, qty);
        if (response.success) {
            $("#msgToast").removeClass("hidden");
            $("#msg").html("Product successfully added to the Cart!");
            $("#msgToast").addClass("border-green-500");
            $("#msgIcon").addClass("bg-green-500");
            setTimeout(() => {
                $("#msgToast").addClass("hidden");
                window.location.reload();
            }, 1000);
        }
    } catch (error) {
        $("#msgToast").removeClass("hidden");
        $("#msg").html(error.message);
        setTimeout(() => {
            $("#msgToast").addClass("hidden");
        }, 2500);
    }
}