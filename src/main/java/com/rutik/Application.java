package com.rutik;


import com.rutik.entity.VacancyEntity;
import com.rutik.storage.StorageService;

import java.io.IOException;
import java.util.*;

public class Application {

    public static void main(String[] args) throws IOException {

        ScrapperService scrapperService = ScrapperService.getInstance();
        scrapperService.scrap();

    }

}
