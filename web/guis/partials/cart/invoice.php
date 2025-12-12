<?php 

function invoice_function(){
  
    if (isset ($_GET["order_id"])) {
        require_once "../../../config/database.php";
    $order_idrrr = $_GET["order_id"];
$invoicerrrs_rs = Database::search("SELECT * FROM `invoice` WHERE `order_id`='" . $order_idrrr . "' ");
$userAddress_rss = Database::search("SELECT * FROM `user_has_address` INNER JOIN `city` ON `city`.`city_id`=`user_has_address`.`city_city_id` INNER JOIN `district` ON `district`.`district_id`=`city`.`district_district_id` WHERE `user_email`='" . $_SESSION["user"]["email"] . "'");
$userAddress = $userAddress_rss->fetch_assoc();
if ($invoicerrrs_rs->num_rows !== 0) {
    for ($r = 0; $r < $invoicerrrs_rs->num_rows; $r++) {
        $invoicerrr = $invoicerrrs_rs->fetch_assoc(); ?>
        <div onclick="redirect('<?= $order_idrrr ?>');" class="w-full opacity-15 bg-black h-screen z-30 fixed">
        </div>
        <div class="flex w-full absolute">
            <div id="cartDiv" class="mx-auto mt-12 bg-[#292a2e] z-50 relative rounded-md  p-5">
                <p onclick="redirect('<?= $order_idrrr ?>');" class="float-right mb-5 text-red-300 cursor-pointer" id="closeXButton"><svg
                        xmlns="http://www.w3.org/2000/svg" viewBox="0 0 20 20" fill="currentColor" class="w-5 h-5">
                        <path
                            d="M6.28 5.22a.75.75 0 0 0-1.06 1.06L8.94 10l-3.72 3.72a.75.75 0 1 0 1.06 1.06L10 11.06l3.72 3.72a.75.75 0 1 0 1.06-1.06L11.06 10l3.72-3.72a.75.75 0 0 0-1.06-1.06L10 8.94 6.28 5.22Z" />
                    </svg>
                </p>
                <div id="showInvoiceTitle" class="bg-[#303136] hidden rounded-t-3xl md:p-20 p-10 ">
                    <div class="flex gap-3 mt-2  text-3xl items-center tracking-[2px]">
                    
                        <p class="tracking-[1px] font-thin">BeFit Corner</p>
                    </div>
                    <p class="flex w-full justify-center font-bold uppercase text-2xl">Invoice</p>

                    <div class="flex flex-wrap justify-between gap-6 mt-14">
                        <div>
                            <h2 class="text-xl font-medium mb-4">
                                <?php echo ($_SESSION["user"]["fname"] . " " . $_SESSION["user"]["lname"]) ?>
                            </h2>
                        </div>

                        <div>
                            <p class="text-base text-right font-normal">
                                <?= $userAddress["line1"] ?>,
                            </p>
                            <p class="text-base text-right font-normal">
                                <?= $userAddress["line2"] ?>,
                            </p>
                            <p class="text-base text-right font-normal">
                                <?= $userAddress["city_name"] ?>,
                            </p>
                            <p class="text-base text-right font-normal">
                                <?= $userAddress["district_name"] ?>.
                            </p>
                        </div>
                    </div>

                </div>
                <div class=" pt-2 pb-4 border-b border-[#3f4047] text-gray-300 px-3">
                    <div class="justify-between flex md:-mx-4  items-center">
                        <h4 class="text-xl font-semibold leading-normal mb-2 text-left">Invoice no :
                            <?= $invoicerrr["invoice_id"] ?>
                        </h4>
                        <p class="float-left mb-0">Invoice date:
                            <?= date("d/m/Y", strtotime($invoicerrr["date"])) ?>
                        </p>
                    </div>
                </div>
                <div class=" py-5 flex-auto">
                    <table id="tableInvoice" class="shadow overflow-hidden border-b border-[#38393f] sm:rounded-lg">
                        <thead  class="bg-[#303136]">
                            <tr>
                                <th scope="col"
                                    class="px-6 py-3 text-center text-xs font-bold text-gray-400 uppercase tracking-wider">
                                    Item
                                </th>
                                <th scope="col"
                                    class="px-6 py-3 text-left text-xs font-bold text-gray-400 uppercase tracking-wider">
                                    Qty
                                </th>
                                <th scope="col"
                                    class="px-6 py-3 text-left text-xs font-bold text-gray-400 uppercase tracking-wider">
                                    Category
                                </th>
                                <th scope="col"
                                    class="px-6 py-3 text-center text-xs font-bold text-gray-400 uppercase tracking-wider">
                                    Rate
                                </th>
                            </tr>
                        </thead>
                        <!-- <thead class="bg-blueGray-800">
                                  <tr class="text-right  uppercase font-light">
                                     <th class="p-3 border-t" scope="col">Item</th>
                                     <th class="p-3 border-t" scope="col">Qty</th>
                                     <th class="p-3 border-t" scope="col">Category</th>
                                     <th class="p-3 border-t" scope="col">Rate</th>
                                  </tr>
                               </thead> -->
                        <tbody class="text-gray-300 text-center">
                            <?php $invoicerrrProduct_rs = Database::search("SELECT * FROM `invoice_has_products`  INNER JOIN `product` ON `invoice_has_products`.`product_id`=`product`.`id` INNER JOIN `category` ON `product`.`category_cat_id`=`category`.`cat_id`  WHERE `invoice_has_products`.`invoice_id`='" . $invoicerrr["invoice_id"] . "'");
                            for ($t = 0; $t < $invoicerrrProduct_rs->num_rows; $t++) {
                                $invoicerrrProduct = $invoicerrrProduct_rs->fetch_assoc(); ?>
                                <tr>
                                    <td class="py-4 p-3 border-t">
                                        <?= $invoicerrrProduct["title"] ?>
                                    </td>
                                    <td class="py-4 p-3 text-center border-t">
                                        <?= $invoicerrrProduct["bought_qty"] ?>
                                    </td>
                                    <td class="py-4 p-3 border-t">
                                        <?= $invoicerrrProduct["cat_name"] ?>
                                    </td>
                                    <td class="py-4 p-3 border-t">Rs.
                                        <?= $invoicerrrProduct["price"] ?>.00
                                    </td>
                                </tr>
                            <?php } ?>
                        </tbody>
                        <tfoot>
                            <tr class="mt-4 text-gray-400">
                                <th class="border-b-0 p-3 border-t">
                                    <p class="text-lg font-semibold pt-2">Total</p>
                                </th>
                                <th class="border-b-0 p-3 border-t" colspan="3">
                                    <p class="text-right text-lg font-semibold pt-2">Rs.
                                        <?= $invoicerrr["total"] ?>.00
                                    </p>
                                </th>
                            </tr>
                        </tfoot>
                    </table>
                </div>
                <div class="float-right"><button id="printButton" onclick="println();"
                        class="outline-none focus:outline-none  uppercase  font-bold text-[#303136] bg-gray-300 text-sm px-6 py-2 rounded-md">Print</button>
                </div>
            </div>
        </div>
        <script>
            function println() {
                var printContent = document.getElementById('cartDiv').innerHTML;
                var originalContent = document.body.innerHTML;
                document.body.innerHTML = printContent;
                document.getElementById("showInvoiceTitle").classList.remove("hidden");
                document.getElementById("printButton").classList.add("hidden");
                document.getElementById("tableInvoice").classList.add("w-full");
                document.getElementById("tableInvoice").classList.add("w-full");
                document.getElementById("closeXButton").classList.add("hidden");
                window.print();
                document.body.innerHTML = originalContent;
            }
            function redirect(order_id) {
                window.location.href = "/orderDetails?order_id=" + order_id;
                
            }
        </script>
    <?php }
}} else {require_once "./guis/partials/nav.php";}}