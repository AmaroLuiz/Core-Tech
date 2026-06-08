document.addEventListener('DOMContentLoaded', function () {
    const signup = document.getElementById('btnCadastrar');
    const split = document.querySelector('.cartao-dividido');
    if (!signup || !split) return;

    const title = document.querySelector('.titulo-entrar');
    const sub = document.querySelector('.subtitulo');
    const submitBtn = document.querySelector('.btn-entrar');

    const texts = {
        login: {
            title: 'Fazer Login',
            sub: 'Faça login com seu e-mail e senha',
            submit: 'ENTRAR',
            promoTitle: 'Bem vindo de volta!',
            promoText: 'Cadastre-se agora e desfrute do nosso site',
            signupLabel: 'CADASTRAR'
        },
        register: {
            title: 'Criar Conta',
            sub: 'Cadastre-se usando seu e-mail e senha',
            submit: 'CADASTRAR',
            promoTitle: 'Bem-vindo!',
            promoText: 'Já tem uma conta? Faça login agora',
            signupLabel: 'ENTRAR'
        }
    };

    // elemento do painel direito
    const promoTitleEl = document.querySelector('.painel-direito .promocao h3');
    const promoTextEl = document.querySelector('.painel-direito .promocao p');

    signup.addEventListener('click', function (e) {
        e.preventDefault();

        if (window.matchMedia && window.matchMedia('(max-width:768px)').matches) {
            window.location.href = 'register.html';
            return;
        }

        const isActive = split.classList.contains('cartao-ativo');

        if (!isActive) {
            // passa para modo cadastro
            split.classList.add('cartao-ativo');

            // trocar textos com pequeno delay para acompanhar a animação
            setTimeout(function () {
                if (title) title.textContent = texts.register.title;
                if (sub) sub.textContent = texts.register.sub;
                if (submitBtn) submitBtn.textContent = texts.register.submit;
                if (promoTitleEl) promoTitleEl.textContent = texts.register.promoTitle;
                if (promoTextEl) promoTextEl.textContent = texts.register.promoText;
                signup.textContent = texts.register.signupLabel;
            }, 220);

        } else {
            // volta para login
            split.classList.remove('cartao-ativo');

            setTimeout(function () {
                if (title) title.textContent = texts.login.title;
                if (sub) sub.textContent = texts.login.sub;
                if (submitBtn) submitBtn.textContent = texts.login.submit;
                if (promoTitleEl) promoTitleEl.textContent = texts.login.promoTitle;
                if (promoTextEl) promoTextEl.textContent = texts.login.promoText;
                signup.textContent = texts.login.signupLabel;
            }, 220);
        }
    });
});
