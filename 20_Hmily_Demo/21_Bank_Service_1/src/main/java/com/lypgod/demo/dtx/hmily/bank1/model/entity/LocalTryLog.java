package com.lypgod.demo.dtx.hmily.bank1.model.entity;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;

/**
 * @author lypgod
 */
@Entity
@Data
public class LocalTryLog {
    @Id
    private String txNo;
    @CreationTimestamp
    private LocalDateTime createTime;
}
