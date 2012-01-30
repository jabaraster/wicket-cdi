/**
 * 
 */
package com.mycompany.util;


/**
 * @author jabaraster
 */
public final class ExceptionUtil {

    private ExceptionUtil() {
        //
    }

    /**
     * @param pCause
     * @return
     */
    public static RuntimeException rethrow(final Throwable pCause) {
        if (pCause == null) {
            throw new IllegalArgumentException();
        }
        if (pCause instanceof Error) {
            throw (Error) pCause;
        }
        if (pCause instanceof RuntimeException) {
            throw (RuntimeException) pCause;
        }
        throw new IllegalStateException(pCause);
    }
}
