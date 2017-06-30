package net.dzale.treeseeder.controller;

import net.dzale.treeseeder.Application;
import net.dzale.treeseeder.exceptions.DiezelComponentException;
import net.dzale.treeseeder.exceptions.DiezelException;
import org.mockito.internal.exceptions.ExceptionIncludingMockitoWarnings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * A generic exception handling controller.
 * @author dzale
 */
@Controller
public class DiezelErrorController extends DiezelController {
    private static final Logger log = LoggerFactory.getLogger(DiezelErrorController.class);

    @ExceptionHandler( {Exception.class } )
    private void handleException(DiezelException ex) {
        log.error("Caught generic Exception.", ex);
    }

    @ExceptionHandler( {DiezelException.class })
    private void handleDiezelException(DiezelException ex) {
        log.error("Caught generic DiezelException.", ex);
        // TODO: Persist.
    }

    @ExceptionHandler( {DiezelComponentException.class })
    private void handleDiezelComponentException(DiezelComponentException ex) {
        log.error("Critical error occurred in Diezel component "+ex.getComponentType(), ex);
    }

}
