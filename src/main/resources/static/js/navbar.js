const toggler = document.getElementById('toggler');

toggler.addEventListener('click', (e) => {
    myFunction();
})

function myFunction() {
    let x = document.getElementById('navbarPrimary');
    if (x.className === "navbar-collapse collapse") {
        x.className += " show";
    } else {
        x.className = "navbar-collapse collapse"
    }
}
