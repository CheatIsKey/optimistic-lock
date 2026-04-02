package jpa.basic.optimisticlock;

import jpa.basic.optimisticlock.common.entity.Account;
import jpa.basic.optimisticlock.domain.repository.AccountRepository;
import jpa.basic.optimisticlock.domain.service.AccountService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SpringBootTest
class OptimisticLockTest {

    @Autowired
    AccountRepository accountRepository;
    @Autowired
    AccountService accountService;

    @Test
    void 낙관적락_정상동작_테스트() throws InterruptedException {
        Account account = accountRepository.save(new Account(100));

        ExecutorService executor = Executors.newFixedThreadPool(2);
        Runnable task = () -> accountService.withdrawWithRetry(account.getId(), 10);

        executor.submit(task);
        executor.submit(task);

        executor.shutdown();
        
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        Account result = accountRepository.findById(account.getId()).orElseThrow();
        System.out.println("최종 잔액: " + result.getBalance());
    }
}