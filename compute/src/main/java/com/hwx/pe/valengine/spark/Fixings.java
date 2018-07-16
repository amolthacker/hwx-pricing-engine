package com.hwx.pe.valengine.spark;

import org.quantlib.Date;
import org.quantlib.IborIndex;
import org.quantlib.Month;

public class Fixings {

    private Fixings(){}

    static void add(IborIndex index)
    {
        index.addFixing(new Date(23, Month.December, 2013), 0.0d);
        index.addFixing(new Date(24, Month.June, 2014), 0.0d);
        index.addFixing(new Date(23, Month.December, 2014), 0.0d);
        index.addFixing(new Date(24, Month.June, 2015), 0.0d);
        index.addFixing(new Date(23, Month.December, 2015), 0.0d);
        index.addFixing(new Date(23, Month.June, 2016), 0.0d);
        index.addFixing(new Date(23, Month.December, 2016), 0.0d);
        index.addFixing(new Date(26, Month.June, 2017), 0.0d);
        index.addFixing(new Date(22, Month.December, 2017), 0.0d);
        index.addFixing(new Date(25, Month.June, 2018), 0.0d);
        index.addFixing(new Date(24, Month.December, 2018), 0.0d);
        index.addFixing(new Date(24, Month.June, 2019), 0.0d);
        index.addFixing(new Date(23, Month.December, 2019), 0.0d);
        index.addFixing(new Date(24, Month.June, 2020), 0.0d);
        index.addFixing(new Date(23, Month.December, 2020), 0.0d);
        index.addFixing(new Date(24, Month.June, 2021), 0.0d);
        index.addFixing(new Date(23, Month.December, 2021), 0.0d);
        index.addFixing(new Date(23, Month.June, 2022), 0.0d);
        index.addFixing(new Date(23, Month.December, 2022), 0.0d);
        index.addFixing(new Date(26, Month.June, 2023), 0.0d);
        index.addFixing(new Date(22, Month.December, 2023), 0.0d);
        index.addFixing(new Date(24, Month.June, 2024), 0.0d);
        index.addFixing(new Date(23, Month.December, 2024), 0.0d);
        index.addFixing(new Date(24, Month.June, 2025), 0.0d);
        index.addFixing(new Date(23, Month.December, 2025), 0.0d);
        index.addFixing(new Date(23, Month.June, 2028), 0.0d);
        index.addFixing(new Date(22, Month.December, 2028), 0.0d);
        index.addFixing(new Date(24, Month.June, 2033), 0.0d);
        index.addFixing(new Date(23, Month.December, 2033), 0.0d);
        index.addFixing(new Date(24, Month.June, 2038), 0.0d);
        index.addFixing(new Date(23, Month.December, 2038), 0.0d);
        index.addFixing(new Date(24, Month.June, 2043), 0.0d);
        index.addFixing(new Date(23, Month.December, 2043), 0.0d);
        index.addFixing(new Date(24, Month.June, 2048), 0.0d);
        index.addFixing(new Date(23, Month.December, 2048), 0.0d);
        index.addFixing(new Date(24, Month.June, 2053), 0.0d);
        index.addFixing(new Date(23, Month.December, 2053), 0.0d);
        index.addFixing(new Date(24, Month.June, 2058), 0.0d);
        index.addFixing(new Date(23, Month.December, 2058), 0.0d);
        index.addFixing(new Date(25, Month.June, 2063), 0.0d);
        index.addFixing(new Date(24, Month.December, 2063), 0.0d);


    }

}
