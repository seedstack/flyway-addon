/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.flyway;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.seedstack.flyway.sample.Repository;
import org.seedstack.jdbc.Jdbc;
import org.seedstack.seed.it.AbstractSeedIT;
import org.seedstack.seed.it.SeedITRunner;
import org.seedstack.seed.transaction.Transactional;
import net.jcip.annotations.NotThreadSafe;

import java.sql.SQLException;

import javax.inject.Inject;

@RunWith(SeedITRunner.class)
@NotThreadSafe
public class FlywayIT extends AbstractSeedIT {
	
    @Inject
    private Repository repository;
    	
    @Test
    @Transactional
    @Jdbc("datasource1")
    public void verifyAutomaticMigration() throws SQLException {      	
    	String firstLineValue = repository.getBar("tableTest");
    	Assertions.assertThat(firstLineValue).isEqualTo("bar");
    }
    
    @Test
    @Transactional
    @Jdbc("datasource2")
    public void verifyAutomaticBaseline() throws SQLException {      	
    	try {
			repository.getBar("tableTest");
		} catch (SQLException e) {
			Assertions.assertThat(e.getMessage().startsWith("java.sql.SQLSyntaxErrorException: user lacks privilege or object not found: TABLETEST"));
		}
    	    	
    	String firstLineValue = repository.getBar("tableTest2");
    	Assertions.assertThat(firstLineValue).isEqualTo("bar");    	
    }
    
    @Test
    @Transactional
    @Jdbc("datasource3")
    public void verifyDisabledAutomaticMigration() {      	
    	try {
			repository.getBar("tableTest");
		} catch (SQLException e) {
			Assertions.assertThat(e.getMessage().startsWith("java.sql.SQLSyntaxErrorException: user lacks privilege or object not found: TABLETEST"));
		}
    }
}
