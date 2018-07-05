package br.com.sgpa.business;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.sgpa.dao.PessoaDao;
import br.com.sgpa.entity.Pessoa;
import br.com.sgpa.utils.exception.DaoException;
import br.com.sgpa.utils.exception.ServiceException;

@Service("pessoaBusiness")
public class PessoaBusiness {

	@Autowired
	private PessoaDao pessoaDao;
	
	private List<String> erros;
	
	@Transactional(value = "transactionManager")
	public List<Pessoa> listaPessoas(String tipo) throws ServiceException {
		List<Pessoa> listaPessoa = new ArrayList<Pessoa>();
		listaPessoa = pessoaDao.listaPessoa(tipo);
		return listaPessoa;
	}
	
	@Transactional(value = "transactionManager")
	public Pessoa pesquisarPorId(int idTipoPessoa) throws ServiceException {
		Pessoa pessoa = new Pessoa();
		pessoa = pessoaDao.pesquisarPorId(idTipoPessoa);
		validaRetorno(pessoa);
		return pessoa;
	}
	
	@Transactional(value = "transactionManager")
	public Pessoa pesquisarLogin(String login, String senha) throws ServiceException {
		return pessoaDao.pesquisarLogin(login, senha);
	}

	private void validaRetorno(Object obj) throws ServiceException {
		if (obj == null) {
			throw new ServiceException("Tipo(s) Pessoa não encontrado(s)");
		}

	}

	@Transactional(value = "transactionManager", rollbackFor = { Exception.class, DaoException.class })
	public Pessoa salvar(Pessoa pessoa) throws DaoException, ServiceException {
		validaPessoa(pessoa);
		pessoaDao.persistirEntidade(pessoa);
		return pessoa;
	}
	
	@Transactional(value = "transactionManager")
	public Pessoa atualizar(Pessoa pessoa) throws DaoException, ServiceException {
		if (pessoa.getId() == null) {
			throw new ServiceException("Pessoa não pode ser alterada!");
		}
		validaPessoa(pessoa);
		pessoaDao.updateEntidade(pessoa);
		return pessoa;
	}
	
	private void validaPessoa(Pessoa pessoa) throws ServiceException, DaoException {
		erros = new ArrayList<String>();
//		if (pessoa == null) {
//			erros.add("Desculpe, a pessoa não pode ser identificada.");
//		}
//
//		if (pessoa.getNomCrianca() == null || pessoa.getNomCrianca().trim().isEmpty()) {
//			erros.add("Desculpe, o campo nome não pode ficar vazio.");
//		}
//		
//		if (pessoa.getNumProcessoCrianca() == null) {
//			erros.add(Utils.getPropriedadeMessage("erro.informa.numero.crianca"));
//		} else {
//		    Utils.validarProcesso(pessoa.getNumProcessoCrianca(),EnumTipoProcesso.C);
//		}
//		
//		if (pessoa.getDatNascimento() == null) {
//			erros.add("Por favor informe a data de nascimento da criança.");
//		}else{
//			//VERIFICA SE A DATA DIGITADA E MAIOR QUE A DATA ATUAL
//			if (pessoa.getDatNascimento().after(new Date())){
//				erros.add("A data de nascimento informada é maior que a data atual ("+Utils.FORMATADORORACLE.format(new Date())+").");
//			}	
//			//VERIFICA SE A CRIANCA E MAIOR DE 18 ANOS
//			Integer idade = Utils.calculaAnoEntreDatas(pessoa.getDatNascimento(), new Date()) ;
//			if (idade>= 18 ){
//				erros.add("Criança maior de idade");
//			}		
//		}
//		if (pessoa.getSituacaoPessoa() == null) {
//			erros.add("Por favor selecione uma situação para a criança.");
//		}else{
//			//validação para o webservice
//			if(pessoa.getId()!=null){
//				if(pessoa.getSituacaoPessoa().getId().equals(EnumTipoSituacaoCrianca.ADOTADA.getValor())){
//					erros.add("Criança já foi adotada, não pode ser alterada");
//				}			
//			}
//		}
//		if (pessoa.getRacaCorEntity() == null || pessoa.getRacaCorEntity().getId() == null || pessoa.getRacaCorEntity().getId().equals(0)) {
//			erros.add("Por favor selecione uma raça/cor para a criança.");
//		}
//		if (pessoa.getUfEntity() == null) {
//			erros.add("Por favor selecione uma UF de localização para a criança.");
//		}
//
//		if (pessoa.getTipSexo() == null || pessoa.getTipSexo().trim().equals("")) {
//			erros.add("Por favor selecione o sexo da criança.");
//		} else {
//		    validarCampoSexo(pessoa.getTipSexo());
//		}
//
//		if (pessoa.getFlgIrmao() == null || pessoa.getFlgIrmao().trim().equals("")) {
//			erros.add("Por favor selecione sim ou não para informar se a criança possui irmãos.");
//		} else {
//		    validarCampoFlg(pessoa.getFlgIrmao(), "possui irmãos");
//		}
//
//		if (pessoa.getFlgIrmaoGemeo() == null || pessoa.getFlgIrmaoGemeo().trim().equals("")) {
//			erros.add("Por favor selecione sim ou não para informar se a criança tem irmãos gemeos.");
//		} else {
//		    validarCampoFlg(pessoa.getFlgIrmaoGemeo(), "possui irmãos gemeos");
//		}
//
//		if (pessoa.getSeqOrgao() == null || pessoa.getSeqOrgao().equals(0)) {
//			erros.add("Por favor informe o código da vara");
//		} else {
//
//		    CorporativoOrgao vara = corp.pesquisaPorId(pessoa.getSeqOrgao());
//		    if (!vara.getTipoOrgao().getId().equals("VARAE")) {
//		    	erros.add("O órgão informado não é uma vara");
//		    }
//
//		    String uf = corp.pesquisaUfPorSeqVara(pessoa.getSeqOrgao());
//
//		    if (!pessoa.getUfEntity().getId().equals(uf)) {
//		    	erros.add("O órgão informado pertence ao estado " + uf + ". Por favor informe uma vara do seguinte estado: " + pessoa.getUfEntity().getId());
//		    }
//		}
//		
//		if(pessoa.getTipoDoencaEntities() != null && pessoa.getTipoDoencaEntities().size() < 1){
//		    List<TipoDoencaEntity> doencaNaoDetectada =   new ArrayList<TipoDoencaEntity>();
//		    doencaNaoDetectada.add(tipoDoencaBusiness.pesquisarPorId(Utils.SAUDAVEL));
//		    pessoa.setTipoDoencaEntities(doencaNaoDetectada);
//		}
//		
		if(erros.size()>0){
			throw new ServiceException(erros);
		}

	    }

	public List<String> getErros() {
		return erros;
	}

	public void setErros(List<String> erros) {
		this.erros = erros;
	}

}
