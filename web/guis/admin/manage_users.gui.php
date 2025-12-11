<?php require_once "./guis/partials/header.php";
if(isset($_SESSION["admin"])){
require_once "./guis/partials/admin_nav.php";

 if(isset($_GET["id"])){
    $pageId =$_GET["id"]; 
  
  }else{
    $pageId =1; 
  }
?>

<div class="w-full mx-4  my-4 flex flex-row items-center">
        <p class="sm:text-xl sm:text-center text-base md:ms-6">Manage Users</p>
        <!-- search bar -->
        <div id="searchForm" class="flex xl:w-[70%] lg:max-w-[50%] lg:min-w-[40%] sm:w-[70%] gap-2 items-center mx-10">
    <input type="text" id="searchInput" placeholder="Search by name or email"  class="bg-[#2e3035] text-gray-400 border border-[#38393f]  text-sm rounded-lg w-full p-2 outline-none" required />
</div>

        <!-- search bar -->
</div>

<!-- product table start -->
<div class="flex flex-col mt-5">
    <div class="-my-2 overflow-x-auto sm:-mx-4 md:-mx-6 lg:mx-2">
        <div class="py-1 align-middle inline-block min-w-full sm:px-4 md:px-6 lg:px-8">
            <div class="shadow overflow-hidden  sm:rounded-lg">
                <table id="manageUsersTable" class="lg:min-w-[90%] sm:md-w-[60%] md:min-w-full overflow-x-scroll divide-y divide-[#3e3e46]">
                    <!-- Table Headers -->
                    <thead class="bg-[#26262b]">
                        <tr>
                            <th scope="col" class="px-2 md:px-3 py-2 text-left text-xs font-medium text-gray-300 uppercase tracking-wider">#</th>
                            <th scope="col" class="px-2 md:px-3 py-2 text-left text-xs font-medium text-gray-300 uppercase tracking-wider"> Profile Image </th>
                            <th scope="col" class="px-2 md:px-3 py-2 text-left text-xs font-medium text-gray-300 uppercase tracking-wider"> Username </th>
                            <th scope="col" class="px-2 md:px-3 py-2 text-left text-xs font-medium text-gray-300 uppercase tracking-wider"> Email </th>
                            <th scope="col" class="px-2 md:px-3 py-2 text-left text-xs font-medium text-gray-300 uppercase tracking-wider"> Mobile </th>
                            <th scope="col" class="px-2 md:px-3 py-2 text-left text-xs font-medium text-gray-300 uppercase tracking-wider"> Registered Date </th>
                            <th scope="col" class="px-2 md:px-3 py-2 text-left text-xs font-medium text-gray-300 uppercase tracking-wider"> Action </th>
                        </tr>
                    </thead>
                    <tbody id="userTableBody" class="bg-[#26262b]/10 divide-y divide-[#3e3e46]">
<?php 
$AllUsers_rs = Database::search("SELECT COUNT(`email`) AS total FROM `user` ");
$AllUsers = $AllUsers_rs->fetch_assoc();
$totalCount = $AllUsers['total'];
$itemsPerPage = 7;
$baseurl='/manageUsers?id=';
$totalPages = ceil($totalCount / $itemsPerPage);
if($AllUsers_rs->num_rows){
    $output = preg_replace( '/[^0-9]/', '', $pageId );
    if($output){
      $pageId = $pageId-1;
      $from = 0;
      $to = 7;
      $from = $to * $pageId;
    }else{
      $from = 0;
    $to = 7;
    }

$manageResult = Database::search("SELECT * FROM `user` LIMIT $from,$to");
if($manageResult->num_rows!==0){
    for ($i=0; $i < $manageResult->num_rows; $i++) { 
        $manageUsers= $manageResult->fetch_assoc();
    ?>
    
    <tr>
                            <td class="px-3 py-2 whitespace-nowrap">
                                    <div class="text-sm text-gray-300"><?= $from + $i + 1?></div>
                                
                                </td>
                                <td class="px-3 py-2 whitespace-nowrap">
                                    <div class="flex items-center">
                                        <div class="flex-shrink-0 h-10 w-10">
                                        <?php
    $ManageUserProfile = Database::search("SELECT `path` FROM profile_img WHERE user_email='".$manageUsers["email"]."'");
    $UserProfile = $ManageUserProfile->fetch_assoc();
    
    if ($UserProfile && isset($UserProfile["path"])) {
    ?>
        <img class="border object-center object-cover border-gray-300 h-10 w-10 rounded-full"
             src="<?= $UserProfile["path"]?>"
             alt="">
    <?php
    } else {
    ?>
        <img class="border object-center object-cover grayscale border-gray-300 h-10 w-10 rounded-full"
             src="./resources/new_user.png"
             alt="">
    <?php
    }
    ?>
                                        </div>
                                 
                                    </div>
                                </td>
    
                                <?php 
                                if($manageUsers["fname"]==!null || $manageUsers["lname"]){
    ?>
                                  <td class="px-3 py-2 whitespace-nowrap">
                                    <div class="text-sm text-gray-300"><?=  $manageUsers["fname"] ." ".$manageUsers["lname"]?> </div> 
                                </td>
    <?php
                                }else{
                                    ?>
                                    <td class="px-3 py-2 whitespace-nowrap">
                                 <div class="text-sm ps-10 text-gray-300">-</div> 
                                     </td>
                                  <?php
                                }
                                
                                ?>
    
    <td id="email" class="px-3 py-2 whitespace-nowrap text-sm text-gray-300"><?= $manageUsers["email"]?></td>
    
    
    
                              
    <?php 
                                if($manageUsers["mobile"]==!null){
    ?>
                                   <td class="px-3 py-2 whitespace-nowrap text-sm text-gray-300"><?= $manageUsers["mobile"]?></td>
    <?php
                                }else{
                                    ?>
                                    <td class="px-3 py-2 whitespace-nowrap">
                                 <div class="text-sm ps-6 text-gray-300">-</div> 
                                     </td>
                                  <?php
                                }
                                ?>

                                <td class="px-3 py-2 whitespace-nowrap text-sm text-gray-300"><?= $manageUsers["joined_date"]?></td>
                                <td class="px-3 py-2 whitespace-nowrap text-right text-sm font-medium flex items-center justify-center">
                               
                               
                                <button id="blockbutton-<?= $manageUsers["email"]?>"
        onclick="toggleUserStatus('<?= $manageUsers['email'] ?>')"
        data-initial-status="<?= $manageUsers['status_status_id'] ?>"
        class="<?php if($manageUsers["status_status_id"]==1){?>hover:bg-red-200 text-red-700 border-red-600 <?php }else{?>hover:bg-green-200 text-green-700 border-green-600<?php }?> inline-flex items-center px-6 py-2 border text-[16px] font-bold rounded">
    <?php if($manageUsers["status_status_id"]==1){?>
        block
    <?php }else{?>
        unblock
    <?php } ?>
</button>



                                </td>
                            </tr>
    <?php
    }
    ?> <!-- More people... -->
       </tbody>
          </table>
          <div id="emptyManageUsers" class="col-span-12 hidden lg:col-span-6 flex flex-col justify-center">
<img src="./public/images/noitems.jpg" alt="" class="h-[290px] bg-center w-fit mx-auto bg-contain bg-no-repeat my-2">
                  <div class="col-span-12 lg:col-span-6 text-center my-2 flex justify-center flex-col">
                    <label class="text-xl font-semibold mt-2">There are no User in this Name</label>
                  </div>
</div>

         <?php 
}else{
    ?>
    <script>window.location.href = "/manageUsers?id=1"</script><?php
}

}
                ?>
            </div>
        </div>
    </div>
</div>
<!-- product table start -->
<?php require_once "./guis/partials/pagination.php";
$currentPage = isset($_GET['id']) ? intval($_GET['id']) : 1;
$currentPage = max(1, min($currentPage, $totalPages));
$parsedJs = "manageUsersPagination"; 
          pagination($currentPage, $totalPages, $baseurl,$itemsPerPage, $parsedJs) ?>
<div>
    
</div>
<script src="./public/js/manageUsers.js"></script>
</div></body></html>
<?php 
} else{ ?><script>window.location.href="/adminSignin";</script><?php  } ?>