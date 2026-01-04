package br.com.valeoservice.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import br.com.valeoservice.model.Grupo;
import br.com.valeoservice.model.HierarquiaItem;
import br.com.valeoservice.model.HierarquiaItemItem;
import br.com.valeoservice.model.Usuario;
import br.com.valeoservice.model.dao.IGruposDAO;
import br.com.valeoservice.model.dao.IHierarquiaItemDAO;
import br.com.valeoservice.model.dao.mysql.impl.GruposDAO;
import br.com.valeoservice.model.dao.mysql.impl.UsuariosDAO;

@ManagedBean
@SessionScoped
public class HierarquiaBean extends AbstractManagedBean {

	private IHierarquiaItemDAO hierarquiaItemDAO = new br.com.valeoservice.model.dao.mysql.impl.HierarquiaItemDAO();
	private IGruposDAO grupoDAO = new br.com.valeoservice.model.dao.mysql.impl.GruposDAO();
	private List<Grupo> opcaoGrupos;
	private List<Grupo> opcaoGruposNaoSelecionado = new ArrayList<Grupo>();
	private Long grupoSelected;
	private Long grupoSelected2;
	private Long IdHierarquiaCabecalho;

	public HierarquiaBean() {
		super();
		opcaoGrupos = grupoDAO.getAll();

	}

	/*
	 * @PostConstruct public void init() { }
	 */

	private Long idSelected;
	private String descricaoSelecionado;

	List<HierarquiaItemItem> grupos = new ArrayList<HierarquiaItemItem>();

	@PostConstruct
	public void init() {

	}

	private void CarregaGrupos() {
		grupos.clear();
		List<HierarquiaItem> itens = hierarquiaItemDAO.getConsultaItens(this.IdHierarquiaCabecalho);

		if (itens != null) {
			for (int i = 0; i < itens.size(); i++) {
				HierarquiaItem hierarquiaItem = itens.get(i);

				if (hierarquiaItem.getIdHierarquiaItemFilho() != 0)
					grupos.add(new HierarquiaItemItem(hierarquiaItem.getIdHierarquiaItem(),
							new GruposDAO().getById(hierarquiaItem.getIdGrupo()).getNome(),
							hierarquiaItem.getIdHierarquiaItemFilho()));
				else
					grupos.add(new HierarquiaItemItem(hierarquiaItem.getIdHierarquiaItem(),
							new GruposDAO().getById(hierarquiaItem.getIdGrupo()).getNome(), null));
			}
		}

		/*
		 * grupos.add(new HierarquiaItemItem((long) 1, "Fernando Ribeiro", (long)0));
		 * 
		 * grupos.add(new HierarquiaItemItem((long) 2, "Ronaldo Carvalhaes", (long)1));
		 * grupos.add(new HierarquiaItemItem((long) 5, "Sales", (long)2));
		 * grupos.add(new HierarquiaItemItem((long) 6, "Log", (long)2));
		 * 
		 * grupos.add(new HierarquiaItemItem((long) 3, "Eliana Coelho", (long)1));
		 * grupos.add(new HierarquiaItemItem((long) 7, "Financeiro", (long)3));
		 * 
		 * 
		 * grupos.add(new HierarquiaItemItem((long) 4, "Robson Lopes", (long)1));
		 * grupos.add(new HierarquiaItemItem((long) 8, "MKT", (long)4));
		 */
		atualizaNaoSelecionado();
	}

	public List<HierarquiaItemItem> getGrupos() {
		return this.grupos;
	}

	private int i = 9;

