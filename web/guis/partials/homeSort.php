<div class="w-[28%] flex items-center mx-2 h-fit py-10 text-gray-300 bg-[#26282bf8] border border-[#1d1e20] shadow-md rounded-md">
<ul class=" ms-4 text-gray-600 w-full">

<?php 
$categorySort_rs = Database::search("SELECT DISTINCT category_cat_id,category.* FROM `product` INNER JOIN `category` ON `category`.`cat_id`=`product`.`category_cat_id` ");
if($categorySort_rs->num_rows){
for ($i=0; $i < $categorySort_rs->num_rows; $i++) { 
  $categorySort = $categorySort_rs->fetch_assoc();
  ?>
      <li onmouseleave="onHoverHideMain(<?= $categorySort['cat_id']?>);" onmouseover="onHoverShowMain(<?= $categorySort['cat_id']?>);" id="<?= $categorySort['cat_id']?>Li" class="hover:text-orange-600 cursor-pointer py-2 text-gray-400 flex items-center"><img src="/<?= $categorySort['cat_icon']?>" class="w-4 h-4 text-gray-300 me-1 font-bold" alt=""><?= $categorySort['cat_name']?><svg id="<?= $categorySort['cat_id']?>LiSVG" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" class="w-5 h-5 ms-auto opacity-0">
  <path stroke-linecap="round" stroke-linejoin="round" d="m8.25 4.5 7.5 7.5-7.5 7.5" />
</svg>
</li>
  <?php }}?>
</ul>

<!-- sub div -->
<ul onmouseover="onHoverShowSubUL();" onmouseleave="onHoverHideSubUL();" id="firstSubUL" class="mx-2 ms-[28%]  h-fit min-h-[15rem] xl:min-w-[10%] lg:min-w-[20%] min-w-[25%] absolute z-40 hidden bg-[#26282bee] text-gray-400 border border-[#1d1e20] shadow-md rounded-md">
</ul>
<ul onmouseover="onHoverShowSubUL2();" onmouseleave="onHoverHideSubUL2();" id="secondSubUL" class="mx-2 xl:ms-[38%] lg:ms-[48%] ms-[53%] absolute h-fit min-h-[15rem] min-w-[20%] max-w-[70%]  z-40 hidden bg-[#26282bee] text-gray-400 border border-[#1d1e20] shadow-md rounded-md">
</ul>
<ul onmouseover="onHoverShowSubUL3();" onmouseleave="onHoverHideSubUL3();" id="thirdSubUL" class="mx-2 xl:ms-[58%] lg:ms-[68%] ms-[73%]  absolute h-fit min-h-[15rem]  xl:min-w-[10%] lg:min-w-[20%] min-w-[25%]  z-40 hidden bg-[#26282bee] text-gray-400 border border-[#1d1e20] shadow-md rounded-md">
</ul>
</div>


<script src="/public/js/homeSort.js"></script>
<script src="/public/js/homeSort2.js"></script>