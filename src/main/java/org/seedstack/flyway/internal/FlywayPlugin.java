/*
 * Copyright © 2013-2020, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.flyway.internal;

import com.google.common.collect.Lists;
import io.nuun.kernel.api.plugin.InitState;
import io.nuun.kernel.api.plugin.context.InitContext;
import org.flywaydb.core.Flyway;
import org.seedstack.flyway.FlywayConfig;
import org.seedstack.flyway.FlywayConfigOptions;
import org.seedstack.jdbc.spi.JdbcProvider;
import org.seedstack.seed.core.internal.AbstractSeedPlugin;
import org.seedstack.shed.reflect.Classes;

import javax.sql.DataSource;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

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
                flywayMap.put(datasourceName,
                        buildFlyway(
                                flywayConfig,
                                datasourceName,
                                datasource
                        )
                );
            }
        });
        return InitState.INITIALIZED;
    }

    private Flyway buildFlyway(FlywayConfig flywayConfig, String dataSourceName, DataSource dataSource) {
        Flyway flyway = new Flyway();
        flyway.setDataSource(dataSource);
        flyway.setLocations(FLYWAY_DEFAULT_PATH + dataSourceName);

        // Apply global options
        applyOptions(flyway, flywayConfig.getOptions());

        FlywayConfig.DataSourceConfig dataSourceConfig = flywayConfig.getDataSource(dataSourceName);
        if (dataSourceConfig != null) {
            // Apply datasource-specific options
            applyOptions(flyway, dataSourceConfig.getOptions());
        }

        return flyway;
    }

    private void applyOptions(Flyway flyway, FlywayConfigOptions options) {
        Optional.ofNullable(options.getLocations()).ifPresent(flyway::setLocations);
        Optional.ofNullable(options.getSchemas()).ifPresent(flyway::setSchemas);
        Optional.ofNullable(options.getBaselineVersion()).ifPresent(flyway::setBaselineVersionAsString);
        Optional.ofNullable(options.getBaselineDescription()).ifPresent(flyway::setBaselineDescription);
        Optional.ofNullable(options.getTable()).ifPresent(flyway::setTable);
        Optional.ofNullable(options.getSqlMigrationPrefix()).ifPresent(flyway::setSqlMigrationPrefix);
        Optional.ofNullable(options.getRepeatableSqlMigrationPrefix()).ifPresent(flyway::setRepeatableSqlMigrationPrefix);
        Optional.ofNullable(options.getSqlMigrationSeparator()).ifPresent(flyway::setSqlMigrationSeparator);
        Optional.ofNullable(options.getSqlMigrationSuffix()).ifPresent(flyway::setSqlMigrationSuffix);
        Optional.ofNullable(options.getEncoding()).ifPresent(flyway::setEncoding);
        Optional.ofNullable(options.getPlaceholderPrefix()).ifPresent(flyway::setPlaceholderPrefix);
        Optional.ofNullable(options.getPlaceholderSuffix()).ifPresent(flyway::setPlaceholderSuffix);
        Optional.ofNullable(options.getTarget()).ifPresent(flyway::setTargetAsString);
        Optional.ofNullable(options.getInstalledBy()).ifPresent(flyway::setInstalledBy);
        Optional.ofNullable(options.getResolvers()).map(this::instantiateDefault).ifPresent(flyway::setResolvers);
        Optional.ofNullable(options.getCallbacks()).map(this::instantiateDefault).ifPresent(flyway::setCallbacks);
        Optional.ofNullable(options.getPlaceholders()).ifPresent(flyway::setPlaceholders);
        Optional.ofNullable(options.getPlaceholderReplacement()).ifPresent(flyway::setPlaceholderReplacement);
        Optional.ofNullable(options.getSkipDefaultResolvers()).ifPresent(flyway::setSkipDefaultResolvers);
        Optional.ofNullable(options.getSkipDefaultCallbacks()).ifPresent(flyway::setSkipDefaultCallbacks);
        Optional.ofNullable(options.getOutOfOrder()).ifPresent(flyway::setOutOfOrder);
        Optional.ofNullable(options.getValidateOnMigrate()).ifPresent(flyway::setValidateOnMigrate);
        Optional.ofNullable(options.getCleanOnValidationError()).ifPresent(flyway::setCleanOnValidationError);
        Optional.ofNullable(options.getMixed()).ifPresent(flyway::setMixed);
        Optional.ofNullable(options.getGroup()).ifPresent(flyway::setGroup);
        Optional.ofNullable(options.getIgnoreMissingMigrations()).ifPresent(flyway::setIgnoreMissingMigrations);
        Optional.ofNullable(options.getIgnoreFutureMigrations()).ifPresent(flyway::setIgnoreFutureMigrations);
        Optional.ofNullable(options.getCleanDisabled()).ifPresent(flyway::setCleanDisabled);
        Optional.ofNullable(options.getBaselineOnMigrate()).ifPresent(flyway::setBaselineOnMigrate);
    }

    @SuppressWarnings("unchecked")
    private <U> U[] instantiateDefault(Class<? extends U>[] classes) {
        return Arrays.stream(classes).map(Classes::instantiateDefault).toArray(size -> (U[]) new Object[size]);
    }

    Map<String, Flyway> getAllFlyway() {
        return Collections.unmodifiableMap(flywayMap);
    }
}
