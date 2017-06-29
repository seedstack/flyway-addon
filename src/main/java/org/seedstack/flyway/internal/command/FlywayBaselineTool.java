/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.flyway.internal.command;

import org.flywaydb.core.Flyway;
import org.seedstack.flyway.internal.AbstractFlywayTool;
import org.seedstack.seed.cli.CliOption;

public class FlywayBaselineTool extends AbstractFlywayTool {
    @CliOption(name = "bv", longName = "baselineVersion", valueCount = 1)
    private String baselineVersion = null;

    @CliOption(name = "bd", longName = "baselineDescription", valueCount = 1)
    private String baselineDescription = null;

    @Override
    public String toolName() {
        return "flyway-baseline";
    }

    @Override
    public Integer call() throws Exception {
        Flyway flyway = getFlyway();
        if (this.baselineVersion != null) {
            flyway.setBaselineVersionAsString(this.baselineVersion);
        }
        if (this.baselineDescription != null) {
            flyway.setBaselineDescription(this.baselineDescription);
        }

        System.out.println("Flyway: baselining datasource " + getDatasource() + " to " + flyway.getBaselineVersion());
        flyway.baseline();
        return 0;
    }
}
