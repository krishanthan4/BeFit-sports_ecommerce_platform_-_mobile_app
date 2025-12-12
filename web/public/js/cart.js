let updateCartId;
let condition_for_update = false;

function changeQTY(id, uPrice, delivery_cost) {
  let quantity = 0;
  const qty_Input = document.getElementById(id + "qtyNum");
  const checkboxes3 = document.querySelectorAll('input[name="singleCheckbox"]');

  // Initialize newOrderTotal and newSubtotal to zero
  let newSubtotal = 0;

  // Add an event listener for the input event
  qty_Input.addEventListener("input", function (event) {
    quantity = parseInt(event.target.value);
    const unitPrice = parseInt(uPrice);

    (async () => {
      try {
        const response = await CartService.updateCartItem(id, quantity);
        if (response.success) {
          const requestedTotal_with_qty = parseInt(delivery_cost) + unitPrice * quantity;
          document.getElementById(id + "requested_total_P").innerText = "Rs. " + requestedTotal_with_qty.toFixed(2);

          checkboxes3.forEach((checkbox) => {
            if (checkbox.checked) {
              const checkbox_unitPrice = parseInt(document.getElementById(checkbox.id + "unit_price_P").textContent.replace("Rs. ", "").trim());
              const checkbox_qty = parseInt(document.getElementById(checkbox.id + "qtyNum").value);
              const delivery_cost_requestTotal = parseInt(document.getElementById(checkbox.id + "delivery_fee_span").innerText);
              updateCartId = parseInt(checkbox.id);
              if (!isNaN(checkbox_unitPrice) && !isNaN(checkbox_qty) && !isNaN(delivery_cost_requestTotal)) {
                newSubtotal += checkbox_unitPrice * checkbox_qty;
              }
            }
          });

          if (condition_for_update) {
            document.getElementById("subtotal").innerText = "Rs. " + newSubtotal.toFixed(2);
            let deliveryGetCost = parseInt(document.getElementById("deliveryCost").textContent.replace("Rs. ", "").trim());
            document.getElementById("orderTotal").innerText = "Rs. " + (deliveryGetCost + newSubtotal).toFixed(2);
          }
        }
      } catch (error) {
        $("#msgToast").removeClass("hidden");
        $("#msg").html(error.message);
        setTimeout(() => {
          $("#msgToast").addClass("hidden");
        }, 2500);
      }
    })();
  });
}


function checkboxChecking(cart_id){
  condition_for_update = parseInt(cart_id) === updateCartId;
}

function orderSummary(
  qty,
  product_id,
  cart_id,
  delivery_cost,
  requested_total,
  unit_price
) {
  const checkbox = document.getElementById(cart_id);
  const isChecked = checkbox.checked;
  let subtotal = parseFloat(document.getElementById("subtotal").textContent.replace("Rs. ", ""));
  let deliveryCost = parseFloat(document.getElementById("deliveryCost").textContent.replace("Rs. ", ""));
  let orderTotal = parseFloat(document.getElementById("orderTotal").textContent.replace("Rs. ", ""));

  if (!condition_for_update) {
    if (isChecked) {
      productIdArray.push(product_id);
      product_qtyArray.push(qty);
      subtotal += parseFloat(unit_price * qty);
      deliveryCost += parseFloat(delivery_cost);
      orderTotal += parseFloat(requested_total);
    } else {
      subtotal -= parseFloat(unit_price * qty);
      deliveryCost -= parseFloat(delivery_cost);
      orderTotal -= parseFloat(requested_total);
      const checkboxes4 = document.querySelectorAll('input[name="singleCheckbox"]');
      checkboxes4.forEach((checkbox) => {
        const product_qty = parseInt(document.getElementById(checkbox.id + "products_qty").innerText);
        const product_id = parseInt(document.getElementById(checkbox.id + "products_id").innerText);
        if (!checkbox.checked) {
          const uncheckedProductQtyIndex = product_qtyArray.findIndex(id => id === product_qty);
          if (uncheckedProductQtyIndex !== -1) {
            product_qtyArray.splice(uncheckedProductQtyIndex, 1);
          }
          const uncheckedProductIDIndex = productIdArray.findIndex(id => id === product_id);
          if (uncheckedProductIDIndex !== -1) {
            productIdArray.splice(uncheckedProductIDIndex, 1);
          }
        }
      });


    
      if (document.getElementById("selectAllCheckBox").checked === true) {
        document.getElementById("selectAllCheckBox").checked = false;
  
        const checkboxes2 = document.querySelectorAll('input[name="singleCheckbox"]');
        let allDeliveryCost = 0;
        let allSubTotal = 0;
        let allOrderTotal = 0;
        productIdArray.length = 0;
        product_qtyArray.length = 0;
        checkboxes2.forEach((checkbox) => {
          if (checkbox.checked) {
            let product_qty = parseInt(document.getElementById(checkbox.id + "products_qty").innerText);
            let product_id = parseInt(document.getElementById(checkbox.id + "products_id").innerText);
            product_qtyArray.push(product_qty);
            productIdArray.push(product_id);
  
            const deliveryFeeElement = document.getElementById(checkbox.id + "delivery_fee_span");
            allDeliveryCost += parseInt(deliveryFeeElement.innerText);
            const subTotalElement = parseInt(document.getElementById(checkbox.id + "unit_price_span"));
            const unitQty =parseInt(document.getElementById(checkbox.id+"products_qty"));
            allSubTotal += subTotalElement.innerText * unitQty;
            const allOrderElement = document.getElementById(checkbox.id + "requested_total_span");
            
            allOrderTotal += parseInt(allOrderElement.innerText);
          }
    
        });
  
        document.getElementById("deliveryCost").innerText = "Rs. " + allDeliveryCost + ".00";
        document.getElementById("subtotal").innerText = "Rs. " + allSubTotal + ".00";
        document.getElementById("orderTotal").innerText = "Rs. " + allOrderTotal + ".00";
      } else {
        let productIdArrayindex = productIdArray.indexOf(product_id);
        if (productIdArrayindex !== -1) {
          productIdArray.splice(productIdArrayindex, 1);
        }
        let product_qtyArrayindex = product_qtyArray.indexOf(qty);
        if (product_qtyArrayindex !== -1) {
          product_qtyArray.splice(product_qtyArrayindex, 1);
        }
      }
    }
    document.getElementById("subtotal").textContent = "Rs. " + subtotal.toFixed(2);
    document.getElementById("deliveryCost").textContent = "Rs. " + deliveryCost.toFixed(2);
    document.getElementById("orderTotal").textContent = "Rs. " + orderTotal.toFixed(2);
  }
}