<?php require_once __DIR__ . '/../config/database.php';
require_once "./common.php"; ?>
<!-- Mobile Menu start -->
<div class="hidden" id="MobileMenublack">
  <div class="fixed inset-0  flex z-[90] lg:hidden " role="dialog" aria-modal="true">
    <div class="fixed inset-0 bg-black bg-opacity-25" aria-hidden="true"></div>
    <div class="relative max-w-xs hidden w-full bg-[#2a2b30] shadow-xl pb-12 flex flex-col overflow-y-auto" id="MobileMenu">
      <div class="px-4 pt-5 pb-2 flex">
        <button type="button" class="-m-2 p-2  rounded-md inline-flex items-center justify-center text-gray-100"
          id="closeMobileMenuButton" onclick="closeMobileMenu();">
          <!-- Heroicon name: outline/x -->
          <svg class="h-6 w-6" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke="currentColor"
            aria-hidden="true">
            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M6 18L18 6M6 6l12 12" />
          </svg>
        </button>
      </div>

      <!-- 'Women' tab panel, show/hide based on tab state. -->
      <div id="tabs-1-panel-1" class="px-4 py-6 space-y-12" aria-labelledby="tabs-1-tab-1" role="tabpanel" tabindex="0">
        <div class="grid grid-cols-2 gap-x-4 gap-y-10">
<?php $nav_category_mobile_rs = Database::search("SELECT * FROM `category` LIMIT 6");
for ($nav_cat=0; $nav_cat < $nav_category_mobile_rs->num_rows; $nav_cat++) { 
$nav_category_mobile = $nav_category_mobile_rs->fetch_assoc(); ?>
   <div class="group relative">
              <img src="<?= $nav_category_mobile["cat_img"]?>"
                alt="Models sitting back to back, wearing Basic Tee in black and bone."
                class="object-center aspect-w-1 aspect-h-1 rounded-md bg-gray-100 min-h-[5.5rem] min-w-[5.5rem] group-hover:opacity-75 object-cover">
            <a href="search?query=<?= $nav_category_mobile["cat_name"]?>" class="mt-6 block text-sm font-medium text-gray-100">
              <span class="absolute z-10 inset-0" aria-hidden="true"></span>
<?= $nav_category_mobile["cat_name"]?>
            </a>
            <p aria-hidden="true" class="mt-1 text-sm text-gray-100">Shop now</p>
          </div>
  <?php } ?>
        </div>
      </div>
        <?php require_once "./guis/partials/nav_profile.php" ?>
      </div>

    </div>
  </div>

</div>


<!-- Main Nav Bar start -->
<header class="relative z-50 bg-[#242529] text-gray-100 shadow-md shadow-[#141516]">
  <nav aria-label="Top" class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
    <div class="">
      <div class="h-16 flex items-center">
        <!-- Mobile menu toggle, controls the 'mobileMenuOpen' state. -->
        <button type="button" class="bg-[#242529] p-2 rounded-md text-gray-100 lg:hidden" onclick="MobileMenuNav();">
          <!-- Heroicon name: outline/menu -->
          <svg class="h-6 w-6" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke="currentColor"
            aria-hidden="true">
            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M4 6h16M4 12h16M4 18h16" />
          </svg>
        </button>

        <!-- Logo -->
        <a href="/" class="ml-4 tracking-[1px] text-2xl font-thin flex lg:ml-0">
BeFit
</a>

        <!-- Flyout menus -->
        <div class="hidden lg:ml-8 lg:block lg:self-stretch">
          <div class="h-full flex space-x-8">
            <!-- Flyout menus -->
            <div class="hidden lg:flex-1 lg:block lg:self-stretch">
              <div class="h-full flex space-x-8">
              <div class="flex">
                  <div class="relative flex">
                    <button type="button"
                      class=" hover:text-white text-gray-300 relative z-10 flex items-center justify-center transition-colors ease-out duration-200 text-sm font-medium"
                      aria-expanded="false" onclick="flyoutMenuWeightHover();" onblur="flyoutMenuWeightRemove();">
                      Weight Training
                      <!-- Open: "bg-indigo-600", Closed: "" -->
                      <span
                        class="absolute bottom-0 inset-x-0 h-0.5 transition-colors ease-out duration-200 sm:mt-5 sm:transform sm:translate-y-px"
                        aria-hidden="true"></span>
                    </button>
                  </div>
                  <div class="absolute  top-full inset-x-0 bg-[#242529] text-sm text-gray-100 hidden"
                    id="flyoutMenuWeight">
                    <div class="relative">
                      <div class="max-w-7xl mx-auto px-8">
                        <div class="grid grid-cols-6 gap-y-10 gap-x-8 py-16">
