let toggle = document.querySelector("#toggler");
let toReplace = document.querySelector("#toReplace")
toggle.addEventListener("click", function(){
    toReplace = toReplace.html("<div th:replace='~{partials/reg_post_form.html :: form'>")
})