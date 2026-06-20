document.addEventListener("DOMContentLoaded", () => {
    let btnEndereco = document.querySelector(".btn-endereco");
    let btnTelefone = document.querySelector(".btn-telefone");
    const createUpdateOverlay = (type) => {

        let overlay = document.querySelector(".atualiza-usuario-overlay");


        if (overlay && overlay.dataset.type === type) {
            overlay.remove();
            document.body.style.overflowY = "";
            return;
        }

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
            nome.className = "nome-input";
            const gmail = makeInput('email', 'Gmail');
            gmail.className = "gmail-input";
            const erroGmail = document.createElement("div");
            erroGmail.className = "erro-gmail";
            const enviar = document.createElement("button");
            enviar.className= "btn-enviar-usuario btn-usuario";
            enviar.textContent = "Enviar";
            enviar.style.backgroundColor = "#ff4d00";
            enviar.style.marginTop = "20px";
            enviar.style.width = "100%";
            enviar.style.color = "#black";
            enviar.style.fontSize = "16px";
            enviar.style.padding = "12px 24px";
            enviar.style.borderRadius = "8px";
            enviar.style.cursor = "pointer";
            enviar.style.fontWeight = "bold";

            card.appendChild(titulo);
            card.appendChild(nome);
            card.appendChild(gmail);
            card.appendChild(erroGmail);
            card.appendChild(enviar);
            card.appendChild(fechar);
        } else if (type === 'endereco') {
            if (btnEndereco.dataset.action === "editar") {

                titulo.textContent = "Atualizar Endereço";
            }
            if (btnEndereco.dataset.action === "adicionar") {
                titulo.textContent = "Adicionar Endereço";
            }
            const estado = makeInput('text', 'Estado');
            estado.className = "estado-input";
            const erroEstado = document.createElement("div");
            erroEstado.className = "erro-estado";
            const cep = makeInput('text', 'CEP');
            cep.className = "cep-input";
            const erroCep = document.createElement("div");
            erroCep.className = "erro-cep";
            const cidade = makeInput('text', 'Cidade');
            cidade.className = "cidade-input";
            const erroCidade = document.createElement("div");
            erroCidade.className = "erro-cidade";
            const bairro = makeInput('text', 'Bairro');
            bairro.className = "bairro-input";
            const erroBairro = document.createElement("div");
            erroBairro.className = "erro-bairro";
            const rua = makeInput('text', 'Rua / Logradouro');
            rua.className = "rua-input";
            const erroRua = document.createElement("div");
            erroRua.className = "erro-rua";
            const numero = makeInput('text', 'Número');
            numero.className = "numero-input";
            const erroNumero = document.createElement("div");
            erroNumero.className = "erro-numero";
            const enviar = document.createElement("button");
            enviar.textContent = "Enviar";
            enviar.className= "btn-enviar-endereco";
            enviar.style.backgroundColor = "#ff4d00";
            enviar.style.marginTop = "20px";
            enviar.style.width = "100%";
            enviar.style.color = "#black";
            enviar.style.fontSize = "16px";
            enviar.style.padding = "12px 24px";
            enviar.style.borderRadius = "8px";
            enviar.style.cursor = "pointer";
            enviar.style.fontWeight = "bold";

            card.appendChild(titulo);
            card.appendChild(estado);
            card.appendChild(erroEstado);
            card.appendChild(cep);
            card.appendChild(erroCep);
            card.appendChild(cidade);
            card.appendChild(erroCidade);
            card.appendChild(bairro);
            card.appendChild(erroBairro);
            card.appendChild(rua);
            card.appendChild(erroRua);
            card.appendChild(numero);
            card.appendChild(erroNumero);
            card.appendChild(enviar);
            card.appendChild(fechar);
            
        } else if (type === 'telefone') {
            if (btnTelefone.dataset.action === "editar") {
                titulo.textContent = "Atualizar Telefone";
            }
            if (btnTelefone.dataset.action === "adicionar") {
                titulo.textContent = "Adicionar Telefone";
            }

            const ddd = makeInput('tel', 'DDD');
            ddd.className = "ddd-input";
            const erroDdd = document.createElement("div");
            erroDdd.className = "erro-ddd";
            const tel = makeInput('tel', 'Telefone');
            tel.className = "telefone-input";
            const erroTel = document.createElement("div");
            erroTel.className = "erro-telefone";
            const enviar = document.createElement("button");
            enviar.textContent = "Enviar";
            enviar.style.backgroundColor = "#ff4d00";
            enviar.className = "btn-enviar-telefone";
            enviar.style.marginTop = "20px";
            enviar.style.width = "100%";
            enviar.style.color = "#black";
            enviar.style.fontSize = "16px";
            enviar.style.padding = "12px 24px";
            enviar.style.borderRadius = "8px";
            enviar.style.cursor = "pointer";
            enviar.style.fontWeight = "bold";
            card.appendChild(titulo);
            card.appendChild(ddd);
            card.appendChild(erroDdd);
            card.appendChild(tel);
            card.appendChild(erroTel);
            card.appendChild(enviar);
            card.appendChild(fechar);
        }

        overlay.appendChild(card);
        document.body.appendChild(overlay);
        document.body.style.overflowY = "hidden";
    };

    const btn = document.querySelector(".btn-menu");
    if (btn) {
        btn.addEventListener("click", () => createUpdateOverlay('usuario'));
    }

    const atualizarButtons = document.querySelectorAll('.btn-atualizar');
    atualizarButtons.forEach(b => {
        b.addEventListener('click', (evt) => {
            // tenta inferir tipo pelo ancestor ou pela presença de classes
            const parent = b.closest('.endereco-card') ? 'endereco' : (b.closest('.telefone-card') ? 'telefone' : 'usuario');
            createUpdateOverlay(parent);
        });
    });
});