package org.fms.web.controller.system;

import org.fms.web.utils.Results;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by lion on 2017/8/3.
 */
@RestController
public class MainsiteErrorController implements ErrorController {

    private static final String ERROR_PATH = "/error";

    @RequestMapping(value=ERROR_PATH)
    public Results handleError(){
        return new Results(403,"");
    }

    @Override
    public String getErrorPath() {
        return "403";
    }

    @RequestMapping(value="/deny")
    public String deny(){
        return "deny";
    }
}

