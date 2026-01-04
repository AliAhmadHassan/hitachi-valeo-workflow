package br.com.valeoservice.controller;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import br.com.valeoservice.model.Grupo;
import br.com.valeoservice.model.Usuario;
import br.com.valeoservice.model.dao.IUsuariosDAO;
import br.com.valeoservice.model.dao.mysql.impl.UsuariosDAO;

@ManagedBean
@SessionScoped
public class LoginBean extends AbstractManagedBean {

	private IUsuariosDAO usuariosDAO = new UsuariosDAO();
	private Usuario usuario = new Usuario();
	private boolean logado;

	public String envia() {
		Usuario usuarioLogin = usuariosDAO.getConsultaLogin(usuario.geteMail(), usuario.getSenha());
		if (usuarioLogin == null) {
			usuarioLogin = new Usuario();
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Usuário não encontrado!", "Erro no Login!"));
			return null;
		} else {
			FacesContext fc = FacesContext.getCurrentInstance();
			ExternalContext ec = fc.getExternalContext();
			HttpSession session = (HttpSession) ec.getSession(false);
			
			Grupo grupo = new br.com.valeoservice.model.dao.mysql.impl.GruposDAO().getById(usuarioLogin.getIdGrupo());
			
			session.setAttribute("usuario", usuarioLogin);
			session.setAttribute("grupo", grupo);
			
			return "/main";
		}
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public boolean getLogado() {
		if(getUsuarioSession() == null)
			return false;
		else
			return true;
	}


}
