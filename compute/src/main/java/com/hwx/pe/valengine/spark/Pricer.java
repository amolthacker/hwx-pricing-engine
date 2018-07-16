package com.hwx.pe.valengine.spark;

import org.quantlib.*;

import java.util.List;
import java.util.ArrayList;


public class Pricer {

    private Pricer(){}

    static {
        try {
            System.loadLibrary("QuantLibJNI");
            System.out.println("Loaded QuantLibJNI");
        } catch (RuntimeException e) {
            System.err.println(e.getMessage());
        }
    }

    static double computeSwapNPV()
    {
        Date todaysDate     = new Date(26, Month.December, 2013);
        Date startDate      = todaysDate;
        Date maturityDate   = new Date(26, Month.December, 2018);
        Settings.instance().setEvaluationDate(todaysDate);

        Schedule fixedSchedule = new Schedule(startDate,
                maturityDate, new Period("6m"), new TARGET(),
                BusinessDayConvention.ModifiedFollowing,
                BusinessDayConvention.ModifiedFollowing,
                DateGeneration.Rule.Forward, false);

        Schedule floatingSchedule = new Schedule(startDate,
                maturityDate, new Period("6m"), new TARGET(),
                BusinessDayConvention.ModifiedFollowing,
                BusinessDayConvention.ModifiedFollowing,
                DateGeneration.Rule.Forward, false);

        DiscountCurve discountCurve = new DiscountCurve(Curve.dateVector(),
                Curve.valueVector(), new Actual360(), new TARGET());

        RelinkableYieldTermStructureHandle forecastTermStructure = new RelinkableYieldTermStructureHandle();
        IborIndex index = new Euribor(new Period("6m"),forecastTermStructure);


        Fixings.add(index);

        Swap swap = new VanillaSwap(VanillaSwap.Receiver, 10000000, fixedSchedule,0.02, new Actual360(),
                floatingSchedule, index, 0, new Actual360());

        RelinkableYieldTermStructureHandle discountTermStructure = new RelinkableYieldTermStructureHandle();

        PricingEngine swapEngine = new DiscountingSwapEngine(discountTermStructure);
        swap.setPricingEngine(swapEngine);

        discountTermStructure.linkTo(discountCurve);
        forecastTermStructure.linkTo(discountCurve);

        return swap.NPV();
    }

    // modified example by Luigi Ballabio (https://github.com/lballabio)
    static double computeFRASpot()
    {
        Date todaysDate = new Date(1, Month.May, 2017);
        Settings.instance().setEvaluationDate(todaysDate);
        Date settlementDate = new Date(26, Month.May, 2017);
        Date maturityDate = new Date(24, Month.August, 2017);

        Position.Type type = Position.Type.Long;
        double strike = 0.05;
        double notional = 100.0;
        double riskFreeRate = 0.09;
        DayCounter dayCounter = new Actual360();

        YieldTermStructureHandle flatTermStructure =
                new YieldTermStructureHandle(new FlatForward(
                        settlementDate, riskFreeRate, dayCounter));
        IborIndex euribor3m = new Euribor3M(flatTermStructure);

        Date fixingDate = new Date(27, Month.April, 2017);
        euribor3m.addFixing(fixingDate, 0.04);

        ForwardRateAgreement myFra =
                new ForwardRateAgreement(todaysDate, maturityDate,
                        type, strike, notional, euribor3m, flatTermStructure);

        return myFra.spotValue();
    }

