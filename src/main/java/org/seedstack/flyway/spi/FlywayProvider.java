/*
 * Copyright © 2013-2020, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.flyway.spi;

import io.nuun.kernel.api.annotations.Facet;
import org.flywaydb.core.Flyway;

import java.util.Optional;

@Facet
public interface FlywayProvider {
    /**
     * Provides a configured flyway by its name.
     *
     * @param name the data source name (as configured in JDBC add-on).
     * @return the flyway object.
     */
    Optional<Flyway> getFlyway(String name);
}