<?php 
$navWeight_rs = Database::search("SELECT * FROM `product` INNER JOIN `product_img` ON `product`.`id`=`product_img`.`product_id` WHERE category_cat_id='2' AND `product`.`status_status_id`='1' LIMIT 6");
for ($weightNav=0; $weightNav < $navWeight_rs->num_rows; $weightNav++) {
  $navWeight= $navWeight_rs->fetch_assoc(); 
?>
<div class="group relative">
                            <div
                              class="aspect-w-1 aspect-h-1 rounded-md bg-gray-100 overflow-hidden group-hover:opacity-75">
                              <img src="<?= $navWeight["img_path"]?>"
                                alt="<?= $navWeight["title"]?>"
                                class="object-center object-cover min-h-[10rem]">
                            </div>
                            <a href="singleProduct?product_id=<?= $navWeight["product_id"]?>" class="mt-4 block font-medium text-gray-100">
                              <span class="absolute z-10 inset-0" aria-hidden="true"></span>
                            <?= $navWeight["title"]?>     </a>
                            <p aria-hidden="true" class="mt-1">Shop now</p>
                          </div>
<?php } ?>        
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
                <div class="flex">
                  <div class="relative flex">
                    <button type="button"
                      class=" hover:text-white text-gray-300 relative z-10 flex items-center justify-center transition-colors ease-out duration-200 text-sm font-medium"
                      aria-expanded="false" onclick="flyoutMenuCalisthenicsHover();" onblur="flyoutMenuCalisthenicsRemove();">
                      Calisthenics
                      <!-- Open: "bg-indigo-600", Closed: "" -->
                      <span
                        class="absolute bottom-0 inset-x-0 h-0.5 transition-colors ease-out duration-200 sm:mt-5 sm:transform sm:translate-y-px"
                        aria-hidden="true"></span>
                    </button>
                  </div>
                  <div class="absolute  top-full inset-x-0 bg-[#242529] text-sm text-gray-100 hidden"
                    id="flyoutMenuCalisthenics">
                    <div class="relative">
                      <div class="max-w-7xl mx-auto px-8">
                        <div class="grid grid-cols-6 gap-y-10 gap-x-8 py-16">
<?php 
$navCalisthenics_rs = Database::search("SELECT * FROM `product` INNER JOIN `product_img` ON `product`.`id`=`product_img`.`product_id` WHERE category_cat_id='1' AND `product`.`status_status_id`='1' LIMIT 6");
for ($calisNav=0; $calisNav < $navCalisthenics_rs->num_rows; $calisNav++) {
  $navCalisthenics= $navCalisthenics_rs->fetch_assoc(); 
?>
<div class="group relative">
                            <div
                              class="aspect-w-1 aspect-h-1 rounded-md bg-gray-100 overflow-hidden group-hover:opacity-75">
                              <img src="<?= $navCalisthenics["img_path"]?>"
                                alt="<?= $navCalisthenics["title"]?>"
                                class="object-center object-cover min-h-[10rem]">
                            </div>
                            <a href="singleProduct?product_id=<?= $navCalisthenics["product_id"]?>" class="mt-4 block font-medium text-gray-100">
                              <span class="absolute z-10 inset-0" aria-hidden="true"></span>
                            <?= $navCalisthenics["title"]?>     </a>
                            <p aria-hidden="true" class="mt-1">Shop now</p>
                          </div>
<?php } ?>                       
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
                <div class="flex">
                  <div class="relative flex">

                    <button type="button"
                      class=" hover:text-white text-gray-300 relative z-10 flex items-center justify-center transition-colors ease-out duration-200 text-sm font-medium"
                      aria-expanded="false" onclick="flyoutMenuSkateboardingHover();" onblur="flyoutMenuSkateboardingRemove();">
                      Skateboarding
                      <!-- Open: "bg-indigo-600", Closed: "" -->
                      <span
                        class="absolute bottom-0 inset-x-0 h-0.5 transition-colors ease-out duration-200 sm:mt-5 sm:transform sm:translate-y-px"
                        aria-hidden="true"></span>
                    </button>
                  </div>
                  <div class="absolute  top-full inset-x-0 bg-[#242529] text-sm text-gray-100 hidden"
                    id="flyoutMenuSkateboarding">
                    <div class="relative">
                      <div class="max-w-7xl mx-auto px-8">
                        <div class="grid grid-cols-6 gap-y-10 gap-x-8 py-16">
