package github.funn.utils.instrumentation;

/**
 * Author : root
 * QQ     : 1562363326
 * Date   : 2017/4/13.
 */

public interface Instrumented {
    /**
     * Forget what happened so far and start measuring once again.
     *
     * @param tag String used to identify the image request
     * @param perfListener listener to be used to track the request state
     */
    void initInstrumentation(final String tag, PerfListener perfListener);
}
