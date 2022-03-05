package interest;

public class SimpleInterestCalculationStrategy implements InterestCalculationStrategy{
    @Override
    public Double calculateInterest(Double principal, Double rate, Double term) {
        return (principal*rate*term)/100;
    }
}
