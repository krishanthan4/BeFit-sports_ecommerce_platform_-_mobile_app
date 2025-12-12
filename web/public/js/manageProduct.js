async function toggleProductStatus(product_id) {
    const button = document.getElementById("blockbutton-" + product_id);
    const newStatus = button.dataset.initialStatus === '1' ? '2' : '1';
  
    try {
      const response = await api.put(`/admin/products/${product_id}/status`, { status: newStatus });
      
      if (response.success) {
        if (button.dataset.initialStatus === '1') {
          button.textContent = "unblock";
          button.classList.remove("hover:bg-red-200", "text-red-700", "border-red-600");
          button.classList.add("hover:bg-green-200", "text-green-700", "border-green-600");
          button.dataset.initialStatus = '2';
        } else {
          button.textContent = "block";
          button.classList.remove("hover:bg-green-200", "text-green-700", "border-green-600");
          button.classList.add("hover:bg-red-200", "text-red-700", "border-red-600");
          button.dataset.initialStatus = '1';
        }
      } else {
        console.error("Error: " + response.message);
      }
    } catch (error) {
      console.error("Error:", error);
      alert("Network error occurred.");
    }
  }
  
  async function handleSearch(event) {
    event.preventDefault();
  
    var searchText = document.getElementById("searchInput").value.trim();
  
    if (searchText !== "") {
      try {
        const response = await api.get('/admin/products', { search: searchText });
        
        if (response.success) {
          if (response.data.length > 0) {
            updateTable(response.data);
          } else {
            const manageProductTable = document.getElementById("manageProductTable");
            if (manageProductTable && !manageProductTable.classList.contains("hidden")) {
              manageProductTable.classList.add("hidden");
            }
            document.getElementById("emptyManageProduct").classList.remove("hidden");
          }
        } else {
          console.error("Search error:", response.message);
        }
      } catch (error) {
        console.error("Failed to fetch search results:", error);
      }
    } else {
      loadAllProducts();
    }
  }
  
  document.getElementById("searchInput").addEventListener("input", function (event) {
    handleSearch(event);
  });
  
  function updateTable(data) {
    var tableBody = document.getElementById("productTableBody");
    tableBody.innerHTML = "";
  
    document.getElementById("manageProductTable").classList.remove("hidden");
    const emptyManageProduct = document.getElementById("emptyManageProduct");
    if (emptyManageProduct && !emptyManageProduct.classList.contains("hidden")) {
      emptyManageProduct.classList.add("hidden");
    }
    data.forEach(function (product, index) {
      var newRow = document.createElement("tr");
      newRow.innerHTML = `
        <td class="px-3 py-2 whitespace-nowrap text-sm">${index + 1}</td>
        <td class="px-3 py-2 whitespace-nowrap">
          <div class="flex items-center">
            <div class="flex-shrink-0 h-10 w-10">
              <img src="${product.profile_image}" alt="Profile Image" class="border border-gray-300 h-10 w-10 rounded-full">
            </div>
          </div>
        </td>
        <td class="px-3 py-2 whitespace-nowrap text-sm">${product.title}</td>
        <td class="px-3 py-2 whitespace-nowrap text-sm">Rs.${product.price}.00</td>
        <td class="px-3 py-2 whitespace-nowrap text-sm">${product.qty} stocks available</td>
        <td class="px-3 py-2 whitespace-nowrap text-sm">${product.datetime_added}</td>
        <td class="px-3 py-2 whitespace-nowrap text-sm">
          <button id="blockbutton-${product.id}" class="blockButtonStyle-${product.status === 1 ? 'blocked' : 'unblocked'} hover:bg-${product.status === 1 ? 'red' : 'green'}-200 text-${product.status === 1 ? 'red' : 'green'}-700 border-${product.status === 1 ? 'red' : 'green'}-600 inline-flex items-center px-6 py-2 border text-[16px] font-bold rounded " data-initial-status="${product.status === 1 ? '1' : '2'}" onclick="toggleProductStatus('${product.id}')">
            ${product.status === 1 ? 'block' : 'unblock'}
          </button>
        </td>
      `;
  
      tableBody.appendChild(newRow);
    });
  }
  
  async function loadAllProducts() {
    try {
      const response = await api.get('/admin/products');
      
      if (response.success) {
        updateTable(response.data);
      } else {
        console.error("Failed to load all products:", response.message);
      }
    } catch (error) {
      console.error("Failed to fetch all products:", error);
    }
  }
  