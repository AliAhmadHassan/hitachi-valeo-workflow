package br.com.valeoservice.controller;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import br.com.valeoservice.model.*;

import java.util.*;

@ManagedBean
@SessionScoped
public class CadastroHierarquiaCabecalhoBean extends AbstractManagedBean {
	List<HierarquiaCabecalho> records = new ArrayList<HierarquiaCabecalho>();
	Boolean novoRegistro;
	HierarquiaCabecalho record = new HierarquiaCabecalho();
	br.com.valeoservice.model.dao.IHierarquiaCabecalhoDAO HierarquiaCabecalhoDAO;
	
	
	 
	    public CadastroHierarquiaCabecalhoBean() {
		 HierarquiaCabecalhoDAO = new br.com.valeoservice.model.dao.mysql.impl.HierarquiaCabecalhoDAO();
			
			
	    }



		private void AtualizaGrid() {
			records = HierarquiaCabecalhoDAO.getAll();
		}



		public List<HierarquiaCabecalho> getRecords() {
			if(!getGrupoSession().getAdm()) {
	    		this.redirecionarPagina("main.xhtml");
	    		return null;
	    	}
			
			AtualizaGrid();
			return records;
		}



		public void setRecords(List<HierarquiaCabecalho> records) {
			this.records = records;
		}



		public HierarquiaCabecalho getRecord() {
			return record;
		}



		public void setRecord(HierarquiaCabecalho record) {
			this.record = record;
		}



	
	
		public Boolean getNovoRegistro() {
			return novoRegistro;
		}



		public void setNovoRegistro(Boolean novoRegistro) {
			this.novoRegistro = novoRegistro;
		}



		public void salvar() {
			
			if (!ValidaCampos())
				return;
			
			if (record.getDtCriacao() == null)
				record.setDtCriacao(new java.sql.Date(Calendar.getInstance().getTimeInMillis()));
			
			record.setDtModificacao(new java.sql.Date(Calendar.getInstance().getTimeInMillis()));
			
			record.setIdUsuario(getUsuarioSession().getIdUsuario());
			
			try {
				if(novoRegistro)
				{
					HierarquiaCabecalho aux = new HierarquiaCabecalho();
					aux.setNome(record.getNome());
					aux.setDtCriacao(record.getDtCriacao());
					aux.setCodSAP(record.getCodSAP());
					aux.setDtModificacao(record.getDtModificacao());
					aux.setIdUsuario(record.getIdUsuario());							
					record = HierarquiaCabecalhoDAO.save(aux);
				}
				else
					record = HierarquiaCabecalhoDAO.update(record);
				
				AtualizaGrid();
				
				FacesMessage msg = new FacesMessage("Atenção",
						"Hierarquia Salvo com sucesso.");
				FacesContext.getCurrentInstance().addMessage(null, msg);
				
				org.primefaces.context.RequestContext.getCurrentInstance().execute("PF('dlgHierarquiaCabecalho').hide()");	
			}
			catch (Exception e) {
				
				FacesMessage msg = new FacesMessage("Erro", e.getMessage());
				FacesContext.getCurrentInstance().addMessage(null, msg);
			}			
		}
		
		private boolean ValidaCampos() {
			boolean semErro = true;

			if(record.getNome() == null || record.getNome() == "")
			{
				FacesMessage msg = new FacesMessage("Atenção",
						"O campo Nome é Obrigatorio");
				FacesContext.getCurrentInstance().addMessage(null, msg);
				semErro = false;
			}
			return semErro;
		}
}
