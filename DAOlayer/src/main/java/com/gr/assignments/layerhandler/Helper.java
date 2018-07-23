package com.gr.assignments.layerhandler;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import com.gr.assignments.pojorepository.*;

/**
 * This class contains some helping methods which are serviced to Account
 * Manager class
 **/
public class Helper {
	public Helper() {

	}

	// This method accepts Account ID and returns an Account corresponds to that
	// Account ID from database
	public Account fetchAccount(int ID, Session session, SessionFactory sessionFactory) {
		Account account;
		try {
			session = sessionFactory.openSession();
			session.beginTransaction();
			account = (Account) session.get(Account.class, ID);
			session.getTransaction().commit();
		} catch (HibernateException hbe) {
			System.out.println("ID not Found in Account Table");
			session.getTransaction().rollback();
			return null;
		} catch (NullPointerException nullexp) {
			System.out.println("Account not found!");
			return null;
		} finally {
			if (session.isOpen()) {
				session.close();
			}
		}
		return account;
	}

	// This method accepts Contact ID and returns an Contact corresponds to that
	// Contact ID from database
	public Contact fetchContact(int ID, Session session, SessionFactory sessionFactory) {
		Contact contact;
		try {
			session = sessionFactory.openSession();
			session.beginTransaction();
			contact = (Contact) session.get(Contact.class, ID);
			session.getTransaction().commit();
		} catch (HibernateException hbe) {
			System.out.println("ID not Found in Account Table");
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

	// This method accepts Contact ID and returns an Address object associated to
	// that Contact ID from database
	public Address fetchAddress(int contactID, Session session, SessionFactory sessionFactory) {
		List<Address> address;
		try {
			session = sessionFactory.openSession();
			session.beginTransaction();
			Query q = session.createQuery("from Address where Contact_ID = " + contactID);
			address = (List<Address>) q.list();
			session.getTransaction().commit();
		} catch (HibernateException hbe) {
			System.out.println("ID not Found in Account Table");
			return null;
		} catch (NullPointerException nullexp) {
			System.out.println("Address not found!");
			return null;
		} finally {
			if (session.isOpen()) {
				session.close();
			}
		}
		if (!address.isEmpty())
			return address.get(0);
		return null;
	}

	// This method accepts Contact ID and performs deletion of an Address object
	// associated to that Contact ID from database
	public boolean deleteAddress(int contactID, Session session, SessionFactory sessionFactory) {
		try {
			session = sessionFactory.openSession();
			session.beginTransaction();
			Query q = session.createQuery("delete Address where Contact_ID = " + contactID);
			q.executeUpdate();
			session.getTransaction().commit();
		} catch (HibernateException hbe) {
			session.getTransaction().rollback();
			return false;
		} catch (NullPointerException nullexp) {
			System.out.println("Address not found!");
			return true;
		} finally {
			if (session.isOpen()) {
				session.close();
			}
		}
		return true;
	}

	// This method accepts Account ID and returns a list of Contact objects
	// correspond to that Account ID from database
	public List<Contact> getContactList(int AccID, Session session, SessionFactory sessionFactory) {
		List<Contact> contacts;
		try {
			session = sessionFactory.openSession();
			session.beginTransaction();
			Query query = session.createQuery("from Contact where Account_ID = " + AccID);
			contacts = (List<Contact>) query.list();
			session.getTransaction().commit();
		} catch (HibernateException hbe) {
			session.getTransaction().rollback();
			return null;
		} catch (NullPointerException nullexp) {
			System.out.println("Contacts not found!");
			return null;
		} finally {
			if (session.isOpen()) {
				session.close();
			}
		}
		return contacts;
	}
}
