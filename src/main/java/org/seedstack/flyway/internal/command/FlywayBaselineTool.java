/*
 * Copyright © 2013-2020, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.flyway.internal.command;

import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.configuration.FluentConfiguration;
import org.seedstack.flyway.internal.AbstractFlywayTool;
import org.seedstack.flyway.internal.FlywayUtils;
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

        FluentConfiguration configuration = FlywayUtils.getFlywayBuilderFromInstance(getFlyway());

        if (this.baselineVersion != null) {
            configuration.baselineVersion(baselineDescription).load();
        }
        if (this.baselineDescription != null) {
            configuration.baselineDescription(baselineDescription).load();
        }
        Flyway flyway = configuration.load();

        System.out.println("Flyway: baselining datasource " + getDatasource() + " to " + configuration.getBaselineVersion());
        flyway.baseline();
        return 0;
    }
}
