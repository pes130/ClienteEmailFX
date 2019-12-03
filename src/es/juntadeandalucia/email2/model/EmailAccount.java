package es.juntadeandalucia.email2.model;

import java.util.Properties;

import javax.mail.Store;


public class EmailAccount {
	private String address;

	private String password;

	private Store store;

	private Properties properties;

	public EmailAccount(String address, String password) {
		super();
		this.address = address;
		this.password = password;
		properties = new Properties();
		properties.put("incomingHost", "imap.gmail.com");
		properties.put("mail.store.protocol", "imaps");
		properties.put("outgoingHost", "smtp.gmail.com");
		properties.put("mail.smtp.host", "smtp.gmail.com");
		properties.put("mail.smtp.port", "587");
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.starttls.enable", "true");
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Store getStore() {
		return store;
	}

	public void setStore(Store store) {
		this.store = store;
	}

	public Properties getProperties() {
		return properties;
	}

	public void setProperties(Properties properties) {
		this.properties = properties;
	}

}
