package br.com.valeoservice.controller;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import br.com.valeoservice.model.*;
import br.com.valeoservice.model.dao.mysql.impl.HierarquiaItemDAO;
import br.com.valeoservice.model.dao.mysql.impl.PedidoDAO;

import java.util.*;

@ManagedBean
@SessionScoped
public class PedidosBean extends AbstractManagedBean {
	List<Pedido> records = new ArrayList<Pedido>();
	Pedido record = new Pedido();
	PedidoAprovacao pedidoAprovacaoAtual;
	HierarquiaItem proximoHierarquiaItem = null;
	HierarquiaItem atualHierarquiaItem;
	br.com.valeoservice.model.dao.IPedidoDAO pedidoDAO;
	br.com.valeoservice.model.dao.IPedidoAprovacaoDAO pedidoAprovacaoDAO;
	br.com.valeoservice.model.dao.IHierarquiaItemDAO hierarquiaItemDAO;
	private Date agora;
	private boolean panelIsVisible;

	public PedidosBean() {
		pedidoDAO = new br.com.valeoservice.model.dao.mysql.impl.PedidoDAO();
		pedidoAprovacaoDAO = new br.com.valeoservice.model.dao.mysql.impl.PedidoAprovacaoDAO();
		hierarquiaItemDAO = new br.com.valeoservice.model.dao.mysql.impl.HierarquiaItemDAO();

		agora = new Date();
		panelIsVisible = true;
		records = new ArrayList<Pedido>();
		AtualizaGrid();
	}

	private void AtualizaGrid() {
		if(records == null)
			records = new ArrayList<>();
		records.clear();
		records = pedidoDAO.getParaAprovar(getGrupoSession().getIdGrupo());
	}

	public List<Pedido> getRecords() {
		AtualizaGrid();
		return records;
	}

	public void setRecords(List<Pedido> records) {
		this.records = records;
	}

	public Pedido getRecord() {
		return record;
	}

	public void setRecord(Pedido record) {
		this.record = record;

		List<HierarquiaItem> listHierarquiaItem = hierarquiaItemDAO
				.getConsultaItens(record.getIdHierarquiaCabecalho());

		for (HierarquiaItem hierarquiaItem : listHierarquiaItem) {
			if (hierarquiaItem.getIdGrupo().equals(getGrupoSession().getIdGrupo())) {
				atualHierarquiaItem = hierarquiaItem;
				break;
			}
		}

		if (atualHierarquiaItem.getIdHierarquiaItem() != null)
			proximoHierarquiaItem = new HierarquiaItemDAO().getById(atualHierarquiaItem.getIdHierarquiaItemFilho());

		pedidoAprovacaoAtual = pedidoAprovacaoDAO.getPedidoAprovacao(record.getIdPedido(),
				atualHierarquiaItem.getIdHierarquiaItem());
	}

	public Date getAgora() {
		return agora;
	}

	public void setAgora(Date agora) {
		this.agora = agora;
	}

	public boolean isPanelIsVisible() {
		return panelIsVisible;
	}

	public void setPanelIsVisible(boolean panelIsVisible) {
		this.panelIsVisible = panelIsVisible;
	}

	public void aprovado() {

		if (proximoHierarquiaItem == null) {
			Pedido pedido = pedidoDAO.getById(record.getIdPedido());

			pedido.setAprovado(true);
			pedido.setHierarquiaFinalizada(true);

			pedidoDAO.update(pedido);

			panelIsVisible = false;
		} else {
			PedidoAprovacao pedidoAprovacao = new PedidoAprovacao();
			pedidoAprovacao.setAprovado(false);
			pedidoAprovacao.seteMailEnviado(false);
			pedidoAprovacao.setIdHierarquiaItem(proximoHierarquiaItem.getIdHierarquiaItem());
			pedidoAprovacao.setIdPedido(record.getIdPedido());
			pedidoAprovacao.setDtAprovacao(new java.sql.Date(new GregorianCalendar(1900,0,1).getTimeInMillis()));
			pedidoAprovacaoDAO.save(pedidoAprovacao);
		}

		pedidoAprovacaoAtual.setAprovado(true);
		pedidoAprovacaoAtual.setDtAprovacao(new java.sql.Date(Calendar.getInstance().getTimeInMillis()));
		pedidoAprovacaoAtual.setIdUsuario(getUsuarioSession().getIdUsuario());
		pedidoAprovacaoDAO.update(pedidoAprovacaoAtual);

		setPanelIsVisible(false);
		
		FacesMessage msg = new FacesMessage("Atenção",
				"Pedido Aprovado com sucesso.");
		FacesContext.getCurrentInstance().addMessage(null, msg);
		AtualizaGrid();
		org.primefaces.context.RequestContext.getCurrentInstance().execute("PF('dlgPedidoDetalhado').hide()");
	}

	public void negado() {
		Pedido pedido = pedidoDAO.getById(record.getIdPedido());

		pedido.setAprovado(false);
		pedido.setHierarquiaFinalizada(true);

		pedidoDAO.update(pedido);

		pedidoAprovacaoAtual.setAprovado(false);
		pedidoAprovacaoAtual.setDtAprovacao(new java.sql.Date(Calendar.getInstance().getTimeInMillis()));
		pedidoAprovacaoAtual.setIdUsuario(getUsuarioSession().getIdUsuario());
		pedidoAprovacaoDAO.update(pedidoAprovacaoAtual);

		panelIsVisible = false;
		
		FacesMessage msg = new FacesMessage("Atenção",
				"Pedido Negado com sucesso.");
		FacesContext.getCurrentInstance().addMessage(null, msg);
		AtualizaGrid();
		org.primefaces.context.RequestContext.getCurrentInstance().execute("PF('dlgPedidoDetalhado').hide()");
	}
}
