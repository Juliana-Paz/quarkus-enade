package br.unitins.estagio.juliana.service;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import br.unitins.estagio.juliana.model.Question;
import io.quarkus.scheduler.Scheduled;
import jakarta.inject.Inject;

public class SendMessages {
    
    // @ConfigProperty(name = "telegram.chatId")
    // String chatId;
    
    @Inject
    TelegramBot bot;
    
    @Inject
    EnadeServiceImpl enadeService;
    
    // Configura o bot para enviar a mensagem todos os dias em um determinado horário
    // segundos - minutos - horas - dia do mês - mês - dia da semana
    //@Scheduled(every = "1s")
    public void sendMessage() {
        bot.sendMessageToAll();
    }

}
