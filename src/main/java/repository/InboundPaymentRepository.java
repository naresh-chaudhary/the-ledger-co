package repository;

import entities.InboundPayment;

import java.util.List;
import java.util.stream.Collectors;

public class InboundPaymentRepository implements BaseRepository<InboundPayment,Long>{
    @Override
    public Long persist(InboundPayment inboundPayment) {
        Long id = inMemoryDb.getIdGenerator().getAndIncrement();
        inboundPayment.setPaymentId(id);
        inMemoryDb.getInboundPayments().put(id,inboundPayment);
        return id;
    }

    @Override
    public InboundPayment findById(Long paymentId) {
        return inMemoryDb.getInboundPayments().get(paymentId);
    }

    public List<InboundPayment> findByLoanId(Long loanId){
        return inMemoryDb.getInboundPayments().values().stream()
                .filter(inboundPayment -> inboundPayment.getLoanId().equals(loanId)).collect(Collectors.toList());
    }
}
