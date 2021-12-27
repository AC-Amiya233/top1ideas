package com.example.user.predefined;

public enum Result {
    Processing(1000, "Server Processing"),

    WrongPassword(1001, "Wrong Password"),
    NoMatchAccount(1002, "No Match Account"),
    LoginSucceed(1003, "Login Complete"),
    LogoutSucceed(1004, "Logout Complete"),

    QuerySucceed(1005, "Query Complete"),

    ServerOverload(1010, "Server Overload"),
    AttackSuspicion(1011,"Attack Suspicion"),
    InvalidInput(1012, "Invalid Input");

    public final int status;
    public final String msg;

    private Result(int status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    @Override
    public String toString() {
        return String.format("Status: %d, Description: %s", status, msg);
    }
}
