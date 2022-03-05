package entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class Bank extends AuditEntity{
    private Long bankId; //internal id
    private String name;
    private String branch;
    private String ifscCode; //external id
    //etc.

}
