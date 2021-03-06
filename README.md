# Project support dropped [check out ASWM](https://github.com/Paul19988/Advanced-Slime-World-Manager)

# Enhanced Slime World Manager ![Release CI](https://github.com/endrealm/Enhanced-Slime-World-Manager/workflows/Release%20CI/badge.svg)

**Legacy Slime World Manager Discord:**

[<img src="https://discordapp.com/assets/e4923594e694a21542a489471ecffa50.svg" alt="" height="55" />](https://discord.gg/P9Pd58d)

Slime World Manager is a Minecraft plugin that implements the Slime Region Format, developed by the Hypixel Dev Team.
 Its goal is to provide server administrators with an easy-to-use tool to load worlds faster and save space.
 
 > Enhanced Slime World Manager is a maintained and improved fork of the original slime world manager. Projects based on the original project will not directly be compatible, *but* can easily be converted.

#### Releases

**NO DOWNLOADS FOR E-SWM AVAILEABLE YET**

**Legacy***
*SWM releases can be found [here](https://www.spigotmc.org/resources/slimeworldmanager.69974/history).*

## Using E-SWM in your plugin

#### Maven
(No releases yet, but you can use the snapshots repo: https://repo.andrei1058.com/snapshots)
```
<repositories>
  <repository>
    <id>eswm-repo</id>
    <url>https://repo.andrei1058.com/releases</url>
  </repository>
</repositories>
```
```
<dependencies>
  <dependency>
    <groupId>com.grinderwolf.eswm</groupId>
    <artifactId>eswm-api</artifactId>
    <version>INSERT LATEST VERSION HERE</version>
  </dependency>
</dependencies>
```
#### Gradle
```
repositories {
    maven { url "https://repo.andrei1058.com/releases/" }
}

dependencies {
    compileOnly group: "com.grinderwolf.eswm", name: "eswm-api", version: "INSERT LATEST VERSION HERE";
}
```

#### Javadocs
**Not visible on pages yet**

## Wiki Overview
 * Plugin Usage
    * [Installing Enhanced Slime World Manager](.docs/usage/install.md)
    * [Using Enhanced Slime World Manager](.docs/usage/using.md)
    * [Commands and permissions](.docs/usage/commands-and-permissions.md)
 * Configuration
    * [Setting up the data sources](.docs/config/setup-data-sources.md)
    * [Converting traditional worlds into the SRF](.docs/config/convert-world-to-srf.md)
    * [Configuring worlds](.docs/config/configure-world.md)
    * [Async world generation](.docs/config/async-world-generation.md)
 * E-SWM API
    * [Getting started](.docs/api/setup-dev.md)
    * [World Properties](.docs/api/properties.md)
    * [Loading a world](.docs/api/load-world.md)
    * [Migrating a world](.docs/api/migrate-world.md)
    * [Importing a world](.docs/api/import-world.md)
    * [Using other data sources](.docs/api/use-data-source.md)
 * [FAQ](.docs/faq.md)

## Credits

Thanks to:
 * Everybody who [contributed](https://github.com/endrealm/Enhanced-Slime-World-Manager/graphs/contributors) and made this work <3
 * Grinderwolf who created [the old SWM](https://github.com/Grinderwolf/Slime-World-Manager).
 * [Minikloon](https://twitter.com/Minikloon) and all the [Hypixel](https://twitter.com/HypixelNetwork) team for developing the SRF.
