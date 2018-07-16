package com.hwx.pe.valengine.spark;

import java.util.Arrays;


public class MockValEngine {

    public static void main(String[] args) throws Exception {
        if(args.length < 1 || args.length > 2){
            usage();
        } else {
            ValMetric metric = ValMetric.FwdRate;
            int numTrades = 10;
            try {
                metric    = ValMetric.valueOf(ValMetric.class, args[0]);
                if(args.length == 2) {
                    numTrades = Integer.parseInt(args[1]);
                }
            } catch(Exception e){
                System.err.println("Cannot process args " + e.getMessage());
                usage();
            }
            System.out.println(mockAgg(metric, numTrades));
        }
    }

    private static void usage(){
        System.err.println("Usage: java -jar -Djava.library.path=<path-to-QuantLib.jar> mockvalengine-0.1.0.jar <metric" + Arrays.toString(ValMetric.values()) + "> [numTrades(default:10)]");
        System.exit(1);
    }

    private static double mockAgg(ValMetric metric, int numTrades){
        double agg = 0.0d;
        for(int i = 0; i < numTrades; i++){
            switch (metric){
                case FwdRate:  agg += Pricer.computeFRASpot(); break;
                case OptionPV: agg += Pricer.computeEquityOptionNPV(); break;
                case NPV:      agg += Pricer.computeSwapNPV(); break;
                default:       agg += 0.0d;
            }
        }
        return agg / numTrades;
    }
}
