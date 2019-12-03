package es.juntadeandalucia.email2.controller;

import javafx.stage.Stage;

public abstract class BaseController {
	
	/** Nombre de la vista asociada **/
	private String fxmlName;
	
	public BaseController(String fxmlName) {
		super();
		this.fxmlName = fxmlName;
	}

	public String getFxmlName() {
		return fxmlName;
	}

	public void setFxmlName(String fxmlName) {
		this.fxmlName = fxmlName;
	}
	
	
	public abstract Stage getStage();
	

}
