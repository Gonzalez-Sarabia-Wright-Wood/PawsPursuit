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

let dropDownShow = document.getElementsByClassName("dd-hidden");
let userToMatch = document.getElementsByClassName("usr-hddn");
let profile = document.getElementsByClassName("profile")
let profileToMatch = document.getElementsByClassName("prf-hddn")
let usr = document.querySelector("#usr");
for (let i = 0; i < profile.length; i++) {
    if (usr.innerHTML === profileToMatch[i].innerHTML) {
        profile[i].setAttribute('hidden', 'hidden');
    }
}
for (let i = 0; i < userToMatch.length; i++) {
    if (usr.innerHTML !== userToMatch[i].innerHTML) {
        dropDownShow[i].setAttribute('hidden', 'hidden');
        userToMatch[i].setAttribute('hidden', 'hidden');
    }
}