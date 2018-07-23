package com.gr.assignments.layerhandler;

import static org.junit.Assert.*;
import java.util.List;
import org.junit.Test;
import com.gr.assignments.pojorepository.*;

public class AccountManagerTest {

	AccountManager manager = new AccountManager();

	// Testing Add Account unit
	@Test
	public void testAddAccount() {
		Account account = generateDummyAccount();
		manager.addAccount(account);
		int actualResult = manager.viewAllAccounts().size();
		assertEquals(1, actualResult);
		manager.deleteAccount(account.getId());
	}

	// Testing update Account unit
	@Test
	public void testUpdateAccount() {
		Account account = generateDummyAccount();
		int ID = manager.addAccount(account);
		Account newAccount = new Account("Asad", "org", "Lahore");
		Account retrievedAccount = manager.updateAccount(ID, newAccount);
		assertEquals(newAccount.getName(), retrievedAccount.getName());
		assertEquals(newAccount.getEmailDomain(), retrievedAccount.getEmailDomain());
		assertEquals(newAccount.getTimezoneCity(), retrievedAccount.getTimezoneCity());
		manager.deleteAccount(retrievedAccount.getId());
	}

	// Testing delete Account unit
	@Test
	public void testDeleteAccount() {
		Account account = generateDummyAccount();
		int ID = manager.addAccount(account);
		manager.addContact(ID, generateDummyContact());
		int afterAccountAddition = manager.viewAllAccounts().size();
		int afterContactAddition = manager.viewAllContacts().size();
		manager.deleteAccount(ID);
		int afterAccountDeletion = manager.viewAllAccounts().size();
		int afterContactDeletion = manager.viewAllContacts().size();
		assertEquals(1, afterAccountAddition);
		assertEquals(1, afterContactAddition);
		assertEquals(0, afterContactDeletion);
		assertEquals(0, afterAccountDeletion);
	}

	// Testing view all Accounts unit
	@Test
	public void testViewAllAccounts() {
		Account account = generateDummyAccount();
		int ID = manager.addAccount(account);
		List<Account> retrievedAccounts = manager.viewAllAccounts();
		for (Account retrievedAccount : retrievedAccounts) {
			assertEquals(account.getId(), retrievedAccount.getId());
			assertEquals(account.getName(), retrievedAccount.getName());
			assertEquals(account.getEmailDomain(), retrievedAccount.getEmailDomain());
			assertEquals(account.getTimezoneCity(), retrievedAccount.getTimezoneCity());
		}
		manager.deleteAccount(ID);
	}

	// Testing Add Contact unit
	@Test
	public void testAddContact() {
		Account account = generateDummyAccount();
		int accountID = manager.addAccount(account);
		manager.addContact(accountID, generateDummyContact());
		int actualResult = manager.viewAllContacts().size();
		assertEquals(1, actualResult);
		manager.deleteAccount(accountID);
		manager.closeFactory();
	}

	// Testing delete Contact unit
	@Test
	public void testDeleteContact() {
		Account account = generateDummyAccount();
		int accountID = manager.addAccount(account);
		int contactID = manager.addContact(accountID, generateDummyContact());
		int afterAccountAddition = manager.viewAllAccounts().size();
		int afterContactAddition = manager.viewAllContacts().size();
		manager.deleteContact(contactID);
		int afterAccountDeletion = manager.viewAllAccounts().size();
		int afterContactDeletion = manager.viewAllContacts().size();
		assertEquals(1, afterAccountAddition);
		assertEquals(1, afterContactAddition);
		assertEquals(0, afterContactDeletion);
		assertEquals(1, afterAccountDeletion);
		manager.deleteAccount(accountID);
	}

	// Testing view all Contacts unit
	@Test
	public void testViewAllContacts() {
		Account account = generateDummyAccount();
		Contact contact = generateDummyContact();
		int ID = manager.addAccount(account);
		manager.addContact(ID, contact);
		List<Contact> retrievedContacts = manager.viewAllContacts();
		for (Contact retrievedContact : retrievedContacts) {
			assertEquals(contact.getId(), retrievedContact.getId());
			assertEquals(contact.getFirstName(), retrievedContact.getFirstName());
			assertEquals(contact.getLastName(), retrievedContact.getLastName());
			assertEquals(contact.getEmailID(), retrievedContact.getEmailID());
			assertEquals(contact.getStatus(), retrievedContact.getStatus());
			assertEquals(contact.getGender(), retrievedContact.getGender());
			assertEquals(contact.getPhoneNo(), retrievedContact.getPhoneNo());
			assertEquals(contact.getAddress().getStreet(), retrievedContact.getAddress().getStreet());
			assertEquals(contact.getAddress().getCity(), retrievedContact.getAddress().getCity());
			assertEquals(contact.getAddress().getState(), retrievedContact.getAddress().getState());
			assertEquals(contact.getAddress().getCountry(), retrievedContact.getAddress().getCountry());
			assertEquals(contact.getAccount().getId(), retrievedContact.getAccount().getId());
		}
		manager.deleteAccount(ID);
	}

	// Testing update Contact unit
	@Test
	public void testUpdateContact() {
		Account account = generateDummyAccount();
		Contact contact = generateDummyContact();
		Contact expactedContact = new Contact("Awab", "Khan", "awab14GR@gmail.com", "male", "+923427676999", "inactive",
				new Address("street 100", "lhr", "punjab", "pakistan"));
		int accountID = manager.addAccount(account);
		int contactID = manager.addContact(accountID, contact);
		Contact actualContact = manager.updateContact(contactID, expactedContact);
		assertEquals(contactID, actualContact.getId());
		assertEquals(expactedContact.getFirstName(), actualContact.getFirstName());
		assertEquals(expactedContact.getLastName(), actualContact.getLastName());
		assertEquals(expactedContact.getEmailID(), actualContact.getEmailID());
		assertEquals(expactedContact.getStatus(), actualContact.getStatus());
		assertEquals(expactedContact.getGender(), actualContact.getGender());
		assertEquals(expactedContact.getPhoneNo(), actualContact.getPhoneNo());
		assertEquals(expactedContact.getAddress().getStreet(), actualContact.getAddress().getStreet());
		assertEquals(expactedContact.getAddress().getCity(), actualContact.getAddress().getCity());
		assertEquals(expactedContact.getAddress().getState(), actualContact.getAddress().getState());
		assertEquals(expactedContact.getAddress().getCountry(), actualContact.getAddress().getCountry());
		manager.deleteAccount(accountID);
	}

	// Utility method for generating a dummy Account for testing
	private Account generateDummyAccount() {
		return new Account("Sufyan", "com", "Islamabad");
	}

	// Utility method for generating a dummy Contact for testing
	private Contact generateDummyContact() {
		return new Contact("Sheraz", "ahmed", "sherazahmed18@gmail.com", "male", "+923427676888", "active",
				new Address("street 12", "rwp", "punjab", "pakistan"));
	}

}
