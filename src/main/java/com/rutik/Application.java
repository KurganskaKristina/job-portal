package com.rutik;


import com.rutik.service.DIContainer;
import com.rutik.service.ScrapperService;

import java.io.IOException;

public class Application {

    public static void main(String[] args) throws IOException {

        ScrapperService scrapperService = DIContainer.getScrapperService();
        scrapperService.scrap();

    }

}
