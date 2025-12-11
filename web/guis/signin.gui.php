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
    <h2 class="mt-10 text-center tracking-[1px] text-2xl font-thin leading-9 text-gray-200">BeFit | Sign In</h2>
  </div>


  <?php
$email = "";
$password = "";

if (!empty($_COOKIE["email"])) {
    $email = $_COOKIE["email"];
}

if (!empty($_COOKIE["password"])) {
    $password = $_COOKIE["password"];
}
  ?>
  <div class="mt-10 sm:mx-auto sm:w-full sm:max-w-sm space-y-6">
    <div>
      <label for="email" class="block text-sm font-medium leading-6 text-gray-300">Email address</label>
      <div class="mt-2">
        <input id="email" name="email" type="email" autocomplete="email" value="<?= $email ?>" required
          class="block w-full rounded-md border-0 border-[#414247] py-1.5 bg-[#252629] text-gray-400 shadow-sm  sm:text-sm sm:leading-6">
      </div>
    </div>

    <div>
      <div class="flex items-center justify-between">
        <label for="password" class="block text-sm font-medium leading-6 text-gray-300">Password</label>
        <div class="text-sm">
          <div onclick="forgotPassword();" class="font-semibold cursor-pointer text-gray-500 hover:text-gray-400">
            Forgot password?</div>
        </div>
      </div>
      <div class="mt-2">
        <input id="password" name="password" type="password" autocomplete="current-password" required
          value="<?= $password ?>"
          class="block w-full rounded-md border-0 border-[#414247] py-1.5 bg-[#252629] text-gray-400 shadow-sm  sm:text-sm sm:leading-6">
      </div>
    </div>
    <div class="flex">
      <div class="checkbox-wrapper-33 mt-2">
        <label class="checkbox">
          <input class="checkbox__trigger visuallyhidden" type="checkbox" id="rememberMe" />
          <span class="checkbox__symbol">
            <svg aria-hidden="true" class="icon-checkbox" width="28px" height="28px" viewBox="0 0 28 28" version="1"
              xmlns="http://www.w3.org/2000/svg">
              <path d="M4 14l8 7L24 7"></path>
            </svg>
          </span>
        </label>
      </div>
      <label class="block text-sm font-medium leading-6 text-gray-300 mt-2">Remember
        Me</label>
    </div>

    <div>
      <button onclick="signIn();"
        class="flex w-full justify-center rounded-md bg-gray-300 px-3 py-1.5 text-sm font-semibold leading-6 text-black shadow-sm hover:bg-gray-50">Sign
        in</button>
    </div>

    <p class="mt-10 text-center text-sm text-gray-500">
      Not a member? 
      <a href="/signup">
        <button class="font-semibold leading-6 text-gray-400 hover:text-gray-300"> Sign Up</button>
      </a>
    </p>

  </div>
</div>
</div>
<!-- signIn Part End -->
<!-- alert model -->
<?php require_once "./guis/partials/alert.php"; ?>
<!--alert model -->
<!-- forgotPassword Model -->
<?php require_once "./guis/partials/verify_code_model.php"; ?>
<?php require_once "./guis/partials/forgot_password_model.php"; ?>
<!-- forgotPassword Model -->
<!-- black cover -->
<div id="modal-backdrop" class="fixed inset-0 bg-black bg-opacity-30  hidden"></div>
<script src="./public/js/signIn.js"></script>
<script src="./public/js/verifyCode.js"></script>
<!-- <script src="./public/js/forgotPassword.js"></script> -->

<!-- Sign Up end -->
<?php include_once "./guis/partials/footer.php" ?>