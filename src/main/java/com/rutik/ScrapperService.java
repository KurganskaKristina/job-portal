package com.rutik;


import com.gargoylesoftware.htmlunit.SilentCssErrorHandler;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomNode;
import com.gargoylesoftware.htmlunit.html.DomNodeList;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.rutik.entity.VacancyEntity;
import org.w3c.dom.Node;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class ScrapperService {

    private ScrapperService (){}
    private static ScrapperService instance;
    public static ScrapperService getInstance() {
        if (instance == null) {
            synchronized (ScrapperService.class) {
                if (instance == null) {
                    instance = new ScrapperService();
                }
            }
        }
        return instance;
    }

    private static final String baseUrl = "https://jobs.dou.ua/";

    public void scrap() throws IOException {

        try (WebClient webClient = new WebClient()) {
            webClient.getOptions().setThrowExceptionOnScriptError(false);
            webClient.setCssErrorHandler(new SilentCssErrorHandler());

            HtmlPage page = webClient.getPage(baseUrl);

            DomNodeList<DomNode> domNodes = page.querySelectorAll("div.jobs-page li.cat a.cat-link");
            List<VacancyEntity> vacancyEntities = new ArrayList<>();

            // iterate through vacancy sections
            for (DomNode node : domNodes) {
                String section = node.asText();

                Node href = node.getAttributes().getNamedItem("href");
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

            }
            for (VacancyEntity vacancyEntity: vacancyEntities){
                String concreteVacancyUrl = vacancyEntity.getUrl();
                HtmlPage concreteVacancy = webClient.getPage(concreteVacancyUrl);
                String concreteVacancyDescription = concreteVacancy.querySelector("div.l-vacancy").asText();
                vacancyEntity.setDescription(concreteVacancyDescription);
            }
            System.out.println("Total vacancies scrapped: " + vacancyEntities.size());
            System.out.println(vacancyEntities.get(0).getDescription());
            //for(VacancyEntity vac: vacancyEntities){
            //     System.out.println(vac.getDescription());
            //}

            return;
        }
    }

}
