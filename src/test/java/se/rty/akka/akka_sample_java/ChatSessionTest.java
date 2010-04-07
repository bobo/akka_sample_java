/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package se.rty.akka.akka_sample_java;

import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

/**
 *
 * @author  bobo
 */
public class ChatSessionTest {

    public ChatSessionTest() {
    }

    @Test
    public void whenSendingMessagesTheyAreStoredInTheLog() {
        ChatSession session = new ChatSession();
        session.setUserName("Nisse");
        session.setServer(mock(ChatServer.class));
        session.sendMessage("Hej");
        List<Message> messages = session.getLog();
        assertEquals("Hej", messages.get(0).getMessage());

    }

    @Test
    public void whenISendMessageMyUsernameIsInMessage() {
        ChatSession session = new ChatSession();
        session.setUserName("Nisse");
        session.setServer(mock(ChatServer.class));
        session.sendMessage("Hej");

        List<Message> messages = session.getLog();
        assertEquals("Nisse", messages.get(0).getUserName());
    }
}
