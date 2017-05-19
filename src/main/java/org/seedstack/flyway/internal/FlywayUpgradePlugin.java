/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.flyway.internal;

import io.nuun.kernel.api.plugin.InitState;
import io.nuun.kernel.api.plugin.context.InitContext;

import org.apache.commons.lang.StringUtils;
import org.flywaydb.core.Flyway;
import org.seedstack.flyway.FlywayConfig;
import org.seedstack.flyway.FlywayConfig.FlywayDataSourceConfig;
import org.seedstack.flyway.spi.FlywayProvider;
import org.seedstack.jdbc.spi.JdbcProvider;
import org.seedstack.seed.core.internal.jndi.JndiPlugin;

import com.google.common.collect.Lists;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;

/**
 * This plugin manage automatic migration of databases.
 */
public class FlywayUpgradePlugin extends FlywayPlugin implements FlywayProvider {

	@Override
	public String name() {
		return "flywayUpgrade";
	}

	@Override
	public Collection<Class<?>> dependencies() {
		return Lists.newArrayList(JdbcProvider.class, JndiPlugin.class, FlywayPlugin.class);
	}

	@Override
	public InitState initialize(InitContext initContext) {
		Map<String, FlywayDataSourceConfig> flywayDSConf = getConfiguration(FlywayConfig.class).getDatasources();	
		
		flywayMap = initContext.dependency(FlywayPlugin.class).getAllFlyway();
		flywayMap.forEach((dataSourceName, flyway) -> {
			FlywayDataSourceConfig dataSourceConf = flywayDSConf.get(dataSourceName);
			automaticMigration(flyway, dataSourceConf);
		});
		return InitState.INITIALIZED; 
	}

	public void automaticMigration(Flyway flyway,FlywayDataSourceConfig dataSourceConf) {		
		if (dataSourceConf != null) {
			if (!dataSourceConf.getEnabled()) {
				return; // NoMigration
			}
			if (dataSourceConf.getBaselineVersion() != null && !StringUtils.isEmpty(dataSourceConf.getBaselineVersion())) {
				flyway.baseline();
			}
		}  		
		flyway.migrate();
	}

	@Override
	public Optional<Flyway> getFlyway(String name) {		
		return Optional.ofNullable(flywayMap.get(name));
	}
}
