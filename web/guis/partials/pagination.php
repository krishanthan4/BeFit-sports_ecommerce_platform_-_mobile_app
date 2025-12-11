<?php
function pagination($currentPage, $totalPages, $baseurl, $itemsPerPage, $parsedJs)
{
  // Calculate the start and end pages
  if ($totalPages <= 3) {
    $startPage = 1;
    $endPage = $totalPages;
  } else {
    if ($currentPage <= 2) {
      $startPage = 1;
      $endPage = 3;
    } elseif ($currentPage >= $totalPages - 1) {
      $startPage = $totalPages - 2;
      $endPage = $totalPages;
    } else {
      $startPage = $currentPage - 1;
      $endPage = $currentPage + 1;
    }
  }

  // Calculate the offset and limit for fetching items from the database
  $offset = ($currentPage - 1) * $itemsPerPage;
  $limit = $itemsPerPage;

  // Fetch items for the current page (adjust the database query as needed)
  $items = fetchItemsFromDatabase($offset, $limit);
  echo '<style>
        .highlighted {
            background-color: #2b2d31; 
            color: #b3b3b3; 
            border: 1px solid rgb(79, 70, 229);
        }
    </style>';
  // Display the items
  echo '<div class="items-container">';
  foreach ($items as $item) {
    echo '<div class="item">';
    // Customize the display of each item
    echo '<p>' . htmlspecialchars($item['name']) . '</p>';
    echo '</div>';
  }
  echo '</div>';

  // Display pagination navigation
  echo '<nav aria-label="Pagination" class="max-w-xl mx-auto px-4 mt-6 mb-6 flex justify-between text-sm font-medium text-gray-400 sm:px-6 lg:px-8">';

  // Previous button
  echo '<div class="min-w-0 flex-1">';
  if ($currentPage > 1) {
    echo '<button onclick="navigatePrevious(' . $currentPage . ');" class="flex items-center px-4 lg:h-10 h-8  rounded-md bg-[#2b2d31] hover:bg-[#2f3035] focus:outline-none focus:border-indigo-600 focus:ring-2 focus:ring-offset-1 focus:ring-offset-indigo-600 focus:ring-indigo-600 focus:ring-opacity-25">Previous</button>';
  }
  echo '</div>';

  // Page numbers and ellipsis
  echo '<div class="hidden space-x-2 sm:flex">';
  if ($totalPages > 3 && $startPage > 1) {
    echo '<a href="' . $baseurl . '1" class="inline-flex items-center px-4 lg:h-10 h-8  rounded-md bg-[#2b2d31] hover:bg-[#2f3035]  focus:outline-none focus:border-indigo-600 focus:ring-2 focus:ring-offset-1 focus:ring-offset-indigo-600 focus:ring-indigo-600 focus:ring-opacity-25">1</a>';
    if ($startPage > 2) {
      echo '<span class="inline-flex items-center px-4 h-10 text-gray-400">...</span>';
    }
  }

  // Loop through and display each page number in the calculated range
  for ($i = $startPage; $i <= $endPage; $i++) {
    $highlightClass = ($i == $currentPage) ? 'highlighted' : '';
    echo '<a id="page-' . $i . '" href="' . $baseurl . $i . '" class="inline-flex items-center px-4 lg:h-10 h-8  rounded-md bg-[#2b2d31] hover:bg-[#2f3035]  focus:outline-none focus:border-indigo-600 focus:ring-2 focus:ring-offset-1 focus:ring-offset-indigo-600 focus:ring-indigo-600 focus:ring-opacity-25 ' . $highlightClass . '">' . $i . '</a>';
  }

  // Ellipsis and last page
  if ($totalPages > 3 && $endPage < $totalPages) {
    if ($endPage < $totalPages - 1) {
      echo '<span class="inline-flex items-center px-4 h-10 text-gray-400">...</span>';
    }
    echo '<a href="' . $baseurl . $totalPages . '" class="inline-flex items-center px-4 lg:h-10 h-8  rounded-md bg-[#2b2d31] hover:bg-[#2f3035]  focus:outline-none focus:border-indigo-600 focus:ring-2 focus:ring-offset-1 focus:ring-offset-indigo-600 focus:ring-indigo-600 focus:ring-opacity-25">' . $totalPages . '</a>';
  }
  echo '</div>';

  // Next button
  echo '<div class="min-w-0 flex-1 flex justify-end">';
  if ($currentPage < $totalPages) {
    echo '<button onclick="navigateNext(' . $currentPage . ');" class="flex items-center px-4 lg:h-10 h-8  rounded-md bg-[#2b2d31] hover:bg-[#2f3035] focus:outline-none focus:border-indigo-600 focus:ring-2 focus:ring-offset-1 focus:ring-offset-indigo-600 focus:ring-indigo-600 focus:ring-opacity-25">Next</button>';
  }
  echo '<span id="totalPages" style="display: none;">' . $totalPages . '</span>';
  echo '</div>';

  echo '</nav>';

  // Include the JavaScript file for pagination handling
  echo '<script src="./public/js/' . $parsedJs . '.js"></script>';
}

// Example function for fetching items from the database
function fetchItemsFromDatabase($offset, $limit)
{
  // This is a placeholder function; you should replace this with your actual database query logic
  // For example, using PDO or MySQLi to query your database
  $query = "SELECT * FROM products LIMIT :limit OFFSET :offset";
  // Bind parameters and execute the query to fetch the items
  // Make sure to replace this part with your actual database connection and query logic
  return [];
}
