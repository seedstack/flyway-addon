/*
 * Copyright © 2013-2020, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.flyway;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.flywaydb.core.api.ResourceProvider;
import org.flywaydb.core.api.callback.Callback;
import org.flywaydb.core.api.migration.JavaMigration;
import org.flywaydb.core.api.resolver.MigrationResolver;

import com.google.common.collect.Lists;

public class FlywayConfigOptions {
    private String baselineDescription;
    private Boolean baselineOnMigrate;
    private String baselineVersion;
    private List<Class<? extends Callback>> callbacks = new ArrayList<>();
    private Boolean cleanDisabled;
    private Boolean cleanOnValidationError;
    private Boolean createSchemas;
    private String defaultSchema;
    private String encoding;
    private Boolean group;
    private Boolean ignoreFutureMigrations;
    private Boolean ignoreIgnoredMigrations;
    private Boolean ignoreMissingMigrations;
    private Boolean ignorePendingMigrations;
    private String initSql;
    private String installedBy;
    private List<Class<? extends JavaMigration>> javaMigrations = new ArrayList<>();
    private String[] locations;
    private Integer lockRetryCount;
    private Boolean mixed;
    private Boolean outOfOrder;
    private String placeholderPrefix;
    private Boolean placeholderReplacement;
    private Map<String, String> placeholders;
    private String placeholderSuffix;
    private String repeatableSqlMigrationPrefix;
    private List<Class<? extends MigrationResolver>> resolvers = new ArrayList<>();
    private Class<? extends ResourceProvider> resourceProvider;
    private String[] schemas;
    private Boolean skipDefaultCallbacks;
    private Boolean skipDefaultResolvers;
    private String sqlMigrationPrefix;
    private String sqlMigrationSeparator;
    private String sqlMigrationSuffix;
    private String table;
    private String tablespace;
    private String target;
    private Boolean validateMigrationNaming;
    private Boolean validateOnMigrate;

    public String getBaselineDescription() {
        return baselineDescription;
    }

    public Boolean getBaselineOnMigrate() {
        return baselineOnMigrate;
    }

    public String getBaselineVersion() {
        return baselineVersion;
    }

    @SuppressWarnings("unchecked")
    public Class<? extends Callback>[] getCallbacks() {
        if (this.callbacks.isEmpty()) {
            return null;
        }
        return this.callbacks.toArray(new Class[0]);
    }

    public Boolean getCleanDisabled() {
        return cleanDisabled;
    }

    public Boolean getCleanOnValidationError() {
        return cleanOnValidationError;
    }

    public Boolean getCreateSchemas() {
        return createSchemas;
    }

    public String getDefaultSchema() {
        return defaultSchema;
    }

    public String getEncoding() {
        return encoding;
    }

    public Boolean getGroup() {
        return group;
    }

    public Boolean getIgnoreFutureMigrations() {
        return ignoreFutureMigrations;
    }

    public Boolean getIgnoreIgnoredMigrations() {
        return ignoreIgnoredMigrations;

    }

    public Boolean getIgnoreMissingMigrations() {
        return ignoreMissingMigrations;
    }

    public Boolean getIgnorePendingMigrations() {
        return ignorePendingMigrations;
    }

    public String getInitSql() {
        return initSql;
    }

    public String getInstalledBy() {
        return installedBy;
    }

    @SuppressWarnings("unchecked")
    public Class<JavaMigration>[] getJavaMigrations() {
        if (javaMigrations.isEmpty()) {
            return null;
        }
        return this.javaMigrations.toArray(new Class[0]);
    }

    public String[] getLocations() {
        return locations == null ? null : locations.clone();
    }

    public Integer getLockRetryCount() {
        return lockRetryCount;
    }

    public Boolean getMixed() {
        return mixed;
    }

    public Boolean getOutOfOrder() {
        return outOfOrder;
    }

    public String getPlaceholderPrefix() {
        return placeholderPrefix;
    }

    public Boolean getPlaceholderReplacement() {
        return placeholderReplacement;
    }

    public Map<String, String> getPlaceholders() {
        return placeholders == null ? null : Collections.unmodifiableMap(placeholders);
    }

    public String getPlaceholderSuffix() {
        return placeholderSuffix;
    }

    public String getRepeatableSqlMigrationPrefix() {
        return repeatableSqlMigrationPrefix;
    }

    @SuppressWarnings("unchecked")
    public Class<? extends MigrationResolver>[] getResolvers() {
        if (this.resolvers.isEmpty()) {
            return null;
        }

        return this.resolvers.toArray(new Class[0]);
    }

    public Class<? extends ResourceProvider> getResourceProvider() {
        return resourceProvider;
    }

    public String[] getSchemas() {
        return schemas == null ? null : schemas.clone();
    }

    public Boolean getSkipDefaultCallbacks() {
        return skipDefaultCallbacks;
    }

    public Boolean getSkipDefaultResolvers() {
        return skipDefaultResolvers;
    }

    public String getSqlMigrationPrefix() {
        return sqlMigrationPrefix;
    }

    public String getSqlMigrationSeparator() {
        return sqlMigrationSeparator;
    }

    public String getSqlMigrationSuffix() {
        return sqlMigrationSuffix;
    }

    public String getTable() {
        return table;
    }

    public String getTablespace() {
        return tablespace;
    }

    public String getTarget() {
        return target;
    }

    public Boolean getValidateMigrationNaming() {
        return validateMigrationNaming;
    }

    public Boolean getValidateOnMigrate() {
        return validateOnMigrate;
    }

    public void setBaselineDescription(String baselineDescription) {
        this.baselineDescription = baselineDescription;
    }

    public void setBaselineOnMigrate(Boolean baselineOnMigrate) {
        this.baselineOnMigrate = baselineOnMigrate;
    }

    public void setBaselineVersion(String baselineVersion) {
        this.baselineVersion = baselineVersion;
    }

    public void setCallbacks(Class<? extends Callback>[] callbacks) {
        if (callbacks == null) {
            this.callbacks = new ArrayList<>();
        } else {
            this.callbacks = new ArrayList<>(Lists.newArrayList(callbacks));
        }
    }

    public void setCleanDisabled(Boolean cleanDisabled) {
        this.cleanDisabled = cleanDisabled;
    }

    public void setCleanOnValidationError(Boolean cleanOnValidationError) {
        this.cleanOnValidationError = cleanOnValidationError;
    }

    public void setCreateSchemas(Boolean createSchemas) {
        this.createSchemas = createSchemas;
    }

    public void setDefaultSchema(String defaultSchema) {
        this.defaultSchema = defaultSchema;
    }

    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    public void setGroup(Boolean group) {
        this.group = group;
    }

    public void setIgnoreFutureMigrations(Boolean ignoreFutureMigrations) {
        this.ignoreFutureMigrations = ignoreFutureMigrations;
    }

    public void setIgnoreIgnoredMigrations(Boolean ignoreIgnoredMigrations) {
        this.ignoreIgnoredMigrations = ignoreIgnoredMigrations;
    }

    public void setIgnoreMissingMigrations(Boolean ignoreMissingMigrations) {
        this.ignoreMissingMigrations = ignoreMissingMigrations;
    }

    public void setIgnorePendingMigrations(Boolean ignorePendingMigrations) {
        this.ignorePendingMigrations = ignorePendingMigrations;
    }

    public void setInitSql(String initSql) {
        this.initSql = initSql;
    }

    public void setInstalledBy(String installedBy) {
        this.installedBy = installedBy;
    }

    public void setJavaMigrations(Class<? extends JavaMigration>[] javaMigrations) {
        if (javaMigrations == null) {
            this.javaMigrations = new ArrayList<>();
        } else {
            this.javaMigrations = Lists.newArrayList(javaMigrations);
        }
    }

    public void setLocations(String[] locations) {
        this.locations = locations == null ? null : locations.clone();
    }

    public void setLockRetryCount(Integer lockRetryCount) {
        this.lockRetryCount = lockRetryCount;
    }

    public void setMixed(Boolean mixed) {
        this.mixed = mixed;
    }

    public void setOutOfOrder(Boolean outOfOrder) {
        this.outOfOrder = outOfOrder;
    }

    public void setPlaceholderPrefix(String placeholderPrefix) {
        this.placeholderPrefix = placeholderPrefix;
    }

    public void setPlaceholderReplacement(Boolean placeholderReplacement) {
        this.placeholderReplacement = placeholderReplacement;
    }

    public void setPlaceholders(Map<String, String> placeholders) {
        this.placeholders = placeholders == null ? null : new HashMap<>(placeholders);
    }

    public void setPlaceholderSuffix(String placeholderSuffix) {
        this.placeholderSuffix = placeholderSuffix;
    }

    public void setRepeatableSqlMigrationPrefix(String repeatableSqlMigrationPrefix) {
        this.repeatableSqlMigrationPrefix = repeatableSqlMigrationPrefix;
    }

    public void setResolvers(Class<? extends MigrationResolver>[] resolvers) {
        if (resolvers == null) {
            this.resolvers = new ArrayList<>();
        } else {
            this.resolvers = Lists.newArrayList(resolvers);
        }
    }

    public void setResourceProvider(Class<? extends ResourceProvider> resourceProvider) {
        this.resourceProvider = resourceProvider;
    }

    public void setSchemas(String[] schemas) {
        this.schemas = schemas == null ? null : schemas.clone();
    }

    public void setSkipDefaultCallbacks(Boolean skipDefaultCallbacks) {
        this.skipDefaultCallbacks = skipDefaultCallbacks;
    }

    public void setSkipDefaultResolvers(Boolean skipDefaultResolvers) {
        this.skipDefaultResolvers = skipDefaultResolvers;
    }

    public void setSqlMigrationPrefix(String sqlMigrationPrefix) {
        this.sqlMigrationPrefix = sqlMigrationPrefix;
    }

    public void setSqlMigrationSeparator(String sqlMigrationSeparator) {
        this.sqlMigrationSeparator = sqlMigrationSeparator;
    }

    public void setSqlMigrationSuffix(String sqlMigrationSuffix) {
        this.sqlMigrationSuffix = sqlMigrationSuffix;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public void setTablespace(String tablespace) {
        this.tablespace = tablespace;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public void setValidateMigrationNaming(Boolean validateMigrationNaming) {
        this.validateMigrationNaming = validateMigrationNaming;
    }

    public void setValidateOnMigrate(Boolean validateOnMigrate) {
        this.validateOnMigrate = validateOnMigrate;
    }
}
