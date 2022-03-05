package repository;

import entities.Loan;

import java.util.Optional;

public class LoanRepository implements BaseRepository<Loan, Long> {

    @Override
    public Long persist(Loan loan) {
        Long id = inMemoryDb.getIdGenerator().getAndIncrement();
        loan.setLoanId(id);
        inMemoryDb.getLoans().put(id, loan);
        return id;
    }

    @Override
    public Loan findById(Long loanId) {
        return inMemoryDb.getLoans().get(loanId);
    }

    public Optional<Loan> findByBankIdAndAccountNumber(Long bankId, Long accountNumber) {
        return inMemoryDb.getLoans().values().stream()
                .filter(loan -> loan.getBankId().equals(bankId) && loan.getAccountHolderId().equals(accountNumber))
                .findFirst(); //assuming one loan per account and bank.
    }
}