<?php 
$navSkateboarding_rs = Database::search("SELECT * FROM `product` INNER JOIN `product_img` ON `product`.`id`=`product_img`.`product_id` WHERE category_cat_id='3' AND `product`.`status_status_id`='1' LIMIT 6");
for ($SkateboardingNav=0; $SkateboardingNav < $navSkateboarding_rs->num_rows; $SkateboardingNav++) {
  $navSkateboarding= $navSkateboarding_rs->fetch_assoc(); 
?>
<div class="group relative">
                            <div
                              class="aspect-w-1 aspect-h-1 rounded-md bg-gray-100 overflow-hidden group-hover:opacity-75">
                              <img src="<?= $navSkateboarding["img_path"]?>"
                                alt="<?= $navSkateboarding["title"]?>"
                                class="object-center object-cover min-h-[10rem]">
                            </div>
                            <a href="singleProduct?product_id=<?= $navSkateboarding["product_id"]?>" class="mt-4 block font-medium text-gray-100">
                              <span class="absolute z-10 inset-0" aria-hidden="true"></span>
                            <?= $navSkateboarding["title"]?></a>
                            <p aria-hidden="true" class="mt-1">Shop now</p>
                          </div>
<?php } ?>                       
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
        <?php require_once "./guis/partials/mainSearchBar.php" ?>
        <div class="ml-auto flex items-center">
          <?php if (isset($_SESSION["user"]) && $_SESSION["user"]) {
            $result = Database::search("SELECT * FROM profile_img WHERE user_email='" . $_SESSION["user"]["email"] . "'");
            $path = $result->fetch_assoc();
            $sellingHistoryNav_rs = Database::search("SELECT `user_email` FROM `product` WHERE `user_email`=?",[$_SESSION["user"]["email"]]);
            if ($result->num_rows !== 0 && $path["path"] !== "resources/new_user.png") { ?>
              <!-- Account -->
              <button id="dropdownUserAvatarButton" data-dropdown-toggle="dropdownAvatar" class=" text-sm bg-[#242529] rounded-full md:block hidden md:me-0 focus:ring-1 focus:ring-gray-300" type="button">

                <img draggable="false" class=" border-2 border-gray-500 object-center object-cover w-10 h-10 rounded-full"
                  src="<?= $path["path"] ?>" alt="<?= $path["user_email"] ?>">
              </button>

              <!-- Dropdown menu -->
              <div id="dropdownAvatar" class=" hidden z-30 bg-[#242529] divide-y  rounded-lg shadow w-44 ">
                <ul class="py-2 text-sm " aria-labelledby="dropdownUserAvatarButton">
                  <a href="/userProfile">
                    <li class=" flex items-center hover:bg-[#35353d] ">
                      <svg class="text-gray-100 group-hover:text-gray-100 flex-shrink-0 ml-2"
                        xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke="currentColor" width="16"
                        height="16" aria-hidden="true">
                        <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                          d="M5.121 17.804A13.937 13.937 0 0112 16c2.5 0 4.847.655 6.879 1.804M15 10a3 3 0 11-6 0 3 3 0 016 0zm6 2a9 9 0 11-18 0 9 9 0 0118 0z" />
                      </svg>
                      <p class="block px-4 py-2 hover:bg-[#35353d] ">Profile</p>
                    </li>
                  </a>

                  <a href="/myProducts">
                    <li class=" flex items-center hover:bg-[#35353d] ">
                      <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor"
                        class="bi bi-dropbox  text-gray-100  ml-2 group-hover:text-gray-100 flex-shrink-0"
                        viewBox="0 0 16 16">
                        <path
                          d="M8.01 4.555 4.005 7.11 8.01 9.665 4.005 12.22 0 9.651l4.005-2.555L0 4.555 4.005 2zm-4.026 8.487 4.006-2.555 4.005 2.555-4.005 2.555zm4.026-3.39 4.005-2.556L8.01 4.555 11.995 2 16 4.555 11.995 7.11 16 9.665l-4.005 2.555z" />
                      </svg>
                      <p class="block px-4 py-2 hover:bg-[#35353d] d">My Products</p>
                    </li>
                  </a>
                  <a href="/wishlist">
                    <li class="  flex items-center hover:bg-[#35353d] ">
                      <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor"
                        class="bi bi-dropbox  text-gray-100  ml-2 group-hover:text-gray-100 flex-shrink-0"
                        viewBox="0 0 16 16">
                        <path
                          d="m8 2.748-.717-.737C5.6.281 2.514.878 1.4 3.053c-.523 1.023-.641 2.5.314 4.385.92 1.815 2.834 3.989 6.286 6.357 3.452-2.368 5.365-4.542 6.286-6.357.955-1.886.838-3.362.314-4.385C13.486.878 10.4.28 8.717 2.01zM8 15C-7.333 4.868 3.279-3.04 7.824 1.143q.09.083.176.171a3 3 0 0 1 .176-.17C12.72-3.042 23.333 4.867 8 15" />
                      </svg>
                      <p class="block px-4 py-2 ">Wishlist</p>
                    </li>
                  </a>

                  <a href="/purchasedHistory">
                    <li class="flex items-center hover:bg-[#35353d] ">
                      <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor"
                        class="bi bi-dropbox  text-gray-100  ml-2 group-hover:text-gray-100 flex-shrink-0"
                        viewBox="0 0 16 16">
                        <path
                          d="M8 1a2 2 0 0 1 2 2v2H6V3a2 2 0 0 1 2-2m3 4V3a3 3 0 1 0-6 0v2H3.36a1.5 1.5 0 0 0-1.483 1.277L.85 13.13A2.5 2.5 0 0 0 3.322 16h9.355a2.5 2.5 0 0 0 2.473-2.87l-1.028-6.853A1.5 1.5 0 0 0 12.64 5zm-1 1v1.5a.5.5 0 0 0 1 0V6h1.639a.5.5 0 0 1 .494.426l1.028 6.851A1.5 1.5 0 0 1 12.678 15H3.322a1.5 1.5 0 0 1-1.483-1.723l1.028-6.851A.5.5 0 0 1 3.36 6H5v1.5a.5.5 0 1 0 1 0V6z" />
                      </svg>
                      <p class="block px-4 py-2 ">Purchased History</p>
                    </li>
                  </a>
                  <?php if($sellingHistoryNav_rs->num_rows!==0){ ?>
                  <a href="/sellingHistory">
                    <li class="  flex items-center hover:bg-[#35353d] ">
                    <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-rocket  text-gray-100  ml-2 group-hover:text-gray-100 flex-shrink-0" viewBox="0 0 16 16">
  <path d="M8 8c.828 0 1.5-.895 1.5-2S8.828 4 8 4s-1.5.895-1.5 2S7.172 8 8 8"/>
  <path d="M11.953 8.81c-.195-3.388-.968-5.507-1.777-6.819C9.707 1.233 9.23.751 8.857.454a3.5 3.5 0 0 0-.463-.315A2 2 0 0 0 8.25.064.55.55 0 0 0 8 0a.55.55 0 0 0-.266.073 2 2 0 0 0-.142.08 4 4 0 0 0-.459.33c-.37.308-.844.803-1.31 1.57-.805 1.322-1.577 3.433-1.774 6.756l-1.497 1.826-.004.005A2.5 2.5 0 0 0 2 12.202V15.5a.5.5 0 0 0 .9.3l1.125-1.5c.166-.222.42-.4.752-.57.214-.108.414-.192.625-.281l.198-.084c.7.428 1.55.635 2.4.635s1.7-.207 2.4-.635q.1.044.196.083c.213.09.413.174.627.282.332.17.586.348.752.57l1.125 1.5a.5.5 0 0 0 .9-.3v-3.298a2.5 2.5 0 0 0-.548-1.562zM12 10.445v.055c0 .866-.284 1.585-.75 2.14.146.064.292.13.425.199.39.197.8.46 1.1.86L13 14v-1.798a1.5 1.5 0 0 0-.327-.935zM4.75 12.64C4.284 12.085 4 11.366 4 10.5v-.054l-.673.82a1.5 1.5 0 0 0-.327.936V14l.225-.3c.3-.4.71-.664 1.1-.861.133-.068.279-.135.425-.199M8.009 1.073q.096.06.226.163c.284.226.683.621 1.09 1.28C10.137 3.836 11 6.237 11 10.5c0 .858-.374 1.48-.943 1.893C9.517 12.786 8.781 13 8 13s-1.517-.214-2.057-.607C5.373 11.979 5 11.358 5 10.5c0-4.182.86-6.586 1.677-7.928.409-.67.81-1.082 1.096-1.32q.136-.113.236-.18Z"/>
  <path d="M9.479 14.361c-.48.093-.98.139-1.479.139s-.999-.046-1.479-.139L7.6 15.8a.5.5 0 0 0 .8 0z"/>
</svg>
                      <p class="block px-4 py-2 ">selling History</p>
                    </li>
                  </a>
                  <?php }

     $orderStatusNav_rs = Database::search("SELECT `invoice`.*,`product`.`user_email` AS `owner_email`,`invoice_has_products`.*,`product`.* FROM `invoice` 
     INNER JOIN `invoice_has_products` 
     ON `invoice`.`invoice_id`=`invoice_has_products`.`invoice_id` 
     INNER JOIN `product` 
     ON `invoice_has_products`.`product_id`=`product`.`id` 
     WHERE `product`.`user_email`=?", [$_SESSION["user"]["email"]]);

     if($orderStatusNav_rs->num_rows!==0){ ?>
                  <a href="/orderStatus">
                    <li class="flex items-center hover:bg-[#35353d] ">
<svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5"  width="16" height="16" stroke="currentColor" class="text-gray-100  ml-2 group-hover:text-gray-100 flex-shrink-0">
  <path stroke-linecap="round" stroke-linejoin="round" d="M4.098 19.902a3.75 3.75 0 0 0 5.304 0l6.401-6.402M6.75 21A3.75 3.75 0 0 1 3 17.25V4.125C3 3.504 3.504 3 4.125 3h5.25c.621 0 1.125.504 1.125 1.125v4.072M6.75 21a3.75 3.75 0 0 0 3.75-3.75V8.197M6.75 21h13.125c.621 0 1.125-.504 1.125-1.125v-5.25c0-.621-.504-1.125-1.125-1.125h-4.072M10.5 8.197l2.88-2.88c.438-.439 1.15-.439 1.59 0l3.712 3.713c.44.44.44 1.152 0 1.59l-2.879 2.88M6.75 17.25h.008v.008H6.75v-.008Z" />
</svg>
                      <p class="block px-4 py-2 ">Order Status</p>
                    </li>
                  </a>
                  <?php } ?>
        
                  <a href="/contactAdmin">
                    <li class="  flex items-center hover:bg-[#35353d] ">
                      <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor"
                        class="bi bi-dropbox  text-gray-100  ml-2 group-hover:text-gray-100 flex-shrink-0"
                        viewBox="0 0 16 16">
                        <path
                          d="M3.654 1.328a.678.678 0 0 0-1.015-.063L1.605 2.3c-.483.484-.661 1.169-.45 1.77a17.6 17.6 0 0 0 4.168 6.608 17.6 17.6 0 0 0 6.608 4.168c.601.211 1.286.033 1.77-.45l1.034-1.034a.678.678 0 0 0-.063-1.015l-2.307-1.794a.68.68 0 0 0-.58-.122l-2.19.547a1.75 1.75 0 0 1-1.657-.459L5.482 8.062a1.75 1.75 0 0 1-.46-1.657l.548-2.19a.68.68 0 0 0-.122-.58zM1.884.511a1.745 1.745 0 0 1 2.612.163L6.29 2.98c.329.423.445.974.315 1.494l-.547 2.19a.68.68 0 0 0 .178.643l2.457 2.457a.68.68 0 0 0 .644.178l2.189-.547a1.75 1.75 0 0 1 1.494.315l2.306 1.794c.829.645.905 1.87.163 2.611l-1.034 1.034c-.74.74-1.846 1.065-2.877.702a18.6 18.6 0 0 1-7.01-4.42 18.6 18.6 0 0 1-4.42-7.009c-.362-1.03-.037-2.137.703-2.877z" />
                      </svg>
                      <p class="block px-4 py-2 ">Contact Admin</p>
                    </li>
                  </a>
                  <a onclick="signOut();" class="cursor-pointer">
                    <li class="  flex items-center hover:bg-[#35353d] ">
                    <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" width="16" height="16"
                      stroke="currentColor" class=" text-gray-100  ml-2 group-hover:text-gray-100 flex-shrink-0">
                      <path stroke-linecap="round" stroke-linejoin="round"
                        d="M15.75 9V5.25A2.25 2.25 0 0 0 13.5 3h-6a2.25 2.25 0 0 0-2.25 2.25v13.5A2.25 2.25 0 0 0 7.5 21h6a2.25 2.25 0 0 0 2.25-2.25V15m3 0 3-3m0 0-3-3m3 3H9" />
                    </svg>
                      <p class="block px-4 py-2 ">Sign out</p>
                    </li>
                  </a>
                </ul>
              </div>
              <?php } else { ?>

<button id="dropdownUserAvatarButton" data-dropdown-toggle="dropdownAvatar"
                class=" text-sm bg-[#242529] rounded-full md:block hidden md:me-0 focus:ring-1 focus:ring-gray-300 "
                type="button">

                <img draggable="false" class="grayscale border-2 border-gray-500 object-center object-cover w-10 h-10 rounded-full"
                  src="./resources/new_user.png">
              </button>

              <!-- Dropdown menu -->
              <div id="dropdownAvatar" class=" hidden z-30 bg-[#242529] divide-y divide-gray-100 rounded-lg shadow w-44 ">
                <ul class="py-2 text-sm " aria-labelledby="dropdownUserAvatarButton">
                  <a href="/userProfile">
                    <li class=" flex items-center hover:bg-[#35353d] ">
                      <svg class="text-gray-100 group-hover:text-gray-100 flex-shrink-0 ml-2"
                        xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke="currentColor" width="16"
                        height="16" aria-hidden="true">
                        <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                          d="M5.121 17.804A13.937 13.937 0 0112 16c2.5 0 4.847.655 6.879 1.804M15 10a3 3 0 11-6 0 3 3 0 016 0zm6 2a9 9 0 11-18 0 9 9 0 0118 0z" />
                      </svg>
                      <p class="block px-4 py-2 hover:bg-[#35353d] ">Profile</p>
                    </li>
                  </a>

                  <a href="/myProducts">
                    <li class=" flex items-center hover:bg-[#35353d] ">
                      <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor"
                        class="bi bi-dropbox  text-gray-100  ml-2 group-hover:text-gray-100 flex-shrink-0"
                        viewBox="0 0 16 16">
                        <path
                          d="M8.01 4.555 4.005 7.11 8.01 9.665 4.005 12.22 0 9.651l4.005-2.555L0 4.555 4.005 2zm-4.026 8.487 4.006-2.555 4.005 2.555-4.005 2.555zm4.026-3.39 4.005-2.556L8.01 4.555 11.995 2 16 4.555 11.995 7.11 16 9.665l-4.005 2.555z" />
                      </svg>
                      <p class="block px-4 py-2 hover:bg-[#35353d] d">My Products</p>
                    </li>
                  </a>
                  <a href="/wishlist">
                    <li class="  flex items-center hover:bg-[#35353d] ">
                      <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor"
                        class="bi bi-dropbox  text-gray-100  ml-2 group-hover:text-gray-100 flex-shrink-0"
                        viewBox="0 0 16 16">
                        <path
                          d="m8 2.748-.717-.737C5.6.281 2.514.878 1.4 3.053c-.523 1.023-.641 2.5.314 4.385.92 1.815 2.834 3.989 6.286 6.357 3.452-2.368 5.365-4.542 6.286-6.357.955-1.886.838-3.362.314-4.385C13.486.878 10.4.28 8.717 2.01zM8 15C-7.333 4.868 3.279-3.04 7.824 1.143q.09.083.176.171a3 3 0 0 1 .176-.17C12.72-3.042 23.333 4.867 8 15" />
                      </svg>
                      <p class="block px-4 py-2 ">Wishlist</p>
                    </li>
                  </a>

                  <a href="/purchasedHistory">
                    <li class="  flex items-center hover:bg-[#35353d] ">
                      <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor"
                        class="bi bi-dropbox  text-gray-100  ml-2 group-hover:text-gray-100 flex-shrink-0"
                        viewBox="0 0 16 16">
                        <path
                          d="M8 1a2 2 0 0 1 2 2v2H6V3a2 2 0 0 1 2-2m3 4V3a3 3 0 1 0-6 0v2H3.36a1.5 1.5 0 0 0-1.483 1.277L.85 13.13A2.5 2.5 0 0 0 3.322 16h9.355a2.5 2.5 0 0 0 2.473-2.87l-1.028-6.853A1.5 1.5 0 0 0 12.64 5zm-1 1v1.5a.5.5 0 0 0 1 0V6h1.639a.5.5 0 0 1 .494.426l1.028 6.851A1.5 1.5 0 0 1 12.678 15H3.322a1.5 1.5 0 0 1-1.483-1.723l1.028-6.851A.5.5 0 0 1 3.36 6H5v1.5a.5.5 0 1 0 1 0V6z" />
                      </svg>
                      <p class="block px-4 py-2 ">Purchased History</p>
                    </li>
                  </a>
                  <?php  if($sellingHistoryNav_rs->num_rows!==0){ ?>
                  <a href="/sellingHistory">
                    <li class="  flex items-center hover:bg-[#35353d] ">
                    <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-rocket   text-gray-100  ml-2 group-hover:text-gray-100 flex-shrink-0" viewBox="0 0 16 16">
  <path d="M8 8c.828 0 1.5-.895 1.5-2S8.828 4 8 4s-1.5.895-1.5 2S7.172 8 8 8"/>
  <path d="M11.953 8.81c-.195-3.388-.968-5.507-1.777-6.819C9.707 1.233 9.23.751 8.857.454a3.5 3.5 0 0 0-.463-.315A2 2 0 0 0 8.25.064.55.55 0 0 0 8 0a.55.55 0 0 0-.266.073 2 2 0 0 0-.142.08 4 4 0 0 0-.459.33c-.37.308-.844.803-1.31 1.57-.805 1.322-1.577 3.433-1.774 6.756l-1.497 1.826-.004.005A2.5 2.5 0 0 0 2 12.202V15.5a.5.5 0 0 0 .9.3l1.125-1.5c.166-.222.42-.4.752-.57.214-.108.414-.192.625-.281l.198-.084c.7.428 1.55.635 2.4.635s1.7-.207 2.4-.635q.1.044.196.083c.213.09.413.174.627.282.332.17.586.348.752.57l1.125 1.5a.5.5 0 0 0 .9-.3v-3.298a2.5 2.5 0 0 0-.548-1.562zM12 10.445v.055c0 .866-.284 1.585-.75 2.14.146.064.292.13.425.199.39.197.8.46 1.1.86L13 14v-1.798a1.5 1.5 0 0 0-.327-.935zM4.75 12.64C4.284 12.085 4 11.366 4 10.5v-.054l-.673.82a1.5 1.5 0 0 0-.327.936V14l.225-.3c.3-.4.71-.664 1.1-.861.133-.068.279-.135.425-.199M8.009 1.073q.096.06.226.163c.284.226.683.621 1.09 1.28C10.137 3.836 11 6.237 11 10.5c0 .858-.374 1.48-.943 1.893C9.517 12.786 8.781 13 8 13s-1.517-.214-2.057-.607C5.373 11.979 5 11.358 5 10.5c0-4.182.86-6.586 1.677-7.928.409-.67.81-1.082 1.096-1.32q.136-.113.236-.18Z"/>
  <path d="M9.479 14.361c-.48.093-.98.139-1.479.139s-.999-.046-1.479-.139L7.6 15.8a.5.5 0 0 0 .8 0z"/>
</svg>
                      <p class="block px-4 py-2 ">selling History</p>
                    </li>
                  </a>
                  <?php } ?>
        
                  <a href="/contactAdmin">
                    <li class="  flex items-center hover:bg-[#35353d] ">
                      <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor"
                        class="bi bi-dropbox  text-gray-100  ml-2 group-hover:text-gray-100 flex-shrink-0"
                        viewBox="0 0 16 16">
                        <path
                          d="M3.654 1.328a.678.678 0 0 0-1.015-.063L1.605 2.3c-.483.484-.661 1.169-.45 1.77a17.6 17.6 0 0 0 4.168 6.608 17.6 17.6 0 0 0 6.608 4.168c.601.211 1.286.033 1.77-.45l1.034-1.034a.678.678 0 0 0-.063-1.015l-2.307-1.794a.68.68 0 0 0-.58-.122l-2.19.547a1.75 1.75 0 0 1-1.657-.459L5.482 8.062a1.75 1.75 0 0 1-.46-1.657l.548-2.19a.68.68 0 0 0-.122-.58zM1.884.511a1.745 1.745 0 0 1 2.612.163L6.29 2.98c.329.423.445.974.315 1.494l-.547 2.19a.68.68 0 0 0 .178.643l2.457 2.457a.68.68 0 0 0 .644.178l2.189-.547a1.75 1.75 0 0 1 1.494.315l2.306 1.794c.829.645.905 1.87.163 2.611l-1.034 1.034c-.74.74-1.846 1.065-2.877.702a18.6 18.6 0 0 1-7.01-4.42 18.6 18.6 0 0 1-4.42-7.009c-.362-1.03-.037-2.137.703-2.877z" />
                      </svg>
                      <p class="block px-4 py-2 ">Contact Admin</p>
                    </li>
                  </a>
                  <a onclick="signOut();" class="cursor-pointer">
                    <li class="  flex items-center hover:bg-[#35353d] ">
                    <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" width="16" height="16"
                      stroke="currentColor" class=" text-gray-100  ml-2 group-hover:text-gray-100 flex-shrink-0">
                      <path stroke-linecap="round" stroke-linejoin="round"
                        d="M15.75 9V5.25A2.25 2.25 0 0 0 13.5 3h-6a2.25 2.25 0 0 0-2.25 2.25v13.5A2.25 2.25 0 0 0 7.5 21h6a2.25 2.25 0 0 0 2.25-2.25V15m3 0 3-3m0 0-3-3m3 3H9" />
                    </svg>
                      <p class="block px-4 py-2 text-sm ">Sign out</p>
                    </li>
                  </a>
                </ul>
           
              </div>
              <?php
            }

          } else { ?>
            <div class="hidden lg:flex lg:flex-1 lg:items-center lg:justify-end lg:space-x-6">
              <a href="/signin" class="text-sm font-medium  hover:text-white text-gray-300">Sign in</a>
              <span class="h-6 w-px bg-gray-200" aria-hidden="true"></span>
              <a href="#" class="text-sm font-medium  hover:text-white text-gray-300">Create account</a>
            </div>
          <?php } ?>
          <!-- Cart -->
          <div class="ml-4 flow-root lg:ml-6">
            <a href="/cart" class="group -m-2 p-2 flex items-center">
              <svg class="flex-shrink-0 h-6 w-6 text-gray-100 group-hover:text-gray-100"
                xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke="currentColor"
                aria-hidden="true">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                  d="M16 11V7a4 4 0 00-8 0v4M5 9h14l1 12H4L5 9z" />
              </svg>
              <?php
              try {
                if (isset($_SESSION["user"]) && $_SESSION["user"]) {


                  $cartNumber_rs = Database::search("SELECT  COUNT(user_email) FROM cart WHERE user_email='" . $_SESSION["user"]["email"] . "'");
                  $cartNumber = $cartNumber_rs->fetch_assoc();
                  ?>
                  <span class="-ms-3 -mt-4 <?php if ($cartNumber["COUNT(user_email)"] > 0) {
                    ?>bg-red-500/90<?php } ?> font-bold w-[1.1rem] text-center h-[1.1rem] rounded-full text-sm  text-[#1a1b1d]">
                    <?php
                    if ($cartNumber["COUNT(user_email)"] > 0) {
                      echo ($cartNumber["COUNT(user_email)"]);
                    }
                    ?>
                  </span>
                </a>
              <?php } else { ?>
                <span class="-ms-3 -mt-4 w-[1.1rem] text-center h-[1.1rem] rounded-full text-sm  text-white">
                </span>
                </a>
                <?php } } catch (\Throwable $th) { } ?>
          </div>
        </div>
      </div>
    </div>
  </nav>
</header>

<?php // require_once "./guis/partials/mobileSearchBar.php" ?>