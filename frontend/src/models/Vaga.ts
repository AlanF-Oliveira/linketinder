import { Empresa } from "./Empresa";
export class Vaga {

    private static nextId: number = 1;

    id: number;
    titulo: string;
    descricao: string;
    competenciasExigidas: string[];
    empresa: Empresa;

    constructor(titulo: string, descricao: string, competenciasExigidas: string[], empresa: Empresa) {
        this.id = Vaga.nextId++
        this.titulo = titulo;
        this.descricao = descricao;
        this.competenciasExigidas = competenciasExigidas;
        this.empresa = empresa;
    }

}