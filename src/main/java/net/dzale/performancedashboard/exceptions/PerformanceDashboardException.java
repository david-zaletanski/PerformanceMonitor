package net.dzale.performancedashboard.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A generic exception for the Diezel application, used as a catch-all for more specific errors.
 *
 * @author dzale
 */
public class PerformanceDashboardException extends Exception {
    private static final Logger log = LoggerFactory.getLogger(PerformanceDashboardException.class);

    public PerformanceDashboardException(String message) {
        super(message);
    }

    public PerformanceDashboardException(Throwable ex) {
        super(ex);
    }

    public PerformanceDashboardException(String message, Throwable ex) {
        super(message, ex);
    }

}
