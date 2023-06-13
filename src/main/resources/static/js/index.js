let lost = document.getElementsByClassName("lost")
for(let i=0;i<lost.length;i++){
    if(lost[i].innerHTML==='false'){
        lost[i].innerHTML= "Lost";
    }else{
        lost[i].innerHTML="Reunited";
    }
}
let modal = document.getElementsByClassName("ml");
let toModal = document.getElementsByClassName("tm");
let edit = document.getElementsByClassName("te");

for(let i =0; i<=modal.length;i++){
    let a = Array.from(toModal[i].outerHTML);
    a.splice(98,1, "modal"+modal[i].innerHTML);
    a=a.join("");
    toModal[i].outerHTML=a;

    let b =Array.from(edit[i].outerHTML);
    b.splice(10,1,"modal"+modal[i].innerHTML);
    b=b.join("");
    edit[i].outerHTML=b;
}