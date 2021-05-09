/*
 * Copyright © 2013-2021, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.flyway.internal.command;

import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.MigrationInfo;
import org.flywaydb.core.api.MigrationInfoService;
import org.seedstack.flyway.internal.AbstractFlywayTool;

public class FlywayInfoTool extends AbstractFlywayTool {
    @Override
    public String toolName() {
        return "flyway-info";
    }

    @Override
    public Integer call() throws Exception {
        Flyway flyway = getFlyway();
        MigrationInfoService migrationInfoService = flyway.info();
        MigrationInfo[] migrationInfos = migrationInfoService.all();
        if (migrationInfos != null) {
            for (MigrationInfo migrationInfo : migrationInfos) {
                System.out.println(
                        "Flyway: " + migrationInfo.getVersion() +
                                " | script: " + migrationInfo.getScript() +
                                " | installed on: " + migrationInfo.getInstalledOn() +
                                " | state: " + migrationInfo.getState().getDisplayName()
                );
            }
        }
        return 0;
    }
}
