
  function eventListener() {
    document.addEventListener("keyup", function(event) {
      const MainSearch2 = document.getElementById("MainSearch2");
      const MainSearch = document.getElementById("MainSearch");

      if (event.key === "Enter" && document.activeElement === MainSearch2) {
        searchBar('MainSearch2');
      }

      if (event.key === "Enter" && document.activeElement === MainSearch) {
        searchBar('MainSearch');
      }
    });

    document.getElementById("MainSearch").addEventListener("keydown", function(event) {
      // You might want to consider using 'keydown' or 'input' event instead of 'keypress'
      searchingText(event);
    });

    document.getElementById("MainSearch2").addEventListener("keydown", function(event) {
      searchingText(event);
    });
  }

  // live search text function
  async function searchingText(event) {
    if (event.key !== "Backspace") {
      let key = null;
      if (document.getElementById("MainSearch2").value) {
        key = document.getElementById("MainSearch2").value;
      } else if (document.getElementById("MainSearch").value) {
        key = document.getElementById("MainSearch").value;
      }
      if (key) {
        try {
          const response = await api.get('/search', { query: key });
          if (response.success) {
            const searchTextMain = document.getElementById("searchTextMain");
            const searchTextSub = document.getElementById("searchTextSub");
            searchTextMain.classList.remove("hidden");
            searchTextMain.classList.add("flex");
            // Clear previous search results
            searchTextSub.innerHTML = "";
            response.data.forEach(product => {
              let ptext = document.createElement("a");
              ptext.classList.add("border-b", "cursor-pointer", "hover:text-orange-500", "transition-transform");
              ptext.href = "search?query=" + product.title;
              if (product.title) {
                ptext.textContent = product.title;
                searchTextSub.appendChild(ptext);
              }
            });
          }
        } catch (error) {
          console.error("Search error:", error);
        }
      } else {
        const searchTextMain = document.getElementById("searchTextMain");
        searchTextMain.classList.add("hidden");
        searchTextMain.classList.remove("flex");
      }
    }
  }
  function onmouseEnterHideSearchText() {
    const searchTextMain = document.getElementById("searchTextMain");
    let timeoutId;
  
    searchTextMain.addEventListener("mouseenter", function (event) {
      // Cancel the setTimeout when mouse enters searchTextMain
      clearTimeout(timeoutId);
    });
  
    searchTextMain.addEventListener("mouseleave", function (event) {
      // Set the setTimeout when mouse leaves searchTextMain
      timeoutId = setTimeout(() => {
        searchTextMain.classList.add("hidden");
        searchTextMain.classList.remove("flex");
      }, 3000);
    });
  }
  

// onclick /enter search function 
  function searchBar(id) {
    const query = document.getElementById(id).value;
    if (query) {
      window.location.href = "search?query=" + query;
    }
  }

  // Call the eventListener function after the DOM has loaded
  eventListener();
