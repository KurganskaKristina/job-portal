package com.rutik;


import com.rutik.entity.VacancyEntity;
import com.rutik.storage.StorageService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Application {

    public static void main(String[] args) throws IOException {

        StorageService storageService = StorageService.getInstance();

        List<VacancyEntity> vacancies = new ArrayList<>();
        vacancies.add(new VacancyEntity("Java dev", "Java 8, 100500$"));

        storageService.save(vacancies);
        List<VacancyEntity> loadedVacancies = storageService.load();

        System.out.println("Hello!");
    }

}
