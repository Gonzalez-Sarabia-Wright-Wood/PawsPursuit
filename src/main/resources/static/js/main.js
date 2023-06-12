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

//MAPBOX
// var mapboxapi= [[${mapboxapi}]]
// mapboxgl.accessToken = mapboxapi;
// const map = new mapboxgl.Map({
//     container: 'map', // Container ID
//     style: 'mapbox://styles/mapbox/streets-v12', // Map style to use
//     center: [-98.4946, 29.4252], // Starting position [lng, lat]
//     zoom: 12 // Starting zoom level
// });
//
// const marker = new mapboxgl.Marker ({ // Initialize a new marker
//     draggable: true
// })
//     .setLngLat([-98.491142, 29.424349])// Marker [lng, lat] coordinates
//     .addTo(map); // Add the marker to the map
//
// marker.on('dragend', function(e) {
//     let html = "";
//     let longlat = e.target._lngLat;
// });