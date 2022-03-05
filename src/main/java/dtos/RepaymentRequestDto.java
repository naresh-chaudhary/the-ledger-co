package dtos;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RepaymentRequestDto {
    private String bankName;
    private String borrowerName;
    private Double amount; //can be lumpsum amount or emi
    private Integer emiNumber;
}
