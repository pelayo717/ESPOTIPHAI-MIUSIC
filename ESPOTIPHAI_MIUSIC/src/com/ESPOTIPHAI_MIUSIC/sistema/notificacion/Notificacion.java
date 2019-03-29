package com.ESPOTIPHAI_MIUSIC.sistema.notificacion;


import com.ESPOTIPHAI_MIUSIC.sistema.usuario.Usuario;


/**
 *	Clase Notificacion
 */
public class Notificacion {
	private Usuario receptor;
	private String mensaje; 
	private Usuario emisor;
	private TipoNotificacion tipo;
	
	
	public Notificacion(Usuario receptor, String mensaje, Usuario emisor, TipoNotificacion tipo) {
		this.setEmisor(emisor);
		this.setMensaje(mensaje);
		this.setReceptor(receptor);
		this.setTipoNotificacion(tipo);
	}
	
	
	
	//GETTERS Y SETTERS
	
	
	/**
	 * Getter de receptor
	 * @return receptor de la notificacion
	 */
	public Usuario getReceptor() {
		return receptor;
	}

	/**
	 * Setter de receptor
	 * @param receptor receptor de la notificacion
	 */
	public void setReceptor(Usuario receptor) {
		this.receptor = receptor;
	}
	
	
	/**
	 * Getter de mensaje
	 * @return mensaje de la notificacion
	 */
	public String getMensaje() {
		return mensaje;
	}

	/**
	 * Setter de mensaje
	 * @param mensaje mensaje de la notificacion
	 */
	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
	
	
	/**
	 * Getter de tipo de notificacion
	 * @return tipo de notificacion
	 */
	public TipoNotificacion getTipoNotificacion() {
		return tipo;
	}

	/**
	 * Setter de tipo de notificacion
	 * @param tipo tipo de la notificacion
	 */
	public void setTipoNotificacion(TipoNotificacion tipo) {
		this.tipo = tipo;
	}
	
	
	/**
	 * Getter de emisor
	 * @return emisor de la notificacion
	 */
	public Usuario getEmisor() {
		return emisor;
	}

	/**
	 * Setter de emisor
	 * @param emisor emisor de la notificacion
	 */
	public void setEmisor(Usuario emisor) {
		this.emisor = emisor;
	}
}
