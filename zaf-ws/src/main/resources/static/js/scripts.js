document.addEventListener('DOMContentLoaded', () => {
  const typeSelect = document.getElementById('validationType');
  const profileSelect = document.getElementById('validationProfile');

  const profileMap = {
    AUTO: ['AUTO', 'SIP_METADATA', 'SIP_PREVIEW', 'SIP', 'AD', 'FA', 'AIP', 'DIP_METADATA', 'DIP_CONTENT', 'SIP_CHANGE'],
    NSESSS2017: ['AUTO', 'SIP_METADATA', 'SIP_PREVIEW', 'SIP'],
    NSESSS2024: ['AUTO', 'SIP_METADATA', 'SIP_PREVIEW', 'SIP'],
    AP2023: ['AUTO', 'AD', 'FA'],
    DAAIP2024: ['AUTO', 'AIP', 'DIP_METADATA', 'DIP_CONTENT', 'SIP_CHANGE']
  };

  const allOptions = Array.from(profileSelect.options).map(opt => ({
    value: opt.value,
    text: opt.textContent
  }));

  typeSelect.addEventListener('change', () => {
    const selectedType = typeSelect.value;
    const allowedProfiles = profileMap[selectedType] || [];

    profileSelect.innerHTML = '';

    allOptions.forEach(opt => {
      if (allowedProfiles.includes(opt.value)) {
        const newOption = document.createElement('option');
        newOption.value = opt.value;
        newOption.textContent = opt.text;
        profileSelect.appendChild(newOption);
      }
    });

    profileSelect.selectedIndex = 0;
  });
});
