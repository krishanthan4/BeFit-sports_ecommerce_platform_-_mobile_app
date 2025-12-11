<?php require_once "./guis/partials/header.php";
require_once "./guis/partials/nav.php";

if (isset($_SESSION["user"])) {
    $email = $_SESSION["user"]["email"];

?>
<div class="">
<main class="max-w-7xl mx-auto pb-10 py-12 lg:px-8">
      <div class="grid grid-cols-12 gap-x-5">

            <?php require_once "./guis/partials/user_profile_sidebar.php" ?>
            <div class="space-y-6 sm:px-6 lg:px-0 col-span-9">
                <!-- content start -->
                <div class="max-w-2xl  mx-auto px-4 lg:max-w-4xl lg:px-0">
                    <h1 class="text-2xl font-extrabold tracking-tight text-gray-200 sm:text-3xl">My Products</h1>
                    <p class="mt-2 text-sm text-gray-500">Update your billing information. Please note that updating your location could affect your tax rates..</p>
                </div>
                <div class="w-full flex justify-end px-5 ">
                    <a href="/addProduct" class="bg-yellow-600/90 hover:bg-yellow-600/70 w-fit text-[#202124] rounded-md sm:py-2.5 py-1 text-sm px-3"> New Product </a>
                </div>
                <div class="">
                    <!-- sort -->
                    <div class="">
                        <!-- Filters -->
                        <?php require_once "./guis/partials/myProducts/filter.php" ?>
                    </div>
                    <!-- sort -->
                    <div id="AllMainDiv">
                        <div id="CardsMaindiv" class="grid grid-cols-2 justify-center items-center">

                            <?php
                            $product_resultSet = Database::search("SELECT * FROM product WHERE user_email='" . $email . "' LIMIT 0,10");
                            $product_numRows = $product_resultSet->num_rows;
                            for ($x = 0; $x < $product_numRows; $x++) {
                                $selected_data = $product_resultSet->fetch_assoc();
                                ?>
                                <!-- card start -->
                                <div id="existCard" class="bg-[#252629] flex m-5 flex-col items-center  rounded-lg shadow lg:flex-row lg:max-w-xl md:max-w-72  sm:max-w-60 hover:bg-[#252629] ">
                                    <div class="lg:w-[40%] lg:ms-3 md:w-full sm:[30%]">
                                        <?php

                                        $product_img_resultSet = Database::search("SELECT * FROM product_img WHERE product_id='".$selected_data["id"]."' LIMIT 10");
if($product_img_resultSet->num_rows!==0){
    $product_img_data = $product_img_resultSet->fetch_assoc();

?>
                                        <img class="rounded-md h-auto w-full  "
                                            src="<?php echo $product_img_data["img_path"]; ?>" alt="">
<?php
}
                                        ?>

                                    </div>
                                    <div class="flex flex-col justify-between px-4 py-2 leading-normal">
                                        <h5 class="mb-2 text-2xl font-bold tracking-tight text-gray-200 ">
                                            <?php echo $selected_data["title"]; ?>
                                        </h5>
                                        <p class="mb-3 font-normal text-gray-400 ">Rs.
                                            <?php echo $selected_data["price"]; ?> .00
                                        </p>
                                        <p class="mb-3 font-normal text-gray-400 ">
                                            <?php echo $selected_data["qty"]; ?> items left
                                        </p>

                                        <label class="inline-flex items-center me-5 cursor-pointer">
                                            <input type="checkbox" role="switch" class="sr-only peer" 
                                            id="toggle<?php echo $selected_data["id"]; ?>" 
                                            onchange="changeStatus(<?php echo $selected_data['id']; ?>);" 
                                            <?php if ($selected_data["status_status_id"] == 2) { ?> checked <?php } ?>>
                                            <div
                                                class="relative w-11 h-6  rounded-full peer peer-focus:ring-4 peer-focus:ring-red-300  peer-checked:after:translate-x-full rtl:peer-checked:after:-translate-x-full peer-checked:after:border-white after:content-[''] after:absolute after:top-0.5 after:start-[2px] after:bg-white after:border-gray-300 after:border after:rounded-full after:h-5 after:w-5 after:transition-all dark:border-gray-600 peer-checked:bg-red-600">
                                            </div>
                                            <span class="ms-3 text-sm font-medium text-gray-200 " for="toggle<?php echo $selected_data["id"]; ?>">
                                            <?php
                                            if($selected_data["status_status_id"] == 1){
                                                ?>
                                                <p>Deactivate</p>
                                                <?php
                                            }else{
                                             ?>
                                             <p>Activate</p>                           
                                                <?php
                                            }      
                                            ?>
                                            </span>
                                            
                                        </label>
                                        <button class="p-2 m-3 rounded-md bg-[#38393f] hover:bg-[#3f4047] text-white"  onclick="sendId(<?php echo $selected_data['id']; ?>);">Update</button>
                                    </div>
                                </div>
                                <!-- card end -->
                                <?php
                            }
                            ?>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </main>
</div>

<!-- alert model -->
<?php require_once "./guis/partials/alert.php"; ?>
<!--alert model -->
<script src="./public/js/myProducts.js"></script>
<?php require_once "./guis/partials/footer.php"; 
 } else { ?><script>window.location.href = "/";</script><?php } ?>