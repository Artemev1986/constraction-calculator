package com.mikarte.constructioncalculator.service;

import com.mikarte.constructioncalculator.config.BotConfig;
import com.mikarte.constructioncalculator.mapper.*;
import com.mikarte.constructioncalculator.model.*;
import com.mikarte.constructioncalculator.repository.*;
import com.vdurmont.emoji.EmojiParser;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.GetFile;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Document;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@Service
public class TelegramBot extends TelegramLongPollingBot {

    private final BotConfig botConfig;
    private final UserRepository userRepository;
    private final MainPriceRepository mainPriceRepository;
    private final CommunicationPriceRepository communicationPriceRepository;
    private final ConcretePriceRepository concretePriceRepository;
    private final AdditionalPriceRepository additionalPriceRepository;
    private final QuestionnaireRepository questionnaireRepository;
    private final FullPriceRepository fullPriceRepository;
    private final Map<Long, Map<InputStatus, Float>> data = new HashMap<>();
    private final Map<Long, InputStatus> inputStatusMap = new HashMap<>();
    private final Map<KeyMainWorking, Float> mainWorkMap = new HashMap<>();
    private final Map<KeyCommunicationWorking, Float> communicationWorkMap = new HashMap<>();
    private final Map<KeyConcreteWorking, Float> concreteWorkMap = new HashMap<>();
    private final Map<KeyAdditionalWorkAndEquipment, Float> additionalWorkMap = new HashMap<>();
    static final String ERROR_TEXT = "Error occurred: ";
    private final StringBuilder savedMessage = new StringBuilder();

    public TelegramBot(BotConfig botConfig,
                       UserRepository userRepository,
                       MainPriceRepository mainPriceRepository,
                       CommunicationPriceRepository communicationPriceRepository,
                       ConcretePriceRepository concretePriceRepository,
                       AdditionalPriceRepository additionalPriceRepository,
                       QuestionnaireRepository questionnaireRepository,
                       FullPriceRepository fullPriceRepository) {
        this.botConfig = botConfig;
        this.userRepository = userRepository;
        this.mainPriceRepository = mainPriceRepository;
        this.communicationPriceRepository = communicationPriceRepository;
        this.concretePriceRepository = concretePriceRepository;
        this.additionalPriceRepository = additionalPriceRepository;
        this.questionnaireRepository = questionnaireRepository;
        this.fullPriceRepository = fullPriceRepository;
        List<BotCommand> listOfCommands = Arrays.asList(
                new BotCommand("/start", "получить приветствие"),
                new BotCommand("/inputdata", "ввод данных"),
                new BotCommand("/getworkingprice", "рассчёт стоимости работ"));
        try {
            this.execute(new SetMyCommands(listOfCommands, new BotCommandScopeDefault(), null));
        } catch (TelegramApiException e) {
            log.error("Error setting bots command list: " + e.getMessage());
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

            Message newMessage = update.getMessage();
            long chatId = newMessage.getChatId();

            if (messageText.contains("/")) {
                if(messageText.contains("/send") && botConfig.getOwnerId() == chatId) {
                    var users = userRepository.findAll();
                    for (User user: users){
                        var textToSend = EmojiParser.parseToUnicode("Hi, " +
                                user.getFirstName() + ", take a message!" + " :blush:");
                        prepareAndSendMessage(user.getChatId(), textToSend);
                    }
                } else {
                    switch (messageText) {
                        case "/start" -> startCommandReceived(chatId,
                                newMessage.getChat().getFirstName(), newMessage);
                        case "/inputdata" ->
                                addDataCommandReceived(chatId, newMessage.getChat().getFirstName());
                        case "/getworkingprice" -> calculateSumWorking(chatId);
                        default -> {
                            sendMessage(chatId, "Sorry, command not recognized");
                            log.info("Replied to user: {}", newMessage.getChat().getFirstName());
                        }
                    }
                }
            } else {
                if (!inputStatusMap.isEmpty()) {
                    if (!(inputStatusMap.get(chatId) == InputStatus.NONE)) {
                        executeEditMessageText(savedMessage.toString(), chatId,
                                newMessage.getMessageId() - 1);
                        try {
                            data.get(chatId).put(inputStatusMap.get(chatId),
                                    Float.parseFloat(messageText.replace(",", ".")));
                        } catch (NumberFormatException e) {
                            sendMessage(chatId, "Введено некорректное значение, повторите ввод");
                            return;
                        }
                        log.info(inputStatusMap.get(chatId).getTitle() + ": " +
                                data.get(chatId).get(inputStatusMap.get(chatId)) + " chatId: " + chatId);
                        checkData(chatId);
                    }
                } else {
                    sendMessage(chatId, "Sorry, command not recognized");
                    log.info("Replied to user: {}", newMessage.getChat().getFirstName());
                }
            }
        } else if (update.hasCallbackQuery()) {
            String callbackData = update.getCallbackQuery().getData();
            long messageId = update.getCallbackQuery().getMessage().getMessageId();
            long chatId = update.getCallbackQuery().getMessage().getChatId();

            switch (Button.valueOf(callbackData)) {
                case NEXT -> {
                    executeEditMessageText(savedMessage.toString(), chatId, messageId);
                    addDataCommandReceived(chatId, update.getCallbackQuery().getMessage().getChat().getFirstName());
                }
                case EXIT -> {
                    executeEditMessageText(savedMessage.toString(), chatId, messageId);
                    exitCommandReceived(chatId, update.getCallbackQuery().getMessage().getChat().getFirstName());
                }
            }
        } else if (update.getMessage().hasDocument()) {
            long chatId = update.getMessage().getChatId();
            if (botConfig.getOwnerId() == chatId) {
                createUnitPrice(update.getMessage());
            }
        }
    }

