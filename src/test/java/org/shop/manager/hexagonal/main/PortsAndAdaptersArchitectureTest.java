package org.shop.manager.hexagonal.main;

import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

import static com.tngtech.archunit.library.Architectures.layeredArchitecture;


@AnalyzeClasses(packages = {"org.shop.manager.hexagonal"},
        importOptions = {ImportOption.DoNotIncludeTests.class, ImportOption.DoNotIncludeJars.class})
public class PortsAndAdaptersArchitectureTest extends AbstractArchUnitTest {

    private static final String MAIN = "main";
    private static final String APPLICATION = "application";
    private static final String DOMAIN = "domain";
    private static final String PRIMARY_ADAPTERS = "primary-adapters";
    private static final String SECONDARY_ADAPTERS = "secondary-adapters";
    private static final String VOCABULARY = "vocabulary";

    @ArchTest
    ArchRule layer_dependencies_are_respected = layeredArchitecture().consideringAllDependencies()
            .layer(MAIN).definedBy(MAIN_PACKAGE)
            .layer(APPLICATION).definedBy(APPLICATION_PACKAGE)
            .layer(DOMAIN).definedBy(DOMAIN_PACKAGE)
            .layer(PRIMARY_ADAPTERS).definedBy(PRIMARY_ADAPTERS_PACKAGE)
            .layer(SECONDARY_ADAPTERS).definedBy(SECONDARY_ADAPTERS_PACKAGE)
            .layer(VOCABULARY).definedBy(VOCABULARY_PACKAGE)

            .whereLayer(APPLICATION).mayOnlyBeAccessedByLayers(PRIMARY_ADAPTERS, MAIN)
            .whereLayer(PRIMARY_ADAPTERS).mayOnlyBeAccessedByLayers(APPLICATION, DOMAIN, MAIN)
            .whereLayer(DOMAIN).mayOnlyBeAccessedByLayers(APPLICATION, PRIMARY_ADAPTERS, SECONDARY_ADAPTERS, MAIN)
            .whereLayer(SECONDARY_ADAPTERS).mayOnlyBeAccessedByLayers(APPLICATION, DOMAIN, MAIN)
            .whereLayer(VOCABULARY).mayOnlyBeAccessedByLayers(APPLICATION, PRIMARY_ADAPTERS, SECONDARY_ADAPTERS, DOMAIN, MAIN);

}
