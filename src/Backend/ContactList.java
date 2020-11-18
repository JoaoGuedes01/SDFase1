/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Backend;

import java.io.Serializable;
import java.net.InetAddress;
import java.util.ArrayList;

/**
 *
 * @author joaog
 */
public class ContactList implements Serializable{
    
    private ArrayList<Contact>contactList;
    
    public ContactList(){
        this.contactList = new ArrayList<Contact>();
    }

    public ArrayList<Contact> getContactList() {
        return contactList;
    }

    public void setUserList(ArrayList<User> userList) {
        this.contactList = contactList;
    }
    
    public void addContact(String name, String nick, String email, String course, InetAddress IP, Integer Port,Boolean accepted){
        Contact contact = new Contact(name,nick,email,course,IP,Port,accepted);
	contactList.add(contact);
	}
    
}
