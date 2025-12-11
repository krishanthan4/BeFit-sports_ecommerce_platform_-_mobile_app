<?php include_once "./guis/partials/header.php"?>

<!-- Sign Up Part Start -->
<div class="flex sm:flex-row flex-col justify-center items-center w-full">
  <img draggable="false" src="/public/images/signin.jpg" class="w-[50%] sm:block hidden h-screen object-cover object-center " alt="">
<div class="flex min-h-[90vh] sm:w-[50%] w-full flex-col justify-center px-6 py-12 lg:px-8 " id="signInDiv">
  <div class="sm:mx-auto sm:w-full sm:max-w-sm">
    <h2 class="mt-10 text-center tracking-[1px] text-2xl font-thin leading-9 text-gray-200">BeFit | Sign Up</h2>
  </div>

  <div class="mt-10 space-y-4 sm:mx-auto sm:w-full sm:max-w-sm">
      <div>
        <label for="email" class="block text-sm font-medium leading-6 text-gray-400">Email address</label>
        <div class="mt-2">
          <input id="email" name="email" type="email" autocomplete="email" required class="block w-full rounded-md border-0 border-[#414247] py-1.5 bg-[#252629] text-gray-400 shadow-sm  sm:text-sm sm:leading-6">
        </div>
      </div>

      <div>
        <div class="flex items-center justify-between">
          <label for="password" class="block text-sm font-medium leading-6 text-gray-400">Password</label>
     
        </div>
        <div class="mt-2">
          <input id="password" name="password" type="password" autocomplete="current-password" required class="block w-full rounded-md border-0 border-[#414247] py-1.5 bg-[#252629] text-gray-400 shadow-sm  sm:text-sm sm:leading-6">
        </div>
      </div>
      <div>
        <label for="retype_password" class="block text-sm font-medium leading-6 text-gray-400">Retype Password</label>
        <div class="mt-2">
          <input id="retype_password" name="retype_password" type="password" autocomplete="retype-password" required class="block w-full rounded-md border-0 border-[#414247] py-1.5 bg-[#252629] text-gray-400 shadow-sm  sm:text-sm sm:leading-6">
        </div>
      </div>

      <div>
        <button type="submit" onclick="signUp();" class="flex w-full justify-center rounded-md bg-gray-300 px-3 py-1.5 text-sm font-semibold leading-6 text-black shadow-sm hover:bg-gray-50">Sign Up</button>
      </div>

      
    <p class="mt-10 text-center text-sm text-gray-500">
      already have a account?
<a href="/signin">      <button class="font-semibold leading-6 text-gray-400 hover:text-gray-300">Sign In</button></a>
    </p>
  </div>
  </div>

</div>
<!-- alert model -->
<?php require_once "./guis/partials/alert.php"; ?>
<!--alert model -->
<script src="./public/js/signUp.js"></script>
<!-- Sign Up end -->
<?php include_once "./guis/partials/footer.php"?>