	public String getEstrutura() {
		String retorno = "";

		for (int i = 0; i < grupos.size(); i++) {
			HierarquiaItemItem grupo = grupos.get(i);
			String idGrupo = String.valueOf(grupo.getIdGrupo());
			String DescricaoGrupo = "<span style=\"font-size: medium;\">" + grupo.getDescricao() + "</span>";

			DescricaoGrupo += "<br/><a href=\"#\" onclick=\"Novo(" + grupo.getId()
					+ ")\"><img class=\"orgchart-icon\" src=\"/workflow/static/images/down2.png\"/></a>";
			DescricaoGrupo += "<a href=\"#\" onclick=\"Remover(" + grupo.getId()
					+ ")\"><img class=\"orgchart-icon\" src=\"/workflow/static/images/delete.png\"/></a>";

			if (i == 0)
				idGrupo = "";

			retorno += "[{v:'" + grupo.getId() + "', f:'" + DescricaoGrupo + "'},'" + idGrupo + "', '"
					+ grupo.getDescricao() + "'],";
		}
		return "[" + retorno.substring(0, retorno.length() - 1) + "]";
	}

	public Long getIdSelected() {
		return idSelected;
	}

	public void setIdSelected(Long idSelected) {
		this.idSelected = idSelected;
	}

	public String getDescricaoSelecionado() {
		return descricaoSelecionado;
	}

	public void setDescricaoSelecionado(String descricaoSelecionado) {
		this.descricaoSelecionado = descricaoSelecionado;
	}

	public void seleciona() {
		this.idSelected = Long
				.valueOf(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("id"));

	}

	private void atualizaNaoSelecionado() {

		opcaoGruposNaoSelecionado = new ArrayList<Grupo>();
		for (Grupo grupo : opcaoGrupos) {
			boolean aux = true;

			for (HierarquiaItemItem hierarquiaItemItem : grupos) {
				if (hierarquiaItemItem.getDescricao().equals(grupo.getNome())) {
					aux = false;
					break;

				}

			}
			if (aux) {
				opcaoGruposNaoSelecionado.add(grupo);

			}

		}

		if (!getGrupoSession().getAdm()) {
			this.redirecionarPagina("main.xhtml");
		}
	}

	public void adicionar() {

		HierarquiaItem hierarquiaItem = new HierarquiaItem();
		hierarquiaItem.setIdHierarquiaCabecalho(IdHierarquiaCabecalho);
		hierarquiaItem.setIdHierarquiaItemFilho(this.idSelected);
		hierarquiaItem.setIdGrupo(grupoSelected2);

		hierarquiaItemDAO.save(hierarquiaItem);

		CarregaGrupos();

		// grupos.add(new HierarquiaItemItem((long) i++, this.descricaoSelecionado,
		// (long)this.idSelected));
	}

	public void remover() {
		HierarquiaItem item = hierarquiaItemDAO.getById((long) this.idSelected);
		if (item != null)
			hierarquiaItemDAO.delete(item);

		CarregaGrupos();
	}

	public void addMessage(String summary) {
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, null);
		FacesContext.getCurrentInstance().addMessage(null, message);
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

	public Long getGrupoSelected2() {
		return grupoSelected2;
	}

	public void setGrupoSelected2(Long grupoSelected2) {
		this.grupoSelected2 = grupoSelected2;
	}

	public void salvar() {
		HierarquiaItem hierarquiaItem = new HierarquiaItem();
		hierarquiaItem.setIdHierarquiaCabecalho(new br.com.valeoservice.model.dao.mysql.impl.HierarquiaCabecalhoDAO()
				.getById(IdHierarquiaCabecalho).getIdHierarquiaCabecalho());
		hierarquiaItem.setIdGrupo(this.grupoSelected);

		hierarquiaItemDAO.save(hierarquiaItem);

		CarregaGrupos();
	}

	public Long getIdHierarquiaCabecalho() {
		return IdHierarquiaCabecalho;
	}

	public void setIdHierarquiaCabecalho(Long idHierarquiaCabecalho) {
		IdHierarquiaCabecalho = idHierarquiaCabecalho;
		CarregaGrupos();
	}

	public List<Grupo> getOpcaoGruposNaoSelecionado() {
		return opcaoGruposNaoSelecionado;
	}

	public void setOpcaoGruposNaoSelecionado(List<Grupo> opcaoGruposNaoSelecionado) {
		this.opcaoGruposNaoSelecionado = opcaoGruposNaoSelecionado;
	}
}
