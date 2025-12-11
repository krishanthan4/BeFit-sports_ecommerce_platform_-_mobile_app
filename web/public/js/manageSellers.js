function toggleSellerStatus(seller_email) {
  const button = document.getElementById("blockbutton-" + seller_email);
  const formData = new FormData();
  formData.append("user_email", seller_email);
  formData.append("active", button.dataset.initialStatus === "1" ? "2" : "1");

  const request = new XMLHttpRequest();
  request.open("POST", "./processes/manageUsersProcess.php", true);
  request.onreadystatechange = function () {
    if (request.readyState == 4) {
      if (request.status == 200) {
        const data = JSON.parse(request.responseText);
        if (data.status === "success") {
          if (button.dataset.initialStatus === "1") {
            button.textContent = "unblock";
            button.classList.remove("hover:bg-red-200", "text-red-700", "border-red-600");
            button.classList.add("hover:bg-green-200", "text-green-700", "border-green-600");
            button.dataset.initialStatus = "2";
          } else {
            button.textContent = "block";
            button.classList.remove("hover:bg-green-200", "text-green-700", "border-green-600");
            button.classList.add("hover:bg-red-200", "text-red-700", "border-red-600");
            button.dataset.initialStatus = "1";
          }
        } else {
          console.error("Error: " + data.message);
        }
      } else {
        console.error("Error: " + request.statusText);
      }
    }
  };
  request.onerror = function () {
    alert("Network error occurred.");
  };
  request.send(formData);
}

function handleSearch(event) {
  event.preventDefault();

  var searchText = document.getElementById("searchInput").value.trim();

  if (searchText !== "") {
    var xhr = new XMLHttpRequest();
    xhr.open("POST", "/processes/searchSellersProcess.php", true);
    xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
    xhr.onreadystatechange = function () {
      if (xhr.readyState === XMLHttpRequest.DONE) {
        if (xhr.status === 200) {
          var response = JSON.parse(xhr.responseText);

          if (response.msg === "success") {
            if (response.data.length > 0) {
              updateTable(response.data);
            } else {
              const manageSellersTable = document.getElementById("manageSellersTable");
              if (manageSellersTable && !manageSellersTable.classList.contains("hidden")) {
                manageSellersTable.classList.add("hidden");
              }

              document.getElementById("emptyManageSellers").classList.remove("hidden");
            }
          } else {
            console.error("Search error:", response.data);
          }
        } else {
          console.error("Failed to fetch search results:", xhr.status);
        }
      }
    };
    xhr.send("search_text=" + encodeURIComponent(searchText));
  } else {
    loadAllSellers();
  }
}

document.getElementById("searchInput").addEventListener("input", function (event) {
  handleSearch(event);
});

function updateTable(data) {
  var tableBody = document.getElementById("sellerTableBody");
  tableBody.innerHTML = "";
  document.getElementById("manageSellersTable").classList.remove("hidden");
  const emptyManageSellers = document.getElementById("emptyManageSellers");
  if (emptyManageSellers && !emptyManageSellers.classList.contains("hidden")) {
    emptyManageSellers.classList.add("hidden");
  }
  data.forEach(function (seller, index) {
    var newRow = document.createElement("tr");
    newRow.innerHTML = `
      <td class="px-3 py-2 whitespace-nowrap text-sm">${index + 1}</td>
      <td class="px-3 py-2 whitespace-nowrap text-sm">
        <img src="${seller.profile_image ? seller.profile_image : "./resources/new_user.png"}" alt="Profile Image" class="h-10 w-10 rounded-full border object-cover object-center border-gray-300">
      </td>
      <td class="px-3 py-2 whitespace-nowrap text-sm">${seller.username ? seller.username : "-"}</td>
      <td class="px-3 py-2 whitespace-nowrap text-sm">${seller.email}</td>
      <td class="px-3 py-2 whitespace-nowrap text-sm">${seller.mobile ? seller.mobile : "-"}</td>
      <td class="px-3 py-2 whitespace-nowrap text-sm">${seller.joined_date}</td>
      <td class="px-3 py-2 whitespace-nowrap text-sm">${seller.totalProducts}</td>
      <td class="px-3 py-2 whitespace-nowrap text-sm">
        <button
          id="blockbutton-${seller.email}"
          class="blockButtonStyle-${seller.status === 1 ? 'blocked' : 'unblocked'} hover:bg-${seller.status === 1 ? 'red' : 'green'}-200 text-${seller.status === 1 ? 'red' : 'green'}-700 border-${seller.status === 1 ? 'red' : 'green'}-600 inline-flex items-center px-6 py-2 border text-[16px] font-bold rounded "
          data-initial-status="${seller.status === 1 ? '1' : '2'}"
          onclick="toggleSellerStatus('${seller.email}')"
        >
          ${seller.status === 1 ? 'block' : 'unblock'}
        </button>
      </td>
    `;


    tableBody.appendChild(newRow);
  });
}

function loadAllSellers() {
  var xhr = new XMLHttpRequest();
  xhr.open("GET", "/processes/loadAllSellersProcess.php", true);
  xhr.setRequestHeader("Content-Type", "application/json");
  xhr.onreadystatechange = function () {
    if (xhr.readyState === XMLHttpRequest.DONE) {
      if (xhr.status === 200) {
        var response = JSON.parse(xhr.responseText);

        if (response.msg === "success") {
          updateTable(response.data);
        } else {
          console.error("Failed to load all users:", response.msg);
        }
      } else {
        console.error("Failed to fetch all users:", xhr.status);
      }
    }
  };
  xhr.send();
}
