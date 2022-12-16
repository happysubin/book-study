package chapter_11;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;
import java.util.Locale;
import java.util.TimeZone;

public class Application {

    public static void main(String[] args) {
        
        LocalDate date = LocalDate.of(2022, 11, 13);
        System.out.println("date.getYear() = " + date.getYear());
        System.out.println("date.getMonth() = " + date.getMonth());
        System.out.println("date.getMonth() = " + date.getDayOfMonth());
        System.out.println("date.getDayOfWeek() = " + date.getDayOfWeek());
        System.out.println("date.lengthOfMonth() = " + date.lengthOfMonth());
        System.out.println("date.isLeapYear() = " + date.isLeapYear());

        LocalDate now = LocalDate.now();
        System.out.println("now.getMonthValue() = " + now.getMonthValue());
        System.out.println("now.getDayOfMonth() = " + now.getDayOfMonth());


        LocalTime time = LocalTime.of(13, 45, 20);
        System.out.println("time.getHour() = " + time.getHour());
        System.out.println("time.getMinute() = " + time.getMinute());
        System.out.println("time.getSecond() = " + time.getSecond());

        LocalDate parse = LocalDate.parse("2021-09-21");
        System.out.println("parse.getYear() = " + parse.getYear());

        LocalTime parse1 = LocalTime.parse("13:45:20");
        System.out.println("parse1.getMinute() = " + parse1.getMinute());

        LocalDateTime localDateTime = LocalDateTime.of(2022, 12, 16, 10, 11, 15);
        System.out.println("localDateTime = " + localDateTime.toLocalDate());
        System.out.println("localDateTime = " + localDateTime.toLocalTime());
        LocalDateTime localDateTime1 = now.atTime(time);
        System.out.println("localDateTime1 = " + localDateTime1);


        Instant instant = Instant.ofEpochSecond(3);
        Instant instant2 = Instant.ofEpochSecond(3, 0);
        System.out.println("instant = " + instant);
        System.out.println("instant2 = " + instant2);

        Duration between = Duration.between(localDateTime, localDateTime1);
        System.out.println("between = " + between);
        Duration between1 = Duration.between(instant, instant2);
        System.out.println("between1 = " + between1);
        Duration between2 = Duration.between(time, parse1);
        System.out.println("between2 = " + between2);

        Period between3 = Period.between(date, parse);
        System.out.println("between3 = " + between3);

        Duration duration = Duration.ofMinutes(3);
        System.out.println("duration = " + duration);

        Period period = Period.ofDays(10);
        System.out.println("period = " + period);

        LocalDate of = LocalDate.of(2018, 9, 21);
        LocalDate localDate = of.withYear(2022);
        LocalDate localDate1 = of.plusDays(10);
        System.out.println("localDate = " + localDate);
        System.out.println("localDate1 = " + localDate1);

        /**
         * TemporalAdjuster
         */

        LocalDate date1 = LocalDate.of(2014, 3, 18);
        LocalDate with = date1.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY));
        System.out.println("with = " + with);
        LocalDate with1 = date1.with(TemporalAdjusters.lastDayOfMonth());
        System.out.println("with1 = " + with1);

        LocalDate with2 = date.with(temporal -> {
            DayOfWeek dow = DayOfWeek.of(temporal.get(ChronoField.DAY_OF_WEEK));
            int dayToAdd = 1;
            if (dow == DayOfWeek.FRIDAY) dayToAdd = 3;
            else if (dow == DayOfWeek.SATURDAY) dayToAdd = 2;
            return temporal.plus(dayToAdd, ChronoUnit.DAYS);
        });

        System.out.println("with2 = " + with2);


        /**
         * 날짜 포매팅
         */

        LocalDate ex = LocalDate.of(2022, 10, 23);
        String format = ex.format(DateTimeFormatter.BASIC_ISO_DATE);//20221023
        System.out.println("format = " + format);
        String format1 = ex.format(DateTimeFormatter.ISO_LOCAL_DATE);//2022-10-23
        System.out.println("format1 = " + format1);

        LocalDate parse2 = LocalDate.parse("20140318", DateTimeFormatter.BASIC_ISO_DATE);
        System.out.println("parse2 = " + parse2);
        LocalDate parse3 = LocalDate.parse("2014-03-18", DateTimeFormatter.ISO_LOCAL_DATE);
        System.out.println("parse3 = " + parse3);

        /**
         *
         */
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate ex2 = LocalDate.of(2014, 9, 12);
        String format2 = ex2.format(formatter);
        System.out.println("format2 = " + format2);

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("d. MMMM yyyy", Locale.ITALIAN);
        LocalDate of1 = LocalDate.of(2014, 3, 18);
        String format3 = of1.format(dateTimeFormatter);
        System.out.println("format3 = " + format3);
        LocalDate parse4 = LocalDate.parse(format3, dateTimeFormatter);
        System.out.println("parse4 = " + parse4);


        /**
         * ZoneId
         */

        ZoneId zoneId = ZoneId.of("Europe/Rome");
        System.out.println(zoneId);

        ZoneId zoneId1 = TimeZone.getDefault().toZoneId();
        System.out.println("zoneId1 = " + zoneId1);


        LocalDate now1 = LocalDate.now();
        System.out.println("now1 = " + now1);
        ZonedDateTime zonedDateTime = now1.atStartOfDay(zoneId1);
        System.out.println("zonedDateTime = " + zonedDateTime);
        LocalDateTime now2 = LocalDateTime.now();
        ZonedDateTime zonedDateTime1 = now.atStartOfDay(zoneId1);
        System.out.println("zonedDateTime1 = " + zonedDateTime1);
        Instant now3 = Instant.now();
        ZonedDateTime zonedDateTime2 = now3.atZone(zoneId1);
        System.out.println("zonedDateTime2 = " + zonedDateTime2);
    }
}
