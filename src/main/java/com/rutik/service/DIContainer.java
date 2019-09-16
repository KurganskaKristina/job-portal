package com.rutik.service;

import com.gargoylesoftware.htmlunit.*;
import com.gargoylesoftware.htmlunit.html.HTMLParserListener;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.javascript.JavaScriptErrorListener;
import com.rutik.entity.VacancyEntity;

import java.net.MalformedURLException;
import java.net.URL;

public class DIContainer {

    private static ScrapperService scrapperService;
    private static StorageService storageService;
    private static WebClient webClient;

    public static ScrapperService getScrapperService() {
        if (scrapperService == null) {
            synchronized (ScrapperService.class) {
                if (scrapperService == null) {
                    scrapperService = new ScrapperService(
                            getStorageService(),
                            getWebClient()
                    );
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

    public static WebClient getWebClient() {
        if (webClient == null) {
            synchronized (StorageService.class) {
                if (webClient == null) {
                    webClient = createWebClient();
                }
            }
        }
        return webClient;
    }

    private static WebClient createWebClient() {
        final WebClient webClient = new WebClient(BrowserVersion.FIREFOX_52);
        webClient.getOptions().setJavaScriptEnabled(false);
        webClient.getOptions().setThrowExceptionOnScriptError(false);
        webClient.getOptions().setCssEnabled(false);
        webClient.getOptions().setUseInsecureSSL(true);
        webClient.setAjaxController(new NicelyResynchronizingAjaxController());

        webClient.setHTMLParserListener(new HTMLParserListener() {
            @Override public void error(String message, URL url, String html, int line, int column, String key) { }
            @Override public void warning(String message, URL url, String html, int line, int column, String key) { }
        });
        webClient.setJavaScriptErrorListener(new JavaScriptErrorListener() {
            @Override public void scriptException(HtmlPage page, ScriptException scriptException) { }
            @Override public void timeoutError(HtmlPage page, long allowedTime, long executionTime) { }
            @Override public void malformedScriptURL(HtmlPage page, String url, MalformedURLException malformedURLException) { }
            @Override public void loadScriptError(HtmlPage page, URL scriptUrl, Exception exception) { }
            @Override public void warn(String message, String sourceName, int line, String lineSource, int lineOffset) { }
        });
        webClient.setCssErrorHandler(new SilentCssErrorHandler());

        return webClient;
    }

}
