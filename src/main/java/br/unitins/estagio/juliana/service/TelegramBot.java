package br.unitins.estagio.juliana.service;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;

import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import br.unitins.estagio.juliana.model.User;
import io.quarkus.scheduler.Scheduled;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonValue;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.Invocation;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@ApplicationScoped
public class TelegramBot {

    @ConfigProperty(name = "telegram.token")
    String token;

    // @ConfigProperty(name = "telegram.chatId")
    // String chatId;

    private static final String TELEGRAM_API_BASE_URL = "https://api.telegram.org/bot";

    private Client client;

    @Inject
    UserServiceImpl userService;

    @Inject
    EnadeServiceImpl enadeService;

    @PostConstruct
    void initClient() {
        client = ClientBuilder.newClient();
    }

    public void sendMessage(String message, String chatId) {
        try {
            JsonObject jsonBody = buildMessageBody(message, chatId);
            Response response = createSendMessageRequest(jsonBody);
            processSendMessageResponse(response);
        } catch (Exception e) {
            System.err.println("Couldn't successfully send message to user " + chatId + ": " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private JsonObject buildMessageBody(String message, String chatId) {
        return Json.createObjectBuilder()
                .add("chat_id", chatId)
                .add("text", message)
                .build();
    }
    
    private Response createSendMessageRequest(JsonObject jsonBody) {
        String sendMessageUrl = TELEGRAM_API_BASE_URL + token + "/sendMessage";
        Invocation.Builder builder = client.target(sendMessageUrl)
                .request(MediaType.APPLICATION_JSON);
        return builder.post(Entity.json(jsonBody));
    }
    
    private void processSendMessageResponse(Response response) {
        JsonObject jsonResponse = response.readEntity(JsonObject.class);
        boolean ok = jsonResponse.getBoolean("ok", false);
        if (!ok) {
            System.err.println("Couldn't successfully send message, bad response.");
        }
    }
    
    public void sendMessageToAll(String message) {
        try {
            List<User> usersWithTelegramId = filterUsersWithTelegramId(userService.findByAll());
            sendMessagesToUsers(usersWithTelegramId, message);
        } catch (Exception e) {
            System.err.println("Couldn't successfully send message to all users: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private List<User> filterUsersWithTelegramId(List<User> users) {
        return users.stream()
                .filter(user -> user.getTelegramUserId() != null)
                .collect(Collectors.toList());
    }
    
    private void sendMessagesToUsers(List<User> users, String message) {
        for (User user : users) {
            sendMessage(message, user.getTelegramUserId().toString());
        }
    }
    
    @Scheduled(every = "1s")
    public void pollUpdates() {
        // Exibe uma mensagem indicando o início da consulta por atualizações
        System.out.println("iniciado um poll");
        try {
            // Obtém as atualizações do Telegram
            JsonObject updatesJson = fetchUpdatesFromTelegram();
            // Processa as atualizações, se houver
            if (updatesJson != null) {
                processUpdates(updatesJson);
            }
        } catch (Exception e) {
            // Trata exceções que podem ocorrer durante a consulta por atualizações
            System.err.println("Erro ao consultar atualizações: " + e.getMessage());
        }
    }

    // Busca as atualizações do telegram
    private JsonObject fetchUpdatesFromTelegram() {
        // Faz uma chamada para obter as atualizações do Telegram
        Invocation.Builder builder = client.target(TELEGRAM_API_BASE_URL + token + "/getUpdates")
                .request(MediaType.APPLICATION_JSON);
        Response response = builder.get();

        // Verifica se a chamada foi bem sucedida
        if (response.getStatus() == 200) {
            // Retorna o JSON com as atualizações
            return response.readEntity(JsonObject.class);
        } else {
            // Exibe uma mensagem de erro caso a chamada falhe
            System.err.println("Erro ao obter atualizações: " + response.getStatus());
            return null;
        }
    }

    private void processUpdates(JsonObject updatesJson) {
        // Verifica se existem atualizações disponíveis no JSON
        if (!updatesJson.containsKey("result")) {
            return;
        }

        // Obtém a lista de atualizações
        JsonArray results = updatesJson.getJsonArray("result");

        // Itera sobre cada atualização na lista
        for (JsonValue result : results) {
            JsonObject update = (JsonObject) result;

            // Verifica se a atualização contém uma mensagem
            if (update.containsKey("message")) {
                JsonObject message = update.getJsonObject("message");
                // Processa a mensagem do remetente
                processUserMessage(message);
            }
        }
    }
    
    private void processUserMessage(JsonObject message) {
        // Verifica se a mensagem contém informações do remetente
        if (message.containsKey("from")) {
            JsonObject sender = message.getJsonObject("from");
            Long userId = extractUserId(sender);
            String userFirstName = extractUserFirstName(sender);
            persistUserIfNotExists(userId, userFirstName);
        }
    }

    private Long extractUserId(JsonObject sender) {
        // Obtém o ID do remetente
        return sender.getJsonNumber("id").longValue();
    }

    private String extractUserFirstName(JsonObject sender) {
        // Obtém o primeiro nome do remetente
        return sender.getString("first_name");
    }
    
    private void persistUserIfNotExists(Long userId, String userFirstName) {
        // Verifica se o usuário já existe no banco de dados
        if (userService.findByTelegramUserId(userId) == null) {
            // Se o usuário não existir, armazena o ID no banco de dados
            User user = new User();
            user.setTelegramUserId(userId);
            user.setName(userFirstName);
            userService.insert(user);
        } else {
            System.out.println("O usuário já existe no banco de dados.");
        }
    }
    
    @PreDestroy
    private void closeClient() {
        client.close();
    }

}


