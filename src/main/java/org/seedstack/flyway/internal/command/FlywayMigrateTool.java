/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.flyway.internal.command;

import org.seedstack.flyway.internal.AbstractFlywayTool;
import org.seedstack.seed.cli.CliOption;

public class FlywayMigrateTool extends AbstractFlywayTool {

    @CliOption(name = "f", longName = "flyway", mandatory = true, valueCount = 1)
    private String flywayName;

    @CliOption(name = "s", longName = "schemas", mandatory = false, valueCount = 1)
    private String schemas = null;

    @CliOption(name = "t", longName = "table", mandatory = false, valueCount = 1)
    private String table = null;

    @CliOption(name = "l", longName = "locations", mandatory = false, valueCount = 1)
    private String locations = null;

    @CliOption(name = "target", mandatory = false, valueCount = 1)
    private String target;

    @CliOption(name = "o", longName = "outorder", mandatory = false, valueCount = 1)
    private Boolean outOfOrder = null;

    @CliOption(name = "v", longName = "validateonmigrate", mandatory = false, valueCount = 1)
    private Boolean validateOnMigrate = null;

    @CliOption(name = "m", longName = "mixed", mandatory = false, valueCount = 1)
    private Boolean mixed = null;

    @CliOption(name = "g", longName = "group", mandatory = false, valueCount = 1)
    private Boolean group = null;

    @CliOption(name = "im", longName = "ignoremissingmig", mandatory = false, valueCount = 1)
    private Boolean ignoreMissingMigrations = null;

    @CliOption(name = "if", longName = "ignorefuturemig", mandatory = false, valueCount = 1)
    private Boolean ignoreFutureMigrations = null;

    @CliOption(name = "bm", longName = "baselineonmigrate", mandatory = false, valueCount = 1)
    private Boolean baselineOnMigrate = null;

    @CliOption(name = "by", longName = "installedby", mandatory = false, valueCount = 1)
    private String installedBy = null;

    @Override
    public String toolName() {
        return "flyway-migrate";
    }

    @Override
    public Integer call() throws Exception {
        flyway = flywayMap.get(flywayName);
        if (flyway == null) {
            System.out.println("Error: the define flyway datasource [-f=" + flywayName + "] is not set");
            return 0;
        }

        if (this.schemas != null) {
            flyway.setSchemas(this.schemas);
        }

        if (this.table != null) {
            flyway.setTable(this.table);
        }

        if (this.locations != null) {
            flyway.setLocations(this.locations);
        }

        if (this.target != null) {
            flyway.setTargetAsString(this.target);
        }

        if (this.outOfOrder != null) {
            flyway.setOutOfOrder(this.outOfOrder);
        }

        if (this.validateOnMigrate != null) {
            flyway.setValidateOnMigrate(this.validateOnMigrate);
        }

        if (this.mixed != null) {
            flyway.setCleanOnValidationError(this.mixed);
        }

        if (this.group != null) {
            flyway.setCleanOnValidationError(this.group);
        }

        if (this.ignoreMissingMigrations != null) {
            flyway.setIgnoreMissingMigrations(this.ignoreMissingMigrations);
        }

        if (this.ignoreFutureMigrations != null) {
            flyway.setIgnoreFutureMigrations(this.ignoreFutureMigrations);
        }

        if (this.baselineOnMigrate != null) {
            flyway.setBaselineOnMigrate(this.baselineOnMigrate);
        }

        if (this.installedBy != null) {
            flyway.setInstalledBy(this.installedBy);
        }

        int result = flyway.migrate();
        if (result == 0) {
            System.out.println("Flyway: The databasource [" + flywayName + "] is already up to date");
        } else {
            System.out.println(result + " migration script(s) successfully executed on datasource [" + flywayName + "]");
        }

        return 0;
    }
}
