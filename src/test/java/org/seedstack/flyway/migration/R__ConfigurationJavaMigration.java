/*
 * Copyright © 2013-2020, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.flyway.migration;

import org.flywaydb.core.api.migration.BaseJavaMigration;
import org.flywaydb.core.api.migration.Context;

public class R__ConfigurationJavaMigration extends BaseJavaMigration {

    private static boolean isApplied = false;

    @Override
    public void migrate(Context context) throws Exception {
        isApplied = true;
    }

    public static void resetApplied() {
        isApplied = false;

    }

    public static boolean isApplied() {
        return isApplied;
    }

}
