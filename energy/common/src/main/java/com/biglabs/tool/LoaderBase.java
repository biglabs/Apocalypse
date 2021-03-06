package com.biglabs.tool;

import com.biglabs.tool.model.poco.ModelBase;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by thainguy on 9/1/2016.
 */
public abstract class LoaderBase<T extends ModelBase> {

    public Map<String, T> load(String root, String configFile) throws Exception {
        File file = new File(root, configFile);
        return load(file);
    }

    public Map<String, T> load(String configFile) throws Exception {
        File file = new File(configFile);
        return load(file);
    }

    protected Map<String, T> load(File file) throws Exception {
        if (!file.exists()) {
            throw new Exception(file.getName() + " doesn't exist");
        }
        if (file.isDirectory()) {
            throw new Exception(file.getName() + " is directory.");
        }

        Map<String, T> regions = new HashMap<>();
        Reader reader = new FileReader(file.getAbsolutePath());
        BufferedReader bufferedReader = new BufferedReader(reader);
        String line = bufferedReader.readLine();
        while (line != null) {
            T model = create(line);
            regions.put(model.getId(), model);
            line = bufferedReader.readLine();
        }
        bufferedReader.close();
        return regions;
    }

    protected abstract T create(String line) throws Exception;
}
