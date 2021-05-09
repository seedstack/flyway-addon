/*
 * Copyright © 2013-2021, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.flyway.internal.command;

import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.output.MigrateResult;
import org.seedstack.flyway.internal.AbstractFlywayTool;
import org.seedstack.flyway.internal.FlywayUtils;
import org.seedstack.seed.cli.CliOption;

public class FlywayMigrateTool extends AbstractFlywayTool {
    @CliOption(name = "t", longName = "target", valueCount = 1)
    private String target;

    @Override
    public String toolName() {
        return "flyway-migrate";
    }

    @Override
    public Integer call() throws Exception {
        Flyway flyway = getFlyway();
        String datasource = getDatasource();

        if (target != null) {
            flyway = FlywayUtils.getFlywayBuilderFromInstance(getFlyway()).locations(target).load();

        }

        System.out.println("Flyway: migrating datasource " + datasource + " to " + flyway.getConfiguration().getTarget());
        MigrateResult result = flyway.migrate();

        if (result.migrationsExecuted == 0) {
            System.out.println("Flyway: datasource " + datasource + " is already up to date");
        } else {
            System.out.println("Flyway: datasource " + datasource + " has been migrated (" + result + " migration(s) applied)");
        }

        result.warnings.forEach(warn -> System.out.println("WARNING: " + warn));

        return 0;
    }
}
