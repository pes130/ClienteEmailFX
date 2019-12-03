package es.juntadeandalucia.email2.service;

import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;

import es.juntadeandalucia.email2.model.EmailMessage;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

/**
 * Servicio que transforma un EmailMessage en un string Buffer listo para
 * ser volcado en un webView o lo que se quiera hacer con él.
 * 
 * @author pablo
 *
 */
public class TransformarMensajeMailService extends Service<StringBuffer>{

	private EmailMessage emailMessage;
	
	public TransformarMensajeMailService(EmailMessage emailMessage) {
		this.emailMessage = emailMessage;
	}
	
	@Override
	protected Task<StringBuffer> createTask() {
		
		return new Task<StringBuffer>() {

			@Override
			protected StringBuffer call() throws Exception {
				StringBuffer sb = new StringBuffer();
				
				
				Message message = emailMessage.getMensaje();
				if(isSimpleType(message.getContentType())) {
					// si el mensaje no es multipart, lo añadimos sin más
					sb.append(message.getContent());
				} else if (isMultipartType(message.getContentType())){
					Multipart multipart = (Multipart)message.getContent();
					for(int i = multipart.getCount()-1; i>=0; i--) {
						BodyPart bp = multipart.getBodyPart(i);
						if(isSimpleType(bp.getContentType())) {
							sb.append(bp.getContent());
						}
						
					}
				}			
				return sb;
			}		
		};
	}
	
	private boolean isSimpleType(String contentType) {
		if (contentType.contains("TEXT/HTML") || contentType.contains("mixed") || contentType.contains("text")) {
			return true;
		} else {
			return false;
		}
	}

	private boolean isMultipartType(String contentType) {
		if (contentType.contains("multipart")) {
			return true;
		} else {
			return false;
		}
	}
	

}
