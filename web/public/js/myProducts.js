async function changeStatus(id) {
  try {
    const response = await api.put(`/seller/products/${id}`, { status_toggle: true });
    if (response.success) {
      window.location.reload();
    }
  } catch (error) {
    $("#msgToast").removeClass("hidden");
    $("#msg").html(error.message);
    setTimeout(() => {
      $("#msgToast").addClass("hidden");
    }, 2500);
  }
}

async function sendId(id) {
  try {
    // Store product ID in session storage for edit page
    sessionStorage.setItem('editProductId', id);
    window.location.href = "/editProduct";
  } catch (error) {
    $("#msgToast").removeClass("hidden");
    $("#msg").html(error.message);
    setTimeout(() => {
      $("#msgToast").addClass("hidden");
    }, 2500);
  }
}

async function updateProduct() {
  try {
    const productId = sessionStorage.getItem('editProductId');
    const title = $("#title").val();
    const quantity = $("#quantity").val();
    const deliveryFee = $("#deliveryFee").val();
    const description = $("#description").val();

    const productData = {
      title,
      quantity,
      delivery_fee: deliveryFee,
      description
    };

    const response = await ProductService.updateProduct(productId, productData);
    if (response.success) {
      $("#msgToast").removeClass("hidden");
      $("#msg").html("Product updated Successfully!");
      $("#msgToast").addClass("border-green-500");
      $("#msgIcon").addClass("bg-green-500");
      setTimeout(() => {
        $("#msgToast").addClass("hidden");
        window.location = "/myProducts";
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

function addProduct() {
  var category = $("#category").val();
  var brand = $("#brand").val();
  var model = $("#model").val();
  var title = $("#title").val();
  var condition = 0;

  if ($("#brandNew").prop("checked")) {
    condition = 1;
  } else if ($("#used").prop("checked")) {
    condition = 2;
  }

  var color = $("#color").val();
  var quantity = $("#quantity").val();
  var cost = $("#cost").val();
  var deliveryFee = $("#deliveryFee").val();
  var description = $("#description").val();
  var image = $("#imageUploader")[0].files;

  var form = new FormData();
  form.append("category", category);
  form.append("brand", brand);
  form.append("model", model);
  form.append("title", title);
  form.append("condition", condition);
  form.append("color", color);
  form.append("quantity", quantity);
  form.append("cost", cost);
  form.append("deliveryFee", deliveryFee);
  form.append("description", description);

  $.each(image, function (index, file) {
    form.append("image" + index, file);
  });

  try {
    const productData = {
      category_id: category,
      brand_id: brand,
      model_id: model,
      title,
      condition_id: condition,
      color_id: color,
      quantity,
      price: cost,
      delivery_fee: deliveryFee,
      description
    };

    const response = await ProductService.createProduct(productData);
    if (response.success) {
      $("#msgToast").removeClass("hidden");
      $("#msg").html("Product Saved Successfully!");
      $("#msgToast").addClass("border-green-500");
      $("#msgIcon").addClass("bg-green-500");
      setTimeout(function () {
        $("#msgToast").addClass("hidden");
        window.location.reload();
        }, 2500);
      } else {
        $("#msgToast").removeClass("hidden");
        $("#msg").html(data);
        setTimeout(function () {
          $("#msgToast").addClass("hidden");
        }, 2500);
      }
    },
    error: function (error) {
      console.error("Error occurred while adding product:", error);
    },
  });
}

function addColor() {
  const colorinput = document.getElementById("colorinput").value;
  const colorOption = document.createElement("option");
  colorOption.value = colorinput;
  colorOption.textContent = colorinput;

  const request = new XMLHttpRequest();
  const form = new FormData();
  form.append("colorInput", colorinput);

  request.onreadystatechange = () => {
    try {
      const response = await api.post('/products/colors', { color: colorValue });
      
      if (response.success) {
        document.getElementById("color").appendChild(colorOption);
        $("#msgToast").removeClass("hidden");
        $("#msg").html("Color Added !");
        $("#msgToast").addClass("border-green-500");
        $("#msgIcon").addClass("bg-green-500");
        setTimeout(function () {
          $("#msgToast").addClass("hidden");
          $("#colorinput").val("");
        }, 2500);
      } else {
        $("#msgToast").removeClass("hidden");
        $("#msg").html(response.message || "Failed to add color");
        $("#msgToast").removeClass("border-green-500");
        $("#msgIcon").removeClass("bg-green-500");
        $("#msgToast").addClass("border-red-500");
        $("#msgIcon").addClass("bg-red-500");
        setTimeout(function () {
          $("#msgToast").addClass("hidden");
        }, 2500);
      }
    } catch (error) {
      console.error("Error:", error);
      $("#msgToast").removeClass("hidden");
      $("#msg").html("Network error occurred");
      $("#msgToast").addClass("border-red-500");
      $("#msgIcon").addClass("bg-red-500");
      setTimeout(function () {
        $("#msgToast").addClass("hidden");
      }, 2500);
    }
  };

  // Removed XMLHttpRequest code
}