    private void registerUser(Message message) {

        if (userRepository.findById(message.getChatId()).isEmpty()) {

            var chatId = message.getChatId();
            var chat = message.getChat();

            User user = new User();

            user.setChatId(chatId);
            user.setFirstName(chat.getFirstName());
            user.setLastName(chat.getLastName());
            user.setUserName(chat.getUserName());
            user.setRegisteredOn(LocalDateTime.now());

            userRepository.save(user);
            log.info("user saved: " + user);
        }
    }

    public void createUnitPrice(Message message) {
        String docId = message.getDocument().getFileId();
        String docName = message.getDocument().getFileName();
        String docMine = message.getDocument().getMimeType();
        long docSize = message.getDocument().getFileSize();

        Document document = new Document();
        document.setMimeType(docMine);
        document.setFileName(docName);
        document.setFileSize(docSize);
        document.setFileId(docId);

        GetFile getFile = new GetFile();
        getFile.setFileId(document.getFileId());
        String filePath;
        File file = null;
        try {
            filePath = execute(getFile).getFilePath();
            file = downloadFile(filePath);
        } catch (TelegramApiException e) {
            log.error(ERROR_TEXT + e.getMessage());
        }

        try {
            assert file != null;
            FileInputStream inputStream = new FileInputStream(file);
            Workbook workbook = new XSSFWorkbook(inputStream);
            Sheet sheet = workbook.getSheet("Лист1");
            int firstRow = sheet.getFirstRowNum() + 1;
            int cellNum = sheet.getRow(firstRow).getFirstCellNum();
            int i = 0;
            for (KeyMainWorking key: KeyMainWorking.values()) {
                float value = (float) sheet.getRow(firstRow + i).getCell(cellNum + 2).getNumericCellValue();
                mainWorkMap.put(key, value);
                i++;
            }

            for (KeyCommunicationWorking key: KeyCommunicationWorking.values()) {
                float value = (float) sheet.getRow(firstRow + i).getCell(cellNum + 2).getNumericCellValue();
                communicationWorkMap.put(key, value);
                i++;
            }

            for (KeyConcreteWorking key: KeyConcreteWorking.values()) {
                float value = (float) sheet.getRow(firstRow + i).getCell(cellNum + 2).getNumericCellValue();
                concreteWorkMap.put(key, value);
                i++;
            }

            for (KeyAdditionalWorkAndEquipment key: KeyAdditionalWorkAndEquipment.values()) {
                float value = (float) sheet.getRow(firstRow + i).getCell(cellNum + 2).getNumericCellValue();
                additionalWorkMap.put(key, value);
                i++;
            }
            inputStream.close();


            MainPrice mainPrice = MainPriceMapper.toMainPriceFromMap(mainWorkMap);
            CommunicationPrice communicationPrice = CommunicationPriceMapper.toCommunicationPriceFromMap(communicationWorkMap);
            ConcretePrice concretePrice = ConcretePriceMapper.toConcretePriceFromMap(concreteWorkMap);
            AdditionalPrice additionalPrice = AdditionalPriceMapper.toAdditionalPriceFromMap(additionalWorkMap);

            mainPriceRepository.save(mainPrice);
            communicationPriceRepository.save(communicationPrice);
            concretePriceRepository.save(concretePrice);
            additionalPriceRepository.save(additionalPrice);

            FullPrice fullPrice = FullPriceMapper.toFullPriceFromMap(
                    mainWorkMap,
                    communicationWorkMap,
                    concreteWorkMap,
                    additionalWorkMap);
            fullPriceRepository.save(fullPrice);
        } catch (IOException e) {
            log.error(ERROR_TEXT + e.getMessage());
        }
    }

