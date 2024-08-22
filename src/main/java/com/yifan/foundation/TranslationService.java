package com.yifan.foundation;

/**
 * package_name: com.yifan.foundation
 * author: wyifa
 * date: 2024/8/22
 * description:
 */
public interface TranslationService {
    default String translate(String text,
                             String sourceLan,
                             String targetLan) {
        return text;
    }
}
