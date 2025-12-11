function signOut(){
  
    const request = new XMLHttpRequest();
    request.onreadystatechange = ()=>{
  if(request.status==200 && request.readyState==4){
  if(request.responseText=="success"){
  window.location.href = "/adminSignin";
  }
  
  }
    }
    request.open("POST","./processes/adminSignoutProcess.php",true);
    request.send();
  }

  function toggleNavClose(){
    const navDiv = document.getElementById("navDiv");
    navDiv.classList.toggle("hidden");
    navDiv.classList.toggle("flex");
    
}