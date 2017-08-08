package net.dzale.diezel.controller.grams;

import net.dzale.diezel.model.database.GramEntity;
import net.dzale.diezel.model.grams.Gram;
import net.dzale.diezel.service.GramService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * Created by zalet on 8/5/2017.
 */
@Controller
@RequestMapping("/grams")
public class DiezelGramController {
    Logger log = LoggerFactory.getLogger(DiezelGramController.class);

    @Autowired
    private GramService gramService;

    @RequestMapping("/{gramId}")
    @ResponseBody
    public Gram getGramById(@PathVariable("gramId") Long gramId) {
        return gramService.getGramById(gramId);
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public String addGram(@RequestBody String body) {
        log.info("Adding gram: " + body);
        GramEntity gramEntityAdded = gramService.addGram(body);
        return gramEntityAdded.getId().toString();
    }

}
