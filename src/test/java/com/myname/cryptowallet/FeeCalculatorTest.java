package com.myname.cryptowallet;

import com.myname.cryptowallet.service.FeeCalculator;
import org.junit.Assert;
import org.junit.Test;

/**
 * Tests simples pour FeeCalculator.
 */
public class FeeCalculatorTest {

    @Test
    public void estimateFee_shouldReturnZeroByDefault() {
        FeeCalculator calc = new FeeCalculator();
        double fee = calc.estimateFee();
        Assert.assertEquals(0.0, fee, 0.00001);
    }
}



