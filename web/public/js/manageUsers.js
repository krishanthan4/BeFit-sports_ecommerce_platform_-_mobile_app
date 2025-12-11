function toggleUserStatus(user_email) {
    const button = document.getElementById("blockbutton-" + user_email);
    const formData = new FormData();
    formData.append("user_email", user_email);
    formData.append("active", button.dataset.initialStatus === "1" ? "2" : "1");
  
    const request = new XMLHttpRequest();
    request.open("POST", "./processes/manageUsersProcess.php", true);
    request.onreadystatechange = function () {
      if (request.readyState == 4) {
        if (request.status == 200) {
          const data = JSON.parse(request.responseText);
          if (data.status === "success") {
            console.log(`Initial status: ${button.dataset.initialStatus}`);
            if (button.dataset.initialStatus === "1") {
              button.textContent = "unblock";
              button.classList.remove("hover:bg-red-200", "text-red-500", "border-red-500");
              button.classList.add("hover:bg-green-200", "text-green-500", "border-green-500");
              button.dataset.initialStatus = "2";
              console.log(`Updated to unblock with classes: ${button.className}`);
            } else {
              button.textContent = "block";
              button.classList.remove("hover:bg-green-200", "text-green-500", "border-green-500");
              button.classList.add("hover:bg-red-200", "text-red-500", "border-red-500");
              button.dataset.initialStatus = "1";
              console.log(`Updated to block with classes: ${button.className}`);
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
      xhr.open("POST", "/processes/searchUsersProcess.php", true);
      xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
      xhr.onreadystatechange = function () {
        if (xhr.readyState === XMLHttpRequest.DONE) {
          if (xhr.status === 200) {
            var response = JSON.parse(xhr.responseText);
  
            if (response.msg === "success") {
              if (response.data.length > 0) {
                updateTable(response.data);
              } else {
                const manageUsersTable = document.getElementById("manageUsersTable");
                if (manageUsersTable && !manageUsersTable.classList.contains("hidden")) {
                  manageUsersTable.classList.add("hidden");
                }
  
                document.getElementById("emptyManageUsers").classList.remove("hidden");
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
      loadAllUsers();
    }
  }
  
  document.getElementById("searchInput").addEventListener("input", function (event) {
    handleSearch(event);
  });
  
  function updateTable(data) {
    var tableBody = document.getElementById("userTableBody");
    tableBody.innerHTML = "";
  
    document.getElementById("manageUsersTable").classList.remove("hidden");
    const emptyManageUsers = document.getElementById("emptyManageUsers");
    if (emptyManageUsers && !emptyManageUsers.classList.contains("hidden")) {
      emptyManageUsers.classList.add("hidden");
    }
  
    data.forEach(function (user, index) {
      var newRow = document.createElement("tr");
      newRow.innerHTML = `
        <td class="px-3 py-2 whitespace-nowrap text-sm">${index + 1}</td>
        <td class="px-3 py-2 whitespace-nowrap text-sm">
          <img src="${user.profile_image ? user.profile_image : "./resources/new_user.png"}" alt="Profile Image" class="h-10 w-10 rounded-full border object-cover object-center border-gray-300">
        </td>
        <td class="px-3 py-2 whitespace-nowrap text-sm">${user.username ? user.username : "-"}</td>
        <td class="px-3 py-2 whitespace-nowrap text-sm">${user.email}</td>
        <td class="px-3 py-2 whitespace-nowrap text-sm">${user.mobile ? user.mobile : "-"}</td>
        <td class="px-3 py-2 whitespace-nowrap text-sm">${user.joined_date}</td>
        <td class="px-3 py-2 whitespace-nowrap text-sm">
          <button
            id="blockbutton-${user.email}"
            class="blockButtonStyle-${user.status === 1 ? 'blocked' : 'unblocked'} hover:bg-${user.status === 1 ? 'red' : 'green'}-200 text-${user.status === 1 ? 'red' : 'green'}-500 border-${user.status === 1 ? 'red' : 'green'}-500 inline-flex items-center px-6 py-2 border text-[16px] font-bold rounded"
            data-initial-status="${user.status === 1 ? '1' : '2'}"
            onclick="toggleUserStatus('${user.email}')"
          >
            ${user.status === 1 ? 'block' : 'unblock'}
          </button>
        </td>
      `;
      tableBody.appendChild(newRow);
    });
  }
  
  function loadAllUsers() {
    var xhr = new XMLHttpRequest();
    xhr.open("GET", "/processes/loadAllUsersProcess.php", true);
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
  