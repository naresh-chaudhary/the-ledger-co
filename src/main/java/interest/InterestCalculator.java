package interest;

import enums.InterestType;
import exceptions.InvalidInterestTypeException;

public class InterestCalculator {
    private InterestCalculationStrategy interestCalculationStrategy;

    public InterestCalculator(InterestType interestType) throws InvalidInterestTypeException {
        if(InterestType.SIMPLE.equals(interestType)) {
            this.interestCalculationStrategy = new SimpleInterestCalculationStrategy();
        }else if(InterestType.COMPOUND.equals(interestType)){
            this.interestCalculationStrategy = new CompoundInterestCalculationStrategy();
        }else{
            throw new InvalidInterestTypeException("Interest calculation Strategy for interestType: "
                    +interestType+"not implemented");
        }
    }

    public Double executeInterestCalculationStrategy(Double principal, Double rate, Double term){
        return interestCalculationStrategy.calculateInterest(principal,rate,term);
    }
}
