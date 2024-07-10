package com.example.student.student;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Permission {

    TA_READ("ta:read"),
    TA_UPDATE("ta:update"),
   TA_DELETE("ta:delete"),
    TA_CREATE("ta:create"),
    RA_READ("ra:read"),


;

    @Getter
    private final String permission;
}
