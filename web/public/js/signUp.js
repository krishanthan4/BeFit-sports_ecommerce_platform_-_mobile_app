// function signUp(){
// // alert("pp");
// const email = document.getElementById("email");
// const password = document.getElementById("password");
// const retype_password = document.getElementById("retype_password");

// const form = new FormData();

// form.append("email",email);
// form.append("password",password);
// form.append("retype_password",retype_password);

// const request = new XMLHttpRequest();

// request.onreadystatechange = () => {

//     if(request.status == 200 &&request.readyState==4){
//         const responseText = request.responseText;

//         if(responseText=="success"){
// alert(responseText);
//         }else{

//         }
// }
// }
// request.open("POST","./processes/signupProcess.php",true);
// request.send(form);

// }

// fetch("/signUp.php", {
//     method: "POST",
//     body: form
// })
// .then(function(response) {
//     if (response.ok) {
//         return response.text();
//     }
//     throw new Error("Network response was not ok.");
// })
// .then(function(data) {
//     if (data.trim() === "success") {
//         window.location = "home.php";
//     } else {
//         document.getElementById("msg1").innerHTML = data;
//         document.getElementById("toast-default1").className = "block flex items-center shadow max-w-xs p-4 border-[1px] text-white bg-white rounded-lg   dark:bg-white";
//     }
// })
// .catch(function(error) {
//     console.error("Fetch error:", error);
// });



async function signUp() {
    const email = document.getElementById("email").value;
    const password = document.getElementById("password").value;
    const retype_password = document.getElementById("retype_password").value;

    try {
        const response = await AuthService.register({
            email,
            password,
            retype_password
        });

        if (response.success) {
            document.getElementById("msgToast").classList.remove("hidden");
            document.getElementById("msg").innerHTML = "Account Created Successfully";
            document.getElementById("msgToast").classList.add("border-green-500");
            document.getElementById("msgIcon").classList.add("bg-green-500");
            setTimeout(() => {
                document.getElementById("msgToast").classList.add("hidden");
                window.location.href = "/signin";
            }, 2500);
        }
    } catch (error) {
        document.getElementById("msgToast").classList.remove("hidden");
        document.getElementById("msg").innerHTML = error.message;
        setTimeout(() => {
            document.getElementById("msgToast").classList.add("hidden");
        }, 2500);
    }
}

