---
title: "Flyway"
repo: "https://github.com/seedstack/flyway-addon"
author: Adrien LAUER
description: "Provides automatic database migration tools through Flyway."
tags:
    - persistence
zones:
    - Addons
menu:
    AddonFlyway:
        weight: 20
---

SeedStack Flyway add-on provides support for database migration through the Flyway API.

# Dependency

{{< dependency g="org.seedstack.addons.flyway" a="flyway" >}}

{{% callout info %}}
The JDBC add-on is also required: [http://seedstack.org/addons/jdbc/](http://seedstack.org/addons/jdbc/)
{{% /callout %}}

{{% callout info %}}
For more information about Flyway: [https://flywaydb.org/documentation/](https://flywaydb.org/documentation/)
{{% /callout %}}

# Configuration

To migrate a database, you need to declare a flyway and jdbc configuration, and in its basic form is:

```yaml
jdbc:
    datasources:
        datasource1:
            # See JDBC add-on documentation: http://seedstack.org/addons/jdbc/
```

With all the options, the configuration file looks like this:

```yaml
jdbc:
    datasources:
        datasource1:
            # See JDBC add-on documentation: http://seedstack.org/addons/jdbc/
        datasource2:
            ...
flyway:
    schemas:
    table:
    sqlMigrationPrefix:
    repeatableSqlMigrationPrefix:
    sqlMigrationSeparator:
    sqlMigrationSuffix:
    encoding:
    placeholderReplacement:
    placeholders.name:
    placeholderPrefix:
    placeholderSuffix:
    resolvers:
    skipDefaultCallResolvers:
    callbacks:
    skipDefaultCallbacks:
    target:
    outOfOrder:
    validateOnMigrate:
    cleanOnValidationError:
    mixed:
    group:
    ignoreMissingMigrations:
    ignoreFutureMigrations:
    cleanDisabled:
    baselineOnMigrate:
    installedBy:

    # Configured Flyway with the datasource name as key
    datasources:          
          datasource1:
             # Enable / disable automatic database migration, and the default value is true
             enabled: boolean

             # `locations` is a comma-separated list of locations scanned recursively for migrations.
             # Unprefixed locations or locations starting with `classpath:` point to a package on the classpath and may contain both sql and java-based migrations.
             # Classpath locations are under `src/main/resources/` and the default value is `META-INF/sql/<datasourceName>`.
             # Locations starting with `filesystem:` point to a directory on the filesystem.
             locations: classpath:com.mycomp.migration,database/migrations,filesystem:/sql-migrations

             # The version to tag an existing schema
             baselineVersion: 1   

             # The description to tag an existing schema
             baselineDescription: String

          # This is the short way to write enabled: boolean
          datasource2: boolean
```

The declaration of a datasource in jdbc is enough to create a Flyway instance.
For the description of other global parameters, see the list below:


| Parameters                   | Default                        |    Description                                                            |
| ---------------------------- | ------------------------------ | ------------------------------------------------------------------------- |
| schemas                      | Default schema of the connection | Comma-separated case-sensitive list of schemas managed by Flyway. The first schema will be the one containing the metadata table. |
| table                        | schema_version                 | The name of Flyway's metadata table. By default (single-schema mode) the metadata table is placed in the default schema for the connection provided by the datasource. When the flyway.schemas property is set (multi-schema mode), the metadata table is placed in the first schema of the list. |
| sqlMigrationPrefix           | V                              | The file name prefix for Sql migrations                                   |
| repeatableSqlMigrationPrefix | R                              | The file name prefix for repeatable Sql migrations                        |
| sqlMigrationSeparator        | __                             | The file name separator for Sql migrations                                |
| sqlMigrationSuffix           | .sql                           | The file name suffix for Sql migrations                                   |
| encoding                     | UTF-8                          | The encoding of Sql migrations                                            |
| placeholderReplacement       | true                           | Whether placeholders should be replaced                                   |
| placeholders.name            |                                | Placeholders to replace in Sql migrations. You can add many placeholders.name and replace `name` by the property name. |
| placeholderPrefix            | ${                             | The prefix of every placeholder |
| placeholderSuffix            | }                              | The suffix of every placeholder |
| resolvers                    |                                | Comma-separated list of fully qualified class names of custom MigrationResolver implementations to be used in addition to the built-in ones for resolving Migrations to apply. |
| skipDefaultCallResolvers     | false                          | Whether default built-in resolvers (sql, jdbc and spring-jdbc) should be skipped. If true, only custom resolvers are used. |
| callbacks                    |                                | Comma-separated list of fully qualified class names of FlywayCallback implementations to use to hook into the Flyway lifecycle. |
| skipDefaultCallbacks         | false                          | Whether default built-in callbacks (sql) should be skipped. If true, only custom callbacks are used. |
| target                       | Latest version                 | The target version up to which Flyway should consider migrations. Migrations with a higher version number will be ignored. The special value current designates the current version of the schema. Example: 5.1 |
| outOfOrder                   | false                          | Allows migrations to be run "out of order". If you already have versions 1 and 3 applied, and now a version 2 is found, it will be applied too instead of being ignored. |
| validateOnMigrate            | true                           | For each sql migration a checksum is calculated when the sql script is executed. The validate mechanism checks if the sql migration in the classpath still has the same checksum as the sql migration already executed in the database. |
| cleanOnValidationError       | false                          | Whether to automatically call clean or not when a validation error occurs. Warning ! Do not enable in production ! |
| mixed                        | false                          | Whether to allow mixing transactional and non-transactional statements within the same migration |
| group                        | false                          | Whether to group all pending migrations together in the same transaction when applying them (only recommended for databases with support for DDL transactions) |
| ignoreMissingMigrations      | false                          | Ignore missing migrations when reading the metadata table. These are migrations that were performed by an older deployment of the application that are no longer available in this version. |
| ignoreFutureMigrations       | true                           | Ignore future migrations when reading the metadata table. These are migrations that were performed by a newer deployment of the application that are not yet available in this version.  |
| cleanDisabled                | false                          | Whether to disable clean. This is especially useful for production environments where running clean can be quite a career limiting move. |
| baselineOnMigrate            | false                          | Whether to automatically call baseline when migrate is executed against a non-empty schema with no metadata table. This schema will then be baselined with the `baselineVersion` before executing the migrations. Only migrations above baselineVersion will then be applied. This is useful for initial Flyway production deployments on projects with an existing DB. |
| installedBy                  | Current database user          | The username that will be recorded in the metadata table as having applied the migration |

# Migration Scripts

The default location of migration scripts is `src/main/resources/META-INF/sql/<datasourceName>`.

The default naming convention is as follows: `V<VERSION>__<DESCRIPTION>.<FORMAT>`

Where `<VERSION>` is a numeric value that can contain a point (“.”) or an underscore (“_”) for example 3, 3.1, 3_1 are all valid for the version.
The double underscore, __ is what is used to separate the version number from the description.
For the `<FORMAT>`,  you can have either sql or java depending on the method being used to supply the migrations.

{{% callout info %}}
When you use flyway for the first time, it will create a schema_version table, which sets the database version number.
{{% /callout %}}

# Automatic migration

By default, the Flyway module automatically updates your databases.
If you add new migration scripts, they will automatically be played when the application starts. It is possible to include a baseline to exclude all previous migration.
To disable automation, you must pass `enabled` to false.

# Tool

You can run Flyway in tool mode.

{{% callout info %}}
You can find more information about this mode: [http://seedstack.org/docs/seed/manual/running/#tool-mode](http://seedstack.org/docs/seed/manual/running/#tool-mode)
{{% /callout %}}

The tool is built like this `java -Dseedstack.tool=flyway-<functionName> -f datasourceName -optionName value org.seedstack.seed.core.SeedMain`. The tool definition must be the first argument.
The available functions are:

| Name     |     Description                                                                              |
| -------- | -------------------------------------------------------------------------------------------- |
| migrate  | Migrates the database                                                                        |
| clean    | Drops all objects in the configured schemas                                                  |
| info     | Prints the details and status information about all the migrations                           |
| validate | Validates the applied migrations against the ones available on the classpath                 |
| baseline | Baselines an existing database, excluding all migrations up to and including baselineVersion |
| repair   | Repairs the metadata table                                                                   |

Then, you need to add the datasource. With Flyway tool, you can override some properties of the basic configuration:

| Parameters                   | name   | longName            | Required | Availability                                     |
| ---------------------------- | ------ | ------------------- | -------- | ------------------------------------------------ |
| datasource                   | f      | flyway              | True     | Migrate, clean, Baseline, info, repair, validate |
| schemas                      | s      | schemas             | False    | Migrate, clean, Baseline, info, repair, validate |
| table                        | t      | table               | False    | Migrate, Baseline, info, repair, validate        |
| locations                    | l      | locations           | False    | Migrate, info, repair, validate                  |
| baselineVersion              | bv     | baselineversion     | False    | Baseline                                         |
| baselineDescription          | bd     | baselinedescription | False    | Baseline                                         |
| target                       | target |                     | False    | Migrate, info, validate                          |
| outOfOrder                   | o      | outorder            | False    | Migrate, info, validate                          |
| validateOnMigrate            | v      | validateonmigrate   | False    | Migrate                                          |
| mixed                        | m      | mixed               | False    | Migrate                                          |
| group                        | g      | group               | False    | Migrate                                          |
| ignoreMissingMigrations      | im     | ignoremissingmig    | False    | Migrate, validate                                |
| ignoreFutureMigrations       | if     | ignorefuturemig     | False    | Migrate, validate                                |
| baselineOnMigrate            | bm     | baselineonmigrate   | False    | Migrate                                          |
| installedBy                  | by     | installedby         | False    | Migrate                                          |

# Example

Configuration for a mysql database running on the same machine:

```yaml
jdbc:
    datasources:
        datasource1:
            url: jdbc:mysql://localhost:3306/mydb
            user: root
        datasource2:
            url: jdbc:mysql://localhost:3306/mydb2
            user: root
flyway:
    datasources:
        datasource2:
            baselineVersion: 1.1
```

List of migration files:
```file
src/main/resources/META-INF/sql/
    datasource1/
       V1.1__createStructure.sql
       V1.2__addEmailColumn.sql
    datasource2/
       V1.1__createStructure.sql
       V2__structureV2.sql
```

And that's all to have an automatic migration. In this example, the database "mydb" executed migration 1.1 and 1.2. The database "mydb2" executed only migration 2.


With this basic configuration, you can use tool function like this: `java -Dseedstack.tool=flyway-migrate -f datasource1 org.seedstack.seed.core.SeedMain`
