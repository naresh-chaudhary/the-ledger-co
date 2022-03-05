package services;

import dtos.LoanDisbursalRequestDto;
import enums.InterestType;
import exceptions.InvalidInterestTypeException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TransactionManagerAcceptanceTest {
    private TransactionManager transactionManager = new TransactionManager();

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void disburseLoan() throws InvalidInterestTypeException {
        LoanDisbursalRequestDto loanDisbursalRequestDto = LoanDisbursalRequestDto.builder()
                .bankName("IDIDI")
                .borrowerName("Dale")
                .principal(10000.0)
                .rateOfInterest(4.0)
                .term(5.0)
                .initiatedBy("admin")
                .interestType(InterestType.SIMPLE)
                .build();
        Long loanId = transactionManager.disburseLoan(loanDisbursalRequestDto);
        Assertions.assertNotNull(loanId);

    }

}