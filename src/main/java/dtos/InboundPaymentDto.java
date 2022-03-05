package dtos;

import entities.AuditEntity;
import entities.Loan;
import enums.PaymentType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InboundPaymentDto {
    private Long paymentId;
    private Long loanId;
    private PaymentType paymentType = PaymentType.LUMP_SUM; //default
    private Double amountPaid;

}
