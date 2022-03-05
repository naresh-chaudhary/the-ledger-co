package repository;

import entities.Bank;

import java.util.Optional;

public class BankRepository implements BaseRepository<Bank, Long> {
    @Override
    public Long persist(Bank bank) {
        Long id = inMemoryDb.getIdGenerator().getAndIncrement();
        bank.setBankId(id);
        inMemoryDb.getBanks().put(id, bank);
        return id;
    }

    @Override
    public Bank findById(Long bankId) {
        return inMemoryDb.getBanks().get(bankId);
    }

    //We can use a reverseIndex Map<bankName,Bank> to make below query O(1)
    public Optional<Bank> findByName(String name) {
        return inMemoryDb.getBanks().values().stream().filter(bank -> bank.getName().equals(name)).findFirst();
    }
}
