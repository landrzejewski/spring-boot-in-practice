package pl.fullstackdeveloper.payments;

import org.junit.jupiter.api.Test;
import org.springframework.modulith.core.ApplicationModules;
import pl.fullstackdeveloper.Application;

class ApplicationModularityTests {

    @Test
    void testModules() {
        var modules = ApplicationModules.of(Application.class);
        modules.verify();
        modules.forEach(System.out::println);
    }

}
