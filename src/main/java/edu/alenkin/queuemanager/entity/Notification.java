package edu.alenkin.queuemanager.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @author Alenkin Andrew
 * oxqq@ya.ru
 */
@Getter
@Setter
@Entity
public class Notification {
    @Column(name = "id", unique = true, nullable = false)
    @Id
    private String id;

    @Column(name = "deployment_id", unique = true, nullable = false)
    private String deployment_id;

    @Column(name = "status", nullable = false)
    private String status;

    @Column(name = "created", nullable = false)
    private LocalDateTime created;

    @Column(name = "updated")
    private LocalDateTime updated;

    public Notification(String id, String deployment_id, String status) {
        this.id = id;
        this.deployment_id = deployment_id;
        this.status = status;
        this.created = LocalDateTime.now();
        this.updated = created;
    }

    public Notification() {
    }
}