    private void calculateSumWorking(long chatId) {
        FullPrice fullPrice;
        Questionnaire questionnaire;
        try {
            fullPrice = fullPriceRepository.getById(1L);
        } catch (JpaObjectRetrievalFailureException e) {
            sendMessage(chatId, "отсутствуют данные о ценах за единицу");
            log.error(ERROR_TEXT + e.getMessage());
            return;
        }

        try {
            questionnaire = questionnaireRepository.findFirstByChatIdOrderByIdDesc(chatId);
        } catch (JpaObjectRetrievalFailureException e) {
            sendMessage(chatId, "нет вводных данных");
            log.error(ERROR_TEXT + e.getMessage());
            return;
        }
            float sum = Calculator.totalSumWorking(
                    fullPrice.getMainPrice(),
                    fullPrice.getCommunicationPrice(),
                    fullPrice.getConcretePrice(),
                    fullPrice.getAdditionalPrice(),
                    questionnaire);

        Locale locale = new Locale("ru");
        NumberFormat format = NumberFormat.getInstance(locale);
        String answer = " Общая стоимость работ = " + format.format(sum) + " руб.";
        sendMessage(chatId, answer);
    }

    private void checkData(long chatId) {

        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        String answer = inputStatusMap.get(chatId).getTitle() + " = " +
                data.get(chatId).get(inputStatusMap.get(chatId)) +
                " если параметр введён верно, то перейдите к следующему, " +
                "если нет, то скорректируйте его";
        savedMessage.delete(0, savedMessage.length());
        savedMessage.append(answer);
        underTextButton(message, answer);
    }

    private void underTextButton(SendMessage message, String answer) {
        message.setText(answer);

        InlineKeyboardMarkup markupInLine = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInLine = new ArrayList<>();
        List<InlineKeyboardButton> rowInLine = new ArrayList<>();
        var nextButton = new InlineKeyboardButton();

        nextButton.setText(Button.NEXT.getTitle());
        nextButton.setCallbackData(Button.NEXT.toString());

        var exitButton = new InlineKeyboardButton();

        exitButton.setText(Button.EXIT.getTitle());
        exitButton.setCallbackData(Button.EXIT.toString());

        rowInLine.add(nextButton);
        rowInLine.add(exitButton);

        rowsInLine.add(rowInLine);

        markupInLine.setKeyboard(rowsInLine);
        message.setReplyMarkup(markupInLine);

        executeMessage(message);
    }

    private void startCommandReceived(long chatId, String name, Message message) {
        String answer = EmojiParser.parseToUnicode("Hi, " + name + ", nice to meet you!" + " :blush:");

        log.info("Replied to user " + name);

        registerUser(message);
        sendMessage(chatId, answer);
        log.info("Replied to user: {}", name);
    }

