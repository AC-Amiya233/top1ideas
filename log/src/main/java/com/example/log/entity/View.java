package com.example.log.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class View implements Serializable {
    /**
     * item id
     */
    @ApiModelProperty(value = "item id")
    private Long id;

    /**
     * user id
     */
    @ApiModelProperty(value = "user id")
    private Long uid;

    /**
     * paper id
     */
    @ApiModelProperty(value = "paper id")
    private Long pid;

    /**
     * view date
     */
    @ApiModelProperty(value = "view date")
    private Date viewDate;

    private static final long serialVersionUID = 1L;

    public View(Long uid, Long pid) {
        this.uid = uid;
        this.pid = pid;
    }
}

