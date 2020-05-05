package victorjeanjr.tempmute.api;

import org.bukkit.configuration.file.YamlConfiguration;
import victorjeanjr.tempmute.Core;

import java.io.*;
import java.util.List;
import java.util.Set;

/**
 * @author Victor Jean (VictorJeanJr)
 * @version 1.0
 * @since 05-02-2019
 */

public class Config {

    private String name;
    private File file;
    private YamlConfiguration yamlConfiguration;

    public Config(String name) {
        this.name = name;
        this.reload();
    }

    private void reload() {
        this.setFile(new File(Core.getCore().getDataFolder(), this.getName()));
        if(this.exist()) {
            try {
                this.setYamlConfiguration(YamlConfiguration.loadConfiguration(
                        new BufferedReader(new InputStreamReader(new FileInputStream(getFile()), "UTF-8"))));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void saveDefaultConfig() {
        if (!this.exist()) {
            Core.getCore().saveResource(this.getName(), false);
            this.reload();
        }
    }

    public void saveConfig() {
        try {
            Config.this.getYamlConfiguration().save(getFile());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveDefault() {
        this.getYamlConfiguration().options().copyDefaults(true);
    }

    public boolean exist() {
        return this.getFile().exists();
    }

    public boolean delete() {
        return this.getFile().delete();
    }

    ///

    public boolean contains(String path) {
        return this.getYamlConfiguration().contains(path);
    }

    public void set(String path, Object value) {
        this.getYamlConfiguration().set(path, value);
    }

    public String getString(String path) {
        return this.getYamlConfiguration().getString(path);
    }

    public List<String> getList(String path) {
        return this.getYamlConfiguration().getStringList(path);
    }

    public double getDouble(String path) {
        return this.getYamlConfiguration().getDouble(path);
    }

    public int getInt(String path) {
        return this.getYamlConfiguration().getInt(path);
    }

    public float getFloat(String path) {
        return this.getYamlConfiguration().getInt(path);
    }

    public long getLong(String path) {
        return this.getYamlConfiguration().getLong(path);
    }

    public boolean getBoolean(String path) {
        return this.getYamlConfiguration().getBoolean(path);
    }

    public Set<String> getSection(String path, boolean keys) {
        return this.getYamlConfiguration().getConfigurationSection(path).getKeys(keys);
    }

    public Object get(String path) {
        return this.getYamlConfiguration().get(path);
    }

    ///

    public String getName() {
        return name;
    }

    public File getFile() {
        return file;
    }

    private void setFile(File file) {
        this.file = file;
    }

    private void setYamlConfiguration(YamlConfiguration yamlConfiguration) {
        this.yamlConfiguration = yamlConfiguration;
    }

    public YamlConfiguration getYamlConfiguration() {
        return yamlConfiguration;
    }
}
