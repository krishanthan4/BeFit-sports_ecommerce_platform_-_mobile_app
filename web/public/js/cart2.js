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

function deleteFromCart(id) {
  $.ajax({
    url: "/processes/removeCartProcess.php?id=" + id,
    method: "GET",
    success: function (responceData) {
      if (responceData === "Removed") {
        $("#msgToast").removeClass("hidden");
        $("#msg").html("Product removed from Cart.");
        $("#msgToast").addClass("border-green-500");
        $("#msgIcon").addClass("bg-green-500");
        setTimeout(() => {
          $("#msgToast").addClass("hidden");
          window.location.reload();
        }, 1000);
      } else {
        $("#msgToast").removeClass("hidden");
        $("#msg").html(responseData);
        setTimeout(() => {
          $("#msgToast").addClass("hidden");
        }, 2500);
      }
    },

    error: function (errorThrown) {
      console.error(
        "There was a problem with the fetch operation:",
        errorThrown
      );
    },
  });
}

function selectAllItems() {
  var isChecked = $("#selectAllCheckBox").prop("checked");
  $(".itemCheckbox").prop("checked", isChecked);
}

function checkout() {
  const request = new XMLHttpRequest();
  const form = new FormData();
  form.append("products_id", JSON.stringify(productIdArray));
  form.append("products_qty", JSON.stringify(product_qtyArray));

  request.onreadystatechange = () => {
      if (request.readyState == 4 && request.status == 200) {
          const result = JSON.parse(request.responseText);
          if (result["msg"] == "success") {
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
              // document.getElementById('payhere-payment').onclick = function (e) {
              payhere.startPayment(payment);
              // };
          } else if (result["msg"] == "no_address") {
              document.getElementById("msgToast").classList.remove("hidden");
              document.getElementById("msg").innerText =
                  "add your address to Purchase Items";
              setTimeout(() => {
                  document.getElementById("msgToast").classList.add("hidden");
              }, 2000);
          } else {

          }
      }
  };
  request.open("POST", "processes/SendBuyDetailsProcess.php", true);
  request.send(form);
}
function updateProductQuantity() {
  $.ajax({
      url: "/processes/updateProductQuantityProcess.php",
      method: "POST",
      data: {
          products_id: JSON.stringify(productIdArray),
          products_qty: JSON.stringify(product_qtyArray)
      },
      success: function (response) {
          console.log(response);
      },
      error: function (errorThrown) {
          console.error("There was a problem with the fetch operation:", errorThrown);
      },
  });
}

function saveInvoice(order_id, products_id, products_qty, amount) {
  const request = new XMLHttpRequest();
  const form = new FormData();
  form.append("order_id", order_id);
  form.append("products_id", JSON.stringify(products_id));
  form.append("products_qty", JSON.stringify(products_qty));
  form.append("amount", amount);
  request.onreadystatechange = () => {
    if (request.status == 200 && request.readyState == 4) {
      const result = JSON.parse(request.responseText);
      if (result["msg"] == "success") {
        window.location.href = "/cart?order_id=" + order_id;
      }
    }
  };
  request.open("POST", "/processes/invoiceItemAddProcess.php", true);
  request.send(form);
}
