import dtos.BalanceRequestDto;
import dtos.BalanceResponseDto;
import dtos.LoanDisbursalRequestDto;
import dtos.RepaymentRequestDto;
import enums.InterestType;
import exceptions.*;
import services.TransactionManager;
import utils.FileUtils;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class TheLedgerCoApplication {
    public static void main(String[] args) throws IOException, InvalidInterestTypeException, InvalidEmiNumberException, LoanAlreadySettledException, InvalidAmountException, InvalidBankException, InvalidAccountHolderException {
        TransactionManager transactionManager = new TransactionManager();
        List<String> lines;
        if(args.length!=0) {
            lines = FileUtils.readFileInList(args[0]);
        }else{
            lines = FileUtils.readFileInList("src/main/resources/input2.txt");
        }
        List<String[]> commands = lines.stream().map(line -> line.split(" ")).collect(Collectors.toList());//.forEach(line->System.out.println(line));
        for(String[] command : commands){
            if(command[0].equals("LOAN")){
                //System.out.println("LOAN command:");
                LoanDisbursalRequestDto loanDisbursalRequestDto = LoanDisbursalRequestDto.builder()
                        .bankName(command[1])
                        .borrowerName(command[2])
                        .principal(Double.parseDouble(command[3]))
                        .term(Double.parseDouble(command[4]))
                        .rateOfInterest(Double.parseDouble(command[5]))
                        .initiatedBy("ledger_co")
                        .interestType(InterestType.SIMPLE) //default
                        .build();
                Long loanId = transactionManager.disburseLoan(loanDisbursalRequestDto);
                //System.out.println("Loan Disbursed with id: "+loanId);
            }else if(command[0].equals("PAYMENT")){
               //System.out.println("PAYMENT command:");
                RepaymentRequestDto repaymentRequestDto = RepaymentRequestDto.builder()
                        .bankName(command[1])
                        .borrowerName(command[2])
                        .amount(Double.parseDouble(command[3]))
                        .emiNumber(Integer.parseInt(command[4]))
                        .build();
                Long paymentId = transactionManager.repayWithLumpSumAmount(repaymentRequestDto, "nareshkumar");
                //System.out.println("Lumpsum payment done with id: "+paymentId);
            }else if(command[0].equals("BALANCE")){
                //System.out.println("BALANCE command:");
                BalanceRequestDto balanceRequestDto = BalanceRequestDto.builder()
                        .bankName(command[1])
                        .borrowerName(command[2])
                        .emiNo(Integer.parseInt(command[3]))
                        .build();

                BalanceResponseDto balanceResponseDto = transactionManager.displayBalance(balanceRequestDto);
                System.out.println(balanceResponseDto.getBankName()+" "
                        +balanceResponseDto.getBorrowerName()+" "
                        +balanceResponseDto.getAmountPaid()+" "+balanceResponseDto.getNoOfEmisLeft());

            }else{
                System.out.println("Invalid command: " + command[0]);
                throw new RuntimeException("Invalid input");

            }
            }

    }


}
