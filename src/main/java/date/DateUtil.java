package date;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalAdjusters;
import java.util.Date;

/**
 * 日期工具类
 * 针对入参是Date,long,string有三套函数
 * 使用jdk8的新时间API
 **/
public class DateUtil {

    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    public static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");


    private static final String TIME_SUBFIX = " 00:00:00";
    
    
    /**
     * 返回格式化的当前日期的0点0分
     *
     * @param pattern
     * @return String
     */
    public static String startOfDayString(LocalDateTime date) {
    	return DATE_FORMATTER.format(date) + TIME_SUBFIX;
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd 00:00:00");
//        return formatter.format(date);
    }
    
    /**
     * 
     * @param 日期yyyy-MM-dd HH:mm:ss的格式
     * @param 当前日期的前几天，天数
     * @return 返回LocalDateTime
     */
    public static LocalDateTime beforeOfDay(String datetime, int days) {
    	LocalDateTime dateTime = LocalDateTime.parse(datetime, TIME_FORMATTER);
        return dateTime.minus(Period.ofDays(days));
	}
    
    /**
     * 
     * @param 日期yyyy-MM-dd HH:mm:ss的格式
     * @param 当前日期的前几天，天数
     * @return 返回String
     */
    public static String beforeOfDayString(String datetime, int days) {
    	LocalDateTime dateTime = LocalDateTime.parse(datetime, TIME_FORMATTER);
        return TIME_FORMATTER.format(dateTime.minus(Period.ofDays(days)));
	}
    
