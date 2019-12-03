package es.juntadeandalucia.email2.model;

import javax.mail.Flags;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem;

/**
 * Para extender las capacidades de un TreeItem.
 * 
 * @author asir1a
 *
 * @param <String>
 */
public class EmailTreeItem<String> extends TreeItem<String> {

	private String nombre;
	private ObservableList<EmailMessage> mensajes;
	private int mensajesSinLeer;

	public EmailTreeItem(String nombre) {
		super(nombre);
		this.nombre = nombre;
		this.mensajes = FXCollections.observableArrayList();
		this.mensajesSinLeer = 0;
	}

	public ObservableList<EmailMessage> getMensajes() {
		return mensajes;
	}

	/**
	 * Añade un mensaje a nuestro nodo.
	 * 
	 * @param mensaje
	 * @throws MessagingException
	 */
	public void addMensaje(Message mensaje) throws MessagingException {
		boolean mensajeLeido = mensaje.getFlags().contains(Flags.Flag.SEEN);
		EmailMessage emailMessage = new EmailMessage(mensaje.getFrom()[0].toString(), mensaje.getSubject(),
				mensaje.getRecipients(MimeMessage.RecipientType.TO)[0].toString(), mensaje.getSentDate(),
				mensaje.getSize(), mensaje, mensajeLeido);
		this.mensajes.add(emailMessage);
		if (!mensajeLeido) {
			mensajesSinLeer++;
			actualizarNombre();
		}
	}
	
	
	private void actualizarNombre() {
		if(this.mensajesSinLeer > 0) {
			String nuevoNombre = (String) (this.nombre + " ("+this.mensajesSinLeer+")");
			this.setValue(nuevoNombre);
			//this.getGraphic().setStyle("-fx-font-weight: bold;");
		} 
	}

}
