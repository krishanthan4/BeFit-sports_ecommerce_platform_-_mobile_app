let productIdArray = [];
let product_qtyArray = [];

document.addEventListener("DOMContentLoaded", function () {
  document
    .getElementById("checkoutButton")
    .classList.add("cursor-not-allowed", "opacity-50");
    document.getElementById("checkoutButton").disabled=true;
});

document.addEventListener("change", function () {
  const checkoutButton = document.getElementById("checkoutButton");
  const orderTotalText = document.getElementById("orderTotal");
  if (orderTotalText.textContent.trim() === "Rs. 0.00") {
    if (
      !checkoutButton.classList.contains("cursor-not-allowed") &&
      !checkoutButton.classList.contains("opacity-50")
    ) {
      checkoutButton.classList.add("cursor-not-allowed");
      checkoutButton.classList.add("opacity-50");
      checkoutButton.disabled = true;
    }
  } else {
    if (
      checkoutButton.classList.contains("cursor-not-allowed") &&
      checkoutButton.classList.contains("opacity-50")
    ) {
      checkoutButton.classList.remove("cursor-not-allowed");
      checkoutButton.classList.remove("opacity-50");
      checkoutButton.disabled = false;

    }
  }
});

function toggleCheckout() {
  const checkoutButton = document.getElementById("checkoutButton");
  checkoutButton.classList.toggle("cursor-not-allowed");
  checkoutButton.classList.toggle("opacity-50");

  if (document.getElementById("selectAllCheckBox").checked) {
    let products_ids = document.querySelectorAll(".product_id");
    productIdArray = [];
    products_ids.forEach((element) => {
      productIdArray.push(element.value);
    });

    let products_qtys = document.querySelectorAll(".product_qty");
    product_qtyArray = [];
    products_qtys.forEach((element) => {
      product_qtyArray.push(element.value);
    });
  } else {
    product_qtyArray = [];
    productIdArray = [];
  }
}

async function deleteFromCart(id) {
    try {
        const response = await CartService.removeFromCart(id);
        if (response.success) {
            $("#msgToast").removeClass("hidden");
            $("#msg").html("Product removed from Cart.");
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

function selectAllItems() {
  var isChecked = $("#selectAllCheckBox").prop("checked");
  $(".itemCheckbox").prop("checked", isChecked);
}

async function checkout() {
  try {
    const response = await api.post('/orders/checkout', { 
      products_id: JSON.stringify(productIdArray),
      products_qty: JSON.stringify(product_qtyArray)
    });
    
    if (response.success && response.data) {
      const result = response.data;
      
      if (result.msg === "success") {
        // Compare parsed order total with server result
        // Payment completed. It can be a successful failure.
        payhere.onCompleted = function onCompleted(orderId) {
          $("#msgToast").removeClass("hidden");
          $("#msg").html("Payment Completed !");
          $("#msgToast").removeClass("border-red-500");
          $("#msgToast").addClass("border-green-500");
          $("#msgIcon").removeClass("bg-red-500");
          $("#msgIcon").addClass("bg-green-500");
          setTimeout(function () {
            $("#msgToast").addClass("hidden");
            saveInvoice(
              orderId,
              result["items"],
              result["qty"],
              result["amount"]
            );
          }, 2500);
          // Note: validate the payment and show success or failure page to the customer
          updateProductQuantity();
        };

        // Payment window closed
        payhere.onDismissed = function onDismissed() {
          // Note: Prompt user to pay again or show an error page
          $("#msgToast").removeClass("hidden");
          $("#msg").html("Payment Dismissed");
          $("#msgToast").removeClass("border-red-500");
          $("#msgToast").addClass("border-orange-500");
          $("#msgIcon").removeClass("bg-red-500");
          $("#msgIcon").addClass("bg-orange-500");
          setTimeout(function () {
            $("#msgToast").addClass("hidden");
          }, 2500);
        };

        // Error occurred
        payhere.onError = function onError(error) {
          // Note: show an error page
          $("#msgToast").removeClass("hidden");
          $("#msg").html("Something Went Wrong !");
          $("#msgToast").removeClass("border-orange-500");
          $("#msgToast").addClass("border-red-500");
          $("#msgIcon").removeClass("bg-orange-500");
          $("#msgIcon").addClass("bg-red-500");
          setTimeout(function () {
            $("#msgToast").addClass("hidden");
          }, 2500);
        };

        // Put the payment variables here
        var payment = {
          sandbox: true,
          merchant_id: result["mid"],
          return_url: "http://localhost:8000/single_product.gui.php?id=1",
          cancel_url: "http://localhost:8000/single_product.gui.php?id=1",
          notify_url: "http://sample.com/notify",
          order_id: result["id"],
          items: result["product_names"],
          amount: result["amount"] + ".00",
          currency: "LKR",
          hash: result["hash"],
          first_name: result["fname"],
          last_name: result["lname"],
          email: result["umail"],
          phone: result["mobile"],
          address: result["address"],
          city: result["city"],
          country: "Sri Lanka",
        };
        // Show the payhere.js popup, when "PayHere Pay" is clicked
        payhere.startPayment(payment);
      } else if (result.msg === "no_address") {
        document.getElementById("msgToast").classList.remove("hidden");
        document.getElementById("msg").innerText = "add your address to Purchase Items";
        setTimeout(() => {
          document.getElementById("msgToast").classList.add("hidden");
        }, 2000);
      }
    }
  } catch (error) {
    console.error("Checkout error:", error);
    document.getElementById("msgToast").classList.remove("hidden");
    document.getElementById("msg").innerText = "Network error occurred";
    setTimeout(() => {
      document.getElementById("msgToast").classList.add("hidden");
    }, 2000);
  }
}
async function updateProductQuantity() {
  try {
      const response = await api.put('/products/update-quantity', {
          products_id: JSON.stringify(productIdArray),
          products_qty: JSON.stringify(product_qtyArray)
      });
      console.log(response);
  } catch (error) {
      console.error("There was a problem updating product quantity:", error);
  }
}

async function saveInvoice(order_id, products_id, products_qty, amount) {
  try {
    const response = await api.post('/orders/invoice', {
      order_id,
      products_id: JSON.stringify(products_id),
      products_qty: JSON.stringify(products_qty),
      amount
    });
    
    if (response.success) {
      window.location.href = "/cart?order_id=" + order_id;
    }
  } catch (error) {
    console.error("Error saving invoice:", error);
  }
}
