package com.mikarte.constructioncalculator.service;

import com.mikarte.constructioncalculator.config.BotConfig;
import com.vdurmont.emoji.EmojiParser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class TelegramBot extends TelegramLongPollingBot {

    private final BotConfig botConfig;

    private final Map<InputStatus, Double> data = new HashMap<>();
    InputStatus inputStatus = InputStatus.NONE;
    static final String ERROR_TEXT = "Error occurred: ";
    static final String YES_BUTTON = "YES_BUTTON";
    static final String NO_BUTTON = "NO_BUTTON";

    public TelegramBot(BotConfig botConfig) {
        this.botConfig = botConfig;
        List<BotCommand> listOfCommands = Arrays.asList(
                new BotCommand("/start", "get a welcome message"),
                new BotCommand("/addData", "get your data stored"),
                new BotCommand("/addData2", "get your data stored"),
                new BotCommand("/addData3", "get your data stored"),
                new BotCommand("/deletedata", "delete my data"),
                new BotCommand("/help", "info how to use this bot"),
                new BotCommand("/settings", "set your preferences"));
        try {
            this.execute(new SetMyCommands(listOfCommands, new BotCommandScopeDefault(), null));
        } catch (TelegramApiException e) {
            log.error("Error setting bot's command list: " + e.getMessage());
        }
    }

    @Override
    public String getBotUsername() {
        return botConfig.getBotName();
    }

    @Override
    public String getBotToken() {
        return botConfig.getToken();
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();

            long chatId = update.getMessage().getChatId();

            if (messageText.contains("/")) {
                switch (messageText) {
                    case "/start" ->
                            startCommandReceived(chatId, update.getMessage().getChat().getFirstName(), update.getMessage());
                    case "/addData" -> {
                        addDataCommandReceived(chatId, update.getMessage().getChat().getFirstName(), update.getMessage());
                        inputStatus = InputStatus.SLAB_PERIMETER;
                    }
                    case "/addData2" -> {
                        addDataSecondCommandReceived(chatId, update.getMessage().getChat().getFirstName(), update.getMessage());
                        inputStatus = InputStatus.SLAB_AREA_FULL;
                    }
                    case "/addData3" -> {
                        addDataThirdCommandReceived(chatId, update.getMessage().getChat().getFirstName(), update.getMessage());
                        inputStatus = InputStatus.SLAB_AREA_BASE;
                    }
                    default -> {
                        sendMessage(chatId, "Sorry, command not recognized", update.getMessage());
                        log.info("Replied to user: {}", update.getMessage().getChat().getFirstName());
                    }
                }
            } else {
                if (!(inputStatus == InputStatus.NONE)) {
                    data.put(inputStatus, Double.parseDouble(messageText));
                    log.info(inputStatus.getTitle() + ": " + data.get(inputStatus));
                }
            }
        } else if (update.hasCallbackQuery()) {
            String callbackData = update.getCallbackQuery().getData();
            long messageId = update.getCallbackQuery().getMessage().getMessageId();
            long chatId = update.getCallbackQuery().getMessage().getChatId();

            if (callbackData.equals(YES_BUTTON)) {
                String text = "You pressed YES button";
                executeEditMessageText(text, chatId, messageId);
            }
            else if (callbackData.equals(NO_BUTTON)) {
                String text = "You pressed NO button";
                executeEditMessageText(text, chatId, messageId);
            }
        }
    }

    private void startCommandReceived(long chatId, String name, Message message) {
        String answer = EmojiParser.parseToUnicode("Hi, " + name + ", nice to meet you!" + " :blush:");
        log.info("Replied to user " + name);


        sendMessage(chatId, answer, message);
        log.info("Replied to user: {}", name);
    }

    private void addDataCommandReceived(long chatId, String name, Message message) {
        String answer = "Enter new data";
        sendMessage(chatId, answer, message);
        log.info("Replied to user: {}", name);
    }

    private void addDataSecondCommandReceived(long chatId, String name, Message message) {
        String answer = "Enter new data2";
        sendMessage(chatId, answer, message);
        log.info("Replied to user: {}", name);
    }

    private void addDataThirdCommandReceived(long chatId, String name, Message message) {
        String answer = "Enter new data3";
        sendMessage(chatId, answer, message);
        log.info("Replied to user: {}", name);
    }

    private void sendMessage(long chatId, String textToSend, Message message) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(chatId));
        sendMessage.setText(textToSend);
        executeMessage(sendMessage);
    }

    private void executeEditMessageText(String text, long chatId, long messageId){
        EditMessageText message = new EditMessageText();
        message.setChatId(String.valueOf(chatId));
        message.setText(text);
        message.setMessageId((int) messageId);

        try {
            execute(message);
        } catch (TelegramApiException e) {
            log.error(ERROR_TEXT + e.getMessage());
        }
    }

    private void executeMessage(SendMessage message){
        try {
            execute(message);
        } catch (TelegramApiException e) {
            log.error(ERROR_TEXT + e.getMessage());
        }
    }
}
