package dtos;

import enums.InterestType;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoanDisbursalRequestDto {
    String bankName;
    String borrowerName;
    Double principal;
    Double term; //in years
    Double rateOfInterest;
    InterestType interestType = InterestType.SIMPLE; //default
    String initiatedBy = "admin"; //name of admin initiating disbursal request

}
