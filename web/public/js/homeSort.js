function onHoverShowMain(id) {
    document.getElementById(id.toString() + "LiSVG").classList.remove('opacity-0');
const request = new XMLHttpRequest();
const form = new FormData();
form.append("cat_id",id);
(async () => {
  try {
    const response = await api.post('/categories/sort', { cat_id: id });
    
    if (response.success && response.data) {
      const data = response.data;
      
      if (data.msg === "brand_success") {
        try {
          document.getElementById("firstSubUL").classList.remove("hidden");
          document.getElementById("secondSubUL").classList.add("hidden");

          document.getElementById("firstSubUL").innerHTML="";
          for (let index = 0; index < Object.keys(data).length; index++) {
            let li1 = document.createElement("li");
            li1.classList.add("text-gray-400","py-2","cursor-pointer","px-3","hover:text-orange-500","flex","items-center");
            li1.innerHTML=data[index].brand_name+"<svg id='"+data[index].brand_id.toString()+"Li2BrandSVG' xmlns='http://www.w3.org/2000/svg' fill='none' viewBox='0 0 24 24' stroke-width='1.5' stroke='currentColor' class='w-5 h-5 ms-auto opacity-0'><path stroke-linecap='round' stroke-linejoin='round' d='m8.25 4.5 7.5 7.5-7.5 7.5'/></svg>";
            li1.id=data[index].brand_id+"brandLI";
            document.getElementById("firstSubUL").append(li1);

            document.getElementById(data[index].brand_id + "brandLI").addEventListener("mouseover", function() {
              onHoverShowSubBrandLI1(data[index].brand_id);
            });
            document.getElementById(data[index].brand_id + "brandLI").addEventListener("mouseleave", function() {
              onHoverHideSubBrandLI1(data[index].brand_id);
            });
          }
        } catch (error) {
          console.error("Error:", error);
        }
      } else if (data.msg === "product_success") {
        product_success(data);
      } else {
        console.log("something went wrong !");
      }
    }
  } catch (error) {
    console.error("Error:", error);
  }
})();

    }

    
    function onHoverShowSubBrandLI1(id) {
        document.getElementById(id.toString() + "Li2BrandSVG").classList.remove('opacity-0');
        const request2= new XMLHttpRequest();
        const form2 = new FormData();
        form2.append("brand_id",id);
        (async () => {
          try {
            const response2 = await api.post('/categories/sort', { brand_id: id });
            
            if (response2.success && response2.data) {
              const data = response2.data;
              
              if (data.msg === "model_success") {
                try {
                  document.getElementById("secondSubUL").innerHTML="";
                  document.getElementById("secondSubUL").classList.remove("hidden");
                  document.getElementById("secondSubUL").classList.add("xl:ms-[38%]","lg:ms-[48%]","ms-[53%]");
              
                  for (let index = 0; index < Object.keys(data).length; index++) {
                    let li1 = document.createElement("li");
                    li1.classList.add("text-gray-400","py-2","cursor-pointer","px-3","hover:text-orange-500","flex","items-center");
                    li1.innerHTML=data[index].model_name+"<svg id='"+data[index].model_id.toString()+"Li2ModelSVG' xmlns='http://www.w3.org/2000/svg' fill='none' viewBox='0 0 24 24' stroke-width='1.5' stroke='currentColor' class='w-5 h-5 ms-auto opacity-0'><path stroke-linecap='round' stroke-linejoin='round' d='m8.25 4.5 7.5 7.5-7.5 7.5'/></svg>";
                    li1.id=data[index].model_id+"ModelLI";
             
                    document.getElementById("secondSubUL").append(li1);
                
                    document.getElementById(data[index].model_id + "ModelLI").addEventListener("mouseover", function() {
                      onHoverShowSubModelLI1(data[index].model_id);
                    });
                    document.getElementById(data[index].model_id + "ModelLI").addEventListener("mouseleave", function() {
                      onHoverHideSubModelLI1(data[index].model_id);
                    });
              
                  }
                } catch (error) {
                  console.error("Error:", error);
                }
              } else if (data.msg === "product_success") {
                product_success(data);
              }
            }
          } catch (error) {
            console.error("Error:", error);
          }
        })();
    }
    // document.getElementById("firstSubUL").classList.remove("hidden");
