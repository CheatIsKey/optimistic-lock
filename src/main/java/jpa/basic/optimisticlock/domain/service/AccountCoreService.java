package jpa.basic.optimisticlock.domain.service;

import jpa.basic.optimisticlock.common.entity.Account;
import jpa.basic.optimisticlock.domain.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AccountCoreService {

    private final AccountRepository accountRepository;

    @Transactional
    public void withdraw(Long id, int amount) {
        Account account = accountRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("계좌 없음"));

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        account.decrease(amount);
    }
}