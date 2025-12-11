function closeMobileMenu() {
  const MobileMenu = document.getElementById("MobileMenu");
  const MobileMenublack = document.getElementById("MobileMenublack");
  MobileMenu.classList.toggle("hidden");
  MobileMenublack.classList.toggle("hidden");
}
function MobileMenuNav(){
  const MobileMenu = document.getElementById("MobileMenu");
  const MobileMenublack = document.getElementById("MobileMenublack");
  MobileMenu.classList.remove("hidden");
  MobileMenublack.classList.remove("hidden");

}

function flyoutMenuCalisthenicsHover() {
  const flyoutMenuCalisthenics = document.getElementById("flyoutMenuCalisthenics");

  flyoutMenuCalisthenics.classList.remove("hidden");
}
function flyoutMenuCalisthenicsRemove() {
  const flyoutMenuCalisthenics = document.getElementById("flyoutMenuCalisthenics");
setTimeout(() => {
  flyoutMenuCalisthenics.classList.add("hidden");
}, 500);

}

function flyoutMenuWeightHover() {
  const flyoutMenuWeight = document.getElementById("flyoutMenuWeight");

  flyoutMenuWeight.classList.remove("hidden");
}
function flyoutMenuWeightRemove() {
  const flyoutMenuWeight = document.getElementById("flyoutMenuWeight");
setTimeout(() => {
  flyoutMenuWeight.classList.add("hidden");
}, 500);
}

function flyoutMenuSkateboardingHover() {
  const flyoutMenuSkateboarding = document.getElementById("flyoutMenuSkateboarding");

  flyoutMenuSkateboarding.classList.remove("hidden");
}
function flyoutMenuSkateboardingRemove() {
  const flyoutMenuSkateboarding = document.getElementById("flyoutMenuSkateboarding");
setTimeout(() => {
  flyoutMenuSkateboarding.classList.add("hidden");
}, 500);
}



function signOut(){
  
  const request = new XMLHttpRequest();
  request.onreadystatechange = ()=>{
if(request.status==200 && request.readyState==4){
if(request.responseText=="success"){
window.location.href = "/";
}

}
  }
  request.open("POST","./processes/signoutProcess.php",true);
  request.send();
}