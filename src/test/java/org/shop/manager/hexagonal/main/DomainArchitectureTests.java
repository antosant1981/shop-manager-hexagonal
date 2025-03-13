package org.shop.manager.hexagonal.main;

import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition;

@AnalyzeClasses(packages = {"org.shop.manager.hexagonal"},
        importOptions = {ImportOption.DoNotIncludeTests.class})
public class DomainArchitectureTests extends AbstractArchUnitTest {

    @ArchTest
    ArchRule domainPackageOnlyDependsItselfOrStdLib = ArchRuleDefinition.classes()
            .that().resideInAPackage(DOMAIN_PACKAGE)
            .should().onlyDependOnClassesThat().resideInAnyPackage(DOMAIN_PACKAGE, VOCABULARY_PACKAGE, "java..","lombok..");

}
