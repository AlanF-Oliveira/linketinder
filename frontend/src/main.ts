import 'bootstrap/dist/css/bootstrap.min.css';
import { CandidatoService } from './services/CandidatoService';
import { candidatos } from './data/candidatos';
import { criarCadastroCandidato } from './views/cadastrarCandidato';
import { EmpresaService } from './services/EmpresaService';
import { empresas } from './data/empresas';
import { criarCadastroEmpresa } from './views/cadastrarEmpresa';

const candidatoService = new CandidatoService(candidatos); 
const app = document.getElementById('app') as HTMLElement;
criarCadastroCandidato(app, candidatoService);

const empresaService = new EmpresaService(empresas)
criarCadastroEmpresa(app, empresaService);
