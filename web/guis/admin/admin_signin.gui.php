<?php
include_once "./guis/partials/header.php";
include_once "./DbConnection.php";
?>
    <link rel="stylesheet" href="./public/css/style.css">

<!-- signIn part start -->
<div class="flex sm:flex-row flex-col justify-center items-center w-full">
  <img draggable="false" src="/public/images/signin.jpg" class="w-[50%] sm:block hidden h-screen object-cover object-center " alt="">
<div class="flex min-h-[90vh] sm:w-[50%] w-full flex-col justify-center px-6 py-12 lg:px-8 " id="signInDiv">
  <div class="sm:mx-auto sm:w-full sm:max-w-sm">
    <h2 class="mt-10 text-center tracking-[1px] text-2xl font-thin leading-9 text-gray-200">BeFit | Admin Sign In</h2>
  </div>

  <div class="mt-10 sm:mx-auto sm:w-full sm:max-w-sm">
    <div class="space-y-6" >
      <div>
        <label for="email" class="block text-sm font-medium leading-6 text-gray-200">Email address</label>
        <div class="mt-2">
<?php 
if($_COOKIE["admin"]){
}
?>
          <input id="adminEmail" name="email" type="email" autocomplete="email" 
            class="block w-full rounded-md border-0 border-[#414247] py-1.5 bg-[#252629] text-gray-400 shadow-sm  sm:text-sm sm:leading-6">
        </div>
      </div>
      <div>
        <button onclick="adminSignin();" 
          class="flex w-full justify-center rounded-md bg-gray-200 px-3 py-1.5 text-sm font-semibold leading-6 text-[#242529] shadow-sm hover:bg-[#121314] hover:border hover:border-[#242529] hover:text-gray-200">Send
          Verification Code</button>
      </div>
    </div>
    <p class="mt-10 text-center text-sm text-gray-500">
      <a href="/signin" class="font-semibold leading-6 text-gray-500 hover:text-gray-400">Back to Customer
        Signin</a>
    </p>
  </div>
</div>
</div>

<!-- model -->
<div class="fixed z-10 inset-0 overflow-y-auto hidden" id="verificationModal">
  <div class="flex items-center justify-center  min-h-screen pt-4 px-4 pb-20 text-center sm:block sm:p-0">
    <div class="fixed inset-0 transition-opacity" aria-hidden="true">
      <div class="absolute inset-0 bg-black opacity-75"></div>
    </div>
    <div
      class="inline-block  align-bottom bg-[#1e2024] rounded-lg text-left overflow-hidden shadow-xl transform transition-all sm:my-8 sm:align-middle sm:max-w-lg sm:w-full"
      role="dialog" aria-modal="true" aria-labelledby="modal-headline">
      <div class="absolute top-0 right-0 mt-4 mr-4">
        <button type="button" class="text-gray-400 hover:text-gray-500 focus:outline-none" aria-label="Close"
          onclick="closeModal()">
          <svg class="h-6 w-6" fill="none" viewBox="0 0 24 24" stroke="currentColor">
            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M6 18L18 6M6 6l12 12"></path>
          </svg>
        </button>
      </div>
      <div class="bg-[#1e2024] px-4 pt-5 pb-4 sm:p-6 sm:pb-4 w-full">
        <div class="sm:flex sm:items-start  w-full">
          <div class="mt-3 text-center sm:mt-0 sm:ml-4 sm:text-left  w-full">
            <h3 class="text-lg leading-6 font-medium text-gray-200" id="modal-headline">Admin Verification</h3>
            <div class="mt-2 max-w-lg">
              <label for="vcode" class="block text-sm font-medium text-gray-500">Enter Your Verification Code</label>
              <input type="text" name="vcode" id="verifyCode"
                class="mt-1 bg-[#25272c] block w-full shadow-sm sm:text-sm  rounded-md">
            </div>
          </div>
        </div>
      </div>
      <div class=" px-4 py-3 sm:px-6 sm:flex sm:flex-row-reverse">
        <button type="button" onclick="verifyAdminCode();"
          class="w-full inline-flex justify-center rounded-md border border-transparent shadow-sm px-4 py-2 bg-blue-600/90 text-base font-medium text-[#1e2024] hover:border hover:text-blue-600 hover:bg-transparent hover:border-blue-600/90 focus:outline-none sm:ml-3 sm:w-auto sm:text-sm">Verify</button>
        <button type="button"
          class="mt-3 w-full inline-flex justify-center rounded-md border border-gray-300 shadow-sm px-4 py-2 bg-[#1e2024] text-base font-medium text-gray-200 hover:text-[#1e2024] hover:bg-gray-50 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-indigo-500 sm:mt-0 sm:ml-3 sm:w-auto sm:text-sm"
          onclick="closeModal()">Close</button>
      </div>
    </div>
  </div>
</div>


<!-- model -->



<!-- alert model -->
<?php require_once "./guis/partials/alert.php"; ?>
<!--alert model -->

<script src="/public/js/adminSignin.js"></script>
<script src="/public/js/verifyAdminCode.js"></script>
<!-- signIn Part End -->
<?php
include_once "./guis/partials/footer.php";

?>