//model
function onHoverShowSubModelLI1(id) {
    // document.getElementById(id.toString() + "Li2ModelSVG").classList.remove('opacity-0');
    document.getElementById(id.toString() + "Li2ModelSVG").classList.remove('opacity-0');

    const request3= new XMLHttpRequest();
    const form3 = new FormData();
    form3.append("model_id",id);
    (async () => {
      try {
        const response3 = await api.post('/categories/sort', { model_id: id });
        
        if (response3.success && response3.data) {
          const data = response3.data;
          if (data.msg === "product_success") {
            product_success2(data);
          }
        }
      } catch (error) {
        console.error("Error:", error);
      }
    })();
}

function product_success(response){
    try {
        document.getElementById("secondSubUL").classList.remove("hidden","xl:ms-[38%]","lg:ms-[48%]","ms-[53%]");
        document.getElementById("secondSubUL").classList.add("ms-[28%]");
    
    
        document.getElementById("secondSubUL").innerHTML="";
        for (let index2 = 0; index2 < Object.keys(response).length; index2++) {
            let li1 = document.createElement("li");
    
            li1.classList.add("text-gray-400", "py-2", "cursor-pointer", "px-3", "hover:text-orange-500","flex","items-center");
            li1.innerHTML=response[index2].title+"<svg id='"+response[index2].id.toString()+"Li2ProductSVG' xmlns='http://www.w3.org/2000/svg' fill='none' viewBox='0 0 24 24' stroke-width='1.5' stroke='currentColor' class='w-5 h-5 ms-auto opacity-0'><path stroke-linecap='round' stroke-linejoin='round' d='m8.25 4.5 7.5 7.5-7.5 7.5'/></svg>";
            li1.id=response[index2].id+"ProductLI";
            document.getElementById("secondSubUL").append(li1);
        
            document.getElementById(response[index2].id + "ProductLI").addEventListener("mouseover", function() {
                onHoverShowSubProductLI1(response[index2].id);
            });
            document.getElementById(response[index2].id + "ProductLI").addEventListener("mouseleave", function() {
                onHoverHideSubProductLI1(response[index2].id);
            });
                   document.getElementById(response[index2].id + "ProductLI").addEventListener("click", function() {
                sendSingleProductLI1(response[index2].id);
            });
      
        }
       } catch (error) {
        
       }
}

function product_success2(response){
    try {
        document.getElementById("thirdSubUL").classList.remove("hidden");
        // document.getElementById("secondSubUL").classList.add("ms-[25rem]");
    
        document.getElementById("thirdSubUL").innerHTML="";
        for (let index2 = 0; index2 < Object.keys(response).length; index2++) {
            let li1 = document.createElement("li");
    
            li1.classList.add("text-gray-400", "py-2", "cursor-pointer", "px-3", "hover:text-orange-500","flex","items-center");
            li1.innerHTML=response[index2].title+"<svg id='"+response[index2].id.toString()+"Li2ProductSVG' xmlns='http://www.w3.org/2000/svg' fill='none' viewBox='0 0 24 24' stroke-width='1.5' stroke='currentColor' class='w-5 h-5 ms-auto opacity-0'><path stroke-linecap='round' stroke-linejoin='round' d='m8.25 4.5 7.5 7.5-7.5 7.5'/></svg>";
            li1.id=response[index2].id+"ProductLI";
            document.getElementById("thirdSubUL").append(li1);
        
            document.getElementById(response[index2].id + "ProductLI").addEventListener("mouseover", function() {
                onHoverShowSubProductLI2(response[index2].id);
            });
            document.getElementById(response[index2].id + "ProductLI").addEventListener("mouseleave", function() {
                onHoverHideSubProductLI2(response[index2].id);
            });
                   document.getElementById(response[index2].id + "ProductLI").addEventListener("click", function() {
                sendSingleProductLI1(response[index2].id);
            });
      
        }
       } catch (error) {
        
       }
}