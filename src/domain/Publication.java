package domain;

import java.util.Date;

public class Publication extends Entity {
    private Integer issn;
    private String title;
    private Float monthCost;
    private Boolean active;
    private Date lastUpdate;

    public Publication(Integer issn, String title, 
            Float monthCost, Boolean active, Date lastUpdate) {
        super();
        this.issn = issn;
        this.title = title;
        this.active = active;
        this.lastUpdate = lastUpdate;
    }

    public Publication() {
        super();
    }

    public Integer getIssn() {
        return issn;
    }

    public void setIssn(Integer issn) {
        this.issn = issn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public float getMonthCost() {
        return monthCost;
    }

    public void setMonthCost(float monthCost) {
        this.monthCost = monthCost;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Date getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Date lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    @Override
    public String toString() {
        return "ИЗДАНИЕ: [Индекс: " + issn + ", \t Название: " 
                + title + ",  Цена за месяц: " + monthCost + ",  Доступно: " 
                + active + ",  Последнее изменение: " + lastUpdate + "]\n";
        // переделать формат
    }
}
