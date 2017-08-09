package net.dzale.diezel.controller;

import net.dzale.diezel.exceptions.DiezelException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * A generic exception handling controller.
 * @author dzale
 */
@Controller
public class DiezelErrorController {
    private static final Logger log = LoggerFactory.getLogger(DiezelErrorController.class);

    @ExceptionHandler( {Exception.class } )
    private void handleException(DiezelException ex) {
        log.error("Caught generic Exception.", ex);
    }

    @ExceptionHandler( {DiezelException.class })
    private void handleDiezelException(DiezelException ex) {
        log.error("Caught a generic DiezelException: ", ex);
        // TODO: Ensure real errors / exceptions are persisted.
    }

}
