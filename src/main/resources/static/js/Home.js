const nav = document.querySelector('.nav');

window.addEventListener('scroll', function(){
    nav.classList.toggle('active', window.scrollY>0)
})

    document.addEventListener('DOMContentLoaded', function() {
        const hamburger = document.querySelector('.hamburger-menu');
        const menu = document.querySelector('.menu');

        hamburger.addEventListener('click', function() {
            menu.classList.toggle('active');
        });
    });
    document.addEventListener('DOMContentLoaded', () => {
        const options = {
            root: null,
            rootMargin: '0px',
            threshold: 0.1
        };
    
        const callback = (entries, observer) => {
            entries.forEach(entry => {
                if (entry.isIntersecting) {
                    if (entry.target.classList.contains('general-img')) {
                        entry.target.classList.add('fadeInLeft');
                    } else if (entry.target.classList.contains('general-txt')) {
                        entry.target.classList.add('fadeInRight');
                    }
                    observer.unobserve(entry.target);
                }
            });
        };
    
        const observer = new IntersectionObserver(callback, options);
    
        const targets = document.querySelectorAll('.general-img, .general-txt');
        targets.forEach(target => observer.observe(target));
    });

    document.addEventListener('DOMContentLoaded', () => {
        const options = {
            root: null,
            rootMargin: '0px',
            threshold: 0.1
        };
    
        const callback = (entries, observer) => {
            entries.forEach(entry => {
                if (entry.isIntersecting) {
                    if (entry.target.classList.contains('general-img')) {
                        entry.target.classList.add('fadeInLeft');
                    } else if (entry.target.classList.contains('general-txt')) {
                        entry.target.classList.add('fadeInRight');
                    } else if (entry.target.classList.contains('Comunity')) {
                        entry.target.classList.add('fadeIn');
                    }
                    observer.unobserve(entry.target);
                }
            });
        };
    
        const observer = new IntersectionObserver(callback, options);
    
        const targets = document.querySelectorAll('.general-img, .general-txt, .Comunity');
        targets.forEach(target => observer.observe(target));
    });
    
