package ze.delivery;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.core.importer.ImportOptions;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.lang.syntax.elements.ClassesShouldConjunction;
import org.junit.Test;
import org.springframework.web.bind.annotation.RestController;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;

public class ArchitectureTests {

    @Test
    public void applicationServicesShouldOnlyBeAccessedByControllers() {
        JavaClasses importedClasses = getImportedClasses();

        ArchRule packageRule = classes()
                .that()
                .resideInAPackage("..application..")
                .should()
                .onlyBeAccessed()
                .byClassesThat()
                .resideInAnyPackage("..application..");

        ArchRule classNameRule = classes()
                .that()
                .resideInAPackage("..application..")
                .and()
                .haveSimpleNameEndingWith("AppService")
                .should()
                .onlyBeAccessed()
                .byClassesThat()
                .haveSimpleNameEndingWith("Controller");

        packageRule.check(importedClasses);
        classNameRule.check(importedClasses);
    }

    @Test
    public void infraTierShouldOnlyBeAccessedByDomain() {
        JavaClasses importedClasses = getImportedClasses();

        ArchRule rule = classes()
                .that()
                .resideInAPackage("..infrastructure..")
                .should()
                .onlyBeAccessed()
                .byClassesThat()
                .resideInAnyPackage("..domain..", "..infrastructure..");

        rule.check(importedClasses);
    }

    @Test
    public void annotatedWithRestControllerShouldHaveControllerInName() {
        JavaClasses importedClasses = getImportedClasses();

        final ClassesShouldConjunction rule = classes()
                .that()
                .areAnnotatedWith(RestController.class)
                .should().haveSimpleNameEndingWith("Controller");

        rule.check(importedClasses);
    }

    private JavaClasses getImportedClasses() {
        return new ClassFileImporter(new ImportOptions().with(new ImportOption.DoNotIncludeTests())).importPackages("ze.delivery");
    }
}
