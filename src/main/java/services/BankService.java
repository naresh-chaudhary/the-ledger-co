package services;

import dtos.AccountHolderDto;
import dtos.BankDto;
import entities.AccountHolder;
import entities.Bank;
import exceptions.InvalidBankException;
import org.modelmapper.ModelMapper;
import repository.AccountHolderRepository;
import repository.BankRepository;

import java.util.Optional;

public class BankService {
    private ModelMapper modelMapper = new ModelMapper();
    private BankRepository bankRepository = new BankRepository();
    private AccountHolderRepository accountHolderRepository = new AccountHolderRepository();

    public Long onboardBank(BankDto bankDetails,String onboardedBy){
        Optional<Bank> bank = bankRepository.findByName(bankDetails.getName());
        if(bank.isPresent()){
            return bank.get().getBankId();
        }

        Bank bankEntity = modelMapper.map(bankDetails, Bank.class);
        bankEntity.setCreatedBy(onboardedBy);
        return bankRepository.persist(bankEntity);
    }

    public Long onboardLoanAccount(AccountHolderDto accountHolder,String onboardedBy){
        Optional<AccountHolder> account = accountHolderRepository.findByName(accountHolder.getName());
        if(account.isPresent()){
            return account.get().getAccountNumber();
        }
        AccountHolder accountEntity = modelMapper.map(accountHolder,AccountHolder.class);
        accountEntity.setCreatedBy(onboardedBy);
        return accountHolderRepository.persist(accountEntity);
    }

    public Bank getBankByName(String name) throws InvalidBankException {

        Optional<Bank> bank = bankRepository.findByName(name);
        if(bank.isPresent()){
            return bank.get();
        }else throw new InvalidBankException("Bank name not found");
    }

}
