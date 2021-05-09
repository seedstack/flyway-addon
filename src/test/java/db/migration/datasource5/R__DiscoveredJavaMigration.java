/*
 * Copyright © 2013-2021, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package db.migration.datasource5;

import org.flywaydb.core.api.migration.BaseJavaMigration;
import org.flywaydb.core.api.migration.Context;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class R__DiscoveredJavaMigration extends BaseJavaMigration {

    private static boolean isApplied = false;

    @Override
    public void migrate(Context context) throws Exception {
        isApplied = true;
    }

    public static boolean isApplied() {
        return isApplied;
    }
}
