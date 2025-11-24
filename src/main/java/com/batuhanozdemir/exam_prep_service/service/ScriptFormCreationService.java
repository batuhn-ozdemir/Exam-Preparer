package com.batuhanozdemir.exam_prep_service.service;

import com.batuhanozdemir.exam_prep_service.entity.MessageResponseFromScript;
import com.batuhanozdemir.exam_prep_service.entity.QuestionPOJO;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class ScriptFormCreationService {

    @Value("${console_google_access_token}")
    public String accessToken;

    @Value("${google_app_script_url}")
    public String scriptURL;

    public final WebClient webClient;

    public ScriptFormCreationService(WebClient.Builder webClient) {
        this.webClient = webClient.build();
    }

    public MessageResponseFromScript getFormURL(QuestionPOJO questionPOJO){

        String firstResponse = webClient.post()
                .uri(scriptURL)
                .header("Content-Type" , "application/json")
                .header("Authorization" , "Bearer "+accessToken)
                .bodyValue(questionPOJO)
                .retrieve()
                .bodyToMono(String.class)
                .block();

        String storeResponse = extractRedirectUrl(firstResponse);

        MessageResponseFromScript secondResponse = webClient.get()
                .uri(storeResponse)
                .header("Content-Type" , "application/json")
                .retrieve()
                .bodyToMono(MessageResponseFromScript.class)
                .block();

        return secondResponse;

    }

    public String extractRedirectUrl(String htmlResponse) {
        // Parsing the HTML response
        Document docmnt = Jsoup.parse(htmlResponse);

        // Finding the <a> tag
        Element linkElement = docmnt.selectFirst("a");

        if (linkElement != null) {
            // Getting the href attribute
            return linkElement.attr("href");
        } else {
            return "NULL";
        }
    }
}
