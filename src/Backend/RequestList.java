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
public class RequestList implements Serializable {

    private ArrayList<Contact> requestList;

    public RequestList() {
        this.requestList = new ArrayList<Contact>();
    }

    public ArrayList<Contact> getRequestList() {
        return requestList;
    }

    public void setRequestList(ArrayList<User> userList) {
        this.requestList = requestList;
    }

    public void addContact(String name, String nick, String email, String course, InetAddress IP, Integer Port, Boolean accepted) {
        Contact contact = new Contact(name, nick, email, course, IP, Port, accepted);
        requestList.add(contact);
    }

}
