package org.shop.manager.hexagonal.main;

import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition;
import org.springframework.stereotype.Component;

@AnalyzeClasses(packages = {"org.shop.manager.hexagonal"},
        importOptions = {ImportOption.DoNotIncludeTests.class, ImportOption.DoNotIncludeArchives.class})
public class ApplicationArchitectureTests extends AbstractArchUnitTest {

    @ArchTest
    ArchRule useCasesShouldResideInApplicationLayer = ArchRuleDefinition.noClasses()
            .that().haveSimpleNameEndingWith("UseCase")
            .should().resideOutsideOfPackage(APPLICATION_PACKAGE);

    @ArchTest
    ArchRule useCasesShouldNotBeAnnotated = ArchRuleDefinition.noClasses()
            .that().haveSimpleNameEndingWith("UseCase")
            .should().beMetaAnnotatedWith(Component.class);

    @ArchTest
    ArchRule queriesShouldResideInApplicationLayer = ArchRuleDefinition.noClasses()
            .that().haveSimpleNameEndingWith("Query")
            .should().resideOutsideOfPackage(APPLICATION_PACKAGE)
            .allowEmptyShould(true);

    @ArchTest
    ArchRule queriesShouldNotBeAnnotated = ArchRuleDefinition.noClasses()
            .that().haveSimpleNameEndingWith("Query")
            .should().beMetaAnnotatedWith(Component.class)
            .allowEmptyShould(true);

}
