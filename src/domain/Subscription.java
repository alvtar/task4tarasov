package domain;

import java.time.Year;
import java.util.Date;

public class Subscription extends Entity {
    private Date regDate;
    
    ///// User user; Publication publication;
    private Integer user;
    private Integer publication;
    private Year subsYear;
    private Integer subsMonths;
    private Float paymentSum;

    public Subscription(Date regDate, Integer user, Integer publication, Year subsYear, Integer subsMonths,
            Float paymentSum) {
        super();
        this.regDate = regDate;
        this.user = user;
        this.publication = publication;
        this.subsYear = subsYear;
        this.subsMonths = subsMonths;
        this.paymentSum = paymentSum;
    }

    public Subscription() {
        super();
    }

    
    public Date getRegDate() {
        return regDate;
    }

    public void setRegDate(Date regDate) {
        this.regDate = regDate;
    }

    public Integer getUser() {
        return user;
    }

    public void setUser(Integer user) {
        this.user = user;
    }

    public Integer getPublication() {
        return publication;
    }

    public void setPublication(Integer publication) {
        this.publication = publication;
    }

    public Year getSubsYear() {
        return subsYear;
    }

    public void setSubsYear(Year subsYear) {
        this.subsYear = subsYear;
    }

    public Integer getSubsMonths() {
        return subsMonths;
    }

    public void setSubsMonths(Integer subsMonths) {
        this.subsMonths = subsMonths;
    }

    public Float getPaymentSum() {
        return paymentSum;
    }

    public void setPaymentSum(Float paymentSum) {
        this.paymentSum = paymentSum;
    }
    
    @Override
    public String toString() {
            return "Subscription [regDate="+regDate + ", user=" + user.toString() + ", publication="
                            + publication.toString() + ", subsYear=" + subsYear + ", subsMonths=" + subsMonths + ", paymentSum=" + paymentSum + "]";
            // переделать формат
    }

}
