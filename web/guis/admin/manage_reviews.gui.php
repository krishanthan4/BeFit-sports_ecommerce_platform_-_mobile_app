<?php require_once "./guis/partials/header.php";
require_once "./guis/partials/admin_nav.php";
if (isset($_SESSION["admin"])) {
$reviews_rs = Database::search("SELECT * FROM `feedback` INNER JOIN `user` ON `user`.`email`=`feedback`.`user_email` INNER JOIN `product` ON `product`.`id`=`feedback`.`product_id` ORDER BY `feed_id` DESC");
?>

<!-- model -->
<div class="fixed z-10 inset-0 overflow-y-auto hidden" id="deleteReviewModel">
    <div class="flex items-center justify-center  min-h-screen pt-4 px-4 pb-20 text-center sm:block sm:p-0">
        <div class="fixed inset-0 transition-opacity" aria-hidden="true">
            <div class="absolute inset-0 bg-gray-500 opacity-75"></div>
        </div>
        <div class="inline-block  align-bottom bg-white rounded-lg text-left overflow-hidden shadow-xl transform transition-all sm:my-8 sm:align-middle sm:max-w-lg sm:w-full"
            role="dialog" aria-modal="true" aria-labelledby="modal-headline">
            <div class="absolute top-0 right-0 mt-4 mr-4">
                <button type="button" class="text-gray-400 hover:text-gray-500 focus:outline-none" aria-label="Close"
                    onclick="closeModal()">
                    <svg class="h-6 w-6" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                        <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M6 18L18 6M6 6l12 12">
                        </path>
                    </svg>
                </button>
            </div>
            <div class="bg-[#303135] px-4 pt-5 pb-4 sm:p-6 sm:pb-4 w-full">
                <div class="sm:flex sm:items-start  w-full">
                    <div
                        class="mx-auto  flex-shrink-0 flex items-center justify-center h-12 w-12 rounded-full bg-red-900/30 sm:mx-0 sm:h-10 sm:w-10">
                        <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5"
                            stroke="currentColor" class="w-6 h-6 text-red-500">
                            <path stroke-linecap="round" stroke-linejoin="round"
                                d="m14.74 9-.346 9m-4.788 0L9.26 9m9.968-3.21c.342.052.682.107 1.022.166m-1.022-.165L18.16 19.673a2.25 2.25 0 0 1-2.244 2.077H8.084a2.25 2.25 0 0 1-2.244-2.077L4.772 5.79m14.456 0a48.108 48.108 0 0 0-3.478-.397m-12 .562c.34-.059.68-.114 1.022-.165m0 0a48.11 48.11 0 0 1 3.478-.397m7.5 0v-.916c0-1.18-.91-2.164-2.09-2.201a51.964 51.964 0 0 0-3.32 0c-1.18.037-2.09 1.022-2.09 2.201v.916m7.5 0a48.667 48.667 0 0 0-7.5 0" />
                        </svg>

                    </div>
                    <div class="mt-3 text-center sm:mt-0 sm:ml-4 sm:text-left  w-full">
                        <h3 class="text-lg leading-6 font-medium text-white" id="modal-headline">Confirm Deletion
                        </h3>
                        <div class="mt-2 max-w-lg">
                            <label id="deletingReviewText" class="block text-sm font-medium text-gray-500"></label>
                            <p class="hidden" id="hiddenP"></p>
                        </div>

                    </div>
                </div>
            </div>
            <div class="bg-[#303135] px-4 py-3 sm:px-6 sm:flex sm:flex-row-reverse">
                <button type="button" onclick="deleteConform();"
                    class="w-full inline-flex justify-center rounded-md border border-transparent shadow-sm px-4 py-2 bg-red-600 text-base font-medium text-white hover:bg-red-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-red-500 sm:ml-3 sm:w-auto sm:text-sm">Conform</button>
                <button type="button"
                    class="mt-3 w-full inline-flex justify-center rounded-md shadow-sm px-4 py-2 bg-gray-700 text-base font-medium text-white focus:outline-none focus:ring-2 focus:ring-offset-2  sm:mt-0 sm:ml-3 sm:w-auto sm:text-sm"
                    onclick="closeModal()">Close</button>
            </div>
        </div>
    </div>
</div>
       
<!-- component -->
<div id="mainDiv" class=" px-5 flex flex-col gap-2 pt-2 lg:w-[70vw] w-[90vw] bg-[#252629]/80 rounded-md mx-10 lg:mt-5 mt-0 shadow-md ">
    <h1 class="py-1 text-xl">Reviews</h1>
    <div class="flex items-center bg-gray-600 bg-opacity-20 border border-[#45474d] rounded-md">
        <ion-icon class="py-1 ps-2" name="search-outline"></ion-icon>
        <input id="searchInput" type="text" placeholder="Search By review,User,date" class="bg-transparent border-transparent focus:border-transparent focus:ring-0  w-full rounded-md search-input">
<style>
  .search-input:focus {
    outline: none;
  }
</style>

        <p onclick="setDefault();" id="cancelX" class="pe-2 hidden text-gray-500 cursor-pointer hover:text-red-800 font-bold">x</p>
    </div>
    <!-- Tags -->
    <div class="flex flex-wrap gap-2  items-center  pb-2 pt-1">
        <p class=" text-gray-500 text-sm me-5">Sort By : </p>
        <div>
    <label for="stars" class="flex items-center cursor-pointer">
        <input name="radioButton" id="stars" type="radio" class="hidden peer">
        <span id="stars" class="px-2 py-1 hover:bg-gray-400 bg-[#333338] rounded-md  peer-checked:bg-[#4a4b52] " onclick="filterBy('stars')">Stars</span>
    </label>
</div>
<div>
    <label for="date" class="flex items-center cursor-pointer">
        <input name="radioButton" id="date" type="radio" class="hidden peer">
        <span id="date" class="px-2 py-1  hover:bg-gray-400 bg-[#333338] rounded-md  peer-checked:bg-[#4a4b52]  " onclick="filterBy('date')">Date</span>
    </label>
</div>
<div>
    <label for="title" class="flex items-center cursor-pointer">
        <input name="radioButton" id="title" type="radio" class="hidden peer">
        <span id="title" class="px-2 py-1 hover:bg-gray-400 bg-[#333338] rounded-md  peer-checked:bg-[#4a4b52]  " onclick="filterBy('title')">Product Name</span>
    </label>
</div>
<div>
    <label for="feed" class="flex items-center cursor-pointer">
        <input name="radioButton" id="feed" type="radio" class="hidden peer">
        <span id="feed"  class="px-2 py-1 hover:bg-gray-400 bg-[#333338] rounded-md  peer-checked:bg-[#4a4b52]  " onclick="filterBy('feed')">Review (A-Z)</span>
    </label>
</div>
<div>
    <label for="fname" class="flex items-center cursor-pointer">
        <input name="radioButton" id="fname" type="radio" class="hidden peer">
        <span id="fname" class="px-2 py-1 hover:bg-gray-400 bg-[#333338] rounded-md  peer-checked:bg-[#4a4b52] " onclick="filterBy('fname')">User Name</span>
    </label>
</div>

<p id="clearAll" class="ms-5 text-sm hover:underline cursor-pointer hidden" onclick="reset();">Clear All</p>

    </div>

<!-- Item Container -->
<div id="subDiv" class="flex flex-col gap-3 mt-2 overflow-y-scroll h-[29rem]">
    <?php 
    for ($t = 0; $t < $reviews_rs->num_rows; $t++) {
        $reviewsData = $reviews_rs->fetch_assoc(); 
    ?>
    <div id="<?= "tr".$reviewsData["feed_id"] ?>" class="flex flex-col gap-3 bg-[#303135] rounded-md p-4">
        <!-- Profile and Rating -->
        <input type="hidden" id="<?= $reviewsData["feed_id"] ?>" value="<?= $reviewsData["title"] ?>">
        <div class="flex justify-between items-center">
            <div class="flex gap-2 items-center">
                <div id="randomColorDiv" class="w-7 h-7 text-[#252629] flex items-center justify-center rounded-full">
                    <?php
                    if (isset($reviewsData["fname"])) {
                        echo strtoupper($reviewsData["fname"][0]);
                    } else {echo "0"; }
                    ?>
                </div>
                <span><?= isset($reviewsData["fname"]) ? $reviewsData["fname"] . " " . $reviewsData["lname"] : "User" ?></span>
            </div>
            <div class="flex gap-1 text-yellow-400">
                <?php for ($i = 1; $i <= 5; $i++) { ?>
                    <ion-icon name="star" class="<?= $i <= $reviewsData["stars"] ? '' : 'text-gray-400' ?>"></ion-icon>
                <?php } ?>
            </div>
        </div>
        <div>
            <p class="font-bold"><?= $reviewsData["title"] ?></p>
            <p class="ms-5 text-gray-600"><?= $reviewsData["feed"] ?></p>
        </div>
        <div class="flex justify-between">
            <span><?= date('Y-m-d', strtotime($reviewsData["date"])) ?></span>
            <button onclick="openModal('<?= $reviewsData['feed_id'] ?>','<?= $reviewsData['title'] ?>');" class="p-1 px-2 text-[#252629] bg-red-500/90 hover:bg-red-500/70 rounded-md">
                Delete
            </button>
        </div>
    </div>
    <?php } ?>
</div>

<script>
    function openModal(feed_id, product_name) {
    document.getElementById("deletingReviewText").innerText = "Confirm Deleting '" + product_name + "' Review";
    document.getElementById("hiddenP").innerText = feed_id;
    document.getElementById("deleteReviewModel").classList.remove("hidden");
}

function deleteConform() {
    const request = new XMLHttpRequest();
    const form = new FormData();
  const feed_id = document.getElementById("hiddenP").innerText;
    try {
        const response = await api.delete(`/admin/reviews/${feed_id}`);
        if (response.success) {
            document.getElementById("deleteReviewModel").classList.add("hidden");
            document.getElementById("tr" + feed_id).classList.add("hidden");
        }
    } catch (error) {
        console.error("Error deleting review:", error);
    }
}

</script>
</div>
</div>
<script src="/public/js/manageReviews.js"></script>
<script src="https://unpkg.com/ionicons@5.0.0/dist/ionicons.js"></script>
<script>
    const colors = [
        'bg-red-500',
        'bg-blue-500',
        'bg-green-500',
        'bg-yellow-500',
        'bg-indigo-500',
        'bg-purple-500',
        'bg-pink-500',
        'bg-gray-500',
        'bg-red-500',
        'bg-orange-500',
        'bg-yellow-500',
        'bg-green-500',
        'bg-teal-500',
        'bg-blue-500',
        'bg-indigo-500',
        'bg-purple-500',
        'bg-pink-500',
        // Add more colors as needed
    ];

    const randomColorDivs = document.querySelectorAll('#randomColorDiv');

    randomColorDivs.forEach(div => {
        const randomIndex = Math.floor(Math.random() * colors.length);
        const randomColorClass = colors[randomIndex];
        div.classList.add(randomColorClass);
    });

    function closeModal() {
        document.getElementById("deleteReviewModel").classList.add("hidden");
    }

</script>
</div>
</body>
</html>

<?php } else { ?><script>window.location.href = "/";</script><?php } ?>
