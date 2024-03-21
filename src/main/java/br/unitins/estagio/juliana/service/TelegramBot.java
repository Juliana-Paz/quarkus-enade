package br.unitins.estagio.juliana.service;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;

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

    @ConfigProperty(name = "telegram.chatId")
    String chatId;

    private static final String TELEGRAM_API_BASE_URL = "https://api.telegram.org/bot";

    private Client client;

    @Inject
    UserService userService;

    @PostConstruct
    void initClient() {
        client = ClientBuilder.newClient();
    }

    public void sendMessage(String message) {
        try {
            // Construindo a URL completa para o endpoint sendMessage
            String sendMessageUrl = TELEGRAM_API_BASE_URL + token + "/sendMessage";
            
            // Criando a chamada para o endpoint sendMessage
            Invocation.Builder builder = client.target(sendMessageUrl)
                    .request(MediaType.APPLICATION_JSON);

            // Montando o corpo da mensagem
            JsonObject jsonBody = Json.createObjectBuilder()
                    .add("chat_id", chatId)
                    .add("text", message)
                    .build();

            // Enviando a mensagem e obtendo a resposta
            Response response = builder.post(Entity.json(jsonBody));
            JsonObject jsonResponse = response.readEntity(JsonObject.class);
            System.out.println("  Enviando a mensagem e obtendo a resposta");


            // Verificando se a mensagem foi enviada com sucesso
            boolean ok = jsonResponse.getBoolean("ok", false);
            if (!ok) {
                System.err.println("Couldn't successfully send message");
            }

            /*
            // Obtendo o ID da mensagem enviada
            JsonObject result = jsonResponse.getJsonObject("result");
            Long messageId = result.getJsonNumber("message_id").longValue();
            
            // Obtendo o ID do chat
            Long chatId = result.getJsonObject("chat").getJsonNumber("id").longValue();
            
            // Obtendo o ID do usuário
            Long userId = result.getJsonObject("from").getJsonNumber("id").longValue();

            User user = new User();
            user.setTelegramUserId(userId);
            userService.insert(user);
            System.out.println(user.getTelegramUserId());
            */


            /*
            if(userService.telegramUserIdExists(userId) != null) {
                User user = new User();
                user.setTelegramUserId(userId);
                userService.insert(user);
                System.out.println(user.getTelegramUserId());
            } else {
                System.out.println("The user already exists.");
            }            
            
            */

        } catch (Exception e) {
            System.err.println("Couldn't successfully send message, " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    @Scheduled(every = "1s")
    public void pollUpdates() {
        System.out.println("  poll");
        try {
            while (true) {
                // Fazendo a chamada para obter as atualizações
                Invocation.Builder builder = client.target(TELEGRAM_API_BASE_URL + token + "/getUpdates")
                .request(MediaType.APPLICATION_JSON);
                Response response = builder.get();
                
                // Verificando se a chamada foi bem sucedida
                if (response.getStatus() == 200) {
                    // Processando as atualizações
                    JsonObject updatesJson = response.readEntity(JsonObject.class);
                    processUpdates(updatesJson);
                } else {
                    System.err.println("Erro ao obter atualizações: " + response.getStatus());
                }
                
                // Esperando um intervalo de tempo antes de fazer a próxima chamada
                Thread.sleep(5000); // Intervalo de 5 segundos (ajuste conforme necessário)
            }
        } catch (Exception e) {
            System.err.println("Erro ao consultar atualizações: " + e.getMessage());
        }
    }
    
    private void processUpdates(JsonObject updatesJson) {
        // Implemente a lógica para processar as atualizações aqui
        // Você pode analisar o JSON retornado para extrair as informações relevantes, como o ID da mensagem, texto da mensagem, etc.
        // E, em seguida, realizar a lógica do bot com base nas informações extraídas
        
        // Verificando se existem atualizações
        if (!updatesJson.containsKey("result")) {
            return;
        }

        // Obtendo a lista de atualizações
        JsonArray results = updatesJson.getJsonArray("result");

        // Iterando sobre as atualizações
        for (JsonValue result : results) {
            JsonObject update = (JsonObject) result;

            // Verificando se a atualização contém uma mensagem
            if (update.containsKey("message")) {
                JsonObject message = update.getJsonObject("message");

                findUserId(message);

                
            }
        }
    }
    
    private void findUserId(JsonObject message) {
        
        // Verificando se a mensagem contém informações do remetente
        if (message.containsKey("from")) {
            JsonObject sender = message.getJsonObject("from");
            
            // Obtendo o ID do remetente
            Long userId = sender.getJsonNumber("id").longValue();
            
            // Verificando se o usuário já existe no banco de dados
            if (userService.telegramUserIdExists(userId) != null) {
                // Se o usuário não existir, armazenar o ID no banco de dados
                User user = new User();
                user.setTelegramUserId(userId);
                userService.insert(user);
                
                System.out.println(user.getTelegramUserId());
            } else {
                System.out.println(userId + " não inseriu, já existe");

            }
        }
    }
    
    @PreDestroy
    private void closeClient() {
        client.close();
    }

}


