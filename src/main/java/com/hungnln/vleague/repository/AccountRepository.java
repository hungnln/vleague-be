package com.hungnln.vleague.repository;

import com.hungnln.vleague.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, String> {
    List<Account> findAll();
    Optional<Account> findById(String id);
    Account findByEmail(String email);
}
