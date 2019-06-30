package jd.demo.adv.class163.cache.ticket.bean;

import java.math.BigDecimal;

public class Ticket {

    private String line;
    private BigDecimal price;
    private int avaliable;
    private int total;

    public String getLine() {
        return line;
    }

    public void setLine(String line) {
        this.line = line;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getAvaliable() {
        return avaliable;
    }

    public void setAvaliable(int avaliable) {
        this.avaliable = avaliable;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
