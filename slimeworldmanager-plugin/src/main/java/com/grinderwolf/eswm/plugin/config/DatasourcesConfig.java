package com.grinderwolf.eswm.plugin.config;

import lombok.Getter;
import ninja.leaping.configurate.objectmapping.Setting;
import ninja.leaping.configurate.objectmapping.serialize.ConfigSerializable;

@Getter
@ConfigSerializable
public class DatasourcesConfig {

    @Setting("file") private FileConfig fileConfig = new FileConfig();
    @Setting("mysql") private MysqlConfig mysqlConfig = new MysqlConfig();
    @Setting("mongodb") private MongoDBConfig mongoDbConfig = new MongoDBConfig();

    @Getter
    @ConfigSerializable
    public static class MysqlConfig {

        @Setting("enabled") private boolean enabled = false;

        @Setting("host") private String host = "127.0.0.1";
        @Setting("port") private int port = 3306;

        @Setting("username") private String username = "pigeon";
        @Setting("password") private String password = "bread";

        @Setting("database") private String database = "slimeworld";
    }

    @Getter
    @ConfigSerializable
    public static class MongoDBConfig {

        @Setting("enabled") private boolean enabled = false;

        @Setting("host") private String host = "127.0.0.1";
        @Setting("port") private int port = 27017;

        @Setting("auth") private String authSource = "admin";
        @Setting("username") private String username = "pigeon";
        @Setting("password") private String password = "bread";

        @Setting("database") private String database = "slimeworld";
        @Setting("collection") private String collection = "worlds";
    }

    @Getter
    @ConfigSerializable
    public static class FileConfig {

        @Setting("path") private String path = "slime_worlds";

    }
}
