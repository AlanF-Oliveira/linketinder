import 'bootstrap/dist/css/bootstrap.min.css';
import { CandidatoService } from './services/CandidatoService';
import { EmpresaService } from './services/EmpresaService';
import { VagaService } from './services/VagaService';
import { candidatos } from './data/candidatos';
import { empresas } from './data/empresas';
import { criarTelaInicial } from './views/home';
import { vagas } from './data/vagas';
import { recrutadores } from './data/recrutadores';
import { RecrutadorService } from './services/RecrutadorService';
import { AuthService } from './services/AuthService';

const candidatoService = new CandidatoService(candidatos);
const empresaService = new EmpresaService(empresas);
const vagaService = new VagaService(vagas);
const recrutadorService = new RecrutadorService(recrutadores);
const authService = new AuthService(candidatos, empresas, recrutadores);

const app = document.getElementById('app') as HTMLElement;
criarTelaInicial(app, candidatoService, empresaService, vagaService, authService, recrutadorService);
