package org.jpmc.service;

import org.jpmc.custom.message.CustomMessage;
import org.jpmc.enums.PortfolioCodeEnum;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

public class FileNameValidationService {

    private final String TEST = "Test";
    private final String separator = "_";
    private final String validFileExtension = ".csv";
    private final String passed = "has passed validation";
    private final String failed = "has failed validation";

    public String validateFileName(String fileName) {
        int extensionIndex = fileName.lastIndexOf(".");
        String cleanedFileName = fileName.substring(0, extensionIndex);
        List<String> fileNameParts = List.of(cleanedFileName.split(separator));
        return validateFile(fileName, fileNameParts);
    }

    private String validateFile(String fileName, List<String> fileNameParts) {
        boolean format = checkIfFormatIsValid(fileNameParts, fileName);
        if(format) {
            return new CustomMessage(fileName, failed,
                    "File format should be ‘Test_<portfoliocode>_<ddmmyyyy>_<2digit-sequencenumber>.csv’").toString();
        }
        if(fileName.endsWith(validFileExtension)) {
            return validate(fileNameParts,fileName);
        } else {
            return new CustomMessage(fileName, failed,
                    "Invalid File format.Expected ‘csv’ found ‘txt’").toString();
        }
    }

    private Boolean checkIfFormatIsValid(List<String> fileNameParts, String fileName) {
        return fileNameParts.size() != 4;
    }

    private String validate(List<String> fileNameParts, String fileName) {
        for (String name : fileNameParts) {
            switch (fileNameParts.indexOf(name)){
                case 0:
                    if(name.equals(TEST)){
                        break;
                    } else{
                        return new CustomMessage(fileName, failed,
                                "Prefix for the file should be ‘Test’ found " + name).toString();
                    }
                case 1:
                    if(PortfolioCodeEnum.forName(name)){
                        break;
                    } else{
                        return new CustomMessage(fileName, failed,
                                "PortfolioCode should be A/B/C found " + name).toString();
                    }
                case 2:
                    if(validateDateFormat(name)){
                        break;
                    } else{
                        return new CustomMessage(fileName, failed,
                                "Valuation Date is not a valid date format ‘ddmmyyyy’").toString();
                    }
                case 3:
                    if(validateNumberSequence(name)){
                        break;
                    } else{
                        return new CustomMessage(fileName, failed,
                                "Sequence Number not 2 digits").toString();
                    }
            }
        }
        return new CustomMessage(fileName, passed, "").toString();
    }

    private Boolean validateNumberSequence(String name) {
        return name.length() == 2;
    }

    private Boolean validateDateFormat(String name) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyyyy");
        try {
            LocalDate date = LocalDate.parse(name, formatter);
            return true;

        } catch (DateTimeParseException e) {
            return false;
        }

    }
}
