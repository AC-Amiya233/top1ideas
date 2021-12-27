package com.example.login.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import lombok.Data;


@Data
public class Homepage implements Serializable {
    /**
     *
     */
    @ApiModelProperty(value = "uid")
    private Long id;

    /**
     *
     */
    @ApiModelProperty(value = "nickname")
    private String nickname;

    /**
     *
     */
    @ApiModelProperty(value = "up votes")
    private Long upVotes;

    /**
     *
     */
    @ApiModelProperty(value = "down votes")
    private byte[] downVotes;

    /**
     *
     */
    @ApiModelProperty(value = "profile image url")
    private String profileImageUrl;

    /**
     *
     */
    @ApiModelProperty(value = "website url")
    private String websiteUrl;

    /**
     *
     */
    @ApiModelProperty(value = "view")
    private Long view;

    private static final long serialVersionUID = 1L;
}

