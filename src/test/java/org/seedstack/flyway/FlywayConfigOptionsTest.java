/*
 * Copyright © 2013-2020, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.flyway;

import java.util.HashMap;
import java.util.Map;

import org.assertj.core.api.Assertions;
import org.flywaydb.core.api.ResourceProvider;
import org.flywaydb.core.api.callback.BaseCallback;
import org.flywaydb.core.api.migration.JavaMigration;
import org.flywaydb.core.api.resolver.MigrationResolver;
import org.flywaydb.core.internal.callback.NoopCallback;
import org.junit.Before;
import org.junit.Test;

public class FlywayConfigOptionsTest {

    private FlywayConfigOptions underTest;

    @Before
    public void setup() {
        underTest = new FlywayConfigOptions();
    }

    @Test
    public void testBaselineDescription() throws Exception {
        Assertions.assertThat(underTest.getBaselineDescription()).isNull();
        underTest.setBaselineDescription("baselineDescription");
        Assertions.assertThat(underTest.getBaselineDescription()).isEqualTo("baselineDescription");
    }

    @Test
    public void testBaselineOnMigrate() throws Exception {
        Assertions.assertThat(underTest.getBaselineOnMigrate()).isNull();
        underTest.setBaselineOnMigrate(true);
        Assertions.assertThat(underTest.getBaselineOnMigrate()).isEqualTo(true);
    }

    @Test
    public void testBaselineVersion() throws Exception {
        Assertions.assertThat(underTest.getBaselineVersion()).isNull();
        underTest.setBaselineVersion("BaselineVersion");
        Assertions.assertThat(underTest.getBaselineVersion()).isEqualTo("BaselineVersion");
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Test
    public void testCallbacks() throws Exception {

        Class[] testCallbacks = { BaseCallback.class, NoopCallback.class };
        Assertions.assertThat(underTest.getCallbacks()).isNull();
        underTest.setCallbacks(testCallbacks);
        Assertions.assertThat(underTest.getCallbacks()).isEqualTo(testCallbacks);
    }

    @Test
    public void testCleanDisabled() throws Exception {
        Assertions.assertThat(underTest.getCleanDisabled()).isNull();
        underTest.setCleanDisabled(true);
        Assertions.assertThat(underTest.getCleanDisabled()).isEqualTo(true);
    }

    @Test
    public void testCleanOnValidationError() throws Exception {
        Assertions.assertThat(underTest.getCleanOnValidationError()).isNull();
        underTest.setCleanOnValidationError(false);
        Assertions.assertThat(underTest.getCleanOnValidationError()).isEqualTo(false);
    }

    @Test
    public void testCreateSchemas() throws Exception {
        Assertions.assertThat(underTest.getCreateSchemas()).isNull();
        underTest.setCreateSchemas(true);
        Assertions.assertThat(underTest.getCreateSchemas()).isEqualTo(true);
    }

    @Test
    public void testDefaultSchema() throws Exception {
        Assertions.assertThat(underTest.getDefaultSchema()).isNull();
        underTest.setDefaultSchema("DefaultSchema");
        Assertions.assertThat(underTest.getDefaultSchema()).isEqualTo("DefaultSchema");
    }

    @Test
    public void testEncoding() throws Exception {
        Assertions.assertThat(underTest.getEncoding()).isNull();
        underTest.setEncoding("Encoding");
        Assertions.assertThat(underTest.getEncoding()).isEqualTo("Encoding");
    }

    @Test
    public void testGroup() throws Exception {
        Assertions.assertThat(underTest.getGroup()).isNull();
        underTest.setGroup(true);
        Assertions.assertThat(underTest.getGroup()).isEqualTo(true);
    }

    @Test
    public void testIgnoreFutureMigrations() throws Exception {
        Assertions.assertThat(underTest.getIgnoreFutureMigrations()).isNull();
        underTest.setIgnoreFutureMigrations(true);
        Assertions.assertThat(underTest.getIgnoreFutureMigrations()).isEqualTo(true);
    }

    @Test
    public void testIgnoreIgnoredMigrations() throws Exception {
        Assertions.assertThat(underTest.getIgnoreIgnoredMigrations()).isNull();
        underTest.setIgnoreIgnoredMigrations(true);
        Assertions.assertThat(underTest.getIgnoreIgnoredMigrations()).isEqualTo(true);
    }

    @Test
    public void testIgnoreMissingMigrations() throws Exception {
        Assertions.assertThat(underTest.getIgnoreMissingMigrations()).isNull();
        underTest.setIgnoreMissingMigrations(true);
        Assertions.assertThat(underTest.getIgnoreMissingMigrations()).isEqualTo(true);
    }

    @Test
    public void testIgnorePendingMigrations() throws Exception {
        Assertions.assertThat(underTest.getIgnorePendingMigrations()).isNull();
        underTest.setIgnorePendingMigrations(true);
        Assertions.assertThat(underTest.getIgnorePendingMigrations()).isEqualTo(true);
    }

    @Test
    public void testInitSql() throws Exception {
        Assertions.assertThat(underTest.getInitSql()).isNull();
        underTest.setInitSql("InitSql");
        Assertions.assertThat(underTest.getInitSql()).isEqualTo("InitSql");
    }

    @Test
    public void testInstalledBy() throws Exception {
        Assertions.assertThat(underTest.getInstalledBy()).isNull();
        underTest.setInstalledBy("InstalledBy");
        Assertions.assertThat(underTest.getInstalledBy()).isEqualTo("InstalledBy");
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Test
    public void testJavaMigrations() throws Exception {

        Class[] testMigrations = { JavaMigration.class };

        Assertions.assertThat(underTest.getJavaMigrations()).isNull();
        underTest.setJavaMigrations(testMigrations);
        Assertions.assertThat(underTest.getJavaMigrations()).isEqualTo(testMigrations);
    }

    @Test
    public void testLocations() throws Exception {

        String[] locations = { "Location A", "Location B" };

        Assertions.assertThat(underTest.getLocations()).isNull();
        underTest.setLocations(locations);
        Assertions.assertThat(underTest.getLocations()).isEqualTo(locations);
    }

    @Test
    public void testLockRetryCount() throws Exception {
        Assertions.assertThat(underTest.getLockRetryCount()).isNull();
        underTest.setLockRetryCount(32);
        Assertions.assertThat(underTest.getLockRetryCount()).isEqualTo(32);
    }

    @Test
    public void testMixed() throws Exception {
        Assertions.assertThat(underTest.getMixed()).isNull();
        underTest.setMixed(false);
        Assertions.assertThat(underTest.getMixed()).isEqualTo(false);
    }

    @Test
    public void testOutOfOrder() throws Exception {
        Assertions.assertThat(underTest.getOutOfOrder()).isNull();
        underTest.setOutOfOrder(false);
        Assertions.assertThat(underTest.getOutOfOrder()).isEqualTo(false);
    }

    @Test
    public void testPlaceholderPrefix() throws Exception {
        Assertions.assertThat(underTest.getPlaceholderPrefix()).isNull();
        underTest.setPlaceholderPrefix("PlaceholderPrefix");
        Assertions.assertThat(underTest.getPlaceholderPrefix()).isEqualTo("PlaceholderPrefix");
    }

    @Test
    public void testPlaceholderReplacement() throws Exception {
        Assertions.assertThat(underTest.getPlaceholderReplacement()).isNull();
        underTest.setPlaceholderReplacement(false);
        Assertions.assertThat(underTest.getPlaceholderReplacement()).isEqualTo(false);
    }

    @Test
    public void testPlaceholders() throws Exception {

        Map<String, String> placeholders = new HashMap<>();
        placeholders.put("A", "placeholder 1");
        Map<String, String> placeholderCopy = new HashMap<>(placeholders);

        Assertions.assertThat(underTest.getPlaceholders()).isNull();
        underTest.setPlaceholders(placeholders);
        placeholders.clear();
        Assertions.assertThat(underTest.getPlaceholders()).isEqualTo(placeholderCopy);
        Assertions.assertThat(underTest.getPlaceholders()).isNotEqualTo(placeholders);

    }

    @Test
    public void testPlaceholderSuffix() throws Exception {
        Assertions.assertThat(underTest.getPlaceholderSuffix()).isNull();
        underTest.setPlaceholderSuffix("PlaceholderSuffix");
        Assertions.assertThat(underTest.getPlaceholderSuffix()).isEqualTo("PlaceholderSuffix");
    }

    @Test
    public void testRepeatableSqlMigrationPrefix() throws Exception {
        Assertions.assertThat(underTest.getRepeatableSqlMigrationPrefix()).isNull();
        underTest.setRepeatableSqlMigrationPrefix("RepeatableSqlMigrationPrefix");
        Assertions.assertThat(underTest.getRepeatableSqlMigrationPrefix()).isEqualTo("RepeatableSqlMigrationPrefix");
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Test
    public void testResolvers() throws Exception {

        Class[] resolvers = { MigrationResolver.class };

        Assertions.assertThat(underTest.getResolvers()).isNull();
        underTest.setResolvers(resolvers);
        Assertions.assertThat(underTest.getResolvers()).isEqualTo(resolvers);
    }

    @Test
    public void testResourceProvider() throws Exception {

        Assertions.assertThat(underTest.getResourceProvider()).isNull();
        underTest.setResourceProvider(ResourceProvider.class);
        Assertions.assertThat(underTest.getResourceProvider()).isEqualTo(ResourceProvider.class);
    }

    @Test
    public void testSchemas() throws Exception {

        String[] schemas = { "Schema A" };

        Assertions.assertThat(underTest.getSchemas()).isNull();
        underTest.setSchemas(schemas);
        Assertions.assertThat(underTest.getSchemas()).isEqualTo(schemas);
    }

    @Test
    public void testSkipDefaultCallbacks() throws Exception {
        Assertions.assertThat(underTest.getSkipDefaultCallbacks()).isNull();
        underTest.setSkipDefaultCallbacks(true);
        Assertions.assertThat(underTest.getSkipDefaultCallbacks()).isEqualTo(true);
    }

    @Test
    public void testSkipDefaultResolvers() throws Exception {
        Assertions.assertThat(underTest.getSkipDefaultResolvers()).isNull();
        underTest.setSkipDefaultResolvers(true);
        Assertions.assertThat(underTest.getSkipDefaultResolvers()).isEqualTo(true);
    }

    @Test
    public void testSqlMigrationPrefix() throws Exception {
        Assertions.assertThat(underTest.getSqlMigrationPrefix()).isNull();
        underTest.setSqlMigrationPrefix("SqlMigrationPrefix");
        Assertions.assertThat(underTest.getSqlMigrationPrefix()).isEqualTo("SqlMigrationPrefix");
    }

    @Test
    public void testSqlMigrationSeparator() throws Exception {
        Assertions.assertThat(underTest.getSqlMigrationSeparator()).isNull();
        underTest.setSqlMigrationSeparator("SqlMigrationSeparator");
        Assertions.assertThat(underTest.getSqlMigrationSeparator()).isEqualTo("SqlMigrationSeparator");
    }

    @Test
    public void testSqlMigrationSuffix() throws Exception {
        Assertions.assertThat(underTest.getSqlMigrationSuffix()).isNull();
        underTest.setSqlMigrationSuffix("SqlMigrationSuffix");
        Assertions.assertThat(underTest.getSqlMigrationSuffix()).isEqualTo("SqlMigrationSuffix");
    }

    @Test
    public void testTable() throws Exception {
        Assertions.assertThat(underTest.getTable()).isNull();
        underTest.setTable("Table");
        Assertions.assertThat(underTest.getTable()).isEqualTo("Table");
    }

    @Test
    public void testTablespace() throws Exception {
        Assertions.assertThat(underTest.getTablespace()).isNull();
        underTest.setTablespace("Tablespace");
        Assertions.assertThat(underTest.getTablespace()).isEqualTo("Tablespace");
    }

    @Test
    public void testTarget() throws Exception {
        Assertions.assertThat(underTest.getTarget()).isNull();
        underTest.setTarget("Target");
        Assertions.assertThat(underTest.getTarget()).isEqualTo("Target");
    }

    @Test
    public void testValidateMigrationNaming() throws Exception {
        Assertions.assertThat(underTest.getValidateMigrationNaming()).isNull();
        underTest.setValidateMigrationNaming(true);
        Assertions.assertThat(underTest.getValidateMigrationNaming()).isEqualTo(true);
    }

    @Test
    public void testValidateOnMigrate() throws Exception {
        Assertions.assertThat(underTest.getValidateOnMigrate()).isNull();
        underTest.setValidateOnMigrate(true);
        Assertions.assertThat(underTest.getValidateOnMigrate()).isEqualTo(true);
    }

}
