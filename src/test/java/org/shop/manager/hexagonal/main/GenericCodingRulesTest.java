package org.shop.manager.hexagonal.main;

import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition;
import com.tngtech.archunit.library.GeneralCodingRules;


@AnalyzeClasses(packages = {"org.shop.manager.hexagonal"},
        importOptions = {ImportOption.DoNotIncludeTests.class, ImportOption.DoNotIncludeArchives.class})
public class GenericCodingRulesTest {

    @ArchTest
    ArchRule shouldNotUseStandardStreams = ArchRuleDefinition.noClasses()
            .should(GeneralCodingRules.ACCESS_STANDARD_STREAMS);

    @ArchTest
    ArchRule shouldNotThrowStandardExceptions = ArchRuleDefinition.noClasses()
            .should(GeneralCodingRules.THROW_GENERIC_EXCEPTIONS);

    @ArchTest
    ArchRule shouldNotUseStandardLogging = ArchRuleDefinition.noClasses()
            .should(GeneralCodingRules.USE_JAVA_UTIL_LOGGING);

    @ArchTest
    ArchRule shouldNotUseJodaTime = ArchRuleDefinition.noClasses()
            .should(GeneralCodingRules.USE_JODATIME);

    @ArchTest
    ArchRule shouldNotUseInjectionAnnotations = ArchRuleDefinition.noFields()
            .should(GeneralCodingRules.BE_ANNOTATED_WITH_AN_INJECTION_ANNOTATION);

}
