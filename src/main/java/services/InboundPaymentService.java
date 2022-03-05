package services;

import dtos.RepaymentRequestDto;
import entities.InboundPayment;
import entities.Loan;
import enums.LoanStatus;
import enums.PaymentType;
import exceptions.*;
import repository.InboundPaymentRepository;

import java.util.List;

public class InboundPaymentService {
    private final InboundPaymentRepository inboundPaymentRepository = new InboundPaymentRepository();
    private final LoanService loanService = new LoanService();


    public Long repayWithLumpSumAmount(RepaymentRequestDto repaymentRequestDto, String user) throws InvalidBankException, InvalidAccountHolderException, InvalidEmiNumberException, LoanAlreadySettledException, InvalidAmountException {
        Loan loan = loanService.getLoanByBankNameAndBorrowerName(repaymentRequestDto.getBankName(), repaymentRequestDto.getBorrowerName());
        validateRepayment(repaymentRequestDto, loan);
        InboundPayment inboundPaymentEntity = new InboundPayment();
        inboundPaymentEntity.setPaymentType(PaymentType.LUMP_SUM);
        inboundPaymentEntity.setAmountPaid(repaymentRequestDto.getAmount());
        inboundPaymentEntity.setEmiNumber(repaymentRequestDto.getEmiNumber());
        inboundPaymentEntity.setLoanId(loan.getLoanId());
        inboundPaymentEntity.setCreatedBy(user);
        return inboundPaymentRepository.persist(inboundPaymentEntity);
    }

    private void validateRepayment(RepaymentRequestDto repaymentRequestDto, Loan loan) throws InvalidEmiNumberException, LoanAlreadySettledException, InvalidAmountException {
        double remainingAmount = getRemainingAmount(loan, repaymentRequestDto.getEmiNumber());

        if ((int) remainingAmount == 0 || LoanStatus.SETTLED.equals(loan.getLoanStatus())) {
            throw new LoanAlreadySettledException("Loan already settled.");
        }

        if (repaymentRequestDto.getEmiNumber() > loan.getTotalEMIs()) {
            throw new InvalidEmiNumberException("EMI number greater than total EMIs");
        }

        if (repaymentRequestDto.getEmiNumber() < 0) {
            throw new InvalidEmiNumberException("EMI number can not be negative");
        }

        //if not last EMI then repayment amount can not be less than EMI amount.
        if (repaymentRequestDto.getEmiNumber() != loan.getTotalEMIs()) {
            if (repaymentRequestDto.getAmount() < loan.getEmiAmount()) {
                throw new InvalidAmountException("Repayment amount can not be less than EMI amount i.e. Rs. " + loan.getEmiAmount());
            }
        }

        if (repaymentRequestDto.getAmount() > remainingAmount) {
            throw new InvalidAmountException("Repayment amount is greater then remaining amount: Rs. " + remainingAmount);
        }

    }

    public Double getRemainingAmount(Loan loan, Integer emiNumber) {
        int totalAmount = loan.getTotalAmount();
        List<InboundPayment> repayments = inboundPaymentRepository.findByLoanId(loan.getLoanId());
        double lumpSumPaymentDoneTillNow = repayments.stream()
                .filter(inboundPayment -> inboundPayment.getEmiNumber() <= emiNumber
                        && inboundPayment.getPaymentType().equals(PaymentType.LUMP_SUM))
                .mapToDouble(InboundPayment::getAmountPaid)
                .sum();


        double totalPaymentDoneTillNow =lumpSumPaymentDoneTillNow+emiNumber*loan.getEmiAmount(); //assuming we are not marking EMIs in Inbound Payment currently
        //adjust last emi to remaining amount
        if(totalPaymentDoneTillNow>totalAmount){
            return 0.0;
        }
        return totalAmount - totalPaymentDoneTillNow;
    }

}
