package com.rutik.storage;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.rutik.entity.VacancyEntity;

import java.io.*;
import java.lang.reflect.Type;
import java.util.List;

public class StorageService {

    private final String storageName = "vacancies.json";
    private static StorageService instance;

    private StorageService(){}

    public static StorageService getInstance(){
        if (instance == null) {
            synchronized (StorageService.class) {
                if (instance == null) {
                    instance = new StorageService();
                }
            }
        }
        return instance;
    }

    public void save(List<VacancyEntity> vacancies) throws IOException {
        Gson gson = new Gson();
        String serialized = gson.toJson(vacancies);

        try (FileWriter fw = new FileWriter(storageName)) {
            fw.write(serialized);
        }
    }

    public List<VacancyEntity> load() throws IOException {
        Gson gson = new Gson();
        JsonReader reader = new JsonReader(new FileReader(storageName));

        Type type = new TypeToken<List<VacancyEntity>>() {}.getType();
        return gson.fromJson(reader, type);
    }
}
