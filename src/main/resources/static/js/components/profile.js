document.addEventListener("DOMContentLoaded", () => {
    const createUpdateOverlay = (type) => {
        // type: 'usuario' | 'endereco' | 'telefone'
        let overlay = document.querySelector(".atualiza-usuario-overlay");

        // toggle: if same type is open, close it
        if (overlay && overlay.dataset.type === type) {
            overlay.remove();
            document.body.style.overflowY = "";
            return;
        }

        // remove existing different overlay
        if (overlay) overlay.remove();

        overlay = document.createElement("div");
        overlay.className = "atualiza-usuario-overlay";
        overlay.dataset.type = type;

        overlay.style.position = "fixed";
        overlay.style.top = "0";
        overlay.style.left = "0";
        overlay.style.width = "100vw";
        overlay.style.height = "100vh";
        overlay.style.backgroundColor = "rgba(0, 0, 0, 0.85)";
        overlay.style.display = "flex";
        overlay.style.justifyContent = "center";
        overlay.style.alignItems = "center";
        overlay.style.zIndex = "9999";

        const card = document.createElement("div");
        card.style.width = "500px";
        card.style.maxWidth = "90%";
        card.style.backgroundColor = "#000000";
        card.style.borderRadius = "12px";
        card.style.padding = "32px";
        card.style.boxShadow = "0 10px 30px rgba(0,0,0,0.3)";

        const titulo = document.createElement("h2");
        titulo.style.textAlign = "center";
        titulo.style.marginBottom = "24px";
        titulo.style.color = "#fff";
        titulo.style.fontSize = "28px";
        titulo.style.fontWeight = "600";

        const fechar = document.createElement("button");
        fechar.textContent = "Sair";
        fechar.style.marginTop = "20px";
        fechar.style.color = "#ff4d00"
        fechar.addEventListener("click", () => {
            overlay.remove();
            document.body.style.overflowY = "";
        });

        const makeInput = (typeAttr, placeholder) => {
            const el = document.createElement("input");
            el.type = typeAttr;
            el.placeholder = placeholder;
            el.style.width = "100%";
            el.style.padding = "12px 16px";
            el.style.marginBottom = "12px";
            el.style.border = "1px solid #333";
            el.style.borderRadius = "10px";
            el.style.backgroundColor = "#1a1a1a";
            el.style.color = "#fff";
            el.style.fontSize = "14px";
            el.style.outline = "none";
            el.style.boxSizing = "border-box";
            return el;
        };

        if (type === 'usuario') {
            titulo.textContent = "Atualizar Dados";
            const nome = makeInput('text', 'Nome');
            const gmail = makeInput('email', 'Gmail');
            card.appendChild(titulo);
            card.appendChild(nome);
            card.appendChild(gmail);
            card.appendChild(fechar);
        } else if (type === 'endereco') {
            titulo.textContent = "Atualizar Endereço";
            const cidade = makeInput('text', 'Cidade');
            const bairro = makeInput('text', 'Bairro');
            const rua = makeInput('text', 'Rua / Logradouro');
            const numero = makeInput('text', 'Número');

            card.appendChild(titulo);
            card.appendChild(cidade);
            card.appendChild(bairro);
            card.appendChild(rua);
            card.appendChild(numero);
            card.appendChild(fechar);
            
        } else if (type === 'telefone') {
            titulo.textContent = "Atualizar Telefone";
            const tel1 = makeInput('tel', 'Telefone principal');
            const tel2 = makeInput('tel', 'Telefone secundário (opcional)');
            card.appendChild(titulo);
            card.appendChild(tel1);
            card.appendChild(tel2);
            card.appendChild(fechar);
        }

        overlay.appendChild(card);
        document.body.appendChild(overlay);
        document.body.style.overflowY = "hidden";
    };

    // listener para o botão de menu (dados do usuário)
    const btn = document.querySelector(".btn-menu");
    if (btn) {
        btn.addEventListener("click", () => createUpdateOverlay('usuario'));
    }

    // listeners para botões de atualizar (endereço e telefone). Usa atributo data-target opcional
    const atualizarButtons = document.querySelectorAll('.btn-atualizar');
    atualizarButtons.forEach(b => {
        b.addEventListener('click', (evt) => {
            // tenta inferir tipo pelo ancestor ou pela presença de classes
            const parent = b.closest('.endereco-card') ? 'endereco' : (b.closest('.telefone-card') ? 'telefone' : 'usuario');
            createUpdateOverlay(parent);
        });
    });
});