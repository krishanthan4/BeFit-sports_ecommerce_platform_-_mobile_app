<?php
require_once "./guis/partials/header.php";
$categoryPublicId;
require_once "./guis/partials/cart/invoice.php";
invoice_function();

if (isset($_SESSION["user"])) {
  $user = $_SESSION["user"]["email"];
  $total = 0;
  $subtotal = 0;
  $deliveryCost = 0; ?>
  <main class="max-w-2xl mx-auto pt-16 pb-24 px-4 sm:px-6 lg:max-w-7xl lg:px-8">
    <h1 class="text-3xl font-extrabold tracking-tight text-gray-200 sm:text-4xl ">Shopping Cart <img
        src="./public/images/carticon.svg" draggable="false" class="w-[50px] inline-block h-[50px]"></h1>
    <?php $cart_rs = Database::search("SELECT *, `cart`.`qty`  AS cart_qty FROM `cart` INNER JOIN `product` ON 
`cart`.`product_id`=`product`.`id` INNER JOIN `product_has_color` ON 
`product_has_color`.`product_id`=`product`.`id` INNER JOIN `color` ON 
`product_has_color`.`color_clr_id`=`color`.`clr_id` INNER JOIN `condition` ON 
`product`.`condition_condition_id`=`condition`.`condition_id` INNER JOIN `user` ON 
`product`.`user_email`=`user`.`email` WHERE `cart`.`user_email`='" . $user . "' ORDER BY `cart_id` AND `product`.`status_status_id`='1' DESC");
    $cart_num = $cart_rs->num_rows;

    if ($cart_num !== 0) { 
      $address_status=0;
      ?>
      <div class="mt-12 lg:grid lg:grid-cols-12 lg:gap-x-12 lg:items-start xl:gap-x-16">
        <section aria-labelledby="cart-heading" class="lg:col-span-7  ">
          <div class="flex flex-row items-center justify-between mb-3">
            <div class="flex items-center gap-4">
              <input onchange="toggleCheckout();" type="checkbox" class="ms-2 bg-[#252629] text-[#2b2c30]"
                id="selectAllCheckBox" onclick="sendToPHP2();">
              <p class="text-sm text-gray-400">Select All Items</p>
            </div>
          </div>
          <?php $selectAllArray = [];

          include './guis/partials/colorsMap.php';

          $normalizedColorHexMap = [];
          foreach ($colorHexMap as $colorName => $hexValue) {
            $normalizedKey = strtolower(str_replace(' ', '', $colorName));
            $normalizedColorHexMap[$normalizedKey] = $hexValue;
          }
          for ($x = 0; $x < $cart_num; $x++) {
            $cart_data = $cart_rs->fetch_assoc();
            $colorName = $cart_data["clr_name"];
            $normalizedColorName = strtolower(str_replace(' ', '', $colorName));
            $hexColor = $normalizedColorHexMap[$normalizedColorName] ?? "#FFFFFF";

            $product_rs = Database::search("SELECT * FROM product INNER jOIN product_img ON `product`.`id`=`product_img`.`product_id` WHERE id='" . $cart_data["product_id"] . "'");
            $product_data = $product_rs->fetch_assoc();
            $categoryPublicId = $product_data["category_cat_id"];
            $unitPrice = $product_data["price"];
            $total = $unitPrice * $category_data["cat_qty"];
            $address_rs = Database::search("SELECT district_id AS did FROM user_has_address INNER JOIN city ON `user_has_address`.`city_city_id`=`city`.`city_id` INNER JOIN district  ON  `city`.`district_district_id`=`district`.`district_id` WHERE user_email='" . $user . "' ");
            $address_data = $address_rs->fetch_assoc();
            $cost = 0;
            if($address_data){
              $address_status = 1;
              $cost = $product_data["delivery_fee"];
              $deliveryCost = $deliveryCost + $cost;
            }
            $requested_total = ($unitPrice * $cart_data["cart_qty"]) + $cost;
            $singleProductObject = new stdClass();
            $singleProductObject->unitPrice = $unitPrice * $cart_data["cart_qty"];
            $singleProductObject->deliveryFee = $cost;
            $singleProductObject->requested_total = $requested_total; ?>
            <div class="overflow-x-auto ">
              <ul role="list" class="border-t border-b border-[#313336] divide-y divide-[#313336]">
                <li class="flex flex-row sm:flex-row py-6 sm:py-6 px-2 ">
                  <input class="product_id" type="hidden" value="<?= $product_data["id"] ?>">
                  <div class="flex justify-center mb-2 sm:mb-0">
                    <input type="checkbox" class="itemCheckbox bg-[#252629] text-[#2b2c30]" name="singleCheckbox"
                      onchange="orderSummary('<?= $cart_data['cart_qty'] ?>','<?= $product_data['id'] ?>','<?= $cart_data['cart_id'] ?>','<?= $cost ?>','<?= $requested_total ?>','<?= $unitPrice ?>');"
                      id="<?= $cart_data["cart_id"] ?>">
                  </div>
                  <div class="bg-[#252629]/90 rounded-md p-4 ml-2 w-24 h-24 sm:w-48 sm:h-48">
                    <img src="<?= $product_data["img_path"] ?>" class="w-full h-full rounded-md object-center object-cover  ">
                  </div>
                  <div class="ml-4 flex-1 flex flex-col justify-between sm:ml-6  relative">
                    <!-- qty problem start -->
                    <div class="relative pr-9 sm:grid sm:grid-cols-2 sm:gap-x-6 sm:pr-0">
                      <div>
                        <div class="flex justify-between w-full sm:w-auto">
                          <h3 class="text-xl">
                            <span class="font-medium text-gray-200">
                              <?= $product_data["title"] ?>
                            </span>
                          </h3>
                        </div>
                        <div class="mt-2 flex text-sm items-center ">
                          <?php $bgColorClass = (strtolower($colorName) === 'black' || strtolower($colorName) === 'white') ? 'bg-' . strtolower($colorName) : 'bg-' . strtolower($colorName) . '-500'; ?>
                          <div
                            class="h-8 w-8 overflow-hidden flex justify-center items-center border bg-[<?= $hexColor ?>] rounded-full cursor-pointer">
                          </div>
                          <div class="w-[1px] h-[32px] border-l-[1px] border-[#393a3f] ml-4 mt-[5px] mr-4"></div>
                          <span
                            class="px-1 py-1 inline-flex text-xs leading-5 font-semibold rounded-lg bg-green-600/70 text-[#2b2c30] mt-2"><?= $cart_data["condition_name"] ?></span>
                        </div>
                        <div class="flex gap-2 mt-2">
                          <p class="mt-2 text-sm font-medium text-gray-300 ">Unit Price <span class="text-gray-300">:</span>
                          </p>
                          <p id="<?= $cart_data["cart_id"] ?>unit_price_P" class=" text-lg mt-1 font-medium text-gray-200">Rs.
                            <?= $unitPrice ?>.00<span class="hidden" id="<?= $cart_data["cart_id"] ?>unit_price_span">
                              <?= $unitPrice ?>
                            </span>
                          </p>
                        </div>

                        <div class="flex gap-2 items-center mt-2">
                          <p class="mt-2 text-sm font-medium text-gray-300 ">Quanity<span class="text-gray-300"> : </span></p>
                          <input min="1" max="<?= $product_data["qty"] ?>" type="number"
                            class="product_qty w-[50px] mt-2 h-8 rounded-md p-2 font-medium bg-[#2b2c30] text-gray-400"
                            value="<?= $cart_data["cart_qty"] ?>" onclick="checkboxChecking('<?= $cart_data['cart_id'] ?>');"
                            onchange="changeQTY('<?= $cart_data['cart_id'] ?>','<?= $unitPrice ?>','<?= $cost ?>');" disabled
                            id="<?= $cart_data["cart_id"] ?>qtyNum">
                          <span class="hidden" id="<?= $cart_data["cart_id"] ?>products_qty">
                            <?= $cart_data["cart_qty"] ?>
                          </span>
                          <span class="hidden" id="<?= $cart_data["cart_id"] ?>products_id">
                            <?= $product_data["id"] ?>
                          </span>
                        </div>

                        <div class="flex  gap-2 mt-2">
                          <p class="mt-2 text-sm font-medium text-gray-300 ">Delivery Fee<span class="text-gray-300"> :
                            </span></p>
                          <p class="mt-2 text-sm font-medium text-gray-200">Rs.
                            <?= $cost ?>.00 <span class="hidden" id="<?= $cart_data["cart_id"] ?>delivery_fee_span">
                              <?= $cost ?>
                            </span>
                          </p>
                        </div>
                      </div>
                    </div>
                    <!-- qty problem end -->
                    <!-- Move SVG icon to the right -->
                    <div class="absolute top-0 right-0">
                      <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5"
                        onclick="deleteFromCart(<?= $cart_data['cart_id'] ?>);" stroke="currentColor"
                        class="w-8 h-8 cursor-pointer text-[#424449] hover:text-[#643737]">
                        <path stroke-linecap="round" stroke-linejoin="round"
                          d="m9.75 9.75 4.5 4.5m0-4.5-4.5 4.5M21 12a9 9 0 1 1-18 0 9 9 0 0 1 18 0Z" />
                      </svg>
                    </div>
                    <div class="flex  items-center gap-2  absolute bottom-0 right-0">
                      <p class="mt-2 text-sm font-medium text-gray-300 ">Requested Total<span class="text-gray-300"> : </span>
                      </p>
                      <p id="<?= $cart_data["cart_id"] ?>requested_total_P" class="mt-2  text-sm font-medium text-gray-200">
                        Rs.
                        <?= $requested_total ?>.00<span class="hidden" id="<?= $cart_data["cart_id"] ?>requested_total_span">
                          <?= $requested_total ?>
                        </span>
                      </p>
                    </div>
                  </div>
                </li>
              </ul>
            </div>
            <?php $selectAllArray[$x] = $singleProductObject;
          }
          $encodedSelectAll = json_encode($selectAllArray); ?>
        </section>
        <script>
          function sendToPHP2(array = <?= $encodedSelectAll ?>) {
            const selectAllCheckbox = document.getElementById("selectAllCheckBox");
            const checkboxes = document.querySelectorAll('input[name="singleCheckbox"]');

            if (selectAllCheckbox.checked) {
              checkboxes.forEach(checkbox => {
                checkbox.checked = true;
              });
              document.getElementById("checkoutButton").disabled = false;
              let AllunitPrices = 0;
              let AllDeliveryCosts = 0;

              for (let index = 0; index < array.length; index++) {
                AllunitPrices += parseInt(array[index].unitPrice);
                AllDeliveryCosts += parseInt(array[index].deliveryFee);
              }

              const AllOrderTotal = AllunitPrices + AllDeliveryCosts;

              document.getElementById('subtotal').innerText = "Rs. " + AllunitPrices.toFixed(2);
              document.getElementById('deliveryCost').innerText = "Rs. " + AllDeliveryCosts.toFixed(2);
              document.getElementById('orderTotal').innerText = "Rs. " + AllOrderTotal.toFixed(2);
            } else {
              checkboxes.forEach(checkbox => {
                checkbox.checked = false;
              });
              document.getElementById('subtotal').innerText = "Rs. 0.00";
              document.getElementById('deliveryCost').innerText = "Rs. 0.00";
              document.getElementById('orderTotal').innerText = "Rs. 0.00";
            }
          }
        </script>
        <?php require_once "./guis/partials/cart/order_total_ui.php"; ?>
      </div>
      <?php require_once "./guis/partials/singleproductView/relatedProducts.php";
      relatedProduct("Based On your Favour", $categoryPublicId);
    } else {
      require_once "./guis/partials/cart/empty_cart.php";
    } ?>
  </main>


<?php require_once "./guis/partials/alert.php"; ?>
<script src="./public/js/cart2.js"></script>
<script src="./public/js/cart.js"></script>
<script type="text/javascript" src="https://www.payhere.lk/lib/payhere.js"></script>
<?php require_once "./guis/partials/footer.php"; 
 } else { ?><script>window.location.href = "/";</script><?php } ?>