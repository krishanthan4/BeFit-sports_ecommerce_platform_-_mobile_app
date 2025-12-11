        <!-- Order summary -->
        <section aria-labelledby="summary-heading"
          class="mt-16 bg-[#2b2c30] rounded-lg px-4 py-6 sm:p-6 lg:p-8 lg:mt-0 lg:col-span-5">
          <h2 id="summary-heading" class="text-lg font-medium text-gray-300">Order summary</h2>
          <dl class="mt-6 space-y-4">
            <div class="flex items-center justify-between">
              <dt class="text-sm text-gray-400">Subtotal</dt>
              <dd id="subtotal" class="text-sm font-medium text-gray-300">Rs. 0.00</dd>
            </div>
            <div class="border-t border-[#393b41] pt-4 flex items-center justify-between">
              <dt class="flex items-center text-sm text-gray-400">
                <span>Delivery Cost</span>
                <a href="#" class="ml-2 flex-shrink-0 text-gray-300 hover:text-gray-400">
                  <svg class="h-5 w-5" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 20 20" fill="currentColor"
                    aria-hidden="true">
                    <path fill-rule="evenodd"
                      d="M18 10a8 8 0 11-16 0 8 8 0 0116 0zm-8-3a1 1 0 00-.867.5 1 1 0 11-1.731-1A3 3 0 0113 8a3.001 3.001 0 01-2 2.83V11a1 1 0 11-2 0v-1a1 1 0 011-1 1 1 0 100-2zm0 8a1 1 0 100-2 1 1 0 000 2z"
                      clip-rule="evenodd" />
                  </svg>
                </a>
              </dt>
              <dd id="deliveryCost" class="text-sm font-medium text-gray-300">Rs. 0.00</dd>
            </div>
            <div class="border-t border-[#393b41] pt-4 flex items-center justify-between">
              <dt class="text-base font-medium text-gray-300">Order total</dt>
              <dd id="orderTotal" class="text-base font-medium text-gray-300">Rs. 0.00</dd>
            </div>
          </dl>
          <div class="mt-6">
            <button id="checkoutButton" onclick="checkout();" <?php if($address_status){ echo"disabled";}?> 
              class="w-full bg-gray-400 border border-transparent rounded-md shadow-sm py-3 px-4 text-base font-medium text-[#303136] hover:bg-gray-400/80">Checkout</button>
          </div>
        </section>