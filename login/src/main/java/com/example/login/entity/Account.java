package com.example.login.entity;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Account implements Serializable {
    /**
     *
     */
    @ApiModelProperty(value = "uid")
    private Long uid;

    /**
     *
     */
    @ApiModelProperty(value = "account")
    private String account;

    /**
     *
     */
    @ApiModelProperty(value = "password")
    private String pwd;

    /**
     *
     */
    @ApiModelProperty(value = "creation date")
    private Date creationDate;

    /**
     *
     */
    @ApiModelProperty(value = "latest login date")
    private Date latestLoginDate;

    public Account(String account, String password, Date creationDate, Date latestLoginDate) {
        this.account = account;
        this.pwd = password;
        this.creationDate = creationDate;
        this.latestLoginDate = latestLoginDate;
    }

    private static final long serialVersionUID = 1L;
}

