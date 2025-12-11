<?php require_once "./guis/partials/carousel.php"; ?>
<?php require_once "./guis/partials/category.php";

?>
<!-- Product grid -->
<section aria-="products-heading" class="max-w-7xl mx-auto overflow-hidden sm:px-6 lg:px-8">
  <p class="text-xl p-3">Just For You</p>
  <div class="-mx-px  gap-1 grid grid-cols-2 sm:mx-0 md:grid-cols-3 lg:grid-cols-6">
    <?php require_once "./guis/partials/home_product_card.php" ?>
  </div>
</section>

<script src="./public/js/singleProductView.js"></script>
<?php require_once "./guis/partials/pagination.php";
$currentPage = isset($_GET['id']) ? intval($_GET['id']) : 1;
$currentPage = max(1, min($currentPage, $totalPages));
$parsedJs = "homePagination"; 
pagination($currentPage, $totalPages, $baseurl,$itemsPerPage, $parsedJs);

?>