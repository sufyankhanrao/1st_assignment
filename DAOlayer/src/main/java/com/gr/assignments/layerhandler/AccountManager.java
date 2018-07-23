package com.gr.assignments.layerhandler;

import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import com.gr.assignments.factoryprovider.SessionFactoryProvider;
import com.gr.assignments.pojorepository.*;

/**
 * This class manages all the accounts and its Contacts information.All methods
 * descriptions are provided in IManager.java interface
 **/
public class AccountManager implements IManager {
	private SessionFactory sessionFactory;
	private Session session;
	private Helper helper = new Helper();

	public AccountManager() {
		sessionFactory = SessionFactoryProvider.getInstance().getSessionFactory();
	}

	public void openFactory() {
		sessionFactory = SessionFactoryProvider.getInstance().getSessionFactory();
	}

	public int addAccount(Account account) {
		int ID;
		try {
			session = sessionFactory.openSession();
			session.beginTransaction();
			ID = (Integer) session.save(account);
			session.getTransaction().commit();
		} catch (HibernateException hbe) {
			System.out.println("Cannot Add Account!");
			session.getTransaction().rollback();
			return -1;
		} finally {
			if (session.isOpen()) {
				session.close();
			}
		}
		return ID;
	}

	public Account updateAccount(int AccID, Account account) {
		Account retrievedAccount = helper.fetchAccount(AccID, this.session, this.sessionFactory);
		try {
			retrievedAccount.setName(account.getName());
			retrievedAccount.setEmailDomain(account.getEmailDomain());
			retrievedAccount.setTimezoneCity(account.getTimezoneCity());
			session = sessionFactory.openSession();
			session.beginTransaction();
			session.update(retrievedAccount);
			session.getTransaction().commit();
		} catch (HibernateException hbe) {
			session.getTransaction().rollback();
			return null;
		} catch (NullPointerException e) {
			return null;
		} finally {
			if (session.isOpen()) {
				session.close();
			}
		}
		return retrievedAccount;
	}

	public Account deleteAccount(int AccID) {
		List<Contact> contacts = helper.getContactList(AccID, this.session, this.sessionFactory);
		Account account = helper.fetchAccount(AccID, this.session, this.sessionFactory);
		try {
			for (Contact contact : contacts) {
				deleteContact(contact.getId());
			}
			session = sessionFactory.openSession();
			session.beginTransaction();
			if (account != null) {
				session.delete(account);
			}
			session.getTransaction().commit();
		} catch (HibernateException hbe) {
			hbe.printStackTrace();
			session.getTransaction().rollback();
			return null;
		} finally {
			if (session.isOpen()) {
				session.close();
			}
		}
		return account;
	}

	public List<Account> viewAllAccounts() {
		String result = "";
		List<Account> accounts;
		try {
			session = sessionFactory.openSession();
			session.beginTransaction();
			Query query = session.createQuery("from Account");
			accounts = (List<Account>) query.list();
			session.getTransaction().commit();
		} catch (HibernateException hexp) {
			hexp.printStackTrace();
			session.getTransaction().rollback();
			return null;
		} finally {
			if (session.isOpen()) {
				session.close();
			}
		}
		return accounts;

	}

	public int addContact(int AccID, Contact contact) {
		int ID;
		Account account = helper.fetchAccount(AccID, this.session, this.sessionFactory);
		if (account != null)
			contact.setAccount(account);
		try {
			contact.getAddress().setContact(contact);
			session = sessionFactory.openSession();
			session.beginTransaction();
			ID = (Integer) session.save(contact);
			session.save(contact.getAddress());
			session.getTransaction().commit();
		} catch (HibernateException hbe) {
			System.out.println("Account does not exist to associate");
			session.getTransaction().rollback();
			return -1;
		} finally {
			if (session.isOpen()) {
				session.close();
			}
		}
		return ID;
	}

	public Contact deleteContact(int CntctID) {
		Contact contact = helper.fetchContact(CntctID, this.session, this.sessionFactory);
		try {
			helper.deleteAddress(contact.getId(), this.session, this.sessionFactory);
			session = sessionFactory.openSession();
			session.beginTransaction();
			if (contact != null) {
				session.delete(contact);
			}
			session.getTransaction().commit();
		} catch (HibernateException hbe) {
			hbe.printStackTrace();
			session.getTransaction().rollback();
			return null;
		} catch (NullPointerException nullexp) {
			System.out.println("Contact not found!");
			return null;
		} finally {
			if (session.isOpen()) {
				session.close();
			}
		}
		return contact;
	}

	public List<Contact> viewAllContacts() {
		String result = "";
		List<Contact> contacts;
		try {
			session = sessionFactory.openSession();
			session.beginTransaction();
			Query query = session.createQuery("from Contact");
			contacts = (List<Contact>) query.list();
			session.getTransaction().commit();
		} catch (HibernateException hexp) {
			hexp.printStackTrace();
			session.getTransaction().rollback();
			return null;
		} finally {
			if (session.isOpen()) {
				session.close();
			}
		}
		return contacts;
	}

	public Contact updateContact(int CntctID, Contact contact) {
		Contact retrievedContact = helper.fetchContact(CntctID, this.session, this.sessionFactory);
		Address retrievedAddress = helper.fetchAddress(CntctID, this.session, this.sessionFactory);
		try {
			retrievedContact.setFirstName(contact.getFirstName());
			retrievedContact.setLastName(contact.getLastName());
			retrievedContact.setEmailID(contact.getEmailID());
			retrievedContact.setGender(contact.getGender());
			retrievedContact.setPhoneNo(contact.getPhoneNo());
			retrievedContact.setStatus(contact.getStatus());
			retrievedContact.setAddress(contact.getAddress());
			retrievedAddress.setStreet(contact.getAddress().getStreet());
			retrievedAddress.setCity(contact.getAddress().getCity());
			retrievedAddress.setState(contact.getAddress().getState());
			retrievedAddress.setCountry(contact.getAddress().getCountry());
			session = sessionFactory.openSession();
			session.beginTransaction();
			session.update(retrievedAddress);
			session.update(retrievedContact);
			session.getTransaction().commit();
		} catch (HibernateException hbe) {
			hbe.printStackTrace();
			session.getTransaction().rollback();
			return null;
		} catch (NullPointerException e) {
			return null;
		} finally {
			if (session.isOpen()) {
				session.close();
			}
		}
		return retrievedContact;
	}

	public void closeFactory() {
		this.sessionFactory.close();
	}

}
