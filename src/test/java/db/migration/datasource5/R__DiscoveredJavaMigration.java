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
