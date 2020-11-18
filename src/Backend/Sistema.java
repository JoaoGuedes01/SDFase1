/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Backend;

/**
 *
 * @author joaog
 */
public class Sistema {

    private User userLogado;

    private ContactList contactList;

    private RequestList requestList;

    public Sistema() {
        this.userLogado = new User();
        this.contactList = new ContactList();
        this.requestList = new RequestList();
    }

    public User getUserLogado() {
        return userLogado;
    }

    public ContactList getContactList() {
        return contactList;
    }

    public RequestList getRequestList() {
        return requestList;
    }

    public void setUserLogado(User userLogado) {
        this.userLogado = userLogado;
    }

    public void setContactList(ContactList contactList) {
        this.contactList = contactList;
    }

    public void setRequestList(RequestList requestList) {
        this.requestList = requestList;
    }

}
