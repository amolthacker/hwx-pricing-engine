package com.hwx.pe.valengine.spark;

import org.quantlib.Date;
import org.quantlib.DateVector;
import org.quantlib.DoubleVector;
import org.quantlib.Month;


public class Curve {

    private Curve(){}

    static DateVector dateVector(){
        DateVector dateVector = new DateVector();
        dateVector.add(new Date(26, Month.December, 2013));
        dateVector.add(new Date(30, Month.June,     2014));
        dateVector.add(new Date(30, Month.July,     2014));
        dateVector.add(new Date(29, Month.August,   2014));
        dateVector.add(new Date(30, Month.September,2014));
        dateVector.add(new Date(30, Month.October,  2014));
        dateVector.add(new Date(28, Month.November, 2014));
        dateVector.add(new Date(30, Month.December, 2014));
        dateVector.add(new Date(30, Month.January,  2015));
        dateVector.add(new Date(27, Month.February, 2015));
        dateVector.add(new Date(30, Month.March,    2015));
        dateVector.add(new Date(30, Month.April,    2015));
        dateVector.add(new Date(29, Month.May,      2015));
        dateVector.add(new Date(30, Month.June,     2015));
        dateVector.add(new Date(30, Month.December, 2015));
        dateVector.add(new Date(30, Month.December, 2016));
        dateVector.add(new Date(29, Month.December, 2017));
        dateVector.add(new Date(31, Month.December, 2018));
        dateVector.add(new Date(30, Month.December, 2019));
        dateVector.add(new Date(30, Month.December, 2020));
        dateVector.add(new Date(30, Month.December, 2021));
        dateVector.add(new Date(30, Month.December, 2022));
        dateVector.add(new Date(29, Month.December, 2023));
        dateVector.add(new Date(30, Month.December, 2024));
        dateVector.add(new Date(30, Month.December, 2025));
        dateVector.add(new Date(29, Month.December, 2028));
        dateVector.add(new Date(30, Month.December, 2033));
        dateVector.add(new Date(30, Month.December, 2038));
        dateVector.add(new Date(30, Month.December, 2043));
        dateVector.add(new Date(30, Month.December, 2048));
        dateVector.add(new Date(30, Month.December, 2053));
        dateVector.add(new Date(30, Month.December, 2058));
        dateVector.add(new Date(31, Month.December, 2063));
        return dateVector;
    }

    static DoubleVector valueVector(){
        DoubleVector doubleVector = new DoubleVector();
        doubleVector.add(1.0);
        doubleVector.add(0.998022);
        doubleVector.add(0.99771);
        doubleVector.add(0.99739);
        doubleVector.add(0.997017);
        doubleVector.add(0.996671);
        doubleVector.add(0.996337);
        doubleVector.add(0.995921);
        doubleVector.add(0.995522);
        doubleVector.add(0.995157);
        doubleVector.add(0.994706);
        doubleVector.add(0.994248);
        doubleVector.add(0.993805);
        doubleVector.add(0.993285);
        doubleVector.add(0.989614);
        doubleVector.add(0.978541);
        doubleVector.add(0.961973);
        doubleVector.add(0.940868);
        doubleVector.add(0.916831);
        doubleVector.add(0.890805);
        doubleVector.add(0.863413);
        doubleVector.add(0.834987);
        doubleVector.add(0.807111);
        doubleVector.add(0.778332);
        doubleVector.add(0.750525);
        doubleVector.add(0.674707);
        doubleVector.add(0.575192);
        doubleVector.add(0.501258);
        doubleVector.add(0.44131);
        doubleVector.add(0.384733);
        doubleVector.add(0.340425);
        doubleVector.add(0.294694);
        doubleVector.add(0.260792);
        return doubleVector;
    }

}
