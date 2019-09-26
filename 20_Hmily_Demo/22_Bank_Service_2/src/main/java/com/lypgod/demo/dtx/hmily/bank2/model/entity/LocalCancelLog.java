package com.lypgod.demo.dtx.hmily.bank2.model.entity;

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
public class LocalCancelLog {
    @Id
    private String txNo;
    @CreationTimestamp
    private LocalDateTime createTime;
}
