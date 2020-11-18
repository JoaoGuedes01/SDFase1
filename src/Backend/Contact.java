/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Backend;

import java.net.InetAddress;
import java.util.ArrayList;

/**
 *
 * @author joaog
 */
public class Contact {
    
    private String name;

    private String nick;

    private String email;

    private String course;

    private InetAddress  IP;

    private Integer Port;
    
    private Boolean accepted;
    
    private ArrayList<Message>messageList;

    public Contact(String name, String nick, String email, String course, InetAddress IP, Integer Port, Boolean accepted) {
        this.name = name;
        this.nick = nick;
        this.email = email;
        this.course = course;
        this.IP = IP;
        this.Port = Port;
        this.accepted = accepted;
        this.messageList= new ArrayList<Message>();
    }

    

    public String getName() {
        return name;
    }

    public String getNick() {
        return nick;
    }

    public String getEmail() {
        return email;
    }

    public String getCourse() {
        return course;
    }

    public InetAddress getIP() {
        return IP;
    }

    public Integer getPort() {
        return Port;
    }

    public Boolean getAccepted() {
        return accepted;
    }

    public ArrayList<Message> getMessageList() {
        return messageList;
    }
    
    

    public void setName(String name) {
        this.name = name;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public void setIP(InetAddress IP) {
        this.IP = IP;
    }

    public void setPort(Integer Port) {
        this.Port = Port;
    }

    public void setAccepted(Boolean accepted) {
        this.accepted = accepted;
    }

    public void setMessageList(ArrayList<Message> messageList) {
        this.messageList = messageList;
    }

    
    
    
    
}
