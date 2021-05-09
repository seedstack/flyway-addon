/*
 * Copyright © 2013-2021, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.flyway;

import static junit.framework.TestCase.fail;

import java.sql.SQLException;

import javax.inject.Inject;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.seedstack.flyway.migration.R__ConfigurationJavaMigration;
import org.seedstack.flyway.sample.Repository;
import org.seedstack.jdbc.Jdbc;
import org.seedstack.seed.testing.junit4.SeedITRunner;
import org.seedstack.seed.transaction.Transactional;

import db.migration.datasource5.R__DiscoveredJavaMigration;

@RunWith(SeedITRunner.class)
public class FlywayIT {
    @Inject
    private Repository repository;

    @Test
    @Transactional
    @Jdbc("datasource1")
    public void verifyAutomaticMigration() throws SQLException {
        String firstLineValue = repository.getBar("tableTest");
        Assertions.assertThat(firstLineValue).isEqualTo("bar");
    }

    @Test(expected = SQLException.class)
    @Transactional
    @Jdbc("datasource2")
    public void verifyAutomaticBaseline() throws SQLException {
        Assertions.assertThat(repository.getBar("tableTest")).isEqualTo("bar");

        try {
            repository.getBar("tableTest2");
        } catch (SQLException e) {
            Assertions.assertThat(
                    e.getMessage().startsWith("java.sql.SQLSyntaxErrorException: user lacks privilege or object not found: TABLETEST2"));
            throw e;
        }
        fail("should have failed with missing table");
    }

    @Test
    @Transactional
    @Jdbc("datasource3")
    public void verifyDisabledAutomaticMigration() {
        try {
            repository.getBar("tableTest");
        } catch (SQLException e) {
            Assertions.assertThat(
                    e.getMessage().startsWith("java.sql.SQLSyntaxErrorException: user lacks privilege or object not found: TABLETEST"));
        }
    }

    @Test
    public void testThatCodeMigrationIsExecuted() throws Exception {
        Assertions.assertThat(R__DiscoveredJavaMigration.isApplied()).isTrue();
        Assertions.assertThat(R__ConfigurationJavaMigration.isApplied()).isTrue();
    }

}
