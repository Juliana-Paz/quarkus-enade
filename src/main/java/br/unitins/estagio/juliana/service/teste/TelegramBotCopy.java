package br.unitins.estagio.juliana.service.teste;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import br.unitins.estagio.juliana.model.User;
import br.unitins.estagio.juliana.service.EnadeServiceImpl;
import br.unitins.estagio.juliana.service.UserServiceImpl;
import io.quarkus.scheduler.Scheduled;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
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
public class TelegramBotCopy {

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

    private void sendMessagesToUsers(List<User> users, String message) {
        for (User user : users) {
            sendMessage(message, user.getTelegramUserId().toString());
        }
    }

    private List<User> filterUsersWithTelegramId(List<User> users) {
        return users.stream()
                .filter(user -> user.getTelegramUserId() != null)
                .collect(Collectors.toList());
    }

    //@Scheduled(every = "1s")
    public void getUpdates() {
        // Exibe uma mensagem indicando o início da consulta por atualizações
        System.out.println("searching Updates...");
        try {
            // Obtém as atualizações do Telegram
            JsonObject updatesJson = searchForUpdates();
            processEachUpdate(updatesJson);
        } catch (Exception e) {
            // Trata exceções que podem ocorrer durante a consulta por atualizações
            System.err.println("Erro ao consultar atualizações: " + e.getMessage());
        }
    }

    // Busca as atualizações do telegram
    private JsonObject searchForUpdates() {
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

    private void processEachUpdate(JsonObject updatesJson) {
        System.out.println("Iniciando o metodo processEachUpdate");
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
            // Se contém mensagem, verifica se o usuário já existe no banco
            if (update.containsKey("message")) {                
                JsonObject message = update.getJsonObject("message");    
                System.out.println("Até aqui está rodando");
                
                Long userId = extractTelegramUserId(message);
                System.out.println("Até aqui está rodando?????");
                if (userExists(userId)) {
                    System.out.println("O usuário " + userId + " já existe no banco de dados.");        
                } else {
                    System.out.println("Tentando inserir o usuário " + userId + " no banco.");        
                    getLastMessageFromUser(message, userId);            
                    if (isMessageStartCommand(message.toString())) {
                        // Processa a mensagem do remetente
                        extractUserData(message);
                    }     
                }
            }
        }
    }

    private boolean isMessageStartCommand(String message) {       
        // Retorna True se a mensagem for igual a /start
        return message.trim().equalsIgnoreCase("/start");
    }

    private String getLastMessageFromUser(JsonObject updatesJson, Long userId) {
        JsonArray results = updatesJson.getJsonArray("result");
        
        // Itera sobre as atualizações em ordem inversa para encontrar a última mensagem do usuário
        for (int i = results.size() - 1; i >= 0; i--) {
            JsonObject update = results.getJsonObject(i);

            // Verifica se a atualização contém uma mensagem e se é do usuário específico
            if (update.containsKey("message")) {
                JsonObject message = update.getJsonObject("message");                       
                JsonObject sender = message.getJsonObject("from");
                Long senderId = extractTelegramUserId(sender);
                // Se a mensagem for do usuário especificado, retorna o texto da mensagem
                if (Objects.equals(senderId, userId)) {
                    System.out.println("ultima mensagem do usuario: " + userId);
                    return extractUserTextMessage(message);
                }
            }
        }

        // Retorna null se não encontrou nenhuma mensagem do usuário especificado
        return null;
    }

    private void extractUserData(JsonObject message) {
        User user = new User();
        // Verifica se a mensagem contém informações do remetente
        if (message.containsKey("from")) {
            JsonObject sender = message.getJsonObject("from");

            Long userId = extractTelegramUserId(sender);
            user.setTelegramUserId(userId);
            // requestUserName(userId);
            // user.setName(extractUserTextMessage(message));
            // requestUserSurname(userId);
            // user.setSurname(extractUserTextMessage(message));
            // requestUserPhoneNumber(userId);
            // user.setTelegram(extractUserTextMessage(message));
            // requestUserMatricula(userId);
            // user.setMatricula(extractUserTextMessage(message));

            insertUser(userId, user);
        }
    }

    private String extractUserTextMessage(JsonObject message) {
        // Verifica se a mensagem contém o texto
        if (message.containsKey("text")) {
            // Extrai e retorna o texto da mensagem
            return message.getString("text");
        } else {
            // Retorna null se não houver texto na mensagem
            return null;
        }
    }

    private Long extractTelegramUserId(JsonObject sender) {
        // Obtém o ID do remetente
        //return sender.getJsonNumber("id").longValue();

        /*
        Long id = 0L;
        if (sender != null) {
            System.out.println("sender não está nulo");
    
            System.out.println("antes de pegar o id funciona.");
            System.out.println("print do id" + sender.getJsonNumber("id").longValue());
            id = sender.getJsonNumber("id").longValue();
        } else {
            System.out.println("está pegando o id.");
            return id;            
        }
        return 0l;

         */

         try {
            // Obtém o ID do remetente
            return sender.getJsonNumber("id").longValue();
        } catch (Exception e) {
            // Em caso de exceção ao extrair o ID do remetente, imprime o erro e retorna null
            System.err.println("Erro ao extrair o ID do remetente: " + e.getMessage());
            return null;
        }
    }

    private void requestUserName(Long userId) {
        sendMessage("Informe seu nome: ", userId.toString());
    }

    private void requestUserSurname(Long userId) {
        sendMessage("Informe seu nome sobrenome: ", userId.toString());
    }

    private void requestUserPhoneNumber(Long userId) {
        sendMessage("Informe numero de telefone: ", userId.toString());
    }

    private void requestUserMatricula(Long userId) {
        sendMessage("Informe sua matrícula: ", userId.toString());
    }

    private boolean userExists(Long userId) {
        // Verifica se o usuário com o ID fornecido existe no banco de dados
        User user = userService.findByTelegramUserId(userId);
        // Retorna True se o usuário existir
        return user != null;
    }

    private void insertUser(Long userId, User user) {
        userService.insert(user);
    }

    @PreDestroy
    private void closeClient() {
        client.close();
    }

}
