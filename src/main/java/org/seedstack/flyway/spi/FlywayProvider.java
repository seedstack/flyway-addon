/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.flyway.spi;

import java.util.Optional;

import org.flywaydb.core.Flyway;

public interface FlywayProvider {
	
	/**
     * Provides a configured flyway by its name.
     *
     * @param name the data source name.
     * @return the flyway.
     */
	public Optional<Flyway> getFlyway(String name);	
}