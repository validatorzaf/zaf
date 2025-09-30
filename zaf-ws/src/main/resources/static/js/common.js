
/**
 * Toggles the visibility of an HTML element based on its ID.
 * @param {string} elementId The ID of the table or element to show/hide.
 */
function showElement(elementId) {
    // 1. Get the HTML element by its ID
    const elem = document.getElementById(elementId);

    // Check if the element was actually found
    if (!elem) {
        console.error(`Element with ID "${elementId}" not found.`);
        return; // Exit the function if the element doesn't exist
    }

    // 2. Toggle the 'display' CSS property
    // If it's currently 'none' (hidden), set it to 'block' (show).
    // Otherwise, set it to 'none' (hide).
    if (elem.style.display === 'none') {
        elem.style.display = 'block'; // Or 'table' if you prefer to be explicit for tables
    } else {
        elem.style.display = 'none';
    }
}

/**
   * Toggles the visibility of a content element and updates a visual icon.
   * @param {string} contentId The ID of the element (e.g., table) to show/hide.
   * @param {string} iconId The ID of the span element containing the visual cue.
   */
function toggleContent(contentId, iconId) {
	const content = document.getElementById(contentId);
	const icon = document.getElementById(iconId);

	if (!content || !icon) {
		console.error(`One or more elements not found. Content ID: ${contentId}, Icon ID: ${iconId}`);
		return;
	}

	// 1. Toggle the content visibility
	if (content.style.display === 'none' || content.style.display === '') {
		content.style.display = 'block'; // Show the content
		icon.innerHTML = '&#x2212;'; // Change icon to MINUS sign (&#x2212; is the proper minus)
	} else {
		content.style.display = 'none'; // Hide the content
		icon.innerHTML = '&#x2b;'; // Change icon back to PLUS sign (&#x2b; is the plus)
	}
}
