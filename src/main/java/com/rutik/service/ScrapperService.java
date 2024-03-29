package com.rutik.service;


import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.SilentCssErrorHandler;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomNode;
import com.gargoylesoftware.htmlunit.html.DomNodeList;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.rutik.entity.VacancyEntity;
import com.rutik.service.Storage;
import org.w3c.dom.Node;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class ScrapperService {

    public ScrapperService(Storage storage, WebClient webClient){
        this.storage = storage;
        this.webClient = webClient;
    }

    private static final String baseUrl = "https://jobs.dou.ua/";

    private Storage storage;
    private WebClient webClient;

    public void scrap() throws IOException {
        storage.reset();

        HtmlPage page = webClient.getPage(baseUrl);

        DomNodeList<DomNode> sectionNodes = page.querySelectorAll("div.jobs-page li.cat a.cat-link");
        List<VacancyEntity> vacancyEntities = new ArrayList<>();

        // iterate through vacancy sections
        for (DomNode sectionNode: sectionNodes) {
            String section = sectionNode.asText();

            Node href = sectionNode.getAttributes().getNamedItem("href");
            String sectionUrl = href.getTextContent();

            HtmlPage sectionPage = webClient.getPage(sectionUrl);
            DomNodeList<DomNode> sectionVacancies = sectionPage.querySelectorAll("div.vacancy a.vt");

            // iterate through vacancies in the sections
            for (DomNode sectionVacancy : sectionVacancies) {
                String vacancyName = sectionVacancy.asText();
                String vacancyUrl = sectionVacancy.getAttributes()
                        .getNamedItem("href")
                        .getTextContent();

                VacancyEntity vacancy = new VacancyEntity(section, vacancyName, vacancyUrl, null);
                vacancyEntities.add(vacancy);
            }

            break;
        }
        for (VacancyEntity vacancyEntity: vacancyEntities){
            String concreteVacancyUrl = vacancyEntity.getUrl();
            HtmlPage concreteVacancy = webClient.getPage(concreteVacancyUrl);
            String concreteVacancyDescription = concreteVacancy.querySelector("div.l-vacancy").asText();
            vacancyEntity.setDescription(concreteVacancyDescription);
        }
        System.out.println("Total vacancies scrapped: " + vacancyEntities.size());

        for(VacancyEntity vacancy:vacancyEntities) {
            storage.append(vacancy);
        }

        return;
    }

}
