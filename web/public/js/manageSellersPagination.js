document.addEventListener("DOMContentLoaded", function () {
    setPaginationLinks();
    highlightCurrentPage();
    controlNavigationButtons();
});

function setPaginationLinks() {
    // Get all pagination links
    const paginationLinks = document.querySelectorAll("a[id^='page-']");

    // Set href attributes dynamically for all links
    paginationLinks.forEach(link => {
        const page = parseInt(link.innerText, 10);
        if (!isNaN(page)) {
            link.href = `/manageSellers?id=${page}`;
        }
    });
}

function highlightCurrentPage() {
    // Get the current page from the URL
    const urlParams = new URLSearchParams(window.location.search);
    const currentPage = parseInt(urlParams.get("id"), 10) || 1;

    console.log("Current Page: ", currentPage); // Debugging

    // Reset previous highlights
    const allLinks = document.querySelectorAll("a[id^='page-']");
    allLinks.forEach(link => {
        console.log("Checking link: ", link.id); // Debugging
        link.classList.remove("highlighted");
    });

    // Highlight the current page link
    const currentLink = document.getElementById(`page-${currentPage}`);
    console.log("Current Link ID: ", currentLink ? currentLink.id : "not found"); // Debugging
    if (currentLink) {
        currentLink.classList.add("highlighted");
    }
}


function controlNavigationButtons() {
    const urlParams = new URLSearchParams(window.location.search);
    const currentPage = parseInt(urlParams.get("id"), 10) || 1;

    const previousButton = document.querySelector("button[onclick*='navigatePrevious']");
    const nextButton = document.querySelector("button[onclick*='navigateNext']");

    // Hide the "Previous" button on the first page
    if (currentPage <= 1 && previousButton) {
        previousButton.style.display = "none";
    }

    // Hide the "Next" button on the last page
    const totalPagesElement = document.getElementById('totalPages');
    if (totalPagesElement) {
        const totalPages = parseInt(totalPagesElement.innerText, 10);
        if (currentPage >= totalPages && nextButton) {
            nextButton.style.display = "none";
        }
    }
}

function navigatePrevious(parsedId) {
    const currentPage = parseInt(parsedId, 10);
    if (!isNaN(currentPage) && currentPage > 1) {
        const previousPage = currentPage - 1;
        window.location.href = `/manageSellers?id=${previousPage}`;
    }
}

function navigateNext(parsedId) {
    const currentPage = parseInt(parsedId, 10);
    const totalPagesElement = document.getElementById('totalPages');

    if (!isNaN(currentPage) && totalPagesElement) {
        const totalPages = parseInt(totalPagesElement.innerText, 10);

        if (!isNaN(totalPages) && currentPage < totalPages) {
            const nextPage = currentPage + 1;
            window.location.href = `/manageSellers?id=${nextPage}`;
        }
    }
}
