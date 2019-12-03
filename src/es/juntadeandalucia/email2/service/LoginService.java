package es.juntadeandalucia.email2.service;

import javax.mail.Authenticator;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Store;

import es.juntadeandalucia.email2.model.EmailAccount;
import es.juntadeandalucia.email2.model.LoginResult;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

public class LoginService extends Service<LoginResult> {

	private EmailAccount emailAccount;

	public LoginService(EmailAccount emailAccount) {
		super();
		this.emailAccount = emailAccount;
	}

	@Override
	protected Task<LoginResult> createTask() {

		return new Task<LoginResult>() {

			@Override
			protected LoginResult call() {
				return login();
			}

		};
	}

	private LoginResult login() {
		Authenticator authenticator = new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(emailAccount.getAddress(), emailAccount.getPassword());
			}
		};
		Session session = Session.getInstance(emailAccount.getProperties(), authenticator);
		Store store;
		try {
			store = session.getStore("imaps");
			store.connect(emailAccount.getProperties().getProperty("incomingHost"), emailAccount.getAddress(),
					emailAccount.getPassword());
			emailAccount.setStore(store);
		} catch (NoSuchProviderException e) {
			e.printStackTrace();
			return LoginResult.ERROR_BY_NETWORK;
		} catch (MessagingException e) {
			e.printStackTrace();
			return LoginResult.BAD_CREDENTIALS;
		} catch (Exception e) {
			e.printStackTrace();
			return LoginResult.ERROR_DESCONOCIDO;
		}
		return LoginResult.OK;
	}

}
