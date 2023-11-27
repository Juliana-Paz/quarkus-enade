package br.unitins.estagio.juliana.service;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.json.JsonObject;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.Response;

@ApplicationScoped
public class TelegramBot {

    @ConfigProperty(name = "telegram.token")
    String token;

    @ConfigProperty(name = "telegram.chatId")
    String chatId;

    private Client client;
    private WebTarget baseTarget;

    @PostConstruct
    void initClient() {
        client = ClientBuilder.newClient();
        baseTarget = client.target("https://api.telegram.org/bot{token}")
                .resolveTemplate("token", this.token);
    }

    public void sendMessage(String message) {
        try {
            Response response = baseTarget.path("sendMessage")
                    .queryParam("chat_id", chatId)
                    .queryParam("text", message)
                    .request()
                    .get();

            JsonObject json = response.readEntity(JsonObject.class);
            boolean ok = json.getBoolean("ok", false);
            if (!ok)
                System.err.println("Couldn't successfully send message");
        } catch (Exception e) {
            System.err.println("Couldn't successfully send message, " + e.getMessage());
            e.printStackTrace();
        }
    }

    @PreDestroy
    private void closeClient() {
        client.close();
    }

}
