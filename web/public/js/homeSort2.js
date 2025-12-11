function onHoverHideMain(id) {
  document.getElementById(id.toString() + "LiSVG").classList.add("opacity-0");
  document.getElementById("firstSubUL").classList.add("hidden");
  document.getElementById("secondSubUL").classList.add("hidden");
}

// brand
function onHoverShowSubUL() {
  document.getElementById("firstSubUL").classList.remove("hidden");
}
// brand
function onHoverHideSubUL() {
  document.getElementById("firstSubUL").classList.add("hidden");
}
// product
function onHoverShowSubUL2() {
  document.getElementById("secondSubUL").classList.remove("hidden");
}
// product
function onHoverHideSubUL2() {
  document.getElementById("secondSubUL").classList.add("hidden");
}
function onHoverHideSubUL3() {
  document.getElementById("thirdSubUL").classList.add("hidden");
}

function onHoverShowSubProductLI1(id) {
  document
    .getElementById(id.toString() + "Li2ProductSVG")
    .classList.remove("opacity-0");
}

function onHoverHideSubProductLI1(id) {
  document
    .getElementById(id.toString() + "Li2ProductSVG")
    .classList.add("opacity-0");
}



// model -> product
function onHoverShowSubProductLI2(id) {
    document
      .getElementById(id.toString() + "Li2ProductSVG")
      .classList.remove("opacity-0");
      document.getElementById("thirdSubUL").classList.remove("hidden");
  
  }
  
  function onHoverHideSubProductLI2(id) {
    document
      .getElementById(id.toString() + "Li2ProductSVG")
      .classList.add("opacity-0");
  }



function sendSingleProductLI1(id) {
  window.location.href = "/singleProduct?product_id=" + id;
}

// product

function onHoverHideSubModelLI1(id) {
  document
    .getElementById(id.toString() + "Li2ModelSVG")
    .classList.add("opacity-0");
    document.getElementById("thirdSubUL").classList.add("hidden");

  // document.getElementById("firstSubUL").classList.add("hidden");
}

function onHoverHideSubBrandLI1(id) {
  document
    .getElementById(id.toString() + "Li2BrandSVG")
    .classList.add("opacity-0");
    document.getElementById("secondSubUL").classList.add("hidden");

  document.getElementById("firstSubUL").classList.add("hidden");
}
