/*
 * Copyright © 2013-2020, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.flyway;

import org.flywaydb.core.api.callback.FlywayCallback;
import org.flywaydb.core.api.resolver.MigrationResolver;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class FlywayConfigOptions {
    private String[] locations;
    private String[] schemas;
    private String baselineVersion;
    private String baselineDescription;
    private String table;
    private String sqlMigrationPrefix;
    private String repeatableSqlMigrationPrefix;
    private String sqlMigrationSeparator;
    private String sqlMigrationSuffix;
    private String encoding;
    private String placeholderPrefix;
    private String placeholderSuffix;
    private String target;
    private String installedBy;
    private Class<? extends MigrationResolver>[] resolvers;
    private Class<? extends FlywayCallback>[] callbacks;
    private Map<String, String> placeholders;
    private Boolean placeholderReplacement;
    private Boolean skipDefaultResolvers;
    private Boolean skipDefaultCallbacks;
    private Boolean outOfOrder;
    private Boolean validateOnMigrate;
    private Boolean cleanOnValidationError;
    private Boolean mixed;
    private Boolean group;
    private Boolean ignoreMissingMigrations;
    private Boolean ignoreFutureMigrations;
    private Boolean cleanDisabled;
    private Boolean baselineOnMigrate;

    public String[] getLocations() {
        return locations == null ? null : locations.clone();
    }

    public void setLocations(String[] locations) {
        this.locations = locations == null ? null : locations.clone();
    }

    public String[] getSchemas() {
        return schemas == null ? null : schemas.clone();
    }

    public void setSchemas(String[] schemas) {
        this.schemas = schemas == null ? null : schemas.clone();
    }

    public String getBaselineVersion() {
        return baselineVersion;
    }

    public void setBaselineVersion(String baselineVersion) {
        this.baselineVersion = baselineVersion;
    }

    public String getBaselineDescription() {
        return baselineDescription;
    }

    public void setBaselineDescription(String baselineDescription) {
        this.baselineDescription = baselineDescription;
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

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getInstalledBy() {
        return installedBy;
    }

    public void setInstalledBy(String installedBy) {
        this.installedBy = installedBy;
    }

    public Class<? extends MigrationResolver>[] getResolvers() {
        return resolvers == null ? null : resolvers.clone();
    }

    public void setResolvers(Class<? extends MigrationResolver>[] resolvers) {
        this.resolvers = resolvers == null ? null : resolvers.clone();
    }

    public Class<? extends FlywayCallback>[] getCallbacks() {
        return callbacks == null ? null : callbacks.clone();
    }

    public void setCallbacks(Class<? extends FlywayCallback>[] callbacks) {
        this.callbacks = callbacks.clone();
    }

    public Map<String, String> getPlaceholders() {
        return placeholders == null ? null : Collections.unmodifiableMap(placeholders);
    }

    public void setPlaceholders(Map<String, String> placeholders) {
        this.placeholders = placeholders == null ? null : new HashMap<>(placeholders);
    }

    public Boolean getPlaceholderReplacement() {
        return placeholderReplacement;
    }

    public void setPlaceholderReplacement(Boolean placeholderReplacement) {
        this.placeholderReplacement = placeholderReplacement;
    }

    public Boolean getSkipDefaultResolvers() {
        return skipDefaultResolvers;
    }

    public void setSkipDefaultResolvers(Boolean skipDefaultResolvers) {
        this.skipDefaultResolvers = skipDefaultResolvers;
    }

    public Boolean getSkipDefaultCallbacks() {
        return skipDefaultCallbacks;
    }

    public void setSkipDefaultCallbacks(Boolean skipDefaultCallbacks) {
        this.skipDefaultCallbacks = skipDefaultCallbacks;
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
}
