import 'bootstrap/dist/css/bootstrap.min.css';
import { CandidatoService } from './services/CandidatoService';
import { EmpresaService } from './services/EmpresaService';
import { VagaService } from './services/VagaService';
import { candidatos } from './data/candidatos';
import { empresas } from './data/empresas';
import { criarTelaInicial } from './views/home';

const candidatoService = new CandidatoService(candidatos);
const empresaService = new EmpresaService(empresas);
const vagaService = new VagaService([]);

const app = document.getElementById('app') as HTMLElement;
criarTelaInicial(app, candidatoService, empresaService, vagaService);