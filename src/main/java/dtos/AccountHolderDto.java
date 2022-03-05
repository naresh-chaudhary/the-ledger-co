package dtos;

import entities.AuditEntity;
import entities.Bank;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountHolderDto {
    private Long accountNumber; //can be uuid or custom generated number as well
    private String name;
    private Long bankId; // one person can have account in multiple banks
    private String phoneNumber;
    private String email;
    //other details ..
}
