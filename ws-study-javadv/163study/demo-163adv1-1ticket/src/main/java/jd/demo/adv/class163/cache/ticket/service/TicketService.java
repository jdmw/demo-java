package jd.demo.adv.class163.cache.ticket.service;

import jd.demo.adv.class163.cache.ticket.bean.Ticket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class TicketService {

    @Autowired
    JdbcTemplate jt;

    public Ticket getTicket(String line) {
        return jt.queryForObject("select * from adv_ticket where line = ?", new Object[]{line},
                new BeanPropertyRowMapper<Ticket>(Ticket.class));
    }

    public void setJt(JdbcTemplate jt) {
        this.jt = jt;
    }
}
