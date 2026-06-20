import {validarLogin}from "../validations/validarLogin.js";
import {Login} from "../types/usuario.js";
import {mostrarErro} from "../validations/erroDiv.js";
import {limparErro} from "../validations/limparErros.js";
import {
    salvaUsuario, login
} from "../services/usuario.service.js";

const btnLogin = document.querySelector(".btn-entrar") as HTMLButtonElement;

const inputEmail = document.querySelector("#email") as HTMLInputElement;
const erroEmail = document.querySelector(".input-email") as HTMLDivElement;

const inputSenha = document.querySelector("#senha") as HTMLInputElement;
const erroSenha = document.querySelector(".input-senha") as HTMLDivElement;

if (btnLogin) {
    btnLogin.addEventListener('click', async (e) => {

        e.preventDefault();

        const usuario: Login = {
            email: inputEmail.value,
            senha: inputSenha.value
        }

        if (!validarLogin(usuario)) {
            return;
        }


        if (btnLogin.dataset.action  === "login"){
            try {
                limparErro(erroSenha, ".erro-texto");
                limparErro(erroEmail, ".erro-texto")
                const entrar = await login(usuario);
                const tokenFormatado = entrar.startsWith("Bearer ") ? entrar : `Bearer ${entrar}`;
                localStorage.setItem("token", tokenFormatado); //se der erro coloque entrar.token
                window.location.href = "../../../templates/index.html";

            } catch (error: any) {
                if (error.response?.status === 401) {
                    mostrarErro(erroSenha,"Usuario ou senha incorretos");
                }
                if (error.response?.status === 404) {
                    mostrarErro(erroSenha,"Usuario não encontrado")
                }
            }
        }

        if (btnLogin.dataset.action === "cadastro"){
            try {

                limparErro(erroSenha, ".erro-texto");
                limparErro(erroEmail, ".erro-texto")

                const cadastro = await salvaUsuario(usuario);
                window.location.href = "../../../templates/login.html";

            } catch (error: any) {
                if (error.response?.status === 409) {
                    mostrarErro(erroSenha,"Usuario já existe");
                } else {
                    mostrarErro(erroSenha, "Ocorreu um erro ao realizar o cadastro");
                }
            }
        }

    });
}



