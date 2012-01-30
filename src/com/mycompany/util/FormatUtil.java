/**
 * 
 */
package com.mycompany.util;

import java.util.Date;

import org.apache.commons.lang.time.FastDateFormat;

/**
 * @author jabaraster
 */
public final class FormatUtil {

    private static final FastDateFormat FORMATTER_DATE     = FastDateFormat.getInstance("yyyy/MM/dd");
    private static final FastDateFormat FORMATTER_DATETIME = FastDateFormat.getInstance("yyyy/MM/dd HH:mm:ss");

    private FormatUtil() {
        //
    }

    /**
     * @param pDate
     * @return
     */
    public static String formatDate(final Date pDate) {
        if (pDate == null) {
            return null;
        }
        return FORMATTER_DATE.format(pDate);
    }

    /**
     * @param pDate
     * @return
     */
    public static String formatDatetime(final Date pDate) {
        if (pDate == null) {
            return null;
        }
        return FORMATTER_DATETIME.format(pDate);
    }
}
