/*
 * Copyright © 2013-2020, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.flyway.internal;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import io.nuun.kernel.api.plugin.InitState;
import io.nuun.kernel.api.plugin.context.InitContext;
import org.flywaydb.core.Flyway;
import org.seedstack.flyway.FlywayConfig;
import org.seedstack.flyway.FlywayConfig.DataSourceConfig;
import org.seedstack.flyway.spi.FlywayProvider;
import org.seedstack.seed.core.internal.AbstractSeedPlugin;
import org.seedstack.shed.ClassLoaders;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;

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
            LOGGER.info("Ignoring Flyway migration for datasource without scripts {}", dataSourceName);
            return;
        }
        if (dataSourceConf != null) {
            if (!dataSourceConf.isEnabled()) {
                // no migration if explicitly disabled
                LOGGER.info("Flyway migration is disabled for datasource {}", dataSourceName);
                return;
            }
            if (!Strings.isNullOrEmpty(dataSourceConf.getOptions().getBaselineVersion())) {
                LOGGER.info("Baselining datasource {} to {}", dataSourceName, flyway.getBaselineVersion());
                flyway.baseline();
            }
        }
        LOGGER.info("Migrating datasource {} to {}", dataSourceName, flyway.getTarget());
        flyway.migrate();
    }


    private boolean locationExists(Flyway flyway) {
        for (String location : flyway.getLocations()) {
            if (location.startsWith(CLASSPATH_PREFIX)) {
                if (ClassLoaders.findMostCompleteClassLoader().getResource(location.substring(CLASSPATH_PREFIX.length())) != null) {
                    return true;
                }
            } else if (location.startsWith(FILESYSTEM_PREFIX)) {
                if (new File(location.substring(FILESYSTEM_PREFIX.length())).exists()) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public Optional<Flyway> getFlyway(String name) {
        return Optional.ofNullable(flywayPlugin.getAllFlyway().get(name));
    }
}
