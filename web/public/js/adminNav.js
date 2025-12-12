async function signOut() {
  try {
    const response = await AuthService.logout();
    
    if (response.success) {
      window.location.href = "/adminSignin";
    }
  } catch (error) {
    console.error("Logout error:", error);
    window.location.href = "/adminSignin";
  }
}

  function toggleNavClose(){
    const navDiv = document.getElementById("navDiv");
    navDiv.classList.toggle("hidden");
    navDiv.classList.toggle("flex");
    
}