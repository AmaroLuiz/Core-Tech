// scope the navbar controls to the top <nav> so sidebar .menu is not affected
const topNav = document.querySelector('nav');
if (topNav) {
    const menuBtn = topNav.querySelector('.menu-btn');
    const menu = topNav.querySelector('.menu');
    if (menuBtn && menu) {
        menuBtn.addEventListener('click', () => {
            menu.classList.toggle('active');
            const icon = menuBtn.querySelector('i');
            if (menu.classList.contains('active')) {
                icon.classList.remove('fa-bars');
                icon.classList.add('fa-xmark');
                document.body.style.overflowY = 'hidden';
            } else {
                icon.classList.remove('fa-xmark');
                icon.classList.add('fa-bars');
                document.body.style.overflowY = 'auto';
            }
        });
    }
}
