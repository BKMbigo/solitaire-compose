
window.addEventListener("DOMContentLoaded", (event) => {
    const button = document.querySelector("button");
    if (button) {
        button.addEventListener("click", function () {
            chrome.tabs.create(
                {
                    url: "/game.html"
                }
            )
        })
    }
});

