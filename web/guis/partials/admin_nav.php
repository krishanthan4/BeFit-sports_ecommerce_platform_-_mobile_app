<?php 
if(isset($_SESSION["admin"])){
  require_once "common.php";
  require_once "DbConnection.php";
  
  $adminPhoto_rs = Database::search("SELECT * FROM `admin`");
  $adminPhoto = $adminPhoto_rs->fetch_assoc();
  ?>
  <div class="lg:flex lg:flex-row lg:justify-stretch  lg:items-center overflow-hidden">
    <div class="">
  
      <div class="inset-0  z-50 hidden lg:hidden" role="dialog" id="navDiv" aria-modal="true">
        <div class="fixed inset-0 bg-black z-40 bg-opacity-50" aria-hidden="true"></div>
  
        <div class="relative z-50 flex-1 h-full flex flex-col max-w-xs ">
          <div class="absolute top-0 right-0 -mr-12 pt-2">
            <button type="button"
              class="ml-1 flex items-center justify-center h-10 w-10 rounded-full focus:outline-none focus:ring-2 focus:ring-inset focus:ring-white"
              onclick="toggleNavClose();">
              <span class="sr-only">Close sidebar</span>
              <svg class="h-6 w-6 text-white" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24"
                stroke="currentColor" aria-hidden="true">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M6 18L18 6M6 6l12 12" />
              </svg>
            </button>
          </div>
  
  
          <div class="flex h-full max-w-60 min-w-60 fixed z-50">
            <!-- Sidebar component, swap this element with another sidebar if you like -->
            <div class="flex-1 flex flex-col min-h-0">
            <div style="background-image: url('/public/images/favicon.ico');"
            class="flex justify-center bg-center bg-cover opacity-80 text-gray-200 tracking-[1px] text-2xl font-thin items-center h-16 flex-shrink-0 px-4 bg-gray-900">
              </div>
              <div class="flex-1 flex flex-col overflow-y-auto bg-[#2d2e33]">
              <nav class="flex-1 px-2 py-4">
              <div class="space-y-1">
                <!-- Current: "hover:bg-[#1e1f22] text-white", Default: "text-gray-300 hover:bg-[#727681]  hover:text-white" -->
                <div class="flex flex-row justify-center">
                  <?php 
                  if(empty($adminPhoto["admin_img"]) || $adminPhoto["admin_img"]=="/resources/new_user.png"){
  ?>
       <button class="rounded-full w-10 h-10 grayscale mb-4 border-gray-600 border-1 overflow-hidden">
                    <img src="/resources/new_user.png" class="object-contain" alt="">
                  </button>
    
  <?php
                  }else{
                    ?>
                     <button class="rounded-full w-10 h-10  mb-4 border-gray-600 border-1">
                    <img src="<?= $adminPhoto["admin_img"]?>" class="object-cover rounded-full w-full h-full" alt="">
                  </button>
                    <?php
                  }
                  ?>
                
                </div>
                <a href="/adminDashboard"
                  class="<?= urlis('/adminDashboard') ?
                    "bg-[#3a3c42] hover:bg-[#1e1f22] " : "hover:bg-[#727681] " ?> text-white group flex items-center px-2 py-2 text-sm font-medium rounded-md"
                  aria-current="page">
  
                  <svg class="text-gray-300 mr-3 flex-shrink-0 h-6 w-6" xmlns="http://www.w3.org/2000/svg" fill="none"
                    viewBox="0 0 24 24" stroke="currentColor" aria-hidden="true">
                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                      d="M3 12l2-2m0 0l7-7 7 7M5 10v10a1 1 0 001 1h3m10-11l2 2m-2-2v10a1 1 0 01-1 1h-3m-6 0a1 1 0 001-1v-4a1 1 0 011-1h2a1 1 0 011 1v4a1 1 0 001 1m-6 0h6" />
                  </svg>
                  Dashboard
                </a>
  
  
                <a href="/manageUsers"
                  class=" <?= urlis('/manageUsers') ?
                    "hover:bg-[#1e1f22] bg-[#3a3c42]" : "hover:bg-[#727681] " ?>  text-gray-300 hover:bg-[#727681]  hover:text-white group flex items-center px-2 py-2 text-sm font-medium rounded-md">
                  <!-- Heroicon name: outline/user-circle -->
                      <!-- Heroicon name: outline/archive -->
                      <svg class="text-gray-400 group-hover:text-gray-300 mr-3 flex-shrink-0 h-6 w-6 bi bi-people"
                    xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" 
                    viewBox="0 0 16 16">
                    <path
                      d="M15 14s1 0 1-1-1-4-5-4-5 3-5 4 1 1 1 1zm-7.978-1L7 12.996c.001-.264.167-1.03.76-1.72C8.312 10.629 9.282 10 11 10c1.717 0 2.687.63 3.24 1.276.593.69.758 1.457.76 1.72l-.008.002-.014.002zM11 7a2 2 0 1 0 0-4 2 2 0 0 0 0 4m3-2a3 3 0 1 1-6 0 3 3 0 0 1 6 0M6.936 9.28a6 6 0 0 0-1.23-.247A7 7 0 0 0 5 9c-4 0-5 3-5 4q0 1 1 1h4.216A2.24 2.24 0 0 1 5 13c0-1.01.377-2.042 1.09-2.904.243-.294.526-.569.846-.816M4.92 10A5.5 5.5 0 0 0 4 13H1c0-.26.164-1.03.76-1.724.545-.636 1.492-1.256 3.16-1.275ZM1.5 5.5a3 3 0 1 1 6 0 3 3 0 0 1-6 0m3-2a2 2 0 1 0 0 4 2 2 0 0 0 0-4" />
                  </svg>
                  Manage Users
                </a>
  
                <a href="/manageSellers"
                  class=" <?= urlis('/manageSellers') ?
                    "hover:bg-[#1e1f22] bg-[#3a3c42]" : "hover:bg-[#727681] " ?>  text-gray-300 hover:bg-[#727681]  hover:text-white group flex items-center px-2 py-2 text-sm font-medium rounded-md">
                  <!-- Heroicon name: outline/user-circle -->
                  <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" class="text-gray-400 group-hover:text-gray-300 mr-3 flex-shrink-0 h-6 w-6">
    <path stroke-linecap="round" stroke-linejoin="round" d="M18 18.72a9.094 9.094 0 0 0 3.741-.479 3 3 0 0 0-4.682-2.72m.94 3.198.001.031c0 .225-.012.447-.037.666A11.944 11.944 0 0 1 12 21c-2.17 0-4.207-.576-5.963-1.584A6.062 6.062 0 0 1 6 18.719m12 0a5.971 5.971 0 0 0-.941-3.197m0 0A5.995 5.995 0 0 0 12 12.75a5.995 5.995 0 0 0-5.058 2.772m0 0a3 3 0 0 0-4.681 2.72 8.986 8.986 0 0 0 3.74.477m.94-3.197a5.971 5.971 0 0 0-.94 3.197M15 6.75a3 3 0 1 1-6 0 3 3 0 0 1 6 0Zm6 3a2.25 2.25 0 1 1-4.5 0 2.25 2.25 0 0 1 4.5 0Zm-13.5 0a2.25 2.25 0 1 1-4.5 0 2.25 2.25 0 0 1 4.5 0Z" />
  </svg>
  
                  Manage Sellers
                </a>
                <a href="/manageProduct"
                  class=" <?= urlis('/manageProduct') ?
                    "hover:bg-[#1e1f22] bg-[#3a3c42]" : "hover:bg-[#727681] " ?> text-gray-300 hover:bg-[#727681]  hover:text-white group flex items-center px-2 py-2 text-sm font-medium rounded-md">
                  <!-- Heroicon name: outline/archive -->
                  <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" class="text-gray-400 group-hover:text-gray-300 mr-3 flex-shrink-0 h-6 w-6">
    <path stroke-linecap="round" stroke-linejoin="round" d="m7.875 14.25 1.214 1.942a2.25 2.25 0 0 0 1.908 1.058h2.006c.776 0 1.497-.4 1.908-1.058l1.214-1.942M2.41 9h4.636a2.25 2.25 0 0 1 1.872 1.002l.164.246a2.25 2.25 0 0 0 1.872 1.002h2.092a2.25 2.25 0 0 0 1.872-1.002l.164-.246A2.25 2.25 0 0 1 16.954 9h4.636M2.41 9a2.25 2.25 0 0 0-.16.832V12a2.25 2.25 0 0 0 2.25 2.25h15A2.25 2.25 0 0 0 21.75 12V9.832c0-.287-.055-.57-.16-.832M2.41 9a2.25 2.25 0 0 1 .382-.632l3.285-3.832a2.25 2.25 0 0 1 1.708-.786h8.43c.657 0 1.281.287 1.709.786l3.284 3.832c.163.19.291.404.382.632M4.5 20.25h15A2.25 2.25 0 0 0 21.75 18v-2.625c0-.621-.504-1.125-1.125-1.125H3.375c-.621 0-1.125.504-1.125 1.125V18a2.25 2.25 0 0 0 2.25 2.25Z" />
  </svg>
  
  
                  Manage Products
                </a>
  
                <a href="/manageCategory"
                  class="  <?= urlis('/manageCategory') ?
                    "hover:bg-[#1e1f22] bg-[#3a3c42]" : "hover:bg-[#727681] " ?> text-gray-300 hover:bg-[#727681]  hover:text-white group flex items-center px-2 py-2 text-sm font-medium rounded-md">
                  <!-- Heroicon name: outline/clock -->
                  <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" class="text-gray-400 group-hover:text-gray-300 mr-3 flex-shrink-0 h-6 w-6">
    <path stroke-linecap="round" stroke-linejoin="round" d="M9.568 3H5.25A2.25 2.25 0 0 0 3 5.25v4.318c0 .597.237 1.17.659 1.591l9.581 9.581c.699.699 1.78.872 2.607.33a18.095 18.095 0 0 0 5.223-5.223c.542-.827.369-1.908-.33-2.607L11.16 3.66A2.25 2.25 0 0 0 9.568 3Z" />
    <path stroke-linecap="round" stroke-linejoin="round" d="M6 6h.008v.008H6V6Z" />
  </svg>
  
                  Manage Categories
                </a>
                <a href="/manageReviews"
                  class="  <?= urlis('/manageReviews') ?
                    "hover:bg-[#1e1f22] bg-[#3a3c42]" : "hover:bg-[#727681] " ?> text-gray-300 hover:bg-[#727681]  hover:text-white group flex items-center px-2 py-2 text-sm font-medium rounded-md">
                  <!-- Heroicon name: outline/clock -->
                  <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" class="text-gray-400 group-hover:text-gray-300 mr-3 flex-shrink-0 h-6 w-6">
    <path stroke-linecap="round" stroke-linejoin="round" d="M9.813 15.904 9 18.75l-.813-2.846a4.5 4.5 0 0 0-3.09-3.09L2.25 12l2.846-.813a4.5 4.5 0 0 0 3.09-3.09L9 5.25l.813 2.846a4.5 4.5 0 0 0 3.09 3.09L15.75 12l-2.846.813a4.5 4.5 0 0 0-3.09 3.09ZM18.259 8.715 18 9.75l-.259-1.035a3.375 3.375 0 0 0-2.455-2.456L14.25 6l1.036-.259a3.375 3.375 0 0 0 2.455-2.456L18 2.25l.259 1.035a3.375 3.375 0 0 0 2.456 2.456L21.75 6l-1.035.259a3.375 3.375 0 0 0-2.456 2.456ZM16.894 20.567 16.5 21.75l-.394-1.183a2.25 2.25 0 0 0-1.423-1.423L13.5 18.75l1.183-.394a2.25 2.25 0 0 0 1.423-1.423l.394-1.183.394 1.183a2.25 2.25 0 0 0 1.423 1.423l1.183.394-1.183.394a2.25 2.25 0 0 0-1.423 1.423Z" />
  </svg>
  
  
                  Manage Reviews
                </a>
  
                <a href="/adminSettings"
                  class="  <?= urlis('/adminSettings') ?
                    "hover:bg-[#1e1f22] bg-[#3a3c42]" : "hover:bg-[#727681] " ?> text-gray-300 hover:bg-[#727681]  hover:text-white group flex items-center px-2 py-2 text-sm font-medium rounded-md">
                  <!-- Heroicon name: outline/clock -->
  
                  <svg class="text-gray-400 group-hover:text-gray-300 mr-3 flex-shrink-0 h-6 w-6"
                    xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5"
                    stroke="currentColor" class="w-6 h-6">
                    <path stroke-linecap="round" stroke-linejoin="round"
                      d="M9.594 3.94c.09-.542.56-.94 1.11-.94h2.593c.55 0 1.02.398 1.11.94l.213 1.281c.063.374.313.686.645.87.074.04.147.083.22.127.325.196.72.257 1.075.124l1.217-.456a1.125 1.125 0 0 1 1.37.49l1.296 2.247a1.125 1.125 0 0 1-.26 1.431l-1.003.827c-.293.241-.438.613-.43.992a7.723 7.723 0 0 1 0 .255c-.008.378.137.75.43.991l1.004.827c.424.35.534.955.26 1.43l-1.298 2.247a1.125 1.125 0 0 1-1.369.491l-1.217-.456c-.355-.133-.75-.072-1.076.124a6.47 6.47 0 0 1-.22.128c-.331.183-.581.495-.644.869l-.213 1.281c-.09.543-.56.94-1.11.94h-2.594c-.55 0-1.019-.398-1.11-.94l-.213-1.281c-.062-.374-.312-.686-.644-.87a6.52 6.52 0 0 1-.22-.127c-.325-.196-.72-.257-1.076-.124l-1.217.456a1.125 1.125 0 0 1-1.369-.49l-1.297-2.247a1.125 1.125 0 0 1 .26-1.431l1.004-.827c.292-.24.437-.613.43-.991a6.932 6.932 0 0 1 0-.255c.007-.38-.138-.751-.43-.992l-1.004-.827a1.125 1.125 0 0 1-.26-1.43l1.297-2.247a1.125 1.125 0 0 1 1.37-.491l1.216.456c.356.133.751.072 1.076-.124.072-.044.146-.086.22-.128.332-.183.582-.495.644-.869l.214-1.28Z" />
                    <path stroke-linecap="round" stroke-linejoin="round" d="M15 12a3 3 0 1 1-6 0 3 3 0 0 1 6 0Z" />
                  </svg>
  
                  Settings
                </a>
                <a onclick="signOut();"
                  class=" cursor-pointer text-gray-300 hover:bg-[#727681]  hover:text-white group flex  items-center px-2 py-2 text-sm font-medium rounded-md">
                  <!-- Heroicon name: outline/clock -->
                  <svg class="text-gray-400 group-hover:text-gray-300 mr-3 flex-shrink-0 h-6 w-6"
                    xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5"
                    stroke="currentColor" class="w-6 h-6">
                    <path stroke-linecap="round" stroke-linejoin="round"
                      d="M3 16.5v2.25A2.25 2.25 0 0 0 5.25 21h13.5A2.25 2.25 0 0 0 21 18.75V16.5m-13.5-9L12 3m0 0 4.5 4.5M12 3v13.5" />
                  </svg>
  
                  Log out
                </a>
              </div>
  
            </nav>
              </div>
            </div>
          </div>
        </div>
  
        <div class="flex-shrink-0 w-14" aria-hidden="true">
          <!-- Dummy element to force sidebar to shrink to fit close icon -->
        </div>
      </div>
  
      <div class="hidden z-50 lg:flex lg:w-64 lg:fixed lg:inset-y-0">
        <!-- Sidebar component, swap this element with another sidebar if you like -->
        <div class="flex-1 flex flex-col min-h-0">
          <div style="background-image: url('/public/images/favicon.ico');"
            class="flex justify-center bg-center bg-cover opacity-80 text-gray-200 tracking-[1px] text-2xl font-thin items-center h-16 flex-shrink-0 px-4 bg-gray-900">
            
          </div>
          <div class="flex-1 flex flex-col overflow-y-auto bg-[#2d2e33]">
          <nav class="flex-1 px-2 py-4">
              <div class="space-y-1">
                <!-- Current: "hover:bg-[#1e1f22] text-white", Default: "text-gray-300 hover:bg-[#727681]  hover:text-white" -->
                <div class="flex flex-row justify-center">
                <?php 
                  if(empty($adminPhoto["admin_img"]) || $adminPhoto["admin_img"]=="/resources/new_user.png"){
  ?>
     <button class="rounded-full w-10 h-10 grayscale mb-4 border-gray-600 border-1 overflow-hidden">
                    <img src="/resources/new_user.png" class="object-contain" alt="">
                  </button>
  <?php
                  }else{
                    ?>
                        <button class="rounded-full w-10 h-10  mb-4 border-gray-600 border-1">
                    <img src="<?= $adminPhoto["admin_img"]?>" class="object-cover rounded-full w-full h-full" alt="">
                  </button>
                               
                    <?php
                  }
                  ?>  
                
                </div>
                <a href="/adminDashboard"
                  class="<?= urlis('/adminDashboard') ?
                    "bg-[#3a3c42] hover:bg-[#1e1f22] " : "hover:bg-[#727681] " ?> text-white group flex items-center px-2 py-2 text-sm font-medium rounded-md"
                  aria-current="page">
  
                  <svg class="text-gray-300 mr-3 flex-shrink-0 h-6 w-6" xmlns="http://www.w3.org/2000/svg" fill="none"
                    viewBox="0 0 24 24" stroke="currentColor" aria-hidden="true">
                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                      d="M3 12l2-2m0 0l7-7 7 7M5 10v10a1 1 0 001 1h3m10-11l2 2m-2-2v10a1 1 0 01-1 1h-3m-6 0a1 1 0 001-1v-4a1 1 0 011-1h2a1 1 0 011 1v4a1 1 0 001 1m-6 0h6" />
                  </svg>
                  Dashboard
                </a>
  
  
                <a href="/manageUsers"
                  class=" <?= urlis('/manageUsers') ?
                    "hover:bg-[#1e1f22] bg-[#3a3c42]" : "hover:bg-[#727681] " ?>  text-gray-300 hover:bg-[#727681]  hover:text-white group flex items-center px-2 py-2 text-sm font-medium rounded-md">
                  <!-- Heroicon name: outline/user-circle -->
                      <!-- Heroicon name: outline/archive -->
                      <svg class="text-gray-400 group-hover:text-gray-300 mr-3 flex-shrink-0 h-6 w-6 bi bi-people"
                    xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" 
                    viewBox="0 0 16 16">
                    <path
                      d="M15 14s1 0 1-1-1-4-5-4-5 3-5 4 1 1 1 1zm-7.978-1L7 12.996c.001-.264.167-1.03.76-1.72C8.312 10.629 9.282 10 11 10c1.717 0 2.687.63 3.24 1.276.593.69.758 1.457.76 1.72l-.008.002-.014.002zM11 7a2 2 0 1 0 0-4 2 2 0 0 0 0 4m3-2a3 3 0 1 1-6 0 3 3 0 0 1 6 0M6.936 9.28a6 6 0 0 0-1.23-.247A7 7 0 0 0 5 9c-4 0-5 3-5 4q0 1 1 1h4.216A2.24 2.24 0 0 1 5 13c0-1.01.377-2.042 1.09-2.904.243-.294.526-.569.846-.816M4.92 10A5.5 5.5 0 0 0 4 13H1c0-.26.164-1.03.76-1.724.545-.636 1.492-1.256 3.16-1.275ZM1.5 5.5a3 3 0 1 1 6 0 3 3 0 0 1-6 0m3-2a2 2 0 1 0 0 4 2 2 0 0 0 0-4" />
                  </svg>
                  Manage Users
                </a>
  
                <a href="/manageSellers"
                  class=" <?= urlis('/manageSellers') ?
                    "hover:bg-[#1e1f22] bg-[#3a3c42]" : "hover:bg-[#727681] " ?>  text-gray-300 hover:bg-[#727681]  hover:text-white group flex items-center px-2 py-2 text-sm font-medium rounded-md">
                  <!-- Heroicon name: outline/user-circle -->
                  <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" class="text-gray-400 group-hover:text-gray-300 mr-3 flex-shrink-0 h-6 w-6">
    <path stroke-linecap="round" stroke-linejoin="round" d="M18 18.72a9.094 9.094 0 0 0 3.741-.479 3 3 0 0 0-4.682-2.72m.94 3.198.001.031c0 .225-.012.447-.037.666A11.944 11.944 0 0 1 12 21c-2.17 0-4.207-.576-5.963-1.584A6.062 6.062 0 0 1 6 18.719m12 0a5.971 5.971 0 0 0-.941-3.197m0 0A5.995 5.995 0 0 0 12 12.75a5.995 5.995 0 0 0-5.058 2.772m0 0a3 3 0 0 0-4.681 2.72 8.986 8.986 0 0 0 3.74.477m.94-3.197a5.971 5.971 0 0 0-.94 3.197M15 6.75a3 3 0 1 1-6 0 3 3 0 0 1 6 0Zm6 3a2.25 2.25 0 1 1-4.5 0 2.25 2.25 0 0 1 4.5 0Zm-13.5 0a2.25 2.25 0 1 1-4.5 0 2.25 2.25 0 0 1 4.5 0Z" />
  </svg>
  
                  Manage Sellers
                </a>
                <a href="/manageProduct"
                  class=" <?= urlis('/manageProduct') ?
                    "hover:bg-[#1e1f22] bg-[#3a3c42]" : "hover:bg-[#727681] " ?> text-gray-300 hover:bg-[#727681]  hover:text-white group flex items-center px-2 py-2 text-sm font-medium rounded-md">
                  <!-- Heroicon name: outline/archive -->
                  <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" class="text-gray-400 group-hover:text-gray-300 mr-3 flex-shrink-0 h-6 w-6">
    <path stroke-linecap="round" stroke-linejoin="round" d="m7.875 14.25 1.214 1.942a2.25 2.25 0 0 0 1.908 1.058h2.006c.776 0 1.497-.4 1.908-1.058l1.214-1.942M2.41 9h4.636a2.25 2.25 0 0 1 1.872 1.002l.164.246a2.25 2.25 0 0 0 1.872 1.002h2.092a2.25 2.25 0 0 0 1.872-1.002l.164-.246A2.25 2.25 0 0 1 16.954 9h4.636M2.41 9a2.25 2.25 0 0 0-.16.832V12a2.25 2.25 0 0 0 2.25 2.25h15A2.25 2.25 0 0 0 21.75 12V9.832c0-.287-.055-.57-.16-.832M2.41 9a2.25 2.25 0 0 1 .382-.632l3.285-3.832a2.25 2.25 0 0 1 1.708-.786h8.43c.657 0 1.281.287 1.709.786l3.284 3.832c.163.19.291.404.382.632M4.5 20.25h15A2.25 2.25 0 0 0 21.75 18v-2.625c0-.621-.504-1.125-1.125-1.125H3.375c-.621 0-1.125.504-1.125 1.125V18a2.25 2.25 0 0 0 2.25 2.25Z" />
  </svg>
  
  
                  Manage Products
                </a>
  
                <a href="/manageCategory"
                  class="  <?= urlis('/manageCategory') ?
                    "hover:bg-[#1e1f22] bg-[#3a3c42]" : "hover:bg-[#727681] " ?> text-gray-300 hover:bg-[#727681]  hover:text-white group flex items-center px-2 py-2 text-sm font-medium rounded-md">
                  <!-- Heroicon name: outline/clock -->
                  <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" class="text-gray-400 group-hover:text-gray-300 mr-3 flex-shrink-0 h-6 w-6">
    <path stroke-linecap="round" stroke-linejoin="round" d="M9.568 3H5.25A2.25 2.25 0 0 0 3 5.25v4.318c0 .597.237 1.17.659 1.591l9.581 9.581c.699.699 1.78.872 2.607.33a18.095 18.095 0 0 0 5.223-5.223c.542-.827.369-1.908-.33-2.607L11.16 3.66A2.25 2.25 0 0 0 9.568 3Z" />
    <path stroke-linecap="round" stroke-linejoin="round" d="M6 6h.008v.008H6V6Z" />
  </svg>
  
                  Manage Categories
                </a>
                <a href="/manageReviews"
                  class="  <?= urlis('/manageReviews') ?
                    "hover:bg-[#1e1f22] bg-[#3a3c42]" : "hover:bg-[#727681] " ?> text-gray-300 hover:bg-[#727681]  hover:text-white group flex items-center px-2 py-2 text-sm font-medium rounded-md">
                  <!-- Heroicon name: outline/clock -->
                  <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" class="text-gray-400 group-hover:text-gray-300 mr-3 flex-shrink-0 h-6 w-6">
    <path stroke-linecap="round" stroke-linejoin="round" d="M9.813 15.904 9 18.75l-.813-2.846a4.5 4.5 0 0 0-3.09-3.09L2.25 12l2.846-.813a4.5 4.5 0 0 0 3.09-3.09L9 5.25l.813 2.846a4.5 4.5 0 0 0 3.09 3.09L15.75 12l-2.846.813a4.5 4.5 0 0 0-3.09 3.09ZM18.259 8.715 18 9.75l-.259-1.035a3.375 3.375 0 0 0-2.455-2.456L14.25 6l1.036-.259a3.375 3.375 0 0 0 2.455-2.456L18 2.25l.259 1.035a3.375 3.375 0 0 0 2.456 2.456L21.75 6l-1.035.259a3.375 3.375 0 0 0-2.456 2.456ZM16.894 20.567 16.5 21.75l-.394-1.183a2.25 2.25 0 0 0-1.423-1.423L13.5 18.75l1.183-.394a2.25 2.25 0 0 0 1.423-1.423l.394-1.183.394 1.183a2.25 2.25 0 0 0 1.423 1.423l1.183.394-1.183.394a2.25 2.25 0 0 0-1.423 1.423Z" />
  </svg>
  
  
                  Manage Reviews
                </a>
  
                <a href="/adminSettings"
                  class="  <?= urlis('/adminSettings') ?
                    "hover:bg-[#1e1f22] bg-[#3a3c42]" : "hover:bg-[#727681] " ?> text-gray-300 hover:bg-[#727681]  hover:text-white group flex items-center px-2 py-2 text-sm font-medium rounded-md">
                  <!-- Heroicon name: outline/clock -->
  
                  <svg class="text-gray-400 group-hover:text-gray-300 mr-3 flex-shrink-0 h-6 w-6"
                    xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5"
                    stroke="currentColor" class="w-6 h-6">
                    <path stroke-linecap="round" stroke-linejoin="round"
                      d="M9.594 3.94c.09-.542.56-.94 1.11-.94h2.593c.55 0 1.02.398 1.11.94l.213 1.281c.063.374.313.686.645.87.074.04.147.083.22.127.325.196.72.257 1.075.124l1.217-.456a1.125 1.125 0 0 1 1.37.49l1.296 2.247a1.125 1.125 0 0 1-.26 1.431l-1.003.827c-.293.241-.438.613-.43.992a7.723 7.723 0 0 1 0 .255c-.008.378.137.75.43.991l1.004.827c.424.35.534.955.26 1.43l-1.298 2.247a1.125 1.125 0 0 1-1.369.491l-1.217-.456c-.355-.133-.75-.072-1.076.124a6.47 6.47 0 0 1-.22.128c-.331.183-.581.495-.644.869l-.213 1.281c-.09.543-.56.94-1.11.94h-2.594c-.55 0-1.019-.398-1.11-.94l-.213-1.281c-.062-.374-.312-.686-.644-.87a6.52 6.52 0 0 1-.22-.127c-.325-.196-.72-.257-1.076-.124l-1.217.456a1.125 1.125 0 0 1-1.369-.49l-1.297-2.247a1.125 1.125 0 0 1 .26-1.431l1.004-.827c.292-.24.437-.613.43-.991a6.932 6.932 0 0 1 0-.255c.007-.38-.138-.751-.43-.992l-1.004-.827a1.125 1.125 0 0 1-.26-1.43l1.297-2.247a1.125 1.125 0 0 1 1.37-.491l1.216.456c.356.133.751.072 1.076-.124.072-.044.146-.086.22-.128.332-.183.582-.495.644-.869l.214-1.28Z" />
                    <path stroke-linecap="round" stroke-linejoin="round" d="M15 12a3 3 0 1 1-6 0 3 3 0 0 1 6 0Z" />
                  </svg>
  
                  Settings
                </a>
                <a onclick="signOut();"
                  class=" cursor-pointer text-gray-300 hover:bg-[#727681]  hover:text-white group flex  items-center px-2 py-2 text-sm font-medium rounded-md">
                  <!-- Heroicon name: outline/clock -->
                  <svg class="text-gray-400 group-hover:text-gray-300 mr-3 flex-shrink-0 h-6 w-6"
                    xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5"
                    stroke="currentColor" class="w-6 h-6">
                    <path stroke-linecap="round" stroke-linejoin="round"
                      d="M3 16.5v2.25A2.25 2.25 0 0 0 5.25 21h13.5A2.25 2.25 0 0 0 21 18.75V16.5m-13.5-9L12 3m0 0 4.5 4.5M12 3v13.5" />
                  </svg>
  
                  Log out
                </a>
              </div>
  
            </nav>
          </div>
        </div>
      </div>
      <div class="lg:pl-64 flex flex-col w-0 flex-1 ">
        <div class="sticky top-0 z-10 flex-shrink-0 flex h-10 bg-white border-b border-gray-200">
          <button type="button"
            class="px-4 border-r border-gray-200 text-gray-500 focus:outline-none focus:ring-2 focus:ring-inset focus:rihover:ng-[#1e1f22] lg:hidden"
            onclick="toggleNavClose();">
            <span class="sr-only">Open sidebar</span>
            <!-- Heroicon name: outline/menu-alt-2 -->
            <svg class="h-6 w-6" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke="currentColor"
              aria-hidden="true">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M4 6h16M4 12h16M4 18h7" />
            </svg>
          </button>
  
        </div>
      </div>
  
    </div>
    <script src="/public/js/adminNav.js"></script>
    <div class="main-div w-full">
      <?php }else{
        ?>
        <script>window.location.href="/adminSignin";</script>
        <?php
      } ?>