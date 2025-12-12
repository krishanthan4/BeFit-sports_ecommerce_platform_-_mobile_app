async function wishlist(id) {
    const outline_heart = document.getElementById("outline_heart");
    const filled_heart = document.getElementById("filled_heart");

    try {
        const response = await WishlistService.addToWishlist(id);
        if (response.success) {
            outline_heart.classList.add("hidden");
            filled_heart.classList.remove("hidden");
        }
    } catch (error) {
        alert(error.message);
    }
}


async function removewishlist(id) {
    const outline_heart = document.getElementById("outline_heart");
    const filled_heart = document.getElementById("filled_heart");

    try {
        const response = await WishlistService.removeFromWishlist(id);
        if (response.success) {
            outline_heart.classList.remove("hidden");
            filled_heart.classList.add("hidden");
        }
    } catch (error) {
        alert(error.message);
    }
}


async function buyNow(id) {
   const qty = document.getElementById("counter-input").value;

    try {
        const response = await CartService.addToCart(id, qty);
        
        if (response.success) {
            window.location.href="/cart";
        } else {
            document.getElementById("msgToast").classList.remove("hidden");
            document.getElementById("msg").innerHTML = response.message || "Failed to add to cart";
            setTimeout(() => {
                document.getElementById("msgToast").classList.add("hidden");
            }, 2000);
        }
    } catch (error) {
        console.error("Error:", error);
        document.getElementById("msgToast").classList.remove("hidden");
        document.getElementById("msg").innerHTML = "Network error occurred";
        setTimeout(() => {
            document.getElementById("msgToast").classList.add("hidden");
        }, 2000);
    }
}


async function addToCart(id) {
    const qty = document.getElementById("counter-input").value;
 
    try {
        const response = await CartService.addToCart(id, qty);
        
        if (response.success) {
            document.getElementById("msgToast").classList.remove("hidden","border-red-500");
            document.getElementById("msgToast").classList.add("border-green-500");
            document.getElementById("msgIcon").classList.remove("bg-red-500");
            document.getElementById("msgIcon").classList.add("bg-green-500");
            document.getElementById("msg").innerHTML = "Product added to cart. Continue Shopping!";
            setTimeout(() => {
                document.getElementById("msgToast").classList.add("hidden","border-red-500");
            }, 2000);
        } else {
            document.getElementById("msgToast").classList.remove("hidden");
            document.getElementById("msg").innerHTML = response.message || "Failed to add to cart";
            setTimeout(() => {
                document.getElementById("msgToast").classList.add("hidden");
            }, 2000);
        }
    } catch (error) {
        console.error("Error:", error);
        document.getElementById("msgToast").classList.remove("hidden");
        document.getElementById("msg").innerHTML = "Network error occurred";
        setTimeout(() => {
            document.getElementById("msgToast").classList.add("hidden");
        }, 2000);
    }
 }
