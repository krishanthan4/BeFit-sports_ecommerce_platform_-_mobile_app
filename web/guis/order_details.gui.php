<?php
//  if(isset($_GET["order_id"])){
  require_once "./guis/partials/header.php";
  require_once "./guis/partials/nav.php";
  if (isset($_SESSION["user"])) {
$orderDetails_rs = Database::search("SELECT * FROM `invoice` WHERE `order_id`=? ORDER BY `date` DESC",[$_GET["order_id"]]);
if($orderDetails_rs->num_rows!==0){
  $orderDetails = $orderDetails_rs->fetch_assoc();
  ?>
  <div>

  <main class="max-w-7xl mx-auto pb-10 py-12 lg:px-8">
    <div class="grid grid-cols-12 gap-x-5">

      <?php require_once "./guis/partials/user_profile_sidebar.php" ?>

      <div class="space-y-6 sm:px-6 lg:px-0 col-span-9">
          <div class="max-w-7xl mx-auto sm:px-2 lg:px-8">
            <div class="max-w-2xl  mx-auto px-4 lg:max-w-4xl lg:px-0">
        <h1 class="text-sm font-semibold uppercase tracking-wide text-blue-600">Thank you!</h1>
      
        <span class="mt-2 text-4xl font-extrabold tracking-tight text-gray-200 sm:text-5xl">It's on the way!</span>
        <a href="/cart?order_id=<?= $orderDetails["order_id"] ?>" class="text-sm ms-3 font-medium text-blue-600 hover:text-blue-500 ">View invoice<span aria-hidden="true"> &rarr;</span></a>
      </div>
     
      <p class="text-sm text-gray-400">Order placed - <time datetime="<?= $orderDetails["date"] ?>" class="font-medium text-gray-300"> <?= date('Y-m-d',strtotime($orderDetails["date"])) ?></time></p>
    </div>

    <!-- Products -->
    <section aria-labelledby="products-heading" class="mt-6">
      <h2 id="products-heading" class="sr-only">Products purchased</h2>

      <div class="space-y-8">
        <?php 
     $orderDetailsProduct_rs = Database::search("SELECT * FROM `invoice_has_products` INNER JOIN `product` ON `product`.`id`=`invoice_has_products`.`product_id` WHERE `invoice_id`=?",[$orderDetails["invoice_id"]]); 
     $userAddress_rs = Database::search("SELECT * FROM `user_has_address` INNER JOIN `city` ON `city`.`city_id`=`user_has_address`.`city_city_id` INNER JOIN `district` ON `district`.`district_id`=`city`.`district_district_id` WHERE `user_email`=?",[$_SESSION["user"]["email"]]);
     $userAddress = $userAddress_rs->fetch_assoc(); 
  
     for ($i=0; $i < $orderDetailsProduct_rs->num_rows; $i++) { 
      $orderDetailsProduct = $orderDetailsProduct_rs->fetch_assoc();
      $seller_rs = Database::search("SELECT * FROM `user` WHERE `email`=? ",[$orderDetailsProduct["user_email"]]);
      $seller = $seller_rs->fetch_assoc();
?>
  <div class="bg-[#27282b] border-t border-b border-[#323336] shadow-sm sm:border sm:rounded-lg">
          <div class="py-6 px-4 sm:px-6 lg:grid lg:grid-cols-12 lg:gap-x-8 lg:p-8">
            <div class="sm:flex lg:col-span-7">
          <div class="rounded-md p-2 bg-[#323336]/60">
          <div class="flex-shrink-0 w-full aspect-w-1 aspect-h-1 rounded-lg overflow-hidden sm:aspect-none sm:w-40 sm:h-40">
<?php 
$productImage1_rs = Database::search("SELECT * FROM `product_img` WHERE `product_id` = ? LIMIT 1", [$orderDetailsProduct["id"]]);

if ($productImage1_rs->num_rows > 0) {
    $productImage1 = $productImage1_rs->fetch_assoc();
    ?>
    <img src="<?= htmlspecialchars($productImage1["img_path"]) ?>" class="w-full h-full object-center object-cover sm:w-full sm:h-full">
    <?php
}
?>
              </div>
          </div>

              <div class="mt-6 sm:mt-0 sm:ml-6">
                <h3 class="text-base font-medium text-gray-200">
                  <a href="#"><?= $orderDetailsProduct["title"] ?></a>
                </h3>
                <p class="mt-2 text-sm font-medium text-gray-200">Rs.<?= $orderDetailsProduct["price"] ?>.00</p>
                <p class="mt-3 text-sm text-gray-500"><?= $orderDetailsProduct["description"] ?></p>
              </div>
            </div>

            <div class="mt-6 lg:mt-0 lg:col-span-5">
              <dl class="grid grid-cols-2 gap-x-6 text-sm">
                <div>
                  <dt class="font-medium text-gray-200">Delivery address</dt>
                  <dd class="mt-3 text-gray-500">
                    <span class="block"><?= $userAddress["line1"]?>,</span>
                    <span class="block"><?= $userAddress["line2"]?>,</span>
                    <span class="block"><?= $userAddress["city_name"]?>,</span>
                    <span class="block"><?= $userAddress["district_name"]?>.</span>
                  </dd>
                </div>
                <div>
                  <dt class="font-medium text-gray-200">Seller Details</dt>
                  <dd class="mt-3 text-gray-500 space-y-3">
                  <p><?= substr($seller["email"], 0, 2) . str_repeat(".", strlen($seller["email"]) - 6) . substr($seller["email"], -2) ?></p>
<p><?php 
    if(!empty($seller["mobile"])) {
        echo substr($seller["mobile"], 0, 2) . str_repeat("*", strlen($seller["mobile"]) - 5) . substr($seller["mobile"], -2);
    }
?></p>
                  </dd>
                </div>
              </dl>
            </div>
          </div>

          <div class=" py-6 px-4 sm:px-6 lg:p-8">
            <div class="mt-6" aria-hidden="true">
              <div class="bg-[rgb(96,99,107)] rounded-full overflow-hidden">
                <div class="h-2 bg-blue-700/80 rounded-full" style="width: calc((<?= $orderDetailsProduct["order_status"]?> / 4) * 100%)"></div>
              </div>
              <div class="hidden sm:grid grid-cols-4 text-sm font-medium text-gray-600 mt-6">
                <div class="text-blue-600">Order placed</div>
                <div class="text-center text-blue-600">Processing</div>
                <div class="text-center text-gray-500">Shipped</div>
                <div class="text-right text-gray-500">Delivered</div>
              </div>
            </div>
          </div>
        </div>
<?php
    } ?>      
      </div>
    </section>
      </div></div></main></div>
  <?php require_once "./guis/partials/footer.php";
 }} else { ?><script>window.location.href = "/";</script><?php } ?>


