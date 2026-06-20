import {api} from "../services/api.js"
import {Usuario} from "../types/usuario.js"
import {coletarEndereco} from "./endereco.js";
import {coletarTelefone} from "./telefone.js";
import {
    atualizarEndereco,
    atualizarTelefone, atualizarUsuario,
    salvarEndereco,
    salvarTelefone
} from "../services/usuario.service.js";
import {validarTefone} from "../validations/validarTelefone.js";
import {validarEndereco} from "../validations/validarEndereco.js";
import {validarUsuario} from "../validations/validarUsuario.js";

let nome = document.querySelector("#nome") as HTMLElement;
let email = document.querySelector("#email") as HTMLElement;


let numeroUsuario = document.querySelector("#numero-rua-usuario") as HTMLElement;
let ruaUsuario = document.querySelector("#rua-usuario") as HTMLElement;

let btnEndereco = document.querySelector(".btn-endereco") as HTMLElement;

let ruaEndereco = document.querySelector("#rua-endereco") as HTMLElement;
let numeroEndereco = document.querySelector("#numero-rua-endereco") as HTMLElement;
let rua = document.querySelectorAll("#rua-endereco") as NodeListOf<HTMLElement>;
let cidade = document.querySelector("#cidade") as HTMLElement;
let bairro = document.querySelector("#bairro") as HTMLElement;

let btnTelefone = document.querySelector(".btn-telefone") as HTMLElement;

let telefone = document.querySelector("#telefone") as HTMLElement;
let ddd = document.querySelector("#ddd") as HTMLElement;

const sair = document.querySelector("#sair") as HTMLElement;



const dadosUsuario = await api.get("/usuario/me")
const usuario: Usuario = dadosUsuario.data;



export async function preencherDadosUsuario(usuario: Usuario) {
    if (nome != null){
        nome.textContent = usuario.nome;
    }
    if (usuario.nome === null || usuario.nome.length === 0){
        nome.textContent = "Nome não cadastrado";
    }
    if (email != null){
        email.textContent = usuario.email;
    }
    if (usuario.enderecos == null || usuario.enderecos.length === 0) {
        btnEndereco.textContent = "Adicionar"
        ruaUsuario.textContent = "Rua não cadastrado";
        numeroUsuario.textContent = "Número não cadastrado";

    }
    else{
        for (const endereco of usuario.enderecos){

            if ( endereco.rua != null && endereco.rua.length > 0
                && endereco.numero != null &&  endereco.numero > 0) {
                ruaUsuario.textContent = endereco.rua;
                numeroUsuario.textContent = endereco.numero.toString() ;
            } else {
                ruaUsuario.textContent = "Rua não cadastrado";
                numeroUsuario.textContent = "Numero não cadastrado";
            }
        }
    }
}
export async function preencherEndereco(usuario: Usuario) {
    if (usuario.enderecos == null || usuario.enderecos.length === 0 ){
        btnEndereco.textContent = "Adicionar"
        btnEndereco.dataset.action = "adicionar";
        ruaEndereco.textContent = "Rua não cadastrada";
        numeroEndereco.textContent = "Número não cadastrado";
        bairro.textContent = "bairro não cadastrado";
        cidade.textContent = "cidade não cadastrada";
    } else {
        for (let endereco of usuario.enderecos) {
            if (endereco.rua != null && endereco.numero != null && endereco.complemento != null
                && endereco.cidade != null && endereco.estado != null && endereco.cep != null) {
                btnEndereco.textContent = "Editar"
                btnEndereco.dataset.action = "editar";
                btnEndereco.dataset.id = endereco.id?.toString();
            }
            if (endereco.estado != null){

            }
            if (endereco.cep != null){

            }
            if (endereco.rua != null){
                ruaEndereco.textContent = endereco.rua;
            }
            if (endereco.cidade != null){
                cidade.textContent = endereco.cidade;
            }
            if (endereco.numero != null) {
                numeroEndereco.textContent = endereco.numero.toString();
            }
            if (endereco.complemento != null) {
                bairro.textContent = endereco.complemento;
            }
            else{
                btnEndereco.textContent = "Adicionar"
                btnEndereco.dataset.action = "adicionar";
                ruaEndereco.textContent = "Rua não cadastrada";
                numeroEndereco.textContent = "Número não cadastrado";
                bairro.textContent = "bairro não cadastrado";
                cidade.textContent = "cidade não cadastrada";
            }
        }
    }

}

export async function preencherTelefone(usuario: Usuario) {
    if (usuario.telefones == null || usuario.telefones.length === 0) {
        btnTelefone.textContent = "Adicionar"
        btnTelefone.dataset.action = "adicionar";
        telefone.textContent = "Telefone não cadastrado";
        ddd.textContent = "DDD não cadastrado";
    } else {
        for (const telefoneObject of usuario.telefones) {
            if (telefoneObject.ddd != null && telefoneObject.telefone != null) {
                btnTelefone.textContent = "Editar"
                btnTelefone.dataset.action = "editar";
                btnTelefone.dataset.id = telefoneObject.id?.toString();
            }
            if (telefoneObject.ddd != null) {
                ddd.textContent = telefoneObject.ddd;
            }
            if (telefoneObject.telefone != null) {
                telefone.textContent = telefoneObject.telefone;
            } else {
                btnTelefone.textContent = "Adicionar"
                btnTelefone.dataset.action = "adicionar";
                telefone.textContent = "Telefone não cadastrado";
                ddd.textContent = "DDD não cadastrado";
            }
        }
    }
}

preencherDadosUsuario(usuario);
preencherEndereco(usuario);
preencherTelefone(usuario);

email.textContent = usuario.email;

console.log(usuario);




document.addEventListener('click', async (e) => {
    const target = e.target as HTMLElement;

    if (target.classList.contains('btn-enviar-usuario')) {
        const btnUsuario = document.querySelector(".btn-usuario") as HTMLElement;

        let nome = document.querySelector(".nome-input") as HTMLInputElement;
        let email:HTMLInputElement | null = document.querySelector(".gmail-input") as HTMLInputElement;

        const usuarioId = Number(usuario.id);

        await atualizarUsuario(
            nome.value,
            email.value, usuarioId
        );

        location.reload();


    }

    if (target.classList.contains('btn-enviar-telefone')) {
        const telefone = coletarTelefone();
        const telefoneId = Number(btnTelefone.dataset.id);

        if (!validarTefone(telefone)) return;

        if (btnTelefone.dataset.action === "editar") {

            await atualizarTelefone(telefone, telefoneId);

        } else {
            await salvarTelefone(telefone);
        }
        location.reload();
    }

    if (target.classList.contains('btn-enviar-endereco')) {
        const endereco = coletarEndereco();
        const enderecoId = Number(btnEndereco.dataset.id);


        if (btnEndereco.dataset.action === "editar") {
            await atualizarEndereco(endereco, enderecoId);
        } else {
            if (validarEndereco(endereco)) {
                await salvarEndereco(endereco);
            }
        }
        location.reload();
    }
});





sair.addEventListener('click', async (e) => {
    localStorage.clear();
})




