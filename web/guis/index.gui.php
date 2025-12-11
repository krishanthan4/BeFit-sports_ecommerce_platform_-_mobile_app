<?php require_once "./guis/partials/header.php";
require_once "./guis/partials/nav.php";?>
 <?php 
try {
  if(isset($_SESSION["user"]) && $_SESSION["user"]){
    if(isset($_GET["id"])){
      $pageId =$_GET["id"]; 
    
    }else{
      $pageId =1; 
    }
    require_once "./guis/home.gui.php";
     }else{
 
 ?>
    
     <main>
        <!-- Hero -->
        <div class="flex flex-col border-b border-gray-200 lg:border-0">
    
          <div class="relative">
            <div aria-hidden="true" class="hidden absolute w-1/2 h-full bg-[#1d1e20] lg:block"></div>
            <div class="relative bg-[#1d1e20] lg:bg-transparent">
              <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 lg:grid lg:grid-cols-2">
                <div class="max-w-2xl mx-auto py-24 lg:py-64 lg:max-w-none">
                  <div class="lg:pr-16">
                    <h1 class="text-4xl font-extrabold tracking-tight text-gray-200 sm:text-5xl text-center xl:text-6xl"> One-Stop Shop for Fitness Gear</h1>
                    <p class="mt-4 text-xl text-gray-600">Elevate your fitness journey with premium equipment for calisthenics, swimming, gym, skateboarding, and badminton. Get fit, stay fit.</p>
                    <div class="mt-6 text-center">
                      <a href="/signin" class="inline-block bg-gray-200  py-3 px-8 rounded-md font-medium text-[#252629] hover:bg-gray-500">Shop To Fit</a>
                    </div>
                  </div>
                </div>
              </div>
            </div>
            <div class="w-full h-48 sm:h-64 lg:absolute lg:top-0 lg:right-0 lg:w-1/2 lg:h-full">
              <img src="https://i.pinimg.com/736x/7d/59/15/7d591529d90f6d842701aaf87ce09572.jpg" alt="" class="w-full h-full object-center object-cover">
            </div>
          </div>
        </div>
  
       <!-- Collections -->
       <section aria-labelledby="collections-heading" class="bg-[#1d1e20]">
          <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
            <div class="max-w-2xl mx-auto py-16 sm:py-24 lg:py-32 lg:max-w-none">
              <h2 id="collections-heading" class="text-2xl font-extrabold text-gray-200">Collections</h2>
    
              <div class="mt-6 space-y-12 lg:space-y-0 lg:grid lg:grid-cols-3 lg:gap-x-6">
                <div class="group relative">
                  <div class="relative w-full h-80 bg-white rounded-lg overflow-hidden group-hover:opacity-75 sm:aspect-w-2 sm:aspect-h-1 sm:h-64 lg:aspect-w-1 lg:aspect-h-1">
                    <img src="https://c4.wallpaperflare.com/wallpaper/717/57/276/fitness-hd-1080p-windows-wallpaper-preview.jpg" alt="Desk with leather desk pad, walnut desk organizer, wireless keyboard and mouse, and porcelain mug." class="w-full h-full object-center object-cover">
                  </div>
                  <h3 class="mt-6 text-sm text-gray-500">
                    <a >
                      <span class="absolute inset-0"></span>
                      Gym and Calisthenics
                    </a>
                  </h3>
                  <p class="text-base font-semibold text-gray-200">Gym and calisthenics accessories</p>
                </div>
    
                <div class="group relative">
                  <div class="relative w-full h-80 bg-white rounded-lg overflow-hidden group-hover:opacity-75 sm:aspect-w-2 sm:aspect-h-1 sm:h-64 lg:aspect-w-1 lg:aspect-h-1">
                    <img src="https://media.istockphoto.com/id/164249499/photo/swimmer-performing-butterfly-stroke.jpg?s=612x612&w=0&k=20&c=zKevwT3d8ivj1ATDpFjctoTDnsf20HuufYXLS_AOwDA=" alt="Wood table with porcelain mug, leather journal, brass pen, leather key ring, and a houseplant." class="w-full h-full object-center object-cover">
                  </div>
                  <h3 class="mt-6 text-sm text-gray-500">
                    <a >
                      <span class="absolute inset-0"></span>
                      Under Water
                    </a>
                  </h3>
                  <p class="text-base font-semibold text-gray-200">Swimming and water sports</p>
                </div>
    
                <div class="group relative">
                  <div class="relative w-full h-80 bg-white rounded-lg overflow-hidden group-hover:opacity-75 sm:aspect-w-2 sm:aspect-h-1 sm:h-64 lg:aspect-w-1 lg:aspect-h-1">
                    <img src="https://images.hdqwalls.com/wallpapers/man-on-skateboard-48.jpg" alt="Collection of four insulated travel bottles on wooden shelf." class="w-full h-full object-center object-cover">
                  </div>
                  <h3 class="mt-6 text-sm text-gray-500">
                    <a >
                      <span class="absolute inset-0"></span>
                      Fun and go
                    </a>
                  </h3>
                  <p class="text-base font-semibold text-gray-200">Skateboards and badminton</p>
                </div>
              </div>
            </div>
          </div>
        </section>
    
       
        <!-- Featured section -->
        <section aria-labelledby="cause-heading">
          <div class="relative bg-gray-800 py-32 px-6 sm:py-40 sm:px-12 lg:px-16">
            <div class="absolute inset-0 overflow-hidden">
              <img src="https://wallpapercosmos.com/w/full/6/1/b/1130964-2560x2048-desktop-hd-calisthenics-background.jpg" alt="" class="w-full h-full object-center object-cover">
            </div>
            <div aria-hidden="true" class="absolute inset-0 bg-gray-900 bg-opacity-50"></div>
            <div class="relative max-w-3xl mx-auto flex flex-col items-center text-center">
              <h2 id="cause-heading" class="text-3xl font-extrabold tracking-tight text-white sm:text-4xl">Why BeFit ?</h2>
              <p class="mt-3 text-xl text-white">Choose beFit for premium equipment, expert advice, and unparalleled support to achieve your fitness goals effectively and efficiently</p>
              <a href="/" class="mt-8 w-full block bg-[#1e1f22f5] border border-[#27282c] rounded-md py-3 px-8 text-base font-medium text-gray-200 hover:bg-[#151618] sm:w-auto">Read our story</a>
            </div>
          </div>
        </section>
     <!-- Sale and testimonials -->
     <div class="relative overflow-hidden">
       
       <!-- Testimonials -->
       <section aria-labelledby="testimonial-heading" class="relative py-24 max-w-7xl mx-auto px-4 sm:px-6 lg:py-32 lg:px-8">
         <div class="max-w-2xl mx-auto lg:max-w-none">
           <h2 id="testimonial-heading" class="text-2xl font-extrabold tracking-tight text-gray-200">What are people saying?</h2>
    
           <div class="mt-16 space-y-16 lg:space-y-0 lg:grid lg:grid-cols-3 lg:gap-x-8">
             <blockquote class="sm:flex lg:block">
               <svg width="24" height="18" viewBox="0 0 24 18" xmlns="http://www.w3.org/2000/svg" aria-hidden="true" class="flex-shrink-0 text-gray-300">
                 <path d="M0 18h8.7v-5.555c-.024-3.906 1.113-6.841 2.892-9.68L6.452 0C3.188 2.644-.026 7.86 0 12.469V18zm12.408 0h8.7v-5.555C21.083 8.539 22.22 5.604 24 2.765L18.859 0c-3.263 2.644-6.476 7.86-6.451 12.469V18z" fill="currentColor" />
               </svg>
               <div class="mt-8 sm:mt-0 sm:ml-6 lg:mt-10 lg:ml-0">
                 <p class="text-lg text-gray-600">beFit exceeded my expectations! The calisthenics gear is top-notch, and the customer service was exceptional. Highly recommend!</p>
                 <cite class="mt-4 block font-semibold not-italic text-gray-200"> Sarah K. </cite>
               </div>
             </blockquote>
    
             <blockquote class="sm:flex lg:block">
               <svg width="24" height="18" viewBox="0 0 24 18" xmlns="http://www.w3.org/2000/svg" aria-hidden="true" class="flex-shrink-0 text-gray-300">
                 <path d="M0 18h8.7v-5.555c-.024-3.906 1.113-6.841 2.892-9.68L6.452 0C3.188 2.644-.026 7.86 0 12.469V18zm12.408 0h8.7v-5.555C21.083 8.539 22.22 5.604 24 2.765L18.859 0c-3.263 2.644-6.476 7.86-6.451 12.469V18z" fill="currentColor" />
               </svg>
               <div class="mt-8 sm:mt-0 sm:ml-6 lg:mt-10 lg:ml-0">
                 <p class="text-lg text-gray-600">From swimming to badminton, beFit has it all. Their range of equipment is impressive, and the delivery was swift. A+ service!</p>
                 <cite class="mt-4 block font-semibold not-italic text-gray-200">John Mathew</cite>
               </div>
             </blockquote>
    
             <blockquote class="sm:flex lg:block">
               <svg width="24" height="18" viewBox="0 0 24 18" xmlns="http://www.w3.org/2000/svg" aria-hidden="true" class="flex-shrink-0 text-gray-300">
                 <path d="M0 18h8.7v-5.555c-.024-3.906 1.113-6.841 2.892-9.68L6.452 0C3.188 2.644-.026 7.86 0 12.469V18zm12.408 0h8.7v-5.555C21.083 8.539 22.22 5.604 24 2.765L18.859 0c-3.263 2.644-6.476 7.86-6.451 12.469V18z" fill="currentColor" />
               </svg>
               <div class="mt-8 sm:mt-0 sm:ml-6 lg:mt-10 lg:ml-0">
                 <p class="text-lg text-gray-600">I'm a skateboarding enthusiast, and beFit had everything I needed to upgrade my gear. Fast shipping and great quality. Thanks, beFit!</p>
                 <cite class="mt-4 block font-semibold not-italic text-gray-200">  Emily Rail </cite>
               </div>
             </blockquote>
           </div>
         </div>
       </section>
     </div>
    
        <!-- CTA section -->
        <section aria-labelledby="sale-heading">
          <div class="pt-32 overflow-hidden sm:pt-14">
            <div class="bg-[#252629]">
              <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
                <div class="relative pt-48 pb-16 sm:pb-24">
                  <div>
                    <h2 id="sale-heading" class="text-4xl font-extrabold tracking-tight text-white md:text-5xl">
                      Final Stock.
                      <br>
                      Up to 50% off.
                    </h2>
                    <div class="mt-6 text-base">
                      <a href="/signin" class="font-semibold text-white">Shop the sale<span aria-hidden="true"> &rarr;</span></a>
                    </div>
                  </div>
    
                  <div class="absolute -top-32 left-1/2 transform -translate-x-1/2 sm:top-6 sm:translate-x-0">
                    <div class="ml-24 flex space-x-6 min-w-max sm:ml-3 lg:space-x-8">
                      <div class="flex space-x-6 sm:flex-col sm:space-x-0 sm:space-y-6 lg:space-y-8">
                        <div class="flex-shrink-0">
                          <img class="h-64 w-64 rounded-lg object-cover md:h-72 md:w-72" src="./public/images/product_images/product2.jpeg" alt="">
                        </div>
    
                        <div class="mt-6 flex-shrink-0 sm:mt-0">
                          <img class="h-64 w-64 rounded-lg object-cover md:h-72 md:w-72" src="./public/images/product_images/product3.jpeg" alt="">
                        </div>
                      </div>
                      <div class="flex space-x-6 sm:-mt-20 sm:flex-col sm:space-x-0 sm:space-y-6 lg:space-y-8">
                        <div class="flex-shrink-0">
                          <img class="h-64 w-64 rounded-lg object-cover md:h-72 md:w-72" src="./public/images/product_images/product4.jpeg" alt="">
                        </div>
    
                        <div class="mt-6 flex-shrink-0 sm:mt-0">
                          <img class="h-64 w-64 rounded-lg object-cover md:h-72 md:w-72" src="./public/images/product_images/product11.jpeg" alt="">
                        </div>
                      </div>
                      <div class="flex space-x-6 sm:flex-col sm:space-x-0 sm:space-y-6 lg:space-y-8">
                        <div class="flex-shrink-0">
                          <img class="h-64 w-64 rounded-lg object-cover md:h-72 md:w-72" src="./public/images/product_images/product6.jpeg" alt="">
                        </div>
    
                        <div class="mt-6 flex-shrink-0 sm:mt-0">
                          <img class="h-64 w-64 rounded-lg object-cover md:h-72 md:w-72" src="./public/images/product_images/product7.jpeg" alt="">
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </section>
      </main>
    <?php
    
     }
} catch (\Throwable $th) {
  //throw $th;
}
 
 ?>


  <?php require_once "./guis/partials/footer.php";
?>