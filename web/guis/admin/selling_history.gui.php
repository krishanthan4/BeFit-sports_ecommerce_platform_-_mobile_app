<?php require_once "./guis/partials/header.php";
require_once "./guis/partials/nav.php";

if (isset($_GET["id"])) { $pageId = $_GET["id"]; } else { $pageId = 1; }

if (isset($_SESSION["user"])) { ?>
    <div class="">
        <main class="max-w-7xl mx-auto pb-5 lg:py-12 lg:px-2  ">
            <div class="lg:grid lg:grid-cols-12 lg:gap-x-5 ">
                <?php require_once "./guis/partials/user_profile_sidebar.php" ?>
                <div class="space-y-6 sm:px-6 lg:px-0 lg:col-span-9 col-span-8 md:mt-0 mt-10">
                    <div class="max-w-7xl mx-auto sm:px-2 lg:px-2   h-full ">

                        <div class="w-full mx-4  my-4 flex flex-row items-center">
                            <h1 class="text-3xl py-3 ps-3">Selling History</h1>
                            <!-- search bar -->
                            <div id="searchForm"
                                class="flex xl:w-[70%] lg:max-w-[50%] lg:min-w-[40%] sm:w-[70%] gap-2 items-center mx-10">
                                <input type="text" id="searchInput" placeholder="Search by name or email"
                                    class="bg-[#2e3035] text-gray-400 border border-[#38393f]  text-sm rounded-lg w-full p-2 outline-none"
                                    required />
                            </div>

                            <!-- search bar -->
                        </div>

                        <!-- product table start -->
                        <div class="flex my-10 flex-col">
                            <div class="-my-2 overflow-x-auto sm:-mx-6">
                                <div class="py-2 align-middle inline-block min-w-full sm:px-6 lg:px-2">
                                    <div class="shadow overflow-hidden sm:rounded-lg" id="sellingHistoryAboveDiv">
                                        <table class="min-w-full divide-y divide-[#3e3e46]" id="sellingHistoryTable">
                                            <thead class="bg-[#26262b]">
                                                <tr>
                                                    <th scope="col"
                                                        class="px-6 py-3 text-left text-xs font-medium text-gray-300 uppercase tracking-wider">
                                                        Invoice ID</th>
                                                    <th scope="col"
                                                        class="px-6 py-3 text-left text-xs font-medium text-gray-300 uppercase tracking-wider">
                                                        Product</th>
                                                    <th scope="col"
                                                        class="px-6 py-3 text-left text-xs font-medium text-gray-300 uppercase tracking-wider">
                                                        Buyer</th>
                                                    <th scope="col"
                                                        class="px-6 py-3 text-left text-xs font-medium text-gray-300 uppercase tracking-wider">
                                                        Date</th>
                                                    <th scope="col"
                                                        class="px-6 py-3 text-left text-xs font-medium text-gray-300 uppercase tracking-wider">
                                                        Amount</th>
                                                    <th scope="col"
                                                        class="px-6 py-3 text-left text-xs font-medium text-gray-300 uppercase tracking-wider">
                                                        Quantity</th>
                                                </tr>
                                            </thead>
                                            <tbody id="sellingHistoryTableBody" class="bg-[#26262b]/10 divide-y divide-[#3e3e46]">
                                                <?php
                                                $AllProductsHistory_rs = Database::search("SELECT COUNT(`product_id`) as total FROM `invoice_has_products` INNER JOIN `product` ON `product`.`id`=`invoice_has_products`.`product_id` WHERE `user_email`=? ", [$_SESSION["user"]["email"]]);
                                                $AllProductsHistory = $AllProductsHistory_rs->fetch_assoc();
                                                $totalCount = $AllProductsHistory['total'];
                                                $itemsPerPage = 10;
                                                $totalPages = ceil($totalCount / $itemsPerPage);
                                                if ($AllProductsHistory_rs->num_rows) {
                                                    $output = preg_replace('/[^0-9]/', '', $pageId);
                                                    if ($output) {
                                                        $pageId = $pageId - 1;
                                                        $from = 0;
                                                        $to = 10;
                                                        $from = $to * $pageId;
                                                    } else {
                                                        $from = 0;
                                                        $to = 10;
                                                    }
                                                    $sellingHistory_rs = Database::search("SELECT * FROM `invoice_has_products` INNER JOIN `product` ON `product`.`id`=`invoice_has_products`.`product_id` WHERE `user_email`=? LIMIT $from,$to", [$_SESSION["user"]["email"]]);
                                                    if ($sellingHistory_rs->num_rows !== 0) {
                                                        for ($i = 0; $i < $sellingHistory_rs->num_rows; $i++) {
                                                            $sellingHistory = $sellingHistory_rs->fetch_assoc();
                                                            $boughtBuyer_rs = Database::search("SELECT * FROM `invoice` INNER JOIN `user` ON `invoice`.`user_email`=`user`.`email` WHERE `invoice_id`=?", [$sellingHistory["invoice_id"]]);
                                                            $boughtBuyer = $boughtBuyer_rs->fetch_assoc();
                                                            ?>
                                                            <tr>
                                                                <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-400">
                                                                    <?= $sellingHistory["invoice_id"] ?>
                                                                </td>
                                                                <td class="px-6 py-4 whitespace-nowrap">
                                                                    <div class="text-sm text-gray-400">
                                                                        <?= $sellingHistory["title"] ?>
                                                                    </div>

                                                                </td>
                                                                <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-400">
                                                                    <?php echo ($boughtBuyer["fname"] . " " . $boughtBuyer["lname"]); ?>
                                                                </td>
                                                                <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-400">
                                                                    <?php echo ($boughtBuyer["date"]) ?>
                                                                </td>
                                                                <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-400">Rs.
                                                                    <?= $sellingHistory["price"] ?>.00
                                                                </td>
                                                                <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-400">
                                                                    <?= $sellingHistory["bought_qty"] ?>
                                                                </td>
                                                            </tr>
                                                            <?php
                                                        }
                                                    }
                                                    ?>

                                                    <!-- More people... -->
                                                </tbody>
                                            </table>
<div id="emptySellingHistory" class="col-span-12 hidden lg:col-span-6 flex flex-col justify-center">
<img src="./public/images/noitems.jpg" alt="" class="h-[290px] bg-center w-fit mx-auto bg-contain bg-no-repeat my-2">
                  <div class="col-span-12 lg:col-span-6 text-center my-2 flex justify-center flex-col">
                    <label class="text-xl font-semibold mt-2">You have no Selled Items in here</label>
                  </div>
</div>

                                            <div
                                                class="flex items-center justify-between   px-4 py-3 sm:px-6">
                                                <div class="flex flex-1 justify-between sm:hidden">
                                                    <a href="#"
                                                        class="relative inline-flex items-center rounded-md border border-gray-300  px-4 py-2 text-sm font-medium text-gray-700 hover:bg-gray-50">Previous</a>
                                                    <a href="#"
                                                        class="relative ml-3 inline-flex items-center rounded-md border border-gray-300  px-4 py-2 text-sm font-medium text-gray-700 hover:bg-gray-50">Next</a>
                                                </div>
                                                <div class="hidden sm:flex sm:flex-1 sm:items-center sm:justify-between">


                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <?php require_once "./guis/partials/pagination.php";
                            $currentPage = isset($_GET['id']) ? intval($_GET['id']) : 1;
                            $currentPage = max(1, min($currentPage, $totalPages));
                            $parsedJs = "sellingHistoryPagination";
                            $baseurl = '/sellingHistory?id=';

                            pagination($currentPage, $totalPages, $baseurl, $itemsPerPage, $parsedJs);
                                                } else {
                                                    ?>
                            <script>window.location.href = "/sellingHistory?id=1"</script>
                            <?php
                                                } ?>
                        <?php require_once "./guis/partials/pagination.php"; ?>
                    </div>
                </div>
        </main>
    </div>
    <script src="./public/js/sellingHistory.js"></script>
<!-- product table start -->
<?php require_once "./guis/partials/footer.php";
} else { ?><script>window.location.href = "/";</script><?php } ?>