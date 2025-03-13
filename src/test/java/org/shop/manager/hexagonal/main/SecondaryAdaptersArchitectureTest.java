package org.shop.manager.hexagonal.main;

import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition;
import org.springframework.stereotype.Component;


@AnalyzeClasses(packages = {"org.shop.manager.hexagonal"},
        importOptions = {ImportOption.DoNotIncludeTests.class})
public class SecondaryAdaptersArchitectureTest extends AbstractArchUnitTest {

    @ArchTest
    ArchRule repositoriesShouldNotBeAnnotated = ArchRuleDefinition.classes()
            .that().haveSimpleNameEndingWith("Repository")
            .and().areNotInterfaces()
            .should().notBeMetaAnnotatedWith(Component.class);

    @ArchTest
    ArchRule repositoryImplementationsShouldResideInSecondaryAdapters = ArchRuleDefinition.noClasses()
            .that().haveSimpleNameEndingWith("Repository")
            .and().areNotInterfaces()
            .should().resideOutsideOfPackage(SECONDARY_ADAPTERS_PACKAGE);
}
