/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.flyway;

import org.seedstack.coffig.Config;
import org.seedstack.coffig.SingleValue;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Config("flyway")
public class FlywayConfig extends FlywayConfigOptions {
    private Map<String, DataSourceConfig> datasources = new HashMap<>();

    public Map<String, DataSourceConfig> getDataSources() {
        return Collections.unmodifiableMap(datasources);
    }

    public void addDataSource(String name, DataSourceConfig config) {
        datasources.put(name, config);
    }

    public DataSourceConfig getDataSource(String datasourceName) {
        return datasources.get(datasourceName);
    }

    public static class DataSourceConfig extends FlywayConfigOptions {
        @SingleValue
        private boolean enabled = true;

        public boolean isEnabled() {
            return enabled;
        }

        public DataSourceConfig setEnabled(boolean enabled) {
            this.enabled = enabled;
            return this;
        }
    }
}
