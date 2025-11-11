(function () {
    AOS.init({
        duration: 800,
        once: true,
    });

    //슬라이더 공통
    function createSwiper(targetSelector, options = {}) {
        const el = document.querySelector(targetSelector);

        if (!el) return null;

        // 기본 옵션
        const defaultOptions = {
            loop: true,
            slidesPerView: 1,
            spaceBetween: 0,
            slidesPerGroup: 1,
            autoplay: {
                delay: 5000,
                disableOnInteraction: false,
            },
            navigation: {
                nextEl: el.querySelector('.next'),
                prevEl: el.querySelector('.prev'),
            },
            pagination: {
                el: el.querySelector('.pager'),
                clickable: true,
            },
        };

        const swiperOptions = {
            ...defaultOptions,
            ...options
        };

        return new Swiper(el, swiperOptions);
    }


    //신상 슬라이더
    const newBookSwiper = createSwiper('.new_book_swiper', {
        slidesPerView: 5,
        spaceBetween: 30,
        slidesPerGroup: 1,
        autoplay: {
            delay: 3000,
        },
        loop: true,
    });

    //베스트셀러 슬라이더
    const bestBookSwiper = createSwiper('.best_book_swiper', {
        slidesPerView: 'auto',
        spaceBetween: 20,
        slidesPerGroup: 1,
        autoplay: {
            delay: 3000,
        },
        navigation: {
            nextEl: '.section_best .next',
            prevEl: '.section_best .prev',
        },
        loop: true,
    });
    //카테고리 선택 버튼 및 슬라이더 초기화
    window.loadCategory = function (category) {
        fetch("/index/category?category=" + encodeURIComponent(category))
            .then(response => response.text())
            .then(html => {
                const wrapper = document.querySelector(".new_book_swiper");
                if (!wrapper) return;
                if (window.newBookSwiper) {
                    window.newBookSwiper.destroy(true, true);
                }
                wrapper.outerHTML = html;
                requestAnimationFrame(() => {
                    window.newBookSwiper = new Swiper(".new_book_swiper", {
                        slidesPerView: 5,
                        spaceBetween: 30,
                        slidesPerGroup: 1,
                        loop: true,
                        autoplay: {
                            delay: 3000,
                        },
                        navigation: {
                            nextEl: ".next",
                            prevEl: ".prev",
                        },
                        pagination: {
                            el: ".pager",
                            clickable: true,
                        },
                        observer: true,
                        observeParents: true,
                    });
                    window.dispatchEvent(new Event('scroll'));
                });
            })
            .catch(error => {
                console.error("로딩 오류:", error);
            });

    }

    // 애니메이션
    const mainVisu = document.querySelector('.visu');
    if (mainVisu) mainVisu.classList.add('active');

    // 스크롤 애니메이션
    function handleScroll() {
        const h = window.innerHeight;
        const targets = document.querySelectorAll('.animation:not(.active)');
        targets.forEach(el => {
            if (el.getBoundingClientRect().top < h * 0.5) el.classList.add('active');
        });
        if (!document.querySelector('.animation:not(.active)')) {
            window.removeEventListener('scroll', handleScroll);
        }
    }
    window.addEventListener('scroll', handleScroll);
    handleScroll();
})();

// 메인 비쥬얼 애니메이션
const randomX = random(-400, 400);
const randomY = random(-200, 200);
const randomDelay = random(0, 50);
const randomTime = random(6, 12);
const randomTime2 = random(5, 6);
const randomAngle = random(-30, 150);

const blurs = gsap.utils.toArray(".blur");
blurs.forEach((blur) => {
    gsap.set(blur, {
        x: randomX(-1),
        y: randomX(1),
        rotation: randomAngle(-1),
    });

    moveX(blur, 1);
    moveY(blur, -1);
    rotate(blur, 1);
});

function rotate(target, direction) {
    gsap.to(target, randomTime2(), {
        rotation: randomAngle(direction),
        // delay: randomDelay(),
        ease: Sine.easeInOut,
        onComplete: rotate,
        onCompleteParams: [target, direction * -1],
    });
}

function moveX(target, direction) {
    gsap.to(target, randomTime(), {
        x: randomX(direction),
        ease: Sine.easeInOut,
        onComplete: moveX,
        onCompleteParams: [target, direction * -1],
    });
}

function moveY(target, direction) {
    gsap.to(target, randomTime(), {
        y: randomY(direction),
        ease: Sine.easeInOut,
        onComplete: moveY,
        onCompleteParams: [target, direction * -1],
    });
}

function random(min, max) {
    const delta = max - min;
    return (direction = 1) => (min + delta * Math.random()) * direction;
}

