/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package se.rty.akka.akka_sample_java;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import java.util.Date;
import net.lag.logging.Logger;
import org.junit.Before;
import org.junit.Test;
import se.scalablesolutions.akka.config.ActiveObjectConfigurator;
import se.scalablesolutions.akka.config.Config;
import se.scalablesolutions.akka.config.JavaConfig.AllForOne;
import se.scalablesolutions.akka.config.JavaConfig.Component;
import se.scalablesolutions.akka.config.JavaConfig.LifeCycle;
import se.scalablesolutions.akka.config.JavaConfig.Permanent;
import se.scalablesolutions.akka.config.JavaConfig.RemoteAddress;
import se.scalablesolutions.akka.config.JavaConfig.RestartStrategy;
import se.scalablesolutions.akka.dispatch.Dispatchers;
import se.scalablesolutions.akka.dispatch.MessageDispatcher;
import static org.junit.Assert.*;

/**
 *
 * @author bobo
 */
public class IntegrationTest {

    ActiveObjectConfigurator conf = new ActiveObjectConfigurator();
    Module m;

    @Before
    public void setup() {
//        Config.config();
        MessageDispatcher dispatcher = Dispatchers.newExecutorBasedEventDrivenDispatcher("test");
        RemoteAddress address = new RemoteAddress("localhost", 11234);
        m = new AbstractModule() {

            @Override
            protected void configure() {
                bind(ChatSession.class);
            }
        };
        conf.addExternalGuiceModule(m).configure(new RestartStrategy(new AllForOne(), 3, 5000, new Class[]{Exception.class}),
                new Component[]{
                    new Component(ChatServer.class, new LifeCycle(new Permanent()), 10000, address),}).inject().supervise();
    }

    @Test
    public void nils() throws InterruptedException {
        final ChatSession userOne = conf.getExternalDependency(ChatSession.class);
        userOne.setUserName("userOne");
        userOne.login();
        final ChatSession userTwo = conf.getExternalDependency(ChatSession.class);
        final ChatSession userThree = conf.getExternalDependency(ChatSession.class);
        userTwo.setUserName("userTwo");
        userTwo.login();
        Thread.sleep(100); //to ensure login is done
        userOne.sendMessage("Hello");
        userTwo.sendMessage("Another Message 1");
        userOne.sendMessage("Another Message 2");
        userThree.login();
        Thread.sleep(100);
        userTwo.sendMessage("Another Message 3");
        userOne.sendMessage("Another Message 4");
        userOne.sendMessage("Another Message 5");
        Thread.sleep(400); //wait for messages to finish
        assertEquals(6, userOne.getLog().size());
        assertEquals(6, userTwo.getLog().size());
        assertEquals(3, userThree.getLog().size());
        assertEquals(userTwo.getLog(), userOne.getLog());

    }
}
