package com.example.user.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Homepage implements Serializable {
    /**
     * uid
     */
    @ApiModelProperty(value = "uid")
    private Long id;

    /**
     * nickname
     */
    @ApiModelProperty(value = "nickname")
    private String nickname;

    /**
     * up votes
     */
    @ApiModelProperty(value = "up votes")
    private Long upVotes;

    /**
     * down votes
     */
    @ApiModelProperty(value = "down votes")
    private byte[] downVotes;

    /**
     * image
     */
    @ApiModelProperty(value = "image")
    private String profileImageUrl;

    /**
     * website
     */
    @ApiModelProperty(value = "website")
    private String websiteUrl;

    /**
     * homepage view
     */
    @ApiModelProperty(value = "homepage view")
    private Long view;

    /**
     * intro
     */
    @ApiModelProperty(value = "intro")
    private String intro;

    private static final long serialVersionUID = 1L;
}

