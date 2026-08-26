import { Empresa } from "./Empresa";
export class Vaga {

    titulo: string;
    descricao: string;
    competenciasExigidas: string[];
    empresa: Empresa;

    constructor(titulo: string, descricao: string, competenciasExigidas: string[], empresa: Empresa) {
        this.titulo = titulo;
        this.descricao = descricao;
        this.competenciasExigidas = competenciasExigidas;
        this.empresa = empresa;
    }

}