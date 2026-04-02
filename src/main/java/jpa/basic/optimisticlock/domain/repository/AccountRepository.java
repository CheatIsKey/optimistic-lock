package jpa.basic.optimisticlock.domain.repository;

import jpa.basic.optimisticlock.common.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
}