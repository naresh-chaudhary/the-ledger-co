package dtos;

import entities.AccountHolder;
import entities.AuditEntity;
import entities.Bank;
import enums.InterestType;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoanDto {
    private Long loanId;
    private Long accountHolderId;
    private Long bankId;
    private Double principal;
    private Double rateOfInterest;
    private Double term; //years
    private InterestType interestType = InterestType.SIMPLE; //default

}