    /**
     * 按传入的格式 返回格式化的当前日期
     *
     * @param pattern
     * @return
     */
    public static String currentDayStr(String pattern) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        return formatter.format(LocalDateTime.now());
    }

    /**
     * 按传入的格式 返回格式化的传入日期
     *
     * @param pattern
     * @return
     */
    public static String currentDayStr(Date date, String pattern) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        return formatter.format(date.toInstant());
    }

    /**
     * 判断当前日期和传入日期是否是同一个月
     *
     * @param data
     * @return
     */
    public static boolean isCurrentMonth(Date data) {
        LocalDate nowDate = LocalDate.now();
        LocalDate otherDate = data.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        return nowDate.getMonth().equals(otherDate.getMonth());
    }

    /**
     * 判断传入日期和当前日期是否是同一天
     *
     * @param data
     * @return
     */
    public static boolean isCurrentDay(Date data) {
        LocalDate nowDate = LocalDate.now();
        LocalDate otherDate = data.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        return nowDate.getDayOfYear() == otherDate.getDayOfYear();
    }

    /**
     * 返回传入日期的下一天日期，时分秒和当前日期相同
     *
     * @param currentDate 当前日期
     * @return
     */
    public static LocalDateTime nextDay(Date currentDate) {
        Instant instant = currentDate.toInstant().plus(Period.ofDays(1));
        return LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
    }

    /**
     * 返回传入日期的上一天日期，时分秒和当前日期相同
     *
     * @param currentDate 当前日期
     * @return
     */
    public static LocalDateTime previousDay(Date currentDate) {
        Instant instant = currentDate.toInstant().minus(Period.ofDays(1));
        return LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
    }

    /**
     * 返回传入日期当月的第一天日期，时分秒和当前日期相同
     *
     * @param currentDate
     * @return
     */
    public static LocalDateTime firstDayOfMonth(Date currentDate) {
        return currentDate.toInstant()
                .atZone(ZoneId.systemDefault()).toLocalDateTime()
                .with(TemporalAdjusters.firstDayOfMonth());
    }

    /**
     * 返回传入日期当月的最后一天日期，时分秒和当前日期相同
     *
     * @param currentDate
     * @return
     */
    public static LocalDateTime lastDayOfMonth(Date currentDate) {
        return currentDate.toInstant()
                .atZone(ZoneId.systemDefault()).toLocalDateTime()
                .with(TemporalAdjusters.lastDayOfMonth());
    }

    /**
     * 返回传入日期的当天开始 时分秒都是0
     *
     * @param now
     * @return
     */
    public static LocalDateTime startOfDay(Date now) {
        return now.toInstant()
                .atZone(ZoneId.systemDefault()).toLocalDateTime()
                .with(ChronoField.MILLI_OF_DAY, 0);
    }

    /**
     * 返回传入日期的当天结束 也是第二天的开始时间
     *
     * @param now
     * @return
     */
    public static LocalDateTime endOfDay(Date now) {
        return now.toInstant()
                .atZone(ZoneId.systemDefault()).toLocalDateTime()
                .with(ChronoField.MILLI_OF_DAY, 0)
                .plus(1, ChronoUnit.DAYS);
    }


    /**
     * 判断当前日期和传入日期是否是同一个月
     *
     * @param data
     * @return
     */
    public static boolean isCurrentMonth(String data) {
        LocalDate nowDate = LocalDate.now();
        TemporalAccessor otherDate = DATE_FORMATTER.parse(data);
        if (!otherDate.isSupported(ChronoField.MONTH_OF_YEAR)) {
            return false;
        }
        return nowDate.getMonth().getValue() == (otherDate.get(ChronoField.MONTH_OF_YEAR));

    }

    /**
     * 判断传入日期和当前日期是否是同一天
     *
     * @param data
     * @return
     */
    public static boolean isCurrentDay(String data) {
        LocalDate nowDate = LocalDate.now();
        TemporalAccessor otherDate = DATE_FORMATTER.parse(data);
        if (!otherDate.isSupported(ChronoField.DAY_OF_YEAR)) {
            return false;
        }
        return nowDate.getDayOfYear() == (otherDate.get(ChronoField.DAY_OF_YEAR));

    }

    /**
     * 返回传入日期的下一天日期，时分秒和当前日期相同
     *
     * @param date         日期格式 yyyy-MM-dd 或者 yyyy-MM-dd HH:mm:ss
     * @param needTimePart 如果传入格式是 yyyy-MM-dd,则传入true，格式是yyyy-MM-dd HH:mm:ss 传入false
     * @return
     */
    public static LocalDateTime nextDay(String date, boolean needTimePart) {
        if (needTimePart) {
            date = date.concat(TIME_SUBFIX);
        }
        LocalDateTime dateTime = LocalDateTime.parse(date, TIME_FORMATTER);
        return dateTime.plus(Period.ofDays(1));
    }

    /**
     * 返回传入日期的上一天日期，时分秒和当前日期相同
     *
     * @param date         日期格式 yyyy-MM-dd 或者 yyyy-MM-dd HH:mm:ss
     * @param needTimePart 如果传入格式是 yyyy-MM-dd,则传入true，格式是yyyy-MM-dd HH:mm:ss 传入false
     * @return
     */
    public static LocalDateTime previousDay(String date, boolean needTimePart) {
        if (needTimePart) {
            date = date.concat(TIME_SUBFIX);
        }
        LocalDateTime dateTime = LocalDateTime.parse(date, TIME_FORMATTER);
        return dateTime.minus(Period.ofDays(1));
    }

    /**
     * 返回传入日期当月的第一天日期，时分秒和当前日期相同
     *
     * @param date         日期格式 yyyy-MM-dd 或者 yyyy-MM-dd HH:mm:ss
     * @param needTimePart 如果传入格式是 yyyy-MM-dd,则传入true，格式是yyyy-MM-dd HH:mm:ss 传入false
     * @return
     */
    public static LocalDateTime firstDayOfMonth(String date, boolean needTimePart) {
        if (needTimePart) {
            date = date.concat(TIME_SUBFIX);
        }
        LocalDateTime dateTime = LocalDateTime.parse(date, TIME_FORMATTER);
        return dateTime.with(TemporalAdjusters.firstDayOfMonth());
    }

    /**
     * 返回传入日期当月的最后一天日期，时分秒和当前日期相同
     *
     * @param date         日期格式 yyyy-MM-dd 或者 yyyy-MM-dd HH:mm:ss
     * @param needTimePart 如果传入格式是 yyyy-MM-dd,则传入true，格式是yyyy-MM-dd HH:mm:ss 传入false
     * @return
     */
    public static LocalDateTime lastDayOfMonth(String date, boolean needTimePart) {
        if (needTimePart) {
            date = date.concat(TIME_SUBFIX);
        }
        LocalDateTime dateTime = LocalDateTime.parse(date, TIME_FORMATTER);
        return dateTime.with(TemporalAdjusters.lastDayOfMonth());
    }

    /**
     * 返回传入日期的当天开始 时分秒都是0
     *
     * @param date         日期格式 yyyy-MM-dd 或者 yyyy-MM-dd HH:mm:ss
     * @param needTimePart 如果传入格式是 yyyy-MM-dd,则传入true，格式是yyyy-MM-dd HH:mm:ss 传入false
     * @return
     */
    public static LocalDateTime startOfDay(String date, boolean needTimePart) {
        if (needTimePart) {
            date = date.concat(TIME_SUBFIX);
        }
        LocalDateTime dateTime = LocalDateTime.parse(date, TIME_FORMATTER);
        return dateTime.with(ChronoField.MILLI_OF_DAY, 0);
    }

    /**
     * 返回传入日期的当天结束 也是第二天的开始时间
     *
     * @param date         日期格式 yyyy-MM-dd 或者 yyyy-MM-dd HH:mm:ss
     * @param needTimePart 如果传入格式是 yyyy-MM-dd,则传入true，格式是yyyy-MM-dd HH:mm:ss 传入false
     * @return
     */
    public static LocalDateTime endOfDay(String date, boolean needTimePart) {
        if (needTimePart) {
            date = date.concat(TIME_SUBFIX);
        }
        LocalDateTime dateTime = LocalDateTime.parse(date, TIME_FORMATTER);
        return dateTime.with(ChronoField.MILLI_OF_DAY, 0)
                .plus(1, ChronoUnit.DAYS);
    }

    /**
     * 按传入的格式 返回格式化的传入日期
     *
     * @param pattern
     * @return
     */
    public static String currentDayStr(long timeStamp, String pattern) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        return formatter.format(new Date(timeStamp).toInstant());
    }

    /**
     * 判断当前日期和传入日期是否是同一个月
     *
     * @param timeStamp
     * @return
     */
    public static boolean isCurrentMonth(long timeStamp) {
        return isCurrentMonth(new Date(timeStamp));
    }

    /**
     * 判断传入日期和当前日期是否是同一天
     *
     * @param timeStamp
     * @return
     */
    public static boolean isCurrentDay(long timeStamp) {
        return isCurrentDay(new Date(timeStamp));
    }

    /**
     * 返回传入日期的下一天日期，时分秒和当前日期相同
     *
     * @param timeStamp 毫秒的时间戳
     * @return
     */
    public static LocalDateTime nextDay(long timeStamp) {
        return nextDay(new Date(timeStamp));
    }

    /**
     * 返回传入日期的上一天日期，时分秒和当前日期相同
     *
     * @param timeStamp 毫秒的时间戳
     * @return
     */
    public static LocalDateTime previousDay(long timeStamp) {
        return previousDay(new Date(timeStamp));
    }

    /**
     * 返回传入日期当月的第一天日期，时分秒和当前日期相同
     *
     * @param timeStamp 毫秒的时间戳
     * @return
     */
    public static LocalDateTime firstDayOfMonth(long timeStamp) {
        return firstDayOfMonth(new Date(timeStamp));
    }

    /**
     * 返回传入日期当月的最后一天日期，时分秒和当前日期相同
     *
     * @param timeStamp 毫秒的时间戳
     * @return
     */
    public static LocalDateTime lastDayOfMonth(long timeStamp) {
        return lastDayOfMonth(new Date(timeStamp));
    }

    /**
     * 返回传入日期的当天开始 时分秒都是0
     *
     * @param nowTimeStamp 毫秒的时间戳
     * @return
     */
    public static LocalDateTime startOfDay(long nowTimeStamp) {
        return startOfDay(new Date(nowTimeStamp));
    }

    /**
     * 返回传入日期的当天结束 也是第二天的开始时间
     *
     * @param nowTimeStamp 毫秒的时间戳
     * @return
     */
    public static LocalDateTime endOfDay(long nowTimeStamp) {
        return endOfDay(new Date(nowTimeStamp));
    }


    public static void main(String[] args) {
//        System.out.println(TIME_FORMATTER.format(DateUtil.nextDay(new Date())));
//        System.out.println(DateUtil.firstDayOfMonth(new Date()));
//        System.out.println(DateUtil.lastDayOfMonth(new Date()));
//        System.out.println(DateUtil.startOfDay(new Date()));
//        System.out.println(DateUtil.endOfDay(new Date()));
//
//        System.out.println(DATE_FORMATTER.format(DateUtil.endOfDay("2018-12-12 12:12:12", false)));
//        System.out.println(TIME_FORMATTER.format(DateUtil.endOfDay(System.currentTimeMillis())));

    }

}
