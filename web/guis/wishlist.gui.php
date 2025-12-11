<?php require_once "./guis/partials/header.php";
require_once "./guis/partials/nav.php";

if (isset ($_SESSION["user"])) {

  $watchlist_rs = Database::search("SELECT `wishlist`.`qty` AS `wishlist_qty`, `wishlist`.*, `product`.*, `product_has_color`.*, `color`.*, `condition`.*, `user`.*
  FROM `wishlist`
  INNER JOIN `product` ON `wishlist`.`product_id` = `product`.`id`
  INNER JOIN `product_has_color` ON `product_has_color`.`product_id` = `product`.`id`
  INNER JOIN `color` ON `product_has_color`.`color_clr_id` = `color`.`clr_id`
  INNER JOIN `condition` ON `product`.`condition_condition_id` = `condition`.`condition_id`
  INNER JOIN `user` ON `product`.`user_email` = `user`.`email`
  WHERE `wishlist`.`user_email` = '".$_SESSION["user"]["email"]."'");

  $watchlist_num = $watchlist_rs->num_rows;

  ?>

  <div class="h-full  ">

  <main class="max-w-7xl mx-auto pb-10 py-12 lg:px-8">
      <div class="grid grid-cols-12 gap-x-5">

        <?php require_once "./guis/partials/user_profile_sidebar.php" ?>

        <div class="space-y-6 sm:px-6 lg:px-0 col-span-9">
          <div class="max-w-7xl mx-auto sm:px-2 lg:px-6   h-full ">
            <div class="max-w-2xl  mx-auto px-4 lg:max-w-4xl lg:px-0">
              <h1 class="text-2xl font-extrabold tracking-tight text-gray-200 sm:text-3xl">Wishlist</h1>
              <p class="mt-2 text-sm text-gray-500">Check the status of recent orders, manage returns, and discover
                similar products.</p>
            </div>

            <?php
            if ($watchlist_num == 0) {

              ?>
              <div class="col-span-12 lg:col-span-9 rounded-sm flex justify-center items-center my-2 w-full ">
                <div class="grid grid-cols-1 lg:grid-cols-2 items-center">
                  <div class="col-span-12 lg:col-span-6 flex justify-center">
                    <img src="./public/images/wishlist.svg" alt=""
                      class="h-[250px]  bg-center bg-contain bg-no-repeat my-2">
                  </div>
                  <div class="col-span-12 lg:col-span-6 text-center my-2 flex justify-center flex-col">
                    <label class="text-xl text-gray-400 font-semibold mt-2">You have no items in your Watchlist yet.</label>
                    <div class="lg:flex lg:justify-center mt-7">
                      <a href="/" class="lg:mr-3">
                        <button
                          class="bg-yellow-600/90 text-[#1d1e20] px-6 py-2 text-[16px] font-semibold rounded hover:bg-yellow-700/80 transition duration-300">
                          <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5"
                            stroke="currentColor" class="w-6 h-6 inline-block">
                            <path stroke-linecap="round" stroke-linejoin="round"
                              d="M2.25 3h1.386c.51 0 .955.343 1.087.835l.383 1.437M7.5 14.25a3 3 0 0 0-3 3h15.75m-12.75-3h11.218c1.121-2.3 2.1-4.684 2.924-7.138a60.114 60.114 0 0 0-16.536-1.84M7.5 14.25 5.106 5.272M6 20.25a.75.75 0 1 1-1.5 0 .75.75 0 0 1 1.5 0Zm12.75 0a.75.75 0 1 1-1.5 0 .75.75 0 0 1 1.5 0Z" />
                          </svg>
                          Start Shopping</button>
                      </a>
                    </div>
                  </div>
                </div>
              </div>
              <?php

            } else {
              ?>
              <div class="flex mt-7  flex-col">
                <div class="-my-2 overflow-x-auto sm:-mx-6 lg:mx-2">
                  <div class="py-2 align-middle inline-block min-w-full sm:px-6 lg:px-8">
                    <div class="shadow overflow-hidden border-b border-gray-200 sm:rounded-lg">
                      <table class="min-w-full shadow-md shadow-[#1c1c1f]">
                        <thead class="bg-[#27282b]">
                          <tr>
                            <th scope="col"
                              class="px-3 py-3 text-left text-xs font-medium text-gray-200 uppercase tracking-wider">image
                            </th>
                            <th scope="col"
                              class="px-3 py-3 text-left text-xs font-medium text-gray-200 uppercase tracking-wider">Product
                              Name</th>
                     
                            <th scope="col"
                              class="px-3 py-3 text-center text-xs font-medium text-gray-200 uppercase tracking-wider">Unit
                              Price</th>
                            <th scope="col"
                              class="px-3 py-3 text-center text-xs font-medium text-gray-200 uppercase tracking-wider">
                              Quantity</th>
                        
                            <th scope="col"
                              class="px-3 py-3 text-center text-xs font-medium text-gray-200 uppercase tracking-wider">
                              Actions
                            </th>
                          </tr>
                        </thead>
                        <?php
                        for ($x = 0; $x < $watchlist_num; $x++) {
                          $watchlist_data = $watchlist_rs->fetch_assoc();
                          $list_id = $watchlist_data["w_id"];
                          $colorName = $watchlist_data["clr_name"];
                          $product_id = $watchlist_data["product_id"];
                          ?>
                          <tbody class="bg-[#25262994] divide-y divide-[#303135]">
                            <tr>
                              <td class="px-2 py-3 whitespace-nowrap">
                                <div class="flex items-center">
                                  <div class="p-1 bg-[#27282b] rounded-full">
   <div class="flex-shrink-0 h-10 w-10 ">
                                    <?php
                                    $img_rs = Database::search("SELECT * FROM product_img WHERE product_id='" . $product_id . "'");
                                    $img_data = $img_rs->fetch_assoc(); ?>
                                    <img class="h-10 w-10 rounded-full " src="<?php echo $img_data["img_path"]; ?>" alt="">
                                  </div>
                                  </div>
                               
                                  <div class="ml-4">
                                  </div>
                              </td>

                              <td class="px-3 py-3  whitespace-nowrap">
                                <div class="text-sm text-gray-400">
                                  <?php echo $watchlist_data["title"]; ?>
                                </div>
                              </td>
                              <td class="px-3 py-3 whitespace-nowrap text-center text-sm text-gray-400">
                                <!-- <button  class="p-3 rounded-md bg-black text-white">Add to Cart</button> -->
                                Rs.
                                <?php echo $watchlist_data["price"]; ?> .00

                              </td>

                              <td id="quantity" class="px-3 py-3 text-center text-gray-400 whitespace-nowrap  text-sm font-medium">
                              <?= $watchlist_data["wishlist_qty"] ?>
                              </td>
                              <td class="px-3 py-3 whitespace-nowrap text-center flex text-[12px] gap-2 items-center">
                                <?php
                                if (isset ($_SESSION["user"])) {
                                  $cart_rs = Database::search("SELECT * FROM cart WHERE user_email='" . $_SESSION["user"]["email"] . "' AND product_id='" . $product_id . "'");
                                  $cart_num = $cart_rs->num_rows;
                                  if ($cart_num == 1) {
                                    ?>
                                    <button onclick='addToCart(<?php echo $product_id; ?>);' id="shop<?php echo $product_id; ?>"
                                      class="py-1 px-2 border border-green-600 font-bold rounded text-green-600 hover:text-[#252629] hover:bg-green-400/50 focus:outline-none  flex items-center justify-center">
                                   Buy
                                    </button>
                                    <?php
                                  } else {
                                    ?>
                                    <button onclick='addToCart(<?php echo $product_id; ?>);' id="shop<?php echo $product_id; ?>"
                                      class="py-1 px-2 border border-green-600 font-bold rounded text-green-600 hover:text-[#252629] hover:bg-green-400/50 focus:outline-none  flex items-center justify-center">
                                      Buy
                                    </button>
                                    <?php
                                  }
                                }
                                ?>
                                <button onclick="removeFromWatchlist(<?php echo $list_id; ?>);"
                                  class="text-red-500 hover:text-[#34363a] w-[30px] h-[30px] border border-red-500 rounded-full flex items-center justify-center p-1  hover:bg-red-500/70 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-red-500"><svg
                                    xmlns="http://www.w3.org/2000/svg" width="20" height="20" fill="currentColor"
                                    class="bi bi-trash" viewBox="0 0 16 16">
                                    <path
                                      d="M5.5 5.5A.5.5 0 0 1 6 6v6a.5.5 0 0 1-1 0V6a.5.5 0 0 1 .5-.5m2.5 0a.5.5 0 0 1 .5.5v6a.5.5 0 0 1-1 0V6a.5.5 0 0 1 .5-.5m3 .5a.5.5 0 0 0-1 0v6a.5.5 0 0 0 1 0z" />
                                    <path
                                      d="M14.5 3a1 1 0 0 1-1 1H13v9a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2V4h-.5a1 1 0 0 1-1-1V2a1 1 0 0 1 1-1H6a1 1 0 0 1 1-1h2a1 1 0 0 1 1 1h3.5a1 1 0 0 1 1 1zM4.118 4 4 4.059V13a1 1 0 0 0 1 1h6a1 1 0 0 0 1-1V4.059L11.882 4zM2.5 3h11V2h-11z" />
                                  </svg></b>

                              </td>
                            </tr>
                            <!-- More people... -->
                          </tbody>
                          <?php

                        }

                        ?>



                      </table>
                    </div>
                  </div>
                </div>
              </div>

              <?php
            }


            ?>







          </div>
        </div>
    </main>
  </div>

<!-- alert model -->
<?php require_once "./guis/partials/alert.php"; ?>
<!--alert model -->


<script src="./public/js/wishlist.js"></script>
<?php require_once "./guis/partials/footer.php";

} else { ?><script>window.location.href = "/";</script><?php } ?>
