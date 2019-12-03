package es.juntadeandalucia.email2.manager;

import es.juntadeandalucia.email2.model.EmailAccount;

public class EmailManager {
	
	private EmailAccount emailAccount;
	
	public static final EmailManager instance = new EmailManager();

	private EmailManager() {
		super();
	}
	
	public static EmailManager instance() {
		return instance;
	}
	
	public EmailAccount getAccount() {
		return instance.emailAccount;
	}
	
	public void setAccount(EmailAccount emailAccount) {
		instance.emailAccount = emailAccount;
	}
	
	
	

}
