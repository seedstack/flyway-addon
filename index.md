---
title: "Flyway"
addon: "Flyway"
repo: "https://github.com/seedstack/flyway-addon"
author: Adrien LAUER
description: "Provides automatic database migration tools through Flyway."
tags:
    - persistence
zones:
    - Addons
---

SeedStack Flyway add-on provides support for automatic database migration through [Flyway](https://flywaydb.org/).

{{% callout info %}}
For more information about Flyway: [https://flywaydb.org/documentation/](https://flywaydb.org/documentation/)
{{% /callout %}}

## Dependency

{{< dependency g="org.seedstack.addons.flyway" a="flyway" >}}

## Configuration

To enable database migration, a [properly configured datasource]({{< ref "addons/jdbc/index.md#configuration" >}}) is required:

```yaml
jdbc:
  datasources:
    someDatasource:
      url: jdbc:hsqldb:mem:somedb
```

With only this configuration, the Flyway add-on will lookup for SQL migration scripts in the `db/migration/<dsName>` classpath
location and if it finds any, will apply the necessary scripts to upgrade the database to the latest version.

This behavior can be customized with the following options:

```yaml
flyway:
  # Configured Flyway data-sources with the datasource name as key
  datasources:          
    someDatasource:
      # Enable / disable automatic database migration, and the default value is true
      enabled: boolean
      
      # Datasource-specific options that override the global options (see below)
      options: ...
      
  # Global options that can be overridden for each datasource (see above)
  options:
    # The locations to scan recursively for migrations
    locations: (String[])
    
    # Schemas managed by Flyway. The first schema will be the one containing the metadata table                      
    schemas: (String[])
    
    # The version to tag an existing schema with when executing baseline                        
    baselineVersion: (String)
    
    # The description to tag an existing schema with when executing baseline               
    baselineDescription: (String)
    
    # The name of the Flyway metadata table            
    table: (String)
    
    # The file name prefix for sql migrations                         
    sqlMigrationPrefix: (String)
    
    # The file name suffix for sql migrations         
    sqlMigrationSuffix: (String)

    # The file name prefix for repeatable sql migrations            
    repeatableSqlMigrationPrefix: (String)
    
    # The file name separator for sql migrations  
    sqlMigrationSeparator: (String)
    
    # The encoding of Sql migrations            
    encoding: (String)
    
    # The prefix of every placeholder                      
    placeholderPrefix: (String)
    
    # The suffix of every placeholder             
    placeholderSuffix: (String)
    
    # The target version up to which Flyway should consider migrations             
    target: (String)
    
    # The username that will be recorded in the metadata table as having applied the migration                        
    installedBy: (String)
    
    # Custom MigrationResolvers to be used in addition to the built-in ones for resolving Migrations to apply.                   
    resolvers: (Class<? extends MigrationResolver>[])
    
    # The callbacks for lifecycle notifications                     
    callbacks: (Class<? extends FlywayCallback>[])
    
    # The placeholders to replace in sql migration scripts                     
    placeholders: (Map<String, String>)
    
    # Whether placeholders should be replaced                  
    placeholderReplacement: (Boolean)
    
    # Whether Flyway should skip the default resolvers         
    skipDefaultResolvers: (Boolean)
    
    # Whether Flyway should skip the default callbacks      
    skipDefaultCallbacks: (Boolean)
    
    # Allows migrations to be run "out of order"          
    outOfOrder: (Boolean)
    
    # Whether to automatically call validate or not when running migrate                    
    validateOnMigrate: (Boolean)
    
    # Whether to automatically call clean or not when a validation error occurs             
    cleanOnValidationError: (Boolean)
    
    # Whether to allow mixing transactional and non-transactional statements within the same migration         
    mixed: (Boolean)
    
    # Whether to group all pending migrations together in the same transaction when applying them (only recommended for databases with support for DDL transactions)                          
    group: (Boolean)
    
    # Ignore missing migrations when reading the metadata table                         
    ignoreMissingMigrations: (Boolean)
    
    # Whether to ignore future migrations when reading the metadata table        
    ignoreFutureMigrations: (Boolean)
    
    # Whether to disable clean    
    cleanDisabled: (Boolean)
    
    # Whether to automatically call baseline when migrate is executed against a non-empty schema with no metadata table    
    baselineOnMigrate: (Boolean)
```

{{% callout info %}}
More information about options and their default values can be found [here](https://flywaydb.org/documentation/api/javadoc.html).
{{% /callout %}}

## Usage

### Migration Scripts

The default location of migration scripts is in the classpath location `db/migration/<datasourceName>`. The default naming 
convention of a migration file is: 

```plain
V<VERSION>__<DESCRIPTION>.<FORMAT>
```

Where:

* `<VERSION>` is a numeric value that can contain a point (“.”) or an underscore (“_”) for example 3, 3.1, 3_1 are all 
valid for the version.
* The **double underscore** (__) is what is used to separate the version number from the description.
* The `<FORMAT>` is either `sql` or `java` depending on the method being used to supply the migrations.

{{% callout info %}}
When you use Flyway for the first time, it will by default create a table named `schema_version`, which records the migration
metadata.
{{% /callout %}}

### Automatic migration

By default, the Flyway add-on automatically migrates your databases to their respective latest versions during the application
startup. It is possible to specify a baseline which will exclude all previous migrations (to work on a previously existing 
database). Automatic migration on startup can be disabled per datasource by setting the corresponding `enabled` configuration
attribute to `false`.

## Tooling

The Flyway add-on provides several SeedStack tools to execute database operations manually.

{{% callout info %}}
For more information about the tool mode, see [this page]({{< ref "docs/core/index.md#tool-mode" >}}).
{{% /callout %}}

### Migrate tool
 
Executes a migration operation using the existing configuration. 
With the Maven plugin [tool goal]({{< ref "docs/maven-plugin/tool.md" >}}):

```bash
mvn -Dargs="flyway-migrate -d <dataSourceName>" seedstack:tool
```

Or directly by [running the application capsule]({{< ref "docs/core/index.md#capsule" >}}):
 
```bash
java -Dseedstack.tool=flyway-migrate -jar app-capsule.jar -d <dataSourceName>
``` 

Optional parameters:

* `-t <targetVersion>`: the target version to migrate to (overrides the configured target if any). 
 
### Clean tool 

Drops all database objects.

With the Maven plugin [tool goal]({{< ref "docs/maven-plugin/tool.md" >}}):

```bash
mvn -Dargs="flyway-clean -d <dataSourceName>" seedstack:tool
```

Or directly by [running the application capsule]({{< ref "docs/core/index.md#capsule" >}}):
 
```bash
java -Dseedstack.tool=flyway-clean -jar app-capsule.jar -d <dataSourceName>
``` 

### Info tool 

Prints details and status information about the migrations.

With the Maven plugin [tool goal]({{< ref "docs/maven-plugin/tool.md" >}}):

```bash
mvn -Dargs="flyway-info -d <dataSourceName>" seedstack:tool
```

Or directly by [running the application capsule]({{< ref "docs/core/index.md#capsule" >}}):
 
```bash
java -Dseedstack.tool=flyway-info -jar app-capsule.jar -d <dataSourceName>
``` 

### Validate tool 

Validates the currently applied migrations against the ones available on the classpath.

With the Maven plugin [tool goal]({{< ref "docs/maven-plugin/tool.md" >}}):

```bash
mvn -Dargs="flyway-validate -d <dataSourceName>" seedstack:tool
```

Or directly by [running the application capsule]({{< ref "docs/core/index.md#capsule" >}}):
 
```bash
java -Dseedstack.tool=flyway-validate -jar app-capsule.jar -d <dataSourceName>
``` 

Optional parameters:

* `-t <targetVersion>`: the target version to validate (overrides the configured target if any). 

### Baseline tool 

Baselines an existing database, excluding all migrations up to and including the configured baseline version.

With the Maven plugin [tool goal]({{< ref "docs/maven-plugin/tool.md" >}}):

```bash
mvn -Dargs="flyway-baseline -d <dataSourceName>" seedstack:tool
```

Or directly by [running the application capsule]({{< ref "docs/core/index.md#capsule" >}}):
 
```bash
java -Dseedstack.tool=flyway-baseline -jar app-capsule.jar -d <dataSourceName>
``` 

Optional parameters:

* `-bv <baselineVersion>`: the baseline version to use (overrides the configured baseline version if any). 
* `-bd <baselineDescription>`: the baseline description (overrides the configured baseline description if any).

### Repair tool 

Repairs the metadata table.

With the Maven plugin [tool goal]({{< ref "docs/maven-plugin/tool.md" >}}):

```bash
mvn -Dargs="flyway-repair -d <dataSourceName>" seedstack:tool
```

Or directly by [running the application capsule]({{< ref "docs/core/index.md#capsule" >}}):
 
```bash
java -Dseedstack.tool=flyway-repair -jar app-capsule.jar -d <dataSourceName>
``` 

## Example

Configuration for a mysql database running on the same machine:

```yaml
jdbc:
  datasources:
    datasource1:
      url: jdbc:hsqldb:mem:somedb1
    datasource2:
      url: jdbc:hsqldb:mem:somedb2
      
flyway:
  datasources:
    datasource2:
      options:
        baselineVersion: 1.1
```

Migration files:

```plain
src/main/resources/db/migration
  datasource1
    V1.1__createStructure.sql
    V1.2__addEmailColumn.sql
  datasource2
    V1.1__createStructure.sql
    V2__structureV2.sql
```

In this example: 

* The database `somedb1` will be automatically updated to version 1.2 by applying all the required migrations. 
* The database `somedb2` will be automatically updated to version 1.2 by only applying the V2 migration if needed (migration
V1.1 will be left out as specified by the baseline configuration).

You can also use the following tool to manually migrate `datasource1`:

```bash
mvn -Dargs="flyway-migrate -d datasource1" seedstack:tool
```

Or without using Maven, directly by running the application capsule:

```bash
java -Dseedstack.tool=flyway-migrate -jar app-capsule.jar -d datasource1
```
