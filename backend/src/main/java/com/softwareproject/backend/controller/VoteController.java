package com.softwareproject.backend.controller;

import com.softwareproject.backend.model.Vote;
import com.softwareproject.backend.service.VoteService;
import org.jsondoc.core.annotation.Api;
import org.jsondoc.core.annotation.ApiMethod;
import org.jsondoc.core.annotation.ApiPathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.util.List;

@Api(name = "Vote", description = "Endpunkt für Votes", group = "Vote")
@Controller
@RequestMapping("/vote")
public class VoteController {

    @Autowired
    VoteService voteService;

    @ApiMethod(description = "Gibt alle Vote-Einträge zurück")
    @RequestMapping(value = "/votes", method = RequestMethod.GET)
    public @ResponseBody
    List<Vote> getAllIVotes() {

        return voteService.getAllIVotes();
    }

    @ApiMethod(description = "Speichert ein Bild in der Datenbank")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public @ResponseBody
    Vote addVote(@ApiPathParam(name = "vote", description = "Wertung, welche ind er Datenbank hinzugefügt werden soll") @Valid @RequestBody Vote vote) {

        return voteService.addVote(vote);
    }
}
