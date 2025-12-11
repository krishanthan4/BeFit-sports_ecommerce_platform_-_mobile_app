function changeProfileImage() {
  const image = document.getElementById("userImage");
  image.onchange = () => {
    const file = image.files[0];
    const url = window.URL.createObjectURL(file);
    document.getElementById("image").src = url;
  };
}

function removeProfileImage(image) {
  const email = document.getElementById("email2").value;
  const request = new XMLHttpRequest();
  const form = new FormData();
  form.append("email", email);
  request.onreadystatechange = () => {
    if (request.readyState == 4 && request.status == 200) {
      if (request.responseText == "success") {
        document.getElementById("msgToast").classList.remove("hidden");
        document.getElementById("msg").innerHTML = "Profile Image Removed";
        document.getElementById("msgToast").classList.remove("border-red-500");
        document.getElementById("msgToast").classList.add("border-green-500");
        document.getElementById("msgIcon").classList.remove("bg-red-500");
        document.getElementById("msgIcon").classList.add("bg-green-500");

        sendImageToDelete(image);
        document.getElementById("msgToast").classList.remove("hidden");
        document.getElementById("msg").innerHTML = "Profile Image Removed";
        document.getElementById("msgToast").classList.remove("border-red-500");
        document.getElementById("msgToast").classList.add("border-green-500");
        document.getElementById("msgIcon").classList.remove("bg-red-500");
        document.getElementById("msgIcon").classList.add("bg-green-500");
        setTimeout(() => {
          window.location.reload();
        }, 1000);
      } else {
        document.getElementById("msgToast").classList.remove("hidden");
        document.getElementById("msg").innerHTML = "something went wrong";
        setTimeout(() => {
          document.getElementById("msgToast").classList.add("hidden");
        }, 1000);
      }
    }
  };

  request.open("POST", "./processes/removeAdminProfileProcess.php", true);
  request.send(form);
}

function sendImageToDelete(image) {
  const request = new XMLHttpRequest();
  const form = new FormData();
  form.append("image", image);
  request.onreadystatechange = () => {
    if (request.readyState == 4 && request.status == 200) {
    }
  };
  request.open("POST", "./processes/removeProfile2Process.php", true);
  request.send(form);
}

async function updateProfile() {
  const firstName = document.getElementById("fname").value;
  const lastName = document.getElementById("lname").value;
  const image = document.getElementById("userImage").files[0];

  const formData = new FormData();
  formData.append("firstName", firstName);
  formData.append("lastName", lastName);
  formData.append("image", image);

  try {
    const response = await fetch(
      "/processes/updateProfileAdminProcess.php",
      {
        method: "POST",
        body: formData,
      }
    );
    if (response.ok) {
      const responseData = await response.text();
      if (
        responseData.trim() === "Updated" ||
        responseData.trim() === "Saved"
      ) {
        document.getElementById("msgToast").classList.remove("hidden");
        document.getElementById("msg").innerHTML =
          "Profile Image Updated Successfully !";
        document.getElementById("msgToast").classList.add("border-green-500");
        document.getElementById("msgIcon").classList.add("bg-green-500");
        setTimeout(() => {
          document.getElementById("msgToast").classList.add("hidden");
          window.location.href = "/signin";
        }, 2500);
        window.location.reload();
      } else if (
        responseData.trim() === "Updated2" 
      ) {
        document.getElementById("msgToast").classList.remove("hidden");
        document.getElementById("msg").innerHTML =
          "Admin Details Updated Successfully";
        document.getElementById("msgToast").classList.add("border-green-500");
        document.getElementById("msgIcon").classList.add("bg-green-500");
        setTimeout(() => {
          document.getElementById("msgToast").classList.add("hidden");
          window.location.href = "/signin";
        }, 2500);
        window.location.reload();
      } else if (responseData.trim() == "You have not selected any image.") {
        document.getElementById("msgToast").classList.remove("hidden");
        document.getElementById("msg").innerHTML =
          "You have not selected any image.";
        document.getElementById("msgToast").classList.add("border-green-500");
        document.getElementById("msgIcon").classList.add("bg-green-500");
        setTimeout(() => {
          document.getElementById("msgToast").classList.add("hidden");
          window.location.reload();
        }, 2500);
      } else {
        document.getElementById("msgToast").classList.remove("hidden");
        document.getElementById("msg").innerHTML = responseData;
        setTimeout(() => {
          document.getElementById("msgToast").classList.add("hidden");
        }, 2500);
      }
    } else {
      throw new Error("Network response was not ok.");
    }
  } catch (error) {
    console.error("Fetch error:", error);
  }
}
