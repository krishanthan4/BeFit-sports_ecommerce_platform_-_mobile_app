<div id="msgToast" class="hidden rounded-md z-[70] fixed right-10 bottom-10 ps-5 pe-2 py-2 border-l-8 border-red-500/90 bg-[#303136] drop-shadow-lg">
        <div class="text-sm flex items-center">
            <div class="inline-block mr-2 px-3 py-1 rounded-full bg-red-500/90 text-gray-400 font-extrabold" id="msgIcon">?</div>
            <div class="inline-block text-gray-300" id="msg"></div>
            <div onclick="closeAlert();" class="ps-2 cursor-pointer text-[#828592] ">
            &#x2715;
            </div>
        </div>
    </div>


<script>
    function closeAlert(){
        document.getElementById("msgToast").classList.add("hidden");
    }
</script>