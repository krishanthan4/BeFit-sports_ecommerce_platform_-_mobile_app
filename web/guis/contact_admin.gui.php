<?php
require_once "./guis/partials/header.php";
require_once "./guis/partials/nav.php";

if (isset($_SESSION["user"]["email"])) { ?>
    <div class="">

        <main class="max-w-7xl mx-auto pb-10 py-12 lg:px-8">
            <div class="grid grid-cols-12 gap-x-5">
                <?php require_once "./guis/partials/user_profile_sidebar.php" ?>


                <div class="space-y-6 sm:px-6 lg:px-0 col-span-9">

                    <!-- content -->

                    <div class="max-w-2xl  mx-auto px-4 lg:max-w-4xl lg:px-0">
                        <h1 class="text-2xl font-extrabold tracking-tight text-gray-200 sm:text-3xl">Contact Admin</h1>
                        <p class="mt-2 text-sm text-gray-500">Update your billing information. Please note that updating
                            your
                            location could affect your tax rates..</p>
                    </div>
                    <section class="">
                        <div class="py-8  px-4 mx-auto max-w-screen-md">

                            <div class="space-y-8">
                                <div>

                                    <label for="email" class="block mb-2 text-sm font-medium text-gray-200 ">Your
                                        email</label>
                                    <input type="email" id="email" disabled value="<?php echo $_SESSION["user"]["email"] ?>"
                                        class=" bg-[#252629] border-none text-gray-200 text-sm rounded-md block w-full p-2.5"
                                        placeholder="name@flowbite.com" required>
                                </div>
                                <div>
                                    <label for="subject"
                                        class="block mb-2 text-sm font-medium text-gray-200 ">Subject</label>
                                    <input type="text" id="subject"
                                        class="block p-3 w-full text-sm text-gray-200 bg-[#252629] border-none rounded-md"
                                        placeholder="Let us know how we can help you" required>
                                </div>
                                <div class="sm:col-span-2">
                                    <label for="message" class="block mb-2 text-sm font-medium text-gray-200 ">Your
                                        message</label>
                                    <textarea id="message" rows="6" required
                                        class="block p-2.5 w-full text-sm text-gray-200 bg-[#252629] border-none rounded-md"
                                        placeholder="Leave a comment..."></textarea>
                                </div>
                                <button onclick="contactAdmin();"
                                    class="py-2.5 px-3 text-sm font-medium text-center text-[#1d1e20]  rounded-lg bg-blue-600/90 sm:w-fit hover:bg-primary-600/70">Send
                                    message</button>
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
<script src="./public/js/contactAdmin.js"></script>
<?php require_once "./guis/partials/footer.php";
} else { ?><script>window.location.href = "/";</script><?php } ?>
