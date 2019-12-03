package es.juntadeandalucia.email2.controller;

import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

import es.juntadeandalucia.email2.manager.EmailManager;
import es.juntadeandalucia.email2.model.EmailAccount;
import es.juntadeandalucia.email2.model.EmailMessage;
import es.juntadeandalucia.email2.model.EmailTreeItem;
import es.juntadeandalucia.email2.service.ObtenerCarpetasService;
import es.juntadeandalucia.email2.service.TransformarMensajeMailService;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.util.Callback;

/**
 * Controlador asociado a la vista MainWindow.
 * 
 * @author pablo
 *
 */
public class MainWindowController extends BaseController implements Initializable {
	/** Árbol con carpetas a la izquierda de la ventana. */
	@FXML
	private TreeView<String> emailTreeView;
	/** Tabla con emails por carpeta. */
	@FXML
	private TableView<EmailMessage> emailTableView;
	/** Columna remitente. */
	@FXML
	private TableColumn<EmailMessage, String> remitenteCol;
	/** Columna asunto **/
	@FXML
	private TableColumn<EmailMessage, String> asuntoCol;

	@FXML
	private TableColumn<EmailMessage, String> recipientCol;

	@FXML
	private TableColumn<EmailMessage, Date> fechaCol;

	@FXML
	private TableColumn<EmailMessage, Integer> tamanhoCol;

	@FXML
	private WebView emailWebView;

	private EmailTreeItem<String> treeRoot;

	public MainWindowController(String fxmlName) {
		super(fxmlName);

	}

	@Override
	public Stage getStage() {
		return (Stage) this.emailTableView.getScene().getWindow();
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// Preparamos el árbol con carpetas de la izquierda. Además si las carpetas
		// tienen mensajes, los almacenamos en objetos EmailMessage
		populateTreeView();
		// Preparamos las columnas de la tabla de mensajes
		setUpTableView();
		// Cargar mensajes al pulsar una carpeta
		cargarMensajesDeCarpeta();

		// Accion al pulsar un correo
		asociarEventosTablaEmails();

		// Poner correos no leidos en negrita
		marcarCorreosNoLeidosEnTabla();
	}

	/**
	 * Consulta las carpetas de nuestra cuenta y las engancha en el arbol de items
	 */
	private void populateTreeView() {
		EmailAccount account = EmailManager.instance().getAccount();
		treeRoot = new EmailTreeItem<String>(account.getAddress());
		this.emailTreeView.setRoot(treeRoot);
		this.treeRoot.setExpanded(true);
		ObtenerCarpetasService ocs = new ObtenerCarpetasService(account, treeRoot);
		ocs.start();
	}

	/**
	 * Preparamos las columnas de la tabla
	 */
	private void setUpTableView() {
		this.remitenteCol.setCellValueFactory(new PropertyValueFactory<EmailMessage, String>("remitente"));
		this.asuntoCol.setCellValueFactory(new PropertyValueFactory<EmailMessage, String>("asunto"));
		this.recipientCol.setCellValueFactory(new PropertyValueFactory<EmailMessage, String>("recipient"));
		this.fechaCol.setCellValueFactory(new PropertyValueFactory<EmailMessage, Date>("fecha"));
		this.tamanhoCol.setCellValueFactory(new PropertyValueFactory<EmailMessage, Integer>("tamanho"));
	}

	/**
	 * Carga mensajes de carpeta cada vez que pulsamos en una.
	 */
	private void cargarMensajesDeCarpeta() {
		this.emailTreeView.setOnMouseClicked(event -> {
			// Recuperamos el item del árbol seleccionado
			EmailTreeItem<String> carpetaPulsada = (EmailTreeItem<String>) this.emailTreeView.getSelectionModel()
					.getSelectedItem();
			if (carpetaPulsada != null) {
				this.emailTableView.setItems(carpetaPulsada.getMensajes());
			}
		});

	}

	private void asociarEventosTablaEmails() {
		this.emailTableView.setOnMouseClicked(event -> {
			EmailMessage em = this.emailTableView.getSelectionModel().getSelectedItem();
			TransformarMensajeMailService tmms = new TransformarMensajeMailService(em);
			tmms.start();
			tmms.setOnSucceeded(e -> {
				StringBuffer sb = tmms.getValue();
				this.emailWebView.getEngine().loadContent(sb.toString());
			});
		});

	}

	private void marcarCorreosNoLeidosEnTabla() {
		this.emailTableView.setRowFactory(new Callback<TableView<EmailMessage>, TableRow<EmailMessage>>() {
			@Override
			public TableRow<EmailMessage> call(TableView<EmailMessage> arg0) {
				return new TableRow<EmailMessage>() {
					@Override
					protected void updateItem(EmailMessage item, boolean arg1) {
						super.updateItem(item, arg1);
						if (item != null) {
							if (item.isRead()) {
								setStyle("");
							} else {
								setStyle("-fx-font-weight: bold");
							}
						}
					}
				};
			}
		});
	}
	
	@FXML
	public void cerrarAplicacion() {
		Platform.exit();
	}
}
