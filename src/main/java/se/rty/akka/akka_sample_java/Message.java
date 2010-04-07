/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package se.rty.akka.akka_sample_java;

/**
 *
 * @author bobo
 */
public class Message {

    private final String userName;
    private final String message;

    public Message(String userName, String message) {
        this.userName = userName;
        this.message = message;
    }

    public String getUserName() {
        return userName;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return (userName + ": " + message);
    }

    public static Message fromString(String str) {
        String[] split = str.split(":");
        return new Message(split[0], split[1]);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Message other = (Message) obj;
        if ((this.userName == null) ? (other.userName != null) : !this.userName.equals(other.userName)) {
            return false;
        }
        if ((this.message == null) ? (other.message != null) : !this.message.equals(other.message)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + (this.userName != null ? this.userName.hashCode() : 0);
        hash = 97 * hash + (this.message != null ? this.message.hashCode() : 0);
        return hash;
    }

    
}
