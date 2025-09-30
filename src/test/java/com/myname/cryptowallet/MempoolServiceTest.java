package com.myname.cryptowallet;

import com.myname.cryptowallet.model.FeePriority;
import com.myname.cryptowallet.model.Transaction;
import com.myname.cryptowallet.service.MempoolService;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.List;

/**
 * Tests simples pour MempoolService.
 */
public class MempoolServiceTest {

    @Test
    public void mempool_shouldBeSortedByFeeDesc() {
        MempoolService svc = new MempoolService();

        Transaction slow = new Transaction("a1", "b1", new BigDecimal("0.1"), FeePriority.ECONOMIQUE);
        Transaction medium = new Transaction("a2", "b2", new BigDecimal("0.1"), FeePriority.STANDARD);
        Transaction fast = new Transaction("a3", "b3", new BigDecimal("0.1"), FeePriority.RAPIDE);

        svc.addTransaction(slow);
        svc.addTransaction(medium);
        svc.addTransaction(fast);

        List<Transaction> sorted = svc.getSortedMempool();
        Assert.assertEquals(FeePriority.RAPIDE, sorted.get(0).getPriority());
        Assert.assertEquals(FeePriority.STANDARD, sorted.get(1).getPriority());
        Assert.assertEquals(FeePriority.ECONOMIQUE, sorted.get(2).getPriority());
    }
}



