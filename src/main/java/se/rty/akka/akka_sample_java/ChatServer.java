/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package se.rty.akka.akka_sample_java;

import com.google.inject.Inject;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import se.scalablesolutions.akka.actor.annotation.inittransactionalstate;
import se.scalablesolutions.akka.actor.annotation.transactionrequired;
import se.scalablesolutions.akka.persistence.common.PersistentVector;
import se.scalablesolutions.akka.persistence.redis.RedisStorage;

/**
 *
 * @author bobo
 */
@transactionrequired
public class ChatServer {

    @Inject
    public ChatServer() {
    }

    private List<Message> messages = new ArrayList<Message>();
    private Set<ChatSession> sessions = new HashSet<ChatSession>();
    private PersistentVector<byte[]> storage;

    @inittransactionalstate
    public void init() {
        storage = RedisStorage.getVector("Nils");
    }

    public void setStorage(PersistentVector<byte[]> storage) {
        this.storage = storage;
    }
    

    public void login(ChatSession chatSession) {
        sessions.add(chatSession);
    }

    public List<Message> getLog() {
        return messages;
    }

    public void sendMessage(Message message) {
        storeMessage(message);
        for (ChatSession chatSession : sessions) {
            chatSession.recieveMessage(message);
        }
    }

    private void storeMessage(Message message) {
        messages.add(message);
        storage.add(message.toString().getBytes());
        storage.commit();
    }
}


