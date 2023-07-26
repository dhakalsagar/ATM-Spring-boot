
// script.js
document.addEventListener('DOMContentLoaded', function () {
  const menuItems = document.querySelectorAll('.menu-item');
  const contents = document.querySelectorAll('.content');

  function hideAllContents() {
    contents.forEach((content) => {
      content.style.display = 'none';
    });
  }

  menuItems.forEach((item) => {
    item.addEventListener('click', function (event) {
      event.preventDefault();
      hideAllContents();

      const targetContentId = item.dataset.page + '-content';
      const targetContent = document.getElementById(targetContentId);
      if (targetContent) {
        targetContent.style.display = 'block';
      }
    });
  });
});
