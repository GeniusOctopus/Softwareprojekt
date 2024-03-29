package com.softwareproject.backend.controller;

import com.softwareproject.backend.model.*;
import com.softwareproject.backend.service.VoteService;
import org.jsondoc.core.annotation.Api;
import org.jsondoc.core.annotation.ApiMethod;
import org.jsondoc.core.annotation.ApiPathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Api(name = "Vote", description = "Endpunkt für Votes", group = "Vote")
@CrossOrigin(origins = "http://localhost:4200/")
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
    Vote addVote(@ApiPathParam(name = "vote", description = "Wertung, welche in der Datenbank hinzugefügt werden soll") @Valid @RequestBody Vote vote) {

        return voteService.addVote(vote);
    }

    @ApiMethod(description = "Gibt das Ranking aller Bilder zurück")
    @RequestMapping(value = "/ranking", method = RequestMethod.GET)
    public @ResponseBody
    List<Ranking> getRanking() {

        return voteService.getRanking();
    }

    @ApiMethod(description = "Die Anzahl der Bilder zurück, welche jeweils aus der linken und rechten Seite ein Voting gewonnen haben")
    @RequestMapping(value = "/statistics/countOfWinnerOnLeftAndRightSide", method = RequestMethod.GET)
    public @ResponseBody
    WinnerOnLeftSide getCountOfWinnerOnLeftAndRightSide() {

        return voteService.getCountOfWinnerOnLeftAndRightSide();
    }

    @ApiMethod(description = "Gibt die Anzahl der Votes der letzten 24h, der letzten 7 Tage und des gesamten Zeitraums zurück")
    @RequestMapping(value = "/statistics/basicStatisticData", method = RequestMethod.GET)
    public @ResponseBody
    BasicStatisticData getBasicStatisticData() {

        return voteService.getBasicStatisticData();
    }

    @ApiMethod(description = "Gibt zurück, wie viel Zeit für die Votings benötigt wurde")
    @RequestMapping(value = "/statistics/durationStatisticData", method = RequestMethod.GET)
    public @ResponseBody
    DurationStatisticData getDurationStatistic() {

        return voteService.getDurationStatistic();
    }
}
