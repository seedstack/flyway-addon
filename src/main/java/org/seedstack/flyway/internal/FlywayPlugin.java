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
import org.seedstack.jdbc.spi.JdbcProvider;
import org.seedstack.seed.core.internal.AbstractSeedPlugin;
import org.seedstack.seed.core.internal.jndi.JndiPlugin;

import com.google.common.collect.Lists;

import javax.sql.DataSource;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This plugin manage datasource and configuration used to access Flyway instance.
 */
public class FlywayPlugin extends AbstractSeedPlugin {	
	protected static final String FLYWAY_PATH = "META-INF/sql/";
	protected Map<String, Flyway> flywayMap = new HashMap<>();

	@Override
	public String name() {
		return "flyway";
	}

	@Override
	public Collection<Class<?>> dependencies() {
		return Lists.newArrayList(JdbcProvider.class, JndiPlugin.class);
	}

	@Override
	public InitState initialize(InitContext initContext) {
		FlywayConfig flywayConfig = getConfiguration(FlywayConfig.class);		
		List<String> dataSourceNames = initContext.dependency(JdbcProvider.class).getDataSourceNames();
		
		dataSourceNames.forEach((datasourceName) -> {
			DataSource datasource = initContext.dependency(JdbcProvider.class).getDataSource(datasourceName);
			if (datasource != null) {
				flywayMap.put(datasourceName, buildFlyway(datasourceName, datasource, flywayConfig));
			}			
		});
		return InitState.INITIALIZED;
	}
	
	private Flyway buildFlyway(String datasourceName, DataSource datasource, FlywayConfig flywayConfig) {		
		String defaultLocations = FLYWAY_PATH + datasourceName;
		Flyway flyway = new Flyway();
		flyway.setDataSource(datasource);
		setFlywayGlobalParameter(flyway, flywayConfig);
		
		Map<String, FlywayDataSourceConfig> datasources = flywayConfig.getDatasources();
		FlywayDataSourceConfig flywayDatasourceConfig = null;
		if (!datasources.isEmpty()) {
			flywayDatasourceConfig  = datasources.get(datasourceName);
		} 

		if (flywayDatasourceConfig == null ) {
			flyway.setLocations(defaultLocations);
			return flyway;
		}
				
		if (flywayDatasourceConfig.getLocations() == null && StringUtils.isEmpty(flywayDatasourceConfig.getLocations())) {
			flywayDatasourceConfig.setLocations(defaultLocations);
		} 				
		flyway.setLocations(flywayDatasourceConfig.getLocations());	

		if (flywayDatasourceConfig.getBaselineVersion() != null && !StringUtils.isEmpty(flywayDatasourceConfig.getBaselineVersion())) {
			flyway.setBaselineVersionAsString(flywayDatasourceConfig.getBaselineVersion());
		}
		
		if (flywayDatasourceConfig.getBaselineDescription() != null && !StringUtils.isEmpty(flywayDatasourceConfig.getBaselineDescription())) {
			flyway.setBaselineDescription(flywayDatasourceConfig.getBaselineDescription());
		}	
		return flyway;		
	}
	
