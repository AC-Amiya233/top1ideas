package com.example.login.config;

public class BloomingFilterConfig {
    final static String lower_chars = "abcdefghijklmnopqrstuvwxyz";
    final static String higher_chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    final static String int_chars = "1234567890";
    final static String special_chars = " ";
    final static String mail_chars = "@.";

    public final static String keywordChars = lower_chars + higher_chars + int_chars;
    public final static String mailChars = keywordChars + mail_chars;
}