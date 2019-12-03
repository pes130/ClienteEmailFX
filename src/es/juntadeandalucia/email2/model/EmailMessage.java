package es.juntadeandalucia.email2.model;

import java.util.Date;

import javax.mail.Message;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

public class EmailMessage {
	private SimpleStringProperty remitente;
	private SimpleStringProperty asunto;
	private SimpleStringProperty recipient;
	private SimpleObjectProperty<Date> fecha;
	private SimpleIntegerProperty tamanho;
	private Message mensaje;
	private boolean isRead;

	public EmailMessage(String remitente, String asunto, String recipient, Date fecha, Integer tamanho, Message message,
			boolean isRead) {
		super();
		this.remitente = new SimpleStringProperty(remitente);
		this.asunto = new SimpleStringProperty(asunto);
		this.recipient = new SimpleStringProperty(recipient);
		this.fecha = new SimpleObjectProperty<Date>(fecha);
		this.tamanho = new SimpleIntegerProperty(tamanho);
		this.isRead = false;
		this.mensaje = message;
	}

	public String getRemitente() {
		return remitente.getValue();
	}

	public void setRemitente(SimpleStringProperty remitente) {
		this.remitente = remitente;
	}

	public String getAsunto() {
		return asunto.getValue();
	}

	public void setAsunto(SimpleStringProperty asunto) {
		this.asunto = asunto;
	}

	public String getRecipient() {
		return recipient.getValue();
	}

	public void setRecipient(SimpleStringProperty recipient) {
		this.recipient = recipient;
	}

	public Date getFecha() {
		return fecha.getValue();
	}

	public void setFecha(SimpleObjectProperty<Date> fecha) {
		this.fecha = fecha;
	}

	public Integer getTamanho() {
		return tamanho.getValue();
	}

	public void setTamanho(SimpleIntegerProperty tamanho) {
		this.tamanho = tamanho;
	}

	public boolean isRead() {
		return isRead;
	}

	public void setRead(boolean isRead) {
		this.isRead = isRead;
	}

	public Message getMensaje() {
		return mensaje;
	}

	public void setMensaje(Message mensaje) {
		this.mensaje = mensaje;
	}

}
