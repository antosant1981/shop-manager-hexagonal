package org.shop.manager.hexagonal.main;

import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition;
import org.springframework.context.annotation.Configuration;

@AnalyzeClasses(packages = {"org.shop.manager.hexagonal"},
        importOptions = {ImportOption.DoNotIncludeTests.class, ImportOption.DoNotIncludeArchives.class})
public class MainArchitectureTests extends AbstractArchUnitTest {

    @ArchTest
    ArchRule configurationClassesShouldBeAnnotated = ArchRuleDefinition.classes()
            .that().haveSimpleNameEndingWith("Config")
            .should().beAnnotatedWith(Configuration.class);

    @ArchTest
    ArchRule configurationClassesShouldResideInMainPackage = ArchRuleDefinition.noClasses()
            .that().areAnnotatedWith(Configuration.class)
            .should().resideOutsideOfPackage(MAIN_PACKAGE);
}
