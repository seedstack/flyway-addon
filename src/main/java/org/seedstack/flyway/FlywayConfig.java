/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.flyway;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.validation.constraints.NotNull;

import org.seedstack.coffig.Config;
import org.seedstack.coffig.SingleValue;

@Config("flyway")
public class FlywayConfig {
	private Map<String, FlywayDataSourceConfig> datasources = new HashMap<>();
	private String schemas;
	private String table;
	private String sqlMigrationPrefix;
	private String repeatableSqlMigrationPrefix;
	private String sqlMigrationSeparator;
	private String sqlMigrationSuffix;
	private String encoding;
	private Boolean placeholderReplacement;
	private Map<String, String> placeholders = new HashMap<String, String>();
	private String placeholderPrefix;
	private String placeholderSuffix;
	private String resolvers;
	private Boolean skipDefaultCallResolvers;
	private String callbacks;
	private Boolean skipDefaultCallbacks;
	private String target;
	private Boolean outOfOrder;
	private Boolean validateOnMigrate;
	private Boolean cleanOnValidationError;
	private Boolean mixed;
	private Boolean group;
	private Boolean ignoreMissingMigrations;
	private Boolean ignoreFutureMigrations;
	private Boolean cleanDisabled;
	private Boolean baselineOnMigrate;
	private String installedBy;

	public Map<String, FlywayDataSourceConfig> getFlywayDataSources() {
		return Collections.unmodifiableMap(datasources);
	}

	public void addFlywayDataSource(String name, FlywayDataSourceConfig config) {
		datasources.put(name, config);
	}

	public static class FlywayDataSourceConfig {
		@NotNull
		@SingleValue
		private Boolean enabled = true;

		private String locations;
		private String baselineVersion;
		private String baselineDescription;

		public String getBaselineVersion() {
			return baselineVersion;
		}

		public void setBaselineVersion(String baselineVersion) {
			this.baselineVersion = baselineVersion;
		}

		public String getLocations() {
			return locations;
		}

		public void setLocations(String locations) {
			this.locations = locations;
		}

		public String getBaselineDescription() {
			return baselineDescription;
		}

		public Boolean getEnabled() {
			return enabled;
		}

		public void setEnabled(Boolean enabled) {
			this.enabled = enabled;
		}

		public void setBaselineDescription(String baselineDescription) {
			this.baselineDescription = baselineDescription;
		}
	}

	public Map<String, FlywayDataSourceConfig> getDatasources() {
		return datasources;
	}

	public void setDatasources(Map<String, FlywayDataSourceConfig> datasources) {
		this.datasources = datasources;
	}

	public String getSchemas() {
		return schemas;
	}

	public void setSchemas(String schemas) {
		this.schemas = schemas;
	}

	public String getTable() {
		return table;
	}

	public void setTable(String table) {
		this.table = table;
	}

	public String getSqlMigrationPrefix() {
		return sqlMigrationPrefix;
	}

	public void setSqlMigrationPrefix(String sqlMigrationPrefix) {
		this.sqlMigrationPrefix = sqlMigrationPrefix;
	}

	public String getRepeatableSqlMigrationPrefix() {
		return repeatableSqlMigrationPrefix;
	}

	public void setRepeatableSqlMigrationPrefix(String repeatableSqlMigrationPrefix) {
		this.repeatableSqlMigrationPrefix = repeatableSqlMigrationPrefix;
	}

	public String getSqlMigrationSeparator() {
		return sqlMigrationSeparator;
	}

	public void setSqlMigrationSeparator(String sqlMigrationSeparator) {
		this.sqlMigrationSeparator = sqlMigrationSeparator;
	}

	public String getSqlMigrationSuffix() {
		return sqlMigrationSuffix;
	}

	public void setSqlMigrationSuffix(String sqlMigrationSuffix) {
		this.sqlMigrationSuffix = sqlMigrationSuffix;
	}

	public String getEncoding() {
		return encoding;
	}

	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

	public Boolean getPlaceholderReplacement() {
		return placeholderReplacement;
	}

	public void setPlaceholderReplacement(Boolean placeholderReplacement) {
		this.placeholderReplacement = placeholderReplacement;
	}

	public Map<String, String> getPlaceholders() {
		return placeholders;
	}

	public void setPlaceholders(Map<String, String> placeholders) {
		this.placeholders = placeholders;
	}

	public String getPlaceholderPrefix() {
		return placeholderPrefix;
	}

	public void setPlaceholderPrefix(String placeholderPrefix) {
		this.placeholderPrefix = placeholderPrefix;
	}

	public String getPlaceholderSuffix() {
		return placeholderSuffix;
	}

	public void setPlaceholderSuffix(String placeholderSuffix) {
		this.placeholderSuffix = placeholderSuffix;
	}

	public String getResolvers() {
		return resolvers;
	}

	public void setResolvers(String resolvers) {
		this.resolvers = resolvers;
	}

	public Boolean getSkipDefaultCallResolvers() {
		return skipDefaultCallResolvers;
	}

	public void setSkipDefaultCallResolvers(Boolean skipDefaultCallResolvers) {
		this.skipDefaultCallResolvers = skipDefaultCallResolvers;
	}

	public String getCallbacks() {
		return callbacks;
	}

	public void setCallbacks(String callbacks) {
		this.callbacks = callbacks;
	}

	public Boolean getSkipDefaultCallbacks() {
		return skipDefaultCallbacks;
	}

	public void setSkipDefaultCallbacks(Boolean skipDefaultCallbacks) {
		this.skipDefaultCallbacks = skipDefaultCallbacks;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public Boolean getOutOfOrder() {
		return outOfOrder;
	}

	public void setOutOfOrder(Boolean outOfOrder) {
		this.outOfOrder = outOfOrder;
	}

	public Boolean getValidateOnMigrate() {
		return validateOnMigrate;
	}

	public void setValidateOnMigrate(Boolean validateOnMigrate) {
		this.validateOnMigrate = validateOnMigrate;
	}

	public Boolean getCleanOnValidationError() {
		return cleanOnValidationError;
	}

	public void setCleanOnValidationError(Boolean cleanOnValidationError) {
		this.cleanOnValidationError = cleanOnValidationError;
	}

	public Boolean getMixed() {
		return mixed;
	}

	public void setMixed(Boolean mixed) {
		this.mixed = mixed;
	}

	public Boolean getGroup() {
		return group;
	}

	public void setGroup(Boolean group) {
		this.group = group;
	}

	public Boolean getIgnoreMissingMigrations() {
		return ignoreMissingMigrations;
	}

	public void setIgnoreMissingMigrations(Boolean ignoreMissingMigrations) {
		this.ignoreMissingMigrations = ignoreMissingMigrations;
	}

	public Boolean getIgnoreFutureMigrations() {
		return ignoreFutureMigrations;
	}

	public void setIgnoreFutureMigrations(Boolean ignoreFutureMigrations) {
		this.ignoreFutureMigrations = ignoreFutureMigrations;
	}

	public Boolean getCleanDisabled() {
		return cleanDisabled;
	}

	public void setCleanDisabled(Boolean cleanDisabled) {
		this.cleanDisabled = cleanDisabled;
	}

	public Boolean getBaselineOnMigrate() {
		return baselineOnMigrate;
	}

	public void setBaselineOnMigrate(Boolean baselineOnMigrate) {
		this.baselineOnMigrate = baselineOnMigrate;
	}

	public String getInstalledBy() {
		return installedBy;
	}

	public void setInstalledBy(String installedBy) {
		this.installedBy = installedBy;
	}
}
