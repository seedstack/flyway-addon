/*
 * Copyright © 2013-2021, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.flyway.internal;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.sql.DataSource;

import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.callback.Callback;
import org.flywaydb.core.api.configuration.FluentConfiguration;
import org.flywaydb.core.api.migration.JavaMigration;
import org.flywaydb.core.api.resolver.MigrationResolver;
import org.seedstack.flyway.FlywayConfig;
import org.seedstack.flyway.FlywayConfigOptions;
import org.seedstack.jdbc.spi.JdbcProvider;
import org.seedstack.seed.core.internal.AbstractSeedPlugin;
import org.seedstack.shed.reflect.Classes;

import com.google.common.collect.Lists;

import io.nuun.kernel.api.plugin.InitState;
import io.nuun.kernel.api.plugin.context.InitContext;

/**
 * This plugin manage datasource and configuration used to access Flyway instance.
 */
public class FlywayPlugin extends AbstractSeedPlugin {
    private static final String FLYWAY_DEFAULT_PATH = "classpath:db/migration/";
    private Map<String, Flyway> flywayMap = new HashMap<>();

    @Override
    public String name() {
        return "flyway";
    }

    @Override
    public Collection<Class<?>> dependencies() {
        return Lists.newArrayList(JdbcProvider.class);
    }

    @Override
    public InitState initialize(InitContext initContext) {
        FlywayConfig flywayConfig = getConfiguration(FlywayConfig.class);
        initContext.dependency(JdbcProvider.class).getDataSourceNames().forEach((datasourceName) -> {
            DataSource datasource = initContext.dependency(JdbcProvider.class).getDataSource(datasourceName);
            if (datasource != null) {
                flywayMap.put(datasourceName, buildFlyway(flywayConfig, datasourceName, datasource));
            }
        });
        return InitState.INITIALIZED;
    }

    private Flyway buildFlyway(FlywayConfig flywayConfig, String dataSourceName,
            DataSource dataSource) {

        Flyway flyway = Flyway.configure().dataSource(dataSource).locations(FLYWAY_DEFAULT_PATH + dataSourceName).load();

        // Apply global options
        flyway = applyOptions(flyway, flywayConfig.getOptions());

        FlywayConfig.DataSourceConfig dataSourceConfig = flywayConfig.getDataSource(dataSourceName);
        if (dataSourceConfig != null) {
            // Apply datasource-specific options
            flyway = applyOptions(flyway, dataSourceConfig.getOptions());
        }

        return flyway;
    }

