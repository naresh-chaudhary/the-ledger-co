package services;

import dtos.LoanDto;
import entities.AccountHolder;
import entities.Bank;
import entities.InboundPayment;
import entities.Loan;
import enums.InterestType;
import exceptions.InvalidAccountHolderException;
import exceptions.InvalidBankException;
import exceptions.InvalidInterestTypeException;
import interest.InterestCalculator;
import org.modelmapper.ModelMapper;
import repository.AccountHolderRepository;
import repository.InboundPaymentRepository;
import repository.LoanRepository;

import java.util.List;

public class LoanService {

    private final ModelMapper modelMapper = new ModelMapper();
    private final LoanRepository loanRepository = new LoanRepository();
    private final BankService bankService = new BankService();
    private final AccountHolderService accountHolderService = new AccountHolderService();

    public Long disburseLoan(LoanDto loanDto, String disbursedBy) throws InvalidInterestTypeException {
        Loan loan = modelMapper.map(loanDto, Loan.class);

        int totalEMIs = (int) Math.ceil(loanDto.getTerm() * 12);
        double totalAmountToBeRecovered = calculateTotalAmount(loanDto.getPrincipal(), loanDto.getRateOfInterest(), loanDto.getTerm());

        loan.setInterestType(loanDto.getInterestType());
        loan.setCreatedBy(disbursedBy);
        loan.setTotalEMIs(totalEMIs);
        loan.setTotalAmount((int) Math.ceil(totalAmountToBeRecovered));
        loan.setEmiAmount((int) Math.ceil(totalAmountToBeRecovered / totalEMIs));
        return loanRepository.persist(loan);
    }

    public Loan getLoanByBankNameAndBorrowerName(String bankName, String borrowerName) throws InvalidBankException, InvalidAccountHolderException {
        Bank bank = bankService.getBankByName(bankName);
        AccountHolder accountHolder = accountHolderService.findByName(borrowerName);
        return loanRepository.findByBankIdAndAccountNumber(bank.getBankId(),accountHolder.getAccountNumber()).get();

    }

    private Double calculateTotalAmount(Double principal, Double rateOfInterest, Double term) throws InvalidInterestTypeException {
        InterestCalculator interestCalculator = new InterestCalculator(InterestType.SIMPLE);
        Double interest = interestCalculator.executeInterestCalculationStrategy(principal, rateOfInterest, term);
        return principal + interest;
    }


}
