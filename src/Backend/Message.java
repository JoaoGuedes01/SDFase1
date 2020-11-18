/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Backend;

import java.io.Serializable;

/**
 *
 * @author joaog
 */
public class Message implements Serializable{
    
    private String type;
    private String message;
    private User user;
    
    

    public Message(String type, String message, User user) {
        this.type = type;
        this.message = message;
        this.user = user;
    }

   

    public String getType() {
        return type;
    }

    public String getMessage() {
        return message;
    }

    public User getUser() {
        return user;
    }
    
    public void setType(String type) {
        this.type = type;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    
}
