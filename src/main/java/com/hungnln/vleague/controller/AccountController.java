package com.hungnln.vleague.controller;
import com.hungnln.vleague.entity.Account;
import com.hungnln.vleague.repository.AccountRepository;
import com.hungnln.vleague.service.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1.0/accounts")

public class AccountController {
    public static Logger logger = LoggerFactory.getLogger(AccountController.class);
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private AccountService accountService;
//
//    @RequestMapping(value = "", method = RequestMethod.GET)
//    ResponseEntity<ResponseObject> getAllAccounts(){
//        List<Account> listAccount=accountService.getAllAccounts();
//        return ResponseEntity.status(HttpStatus.OK).body(
//                new ResponseObject("ok","Get all accounts success",listAccount)
//        );
//    }
//    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
//    ResponseEntity<ResponseObject> findById(@PathVariable String id){
//        Optional<Account> foundAccount = accountRepository.findById(id);
//        if(foundAccount.isPresent()){
//            return ResponseEntity.status(HttpStatus.OK).body(
//                    new ResponseObject("ok","Get account detail success",foundAccount)
//            );
//        }else{
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
//                    new ResponseObject("false","Cannot find account with id"+id,"")
//            );
//        }
//    }



//    @RequestMapping(value = "/new", method = RequestMethod.POST)
//    ResponseEntity<ResponseObject>createAccount(@Validated @RequestBody Account account) {
//            return accountRepository.save(account);
//    }

}
