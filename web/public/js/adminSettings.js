function changeProfileImage() {
  const image = document.getElementById("userImage");
  image.onchange = () => {
    const file = image.files[0];
    const url = window.URL.createObjectURL(file);
    document.getElementById("image").src = url;
  };
}

async function removeProfileImage(image) {
  const email = document.getElementById("email2").value;

  try {
    const response = await api.post('/admin/profile/remove-image', { email });

    if (response.success) {
      document.getElementById("msgToast").classList.remove("hidden");
      document.getElementById("msg").innerHTML = "Profile Image Removed";
      document.getElementById("msgToast").classList.remove("border-red-500");
      document.getElementById("msgToast").classList.add("border-green-500");
      document.getElementById("msgIcon").classList.remove("bg-red-500");
      document.getElementById("msgIcon").classList.add("bg-green-500");

      await sendImageToDelete(image);
      
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
  } catch (error) {
    console.error("Error:", error);
    document.getElementById("msgToast").classList.remove("hidden");
    document.getElementById("msg").innerHTML = "something went wrong";
    setTimeout(() => {
      document.getElementById("msgToast").classList.add("hidden");
    }, 1000);
  }
}

async function sendImageToDelete(image) {
  try {
    await api.post('/admin/profile/delete-image-file', { image });
  } catch (error) {
    console.error("Error deleting image file:", error);
  }
}

async function updateProfile() {
  const firstName = document.getElementById("fname").value;
  const lastName = document.getElementById("lname").value;
  const image = document.getElementById("userImage").files[0];

  const formData = new FormData();
  formData.append("firstName", firstName);
  formData.append("lastName", lastName);
  if (image) {
    formData.append("image", image);
  }

  try {
    const response = await api.post('/admin/profile/update', formData);

    if (response.success) {
      document.getElementById("msgToast").classList.remove("hidden");
      document.getElementById("msg").innerHTML =
        response.message || "Profile Updated Successfully";
      document.getElementById("msgToast").classList.add("border-green-500");
      document.getElementById("msgIcon").classList.add("bg-green-500");
      setTimeout(() => {
        document.getElementById("msgToast").classList.add("hidden");
        window.location.reload();
      }, 2500);
    } else {
      document.getElementById("msgToast").classList.remove("hidden");
      document.getElementById("msg").innerHTML = response.message || "Update failed";
      setTimeout(() => {
        document.getElementById("msgToast").classList.add("hidden");
      }, 2500);
    }
  } catch (error) {
    console.error("Error:", error);
    document.getElementById("msgToast").classList.remove("hidden");
    document.getElementById("msg").innerHTML = "Network error occurred";
    setTimeout(() => {
      document.getElementById("msgToast").classList.add("hidden");
    }, 2500);
  }
}
