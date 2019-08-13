package com.rutik.storage;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.rutik.entity.VacancyEntity;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class StorageService implements Storage<VacancyEntity> {

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


    @Override
    public void reset() throws IOException {
    FileWriter fr = new FileWriter(storageName);
    fr.write("");
    fr.close();
    }

    @Override
    public void append(VacancyEntity obj) throws IOException {
        Gson gson = new Gson();
        String serialized = gson.toJson(obj) + "\n";

        try (FileWriter fw = new FileWriter(storageName, true)) {
            fw.append(serialized);
        }
    }

    @Override
    public List<VacancyEntity> load() throws IOException {
        BufferedReader fr = new BufferedReader(new FileReader(storageName));
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        List<VacancyEntity> result = fr.lines()
                .map(x -> {
                    VacancyEntity y = gson.fromJson(x, VacancyEntity.class);
                    return y;
                })
                .collect(Collectors.toList());
        return result;
    }


}
