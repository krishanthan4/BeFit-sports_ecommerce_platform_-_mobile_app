async function handleSearch(event) {
    event.preventDefault();
    const searchText = document.getElementById("searchInput").value.trim();
    const sellingHistoryAboveDiv = document.getElementById("sellingHistoryAboveDiv");
  
    if (searchText !== "" && searchText!==null) {
      try {
        const response = await api.get('/seller/sales-history', { search: searchText });
  
        if (response.success) {
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
          console.error("Search error:", response.message);
        }
      } catch (error) {
        console.error("Failed to fetch search results:", error);
      }
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

async function loadAllSellings() {
  try {
    const response = await api.get('/seller/sales-history');

    if (response.success) {
      updateTable(response.data);
    } else {
      console.error("Failed to load sales history:", response.message);
    }
  } catch (error) {
    console.error("Failed to fetch sales history:", error);
  }
}
