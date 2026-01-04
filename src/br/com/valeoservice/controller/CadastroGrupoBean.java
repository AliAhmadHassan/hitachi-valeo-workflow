package br.com.valeoservice.controller;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;

import br.com.valeoservice.model.*;

import java.util.*;

@ManagedBean
@SessionScoped
public class CadastroGrupoBean extends AbstractManagedBean {
	List<Grupo> records = new ArrayList<Grupo>();
	Boolean novoContato;
	Grupo record = new Grupo();
	br.com.valeoservice.model.dao.IGruposDAO grupoDAO;
	
	
	 
	    public CadastroGrupoBean() {
	    	
	    	
		 grupoDAO = new br.com.valeoservice.model.dao.mysql.impl.GruposDAO();
			
			AtualizaGrid();
	    }



		private void AtualizaGrid() {
			records.clear();
			records = grupoDAO.getAll();
		}



		public List<Grupo> getRecords() {
			if(!getGrupoSession().getAdm()) {
	    		this.redirecionarPagina("main.xhtml");
	    		return null;
	    	}
			return records;
		}



		public void setRecords(List<Grupo> records) {
			this.records = records;
		}



		public Grupo getRecord() {
			return record;
		}



		public void setRecord(Grupo record) {
			this.record = record;
		}



		public Boolean getNovoContato() {
			return novoContato;
		}



		public void setNovoContato(Boolean novoContato) {
			this.novoContato = novoContato;
			if(this.novoContato)
				this.record = new Grupo();
		}
	
		public void salvar() {
			
			if (!ValidaCampos())
				return;
			
			if (record.getDtCriacao() == null)
				record.setDtCriacao(new java.sql.Date(Calendar.getInstance().getTimeInMillis()));
			
			record.setDtModificacao(new java.sql.Date(Calendar.getInstance().getTimeInMillis()));
			
			try {
				if(novoContato)
				{
					
					record = grupoDAO.save(record);
				}
				else
					record = grupoDAO.update(record);
				
				AtualizaGrid();
				
				FacesMessage msg = new FacesMessage("Atenção",
						"Grupo Salvo com sucesso.");
				FacesContext.getCurrentInstance().addMessage(null, msg);
				
				org.primefaces.context.RequestContext.getCurrentInstance().execute("PF('dlgGrupo').hide()");	
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
