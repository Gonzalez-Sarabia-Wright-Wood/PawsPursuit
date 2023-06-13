let lost = document.getElementsByClassName("lost")
for(let i=0;i<lost.length;i++){
    if(lost[i].innerHTML==='false'){
        lost[i].innerHTML= "Lost";
    }else{
        lost[i].innerHTML="Reunited";
    }
}