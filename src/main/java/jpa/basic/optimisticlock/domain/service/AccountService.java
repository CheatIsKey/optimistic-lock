package jpa.basic.optimisticlock.domain.service;

import lombok.RequiredArgsConstructor;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountCoreService coreService;

    public void withdrawWithRetry(Long id, int amount) {
        int retry = 0;
        while (retry < 10) {
            try {
                coreService.withdraw(id, amount);
                return;
            } catch (ObjectOptimisticLockingFailureException e) {
                retry++;
                System.out.println("충돌 발생, 재시도: " + retry);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException ignored) {
                }
            }
        }
        throw new IllegalStateException("10회 재시도 후 실패");
    }
}