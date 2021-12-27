package com.example.papers.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;


@Data
public class Paper implements Serializable {
    /**
     * paper id
     */
    @ApiModelProperty(value = "paper id")
    private Long pid;

    /**
     * title
     */
    @ApiModelProperty(value = "title")
    private String title;

    /**
     * authors
     */
    @ApiModelProperty(value = "authors")
    private String authors;

    /**
     * publish
     */
    @ApiModelProperty(value = "publish")
    private String publish;

    /**
     * version
     */
    @ApiModelProperty(value = "version")
    private Date version;

    private static final long serialVersionUID = 1L;
}
