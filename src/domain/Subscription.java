package domain;

import java.util.Date;

public class Subscription extends Entity {
    private Date regDate;
    
    ///// User user; Publication publication;
    private Integer userId;
    private Integer publicationId;
    private Integer subsYear;
    private Integer subsMonths;
    private Float paymentSum;

    public Subscription(Date regDate, Integer userId, Integer publicationId, Integer subsYear, Integer subsMonths,
            Float paymentSum) {
        super();
        this.regDate = regDate;
        this.userId = userId;
        this.publicationId = publicationId;
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

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getPublicationId() {
        return publicationId;
    }

    public void setPublicationId(Integer publicationId) {
        this.publicationId = publicationId;
    }

    public Integer getSubsYear() {
        return subsYear;
    }

    public void setSubsYear(Integer subsYear) {
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
            return "Subscription [regDate="+regDate + ", user=" + userId.toString() + ", publication="
                            + publicationId.toString() + ", subsYear=" + subsYear + ", subsMonths=" + subsMonths + ", paymentSum=" + paymentSum + "]";
            // переделать формат
    }

}
