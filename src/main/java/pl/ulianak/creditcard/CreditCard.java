package pl.ulianak.creditcard;

import java.math.BigDecimal;

public class CreditCard {
    private BigDecimal creditlimit;
    private BigDecimal creditLimit;

    public void assignCredit(BigDecimal creditlimit){
        this.creditlimit = creditlimit;
    }

    public BigDecimal getBalance(){
        return creditLimit;
    }
}
