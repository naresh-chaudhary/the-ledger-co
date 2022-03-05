package services;

import dtos.*;
import entities.Loan;
import exceptions.*;
import org.modelmapper.ModelMapper;

public class TransactionManager {
    private final BankService bankService = new BankService(); //TODO: add dependency injection
    private final LoanService loanService = new LoanService();
    private final ModelMapper modelMapper = new ModelMapper();
    private final InboundPaymentService inboundPaymentService = new InboundPaymentService();

    /**
     * Initiate a loan disbursal request,
     * @param loanDisbursalRequest
     * @return
     * @throws InvalidInterestTypeException
     */
    public Long disburseLoan(LoanDisbursalRequestDto loanDisbursalRequest) throws InvalidInterestTypeException {
        Long bankId = bankService.onboardBank(BankDto.builder()
                        .name(loanDisbursalRequest.getBankName()).build(),
                loanDisbursalRequest.getInitiatedBy());

        Long accountHolderId = bankService.onboardLoanAccount(AccountHolderDto.builder()
                .name(loanDisbursalRequest.getBorrowerName())
                .bankId(bankId).build(), loanDisbursalRequest.getInitiatedBy());

        LoanDto loanDto = modelMapper.map(loanDisbursalRequest, LoanDto.class);
        loanDto.setBankId(bankId);
        loanDto.setAccountHolderId(accountHolderId);
        Long loanId = loanService.disburseLoan(loanDto, loanDisbursalRequest.getInitiatedBy());
        //System.out.println("Loan Disbursed with id: "+loanId);
        return loanId;
    }

    /**
     *
     * @param repaymentRequestDto
     * @param repayedBy
     * @return
     * @throws InvalidEmiNumberException
     * @throws LoanAlreadySettledException
     * @throws InvalidAmountException
     * @throws InvalidBankException
     * @throws InvalidAccountHolderException
     */
    public Long repayWithLumpSumAmount(RepaymentRequestDto repaymentRequestDto,String repayedBy) throws
            InvalidEmiNumberException, LoanAlreadySettledException, InvalidAmountException, InvalidBankException, InvalidAccountHolderException {
        return inboundPaymentService.repayWithLumpSumAmount(repaymentRequestDto,repayedBy);
    }

    /**
     *
     * @param balanceRequestDto
     * @return
     * @throws InvalidBankException
     * @throws InvalidAccountHolderException
     */
    public BalanceResponseDto displayBalance(BalanceRequestDto balanceRequestDto) throws InvalidBankException, InvalidAccountHolderException {
        Loan loan = loanService.getLoanByBankNameAndBorrowerName(balanceRequestDto.getBankName(), balanceRequestDto.getBorrowerName());
        double remainingAmount = inboundPaymentService.getRemainingAmount(loan, balanceRequestDto.getEmiNo());

        return BalanceResponseDto.builder()
                .bankName(balanceRequestDto.getBankName())
                .borrowerName(balanceRequestDto.getBorrowerName())
                .amountPaid((int)(loan.getTotalAmount()-remainingAmount))
                .noOfEmisLeft((int)Math.ceil(remainingAmount/loan.getEmiAmount()))
                .build();

    }



}
