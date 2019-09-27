package com.lypgod.demo.dtx.notifymsg.account.model.entity;

import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;

/**
 * @author lypgod
 */
@Entity
@Data
@Accessors(chain = true)
public class DeDuplication {
    @Id
    private String txNo;
    @CreationTimestamp
    private LocalDateTime createTime;
}
