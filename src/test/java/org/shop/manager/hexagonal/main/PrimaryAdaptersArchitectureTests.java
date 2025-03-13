package org.shop.manager.hexagonal.main;

import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@AnalyzeClasses(packages = {"org.shop.manager.hexagonal"},
        importOptions = {ImportOption.DoNotIncludeTests.class, ImportOption.DoNotIncludeJars.class})
public class PrimaryAdaptersArchitectureTests extends AbstractArchUnitTest {

    @ArchTest
    ArchRule controllersShouldBeAnnotatedWithController = ArchRuleDefinition.classes()
            .that().haveSimpleNameEndingWith("Controller")
            .should().beAnnotatedWith(Controller.class)
            .orShould().beAnnotatedWith(RestController.class);

    @ArchTest
    ArchRule controllersShouldNotExistsOutsidePrimaryAdapters = ArchRuleDefinition.noClasses()
            .that().areAnnotatedWith(Controller.class)
            .or().areAnnotatedWith(RestController.class)
            .should().resideOutsideOfPackage(PRIMARY_ADAPTERS_PACKAGE);

    @ArchTest
    ArchRule publicMethodsShouldBeAnnotatedWithMapping = ArchRuleDefinition.methods()
            .that().arePublic()
            .and().areDeclaredInClassesThat().resideInAPackage(PRIMARY_ADAPTERS_PACKAGE)
            .and().areDeclaredInClassesThat().haveSimpleNameEndingWith("Controller")
            .and().areDeclaredInClassesThat().areAnnotatedWith(Controller.class)
            .or().areDeclaredInClassesThat().areAnnotatedWith(RestController.class)
            .should().beAnnotatedWith(RequestMapping.class)
            .orShould().beAnnotatedWith(GetMapping.class)
            .orShould().beAnnotatedWith(PostMapping.class)
            .orShould().beAnnotatedWith(PutMapping.class)
            .orShould().beAnnotatedWith(PatchMapping.class)
            .orShould().beAnnotatedWith(DeleteMapping.class)
            .orShould().beAnnotatedWith(ExceptionHandler.class);

}
