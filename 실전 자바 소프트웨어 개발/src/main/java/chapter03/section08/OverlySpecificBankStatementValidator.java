package chapter03.section08;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Objects;

public class OverlySpecificBankStatementValidator {

    private String description;
    private String date;
    private String amount;

    public OverlySpecificBankStatementValidator(String description, String date, String amount) {
        this.description = Objects.requireNonNull(description);
        this.date = Objects.requireNonNull(date);
        this.amount = Objects.requireNonNull(amount);
    }

    /**
     * 과도하게 세밀함
     *
     * 그래도 이게 좋은거 아닌감..
     */
    public boolean validateTooComplex() throws DescriptionTooLongException, InvalidDateFormat, DateInTheFutureException, InvalidAmountException {
        if(this.description.length() > 100) {
            throw new DescriptionTooLongException();
        }

        final LocalDate parsedDate;

        try {
            parsedDate = LocalDate.parse(this.date);
        } catch (DateTimeParseException e) {
            throw new InvalidDateFormat();
        }

        if(parsedDate.isAfter(LocalDate.now())) throw new DateInTheFutureException();

        try {
            Double.parseDouble(this.amount);
        } catch (NumberFormatException e) {
            throw new InvalidAmountException();
        }

        return true;
    }


    static class DescriptionTooLongException extends Exception {}
    static class InvalidDateFormat extends Exception {}
    static class DateInTheFutureException extends Exception {}
    static class InvalidAmountException extends Exception {}

    /**
     * 과도하게 덤덤함
     */
    public boolean validateTooSimple(){
        if(this.description.length() > 100) {
            throw new IllegalArgumentException("The description is too long");
        }

        final LocalDate parsedDate;

        try {
            parsedDate = LocalDate.parse(this.date);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Invalid format for date", e);
        }

        if(parsedDate.isAfter(LocalDate.now())) throw new IllegalArgumentException("date cannot be in the future");

        try {
            Double.parseDouble(this.amount);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid format for amount", e);
        }

        return true;
    }

    /**
     * 이게 제일 이상적이라는데...
     */
    public Notification validate() {
        Notification notification = new Notification();

        if(this.description.length() > 100) {
            notification.addError("The description is too long");
        }

        final LocalDate parsedDate;

        try {
            parsedDate = LocalDate.parse(this.date);
            if(parsedDate.isAfter(LocalDate.now())) {
                notification.addError("date cannot be in the future");
            }
        } catch (DateTimeParseException e) {
            notification.addError("Invalid format for date");
        }

        final double amount;

        try {
            amount = Double.parseDouble(this.amount);
        }
        catch (NumberFormatException e) {
            notification.addError("Invalid format for amount");
        }

        return notification;
    }
}
