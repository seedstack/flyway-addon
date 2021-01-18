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
