let lost = document.getElementsByClassName("lost")
for (let i = 0; i < lost.length; i++) {
    if (lost[i].innerHTML === 'false') {
        lost[i].innerHTML = "Lost";
    } else {
        lost[i].innerHTML = "Reunited";
    }
}
let modal = document.getElementsByClassName("hiddenModal");
let toModal = document.getElementsByClassName("tooMuchModal");
let a;
for (let i = 0; i < modal.length; i++) {
    a = Array.from(toModal[i].outerHTML);
    a.splice(25, 1, "modal" + modal[i].innerHTML);
    a = a.join("");
    toModal[i].outerHTML = a;
}