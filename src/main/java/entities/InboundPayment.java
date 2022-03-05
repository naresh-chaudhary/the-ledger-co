package entities;

import enums.PaymentType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InboundPayment extends AuditEntity{
    private Long paymentId;
    private Long loanId;
    private PaymentType paymentType = PaymentType.LUMP_SUM; //default,
    private Double amountPaid;
    private Integer emiNumber;

}
