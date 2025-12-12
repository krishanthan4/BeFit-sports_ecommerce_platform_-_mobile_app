<?php
require_once "./guis/partials/header.php";

if (isset($_SESSION["admin"])) {
  ?>
  <div id="backgroundBlack" class="hidden bg-black w-screen h-screen fixed opacity-50 "></div>
  <?php
  require_once "./guis/partials/admin_nav.php"; ?>
  <div class=" lg:w-[80%] w-[90%] mx-auto  my-4 flex flex-row items-center">
    <p class="sm:text-xl sm:text-center text-base md:ms-6">Manage Categories</p>
        <button onclick="addCategory();" type="submit"
        class="inline-flex items-center ms-auto py-2 px-2  text-sm font-medium text-[#202124] bg-yellow-600/90 hover:bg-yellow-600/70 rounded-lg">
        <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor"
          class="w-6 h-6">
          <path stroke-linecap="round" stroke-linejoin="round" d="M12 4.5v15m7.5-7.5h-15" />
        </svg>
        Add
      </button> 
 
  </div>

  <div class="px-5 py-2">

    <div id="categoryModel"
      class="hidden z-30 bg-[#222427] absolute shadow-md shadow-[#17171a] p-5 rounded-md w-fit my-5  sm:mx-[20%] ms-[-12%] me-[1%] sm:overflow-hidden ">
      <div class="flex justify-between items-center ">
        <h2 id="payment-details-heading" class="text-lg font-medium text-gray-200">Add New Category</h2>
        <button onclick="closeModel();" for="">
          <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 20 20" fill="currentColor"
            class="w-5 h-5 hover:text-[#9e3030] text-[#676972] cursor-pointer">
            <path
              d="M6.28 5.22a.75.75 0 0 0-1.06 1.06L8.94 10l-3.72 3.72a.75.75 0 1 0 1.06 1.06L10 11.06l3.72 3.72a.75.75 0 1 0 1.06-1.06L11.06 10l3.72-3.72a.75.75 0 0 0-1.06-1.06L10 8.94 6.28 5.22Z" />
          </svg>
        </button>
      </div>
      <div class="flex items-center gap-1">
        <input required type="text" id="title" placeholder="Category Title"
          class="mt-1 text-gray-400 block w-full border bg-[#33353a] border-[#2a2c30] rounded-md shadow-sm py-2 px-3 focus:outline-none sm:text-sm">
        <div class="w-36 h-36 justify-center flex flex-col  items-center">
          <label title="Upload Image"
            class="bg-gradient-to-tr from-yellow-500 to-red-500  border cursor-pointer border-transparent rounded-full shadow-sm p-2 inline-flex justify-center text-xs text-white hover:bg-[#374151] ">
            <input required type="file" class="hidden" id="imageUploader" />

            <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5"
              stroke="currentColor" class="w-6 h-6">
              <path stroke-linecap="round" stroke-linejoin="round"
                d="M7.5 7.5h-.75A2.25 2.25 0 0 0 4.5 9.75v7.5a2.25 2.25 0 0 0 2.25 2.25h7.5a2.25 2.25 0 0 0 2.25-2.25v-7.5a2.25 2.25 0 0 0-2.25-2.25h-.75m0-3-3-3m0 0-3 3m3-3v11.25m6-2.25h.75a2.25 2.25 0 0 1 2.25 2.25v7.5a2.25 2.25 0 0 1-2.25 2.25h-7.5a2.25 2.25 0 0 1-2.25-2.25v-.75" />
            </svg>
          </label>
        </div>
        <div class="w-36 h-36 justify-center flex flex-col  items-center">
          <label title="Upload Icon"
            class="bg-gradient-to-tr from-gray-500 to-gray-700  border cursor-pointer border-transparent rounded-full shadow-sm py-1 px-1 inline-flex justify-center text-xs text-white hover:bg-[#374151] ">
            <input required type="file" class="hidden" id="iconUploader" />
            <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor"
              class="bi bi-brightness-low" viewBox="0 0 16 16">
              <path
                d="M8 11a3 3 0 1 1 0-6 3 3 0 0 1 0 6m0 1a4 4 0 1 0 0-8 4 4 0 0 0 0 8m.5-9.5a.5.5 0 1 1-1 0 .5.5 0 0 1 1 0m0 11a.5.5 0 1 1-1 0 .5.5 0 0 1 1 0m5-5a.5.5 0 1 1 0-1 .5.5 0 0 1 0 1m-11 0a.5.5 0 1 1 0-1 .5.5 0 0 1 0 1m9.743-4.036a.5.5 0 1 1-.707-.707.5.5 0 0 1 .707.707m-7.779 7.779a.5.5 0 1 1-.707-.707.5.5 0 0 1 .707.707m7.072 0a.5.5 0 1 1 .707-.707.5.5 0 0 1-.707.707M3.757 4.464a.5.5 0 1 1 .707-.707.5.5 0 0 1-.707.707" />
            </svg>
          </label>
        </div>
      </div>
      <button onclick="addCategorySend();"
        class="bg-green-500/90 float-right  w-fit h-fit rounded-md py-2 px-4 text-sm font-medium text-[#2a2c30] hover:bg-green-500/70">Save</button>
    </div>
    <div class="flex mt-2 flex-col">
      <div class="-my-2 overflow-x-auto sm:-mx-6 lg:mx-2">
        <div class="py-2 align-middle inline-block min-w-full sm:px-3 lg:px-8">
          <div class="shadow overflow-hidden flex justify-center sm:rounded-lg">
            <table class="min-w-7 ">
              <thead class="bg-[#2a2c30]/90">
                <tr>
                  <th scope="col" class="px-3 py-2 text-left text-xs font-medium text-gray-400 uppercase tracking-wider">
                  </th>
                  <th scope="col" class="px-3 py-2 text-left text-xs font-medium text-gray-400 uppercase tracking-wider">
                    Category Name</th>
                  <th scope="col"
                    class="px-3 py-2 text-center text-xs font-medium text-gray-400 uppercase tracking-wider">
                    Category Image</th>
                  <th scope="col"
                    class="px-3 py-2 text-center text-xs font-medium text-gray-400 uppercase tracking-wider">
                  </th>

                </tr>
              </thead>

              <tbody id="tableBody" class="  divide-y divide-[#2a2c30]">
                <?php
                $category_rs = Database::search("SELECT * FROM `category`");

                for ($x = 0; $x < $category_rs->num_rows; $x++) {
                  $category = $category_rs->fetch_assoc();

                  ?>
                  <tr id="tr<?= $category["cat_id"] ?>">
                    <td class="px-6 py-4 whitespace-nowrap">
                      <?= $category["cat_id"] ?>
                    </td>
                    <td class="px-6 py-4 whitespace-nowrap">
                      <?= $category["cat_name"] ?>
                    </td>
                    <td class="px-6 py-2 whitespace-nowrap">
                      <div class="flex justify-center">
                        <img src="<?= $category["cat_img"] ?>" class="w-10 h-10 rounded-full object-cover" alt="">
                      </div>
                    </td>
                    <td class="px-6 py-4 whitespace-nowrap">
                      <button id="delete<?= $category["cat_id"] ?>" onclick="deleteCategory('<?= $category['cat_id'] ?>');"
                        class="bg-red-500/90 hover:bg-red-500/70 py-1 px-2 rounded-md text-[#2a2c30]">Delete</button>
                    </td>
                  </tr>


                  <!-- More people... -->
                </tbody>
              <?php } ?>
            </table>
          </div>
        </div>
      </div>
    </div>
  </div>
  <?php
  require_once "./guis/partials/alert.php";

  ?>
  <script>
    function deleteCategory(cat_id) {
      const request = new XMLHttpRequest();
      try {
        const response = await api.delete(`/admin/categories/${cat_id}`);
        if (response.success) {
          document.getElementById("tr" + cat_id).remove();
        }
      } catch (error) {
        console.error("Error deleting category:", error);
      }
    }

    function addCategory() {
      document.getElementById("categoryModel").classList.remove("hidden");
      document.getElementById("backgroundBlack").classList.remove("hidden");
    }

    function closeModel() {
      document.getElementById("categoryModel").classList.add("hidden");
      document.getElementById("backgroundBlack").classList.add("hidden");
    }

    async function addCategorySend() {
      const title = document.getElementById("title").value;
      
      try {
        const response = await api.post("/admin/categories", {
          name: title,
          description: ""
        });
        
        if (response.success) {
          document.getElementById("categoryModel").classList.add("hidden");
          document.getElementById("backgroundBlack").classList.add("hidden");
          document.getElementById("msgToast").classList.remove("border-red-500");
          document.getElementById("msgToast").classList.add("border-green-500");
          document.getElementById("msgIcon").classList.remove("bg-red-500");
          document.getElementById("msgIcon").classList.add("bg-green-500");
          document.getElementById("msgToast").classList.remove("hidden");
          document.getElementById("msg").innerText = "Category added successfully!";
          setTimeout(() => {
            document.getElementById("msgToast").classList.add("hidden");
            window.location.reload();
          }, 2500);
          } else if (request.responseText == "already") {
            document.getElementById("categoryModel").classList.add("hidden");
            document.getElementById("backgroundBlack").classList.add("hidden");
            document.getElementById("msgToast").classList.remove("hidden");
            document.getElementById("msg").innerText = "category has already added !";
            setTimeout(() => {
              document.getElementById("categoryModel").classList.remove("hidden");
              document.getElementById("backgroundBlack").classList.remove("hidden");
              document.getElementById("msgToast").classList.add("hidden");
            }, 2500);
          } else {
            document.getElementById("categoryModel").classList.add("hidden");
            document.getElementById("backgroundBlack").classList.add("hidden");
            document.getElementById("msgToast").classList.remove("hidden");
            document.getElementById("msg").innerText = request.responseText;
            setTimeout(() => {
              document.getElementById("categoryModel").classList.remove("hidden");
              document.getElementById("backgroundBlack").classList.remove("hidden");
              document.getElementById("msgToast").classList.add("hidden");
            }, 2500);
          }
        }
      }
      request.open("POST", "/processes/addCategoryProcess.php", true);
      request.send(form);
    }
  </script>
  </div>
  </body>

  </html>
<?php } else { ?>
  <script>window.location.href = "/adminSignin";</script>
<?php } ?>