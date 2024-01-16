package org.food.dao;

import org.food.api.repository.AccountRepository;
import org.food.model.Account;
import org.springframework.stereotype.Repository;

@Repository
public class AccountRepositoryImpl extends AbstractDao<Account> implements AccountRepository {

    public AccountRepositoryImpl() {
        super(Account.class);
    }
}
