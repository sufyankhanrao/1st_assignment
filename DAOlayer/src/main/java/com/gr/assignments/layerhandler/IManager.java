package com.gr.assignments.layerhandler;

import java.util.List;

import com.gr.assignments.pojorepository.*;

//This interface expose all the functions which are to be performed by Manager  
public interface IManager {

	// Open the Session Factory
	public void openFactory();

	// Add new Account in the database
	public int addAccount(Account account);

	// Update an existing Account in the database with new Account
	public Account updateAccount(int AccID, Account account);

	// Delete an existing Account from the database
	public Account deleteAccount(int AccID);

	// View all existing Accounts in the database
	public List<Account> viewAllAccounts();

	// Add new Contact in the database
	public int addContact(int AccID, Contact contact);

	// Update an existing Contact in the database with new Contact
	public Contact updateContact(int CntctID, Contact contact);

	// Delete an existing Contact from the database
	public Contact deleteContact(int CntctID);

	// View all existing Contacts in the database
	public List<Contact> viewAllContacts();

	// Close the Session Factory
	public void closeFactory();
}
