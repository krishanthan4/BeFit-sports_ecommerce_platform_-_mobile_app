<?php if(isset($_SESSION["user"]) && $_SESSION["user"]){
$result =  Database::search("SELECT * FROM `profile_img` WHERE `user_email`='".$_SESSION["user"]["email"]."'");
if($result->num_rows!==0){
  $path = $result->fetch_assoc();?>
       <!-- Account -->
       <a href="/userProfile" class="p-2  mx-auto text-gray-400 text-center hover:text-gray-200 ">
                  <!-- Heroicon name: outline/user -->
             <img class=" border-2 border-gray-500 object-center object-cover w-14 h-14 rounded-full" src="<?= $path["path"]?>" alt="<?= $path["user_email"]?>">
                </a>
<?php }else{ ?>
     <a href="/userProfile" class="p-2 mx-auto text-gray-400 hover:text-gray-200 text-center ">
                  <!-- Heroicon name: outline/user -->
                  <svg xmlns="http://www.w3.org/2000/svg" width="25" height="25" fill="currentColor" class="bi bi-person-circle" viewBox="0 0 16 16">
  <path d="M11 6a3 3 0 1 1-6 0 3 3 0 0 1 6 0"/>
  <path fill-rule="evenodd" d="M0 8a8 8 0 1 1 16 0A8 8 0 0 1 0 8m8-7a7 7 0 0 0-5.468 11.37C3.242 11.226 4.805 10 8 10s4.757 1.225 5.468 2.37A7 7 0 0 0 8 1"/>
</svg>                </a>
  <?php
}
    ?>
    
    <?php
    
    }else{
    ?>
        <div class=" pt-4 px-4 ">
              <div class="flow-root">
                <a href="/signup" class=" block text-center font-medium text-gray-400">Create an account</a>
              </div>
              <div class="flow-root">
                <a href="/signin" class=" pt-4 block text-center font-medium text-gray-400">Sign in</a>
              </div>
            </div>
    <?php
    }

?>