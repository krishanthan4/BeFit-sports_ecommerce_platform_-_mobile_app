<?php require_once "./guis/partials/header.php";
require_once "./guis/partials/nav.php";
if (isset($_SESSION["user"])) {
?>

<div>
    <main class="max-w-7xl mx-auto pb-10 lg:py-12 lg:px-8">
        <div class="grid grid-cols-12 gap-x-5">
            <?php require_once "./guis/partials/user_profile_sidebar.php" ?>
            <div class="space-y-6 sm:px-6 lg:px-0 lg:col-span-9">
                <h1 class="text-2xl font-extrabold tracking-tight text-gray-200 sm:text-3xl w-[60vw]">Order Status</h1>
                <table class="min-w-full divide-y divide-[#303135]">
                    <thead class="bg-[#252629]">
                        <tr>
                            <th scope="col"
                                class="px-6 py-3 text-left text-xs font-medium text-gray-400 uppercase tracking-wider">
                                Order ID</th>
                            <th scope="col"
                                class="px-6 py-3 text-left text-xs font-medium text-gray-400 uppercase tracking-wider">
                                Order Date</th>
                            <th scope="col"
                                class="px-6 py-3 text-left text-xs font-medium text-gray-400 uppercase tracking-wider">
                                Products</th>
                            <th scope="col"
                                class="px-6 py-3 text-left text-xs font-medium text-gray-400 uppercase tracking-wider">
                                Total</th>
                            <th scope="col"
                                class="px-6 py-3 text-left text-xs font-medium text-gray-400 uppercase tracking-wider">
                                Order Status</th>
                        </tr>
                    </thead>
                    <tbody id="sellingHistoryTableBody" class=" divide-y divide-[#484a50]">
                        <?php
                        $AllProductsHistory_rs = Database::search("SELECT COUNT(`product_id`) FROM `invoice_has_products` INNER JOIN `product` ON `product`.`id`=`invoice_has_products`.`product_id` WHERE `user_email`=? ", [$_SESSION["user"]["email"]]);
                        $AllProductsHistory = $AllProductsHistory_rs->fetch_assoc();

                        $sellingHistory_rs = Database::search("SELECT `invoice`.*,`product`.`user_email` AS `owner_email`,`invoice_has_products`.*,`product`.* FROM `invoice` 
                                                    INNER JOIN `invoice_has_products` 
                                                    ON `invoice`.`invoice_id`=`invoice_has_products`.`invoice_id` 
                                                    INNER JOIN `product` 
                                                    ON `invoice_has_products`.`product_id`=`product`.`id` 
                                                    WHERE `product`.`user_email`=?", [$_SESSION["user"]["email"]]);
                        if ($sellingHistory_rs->num_rows !== 0) {
                            for ($i = 0; $i < $sellingHistory_rs->num_rows; $i++) {
                                $sellingHistory = $sellingHistory_rs->fetch_assoc(); ?>
                                <tr>
                                    <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-400">
                                        <?= $sellingHistory["order_id"] ?>
                                    </td>
                                    <td class="px-6 py-4 whitespace-nowrap">
                                        <div class="text-sm text-gray-400">
                                            <?= $sellingHistory["date"] ?>
                                        </div>

                                    </td>

                                    <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-400">
                                        <?= $sellingHistory["title"] ?>
                                    </td>
                                    <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-400">Rs.
                                        <?= $sellingHistory["price"] ?>.00
                                    </td>
                                    <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-400">
                                        <!-- $sellingHistory["status"]  -->
                                        <select onchange="changeStatus('<?= $sellingHistory['invoice_id'] ?>');" name="" id="status_select" class="bg-transparent py-1 px-1 border border-[#43454b] rounded-sm">
    <option value="1" <?php if(isset($sellingHistory["order_status"]) && intval($sellingHistory["order_status"])==1){?>selected<?php } ?> class="bg-[#252629] py-1 px-1 border-none flex flex-row justify-between w-full">🧰 Order Placed </option>
    <option value="2" <?php if(isset($sellingHistory["order_status"]) && intval($sellingHistory["order_status"])==2){?>selected<?php } ?> class="bg-[#252629] py-1 px-1 border-none flex flex-row justify-between w-full">👍 Order Success </option>
    <option value="3" <?php if(isset($sellingHistory["order_status"]) && intval($sellingHistory["order_status"])==3){?>selected<?php } ?> class="bg-[#252629] py-1 px-1 border-none flex flex-row justify-between w-full">🚢 Shipped </option>
    <option value="4" <?php if(isset($sellingHistory["order_status"]) && intval($sellingHistory["order_status"])==4){?>selected<?php } ?> class="bg-[#252629] py-1 px-1 border-none flex flex-col justify-center w-full">🤝 Delivered </option>

</select>
                             
                                    </td>
                                </tr>
                                <?php
                            }
                        }
                        ?>

                        <!-- More people... -->
                    </tbody>
                </table>
            </div>
    </main>
</div>
<?php require_once "./guis/partials/alert.php"; ?>

<link href="https://cdnjs.cloudflare.com/ajax/libs/select2/4.0.13/css/select2.min.css" rel="stylesheet" />
<script src="https://cdnjs.cloudflare.com/ajax/libs/select2/4.0.13/js/select2.min.js"></script>
<script src="/public/js/orderStatus.js"></script>
<?php require_once "./guis/partials/footer.php"; 
 } else { ?><script>window.location.href = "/";</script><?php } ?>