package dtos;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BankDto {
    private Long bankId; //internal id
    private String name;
    private String branch;
    private String ifscCode; //external id
    //etc.

}
