package org.mule.modules.zipconnector.automation.functional;

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mule.modules.zipconnector.ZipConnector;
import org.mule.tools.devkit.ctf.junit.AbstractTestCase;

public class CompressAsZipTestCases extends AbstractTestCase<ZipConnector> {

	public CompressAsZipTestCases() {
		super(ZipConnector.class);
	}

	@Before
	public void setup() {
		// TODO
	}

	@After
	public void tearDown() {
		// TODO
	}

	@Test
	public void verify() {
		byte[] expected = null;
		Object payload = null;
		org.mule.api.MuleMessage message = null;
		assertEquals(getConnector().compressAsZip(payload, message), expected);
	}

}