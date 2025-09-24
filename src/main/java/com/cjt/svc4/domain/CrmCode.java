package com.cjt.svc4.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CrmCode {
    private String depth1Code;
    private String depth1Name;
    private String depth2Code;
    private String depth2Name;
}
