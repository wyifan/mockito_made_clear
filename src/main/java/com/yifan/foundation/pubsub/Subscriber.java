package com.yifan.foundation.pubsub;

/**
 * package_name: com.yifan.foundation.pubsub
 * author: wyifa
 * date: 2024/8/23
 * description:
 */
public interface Subscriber {
    void onNext(String message);
}
