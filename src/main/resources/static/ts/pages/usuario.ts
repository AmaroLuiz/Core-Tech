import {validarUsuario}from "../validations/usuario.js";
import {Usuario} from "../types/usuario.js";
import {login, salvaUsuario} from "../services/usuario.service.js";
import {mostrarErro} from "../validations/erro.js";
import {limparErro} from "../validations/limparErros.js";

const btnLogin = document.querySelector(".btn-entrar") as HTMLButtonElement;

const inputEmail = document.querySelector("#email") as HTMLInputElement;
const erroEmail = document.querySelector(".input-email") as HTMLDivElement;

const inputSenha = document.querySelector("#senha") as HTMLInputElement;
const erroSenha = document.querySelector(".input-senha") as HTMLDivElement;

btnLogin.addEventListener('click', async (e) => {

    e.preventDefault();

    const usuario: Usuario = {
        email: inputEmail.value,
        senha: inputSenha.value
    }

    if (!validarUsuario(usuario)) {
        return;
    }


    if (btnLogin.dataset.action  === "login"){
        try {
            limparErro();

            const entrar = await login(usuario);
            localStorage.setItem("token", entrar); //se der erro coloque entrar.token
            window.location.href = "../../../templates/index.html";

        } catch (error: any) {
            if (error.response?.status === 401) {
                mostrarErro(erroSenha, "Usuario ou senha incorretos");
            }
            if (error.response?.status === 404) {
                mostrarErro(erroSenha, "Usuario não encontrado")
            }
        }
    }

    if (btnLogin.dataset.action === "cadastro"){
        try {

            limparErro();

            const cadastro = await salvaUsuario(usuario);
            window.location.href = "../../../templates/login.html";

        } catch (error: any) {
            if (error.response?.status === 409) {
                mostrarErro(erroSenha, "Usuario já existe");
            } else {
                mostrarErro(erroSenha, "Ocorreu um erro ao realizar o cadastro");
            }
        }
    }

});

