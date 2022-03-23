package com.softwareproject.backend.service;

import com.softwareproject.backend.model.Vote;
import com.softwareproject.backend.repository.VoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.List;

@Validated
@Service
public class VoteService {

    @Autowired
    VoteRepository voteRepository;

    public List<Vote> getAllIVotes() {

        return voteRepository.findAll();
    }

    public Vote addVote(@Valid Vote vote) {

        return voteRepository.save(vote);
    }
}
