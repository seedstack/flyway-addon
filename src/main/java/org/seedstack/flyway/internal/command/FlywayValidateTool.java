/*
 * Copyright © 2013-2020, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.flyway.internal.command;

import org.flywaydb.core.Flyway;
import org.seedstack.flyway.internal.AbstractFlywayTool;
import org.seedstack.seed.cli.CliOption;

public class FlywayValidateTool extends AbstractFlywayTool {
    @CliOption(name = "t", longName = "target", valueCount = 1)
    private String target;

    @Override
    public String toolName() {
        return "flyway-validate";
    }

    @Override
    public Integer call() throws Exception {
        Flyway flyway = getFlyway();
        if (this.target != null) {
            flyway.setTargetAsString(this.target);
        }
        System.out.println("Flyway: validating datasource " + getDatasource() + " for " + target);
        flyway.validate();
        return 0;
    }
}
