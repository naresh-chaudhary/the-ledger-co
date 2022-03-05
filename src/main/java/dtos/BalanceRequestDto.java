package dtos;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BalanceRequestDto {
    private String bankName;
    private String borrowerName;
    private Integer emiNo;
}