    // modified example by Luigi Ballabio (https://github.com/lballabio)
    static double computeEquityOptionNPV(){

        // compute result vector
        List<Double> resultSet = new ArrayList<Double>();
        // option
        Option.Type type = Option.Type.Put;
        double strike = 40.0;
        double underlying = 36.0;
        double riskFreeRate = 0.06;
        double dividendYield = 0.00;
        double volatility = 0.2;

        Date todaysDate = new Date(15, Month.May, 1998);
        Date settlementDate = new Date(17, Month.May, 1998);
        Settings.instance().setEvaluationDate(todaysDate);

        Date maturity = new Date(17, Month.May, 1999);
        DayCounter dayCounter = new Actual365Fixed();
        Calendar calendar = new TARGET();

        // define European exercise
        DateVector exerciseDates = new DateVector();
        for (int i = 1; i <= 4; i++) {
            Date forward = settlementDate.add(new Period(3*i, TimeUnit.Months));
            exerciseDates.add(forward);
        }
        Exercise europeanExercise = new EuropeanExercise(maturity);

        // define the underlying asset and the yield/dividend/volatility curves
        QuoteHandle underlyingH = new QuoteHandle(new SimpleQuote(underlying));
        YieldTermStructureHandle flatTermStructure =
                new YieldTermStructureHandle(new FlatForward(
                        settlementDate, riskFreeRate, dayCounter));
        YieldTermStructureHandle flatDividendYield =
                new YieldTermStructureHandle(new FlatForward(
                        settlementDate, dividendYield, dayCounter));
        BlackVolTermStructureHandle flatVolatility =
                new BlackVolTermStructureHandle(new BlackConstantVol(
                        settlementDate, calendar, volatility, dayCounter));

        BlackScholesMertonProcess stochasticProcess =
                new BlackScholesMertonProcess(underlyingH,
                        flatDividendYield,
                        flatTermStructure,
                        flatVolatility);

        // option payoff
        Payoff payoff = new PlainVanillaPayoff(type, strike);

        // European option
        VanillaOption europeanOption =
                new VanillaOption(payoff, europeanExercise);


        // Price the EuropeanOption with different methods

        // Black-Scholes
        europeanOption.setPricingEngine(
                new AnalyticEuropeanEngine(stochasticProcess));
        resultSet.add(europeanOption.NPV());

        // Integral
        europeanOption.setPricingEngine(new IntegralEngine(stochasticProcess));
        resultSet.add(europeanOption.NPV());

        // Finite differences
        int timeSteps = 801;
        europeanOption.setPricingEngine(new FDEuropeanEngine(stochasticProcess,
                timeSteps,
                timeSteps-1));
        resultSet.add(europeanOption.NPV());

        // Binomial method
        europeanOption.setPricingEngine(
                new BinomialVanillaEngine(stochasticProcess,
                        "JarrowRudd", timeSteps));
        resultSet.add(europeanOption.NPV());

        // Additive EQP Binomial Tree
        europeanOption.setPricingEngine(
                new BinomialVanillaEngine(stochasticProcess,
                        "AdditiveEQPBinomialTree", timeSteps));
        resultSet.add(europeanOption.NPV());

        // Bimal Joshi
        europeanOption.setPricingEngine(
                new BinomialVanillaEngine(stochasticProcess,
                        "Joshi4", timeSteps));
        resultSet.add(europeanOption.NPV());

        // Monte Carlo Method
        timeSteps = 1;
        int mcSeed = 42;
        int nSamples = 32768; // 2^15
        int maxSamples = 1048576; // 2^20

        europeanOption.setPricingEngine(
                new MCEuropeanEngine(stochasticProcess,
                        "PseudoRandom", timeSteps,
                        QuantLib.nullInt(),
                        false, false,
                        nSamples, 0.02, maxSamples, mcSeed));
        resultSet.add(europeanOption.NPV());


        europeanOption.setPricingEngine(
                new MCEuropeanEngine(stochasticProcess,
                        "LowDiscrepancy", timeSteps,
                        QuantLib.nullInt(),
                        false, false,
                        nSamples, 0.02, maxSamples, mcSeed));
        resultSet.add(europeanOption.NPV());


        double resultSum = 0.0d;
        for(Double result : resultSet){
            resultSum += result;
        }

        return resultSet.size() > 0 ? resultSum / resultSet.size() : 0.0d;
    }

}
