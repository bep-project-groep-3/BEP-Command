package org.nl.hu.sie.bep.command.input;

import org.nl.hu.sie.bep.command.commands.CommandHandler;
import org.nl.hu.sie.bep.command.commands.ConvertMonthDataToIEF;
import org.nl.hu.sie.bep.command.generic.Command;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class InputHandler {

    private static Logger logger = LoggerFactory.getLogger(InputHandler.class);
    private BufferedReader commandLineReader;
    private CommandHandler commandHandler;

    protected boolean closingProgram = false;

    public InputHandler() {
        commandLineReader = new BufferedReader(new InputStreamReader(System.in));
        commandHandler = new CommandHandler(commandLineReader);

        askForCommandOption();
    }

    private void askForCommandOption(){
        try {
            while(true) {
                if(closingProgram){
                    break;
                }

                String userOptionsString = "Please choose an option\n" +
                        "1 - Convert Month Data To IEF Files\n" +
                        "Any other number - Exit Program";

                logger.info(userOptionsString);

                int userIntegerInput = parseUserInput(commandLineReader.readLine());

                handleUserInput(userIntegerInput);
            }
        } catch (IOException e) {
            logger.error(e.getLocalizedMessage());
        }
    }

    protected int parseUserInput(String userInput)
    {
        int parsedInteger = 0;

        try {
            parsedInteger = Integer.parseInt(userInput);
        } catch (NumberFormatException e) {
            logger.error(e.getLocalizedMessage());
        }

        return parsedInteger;
    }

    protected void handleUserInput(int userIntegerInput){

        if(userSelectsConvertDataToIEF(userIntegerInput)){
            Command convertMonthDataToIEF = new ConvertMonthDataToIEF();
            commandHandler.addCommand(convertMonthDataToIEF);
            commandHandler.processPendingCommands();
        } else {
            closeProgram();
        }
    }

    protected boolean userSelectsConvertDataToIEF(int userIntegerInput){
        return userIntegerInput == 1;
    }

    protected void closeProgram(){
        try {
            commandLineReader.close();
            closingProgram = true;
        } catch (IOException e) {
            logger.error(e.getLocalizedMessage());
        }
    }
}
