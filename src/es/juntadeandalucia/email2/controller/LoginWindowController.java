package es.juntadeandalucia.email2.controller;

import java.io.IOException;

import es.juntadeandalucia.email2.manager.EmailManager;
import es.juntadeandalucia.email2.model.EmailAccount;
import es.juntadeandalucia.email2.model.LoginResult;
import es.juntadeandalucia.email2.service.LoginService;
import es.juntadeandalucia.email2.view.ViewFactory;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginWindowController extends BaseController {

	@FXML
	private TextField emailField;

	@FXML
	private PasswordField passwordField;

	@FXML
	private Button btnLogin;

	@FXML
	private Label errorLabel;

	public LoginWindowController(String fxmlName) {
		super(fxmlName);
	}
	
	@FXML
	void resetAction() {
		
	}

	@FXML
	void loginAction() throws IOException {
		System.out.println("Le has dado al login");
		EmailAccount emailAccount = new EmailAccount(this.emailField.getText(), this.passwordField.getText());
		LoginService loginService = new LoginService(emailAccount);
		loginService.start();
		loginService.setOnSucceeded((event) -> {
			LoginResult resultado = loginService.getValue();
			switch (resultado) {
			case OK:
				try {
					EmailManager.instance().setAccount(emailAccount);
					ViewFactory.instance().showMainWindows();
					ViewFactory.instance().closeStage(getStage());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			case BAD_CREDENTIALS:
				this.errorLabel.setText("Credenciales incorrectas");
				break;
			case ERROR_BY_NETWORK:
				this.errorLabel.setText("Servidor Kaputt");
				break;
			case ERROR_DESCONOCIDO:
				this.errorLabel.setText("Algo va mal y no sabemos qué es");
				break;
			}
		});
	}

	@Override
	public Stage getStage() {

		return (Stage) this.btnLogin.getScene().getWindow();
	}

}
