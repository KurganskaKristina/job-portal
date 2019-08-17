package com.rutik;

import com.gargoylesoftware.htmlunit.Page;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomNode;
import com.gargoylesoftware.htmlunit.html.DomNodeList;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.rutik.entity.VacancyEntity;
import com.sun.tools.javac.util.Assert;
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
                    String vacancyUrl = node.getAttributes()
                            .getNamedItem("href")
                            .getTextContent();

                    VacancyEntity vacancy = new VacancyEntity(section, vacancyName, vacancyUrl, null);
                    vacancyEntities.add(vacancy);
                }
            }

            return;
        }
    }

}
