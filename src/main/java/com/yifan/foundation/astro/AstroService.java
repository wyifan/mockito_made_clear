package com.yifan.foundation.astro;

import com.yifan.foundation.base.Assignment;
import com.yifan.foundation.base.AstroResponse;
import com.yifan.foundation.base.Gateway;

import java.util.Map;
import java.util.stream.Collectors;

/**
 * package_name: com.yifan.foundation.astro
 * author: wyifa
 * date: 2024/8/22
 * description:
 */
public class AstroService {
    private final Gateway<AstroResponse> gateway;

    public AstroService(Gateway<AstroResponse> gateway) {
        this.gateway = gateway;
    }

    public Map<String, Long> getAstroData() {
        AstroResponse response = gateway.getResponse();
        return groupByCraft(response);
    }

    private Map<String, Long> groupByCraft(AstroResponse response) {
        return response.people().stream()
                .collect(
                        Collectors.groupingBy(Assignment::craft, Collectors.counting()));
    }
}
