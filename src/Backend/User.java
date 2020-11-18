/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Backend;

import java.io.Serializable;
import java.net.InetAddress;
import javax.sound.sampled.Port;

/**
 *
 * @author joaog
 */
public class User implements Serializable {

    private String name;

    private String nick;

    private String email;

    private String course;

    private InetAddress  IP;

    private Integer Port;

    public User(String name, String nick, String email, String course) {
        this.name = name;
        this.nick = nick;
        this.email = email;
        this.course = course;
    }

    User() {

    }

    //Getters
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

    //Setters
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

}
