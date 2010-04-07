package se.rty.akka.akka_sample_java;

import com.google.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import se.scalablesolutions.akka.actor.ActiveObject;
import se.scalablesolutions.akka.remote.RemoteClient;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author bobo
 */
public class ChatSession {

    private String userName;
    private final List<Message> messages = new ArrayList<Message>();
    @Inject
    private ChatServer chatServer;

    public void login() {
    chatServer = ActiveObject.newRemoteInstance(ChatServer.class, 1000l, "localhost", 11234);
        chatServer.init();
        chatServer.login(this);
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public void recieveMessage(Message message) {
        messages.add(message);
    }

    public void sendMessage(String text) {
        Message message = new Message(userName, text);
        chatServer.sendMessage(message);
    }

    public List<Message> getLog() {
        return messages;
    }

    @Override
    public String toString() {
        return "Session: " + userName;
    }

    public void setServer(ChatServer server) {
        this.chatServer = server;
    }
}
