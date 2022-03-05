package repository;

import entities.AccountHolder;

import java.util.Optional;

public class AccountHolderRepository implements BaseRepository<AccountHolder,Long> {

    @Override
    public Long persist(AccountHolder accountHolder) {
        Long id = inMemoryDb.getIdGenerator().getAndIncrement();
        accountHolder.setAccountNumber(id);
        inMemoryDb.getAccountHolders().put(id,accountHolder);
        return id;
    }

    @Override
    public AccountHolder findById(Long accountNumber) {
        return inMemoryDb.getAccountHolders().get(accountNumber);
    }

    public Optional<AccountHolder> findByName(String name){
        return inMemoryDb.getAccountHolders().values().stream().filter(accountHolder -> accountHolder.getName().equals(name)).findFirst();

    }
}
