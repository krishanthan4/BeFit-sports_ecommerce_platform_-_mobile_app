<?php
require_once "./guis/partials/header.php";
require_once "DbConnection.php";

if (isset($_SESSION["admin"])) {


  require_once "./guis/partials/admin_nav.php";
  ?>
  <link rel="shortcut icon" href="../../assets/img/favicon.ico" />
  <link rel="apple-touch-icon" sizes="76x76" href="../../assets/img/apple-icon.png" />
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.9.3/Chart.min.css" />
  <link rel="stylesheet" href="../../assets/vendor/@fortawesome/fontawesome-free/css/all.min.css" />

  <div class="text-blueGray-700 antialiased w-full">
    <?php $user_rs = Database::search("SELECT COUNT(`email`) AS count FROM `user`");
    $user = $user_rs->fetch_assoc();
    $sellers_rs = Database::search("SELECT COUNT(`product`.`user_email`) AS count
FROM `user`
INNER JOIN `product` ON `user`.`email` = `product`.`user_email`
GROUP BY `user`.`email`");
    $sellers = $sellers_rs->fetch_assoc();
    $product_rs = Database::search("SELECT COUNT(`id`) AS count FROM `product`");
    $product = $product_rs->fetch_assoc();
    $category_rs = Database::search("SELECT COUNT(`cat_id`) AS count FROM `category`");
    $category = $category_rs->fetch_assoc(); ?>
        <div class="relative  md:pt-5 pb-10 px-5 pt-5 ">
              <!-- Card stats -->
              <div class="flex flex-wrap ">
                <a href="/manageUsers" class="w-full lg:w-6/12 xl:w-3/12  px-4">
                  <div class="relative flex flex-col min-w-0 break-words bg-[#2a2b30] rounded mb-6 xl:mb-0 shadow-lg">
                    <div class="flex-auto p-4">
                      <div class="flex flex-wrap">
                        <div class="relative w-full pr-4 max-w-full flex-grow flex-1">
                          <h5 class="text-gray-400 uppercase font-bold text-xs">
                            Users
                          </h5>
                          <span class="font-semibold text-xl text-blueGray-700">
                            <?= $user["count"] ?> users
                          </span>
                        </div>
                        <div class="relative w-auto pl-4 flex-initial">
                          <div
                            class="text-white p-3 text-center inline-flex items-center justify-center w-12 h-12 shadow-lg rounded-full bg-gray-500">
                            <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" class="w-6 h-6">
  <path stroke-linecap="round" stroke-linejoin="round" d="M18 18.72a9.094 9.094 0 0 0 3.741-.479 3 3 0 0 0-4.682-2.72m.94 3.198.001.031c0 .225-.012.447-.037.666A11.944 11.944 0 0 1 12 21c-2.17 0-4.207-.576-5.963-1.584A6.062 6.062 0 0 1 6 18.719m12 0a5.971 5.971 0 0 0-.941-3.197m0 0A5.995 5.995 0 0 0 12 12.75a5.995 5.995 0 0 0-5.058 2.772m0 0a3 3 0 0 0-4.681 2.72 8.986 8.986 0 0 0 3.74.477m.94-3.197a5.971 5.971 0 0 0-.94 3.197M15 6.75a3 3 0 1 1-6 0 3 3 0 0 1 6 0Zm6 3a2.25 2.25 0 1 1-4.5 0 2.25 2.25 0 0 1 4.5 0Zm-13.5 0a2.25 2.25 0 1 1-4.5 0 2.25 2.25 0 0 1 4.5 0Z" />
</svg>

                          </div>
                        </div>
                      </div>
                    </div>
                  </div>
</a>
                <a href="/manageSellers" class="w-full lg:w-6/12 xl:w-3/12  px-4">
                  <div class="relative flex flex-col min-w-0 break-words bg-[#2a2b30] rounded mb-6 xl:mb-0 shadow-lg">
                    <div class="flex-auto p-4">
                      <div class="flex flex-wrap">
                        <div class="relative w-full pr-4 max-w-full flex-grow flex-1">
                          <h5 class="text-gray-400 uppercase font-bold text-xs">
                            Sellers
                          </h5>
                          <span class="font-semibold text-xl text-blueGray-700">
                            <?= $sellers["count"] ?> sellers
                          </span>
                        </div>
                        <div class="relative w-auto pl-4 flex-initial">
                          <div
                            class="text-white p-3 text-center inline-flex items-center justify-center w-12 h-12 shadow-lg rounded-full bg-gray-500">
                            <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" class="w-6 h-6">
  <path stroke-linecap="round" stroke-linejoin="round" d="M15 19.128a9.38 9.38 0 0 0 2.625.372 9.337 9.337 0 0 0 4.121-.952 4.125 4.125 0 0 0-7.533-2.493M15 19.128v-.003c0-1.113-.285-2.16-.786-3.07M15 19.128v.106A12.318 12.318 0 0 1 8.624 21c-2.331 0-4.512-.645-6.374-1.766l-.001-.109a6.375 6.375 0 0 1 11.964-3.07M12 6.375a3.375 3.375 0 1 1-6.75 0 3.375 3.375 0 0 1 6.75 0Zm8.25 2.25a2.625 2.625 0 1 1-5.25 0 2.625 2.625 0 0 1 5.25 0Z" />
</svg>

                          </div>
                        </div>
                      </div>

                    </div>
                  </div>
</a>
                <a href="/manageProduct" class="w-full lg:w-6/12 xl:w-3/12  px-4">
                  <div class="relative flex flex-col min-w-0 break-words bg-[#2a2b30] rounded mb-6 xl:mb-0 shadow-lg">
                    <div class="flex-auto p-4">
                      <div class="flex flex-wrap">
                        <div class="relative w-full pr-4 max-w-full flex-grow flex-1">
                          <h5 class="text-gray-400 uppercase font-bold text-xs">
                            Products
                          </h5>
                          <span class="font-semibold text-xl text-blueGray-700">
                            <?= $product["count"] ?> products
                          </span>
                        </div>
                        <div class="relative w-auto pl-4 flex-initial">
                          <div
                            class="text-white p-3 text-center inline-flex items-center justify-center w-12 h-12 shadow-lg rounded-full bg-gray-500">
                            <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" class="w-6 h-6">
  <path stroke-linecap="round" stroke-linejoin="round" d="m7.875 14.25 1.214 1.942a2.25 2.25 0 0 0 1.908 1.058h2.006c.776 0 1.497-.4 1.908-1.058l1.214-1.942M2.41 9h4.636a2.25 2.25 0 0 1 1.872 1.002l.164.246a2.25 2.25 0 0 0 1.872 1.002h2.092a2.25 2.25 0 0 0 1.872-1.002l.164-.246A2.25 2.25 0 0 1 16.954 9h4.636M2.41 9a2.25 2.25 0 0 0-.16.832V12a2.25 2.25 0 0 0 2.25 2.25h15A2.25 2.25 0 0 0 21.75 12V9.832c0-.287-.055-.57-.16-.832M2.41 9a2.25 2.25 0 0 1 .382-.632l3.285-3.832a2.25 2.25 0 0 1 1.708-.786h8.43c.657 0 1.281.287 1.709.786l3.284 3.832c.163.19.291.404.382.632M4.5 20.25h15A2.25 2.25 0 0 0 21.75 18v-2.625c0-.621-.504-1.125-1.125-1.125H3.375c-.621 0-1.125.504-1.125 1.125V18a2.25 2.25 0 0 0 2.25 2.25Z" />
</svg>

                          </div>
                        </div>
                      </div>

                    </div>
                  </div>
</a>
                <a href="/manageCategory" class="w-full lg:w-6/12 xl:w-3/12  px-4">
                  <div class="relative flex flex-col min-w-0 break-words bg-[#2a2b30] rounded mb-6 xl:mb-0 shadow-lg">
                    <div class="flex-auto p-4">
                      <div class="flex flex-wrap">
                        <div class="relative w-full pr-4 max-w-full flex-grow flex-1">
                          <h5 class="text-gray-400 uppercase font-bold text-xs">
                            Categories
                          </h5>
                          <span class="font-semibold text-xl text-blueGray-700">
                            <?= $category["count"] ?> categories
                          </span>
                        </div>
                        <div class="relative w-auto pl-4 flex-initial">
                          <div
                            class="text-white p-3 text-center inline-flex items-center justify-center w-12 h-12 shadow-lg rounded-full bg-gray-500">
                            <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" class="w-6 h-6">
  <path stroke-linecap="round" stroke-linejoin="round" d="M14.25 6.087c0-.355.186-.676.401-.959.221-.29.349-.634.349-1.003 0-1.036-1.007-1.875-2.25-1.875s-2.25.84-2.25 1.875c0 .369.128.713.349 1.003.215.283.401.604.401.959v0a.64.64 0 0 1-.657.643 48.39 48.39 0 0 1-4.163-.3c.186 1.613.293 3.25.315 4.907a.656.656 0 0 1-.658.663v0c-.355 0-.676-.186-.959-.401a1.647 1.647 0 0 0-1.003-.349c-1.036 0-1.875 1.007-1.875 2.25s.84 2.25 1.875 2.25c.369 0 .713-.128 1.003-.349.283-.215.604-.401.959-.401v0c.31 0 .555.26.532.57a48.039 48.039 0 0 1-.642 5.056c1.518.19 3.058.309 4.616.354a.64.64 0 0 0 .657-.643v0c0-.355-.186-.676-.401-.959a1.647 1.647 0 0 1-.349-1.003c0-1.035 1.008-1.875 2.25-1.875 1.243 0 2.25.84 2.25 1.875 0 .369-.128.713-.349 1.003-.215.283-.4.604-.4.959v0c0 .333.277.599.61.58a48.1 48.1 0 0 0 5.427-.63 48.05 48.05 0 0 0 .582-4.717.532.532 0 0 0-.533-.57v0c-.355 0-.676.186-.959.401-.29.221-.634.349-1.003.349-1.035 0-1.875-1.007-1.875-2.25s.84-2.25 1.875-2.25c.37 0 .713.128 1.003.349.283.215.604.401.96.401v0a.656.656 0 0 0 .658-.663 48.422 48.422 0 0 0-.37-5.36c-1.886.342-3.81.574-5.766.689a.578.578 0 0 1-.61-.58v0Z" />
</svg>
                          </div>
                        </div>
                      </div>

                    </div>
                  </div>
</a>
              </div>
        </div>

        <div class="flex  flex-col xl:flex-row justify-center mx-4 items-center gap-3">
     <div class="grid grid-cols-2 me-10 gap-3">
           <div class="bg-[#2a2b30]  p-4  min-w-52 rounded-md shadow-lg shadbg-[#2a2b30] ">
            <div class="flex flex-col items-center">
              <?php
              $bestSelling_product_rs = Database::search("SELECT p.*,`user`.*, (
  SELECT `path`
  FROM profile_img
  WHERE `user`.`email` = `profile_img`.`user_email`
  LIMIT 1
) AS profile_path, (
SELECT img_path
FROM product_img
WHERE product_id = p.id
LIMIT 1
) AS img_path, ihp.product_id, COUNT(*) AS product_count
FROM invoice_has_products AS ihp
INNER JOIN product AS p ON p.id = ihp.product_id
INNER JOIN user ON `p`.`user_email` = `user`.`email`
GROUP BY ihp.product_id, p.id
ORDER BY product_count DESC
LIMIT 1");
              $bestSelling_product = $bestSelling_product_rs->fetch_assoc();
              ?>
              <div class="w-28 h-28 mb-3 rounded-full  overflow-hidden">
                <img class="w-full h-full object-contain  shadow-lg" src="/<?= $bestSelling_product["img_path"] ?>"
                  alt="<?= $bestSelling_product["title"] ?>" />
              </div>
              <h5 class="mb-1 text-sm font-medium text-gray-300 ">Best Selling Product</h5>
              <span class="text-base text-gray-400 ">
                <?= $bestSelling_product["title"] ?>
              </span>

            </div>
            
          </div>

          <div class="bg-[#2a2b30]  min-w-52 ms-14 p-4  rounded-md shadow-lg shadow-[#151518]">
            <div class="flex flex-col items-center">

              <div class="w-28 h-28 mb-3 rounded-full overflow-hidden">
             <?php if(isset($bestSelling_product["profile_path"]) && $bestSelling_product["profile_path"]!=="resources/new_user.png"){ ?>
                 <img class="w-full h-full  shadow-lg " src="/<?= $bestSelling_product["profile_path"] ?>"
                  alt="<?= $bestSelling_product["fname"] ?>" />
              <?php }else{
                ?>
<img class="w-full h-full grayscale shadow-lg " draggable="false" src="/resources/new_user.png"
                 alt="<?= $bestSelling_product["fname"] ?>" />
                
             <?php 
              } ?>
              </div>
              <h5 class="mb-1 text-sm font-medium text-gray-300 ">Best Seller</h5>
              <span class="text-base text-gray-400 ">
                <?= $bestSelling_product["fname"] . " " . $bestSelling_product["lname"] ?>
              </span>
            </div>
          </div>
               <div class=" bg-[#2a2b30]  px-4 py-6 min-w-52  rounded-md shadow-lg shadow-[#151518]">
            <div class="flex flex-col items-center">
              <?php
              $currentYear = date('Y');
              $monthlyIncome_rs = Database::search("SELECT YEAR(date) AS year, MONTH(date) AS month, SUM(total) AS total_sales FROM invoice WHERE date BETWEEN '" . date('Y-m-01') . "' AND '" . date('Y-m-t') . "' GROUP BY YEAR(date), MONTH(date) ORDER BY YEAR(date), MONTH(date)");
              $monthlyIncome = $monthlyIncome_rs->fetch_assoc(); ?>
              <h5 class="mb-1 text-sm font-medium text-gray-300 ">Monthly Income</h5>
              <span class="text-base text-gray-400 ">Rs.
                <?= intval($monthlyIncome["total_sales"]) ?>.00
              </span>
            </div>
          </div>

          <!-- Selled Products -->
          <div class="bg-[#2a2b30]  min-w-52 ms-14  px-4 py-6  rounded-md shadow-lg shadow-[#151518]">
            <div class="flex flex-col items-center">
              <?php $totalProducts_rs = Database::search("SELECT SUM(bought_qty) AS total FROM `invoice_has_products`");
              $totalProducts = $totalProducts_rs->fetch_assoc();
              ?>
              <h5 class="mb-1 text-sm font-medium text-gray-300 ">Total Selled Products</h5>
              <span class="text-base text-gray-400 ">
                <?=   $totalProducts["total"] ?> products
              </span>
            </div>
          </div>
     </div>

     <?php
            $currentDate = new DateTime();
            $startDate = (clone $currentDate)->modify('-11 months');
            $totalSellings_rs = Database::search("
    SELECT 
        YEAR(date) AS year,
        MONTH(date) AS month,
        SUM(total) AS total_sales
    FROM 
        invoice
    WHERE 
        date BETWEEN '" . $startDate->format('Y-m-01') . "' AND '" . $currentDate->format('Y-m-t') . "'
    GROUP BY 
        YEAR(date), MONTH(date)
    ORDER BY 
        YEAR(date), MONTH(date)
");

            // Initialize the array to store total sales
            $ArrayTotalSales = [];
            $monthlySales = [];

            for ($i = 0; $i < 12; $i++) {
                $month = (clone $startDate)->modify("+{$i} months");
                $monthlySales[$month->format('Y-m')] = 0;
            }

            if ($totalSellings_rs->num_rows !== 0) {
                while ($totalSellings = $totalSellings_rs->fetch_assoc()) {
                    $yearMonth = $totalSellings['year'] . '-' . str_pad($totalSellings['month'], 2, '0', STR_PAD_LEFT);
                    $monthlySales[$yearMonth] = (int)$totalSellings['total_sales'];
                }
            }

            $ArrayTotalSales = array_values($monthlySales);
            $jsonTotal = json_encode($ArrayTotalSales);
            $labels = [];
            for ($i = 0; $i < 12; $i++) {
                $month = (clone $startDate)->modify("+{$i} months");
                $labels[] = $month->format('F Y');
            }
            $jsonLabels = json_encode($labels);
            ?>

          <div class="w-full  md:w-[90%]  p-4  rounded-md lg:ms-0 xl:ms-24  bg-[#2a2b30] shadow-lg shadow-[#151518]">
            <h6 class="uppercase text-blueGray-400 mb-1 text-xs font-semibold">
              Total Income -
              <?= date("Y") ?>
            </h6>

            <div class=" flex-auto">
              <canvas id="line-chart"></canvas>
            </div>
          </div>
        </div>
<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
<script>
            const ctx = document.getElementById('line-chart');
            new Chart(ctx, {
                type: 'line',
                data: {
                    labels: <?= $jsonLabels ?>,
                    datasets: [{
                        label: 'Income (Rs.)',
                        data: <?= $jsonTotal ?>,
                        borderWidth: 2,
                        backgroundColor: ['#434f91'],
                        fill: false,
                        borderColor: '#434f91',
                        tension: 0.1
                    }]
                },
                options: {
                    scales: {
                        y: {
                            beginAtZero: true
                        }
                    }
                }
            });
        </script>

<!-- user Registration -->
<!-- users Chart -->
<div class="grid xl:grid-cols-2 xl:w-[90%] md:w-[90%]  lg:grid-cols-1 my-6 justify-center items-center  xl:mx-4 lg:mx-auto gap-2">
        <div class=" w-full p-4 rounded-md  bg-[#2a2b30] shadow-lg shadow-[#18181b]">
            <h6 class="uppercase text-blueGray-400 mb-1 text-xs font-semibold">
              User Registrations 
              (<?= date("Y") ?>)
            </h6>

            <div class="flex-auto">
              <canvas id="bar-chart"></canvas>
            </div>
          </div>
          <script>
                <?php
                $userRegister_rs = Database::search("SELECT YEAR(joined_date) AS year, MONTH(joined_date) AS month, COUNT(*) AS user_count
FROM user
WHERE YEAR(joined_date)='" . date('Y') . "'
GROUP BY YEAR(joined_date), MONTH(joined_date)
ORDER BY year, month;
");

                if ($userRegister_rs->num_rows !== 0) {

                    $userRegisterArray = [];
                    for ($i = 0; $i < $userRegister_rs->num_rows; $i++) {
                        $userRegister = $userRegister_rs->fetch_assoc();
                        $userRegisterArray[$i] = $userRegister["user_count"];

                    }
                    $userRegisterJson = json_encode($userRegisterArray);
                }
                ?>

                // user chart
                const ctx2 = document.getElementById('bar-chart');
                new Chart(ctx2, {
                    type: 'line',
                    data: {
                        labels: ['January', 'February', 'March', 'April', 'May', 'June', 'july', 'Augest', 'October', 'November', 'December'],
                        datasets: [{
                            label: 'Users',
                            data: <?= $userRegisterJson ?>,
                            borderWidth: 1,
                            borderColor: ["#004ea1"],
                            backgroundColor: ['#3d7cbf'],
                            fill: false,
                            tension: 0.1
                        }]
                    },
                    options: {
                        scales: {
                            y: {
                                beginAtZero: true
                            }
                        }
                    }
                }); </script>

<div class="w-full p-4 rounded-md xl:ms-10 ms-0 bg-[#2a2b30] shadow-lg shadow-[#18181b]">
            <h6 class="uppercase text-blueGray-400 mb-1 text-xs font-semibold">
              Most Selled Category
            </h6>

            <div class=" flex-auto">
              <canvas id="pie-chart"></canvas>
            </div>
          </div>
        </div>
<script>
// category chart
<?php 
$famousCategory_rs = Database::search("SELECT c.cat_name, COUNT(*) AS total_sales
FROM invoice_has_products AS ihp
INNER JOIN product AS p ON ihp.product_id = p.id
INNER JOIN category AS c ON p.category_cat_id = c.cat_id
GROUP BY c.cat_name ORDER BY total_sales DESC");

$ArrayfamousCategoryCount =[];
$ArrayfamousCategoryName =[];
if($famousCategory_rs->num_rows!==0){
for ($i=0; $i < $famousCategory_rs->num_rows; $i++) { 
$famousCategory = $famousCategory_rs->fetch_assoc();
  $ArrayfamousCategoryCount[$i]=$famousCategory["total_sales"];
  $ArrayfamousCategoryName[$i]=$famousCategory["cat_name"];
}
$jsonCategoryCount = json_encode($ArrayfamousCategoryCount);
$jsonCategoryName = json_encode($ArrayfamousCategoryName);
}?>

const ctx3 = document.getElementById('pie-chart');
new Chart(ctx3, {
  type: 'bar',
  data: {
    labels: <?= $jsonCategoryName?>,
    datasets: [{
      label: 'Category',
      data:<?= $jsonCategoryCount ?>,
      backgroundColor: ['#434f91','#9342f5','#42f56f','#f54263','#f56f42','#f5f242'],
      fill: false,
      borderWidth:0,
      tension: 0.1
    }]
  },
  options: {
    scales: {
      y: {
        beginAtZero: true
      }
    }
  }
});
</script>

    <script src="https://unpkg.com/@popperjs/core@2/dist/umd/popper.js"></script>
    <script src="./public/js/adminDashboard.js" type="text/javascript"></script>
  </div>
  </div>
  </body>
  </html>
<?php } else { ?> <script>window.location.href = "/adminSignin";</script> <?php } ?>
