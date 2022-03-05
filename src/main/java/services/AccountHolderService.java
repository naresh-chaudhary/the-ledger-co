package services;

import entities.AccountHolder;
import exceptions.InvalidAccountHolderException;
import repository.AccountHolderRepository;

import java.util.Optional;

public class AccountHolderService {
    private final AccountHolderRepository accountHolderRepository = new AccountHolderRepository();

    public AccountHolder findByName(String name) throws InvalidAccountHolderException {
        Optional<AccountHolder> accountHolder = accountHolderRepository.findByName(name);
        if(accountHolder.isPresent()){
            return accountHolder.get();
        }else throw new InvalidAccountHolderException("Account information with given name not found");
    }

}
