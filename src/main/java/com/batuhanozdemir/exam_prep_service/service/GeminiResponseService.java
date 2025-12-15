package com.batuhanozdemir.exam_prep_service.service;

import com.batuhanozdemir.exam_prep_service.entity.QuestionAnswerPOJO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@Service
public class GeminiResponseService {

    @Value("${gemini.api.url}")
    private String geminiApiUrl;

    private final WebClient webClient;

    public GeminiResponseService() {
        this.webClient = WebClient.builder()
                .defaultHeader("Content-Type", "application/json")
                .build();
    }

    public QuestionAnswerPOJO getAnswer(String question) {
        // Tırnak işaretlerini kaçış karakteriyle düzeltelim ki JSON bozulmasın
        String safeQuestion = question.replace("\"", "\\\"").replace("\n", " ");

        // 1. JSON'u ELLE OLUŞTURUYORUZ (En Garanti Yöntem)
        String jsonBody = String.format(
                "{\"contents\": [{\"parts\": [{\"text\": \"%s\"}]}]}",
                safeQuestion
        );

        return sendRequest(jsonBody);
    }

    public QuestionAnswerPOJO getAnswer(String topic, int noOfQuestion) {
        // İstenen JSON format şablonu
        String formatExample = "{" +
                "\"topic\": \"...\", " +
                "\"questions\": [{\"text\": \"...\", \"choices\": [\"...\"]}], " +
                "\"correctAnswers\": [\"...\"]" +
                "}";

        String prompt = String.format(
                "Give me %d questions on topic '%s' strictly in this JSON format: %s. Do not add markdown formatting like ```json.",
                noOfQuestion, topic, formatExample
        );

        String safePrompt = prompt.replace("\"", "\\\"").replace("\n", " ");

        // JSON Body
        String jsonBody = String.format(
                "{\"contents\": [{\"parts\": [{\"text\": \"%s\"}]}]}",
                safePrompt
        );

        return sendRequest(jsonBody);
    }

    private QuestionAnswerPOJO sendRequest(String jsonBody) {
        try {
            return webClient.post()
                    .uri(geminiApiUrl)
                    .bodyValue(jsonBody) // String olarak yolluyoruz
                    .retrieve()
                    .bodyToMono(QuestionAnswerPOJO.class)
                    .block();

        } catch (WebClientResponseException e) {
            // --- HATA DETAYINI KONSOLA BAS ---
            System.err.println("❌ GEMINI HATA VERDİ!");
            System.err.println("👉 Status Code: " + e.getStatusCode());
            System.err.println("👉 Google'ın Cevabı: " + e.getResponseBodyAsString());
            System.err.println("👉 Gönderilen JSON: " + jsonBody);
            throw e;
        } catch (Exception e) {
            System.err.println("❌ Beklenmeyen Hata: " + e.getMessage());
            throw e;
        }
    }
}