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
        <p class="sm:text-xl sm:text-center text-base md:ms-6">Manage Products</p>
        <!-- search bar -->
            <div id="searchForm"  class="flex xl:w-[70%] lg:max-w-[50%] lg:min-w-[40%] sm:w-[70%] gap-2 items-center  mx-10">   
                    <input type="text" id="searchInput" placeholder="Search by Product name"  class="bg-[#2e3035] text-gray-400 border border-[#38393f]  text-sm rounded-lg w-full p-2 outline-none" required />
            </div>
    </div>
<!-- product table start -->
<div class="flex flex-col mt-5">
    <div class="-my-2 overflow-x-auto sm:-mx-4 md:-mx-6 lg:mx-2">
        <div class="py-1 align-middle inline-block min-w-full sm:px-4 md:px-6 lg:px-8">
            <div class="shadow overflow-hidden  sm:rounded-lg">
                <table id="manageProductTable" class="lg:min-w-[90%] sm:md-w-[60%] md:min-w-full overflow-x-scroll divide-y divide-[#3e3e46]">
                    <!-- Table Headers -->
                    <thead class="bg-[#26262b]">
                        <tr>
                        <th scope="col" class="px-2 md:px-3 py-2 text-left text-xs font-medium text-gray-300 uppercase tracking-wider">#</th>
                            <th scope="col"
                                class="px-2 md:px-3 py-1 text-left text-xs font-medium text-gray-300 uppercase tracking-wider">
                                Product Image</th>
                            <th scope="col"
                                class="px-2 md:px-3 py-1 text-left text-xs font-medium text-gray-300 uppercase tracking-wider">
                                Title</th>
                            <th scope="col"
                                class="px-2 md:px-3 py-1 text-left text-xs font-medium text-gray-300 uppercase tracking-wider">
                                Price</th>
                            <th scope="col"
                                class="px-2 md:px-3 py-1 text-left text-xs font-medium text-gray-300 uppercase tracking-wider">
                                Quantity</th>
                            <th scope="col"
                                class="px-2 md:px-3 py-1 text-left text-xs font-medium text-gray-300 uppercase tracking-wider">
                                Registered Date</th>
                        </tr>
                    </thead>

                    <tbody id="productTableBody" class="bg-[#26262b]/10 divide-y divide-[#3e3e46]">

                        <?php
$AllProducts_rs = Database::search("SELECT COUNT(`id`) as total FROM `product` ");
$AllProducts = $AllProducts_rs->fetch_assoc();
$totalCount = $AllProducts['total'];
$itemsPerPage = 7;
$baseurl='/manageProduct?id=';
$totalPages = ceil($totalCount / $itemsPerPage);
if($AllProducts_rs->num_rows){
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


                      $manageProducts_rs =  Database::search("SELECT * FROM `product`  LIMIT $from,$to");
if($manageProducts_rs->num_rows>0){
for ($q=0; $q < $manageProducts_rs->num_rows; $q++) { 
    $manageProducts = $manageProducts_rs->fetch_array();
?>
 <tr>
 <td class="px-3 py-2 whitespace-nowrap">
                                    <div class="text-sm text-gray-300"><?= $from + $q + 1?></div>
                                
                                </td>
                            <td class="px-3  py-2  whitespace-nowrap">
                                <div class="flex items-center">
                                    <div class="flex-shrink-0 h-10 w-10">
                                        <?php 
                                        $mangeProductsImage_rs = Database::search("SELECT * FROM `product_img` WHERE `product_id`=?",[$manageProducts["id"]]);
                                        if($mangeProductsImage_rs->num_rows>0){
$mangeProductsImage = $mangeProductsImage_rs->fetch_assoc();
?>
  <img class=" border border-gray-300 h-10 w-10 rounded-full"
                                            src="<?= $mangeProductsImage["img_path"]?>"
                                            alt="">
<?php
                                        }else{
                                      ?>
                                  <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="currentColor" class="w-6 h-6"><path d="M12.378 1.602a.75.75 0 0 0-.756 0L3 6.632l9 5.25 9-5.25-8.622-5.03ZM21.75 7.93l-9 5.25v9l8.628-5.032a.75.75 0 0 0 .372-.648V7.93ZM11.25 22.18v-9l-9-5.25v8.57a.75.75 0 0 0 .372.648l8.628 5.033Z" /></svg>

                                      <?php      
                                        }
                                        ?>
                                      
                                    </div>
                            </td>
                            <td class="px-3  py-2  whitespace-nowrap">
                                <div class="text-sm text-gray-300"><?= $manageProducts["title"] ?></div>
                            </td>
                            <td class="px-3  py-2  whitespace-nowrap text-sm text-gray-300">Rs.<?= $manageProducts["price"] ?>.00</td>
                            <td class="px-3  py-2  whitespace-nowrap text-sm text-gray-300"><?= $manageProducts["qty"] ?> stocks available</td>
                            <td class="px-3  py-2  whitespace-nowrap text-sm text-gray-300"><?= $manageProducts["datetime_added"] ?></td>
                            <td class="px-3  py-2  whitespace-nowrap text-right text-sm font-medium">
                              
                            <button id="blockbutton-<?= $manageProducts["id"]?>"
        onclick="toggleProductStatus('<?= $manageProducts['id'] ?>')"
        data-initial-status="<?= $manageProducts['status_status_id'] ?>"
        class="<?php if($manageProducts["status_status_id"]==1){?>hover:bg-red-200 text-red-700 border-red-600 <?php }else{?>hover:bg-green-200 text-green-700 border-green-600<?php }?> inline-flex items-center px-6 py-2 border text-[16px] font-bold rounded ">
    <?php if($manageProducts["status_status_id"]==1){?>
        Block
    <?php }else{?>
        Unblock
    <?php } ?>
</button>


                            </td>
                        </tr><?php
}
?>
                
                    </tbody>
                </table>
                <div id="emptyManageProduct" class="col-span-12 hidden lg:col-span-6 flex flex-col justify-center">
<img src="./public/images/noitems.jpg" alt="" class="h-[290px] bg-center w-fit mx-auto bg-contain bg-no-repeat my-2">
                  <div class="col-span-12 lg:col-span-6 text-center my-2 flex justify-center flex-col">
                    <label class="text-xl font-semibold mt-2">There are no product in this name</label>
                  </div>
</div>
<?php 
}else{
    ?><script>window.location.href="/manageProduct?id=1"</script><?php
}
                       }                       ?>
                
            </div>
        </div>
    </div>
</div>
<!-- product table start -->
<?php require_once "./guis/partials/pagination.php";

$currentPage = isset($_GET['id']) ? intval($_GET['id']) : 1;
$currentPage = max(1, min($currentPage, $totalPages));
$parsedJs = "manageProductPagination"; 
pagination($currentPage, $totalPages, $baseurl,$itemsPerPage, $parsedJs); ?>

<div>
</div>
<script src="./public/js/manageProduct.js"></script>

</div>
</body>
</html>
<?php } else{?>
    <script>window.location.href="/adminSignin";</script>
    <?php }?>