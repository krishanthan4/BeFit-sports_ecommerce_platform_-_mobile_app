<?php require_once "./guis/partials/header.php";
require_once "./guis/partials/nav.php";

if (isset($_SESSION["user"])) {

    $email = $_SESSION["user"]["email"];

    $details_rs = Database::search("SELECT * FROM `user` INNER JOIN `gender` ON 
  `user`.`gender_gender_id`=`gender`.`gender_id` WHERE `email`=?", [$email]);
    $details_rs1 = Database::search("SELECT * FROM `user` WHERE `email`=?", [$email]);

    $image_rs = Database::search("SELECT * FROM profile_img WHERE user_email=? ", [$email]);
    $address_rs = Database::search("SELECT * FROM user_has_address INNER JOIN city ON 
  user_has_address.city_city_id=city.city_id INNER JOIN district ON 
  city.district_district_id=district.district_id INNER JOIN province ON 
  district.province_province_id=province.province_id WHERE user_email=?", [$email]);


    $user_details = $details_rs->fetch_assoc();
    $user_details1 = $details_rs1->fetch_assoc();

    $image_details = $image_rs->fetch_assoc();
    $address_details = $address_rs->fetch_assoc();


    ?>
    <div>
        <main class="max-w-7xl mx-auto pb-10 lg:py-12 lg:px-8">
        <div class="grid grid-cols-12 gap-x-5">
                <?php require_once "./guis/partials/user_profile_sidebar.php" ?>
                <!-- Payment details -->
                <div class="space-y-6 sm:px-6 lg:px-0 lg:col-span-9 col-span-8 md:mt-0 mt-10">
                    <div class="max-w-7xl mx-auto sm:px-2 lg:px-8">
                        <div class="max-w-2xl mx-auto px-4 lg:max-w-4xl lg:px-0">
                            <h1 class="text-2xl font-extrabold tracking-tight text-gray-200 sm:text-3xl">Profile
                                Details</h1>
                            <p class="mt-2 text-sm text-gray-500">Update your billing information. Please note that
                                updating your
                                location could affect your tax rates..</p>
                        </div>
                        <!-- Profile detais section -->
                        <section aria-labelledby="payment-details-heading">
                            <div class="shadow sm:rounded-md sm:overflow-hidden">
                                <div class="py-6 px-4 sm:p-6">
                                    <!-- profile image start -->
                                    <div class="sm:col-span-6 py-3">

                                        <div class=" text-gray-400 border-none mt-1 flex items-center">

                                            <?php
                                            if (empty($image_details["path"]) || $image_details["path"] == "resources/new_user.png") {
                                                ?>
                                                <img src="resources/new_user.png"
                                                     class="grayscale inline-block h-32 w-32 rounded-full"
                                                     id="image"/>

                                                <?php

                                            } else {

                                                ?>

                                                <img draggable="false" src="<?php echo $image_details["path"]; ?>"
                                                     class="inline-block h-32 w-32 rounded-full" id="image"/>

                                                <?php

                                            }
                                            ?>
                                            <div class="ml-4 flex">
                                                <div onclick="changeProfileImage();" for="userImage"
                                                     class="relative border-green-600  text-green-700  hover:bg-green-200 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-red-500 py-2 px-3 border  rounded-md text-sm font-medium ">
                                                    <label for="user-photo"
                                                           class="relative text-sm font-medium text-blue-gray-900 pointer-events-none">
                                                        <span>Change</span>
                                                        <span class="sr-only"> user photo</span>
                                                    </label>
                                                    <input id="userImage" type="file"
                                                           class="absolute inset-0 w-full h-full opacity-0 cursor-pointer border-gray-300 rounded-md">
                                                </div>

                                                <?php

                                                if (isset($image_details['path'])) {
                                                    ?>
                                                    <button type="button"
                                                            class="ml-3 border-red-600  text-red-700  hover:bg-red-200 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-red-500 py-2 px-3 border  rounded-md text-sm font-medium "
                                                            onclick="removeProfileImage('<?php echo($image_details['path']); ?>');">
                                                        Remove
                                                    </button>
                                                    <?php
                                                } else {
                                                    ?>
                                                    <button type="button"
                                                            class="ml-3 border-red-600  text-red-700  hover:bg-red-200 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-red-500 py-2 px-3 border  rounded-md text-sm font-medium ">
                                                        Remove
                                                    </button>
                                                    <?php
                                                }


                                                ?>


                                            </div>
                                        </div>
                                    </div>
                                    <!-- profile image end -->

                                    <div class="mt-6 grid grid-cols-4 gap-6">

                                        <div class="col-span-4 sm:col-span-2">
                                            <label class="block text-sm font-medium text-gray-400">First name</label>

                                            <?php

                                            if (isset($user_details1["fname"])): ?>

                                                <input
                                                        class="bg-[#2e3035] text-gray-400 border-none mt-1 block w-full rounded-md shadow-sm py-2 px-3 focus:outline-none focus:ring-gray-900 focus:border-gray-900 sm:text-sm"
                                                        value="<?php echo $user_details1["fname"]; ?>" type="text"
                                                        id="fname">
                                            <?php else: ?>
                                                <input type="text"
                                                       class="bg-[#2e3035] text-gray-400 border-none mt-1 block w-full rounded-md shadow-sm py-2 px-3 focus:outline-none focus:ring-gray-900 focus:border-gray-900 sm:text-sm"
                                                       type="text" id="fname">
                                            <?php endif; ?>


                                        </div>

                                        <div class="col-span-4 sm:col-span-2">
                                            <label for="last-name" class="block text-sm font-medium text-gray-400">Last
                                                name</label>

                                            <?php if (isset($user_details1["lname"])): ?>

                                                <input
                                                        class="bg-[#2e3035] text-gray-400 border-none mt-1 block w-full rounded-md shadow-sm py-2 px-3 focus:outline-none focus:ring-gray-900 focus:border-gray-900 sm:text-sm"
                                                        value="<?php echo $user_details1["lname"]; ?>" type="text"
                                                        id="lname">
                                            <?php else: ?>
                                                <input type="text"
                                                       class="bg-[#2e3035] text-gray-400 border-none mt-1 block w-full rounded-md shadow-sm py-2 px-3 focus:outline-none focus:ring-gray-900 focus:border-gray-900 sm:text-sm"
                                                       type="text" id="lname">
                                            <?php endif; ?>
                                        </div>

                                        <div class="col-span-4 sm:col-span-2">
                                            <label for="Mobile"
                                                   class="block text-sm font-medium text-gray-400">Mobile</label>
                                            <?php if (isset($user_details1["mobile"])): ?>

                                                <input
                                                        class="bg-[#2e3035] text-gray-400 border-none mt-1 block w-full rounded-md shadow-sm py-2 px-3 focus:outline-none focus:ring-gray-900 focus:border-gray-900 sm:text-sm"
                                                        value="<?php echo $user_details1["mobile"]; ?>" type="number"
                                                        id="mobile">
                                            <?php else: ?>
                                                <input type="number"
                                                       class="bg-[#2e3035] text-gray-400 border-none mt-1 block w-full rounded-md shadow-sm py-2 px-3 focus:outline-none focus:ring-gray-900 focus:border-gray-900 sm:text-sm"
                                                       type="text" id="mobile">
                                            <?php endif; ?>
                                        </div>

                                        <div class="col-span-4 sm:col-span-2">
                                            <label class="block text-sm font-medium text-gray-400">Email address</label>
                                            <?php if (isset($user_details1["email"])): ?>

                                                <input
                                                        class="bg-[#2e3035] text-gray-400 border-none mt-1 block w-full rounded-md shadow-sm py-2 px-3 focus:outline-none focus:ring-gray-900 focus:border-gray-900 sm:text-sm"
                                                        value="<?php echo $user_details1["email"]; ?>" type="email"
                                                        id="email2" readonly>
                                            <?php else: ?>
                                                <input type="number"
                                                       class="bg-[#2e3035] text-gray-400 border-none mt-1 block w-full rounded-md shadow-sm py-2 px-3 focus:outline-none focus:ring-gray-900 focus:border-gray-900 sm:text-sm"
                                                       type="email" id="email2" readonly>
                                            <?php endif; ?>
                                        </div>

                                        <?php
                                        $gender_rs = Database::search("SELECT * FROM gender");


                                        ?>
                                        <div class="col-span-4 sm:col-span-2">
                                            <label class="block text-sm font-medium text-gray-400">Gender</label>
                                            <select id="gender"
                                                    class="bg-[#2e3035] text-gray-400 border-none mt-1 block w-full  rounded-md shadow-sm py-2 px-3 focus:outline-none focus:ring-gray-900 focus:border-gray-900 sm:text-sm">
                                                <option>Select</option>
                                                <?php
                                                for ($x = 0; $x < $gender_rs->num_rows; $x++) {
                                                    $gender_data = $gender_rs->fetch_assoc();
                                                    ?>
                                                    <option value="<?php echo $gender_data["gender_id"]; ?>" <?php
                                                    if (!empty($user_details["gender_id"])) {
                                                    if ($gender_data["gender_id"] == $user_details["gender_id"]) {
                                                    ?>selected<?php
                                                    }
                                                    }
                                                    ?>>
                                                        <?php echo $gender_data["gender_name"]; ?>
                                                    </option>
                                                    <?php
                                                }
                                                ?>


                                            </select>
                                        </div>

                                        <div class="col-span-4 sm:col-span-2">
                                            <label class="block text-sm font-medium text-gray-400">Password</label>
                                            <div class="" x-data="{ show: true }">

                                                <div class="relative">
                                                    <input readonly type="password"
                                                           class="bg-[#2e3035] text-gray-400 border-none mt-1 block w-full rounded-md shadow-sm py-2 px-3 focus:outline-none focus:ring-gray-900 focus:border-gray-900 sm:text-sm"
                                                           value="<?php echo $user_details1["password"]; ?>">
                                                  
                                                </div>

                                            </div>
                                        </div>

                                        <div class="col-span-4 sm:col-span-2">
                                            <label class="block text-sm font-medium text-gray-400">Registered
                                                Date</label>
                                            <input type="text"
                                                   class="bg-[#2e3035] text-gray-400 border-none mt-1 block w-full rounded-md shadow-sm py-2 px-3 focus:outline-none focus:ring-gray-900 focus:border-gray-900 sm:text-sm"
                                                   readonly
                                                   value="<?php echo date('Y-m-d', strtotime($user_details1["joined_date"])); ?>">

                                        </div>

                                    </div>
                                </div>
                                <div class="px-4 py-3 text-right sm:px-6">

                                </div>
                            </div>

                        </section>
                        <!-- payment detais section -->
                        <section class="" aria-labelledby="payment-details-heading">

                            <div class="shadow sm:rounded-md sm:overflow-hidden">
                                <div class=" py-6 px-4 sm:p-6">
                                    <div>
                                        <h2 id="payment-details-heading"
                                            class="text-lg leading-6 font-medium text-gray-300">Address</h2>

                                    </div>

                                    <div class="mt-6 grid grid-cols-4 gap-6">
                                        <div class="col-span-4 sm:col-span-2">
                                            <label for="first-name" class="block text-sm font-medium text-gray-400">Addresss
                                                Line 1</label>
                                            <?php
                                            if (empty($address_details["line1"])) {
                                                ?>
                                                <input type="text"
                                                       class="bg-[#2e3035] text-gray-400 border-none mt-1 block w-full rounded-md shadow-sm py-2 px-3 focus:outline-none focus:ring-gray-900 focus:border-gray-900 sm:text-sm"
                                                       id="line1">
                                                <?php } else { ?>
                                                <input type="text"
                                                       class="bg-[#2e3035] text-gray-400 border-none mt-1 block w-full rounded-md shadow-sm py-2 px-3 focus:outline-none focus:ring-gray-900 focus:border-gray-900 sm:text-sm"
                                                       id="line1" value="<?php echo $address_details["line1"]; ?>">
                                                <?php } ?>

                                        </div>

                                        <div class="col-span-4 sm:col-span-2">
                                            <label for="last-name" class="block text-sm font-medium text-gray-400">Address
                                                Line 2</label>
                                            <?php
                                            if (empty($address_details["line2"])) {
                                                ?>
                                                <input type="text"
                                                       class="bg-[#2e3035] text-gray-400 border-none mt-1 block w-full rounded-md shadow-sm py-2 px-3 focus:outline-none focus:ring-gray-900 focus:border-gray-900 sm:text-sm"
                                                       id="line2">
                                                <?php

                                            } else {
                                                ?>
                                                <input type="text"
                                                       class="bg-[#2e3035] text-gray-400 border-none mt-1 block w-full rounded-md shadow-sm py-2 px-3 focus:outline-none focus:ring-gray-900 focus:border-gray-900 sm:text-sm"
                                                       id="line2" value="<?php echo $address_details["line2"]; ?>">
                                                <?php
                                            }

                                            ?>
                                        </div>
                                        <?php
                                        $province_rs = Database::search("SELECT * FROM province");
                                        $district_rs = Database::search("SELECT * FROM district");
                                        $city_rs = Database::search("SELECT * FROM city");

                                        ?>


                                        <div class="col-span-4 sm:col-span-2">
                                            <label class="block text-sm font-medium text-gray-400">Province</label>
                                            <select id="province" onchange="loadDistricts()"
                                                    class="bg-[#2e3035] text-gray-400 border-none mt-1 block w-full  rounded-md shadow-sm py-2 px-3 focus:outline-none focus:ring-gray-900 focus:border-gray-900 sm:text-sm">
                                                <option value="">Select</option>
                                                <?php

                                                $province_rs = Database::search("SELECT * FROM province");
                                                while ($province_data = $province_rs->fetch_assoc()) {
                                                    echo '<option value="' . $province_data["province_id"] . '"';

                                                    if (!empty($address_details["province_id"]) && $province_data["province_id"] == $address_details["province_id"]) {
                                                        echo ' selected';
                                                    }
                                                    echo '>' . $province_data["province_name"] . '</option>';
                                                }
                                                ?>
                                            </select>
                                        </div>

                                        <div class="col-span-4 sm:col-span-2">
                                            <label for="country" class="block text-sm font-medium text-gray-400">District</label>
                                            <select id="district" onchange="loadCities()" disabled
                                                    class="bg-[#2e3035] text-gray-400 border-none mt-1 block w-full  rounded-md shadow-sm py-2 px-3 focus:outline-none focus:ring-gray-900 focus:border-gray-900 sm:text-sm">
                                                <?php if (!empty($address_details["district_id"])): ?>

                                                    <?php
                                                    $district_rs = Database::search("SELECT * FROM district WHERE province_province_id = {$address_details['province_id']}");
                                                    while ($district_data = $district_rs->fetch_assoc()) {
                                                        echo '<option value="' . $district_data["district_id"] . '"';
                                                        // Check if this district is saved in the database and pre-select it if necessary
                                                        if ($district_data["district_id"] == $address_details["district_id"]) {
                                                            echo ' selected';
                                                        }
                                                        echo '>' . $district_data["district_name"] . '</option>';
                                                    }
                                                    ?>
                                                <?php else: ?>
                                                    <option value="">Select</option>
                                                <?php endif; ?>
                                            </select>
                                        </div>

                                        <div class="col-span-4 sm:col-span-2">
                                            <label for="country"
                                                   class="block text-sm font-medium text-gray-400">City</label>
                                            <select id="city" disabled
                                                    class="bg-[#2e3035] text-gray-400 border-none mt-1 block w-full  rounded-md shadow-sm py-2 px-3 focus:outline-none focus:ring-gray-900 focus:border-gray-900 sm:text-sm">
                                                <?php if (!empty($address_details["city_id"])): ?>

                                                    <?php
                                                    $city_rs = Database::search("SELECT * FROM city WHERE district_district_id = {$address_details['district_id']}");
                                                    while ($city_data = $city_rs->fetch_assoc()) {
                                                        echo '<option value="' . $city_data["city_id"] . '"';

                                                        if ($city_data["city_id"] == $address_details["city_id"]) {
                                                            echo ' selected';
                                                        }
                                                        echo '>' . $city_data["city_name"] . '</option>';
                                                    }
                                                    ?>
                                                <?php else: ?>
                                                    <option value="">Select</option>
                                                <?php endif; ?>
                                            </select>
                                        </div>

                                        <div class="col-span-4 sm:col-span-2">
                                            <label for="postal-code" class="block text-sm font-medium text-gray-400">ZIP
                                                / Postal code</label>
                                            <?php
                                            if (empty($address_details["postal_code"])) {
                                                ?>
                                                <input type="text"
                                                       class="bg-[#2e3035] text-gray-400 border-none mt-1 block w-full rounded-md shadow-sm py-2 px-3 focus:outline-none focus:ring-gray-900 focus:border-gray-900 sm:text-sm"
                                                       id="pcode">
                                                <?php
                                            } else {
                                                ?>
                                                <input type="text"
                                                       class="bg-[#2e3035] text-gray-400 border-none mt-1 block w-full rounded-md shadow-sm py-2 px-3 focus:outline-none focus:ring-gray-900 focus:border-gray-900 sm:text-sm"
                                                       value="<?php echo $address_details["postal_code"]; ?>"
                                                       id="pcode">

                                                <?php
                                            }


                                            ?>


                                        </div>
                                    </div>
                                </div>
                                <div class="px-4 py-3  text-right sm:px-6">
                                    <button
                                            class="bg-gray-600 border border-transparent rounded-md shadow-sm py-2 px-4 inline-flex justify-center text-sm font-medium text-white hover:bg-gray-900 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-gray-900"
                                            onclick="updateProfile();">Save
                                    </button>
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
    <script src="./public/js/profileImage.js"></script>
    <script src="./public/js/updateProfile.js"></script>


<?php require_once "./guis/partials/footer.php"; 
} else { ?><script>window.location.href = "/";</script><?php } ?>
