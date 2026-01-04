package br.com.valeoservice.controller;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import br.com.valeoservice.model.*;
import br.com.valeoservice.model.dao.mysql.impl.UsuariosDAO;

import java.util.*;

@ManagedBean
@SessionScoped
public class CadastroHierarquiaItemBean extends AbstractManagedBean {
	private UsuariosDAO usuariosDAO = new UsuariosDAO();
	private Usuario usuario = new Usuario();
	
	public CadastroHierarquiaItemBean() {
		super();
		
		
	}
	
	/*@PostConstruct
	public void init() {
	}*/
	
	private int idSelected;
	private String descricaoSelecionado;
	
	List<HierarquiaItemItem> grupos = new ArrayList<HierarquiaItemItem>();
	
	@PostConstruct
	public void init() {
		CarregaGrupos();
	}

	private void CarregaGrupos() {
		grupos.add(new HierarquiaItemItem((long) 1, "Fernando Ribeiro", (long)0));
		
		grupos.add(new HierarquiaItemItem((long) 2, "Ronaldo Carvalhaes", (long)1));
		grupos.add(new HierarquiaItemItem((long) 5, "Sales", (long)2));
		grupos.add(new HierarquiaItemItem((long) 6, "Log", (long)2));
		
		grupos.add(new HierarquiaItemItem((long) 3, "Eliana Coelho", (long)1));
		grupos.add(new HierarquiaItemItem((long) 7, "Financeiro", (long)3));
		
		
		grupos.add(new HierarquiaItemItem((long) 4, "Robson Lopes", (long)1));
		grupos.add(new HierarquiaItemItem((long) 8, "MKT", (long)4));
		
	}
	
	public List<HierarquiaItemItem> getGrupos(){
		return this.grupos;
	}
	
	private int i=9;
	
	public String getEstrutura(){
		String retorno = "";
		
		for (int i = 0; i < grupos.size(); i++) {
			HierarquiaItemItem grupo = grupos.get(i);
			String idGrupo = String.valueOf(grupo.getIdGrupo());
			String DescricaoGrupo = "<span style=\"font-size: medium;\">" + grupo.getDescricao() + "</span>";
			
			DescricaoGrupo += "<br/><a href=\"#\" onclick=\"Novo("+grupo.getId()+")\"><img class=\"orgchart-icon\" src=\"/workflow/static/images/down2.png\"/></a>";
			DescricaoGrupo += "<a href=\"#\" onclick=\"Remover("+grupo.getId()+")\"><img class=\"orgchart-icon\" src=\"/workflow/static/images/delete.png\"/></a>";
			
			
			if(i == 0)
				idGrupo = "";
			
			retorno += "[{v:'"+grupo.getId()+"', f:'"+DescricaoGrupo+"'},'"+idGrupo+"', '"+grupo.getDescricao()+"'],";
		}
		return "[" + retorno.substring(0, retorno.length()-1) + "]";
	}
	
	public int getIdSelected() {
		return idSelected;
	}

	public void setIdSelected(int idSelected) {
		this.idSelected = idSelected;
	}

	public String getDescricaoSelecionado() {
		return descricaoSelecionado;
	}

	public void setDescricaoSelecionado(String descricaoSelecionado) {
		this.descricaoSelecionado = descricaoSelecionado;
	}

	public void seleciona(){
		this.idSelected = Integer.valueOf(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("id"));
	}
	
	public void adicionar(){
		grupos.add(new HierarquiaItemItem((long) i++, this.descricaoSelecionado, (long)this.idSelected));
	}
	
	public void remover(){
		for (int i = 0; i < grupos.size(); i++) {
			if(grupos.get(i).getId() == this.idSelected)
			{
				grupos.remove(i);
				break;
			}
		}
	}
	
    public void addMessage(String summary) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary,  null);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

}
