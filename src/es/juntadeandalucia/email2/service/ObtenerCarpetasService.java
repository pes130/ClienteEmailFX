package es.juntadeandalucia.email2.service;

import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;

import es.juntadeandalucia.email2.model.EmailAccount;
import es.juntadeandalucia.email2.model.EmailTreeItem;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.scene.control.TreeItem;

/**
 * Servicio para obtener carpetas de una cuenta de correo poblando un
 * EmailTreeItem.
 * 
 * @author pablo
 *
 */
public class ObtenerCarpetasService extends Service<Void> {
	/** Cuenta para la que obtenemos las carpetas. **/
	private EmailAccount cuenta;
	/** carpeta raíz que vamos a rellenar **/
	private EmailTreeItem<String> raiz;

	public ObtenerCarpetasService(EmailAccount cuenta, EmailTreeItem<String> raiz) {
		super();
		this.cuenta = cuenta;
		this.raiz = raiz;
	}

	@Override
	protected Task<Void> createTask() {
		return new Task<Void>() {

			@Override
			protected Void call() throws Exception {
				obtenerCarpetas();
				return null;
			}
		};
	}

	/**
	 * Obtenemos las carpetas del servidor.
	 * 
	 * @throws MessagingException
	 */
	private void obtenerCarpetas() throws MessagingException {
		Folder[] folders = this.cuenta.getStore().getDefaultFolder().list();
		gestionarCarpetas(folders, this.raiz);
	}

	/**
	 * Método recursivo para extraer carpetas e ir colgándolas de un árbol
	 * @param folders
	 * @param carpetaRaiz
	 * @throws MessagingException
	 */
	private void gestionarCarpetas(Folder[] folders, TreeItem<String> carpetaRaiz) throws MessagingException {
		for (Folder folder : folders) {
			EmailTreeItem<String> carpeta = new EmailTreeItem<String>(folder.getName());
			carpetaRaiz.getChildren().add(carpeta);
			extraerMensajes(folder, carpeta);
			if (folder.getType() == Folder.HOLDS_FOLDERS) {
				Folder[] subcarpetas = folder.list();
				gestionarCarpetas(subcarpetas, carpeta);
			}
		}
	}
	
	private void extraerMensajes(Folder folder, EmailTreeItem<String> carpeta) {
		Service<Void> obtenerMensajesService = new Service<Void>() {
			@Override
			protected Task<Void> createTask() {
				return new Task<Void>() {
					@Override
					protected Void call() throws Exception {
						if (folder.getType() != Folder.HOLDS_FOLDERS) {
							folder.open(Folder.READ_WRITE);
							for (Message message : folder.getMessages()) {
								carpeta.addMensaje(message);
							}
						}
						return null;
					}
				};
			}
		};
		obtenerMensajesService.start();
	}

}
