package es.juntadeandalucia.email2.view;

import java.io.IOException;

import es.juntadeandalucia.email2.controller.BaseController;
import es.juntadeandalucia.email2.controller.LoginWindowController;
import es.juntadeandalucia.email2.controller.MainWindowController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ViewFactory {
	
	public static final ViewFactory vfInstance = new ViewFactory();
	
	private ViewFactory() {
		// Evitar que se use este constructor
	}

	public static ViewFactory instance() {
		return vfInstance;
	}
	
	/**
	 * Muestra la ventana de login.
	 * @throws IOException 
	 */
	public void showLoginWindow() throws IOException {
		LoginWindowController loginWindowController = new LoginWindowController("Login.fxml");
		iniciarStage(loginWindowController);
	}

	private void iniciarStage(BaseController controller) throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(controller.getFxmlName()));
		fxmlLoader.setController(controller);
		Parent root = fxmlLoader.load();
		Stage stage = new Stage();
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}

	/**
	 * Muestra la ventana principal.
	 * @throws IOException 
	 */
	public void showMainWindows() throws IOException {
		MainWindowController mainWindowController = new MainWindowController("MainWindow.fxml");
		iniciarStage(mainWindowController);
	}
	
	public void closeStage(Stage stage) {
		stage.close();
	}

}
