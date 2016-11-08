package com.lynas.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by LynAs on 22-Mar-16
 */
@Entity
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public
@Data
class AccountTransaction implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String voucherNumber;
    @Column(nullable = false)
    private String description;
    @Column(nullable = false)
    private String entryType;
    @Column(nullable = false)
    private double amount;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date entryDate;
    @Column(nullable = false)
    private String colorType;
    @ManyToOne
    private Account account;


}
