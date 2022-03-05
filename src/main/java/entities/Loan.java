package entities;

import enums.InterestType;
import enums.LoanStatus;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class Loan extends AuditEntity{
    private Long loanId;
    private Long accountHolderId;
    private Long bankId;
    private Double principal;
    private Double rateOfInterest;
    private Double term;
    private InterestType interestType;
    private Integer totalEMIs;
    private Integer totalAmount; //total amount to be recovered at the end of term, ceiled to whole number
    private Integer emiAmount; //ceiled to whole number
    private LoanStatus loanStatus = LoanStatus.ACTIVE; //default, can be used to settle the loan if all payments are done.

}
