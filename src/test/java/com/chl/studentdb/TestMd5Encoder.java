package com.chl.studentdb;

import org.junit.Test;

import com.chl.studentdb.utils.Md5Encoder;

public class TestMd5Encoder {

	@Test
	public void test() {
		String md5 = Md5Encoder.md5Password("19950205");
		System.out.println(md5.equals("c2990a386b1b1aa26b5b5f18cc6a431d"));
	}

}
