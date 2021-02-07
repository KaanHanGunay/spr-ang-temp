package tr.com.khg.caching;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

class ArchTest {

    @Test
    void servicesAndRepositoriesShouldNotDependOnWebLayer() {

        JavaClasses importedClasses = new ClassFileImporter()
            .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
            .importPackages("tr.com.khg.caching");

        noClasses()
            .that()
                .resideInAnyPackage("tr.com.khg.caching.service..")
            .or()
                .resideInAnyPackage("tr.com.khg.caching.repository..")
            .should().dependOnClassesThat()
                .resideInAnyPackage("..tr.com.khg.caching.web..")
        .because("Services and repositories should not depend on web layer")
        .check(importedClasses);
    }
}
