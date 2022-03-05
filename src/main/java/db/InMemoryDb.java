package db;

import entities.AccountHolder;
import entities.Bank;
import entities.InboundPayment;
import entities.Loan;
import lombok.Getter;
import lombok.Setter;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Thread safe Singleton inMemory Db.
 */
@Getter
@Setter
public class InMemoryDb {
    AtomicLong idGenerator = new AtomicLong();

    Map<Long,Bank> banks = new ConcurrentHashMap<>();
    Map<Long,Loan> loans = new ConcurrentHashMap<>();
    Map<Long,InboundPayment> inboundPayments = new ConcurrentHashMap<>();
    Map<Long,AccountHolder> accountHolders = new ConcurrentHashMap<>();

    private static volatile InMemoryDb obj  = null;

    private InMemoryDb() {}

    public static InMemoryDb getInstance()
    {
        if (obj == null)
        {
            // To make thread safe
            synchronized (InMemoryDb.class)
            {
                // check again as multiple threads
                // can reach above step
                if (obj==null)
                    obj = new InMemoryDb();
            }
        }
        return obj;
    }
}
