package com.hungnln.vleague.service;

import com.hungnln.vleague.entity.Account;
import com.hungnln.vleague.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountService {
    @Autowired
    private AccountRepository accountRepository;

    public List<Account> getAllAccounts(){
        List<Account>userAccounts = accountRepository.findAll();
        return userAccounts;
    }

    public Account addAccount(Account account) {

        return accountRepository.save(account);
    }
//    private AccountResponse mappedAccount(Account account) {
//        AccountResponse tmp = new AccountResponse();
//        tmp.setId(account.getId());
//        tmp.setEmail(account.getEmail());
//        tmp.setRole(account.getRole());
//        tmp.setStatus(account.getStatus());
//        tmp.setCreateAt(account.getCreateAt());
//        tmp.setNotificationToken(account.getNotificationToken());
//        return tmp;
//    }
}
