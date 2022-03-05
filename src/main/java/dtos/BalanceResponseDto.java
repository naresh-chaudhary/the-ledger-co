package dtos;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BalanceResponseDto {
    private String bankName;
    private String borrowerName;
    private Integer amountPaid; //can be double, but following the input output restrictions here
    private Integer noOfEmisLeft;
}
