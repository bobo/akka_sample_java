/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package se.rty.akka.akka_sample_java;

import org.junit.Test;
import se.scalablesolutions.akka.persistence.common.PersistentVector;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class ChatServerTest {

    @Test
    public void when_on_a_server_and_sending_a_message_it_should_be_stored_in_server() {
        final ChatServer server = new ChatServer();
        server.setStorage(mock(PersistentVector.class));
        final ChatSession chatSession = new ChatSession();
        chatSession.setUserName("nils");
        chatSession.setServer(server);
        server.login(chatSession);
        chatSession.sendMessage("Hej");
        assertEquals(new Message("nils", "Hej"), server.getLog().get(0));
    }

    @Test
    public void when_on_a_server_and_someone_else_sends_a_message_it_ends_up_in_my_log() {
        final ChatServer server = new ChatServer();
        server.setStorage(mock(PersistentVector.class));
        final ChatSession chatSession = new ChatSession();
        final ChatSession chatSession2 = new ChatSession();
        chatSession.setUserName("nils");
        chatSession.setServer(server);
        chatSession2.setUserName("kalle");
        chatSession2.setServer(server);
        server.login(chatSession);
        server.login(chatSession2);
        chatSession.sendMessage("Hej");
        assertEquals("Hej", chatSession2.getLog().get(0).getMessage());

    }
}
