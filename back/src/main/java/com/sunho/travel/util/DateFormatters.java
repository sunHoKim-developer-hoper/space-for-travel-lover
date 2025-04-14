package com.sunho.travel.util;
import java.time.format.DateTimeFormatter;

/**
 * 자주 사용하는 날짜 포맷터 모음
 */
public final class DateFormatters {

    private DateFormatters() {
        // 유틸리티 클래스는 인스턴스화 방지
    }

    // LocalDate용
    public static final DateTimeFormatter YYYY_MM_DD = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    public static final DateTimeFormatter YYYYMMDD = DateTimeFormatter.ofPattern("yyyyMMdd");
    public static final DateTimeFormatter KOR_DATE = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일");

    // LocalDateTime용
    public static final DateTimeFormatter YYYY_MM_DD_HH_MM_SS = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    public static final DateTimeFormatter YYYYMMDD_HHMMSS = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
    public static final DateTimeFormatter KOR_DATETIME = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 HH시 mm분 ss초");

    // 필요에 따라 확장 가능
}
