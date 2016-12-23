package domain;

import java.util.Date;
import exception.PersistentException;
import service.PublicationService;
import service.ServiceLocator;
import service.UserService;

public class Subscription extends Entity {
    private Date regDate;
    private Integer userId;
    private Integer publicationId;
    private Integer subsYear;
    private Integer subsMonths;
    private Float paymentSum;
    UserService userService = ServiceLocator.getService(UserService.class);
    PublicationService publicationService = ServiceLocator.getService(PublicationService.class);

    public Subscription(Date regDate, Integer userId, Integer publicationId, 
            Integer subsYear, Integer subsMonths, Float paymentSum) {
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

    // Convert decimal value of months to binary
    public String getMonthsBinaryArray() {
        StringBuilder sb = new StringBuilder();
        int x = 1;
        for (int i = 0; i < 12; i++) {
            sb.append((subsMonths & x) == 0 ? "0" : "1");
            x <<= 1;
        }
        return sb.toString();
    }

    @Override
    public String toString() {
        String userFullName = "";
        String publicationTitle = "";
        try {
            userFullName = userService.findById(userId).getFullName();
            publicationTitle = publicationService.findById(publicationId).getTitle();
        } catch (PersistentException e) {
        }
        return "ПОДПИСКА: [Дата регистрации=" + regDate + ", Подписчик=" + userFullName + ", Издание="
                + publicationTitle + ", Год подписки=" + subsYear + ", Месяцы подписки=" + getMonthsBinaryArray()
                + ", Сумма платежа=" + paymentSum + " руб.]\n";
    }
}
