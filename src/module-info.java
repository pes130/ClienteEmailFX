module ClienteEmail02 {
	requires javafx.fxml;
	requires javafx.controls;
	requires javafx.graphics;
	requires javafx.web;
	requires java.mail;
	requires java.base;
	
	
	opens es.juntadeandalucia.email2;
	opens es.juntadeandalucia.email2.controller;
	opens es.juntadeandalucia.email2.model;
}