    private Flyway applyOptions(Flyway flyway, FlywayConfigOptions options) {

        FluentConfiguration instanceOptions = FlywayUtils.getFlywayBuilderFromInstance(flyway);

        Optional.ofNullable(options.getLocations()).ifPresent(instanceOptions::locations);
        Optional.ofNullable(options.getSchemas()).ifPresent(instanceOptions::schemas);
        Optional.ofNullable(options.getBaselineVersion()).ifPresent(instanceOptions::baselineVersion);
        Optional.ofNullable(options.getBaselineDescription()).ifPresent(instanceOptions::baselineDescription);
        Optional.ofNullable(options.getTable()).ifPresent(instanceOptions::table);
        Optional.ofNullable(options.getSqlMigrationPrefix()).ifPresent(instanceOptions::sqlMigrationPrefix);
        Optional.ofNullable(options.getRepeatableSqlMigrationPrefix()).ifPresent(instanceOptions::repeatableSqlMigrationPrefix);
        Optional.ofNullable(options.getSqlMigrationSeparator()).ifPresent(instanceOptions::sqlMigrationSeparator);
        Optional.ofNullable(options.getSqlMigrationSuffix()).ifPresent(instanceOptions::sqlMigrationSuffixes);
        Optional.ofNullable(options.getEncoding()).ifPresent(instanceOptions::encoding);
        Optional.ofNullable(options.getPlaceholderPrefix()).ifPresent(instanceOptions::placeholderPrefix);
        Optional.ofNullable(options.getPlaceholderSuffix()).ifPresent(instanceOptions::placeholderSuffix);
        Optional.ofNullable(options.getTarget()).ifPresent(instanceOptions::target);
        Optional.ofNullable(options.getInstalledBy()).ifPresent(instanceOptions::installedBy);
        Optional.ofNullable(options.getPlaceholders()).ifPresent(instanceOptions::placeholders);
        Optional.ofNullable(options.getPlaceholderReplacement()).ifPresent(instanceOptions::placeholderReplacement);
        Optional.ofNullable(options.getSkipDefaultResolvers()).ifPresent(instanceOptions::skipDefaultResolvers);
        Optional.ofNullable(options.getSkipDefaultCallbacks()).ifPresent(instanceOptions::skipDefaultCallbacks);
        Optional.ofNullable(options.getOutOfOrder()).ifPresent(instanceOptions::outOfOrder);
        Optional.ofNullable(options.getValidateOnMigrate()).ifPresent(instanceOptions::validateOnMigrate);
        Optional.ofNullable(options.getCleanOnValidationError()).ifPresent(instanceOptions::cleanOnValidationError);
        Optional.ofNullable(options.getMixed()).ifPresent(instanceOptions::mixed);
        Optional.ofNullable(options.getGroup()).ifPresent(instanceOptions::group);
        Optional.ofNullable(options.getIgnoreMissingMigrations()).ifPresent(instanceOptions::ignoreMissingMigrations);
        Optional.ofNullable(options.getIgnoreFutureMigrations()).ifPresent(instanceOptions::ignoreFutureMigrations);
        Optional.ofNullable(options.getCleanDisabled()).ifPresent(instanceOptions::cleanDisabled);
        Optional.ofNullable(options.getBaselineOnMigrate()).ifPresent(instanceOptions::baselineOnMigrate);
        Optional.ofNullable(options.getCreateSchemas()).ifPresent(instanceOptions::createSchemas);
        Optional.ofNullable(options.getDefaultSchema()).ifPresent(instanceOptions::defaultSchema);
        Optional.ofNullable(options.getIgnoreIgnoredMigrations()).ifPresent(instanceOptions::ignoreIgnoredMigrations);
        Optional.ofNullable(options.getIgnorePendingMigrations()).ifPresent(instanceOptions::ignorePendingMigrations);
        Optional.ofNullable(options.getInitSql()).ifPresent(instanceOptions::initSql);
        Optional.ofNullable(options.getLockRetryCount()).ifPresent(instanceOptions::lockRetryCount);
        Optional.ofNullable(options.getResourceProvider()).map(Classes::instantiateDefault).ifPresent(instanceOptions::resourceProvider);
        Optional.ofNullable(options.getTablespace()).ifPresent(instanceOptions::tablespace);
        Optional.ofNullable(options.getValidateMigrationNaming()).ifPresent(instanceOptions::validateMigrationNaming);

        // Instancing Class[] items
        Optional.ofNullable(options.getResolvers()).map(r -> this.instantiateDefault(r, MigrationResolver.class))
                .ifPresent(instanceOptions::resolvers);
        Optional.ofNullable(options.getCallbacks()).map(c -> this.instantiateDefault(c, Callback.class))
                .ifPresent(instanceOptions::callbacks);
        Optional.ofNullable(options.getJavaMigrations()).map(j -> this.instantiateDefault(j, JavaMigration.class))
                .ifPresent(instanceOptions::javaMigrations);

        return instanceOptions.load();

    }

    @SuppressWarnings("unchecked")
    private <U> U[] instantiateDefault(Class<? extends U>[] classes, Class<U> baseClass) {
        return Arrays.stream(classes).map(Classes::instantiateDefault).toArray(size -> (U[]) Array.newInstance(baseClass, size));
    }

    Map<String, Flyway> getAllFlyway() {
        return Collections.unmodifiableMap(flywayMap);
    }
}
