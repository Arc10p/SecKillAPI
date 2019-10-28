package com.study.conchighperf.MQ.MQPOJO;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderAction {
    private int userid;
    private int skuid;
}
