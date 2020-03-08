Slime World Manager supports three data sources out of the box: the filesystem, MySQL and MongoDB. However, there are situations where you might want to use other data sources. To do so, you can create your own implementation of the SlimeLoader interface.

SlimeLoaders are classes used to load worlds from specific data sources. Remember to check out the [docs](../../slimeworldmanager-api/src/main/java/com/grinderwolf/eswm/api/loaders/SlimeLoader.java) for the SlimeLoader interface prior to creating your own implementation, as it contains information on what every method should exactly do. You can also take a look at the [FileLoader class](../../slimeworldmanager-plugin/src/main/java/com/grinderwolf/swm/plugin/loaders/FileLoader.java) for an example of a SlimeLoader.

> The names of the default implementations can be found [here](../../slimeworldmanager-api/src/main/java/com/grinderwolf/eswm/api/loaders/SlimeLoader.java)

Once you've got your own SlimeLoader, remember to register it so you can use it later:
```java
SlimePlugin plugin = (SlimePlugin) Bukkit.getPluginManager().getPlugin("SlimeWorldManager");

plugin.registerLoader("my_data_source", new MyCustomSlimeLoader());
```
