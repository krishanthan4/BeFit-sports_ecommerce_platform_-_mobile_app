function changeStatus(id) {
  $.ajax({
    url: `/processes/changeStatusProcess.php?id=${id}`,
    method: "GET",
    success: function (responseData) {
      if (
        responseData.trim() === "activated" ||
        responseData.trim() === "deactivated"
      ) {
        window.location.reload();
      } else {
        $("#msgToast").removeClass("hidden");
        $("#msg").html(responseData);
        setTimeout(() => {
          $("#msgToast").addClass("hidden");
        }, 2500);
      }
    },
    error: function (errorThrown) {
      console.error("Error:", errorThrown);
    },
  });
}

function sendId(id) {
  $.ajax({
    url: `/processes/sendIdProcess.php?id=${id}`,
    method: "GET",
    success: function (responseData) {
      if (responseData.trim() === "Success") {
        window.location.href = "/editProduct";
      } else {
        $("#msgToast").removeClass("hidden");
        $("#msg").html(responseData);
        setTimeout(() => {
          $("#msgToast").addClass("hidden");
        }, 2500);
      }
    },
    error: function (errorThrown) {
      console.error("Error:", errorThrown);
    },
  });
}

function updateProduct() {
  try {
    const title = $("#title").val();
    const quantity = $("#quantity").val();
    const deliveryFee = $("#deliveryFee").val();
    const description = $("#description").val();
    var image = $("#imageUploader")[0].files;

    const form = new FormData();
    form.append("title", title);
    form.append("quantity", quantity);
    form.append("deliveryFee", deliveryFee);
    form.append("description", description);

    $.each(image, function (index, file) {
      form.append("image" + index, file);
    });

    $.ajax({
      url: "/processes/updateProductProcess.php",
      type: "POST",
      data: form,
      processData: false,
      contentType: false,
      success: function (responseData) {
        if (
          responseData.trim() === "Product has been Updated." ||
          responseData.trim() !== "Invalid Image Count."
        ) {
          $("#msgToast").removeClass("hidden");
          $("#msg").html("Product updated Successfully !");
          $("#msgToast").addClass("border-green-500");
          $("#msgIcon").addClass("bg-green-500");
          setTimeout(() => {
            $("#msgToast").addClass("hidden");
            window.location = "/myProducts";
          }, 2500);
        } else {
          $("#msgToast").removeClass("hidden");
          $("#msg").html(responseData);
          setTimeout(() => {
            $("#msgToast").addClass("hidden");
          }, 2500);
        }
      },
      error: function (xhr, status, error) {
        console.error("Error occurred while updating product:", error);
      },
    });
  } catch (error) {
    console.error("Error occurred while updating product:", error);
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

  $.ajax({
    url: "/processes/addProductProcess.php",
    type: "POST",
    data: form,
    processData: false,
    contentType: false,
    success: function (data) {
      if (data === "success") {
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
    if (request.readyState == 4 && request.status == 200) {
      if (request.responseText == "success") {
        document.getElementById("color").appendChild(colorOption);
        $("#msgToast").removeClass("hidden");
        $("#msg").html("Color Added !");
        $("#msgToast").addClass("border-green-500");
        $("#msgIcon").addClass("bg-green-500");
        setTimeout(function () {
          $("#msgToast").addClass("hidden");
          $("#colorinput").val("");
        }, 2500);
      }else {
        $("#msgToast").removeClass("hidden");
        $("#msg").html(request.responseText);
        $("#msgToast").removeClass("border-green-500");
        $("#msgIcon").removeClass("bg-green-500");
        $("#msgToast").addClass("border-red-500");
        $("#msgIcon").addClass("bg-red-500");
        setTimeout(function () {
          $("#msgToast").addClass("hidden");
        }, 2500);
      }
    }
  };

  request.open("POST", "./processes/addColorProcess.php", true);
  request.send(form);
}
