package pl.fullstackdeveloper;

import org.junit.jupiter.api.Test;
import org.springframework.modulith.core.ApplicationModules;

class ApplicationModularityTests {

    @Test
    void testModules() {
        var modules = ApplicationModules.of(Application.class);
        modules.verify();
        modules.forEach(System.out::println);
    }

}
