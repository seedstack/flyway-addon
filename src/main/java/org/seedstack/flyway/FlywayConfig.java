/*
 * Copyright © 2013-2020, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.flyway;

import org.seedstack.coffig.Config;
import org.seedstack.coffig.SingleValue;

import javax.validation.constraints.NotNull;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Config("flyway")
public class FlywayConfig {
    private Map<String, DataSourceConfig> datasources = new HashMap<>();
    @NotNull
    private FlywayConfigOptions options = new FlywayConfigOptions();

    public Map<String, DataSourceConfig> getDataSources() {
        return Collections.unmodifiableMap(datasources);
    }

    public void addDataSource(String name, DataSourceConfig config) {
        datasources.put(name, config);
    }

    public DataSourceConfig getDataSource(String datasourceName) {
        return datasources.get(datasourceName);
    }

    public FlywayConfigOptions getOptions() {
        return options;
    }

    public FlywayConfig setOptions(FlywayConfigOptions options) {
        this.options = options;
        return this;
    }

    public static class DataSourceConfig {
        @SingleValue
        private boolean enabled = true;
        @NotNull
        private FlywayConfigOptions options = new FlywayConfigOptions();

        public boolean isEnabled() {
            return enabled;
        }

        public DataSourceConfig setEnabled(boolean enabled) {
            this.enabled = enabled;
            return this;
        }

        public FlywayConfigOptions getOptions() {
            return options;
        }

        public DataSourceConfig setOptions(FlywayConfigOptions options) {
            this.options = options;
            return this;
        }
    }
}
