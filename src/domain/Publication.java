package domain;

import java.util.Date;

public class Publication extends Entity {
    private Integer issn;
    private String title;
    private Float monthCost;
    private Boolean active;
    private Date lastUpdate;

    
    public Publication(Integer issn, String title, Float monthCost,
            Boolean active, Date lastUpdate) {
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

    public Date getlastUpdate() {
        return lastUpdate;
    }

    public void setlastUpdate(Date lastUpdate) {
        this.lastUpdate = lastUpdate;
    }
    
    @Override
    public String toString() {
            return "Publication [issn="+issn + ", title=" + title + ", monthCost="
                            + monthCost + ", active=" + active + ", lastUpdate=" + lastUpdate + "]";
            // переделать формат
    }

}
