package com.rutik;


import com.rutik.entity.VacancyEntity;
import com.rutik.storage.StorageService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Application {

    public static void main(String[] args) throws IOException {

        StorageService storageService = StorageService.getInstance();
        storageService.reset();
        storageService.append(new VacancyEntity("Java dev", "Java 8, 100500$"));
        storageService.append(new VacancyEntity("PHP dev", "PHP 7, 100500$"));


        List<VacancyEntity> loadedVacancies = storageService.load();

        for(int i = 0; i<loadedVacancies.size(); i++){
            System.out.println(loadedVacancies.get(i));
        }


    }

}
