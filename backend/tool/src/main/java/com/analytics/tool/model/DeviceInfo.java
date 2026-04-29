package com.analytics.tool.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "deviceinfo")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeviceInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private String browser;
    private String os;
    private String device;
}
