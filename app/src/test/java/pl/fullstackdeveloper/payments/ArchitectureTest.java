package pl.fullstackdeveloper.payments;

import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption.DoNotIncludeTests;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.base.DescribedPredicate.alwaysTrue;
import static com.tngtech.archunit.core.domain.properties.HasName.Predicates.nameMatching;
import static com.tngtech.archunit.library.Architectures.layeredArchitecture;

class ArchitectureTest {

    @Test
    void layer_dependencies_are_respected() {
        var classes = new ClassFileImporter()
                .withImportOption(new DoNotIncludeTests())
                .importPackages("pl.fullstackdeveloper");

        /*noClasses().that()
                .resideInAPackage("..domain..")
                .should()
                .dependOnClassesThat().resideInAPackage("..application..")
                .check(classes);*/

        layeredArchitecture().consideringAllDependencies()
                .layer("Adapters").definedBy("pl.fullstackdeveloper.payments.adapters..")
                .layer("Application").definedBy("pl.fullstackdeveloper.payments.application..")
                .layer("Domain").definedBy("pl.fullstackdeveloper.payments.domain..")

                .whereLayer("Adapters")
                    .mayNotBeAccessedByAnyLayer()
                    .ignoreDependency(nameMatching(".*Configuration.*"), alwaysTrue())
                .whereLayer("Application")
                    .mayOnlyBeAccessedByLayers("Adapters")
                .check(classes);
    }

}