	private void setFlywayGlobalParameter(Flyway flyway, FlywayConfig flywayConfig) {
		if (flywayConfig.getSchemas() != null && !StringUtils.isEmpty(flywayConfig.getSchemas())) {
			flyway.setSchemas(flywayConfig.getSchemas());
		}		
		
		if (flywayConfig.getTable() != null && !StringUtils.isEmpty(flywayConfig.getTable())) {
			flyway.setTable(flywayConfig.getTable());
		}

		if (flywayConfig.getSqlMigrationPrefix() != null && !StringUtils.isEmpty(flywayConfig.getSqlMigrationPrefix())) {
			flyway.setSqlMigrationPrefix(flywayConfig.getSqlMigrationPrefix());
		}

		if (flywayConfig.getRepeatableSqlMigrationPrefix() != null && !StringUtils.isEmpty(flywayConfig.getRepeatableSqlMigrationPrefix())) {
			flyway.setRepeatableSqlMigrationPrefix(flywayConfig.getRepeatableSqlMigrationPrefix());
		}

		if (flywayConfig.getSqlMigrationSeparator() != null && !StringUtils.isEmpty(flywayConfig.getSqlMigrationSeparator())) {
			flyway.setSqlMigrationSeparator(flywayConfig.getSqlMigrationSeparator());
		}

		if (flywayConfig.getSqlMigrationSuffix() != null && !StringUtils.isEmpty(flywayConfig.getSqlMigrationSuffix())) {
			flyway.setSqlMigrationSuffix(flywayConfig.getSqlMigrationSuffix());
		}

		if (flywayConfig.getEncoding() != null && !StringUtils.isEmpty(flywayConfig.getEncoding())) {
			flyway.setEncoding(flywayConfig.getEncoding());
		}

		if (flywayConfig.getPlaceholderReplacement() != null) {
			flyway.setPlaceholderReplacement(flywayConfig.getPlaceholderReplacement());
		}

		if (!flywayConfig.getPlaceholders().isEmpty()) {
			flyway.setPlaceholders(flywayConfig.getPlaceholders());
		}

		if (flywayConfig.getPlaceholderPrefix() != null && !StringUtils.isEmpty(flywayConfig.getPlaceholderPrefix())) {
			flyway.setPlaceholderPrefix(flywayConfig.getPlaceholderPrefix());
		}

		if (flywayConfig.getPlaceholderSuffix() != null && !StringUtils.isEmpty(flywayConfig.getPlaceholderSuffix())) {
			flyway.setPlaceholderSuffix(flywayConfig.getPlaceholderSuffix());
		}

		if (flywayConfig.getResolvers() != null && !StringUtils.isEmpty(flywayConfig.getResolvers())) {
			flyway.setResolversAsClassNames(flywayConfig.getResolvers());
		}

		if (flywayConfig.getSkipDefaultCallResolvers() != null) {
			flyway.setSkipDefaultResolvers(flywayConfig.getSkipDefaultCallResolvers());
		}

		if (flywayConfig.getCallbacks() != null && !StringUtils.isEmpty(flywayConfig.getCallbacks())) {
			flyway.setCallbacksAsClassNames(flywayConfig.getCallbacks());
		}

		if (flywayConfig.getSkipDefaultCallbacks() != null) {
			flyway.setSkipDefaultCallbacks(flywayConfig.getSkipDefaultCallbacks());
		}

		if (flywayConfig.getTarget() != null && !StringUtils.isEmpty(flywayConfig.getTarget())) {
			flyway.setTargetAsString(flywayConfig.getTarget());
		}

		if (flywayConfig.getOutOfOrder() != null) {
			flyway.setOutOfOrder(flywayConfig.getOutOfOrder());
		}

		if (flywayConfig.getValidateOnMigrate() != null) {
			flyway.setValidateOnMigrate(flywayConfig.getValidateOnMigrate());
		}

		if (flywayConfig.getCleanOnValidationError() != null) {
			flyway.setCleanOnValidationError(flywayConfig.getCleanOnValidationError());
		}

		if (flywayConfig.getMixed() != null) {
			flyway.setCleanOnValidationError(flywayConfig.getMixed());
		}

		if (flywayConfig.getGroup() != null) {
			flyway.setCleanOnValidationError(flywayConfig.getGroup());
		}

		if (flywayConfig.getIgnoreMissingMigrations() != null) {
			flyway.setIgnoreMissingMigrations(flywayConfig.getIgnoreMissingMigrations());
		}

		if (flywayConfig.getIgnoreFutureMigrations() != null) {
			flyway.setIgnoreFutureMigrations(flywayConfig.getIgnoreFutureMigrations());
		}

		if (flywayConfig.getCleanDisabled() != null) {
			flyway.setCleanDisabled(flywayConfig.getCleanDisabled());
		}

		if (flywayConfig.getBaselineOnMigrate() != null) {
			flyway.setBaselineOnMigrate(flywayConfig.getBaselineOnMigrate());
		}

		if (flywayConfig.getInstalledBy() != null && !StringUtils.isEmpty(flywayConfig.getInstalledBy())) {
			flyway.setInstalledBy(flywayConfig.getInstalledBy());
		}
	}
		
    public Map<String, Flyway> getAllFlyway() {
        return flywayMap;
    }
}