    private void addDataCommandReceived(long chatId, String name) {
        data.computeIfAbsent(chatId, k -> new HashMap<>());
        inputStatusMap.putIfAbsent(chatId, InputStatus.NONE);

        if (inputStatusMap.get(chatId) == InputStatus.SAND_BED_THICKNESS) {
            InputStatus validStatus = validateData(chatId, data);
            if (validStatus == InputStatus.NONE) {
                Questionnaire questionnaire =
                        QuestionnaireMapper.toQuestionnaireFromMap(data.get(chatId), chatId);
                questionnaireRepository.save(questionnaire);
                exitCommandReceived(chatId, name);
                return;
            } else {
                inputStatusMap.put(chatId, validStatus);
            }
        } else {
            switch (inputStatusMap.get(chatId)) {
                case NONE -> inputStatusMap.put(chatId, InputStatus.SLAB_PERIMETER);
                case SLAB_PERIMETER -> inputStatusMap.put(chatId, InputStatus.SLAB_AREA_FULL);
                case SLAB_AREA_FULL -> inputStatusMap.put(chatId, InputStatus.SLAB_AREA_BASE);
                case SLAB_AREA_BASE -> inputStatusMap.put(chatId, InputStatus.SLAB_AREA_HEATED);
                case SLAB_AREA_HEATED -> inputStatusMap.put(chatId, InputStatus.NUMBER_OF_AXES);
                case NUMBER_OF_AXES -> inputStatusMap.put(chatId, InputStatus.ROUTE_LENGTH);
                case ROUTE_LENGTH -> inputStatusMap.put(chatId, InputStatus.SEWER_RISERS);
                case SEWER_RISERS -> inputStatusMap.put(chatId, InputStatus.DRAW_OFF_POINTS);
                case DRAW_OFF_POINTS -> inputStatusMap.put(chatId, InputStatus.CABLE_ENTRY);
                case CABLE_ENTRY -> inputStatusMap.put(chatId, InputStatus.GROUND);
                case GROUND -> inputStatusMap.put(chatId, InputStatus.LENGTH_INSIDE_WALL);
                case LENGTH_INSIDE_WALL -> inputStatusMap.put(chatId, InputStatus.PLATE_RIB_WIDTH);
                case PLATE_RIB_WIDTH -> inputStatusMap.put(chatId, InputStatus.PIT_DEPTH);
                case PIT_DEPTH -> inputStatusMap.put(chatId, InputStatus.INSULATION_THICKNESS);
                case INSULATION_THICKNESS -> inputStatusMap.put(chatId, InputStatus.SAND_BED_THICKNESS);
            }
        }

        Float tempValue = data.get(chatId).get(inputStatusMap.get(chatId));
        String tempStringValue = tempValue == null ? " не установлено" : " = " + tempValue;
        String answer = "Введите значение для параметра \"" + inputStatusMap.get(chatId).getTitle() + "\". " +
                "Текущее значение" + tempStringValue;

        savedMessage.delete(0, savedMessage.length());
        savedMessage.append(answer);
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        underTextButton(message, answer);
        log.info("Replied to user: {}", name);
    }

    private void exitCommandReceived(long chatId, String name) {
        inputStatusMap.put(chatId, InputStatus.NONE);

        String answer = "Ввод данных завершён";
        sendMessage(chatId, answer);
        log.info("Data entry from {} completed", name);
    }

    private void sendMessage(long chatId, String textToSend) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText(textToSend);
        executeMessage(sendMessage);
    }

    private void executeMessage(SendMessage message){
        try {
            execute(message);
        } catch (TelegramApiException e) {
            log.error(ERROR_TEXT + e.getMessage());
        }
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

    private void prepareAndSendMessage(long chatId, String textToSend){
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(textToSend);
        executeMessage(message);
    }

    private InputStatus validateData(long chatId, Map<Long, Map<InputStatus, Float>> data) {
        for (InputStatus status: InputStatus.values()) {
            if (status != InputStatus.NONE) {
                if (data.get(chatId).get(status) == null) {
                    String textMessage = "Значение параметра \"" + status.getTitle() +
                            "\" не должно быть пустым" + ":warning:";
                    sendMessage(chatId, EmojiParser.parseToUnicode(textMessage));
                    return status;
                }
                if (data.get(chatId).get(status) <= 0) {
                    String textMessage = "Значение параметра \"" + status.getTitle() +
                            "\" должно быть больше нуля" + ":warning:";
                    sendMessage(chatId, EmojiParser.parseToUnicode(textMessage));
                    return status;
                }
            }
        }
        if (data.get(chatId).get(InputStatus.SLAB_AREA_FULL)  < data.get(chatId).get(InputStatus.SLAB_AREA_BASE)) {
            String textMessage = "Значение параметра \"" + InputStatus.SLAB_AREA_FULL.getTitle() +
                    "\" должно быть не меньше параметра \"" + InputStatus.SLAB_AREA_BASE.getTitle() + "\"" + ":warning:";
            sendMessage(chatId, EmojiParser.parseToUnicode(textMessage));
            return InputStatus.SLAB_AREA_FULL;
        }
        if (data.get(chatId).get(InputStatus.SLAB_AREA_BASE) < data.get(chatId).get(InputStatus.SLAB_AREA_HEATED)) {
            String textMessage = "Значение параметра \"" + InputStatus.SLAB_AREA_BASE.getTitle() +
                    "\" должно быть не меньше параметра \"" + InputStatus.SLAB_AREA_HEATED.getTitle() + "\"" + ":warning:";
            sendMessage(chatId, EmojiParser.parseToUnicode(textMessage));
            return InputStatus.SLAB_AREA_BASE;
        }
        return InputStatus.NONE;
    }
}
