package ch.eddjos.qualitool.greeting;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicLong;

@RestController
public class GreetingController {
    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    @GetMapping(value="/greeting",produces = "application/json")
    public Greeting greeting() {
        return new Greeting(counter.incrementAndGet(),
                String.format(template, "World"));
    }
}
