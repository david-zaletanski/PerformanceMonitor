package net.dzale.performancedashboard.controller;

import net.dzale.performancedashboard.exceptions.PerformanceDashboardException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * A generic exception handling controller.
 * @author dzale
 */
@Controller
public class ErrorController {
    private static final Logger log = LoggerFactory.getLogger(ErrorController.class);

    @ExceptionHandler( {Exception.class } )
    private void handleException(PerformanceDashboardException ex) {
        log.error("Caught generic Exception.", ex);
    }

    @ExceptionHandler({PerformanceDashboardException.class})
    private void handleDiezelException(PerformanceDashboardException ex) {
        log.error("Caught a generic PerformanceDashboardException: ", ex);
        // TODO: Ensure real errors / exceptions are persisted.
    }

}
