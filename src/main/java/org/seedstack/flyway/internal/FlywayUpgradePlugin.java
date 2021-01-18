/*
 * Copyright © 2013-2020, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.flyway.internal;

import java.io.File;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;

import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.Location;
import org.flywaydb.core.internal.scanner.Scanner;
import org.seedstack.flyway.FlywayConfig;
import org.seedstack.flyway.FlywayConfig.DataSourceConfig;
import org.seedstack.flyway.spi.FlywayProvider;
import org.seedstack.seed.core.internal.AbstractSeedPlugin;
import org.seedstack.shed.ClassLoaders;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;

import io.nuun.kernel.api.plugin.InitState;
import io.nuun.kernel.api.plugin.context.InitContext;

/**
 * This plugin manage automatic migration of databases.
 */
public class FlywayUpgradePlugin extends AbstractSeedPlugin implements FlywayProvider {
    private static final Logger LOGGER = LoggerFactory.getLogger(FlywayUpgradePlugin.class);
    private static final String CLASSPATH_PREFIX = "classpath:";
    private static final String FILESYSTEM_PREFIX = "filesystem:";
    private FlywayPlugin flywayPlugin;

    @Override
    public String name() {
        return "flywayUpgrade";
    }

    @Override
    public Collection<Class<?>> dependencies() {
        return Lists.newArrayList(FlywayPlugin.class);
    }

    @Override
    public InitState initialize(InitContext initContext) {
        flywayPlugin = initContext.dependency(FlywayPlugin.class);
        Map<String, DataSourceConfig> flywayDSConf = getConfiguration(FlywayConfig.class).getDataSources();

        flywayPlugin.getAllFlyway().forEach((dataSourceName, flyway) -> {
            DataSourceConfig dataSourceConf = flywayDSConf.get(dataSourceName);
            automaticMigration(flyway, dataSourceName, dataSourceConf);
        });

        return InitState.INITIALIZED;
    }

    private void automaticMigration(Flyway flyway, String dataSourceName, DataSourceConfig dataSourceConf) {
        if (!locationExists(flyway)) {
            // no migration if no script is present
            LOGGER.info("Flyway migration for datasource {} has no scripts", dataSourceName);
        }
        if (dataSourceConf != null) {
            if (!dataSourceConf.isEnabled()) {
                // no migration if explicitly disabled
                LOGGER.info("Flyway migration is disabled for datasource {}", dataSourceName);
                return;
            }
            if (!Strings.isNullOrEmpty(dataSourceConf.getOptions().getBaselineVersion())) {
                LOGGER.info("Baselining datasource {} to {}", dataSourceName, flyway.getConfiguration().getBaselineVersion());
                flyway.baseline();
            }
        }
        LOGGER.info("Migrating datasource {} to {}", dataSourceName, flyway.getConfiguration().getTarget());
        flyway.migrate();
    }

    private boolean locationExists(Flyway flyway) {
        for (Location location : flyway.getConfiguration().getLocations()) {
            return checkLocationExistence(location);
        }
        return false;
    }

    private boolean checkLocationExistence(Location location) {
        LOGGER.debug("Checking '{}' to find migration scripts", location);
        if (CLASSPATH_PREFIX.equals(location.getPrefix())) {
            return (ClassLoaders.findMostCompleteClassLoader().getResource(location.getPath()) != null);
        }
        if (FILESYSTEM_PREFIX.equals(location.getPrefix())) {
            if (new File(location.getPath()).exists()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Optional<Flyway> getFlyway(String name) {
        return Optional.ofNullable(flywayPlugin.getAllFlyway().get(name));
    }
}
