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
	const prevValue = select.value;

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
    
	// Select value
	// try to select previous value
	if (prevValue && allowedValues.includes(prevValue) && !(batchDisallowAuto && prevValue === 'AUTO')) {
	  // pokud dřívější hodnota stále dává smysl, vyber ji
	  select.value = prevValue;
	} else if (autoDefault) { // Set default selection
      const autoOpt = select.querySelector('option[value="AUTO"]');
      if (autoOpt) autoOpt.selected = true;
    } else {
	  // if nothing else select first value
      select.selectedIndex = 0; 
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

// redirecto to change language
function changeLanguage(select) {
  const lang = select.value;
  const currentUrl = new URL(window.location.href);
  currentUrl.pathname += "lang";
  currentUrl.searchParams.set('lang', lang);
  window.location.href = currentUrl.toString();
}

// Send form in background and show progress of upload and validation
document.addEventListener('DOMContentLoaded', () => {
  const form = document.getElementById('validationForm');
  const progress = document.getElementById('progress');
  // without support the form is submitted in standard way
  if (!form || !progress || !window.FormData || !window.XMLHttpRequest) return;

  const submitButton = document.getElementById('submitButton');
  const progressText = document.getElementById('progressText');
  const progressBar = document.getElementById('progressBar');
  const progressDetail = document.getElementById('progressDetail');
  const msg = progress.dataset;
  const submitText = submitButton.textContent;
  let running = false;
  let timer = null;

  function formatSize(bytes) {
    if (bytes < 1024 * 1024) {
      return (bytes / 1024).toFixed(1) + ' KB';
    }
    return (bytes / (1024 * 1024)).toFixed(1) + ' MB';
  }

  function formatTime(seconds) {
    const min = Math.floor(seconds / 60);
    const sec = seconds % 60;
    return min + ':' + String(sec).padStart(2, '0');
  }

  function setFormDisabled(disabled) {
    Array.from(form.elements).forEach(el => el.disabled = disabled);
    submitButton.textContent = disabled ? submitButton.dataset.runningText : submitText;
  }

  function stopTimer() {
    if (timer) {
      clearInterval(timer);
      timer = null;
    }
  }

  function showUpload(fileName, loaded, total) {
    const percent = total > 0 ? Math.round(loaded * 100 / total) : 0;
    progressText.textContent = msg.msgUploading + ': ' + fileName;
    progressBar.style.width = percent + '%';
    progressDetail.textContent = formatSize(loaded) + ' / ' + formatSize(total) + ' (' + percent + ' %)';
  }

  function showValidating() {
    if (timer) return;
    progress.classList.add('progress-indeterminate');
    progressBar.style.width = '';
    progressText.textContent = msg.msgValidating;
    const started = Date.now();
    const updateElapsed = () => {
      progressDetail.textContent = msg.msgElapsed + ': ' + formatTime(Math.floor((Date.now() - started) / 1000));
    };
    updateElapsed();
    timer = setInterval(updateElapsed, 1000);
  }

  function showError(detail) {
    stopTimer();
    running = false;
    progress.classList.remove('progress-indeterminate');
    progress.classList.add('progress-error');
    progressText.textContent = msg.msgError;
    progressDetail.textContent = detail;
    setFormDisabled(false);
  }

  // warn user that leaving the page cancels the validation
  window.addEventListener('beforeunload', e => {
    if (running) {
      e.preventDefault();
      e.returnValue = '';
    }
  });

  form.addEventListener('submit', e => {
    e.preventDefault();
    if (running) return;

    // read data before the form is disabled
    const data = new FormData(form);
    const file = form.elements['file'].files[0];
    const fileName = file ? file.name : '';
    const fileSize = file ? file.size : 0;

    running = true;
    setFormDisabled(true);
    progress.hidden = false;
    progress.classList.remove('progress-error', 'progress-indeterminate');
    showUpload(fileName, 0, fileSize);

    const xhr = new XMLHttpRequest();
    xhr.open('POST', form.action);
    xhr.upload.addEventListener('progress', ev => {
      if (ev.lengthComputable) {
        showUpload(fileName, ev.loaded, ev.total);
      }
    });
    // upload finished, server is validating
    xhr.upload.addEventListener('load', showValidating);
    xhr.addEventListener('load', () => {
      if (xhr.status >= 200 && xhr.status < 300) {
        stopTimer();
        running = false;
        // display returned page (result or form with error message)
        document.open();
        document.write(xhr.responseText);
        document.close();
        window.scrollTo(0, 0);
      } else {
        showError('HTTP ' + xhr.status + (xhr.statusText ? ' ' + xhr.statusText : ''));
      }
    });
    xhr.addEventListener('error', () => showError(''));
    xhr.send(data);
  });
});
