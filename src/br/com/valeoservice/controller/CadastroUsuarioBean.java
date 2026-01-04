package br.com.valeoservice.controller;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import br.com.valeoservice.model.*;
import br.com.valeoservice.model.dao.IGruposDAO;

import java.util.*;

@ManagedBean
@SessionScoped
public class CadastroUsuarioBean extends AbstractManagedBean {
	List<Usuario> records = new ArrayList<Usuario>();
	private List<Grupo> opcaoGrupos;
	Boolean novoContato;
	Usuario record = new Usuario();
	private Long grupoSelected;
	br.com.valeoservice.model.dao.IUsuariosDAO usuarioDAO;
	private IGruposDAO grupoDAO = new br.com.valeoservice.model.dao.mysql.impl.GruposDAO();
	
	 
	    public CadastroUsuarioBean() {
		 usuarioDAO = new br.com.valeoservice.model.dao.mysql.impl.UsuariosDAO();

	    }



		private void AtualizaGrid() {
			records = usuarioDAO.getAll();
		}



		public List<Usuario> getRecords() {
			if(!getGrupoSession().getAdm()) {
	    		this.redirecionarPagina("main.xhtml");
	    		return null;
	    	}
			
			 setOpcaoGrupos(grupoDAO.getAll());
				AtualizaGrid();
			return records;
		}



		public void setRecords(List<Usuario> records) {
			this.records = records;
		}



		public Usuario getRecord() {
			return record;
		}



		public void setRecord(Usuario record) {
			this.record = record;
			
			grupoSelected = record.getIdGrupo();
		}



		public Boolean getNovoContato() {
			return novoContato;
		}



		public void setNovoContato(Boolean novoContato) {
			this.novoContato = novoContato;
		}
	
		public List<Grupo> getOpcaoGrupos() {
			return opcaoGrupos;
		}



		public void setOpcaoGrupos(List<Grupo> opcaoGrupos) {
			this.opcaoGrupos = opcaoGrupos;
		}



		public Long getGrupoSelected() {
			return grupoSelected;
		}



		public void setGrupoSelected(Long grupoSelected) {
			this.grupoSelected = grupoSelected;
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
					Usuario aux = new Usuario();
					aux.setDtCriacao(record.getDtCriacao());
					aux.setDtModificacao(record.getDtModificacao());
					aux.seteMail(record.geteMail());
					aux.setIdGrupo(record.getIdGrupo());
					aux.setLogin(record.getLogin());
					aux.setNome(record.getNome());
					aux.setSenha(record.getSenha());
					aux.setIdGrupo(grupoDAO.getById(grupoSelected).getIdGrupo());
					record = usuarioDAO.save(aux);
				}
				else {
					record.setIdGrupo(grupoDAO.getById(grupoSelected).getIdGrupo());
					record = usuarioDAO.update(record);
				}
				AtualizaGrid();
				
				FacesMessage msg = new FacesMessage("Atenção",
						"Usuario Salvo com sucesso.");
				FacesContext.getCurrentInstance().addMessage(null, msg);
				
				org.primefaces.context.RequestContext.getCurrentInstance().execute("PF('dlgUser').hide()");	
			}
			catch (Exception e) {
				
				FacesMessage msg = new FacesMessage("Erro", e.getMessage());
				FacesContext.getCurrentInstance().addMessage(null, msg);
			}			
		}
		
		private boolean ValidaCampos() {
			boolean semErro = true;

			if(record.geteMail() == null || record.geteMail() == "")
			{
				FacesMessage msg = new FacesMessage("Atenção",
						"O campo E-Mail é Obrigatorio");
				FacesContext.getCurrentInstance().addMessage(null, msg);
				semErro = false;
			}
			if(record.getNome() == null || record.getNome() == "")
			{
				FacesMessage msg = new FacesMessage("Atenção",
						"O campo Nome é Obrigatorio");
				FacesContext.getCurrentInstance().addMessage(null, msg);
				semErro = false;
			}
			if(record.getSenha() == null || record.getSenha() == "")
			{
				FacesMessage msg = new FacesMessage("Atenção",
						"O campo Senha é Obrigatorio");
				FacesContext.getCurrentInstance().addMessage(null, msg);
				semErro = false;
			}
			if(grupoSelected == null || grupoSelected == 0)
			{
				FacesMessage msg = new FacesMessage("Atenção",
						"Favor selecionar um Grupo");
				FacesContext.getCurrentInstance().addMessage(null, msg);
				semErro = false;
			}
			
			return semErro;
		}
}
