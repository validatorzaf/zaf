document.addEventListener('DOMContentLoaded', () => {
  const typeSelect = document.getElementById('validationType');
  const profileSelect = document.getElementById('validationProfile');
  const batchCheckbox = document.getElementById('batch');

  // Allowed profiles for each validation type
  const profileMap = {
    AUTO: ['AUTO', 'SIP_METADATA', 'SIP_PREVIEW', 'SIP', 'AD', 'FA', 'AIP', 'DIP_METADATA', 'DIP_CONTENT', 'SIP_CHANGE'],
    NSESSS2017: ['AUTO', 'SIP_METADATA', 'SIP_PREVIEW', 'SIP'],
    NSESSS2024: ['AUTO', 'SIP_METADATA', 'SIP_PREVIEW', 'SIP'],
    AP2023: ['AUTO', 'AD', 'FA'],
    DAAIP2024: ['AUTO', 'AIP', 'DIP_METADATA', 'DIP_CONTENT', 'SIP_CHANGE']
  };

  // Save original options as data objects
  const allTypeOptions = Array.from(typeSelect.options).map(opt => ({ value: opt.value, text: opt.textContent }));
  const allProfileOptions = Array.from(profileSelect.options).map(opt => ({ value: opt.value, text: opt.textContent }));

  /**
   * Refresh a select element with allowed options
   * @param {HTMLSelectElement} select - target select
   * @param {Array} allowedValues - values to display
   * @param {Array} allOptions - all original options
   * @param {boolean} batchDisallowAuto - if true, exclude 'AUTO'
   * @param {boolean} autoDefault - if true, set AUTO as selected
   */
  function refreshSelect(select, allowedValues, allOptions, batchDisallowAuto, autoDefault) {
    // Keep placeholder (first option)
    const placeholder = select.options[0];

    // Remove other options
    select.length = 1;

    // Add allowed options
    allOptions.forEach(opt => {
      if (!opt.value) return; // skip placeholder
      if (batchDisallowAuto && opt.value === 'AUTO') return; // disallow AUTO in batch mode
      if (!allowedValues.includes(opt.value)) return; // only include allowed values
      const newOption = document.createElement('option');
      newOption.value = opt.value;
      newOption.textContent = opt.text;
      select.appendChild(newOption);
    });

    // Set default selection
    if (autoDefault) {
      const autoOpt = select.querySelector('option[value="AUTO"]');
      if (autoOpt) autoOpt.selected = true;
    } else {
      select.selectedIndex = 0; // placeholder
    }
  }

  // Refresh type select
  function refreshTypes() {
    refreshSelect(
      typeSelect,
      allTypeOptions.map(o => o.value).filter(v => v), // all type values except placeholder
      allTypeOptions,
      batchCheckbox.checked, // batch disables AUTO
      !batchCheckbox.checked // AUTO as default if batch is not active
    );
  }

  // Refresh profile select based on current type
  function refreshProfiles() {
    const selectedType = typeSelect.value || 'AUTO';
    const allowedProfiles = profileMap[selectedType] || [];
    refreshSelect(
      profileSelect,
      allowedProfiles,
      allProfileOptions,
      batchCheckbox.checked, // batch disables AUTO
      !batchCheckbox.checked // AUTO as default if batch is not active
    );
  }

  // Event listeners
  typeSelect.addEventListener('change', refreshProfiles);
  batchCheckbox.addEventListener('change', () => {
    refreshTypes();
    refreshProfiles();
  });

  // Initialize selects
  refreshTypes();
  refreshProfiles();
});
