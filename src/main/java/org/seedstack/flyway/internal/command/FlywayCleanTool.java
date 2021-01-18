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

public class FlywayCleanTool extends AbstractFlywayTool {
    @Override
    public String toolName() {
        return "flyway-clean";
    }

    @Override
    public Integer call() throws Exception {
        Flyway flyway = getFlyway();
        System.out.println("Flyway: cleaning datasource " + getDatasource());
        flyway.clean();
        return 0;
    }
}
