package com.rutik;


import java.io.IOException;

public class Application {

    public static void main(String[] args) throws IOException {

        ScrapperService scrapperService = DIContainer.getScrapperService();
        scrapperService.scrap();

    }

}
