package com.rutik;

import com.rutik.entity.VacancyEntity;
import com.rutik.storage.Storage;
import com.rutik.storage.StorageService;

public class DIContainer {

    private static ScrapperService scrapperService;
    private static StorageService storageService;

    public static ScrapperService getScrapperService() {
        if (scrapperService == null) {
            synchronized (ScrapperService.class) {
                if (scrapperService == null) {
                    scrapperService = new ScrapperService(getStorageService());
                }
            }
        }
        return scrapperService;
    }

    public static Storage<VacancyEntity> getStorageService() {
        if (storageService == null) {
            synchronized (StorageService.class) {
                if (storageService == null) {
                    storageService = new StorageService();
                }
            }
        }
        return storageService;
    }

}
