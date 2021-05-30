const track = document.querySelector('.carousell__track');
const slides = Array.from(document.getElementsByClassName('carousell__slide'));
const indicator = document.querySelector('.carousell__nav')
const dots = Array.from(indicator.children);


window.onload = function () {
    slides.forEach(setSlidePosition);
};

function setSlidePosition(slide,index){
    if (slides.length<3){
        track.style.justifyContent = 'center';
    }
    let width = 0;
    for (let i=1;i<=index;i++){
        width+=slideLeft(i-1);
    }
    slide.style.left = width + 'px';
}

function slideLeft(index) {
    return slides[index].clientWidth +20;
}


let a = 0;
if (a===0) {
    slides[0].className += ' active';
    dots[0].className += ' current-dot';
    a++;
}

const moveToSlide = (track, currentSlide, targetSlide)=>{
    track.style.transform = 'translateX(-' +targetSlide.style.left;
    currentSlide.classList.remove('active');
    targetSlide.classList.add('active');
}

const updateDots = (currentDot, targetDot) => {
    currentDot.classList.remove('current-dot');
    targetDot.classList.add('current-dot')
}


indicator.addEventListener('click',e=>{
    const targetDot = e.target.closest('button');
    if (!targetDot)return ;
    const currentSlide = track.querySelector('.active');
    const currentDot = indicator.querySelector('.current-dot');
    const targetIndex = dots.findIndex(dot => dot === targetDot);
    const targetSlide = slides[targetIndex];
    moveToSlide(track,currentSlide,targetSlide);
    updateDots(currentDot,targetDot);
})

let isDown = false;
let startX;
let endX;
let walk;

track.addEventListener('mousedown',evt => {
    isDown= true;
    startX = evt.pageX - track.offsetLeft;
})

track.addEventListener('mouseleave',(e) => {
    isDown = false;
})

track.addEventListener('mouseup',(e) => {
    isDown = false;
})

track.addEventListener('mousemove',evt => {
    if (!isDown||slides.length<2){return;}
    const currentSlide = track.querySelector('.active');
    const currentDot = indicator.querySelector('.current-dot');
    const index = slides.findIndex(e=>e.className.endsWith('active'));
    let x;
    evt.preventDefault();
    endX = evt.pageX - track.offsetLeft;
    walk = endX-startX;
    if (walk<0){
        x = index+1;
    }else{
        x = index-1;
    }
    if (x>=0||x<slides.length) {
        let targetSlide = slides[x];
        let targetDot = dots[x];
        moveToSlide(track, currentSlide, targetSlide);
        updateDots(currentDot, targetDot);
    }
    isDown = false;
})