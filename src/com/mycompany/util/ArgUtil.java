/**
 * 
 */
package com.mycompany.util;

/**
 * @author jabaraster
 */
public final class ArgUtil {

    private ArgUtil() {
        //
    }

    /**
     * @param pValue
     * @param pArgName
     */
    public static void checkNull(final Object pValue, final String pArgName) {
        if (pValue == null) {
            throw new IllegalArgumentException("引数 " + pArgName + " にnullを渡すことは出来ません.");
        }
    }
}
