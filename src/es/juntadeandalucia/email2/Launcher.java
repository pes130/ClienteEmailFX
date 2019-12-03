package es.juntadeandalucia.email2;
	
import java.io.IOException;

import es.juntadeandalucia.email2.view.ViewFactory;
import javafx.application.Application;
import javafx.stage.Stage;


public class Launcher extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			ViewFactory.instance().showLoginWindow();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
