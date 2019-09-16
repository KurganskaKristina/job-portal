package com.rutik.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.rutik.entity.VacancyEntity;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


public class StorageService implements Storage<VacancyEntity> {

    private final String storageName = "vacancies.json";

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

        try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(storageName, true), StandardCharsets.UTF_8))) {
            bw.append(serialized);
        }
    }

    @Override
    public List<VacancyEntity> load() throws IOException {
        BufferedReader fr = new BufferedReader(new FileReader(storageName));
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        Set<VacancyEntity> result = fr.lines()
                .map(serializedVacancy -> {
                    VacancyEntity vacancy = gson.fromJson(serializedVacancy, VacancyEntity.class);
                    return vacancy;
                })
                .collect(Collectors.toSet());

        return new ArrayList<>(result);
    }


}
