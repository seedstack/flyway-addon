/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.flyway.internal;

import com.google.common.collect.Lists;
import io.nuun.kernel.api.plugin.InitState;
import io.nuun.kernel.api.plugin.PluginException;
import io.nuun.kernel.api.plugin.context.InitContext;
import org.flywaydb.core.Flyway;
import org.seedstack.jdbc.internal.JdbcPlugin;
import org.seedstack.seed.cli.CliOption;
import org.seedstack.seed.core.internal.AbstractSeedTool;
import org.seedstack.seed.core.internal.jndi.JndiPlugin;

import java.util.Collection;
import java.util.Map;

public abstract class AbstractFlywayTool extends AbstractSeedTool {
    @CliOption(name = "d", longName = "datasource", mandatory = true, valueCount = 1)
    private String datasource;
    private Map<String, Flyway> flywayMap;

    @Override
    public abstract String toolName();

    @Override
    public Collection<Class<?>> toolPlugins() {
        return Lists.newArrayList(JdbcPlugin.class, JndiPlugin.class, FlywayPlugin.class);
    }

    @Override
    public Collection<Class<?>> dependencies() {
        return Lists.newArrayList(FlywayPlugin.class);
    }

    @Override
    public InitState initialize(InitContext initContext) {
        flywayMap = initContext.dependency(FlywayPlugin.class).getAllFlyway();
        return InitState.INITIALIZED;
    }

    protected Flyway getFlyway() {
        Flyway flyway = flywayMap.get(datasource);
        if (flyway == null) {
            throw new PluginException("Flyway is not available for datasource " + datasource);
        }
        return flyway;
    }

    protected String getDatasource() {
        return datasource;
    }
}