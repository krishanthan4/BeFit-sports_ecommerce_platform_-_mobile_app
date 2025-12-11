function handleSearch(event) {
    event.preventDefault();
    const searchText = document.getElementById("searchInput").value.trim();
    const sellingHistoryAboveDiv = document.getElementById("sellingHistoryAboveDiv");
  
    if (searchText !== "" && searchText!==null) {
      const xhr = new XMLHttpRequest();

      xhr.onreadystatechange = function () {
        if (xhr.readyState === XMLHttpRequest.DONE) {
          if (xhr.status === 200) {
            const response = JSON.parse(xhr.responseText);
  
            if (response.msg === "success") {
              if (response.data.length > 0) {
                updateTable(response.data);
              } else {
                const sellingHistoryTable = document.getElementById("sellingHistoryTable");
                if (sellingHistoryTable && !sellingHistoryTable.classList.contains("hidden")) {
                  sellingHistoryTable.classList.add("hidden");
                }
                
           document.getElementById("emptySellingHistory").classList.remove("hidden");
              }
            } else {
              console.error("Search error:", response.data);
            }
          } else {
            console.error("Failed to fetch search results:", xhr.status);
          }
        }
      };
      xhr.open("POST", "/processes/searchSellingHistoryProcess.php", true);
      xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
      xhr.send("search_text=" + encodeURIComponent(searchText));
    } else {
      // If search text is empty, load all sellings
      loadAllSellings();
    }
  }

document
  .getElementById("searchInput")
  .addEventListener("input", function (event) {
    handleSearch(event);
  });

function updateTable(data) {
  var tableBody = document.getElementById("sellingHistoryTableBody");
  tableBody.innerHTML = "";

  document.getElementById("sellingHistoryTable").classList.remove("hidden");
const emptySellingHistory =  document.getElementById("emptySellingHistory");
if(emptySellingHistory && !emptySellingHistory.classList.contains("hidden")){
  emptySellingHistory.classList.add("hidden");
}
  data.forEach(function (sellingHistory) {
    var newRow = document.createElement("tr");
    newRow.innerHTML = `
        <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-400">${sellingHistory.id}</td>
            <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-400">${sellingHistory.title}</td>
            <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-400">${sellingHistory.buyer}</td>
            <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-400">${sellingHistory.date}</td>
            <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-400">Rs.${sellingHistory.price}.00</td>
            <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-400">${sellingHistory.qty}</td>`;

    newRow.classList.add("border-b", "border-[#3e3e46]");
    tableBody.appendChild(newRow);
  });
}

function loadAllSellings() {
  var xhr = new XMLHttpRequest();

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
  xhr.open("GET", "/processes/loadAllSellingHistoryProcess.php", true);
  xhr.setRequestHeader("Content-Type", "application/json");
  xhr.send();
}
