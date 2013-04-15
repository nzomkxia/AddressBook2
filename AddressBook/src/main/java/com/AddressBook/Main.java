package com.AddressBook;
public class Main {
	public static void main(String [] args){
		String path = "/home/zmx/workspace/addressbook/test.json";
		AddressBook testBook = new AddressBook(null, null, false, path);
		testBook.start();
	}

}
