// mapboxgl.accessToken = mapboxapi;

let toggle = document.querySelector("#toggler");
let pet = document.querySelector("#pet");
let post = document.querySelector("#post");
let hiddenPost = post.getAttribute("hidden");
let hiddenPet = pet.getAttribute("hidden");
let toggled = true;
let n =0;
post.removeAttribute("hidden")

toggle.addEventListener("click", function () {
    n++
    if (n===2){
        toggled = !toggled
        n=0;
    }
    if (toggled === true) {
        post.removeAttribute("hidden")
        pet.setAttribute("hidden","hiddenPet")
    } else {
        pet.removeAttribute("hidden")
        post.setAttribute("hidden","hiddenPost")
    }
})
