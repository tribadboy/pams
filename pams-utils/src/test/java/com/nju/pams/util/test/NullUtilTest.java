package com.nju.pams.util.test;

import org.junit.Assert;
import org.junit.Test;

import com.nju.pams.util.NullUtil;

/**
 * @author yueyang.yang
 * date : 2017-03-05
 */
public class NullUtilTest {
	
	@Test
	public void testNotNullProcess() {
		Integer k = null;
		Assert.assertEquals(Integer.valueOf(0), NullUtil.notNullProcess(k));
		Assert.assertEquals(Integer.valueOf(2), NullUtil.notNullProcess(k = 2));
	}
}
