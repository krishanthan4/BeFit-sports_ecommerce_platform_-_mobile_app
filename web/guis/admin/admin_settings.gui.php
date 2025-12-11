<?php require_once "./guis/partials/header.php";
if (isset ($_SESSION["admin"])) {
    require_once "./guis/partials/admin_nav.php";

    // if (isset($_SESSION["user"])) {


    $details_rs = Database::search("SELECT * FROM `admin`");
    $user_details = $details_rs->fetch_assoc();
?>
    <div>
        <main class="max-w-screen mx-auto pb-10 lg:py-12 lg:px-8">
            <div class="lg:grid lg:grid-cols-12 lg:gap-x-5">

                <div class="space-y-6 sm:px-6 lg:px-0 lg:col-span-9">
                    <div class="max-w-7xl mx-auto sm:px-2 lg:px-8">
                        <div class="max-w-2xl  mx-auto px-4 lg:max-w-4xl lg:px-0">
                            <h1 class="text-2xl font-extrabold tracking-tight text-gray-200 sm:text-3xl">Administrator
                                Settings</h1>
                        </div>
                        <!-- Profile detais section -->
                        <section aria-labelledby="payment-details-heading">
                            <div class="shadow sm:rounded-md sm:overflow-hidden">
                                <div class=" py-6 px-4 sm:p-6">
                                    <!-- profile image start -->
                                    <div class="sm:col-span-6 py-3">

                                        <div class="mt-1 flex items-center">

                                            <?php
                                            if (empty ($user_details["admin_img"]) || $user_details["admin_img"] == "resources/new_user.png") {
                                                ?>
                                                <img src="resources/new_user.png"
                                                    class="grayscale inline-block h-32 w-32 rounded-full" id="image" />

                                                <?php

                                            } else {

                                                ?>

                                                <img draggable="false" src="<?php echo $user_details["admin_img"]; ?>"
                                                    class="inline-block h-32 w-32 rounded-full" id="image" />

                                                <?php

                                            }
                                            ?>
                                            <div class="ml-4 flex">
                                                <div onclick="changeProfileImage();" for="userImage"
                                                    class="relative border-green-600 hover:text-[#2a2c30] text-green-500/80  hover:bg-green-500/70 focus:outline-none  py-2 px-3 border  rounded-md text-sm font-medium ">
                                                    <label for="user-photo"
                                                        class="relative text-sm font-medium text-blue-gray-900 pointer-events-none">
                                                        <span>Change</span>
                                                        <span class="sr-only"> user photo</span>
                                                    </label>
                                                    <input id="userImage" type="file"
                                                        class="absolute inset-0 w-full h-full opacity-0 cursor-pointer border-gray-300 rounded-md">
                                                </div>
                                                <button type="button"
                                                    class="ml-3 border-red-500  text-red-500/80 hover:text-[#2a2c30] hover:bg-red-500/70 focus:outline-none  py-2 px-3 border  rounded-md text-sm font-medium "
                                                    onclick="removeProfileImage('<?php echo ($user_details['admin_img']); ?>');">Remove</button>

                                            </div>
                                        </div>
                                    </div>
                                    <!-- profile image end -->

                                    <div class="mt-6 grid grid-cols-4 gap-6">

                                        <div class="col-span-4 sm:col-span-2">
                                            <label class="block text-sm font-medium text-gray-400">First name</label>
                                            <input
                                                class="mt-1 block w-full border rounded-md shadow-sm py-2 px-3 focus:outline-none bg-[#252629] border-[#4a4c52] text-gray-400  sm:text-sm"
                                                value="<?php echo $user_details["fname"]; ?>" type="text" id="fname">
                                        </div>

                                        <div class="col-span-4 sm:col-span-2">
                                            <label for="last-name" class="block text-sm font-medium text-gray-400">Last
                                                name</label>
                                            <input
                                                class="mt-1 block w-full border rounded-md shadow-sm py-2 px-3 focus:outline-none bg-[#252629] border-[#4a4c52] text-gray-400  sm:text-sm"
                                                value="<?php echo $user_details["lname"]; ?>" type="text" id="lname">
                                        </div>

                                        <div class="col-span-4 sm:col-span-2">
                                            <label class="block text-sm font-medium text-gray-400">Email address</label>
                                            <input
                                                class="mt-1 block w-full border rounded-md shadow-sm py-2 px-3 focus:outline-none bg-[#252629] border-[#4a4c52] text-gray-400  sm:text-sm"
                                                type="email" id="email2" value="<?php echo $user_details["email"]; ?>"
                                                readonly>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="px-4 py-3  text-right sm:px-6">
                                <button
                                    class="bg-[#bbc0ca] border border-transparent rounded-md shadow-sm py-2 px-4 inline-flex justify-center text-sm font-medium text-[#2a2c30] hover:bg-[#89909e] focus:outline-none "
                                    onclick="updateProfile();">Save</button>
                            </div>
                    </div>
                    </section>
                </div>
            </div>
        </main>
    </div>
    <!-- alert model -->
    <?php require_once "./guis/partials/alert.php"; ?>
    <!--alert model -->
    <script src="./public/js/adminSettings.js"></script>

    </div>
    </body>

    </html>
<?php } else { ?>
    <script>window.location.href = "/adminSignin";</script>
<?php } ?>