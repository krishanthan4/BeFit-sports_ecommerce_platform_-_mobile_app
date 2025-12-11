<?php require_once "./guis/partials/header.php";
require_once "./guis/partials/nav.php";

if (isset($_SESSION["user"])) {
  ?>

  <!-- payment detais section -->
  <section class="flex flex-col lg:mx-[20%] " aria-labelledby="payment-details-heading  ">

    <div class="shadow sm:rounded-md sm:overflow-hidden ">
      <div class=" py-6 px-4 sm:p-6">
        <div>
          <h2 id="payment-details-heading" class="text-lg leading-6 font-medium text-gray-200">Add New Product</h2>
        </div>
        <div class="mt-6 grid grid-cols-4 gap-6">
          <div class="col-span-4 sm:col-span-2">
            <label for="country" class="block text-sm font-medium text-gray-400">Product Category</label>
            <select id="category"
              class="mt-1 block w-full  border border-[#323336] rounded-md shadow-sm py-2 px-3 bg-[#252629]  text-gray-400  sm:text-sm">
              <option value="0">Select Category</option>
              <?php
              $category_rs = Database::search("SELECT * FROM category");
              $category_num = $category_rs->num_rows;

              for ($x = 0; $x < $category_num; $x++) {
                $category_data = $category_rs->fetch_assoc();
                ?>
                <option value="<?php echo $category_data["cat_id"]; ?>">
                  <?php echo $category_data["cat_name"]; ?>
                </option>
                <?php
              }
              ?>
            </select>
          </div>
          <div class="col-span-4 sm:col-span-2">
            <label for="country" class="block text-sm font-medium text-gray-400">Select Product Brand</label>
            <select id="brand"
              class="mt-1 block w-full  border border-[#323336] rounded-md shadow-sm py-2 px-3 bg-[#252629]  text-gray-400  sm:text-sm">
              <option value="0">Select Brand</option>
              <?php
              $brand_rs = Database::search("SELECT * FROM brand");
              $brand_num = $brand_rs->num_rows;

              for ($x = 0; $x < $brand_num; $x++) {
                $brand_data = $brand_rs->fetch_assoc();
                ?>
                <option value="<?php echo $brand_data["brand_id"]; ?>">
                  <?php echo $brand_data["brand_name"]; ?>
                </option>
                <?php
              }
              ?>
            </select>
          </div>
          <div class="col-span-4 sm:col-span-2">
            <label class="block text-sm font-medium text-gray-400">Product Title</label>
            <input type="text" id="title"
              class="mt-1 block w-full border border-[#323336] rounded-md shadow-sm py-2 px-3 bg-[#252629]  text-gray-400  sm:text-sm">
          </div>
          <div class="col-span-4 sm:col-span-2">
            <label for="country" class="block text-sm font-medium text-gray-400">Select Product Model</label>
            <select id="model"
              class="mt-1 block w-full  border border-[#323336] rounded-md shadow-sm py-2 px-3 bg-[#252629]  text-gray-400  sm:text-sm">
              <option value="0">Select Model</option>
              <?php
              $model_rs = Database::search("SELECT * FROM model");
              $model_num = $model_rs->num_rows;

              for ($x = 0; $x < $model_num; $x++) {
                $model_data = $model_rs->fetch_assoc();
                ?>
                <option value="<?php echo $model_data["model_id"]; ?>">
                  <?php echo $model_data["model_name"]; ?>
                </option>
                <?php
              }
              ?>
            </select>
          </div>
          <div class="col-span-4 sm:col-span-2">
            <label for="postal-code" class="block text-sm font-medium text-gray-400">Select Product Condition</label>
            <fieldset>
              <div class="pt-6 space-y-6 sm:pt-4 sm:space-y-4">
                <div class="flex items-center text-base sm:text-sm">
                  <input type="radio" name="c" id="brandNew" 
                    class="flex-shrink-0 h-4 w-4 border-[#323336] bg-[#3e4045] rounded-sm text-gray-600 ">
                  <label for="color-0" class="ml-3 min-w-0 flex-1 text-gray-400">Brand New</label>
                </div>
                <div class="flex items-center text-base sm:text-sm">
                  <input type="radio" name="c" id="used"
                    class="flex-shrink-0 h-4 w-4 border-[#323336] bg-[#3e4045] rounded-sm text-gray-600 ">
                  <label for="color-0" class="ml-3 min-w-0 flex-1 text-gray-400">Used</label>
                </div>

              </div>
            </fieldset>
          </div>
          <div class="col-span-4 sm:col-span-2">
            <label for="country" class="block text-sm font-medium text-gray-400">Select Product Color</label>
            <select id="color"
              class="mt-1 block w-full  border border-[#323336] rounded-md shadow-sm py-2 px-3 bg-[#252629]  text-gray-400  sm:text-sm">
              <option value="0">Select Color</option>
              <?php
              $clr_rs = Database::search("SELECT * FROM color");
              $clr_num = $clr_rs->num_rows;

              for ($x = 0; $x < $clr_num; $x++) {
                $clr_data = $clr_rs->fetch_assoc();
                ?>
                <option value="<?php echo $clr_data["clr_id"]; ?>">
                  <?php echo $clr_data["clr_name"]; ?>
                </option>
                <?php
              }
              ?>
            </select>
            <div class="flex">
              <div class="relative w-full pt-3 overflow-hidden">
                <input type="text" placeholder="Add New Color" id="colorinput"
                  class="mt-1 block w-full border border-[#323336] rounded-md shadow-sm py-2 px-3 bg-[#252629]  text-gray-400  sm:text-sm" />
                <button type="button" onclick="addColor();"
                  class="absolute top-4 end-0 p-2.5 h-[70%] text-sm bg-gray-800 border border-transparent rounded-md shadow-sm py-2 px-4 inline-flex justify-center items-center font-medium text-white hover:bg-[#374151] focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-gray-900 ">
                  <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5"
                    stroke="currentColor" class="mr-1 w-6 h-6">
                    <path stroke-linecap="round" stroke-linejoin="round"
                      d="M12 9v6m3-3H9m12 0a9 9 0 1 1-18 0 9 9 0 0 1 18 0Z" />
                  </svg>
                  Add</button>
              </div>
            </div>



          </div>
          <div class="col-span-4 sm:col-span-2">
            <label class="block text-sm font-medium text-gray-400">Add Product Quantity</label>
            <input type="number" id="quantity"
              class="mt-1 block w-full border border-[#323336] rounded-md shadow-sm py-2 px-3 bg-[#252629]  text-gray-400  sm:text-sm">
          </div>
          <div class="col-span-4 sm:col-span-2">
            <label class="block text-sm font-medium text-gray-400">Cost Per Item</label>
            <div class="flex">
              <span
                class="inline-flex items-center px-3 text-sm text-gray-200 bg-[#2e2f33] border rounded-e-0 border-[#323336] rounded-s-md">
                Rs.
              </span>
              <input type="text" id="cost"
                class="rounded-none outline-none bg-[#252629] border text-gray-200 block flex-1 min-w-0 w-full text-sm border-[#323336] p-2.5 "
                placeholder="10000">
              <span
                class="inline-flex items-center px-3 text-sm text-gray-200 bg-[#2e2f33] border rounded-e-md border-[#323336] rounded-s-0 ">
                .00
              </span>
            </div>
          </div>
        </div>
      </div>
      <div class=" py-6 px-4 sm:p-6">
        <div>
          <h2 id="payment-details-heading" class="text-lg leading-6 font-medium text-gray-200">Delivery Cost</h2>
        </div>
        <div class="mt-6 grid grid-cols-4 gap-6 ">
          <div class="col-span-4 sm:col-span-2">
            <label class="block text-sm font-medium mb-2 text-gray-400">Delivery Fee</label>
            <div class="flex">
              <span
                class="inline-flex items-center px-3 text-sm text-gray-200 bg-[#2e2f33] border rounded-e-0 border-[#323336] rounded-s-md">
                Rs.
              </span>
              <input type="text" id="deliveryFee"
                class="rounded-none outline-none bg-[#252629] border text-gray-200 block flex-1 min-w-0 w-full text-sm border-[#323336] p-2.5 "
                placeholder="10000">
              <span
                class="inline-flex items-center px-3 text-sm text-gray-200 bg-[#2e2f33] border rounded-e-md border-[#323336] rounded-s-0 ">
                .00
              </span>
            </div>
          </div>

        </div>

      </div>
      <div class=" py-6 px-4 sm:p-6">
        <div>
          <h2 id="payment-details-heading" class="text-lg leading-6 font-medium text-gray-200">Product Description</h2>
        </div>
        <div class="mt-6 grid grid-cols-1 gap-6 ">
          <textarea id="description" rows="5" class="p-3 outline-none w-full rounded-md border-2 bg-[#252629] border-[#323336]"></textarea>
          <div>
            <h2 id="payment-details-heading" class="text-lg leading-6 font-medium text-gray-200">Product Images</h2>
            <label class="block text-sm font-medium text-gray-500 mt-1">Add Atleast 1 Image of the
              Product</label>
          </div>
          <!-- images adding section start -->
          <div class="grid grid-cols-3  gap-3 mx-auto">
            <div
              class="border-2 rounded-full  border-[#323336] bg-[#2e2f33]/60 w-48 h-48 overflow-hidden">
              <img src="https://static.thenounproject.com/png/3407390-200.png" class="w-full h-full object-cover object-center" id="image0">
            </div>
            <div
              class="border-2 rounded-full  border-[#323336] bg-[#2e2f33]/60 w-48 h-48 overflow-hidden">
              <img src="https://static.thenounproject.com/png/3407390-200.png" class="w-full h-full object-cover object-center" id="image1">
            </div>
            <div
              class="border-2 rounded-full  border-[#323336] bg-[#2e2f33]/60 w-48 h-48 overflow-hidden">
              <img src="https://static.thenounproject.com/png/3407390-200.png" class="w-full h-full object-cover object-center" id="image2">
            </div>
          </div>
          <div>
            <input type="file" class="hidden" id="imageUploader" multiple />
            <label for="imageUploader" onclick="uploadProductImages();"
              class="bg-gray-800 border cursor-pointer border-transparent rounded-md shadow-sm py-2 px-4 inline-flex justify-center text-sm font-medium text-white hover:bg-[#374151] ">
              <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5"
                stroke="currentColor" class="w-6 h-6">
                <path stroke-linecap="round" stroke-linejoin="round"
                  d="M7.5 7.5h-.75A2.25 2.25 0 0 0 4.5 9.75v7.5a2.25 2.25 0 0 0 2.25 2.25h7.5a2.25 2.25 0 0 0 2.25-2.25v-7.5a2.25 2.25 0 0 0-2.25-2.25h-.75m0-3-3-3m0 0-3 3m3-3v11.25m6-2.25h.75a2.25 2.25 0 0 1 2.25 2.25v7.5a2.25 2.25 0 0 1-2.25 2.25h-7.5a2.25 2.25 0 0 1-2.25-2.25v-.75" />
              </svg>
              Upload Images</label>
          </div>

        </div>

      </div>

      <div class="px-4 py-3  text-right sm:px-6">
        <button onclick="addProduct();"
          class="bg-gray-800 border border-transparent rounded-md shadow-sm py-2 px-4 inline-flex justify-center text-sm font-medium text-white hover:bg-gray-900 ">Save</button>
      </div>
    </div>

  </section>

<!-- alert model -->
<?php require_once "./guis/partials/alert.php"; ?>
<!--alert model -->

<script src="./public/js/uploadProductImages.js"></script>
<script src="./public/js/myProducts.js"></script>
<?php require_once "./guis/partials/footer.php";
 } else { ?><script>window.location.href = "/";</script><?php } ?>