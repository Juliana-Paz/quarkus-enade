package br.unitins.estagio.juliana.service;

import java.time.LocalTime;
import java.util.UUID;

import jakarta.inject.Inject;

import io.quarkus.scheduler.Scheduled;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class MessageCalculator {

    @Inject
    TelegramBot bot;

    // Configura o bot para enviar a mensagem todos os dias em um determinado horário
    // segundos - minutos - horas - dia do mês - mês - dia da semana
    @Scheduled(cron = "0 45 16 * * ?")
    public void message() {
        String message = "Now it is " + LocalTime.now().withNano(0) + ": " + UUID.randomUUID();
        //bot.sendMessage(message);
    }

}
