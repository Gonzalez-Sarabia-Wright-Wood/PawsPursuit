let lost = document.getElementsByClassName("lost")
for(let i=0;i<lost.length;i++){
    if(lost[i].innerHTML==='false'){
        lost[i].innerHTML= "Lost";
    }else{
        lost[i].innerHTML="Reunited"
    }
}
let modal = document.getElementsByClassName("modal-label");
let toEdit = document.getElementsByClassName("to-edit")
for(let i =0; i<modal.length;i++){
    let part1 = toEdit[i].outerHTML
    part1+=" "
    part1.shift()
    modal[i].innerHTML
}