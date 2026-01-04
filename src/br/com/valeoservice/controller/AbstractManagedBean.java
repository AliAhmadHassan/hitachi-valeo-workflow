package br.com.valeoservice.controller;


import java.io.IOException;
import java.util.Properties;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import br.com.valeoservice.model.*;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public abstract class AbstractManagedBean {
	
	public Usuario getUsuarioSession() {
		FacesContext fc = FacesContext.getCurrentInstance();
		ExternalContext ec = fc.getExternalContext();
		HttpSession session = (HttpSession) ec.getSession(false);

		Usuario usuario = (Usuario) session
				.getAttribute("usuario");
		if (usuario != null) {
		
			return usuario;
		} else {
			return null;
		}
	}
	
	public Grupo getGrupoSession() {
		FacesContext fc = FacesContext.getCurrentInstance();
		ExternalContext ec = fc.getExternalContext();
		HttpSession session = (HttpSession) ec.getSession(false);

		Grupo grupo = (Grupo) session
				.getAttribute("grupo");
		if (grupo != null) {
		
			return grupo;
		} else {
			return null;
		}
	}
	
	protected void redirecionarPagina(String qualTela) {
		try {
			ExternalContext context = FacesContext.getCurrentInstance()
					.getExternalContext();

			String string = context.getRequestContextPath() + "/faces/"
					+ qualTela + "?faces-redirect=true";

			context.redirect(string);

		} catch (IOException e) {
			System.out.println("Erro ao redirecionar a página '" + qualTela
					+ "'. Erro: " + e.getMessage());
		}
	}
	
	public void logoff() {
		FacesContext fc = FacesContext.getCurrentInstance();
		ExternalContext ec = fc.getExternalContext();
		HttpSession session = (HttpSession) ec.getSession(false);
		session.removeAttribute("usuario");
		this.redirecionarPagina("app_default.xhtml");
		
		
			//generateAndSendEmail();
		
	}

	public String pegaIPCliente(){
		String ipAddress = ((HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest()).getHeader("x-forwarded-for");
		if (ipAddress == null) {
		    ipAddress = ((HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest()).getHeader("X_FORWARDED_FOR");
		    if (ipAddress == null){
		        ipAddress = ((HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest()).getRemoteAddr();
		    }
		}
		return ipAddress;
		
	}
	
	public static void generateAndSendEmail(){
		 
		
		final String username = "haley.eng@gmail.com";
		final String password = "zenantes";

		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");

		Session session = Session.getInstance(props,
		  new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		  });

		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("haley.eng@gmail.com"));
			message.setRecipients(Message.RecipientType.TO,
				InternetAddress.parse("haley.eng@gmail.com"));
			message.setSubject("Testing Subject");
			message.setText("Dear Mail Crawler,"
				+ "\n\n No spam to my email, please!");

			Transport.send(message);

			System.out.println("Done");

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}
}
