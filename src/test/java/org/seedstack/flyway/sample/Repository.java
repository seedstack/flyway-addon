/*
 * Copyright © 2013-2021, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.flyway.sample;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.inject.Inject;

import org.seedstack.seed.Bind;

@Bind
public class Repository {
    @Inject
    private Connection connection;

    public String getBar(String tableName) throws SQLException {
        String sql = "Select * from " + tableName;
        PreparedStatement statement = connection.prepareStatement(sql);
        try (ResultSet resultSet = statement.executeQuery();) {
            if (resultSet.next()) {
                return resultSet.getString("Foo");
            }
            return null;
        }
    }
}
