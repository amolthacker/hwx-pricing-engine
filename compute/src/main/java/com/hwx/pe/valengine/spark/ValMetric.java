package com.hwx.pe.valengine.spark;


public enum ValMetric {

    FwdRate,
    OptionPV,
    NPV;

    public static ValMetric getRandom() {
        return values()[(int) (Math.random() * values().length)];
    }

}
