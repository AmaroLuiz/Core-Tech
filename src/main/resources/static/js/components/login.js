document.addEventListener('DOMContentLoaded', function () {
    const form = document.getElementById('formularioLogin');

    if (!form) return;

    form.addEventListener('submit', async (e) => {
        e.preventDefault();

        const email = document.getElementById('email').value.trim();
        const password = document.getElementById('senha').value;

        if (!email || !password) {
            alert('Preencha email e senha.');
            return;
        }

        // Validação simples de formato de email
        const emailRe = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
        if (!emailRe.test(email)) {
            alert('Digite um email válido.');
            return;
        }

    });

    // botão de olho
    const eyeButtons = document.querySelectorAll('.btn-eye');
    eyeButtons.forEach(btn => {
        btn.addEventListener('click', () => {
            const targetId = btn.getAttribute('data-target');
            const input = document.getElementById(targetId);
            if (!input) return;
            const icon = btn.querySelector('i');
            if (input.type === 'password') {
                input.type = 'text';
                if (icon) { icon.classList.remove('fa-eye'); icon.classList.add('fa-eye-slash'); }
            } else {
                input.type = 'password';
                if (icon) { icon.classList.remove('fa-eye-slash'); icon.classList.add('fa-eye'); }
            }
        });
    });
});
