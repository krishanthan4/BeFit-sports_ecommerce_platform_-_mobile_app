document.addEventListener("DOMContentLoaded", function () {

    setPaginationLinks();


    highlightCurrentPage();
});

function setPaginationLinks() {
    const one = document.getElementById("page-1");
    const two = document.getElementById("page-2");
    const three = document.getElementById("page-3");

    if (one) {
        one.href = `/manageProduct?id=${one.innerText}`;
    }

    if (two) {
        two.href = `/manageProduct?id=${two.innerText}`;
    }

    if (three) {
        three.href = `/manageProduct?id=${three.innerText}`;
    }
}

function highlightCurrentPage() {
    // Get the current page from the URL
    const urlParams = new URLSearchParams(window.location.search);
    const currentPage = parseInt(urlParams.get("id"), 10) || 1;

    // Reset previous highlights
    const allLinks = document.querySelectorAll("a[id^='page-']");
    allLinks.forEach(link => link.classList.remove("highlighted"));

    // Highlight the current page link
    const currentLink = document.getElementById(`page-${currentPage}`);
    if (currentLink) {
        currentLink.classList.add("highlighted");
    }
}

function navigatePrevious(parsedId) {
    const currentPage = parseInt(parsedId, 10);
    if (!isNaN(currentPage) && currentPage > 1) {
        const previousPage = currentPage - 1;
        window.location.href = `/manageProduct?id=${previousPage}`;
    }
}

function navigateNext(parsedId) {
    const currentPage = parseInt(parsedId, 10);
    const totalPagesElement = document.getElementById('totalPages');

    if (!isNaN(currentPage) && totalPagesElement) {
        const totalPages = parseInt(totalPagesElement.innerText, 10);

        if (!isNaN(totalPages) && currentPage < totalPages) {
            const nextPage = currentPage + 1;
            window.location.href = `/manageProduct?id=${nextPage}`;
        }
    } else {
        console.error('Element with id "totalPages" not found.');
    }
}
