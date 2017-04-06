package com.nju.pams.util.test;

import java.math.BigDecimal;

import org.junit.Assert;
import org.junit.Test;

import com.nju.pams.util.BigDecimalUtil;

/**
 * @author yueyang.yang
 * date : 2017-04-05
 */
public class BigDecimalUtilTest {
	
	@Test
	public void testGenerateFormatString() {
		Assert.assertEquals("0.00", BigDecimalUtil.generateFormatString(null));
		Assert.assertEquals("4.30", BigDecimalUtil.generateFormatString(BigDecimal.valueOf(4.3)));
		Assert.assertEquals("2.46", BigDecimalUtil.generateFormatString(BigDecimal.valueOf(2.456)));
		Assert.assertEquals("-9.10", BigDecimalUtil.generateFormatString(BigDecimal.valueOf(-9.1)));	
	}
}
