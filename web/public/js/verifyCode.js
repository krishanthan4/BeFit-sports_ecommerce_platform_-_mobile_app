function showVerifyCodeModal() {
  var modal = document.getElementById("verifyCodeModal");
  var backdrop = document.getElementById("modal-backdrop");
  modal.classList.remove("hidden");
  backdrop.classList.remove("hidden");
  document.body.classList.add("overflow-hidden");
}

function hideVerifyCodeModal() {
  var modal = document.getElementById("verifyCodeModal");
  var backdrop = document.getElementById("modal-backdrop");
  modal.classList.add("hidden");
  backdrop.classList.add("hidden");
  document.body.classList.remove("overflow-hidden");
}

async function forgotPassword() {
  const email = document.getElementById("email").value;
  try {
    const response = await api.post('/auth/forgot-password', { email });
    
    if (response.success) {
      document.getElementById("msgToast").classList.remove("hidden");
      document.getElementById("msg").innerHTML =
        "Verification code has been sent successfully. Please check your email.";
      document.getElementById("msgToast").classList.add("border-green-500");
      document.getElementById("msgIcon").classList.add("bg-green-500");
      setTimeout(function () {
        document.getElementById("msgToast").classList.add("hidden");
        showVerifyCodeModal();
      }, 1000);
    } else {
      document.getElementById("msgToast").classList.remove("hidden");
      document.getElementById("msg").innerHTML = response.message || "Failed to send verification code";
      setTimeout(() => {
        document.getElementById("msgToast").classList.add("hidden");
      }, 1000);
    }
  } catch (error) {
    console.error("Error:", error);
    document.getElementById("msgToast").classList.remove("hidden");
    document.getElementById("msg").innerHTML = "Network error occurred";
    setTimeout(() => {
      document.getElementById("msgToast").classList.add("hidden");
    }, 1000);
  }
}
let email_global = "";

async function verifyCode() {
  const email = document.getElementById("email").value;

  email_global = email;
  const verification_code = document.getElementById("verification_code").value;
  
  try {
    const response = await api.post('/auth/verify-code', { verification_code, email });
    
    if (response.success) {
      openForgotPasswordModel();
    } else {
      document.getElementById("msgToast").classList.remove("hidden");
      document.getElementById("msg").innerHTML = response.message || "Verification failed";
      document
        .getElementById("msgToast")
        .classList.remove("border-green-500");
      document.getElementById("msgToast").classList.add("border-red-500");
      document.getElementById("msgIcon").classList.remove("bg-green-500");
      document.getElementById("msgIcon").classList.add("bg-red-500");
      document.getElementById("verifyCodeModal").classList.add("hidden");
      document.getElementById("modal-backdrop").classList.add("hidden");
      setTimeout(function () {
        document.getElementById("msgToast").classList.add("hidden");
        showVerifyCodeModal();
      }, 2000);
    }
  } catch (error) {
    console.error("Error:", error);
    document.getElementById("msgToast").classList.remove("hidden");
    document.getElementById("msg").innerHTML = "Network error occurred";
    setTimeout(() => {
      document.getElementById("msgToast").classList.add("hidden");
    }, 1000);
  }
}

function openForgotPasswordModel() {
  document.getElementById("verifyCodeModal").classList.add("hidden");
  document.getElementById("fpmodal").classList.remove("hidden");
}

// forgotpassword

function showForgotPasswordModal() {
  var modal = document.getElementById("fpmodal");
  var backdrop = document.getElementById("modal-backdrop");
  modal.classList.remove("hidden");
  backdrop.classList.remove("hidden");
  document.body.classList.add("overflow-hidden");
}

function hideForgotPasswordModal() {
  var modal = document.getElementById("fpmodal");
  var backdrop = document.getElementById("modal-backdrop");
  modal.classList.add("hidden");
  backdrop.classList.add("hidden");
  // visibility_off
  document.body.classList.remove("overflow-hidden");
}
async function resetPassword() {
  const type_email = email_global;
  const type_password = document.getElementById("type_password").value;
  const type_retype_password = document.getElementById("type_retype_password").value;

  if (type_email && type_password && type_retype_password) {
    try {
      const response = await api.post('/auth/reset-password', {
        email: type_email,
        password: type_password,
        retype_password: type_retype_password
      });
      
      if (response.success) {
        document.getElementById("fpmodal").classList.add("hidden");
        document.getElementById("modal-backdrop").classList.add("hidden");

        document.getElementById("msgToast").classList.remove("hidden");
        document.getElementById("msg").innerHTML = "Password Changed";
        document
          .getElementById("msgToast")
          .classList.remove("border-orange-500");
        document.getElementById("msgToast").classList.add("border-green-500");
        document.getElementById("msgIcon").classList.remove("bg-orange-500");
        document.getElementById("msgIcon").classList.add("bg-green-500");
        setTimeout(() => {
          document.getElementById("msgToast").classList.add("hidden");
          document.getElementById("modal-backdrop").classList.add("hidden");
          window.location.reload();
        }, 2000);
      } else {
        document.getElementById("fpmodal").classList.add("hidden");
        document.getElementById("modal-backdrop").classList.add("hidden");

        document.getElementById("msgToast").classList.remove("hidden");
        document.getElementById("msg").innerHTML = response.message || "Password reset failed";
        document
          .getElementById("msgToast")
          .classList.remove("border-green-500");
        document
          .getElementById("msgToast")
          .classList.add("border-orange-500");
        document.getElementById("msgIcon").classList.remove("bg-green-500");
        document.getElementById("msgIcon").classList.add("bg-orange-500");
        setTimeout(() => {
          document.getElementById("fpmodal").classList.remove("hidden");
          document
            .getElementById("modal-backdrop")
            .classList.remove("hidden");
          document.getElementById("msgToast").classList.remove("hidden");
        }, 2000);
      }
    } catch (error) {
      console.error("Error:", error);
      document.getElementById("msgToast").classList.remove("hidden");
      document.getElementById("msg").innerHTML = "Network error occurred";
      setTimeout(() => {
        document.getElementById("msgToast").classList.add("hidden");
      }, 2500);
    }
  } else {
    document.getElementById("msgToast").classList.remove("hidden");
    document.getElementById("msg").innerHTML = "password cannot be empty !";
    setTimeout(() => {
      document.getElementById("msgToast").classList.add("hidden");
    }, 2500);
  }
}

