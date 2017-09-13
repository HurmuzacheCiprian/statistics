package com.interview.n26.interviewn26.resources;

import com.interview.n26.interviewn26.model.Transaction;
import com.interview.n26.interviewn26.services.TransactionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transactions")
public class Transactions {

    private final TransactionsService transactionsService;

    @Autowired
    public Transactions(TransactionsService transactionsService) {
        this.transactionsService = transactionsService;
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity transactions(@RequestBody Transaction transaction) {
        int responseCode = transactionsService.processTransaction(transaction);
        return new ResponseEntity(HttpStatus.valueOf(responseCode));
    }

}
