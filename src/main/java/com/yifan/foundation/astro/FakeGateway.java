package com.yifan.foundation.astro;

import com.yifan.foundation.base.Assignment;
import com.yifan.foundation.base.AstroResponse;
import com.yifan.foundation.base.Gateway;

import java.util.List;

/**
 * package_name: com.yifan.foundation.astro
 * author: wyifa
 * date: 2024/8/22
 * description:
 * demonstrate how Mockito generate a class that implement the gateway interface
 * and use the withReturn to fake the getResponse method returns
 * this class is either a mock or a stub, depending on how it's being used.
 * If you also want to verify afterward that service called the getResponse method
 * on the gateway exactly once, it will be considered mock.
 */
public class FakeGateway implements Gateway<AstroResponse> {
    @Override
    public AstroResponse getResponse() {
        return new AstroResponse(12, "Success",
                List.of(new Assignment("Oleg Kononenko", "ISS"),
                        new Assignment("Nikolai Chub", "ISS"),
                        new Assignment("Tracy Caldwell Dyson", "ISS"),
                        new Assignment("Matthew Dominick", "ISS"),
                        new Assignment("Michael Barratt", "Rocinante"),
                        new Assignment("Jeanett Epps", "Rocinante"),
                        new Assignment("Alexander Grebenkin", "ISS"),
                        new Assignment("Butch Wilmore", "Voyager"),
                        new Assignment("Sunita Williams", "Jupiter 2"),
                        new Assignment("Li Guangsu", "Tiangong"),
                        new Assignment("Li Cong", "Tiangong"),
                        new Assignment("Ye Guangfu", "Tiangong")));
    }
}
