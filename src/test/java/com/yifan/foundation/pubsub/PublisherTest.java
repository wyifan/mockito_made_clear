package com.yifan.foundation.pubsub;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;

import static org.mockito.Mockito.*;

/**
 * package_name: com.yifan.foundation.pubsub
 * author: wyifa
 * date: 2024/8/23
 * description:
 */
class PublisherTest {

    public static final String HELLO_WORLD = "Hello 1";
    private final Publisher publisher = new Publisher();

    private final Subscriber subscriber1 = mock(Subscriber.class);
    private final Subscriber subscriber2 = mock(Subscriber.class);

    // add this annotation to init the test
    @BeforeEach
    void setUp() {
        publisher.addSubscriber(subscriber1);
        publisher.addSubscriber(subscriber2);
    }

    @Test
    @DisplayName("Test publish-subscriber pattern demo")
    void publisherSendsMessageToAllSubscribers() {
        publisher.send(HELLO_WORLD);

        verify(subscriber1).onNext(HELLO_WORLD);
        verify(subscriber2).onNext(HELLO_WORLD);
    }

    @Test
    @DisplayName("Test publish-subscriber order")
    void testSendInOrder() {

        publisher.send(HELLO_WORLD);

        InOrder inOrder = inOrder(subscriber1, subscriber2);

        inOrder.verify(subscriber1).onNext(HELLO_WORLD);
        inOrder.verify(subscriber2).onNext(HELLO_WORLD);
    }

    @Test
    @DisplayName("Test publish-subscriber send 2 messages")
    void publishSendMessageWithAPattern() {

        publisher.send(HELLO_WORLD);
        publisher.send("Hello 2");

        // check for anyString
        verify(subscriber1, times(2)).onNext(anyString());
        verify(subscriber2, times(2)).onNext(anyString());

        // check a specific pattern
        verify(subscriber1, times(2)).onNext(argThat((s -> s.matches("Hello \\d"))));
        verify(subscriber2, times(2)).onNext(argThat((s -> s.matches("Hello \\d"))));
    }

    @Test
    @DisplayName("Set subscriber throw an exception")
    void handleMisbehavingSubscribers() {
        // set subscriber1 throws an exception
        doThrow(RuntimeException.class).when(subscriber1).onNext(anyString());
        // legal, but redundant (it's the default)
        doNothing().when(subscriber2).onNext(anyString());

        publisher.send(HELLO_WORLD);
        publisher.send("Hello 2");

        // check for anyString
        verify(subscriber1, times(2)).onNext(anyString());
        verify(subscriber2, times(2)).onNext(anyString());
    }
}