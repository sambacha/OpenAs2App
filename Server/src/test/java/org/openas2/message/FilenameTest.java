package org.openas2.message;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Properties;
import java.util.logging.Logger;

import javax.mail.Header;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.ContentDisposition;
import javax.mail.internet.MimeMessage;
// import javax.mail.internet.ParseException;

import org.junit.Test;
import org.openas2.util.IOUtil;

public class FilenameTest {
	
	final Logger log = Logger.getLogger(FilenameTest.class.getName());

	// See https://static.javadoc.io/javax.mail/javax.mail-api/1.6.1/javax/mail/internet/package-summary.html
	public static final String SYS_PROP_STRICT_PARAMS = "mail.mime.parameters.strict";

	@Test
	public void testDummy() {
		// dummy
	}
	
	/*
	 * Once the system property in this test is set, it cannot be unset or changed:
	 * javax.mail will remember the value for all the other tests.
	 */
	// @Test
	public void testFilenameLenient() throws IOException, MessagingException {
		
		if (System.getProperty(SYS_PROP_STRICT_PARAMS) != null) {
			log.warning("Unable to reliably test lenient filename parsing.");
		}
		System.setProperty(SYS_PROP_STRICT_PARAMS, "false");
		String fname = parseFilename();
		assertEquals("Filename from header", "2018-09-18-06:13:16.DUMMY.1234567890.xml.edifactDEADBEEF", fname);
		String fname2 = IOUtil.cleanFilename(fname);
		assertEquals("Filename cleaned    ", "2018-09-18-061316.DUMMY.1234567890.xml.edifactDEADBEEF", fname2);

//		There is no way to undo the system property once it is set and used by javax mail.
//		System.setProperty(SYS_PROP_STRICT_PARAMS, "true");
//		try {
//			fname = parseFilename();
//			throw new AssertionError("Filename parsing should have failed - " + fname);
//		} catch (ParseException pe) {
//			assertTrue("Fail on : in filename", pe.toString().contains("expected ';', got \":\""));
//		}
	}

	String parseFilename() throws IOException, MessagingException {
		
		String fname = null;
		try (InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream("FilenameTestHeaders.txt")) {
			Session ss = Session.getDefaultInstance(new Properties());
			MimeMessage mi = new MimeMessage(ss, in);
			for (Enumeration<Header> e = mi.getAllHeaders(); e.hasMoreElements();) {
				Header h = e.nextElement();
				if ("Content-Disposition".equals(h.getName())) {
					ContentDisposition cd = new ContentDisposition(h.getValue());
					for (Enumeration<String> pn = cd.getParameterList().getNames(); pn.hasMoreElements();) {
						String pname = pn.nextElement();
						if ("filename".equalsIgnoreCase(pname)) {
							fname = cd.getParameter(pname);
						}
					}
				}
			}
		}
		return fname;
	}
	
}
