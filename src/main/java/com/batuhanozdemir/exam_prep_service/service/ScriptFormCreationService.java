package com.batuhanozdemir.exam_prep_service.service;

import com.batuhanozdemir.exam_prep_service.entity.MessageResponseFromScript;
import com.batuhanozdemir.exam_prep_service.entity.QuestionPOJO;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import com.google.auth.oauth2.GoogleCredentials; // Yeni eklenen
import com.google.auth.oauth2.AccessToken;

import java.io.IOException;
import java.util.Collections;

@Service
public class ScriptFormCreationService {

    @Value("${google.credentials.path}")
    private String credentialsPath;

    @Value("${google_app_script_url}")
    public String scriptURL;

    public final WebClient webClient;

    public ScriptFormCreationService() {
        this.webClient = WebClient.builder().build();
    }

    private String getAccessToken() {
        try {
            // Dosyayı resources klasöründen bulur (örn: src/main/resources/credentials.json)
            GoogleCredentials credentials = GoogleCredentials.fromStream(
                    new ClassPathResource(credentialsPath).getInputStream()
            ).createScoped(Collections.singletonList("https://www.googleapis.com/auth/drive")); // Drive ve Script yetkisi

            credentials.refreshIfExpired();
            AccessToken token = credentials.getAccessToken();
            return token.getTokenValue();
        } catch (IOException e) {
            throw new RuntimeException("Google Credentials dosyası okunamadı!", e);
        }
    }

    public MessageResponseFromScript getFormURL(QuestionPOJO questionPOJO){

        // Her istekte taze token alıyoruz
        String dynamicToken = getAccessToken();

        String firstResponse = webClient.post()
                .uri(scriptURL)
                .header("Content-Type" , "application/json")
                .header("Authorization" , "Bearer " + dynamicToken) // Dinamik token
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
