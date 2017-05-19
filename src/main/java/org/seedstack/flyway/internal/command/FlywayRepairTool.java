/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.flyway.internal.command;

import org.seedstack.flyway.internal.AbstractFlywayTool;
import org.seedstack.seed.cli.CliOption;

public class FlywayRepairTool extends AbstractFlywayTool {
	
	@CliOption(name = "f", longName="flyway", mandatory=true, valueCount=1)
	private String flywayName;
	
	@CliOption(name = "s", longName="schemas", mandatory=false, valueCount=1)
	private String schemas = null;
	
	@CliOption(name = "t", longName="table", mandatory=false, valueCount=1)
	private String table = null;
	
	@CliOption(name = "l", longName="locations", mandatory=false, valueCount=1)
	private String locations = null;

	@Override
	public String toolName() {
		return "flyway-repair";
	}

	@Override
	public Integer call() throws Exception {
		flyway = flywayMap.get(flywayName);
		if(flyway == null) {
			System.out.println("Error: the define flyway datasource [-f=" + flywayName + "] is not set");
			return 0;
		}
		
		if (this.schemas != null) {
			flyway.setSchemas(this.schemas);
		}
		
		if (this.table != null) {
			flyway.setTable(this.table);
		}
		
		if (this.locations != null) {
			flyway.setLocations(this.locations);
		}

		flyway.repair();
		System.out.println("Flyway repair databasource [" + flywayName + "]");

		return 0;
	}
}
