package bg.softuni.homerestate.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;

@ControllerAdvice
public class GlobalAdvise {

    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalAdvise.class);

    @ExceptionHandler(RuntimeException.class)
    public ModelAndView handleException(RuntimeException exception){
        LOGGER.error("Exception caught",exception);
        ModelAndView modelAndView = new ModelAndView("error-page");
        modelAndView.addObject("message", exception.getMessage());
        return modelAndView;
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ModelAndView handleMaximumFileSize(MaxUploadSizeExceededException exception){
        LOGGER.error("Exception caught",exception);
        ModelAndView modelAndView = new ModelAndView("error-page");
        modelAndView.addObject("message", exception.getMessage());
        return modelAndView;
    }

    @ExceptionHandler(IllegalStateException.class)
    public ModelAndView handleIllegalStateException(IllegalStateException exception){
        LOGGER.error("Exception caught",exception);
        ModelAndView modelAndView = new ModelAndView("error-page");
        modelAndView.addObject("message", exception.getMessage());
        return modelAndView;
    }
    @ExceptionHandler(IllegalArgumentException.class)
    public ModelAndView handleIllegalArgument(IllegalArgumentException exception){
        LOGGER.error("Exception caught",exception);
        ModelAndView modelAndView = new ModelAndView("error-page");
        modelAndView.addObject("message", exception.getMessage());
        return modelAndView;
    }
    @ExceptionHandler(IOException.class)
    public ModelAndView handleIllegalInput(IOException exception){
        LOGGER.error("Exception caught",exception);
        ModelAndView modelAndView = new ModelAndView("error-page");
        modelAndView.addObject("message", exception.getMessage());
        return modelAndView;
    }
    @ExceptionHandler(NullPointerException.class)
    public ModelAndView handleNullPointerArgument(NullPointerException exception){
        LOGGER.error("Exception caught",exception);
        ModelAndView modelAndView = new ModelAndView("error-page");
        modelAndView.addObject("message", exception.getMessage());
        return modelAndView;
    }

}
