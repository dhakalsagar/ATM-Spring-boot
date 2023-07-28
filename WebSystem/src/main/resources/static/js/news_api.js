// news_api.js

// Function to fetch data from the first API
async function fetchAppleNews() {
  try {
    const response = await fetch('https://newsapi.org/v2/everything?q=apple&from=2023-07-26&to=2023-07-26&sortBy=popularity&apiKey=45532acbfb9c42589cd8b12c9e996e96');
    const data = await response.json();

    // Process the data from the first API and extract relevant information
    const articles = data.articles;
    let advertisementText = 'Advertisement - Apple News:<br>';
    articles.forEach(article => {
      advertisementText += `<b>${article.title}</b><br>${article.description}<br><br>`;
    });

    // Update the HTML to display the retrieved information in the advertisement-info_left class
    const advertisementLeft = document.querySelector('.advertisement-info_left');
    advertisementLeft.innerHTML = advertisementText;
  } catch (error) {
    console.error('Error fetching Apple News:', error);
  }
}

// Function to fetch data from the second API
async function fetchTeslaNews() {
  try {
    const response = await fetch('https://newsapi.org/v2/everything?q=tesla&from=2023-06-27&sortBy=publishedAt&apiKey=45532acbfb9c42589cd8b12c9e996e96');
    const data = await response.json();

    // Process the data from the second API and extract relevant information
    const articles = data.articles;
    let advertisementText = 'Advertisement - Tesla News:<br>';
    articles.forEach(article => {
      advertisementText += `<b>${article.title}</b><br>${article.description}<br><br>`;
    });

    // Update the HTML to display the retrieved information in the advertisement-info_right class
    const advertisementRight = document.querySelector('.advertisement-info_right');
    advertisementRight.innerHTML = advertisementText;
  } catch (error) {
    console.error('Error fetching Tesla News:', error);
  }
}

// Call the functions to fetch data from both APIs
fetchAppleNews();
fetchTeslaNews();

function autoScroll(containerElement, scrollSpeed) {
  let scrollAmount = 0;
  const scrollHeight = containerElement.scrollHeight;
  const clientHeight = containerElement.clientHeight;

  function step() {
    // Increment the scrollAmount
    scrollAmount += scrollSpeed;

    // If we have scrolled to the full content height, reset scrollAmount to 0
    if (scrollAmount >= scrollHeight) {
      scrollAmount = 0;
    }

    // Perform the actual scroll
    containerElement.scrollTop = scrollAmount;

    // Continue auto-scrolling with requestAnimationFrame
    requestAnimationFrame(step);
  }

  // Start auto-scrolling
  requestAnimationFrame(step);
}

// Call the autoScroll function on the container with a scrollSpeed of 1 pixel per frame
const containerElement1 = document.querySelector('.advertisement-info_right');
const containerElement2 = document.querySelector('.advertisement-info_left');
const scrollSpeed = 0.1;
autoScroll(containerElement1, scrollSpeed);
autoScroll(containerElement2, scrollSpeed);
