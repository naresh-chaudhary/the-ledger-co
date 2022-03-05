package entities;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountHolder extends AuditEntity{
    private Long accountNumber; //can be uuid or custom generated number as well
    private String name;
    private Long bankId; // one person can have account in multiple banks
    private String phoneNumber;
    private String email;
    //other details ..
}
