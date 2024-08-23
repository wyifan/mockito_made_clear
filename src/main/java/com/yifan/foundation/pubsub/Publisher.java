package com.yifan.foundation.pubsub;

import java.util.ArrayList;
import java.util.List;

/**
 * package_name: com.yifan.foundation.pubsub
 * author: wyifa
 * date: 2024/8/23
 * description:
 */
public class Publisher {
    private final List<Subscriber> subscribers = new ArrayList<>();

    public void addSubscriber(Subscriber subscriber) {
        subscribers.add(subscriber);
    }

    public void send(String message) {
        for (Subscriber subscriber : subscribers) {
            try {
                subscriber.onNext(message);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public void sendParallel(String message) {
        subscribers.parallelStream()
                .forEach(sub -> {
                    try {
                        sub.onNext(message);
                    } catch (Exception ignored) {
                        System.out.println(ignored.getMessage());
                    }
                });
    }
}
