/*
 * Copyright (c) 2006 jPOS.org
 *
 * See terms of license at http://jpos.org/license.html
 *
 */

package org.jpos.iso;

import junit.framework.TestCase;

/**
 * @author joconnor
 */
public class IFB_LLLBINARYTest extends TestCase
{
    public void testPack() throws Exception
    {
        ISOBinaryField field = new ISOBinaryField(12, new byte[] {0x12, 0x34});
        IFB_LLLBINARY packager = new IFB_LLLBINARY(100, "Should be 1234");
        TestUtils.assertEquals(new byte[] {0x00, 0x02, 0x12, 0x34}, packager.pack(field));
    }

    public void testUnpack() throws Exception
    {
        byte[] raw = new byte[] {0x00, 0x02, 0x12, 0x34};
        IFB_LLLBINARY packager = new IFB_LLLBINARY(100, "Should be 1234");
        ISOBinaryField field = new ISOBinaryField(12);
        packager.unpack(field, raw, 0);
        TestUtils.assertEquals(new byte[] {0x12, 0x34}, (byte[])field.getValue());
    }

    public void testReversability() throws Exception
    {
        byte[] origin = new byte[] {0x12, 0x34, 0x56, 0x78};
        ISOBinaryField f = new ISOBinaryField(12, origin);
        IFB_LLLBINARY packager = new IFB_LLLBINARY(100, "Should be 12345678");

        ISOBinaryField unpack = new ISOBinaryField(12);
        packager.unpack(unpack, packager.pack(f), 0);
        TestUtils.assertEquals(origin, (byte[])unpack.getValue());
    }
    
    public void testPackGreaterThan16() throws Exception
    {
        ISOBinaryField field = new ISOBinaryField(1, new byte[] {0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07 ,0x08, 0x09, 0x0a, 0x0b, 0x0c, 0x0d, 0x0e, 0x0f, 0x10, 0x11});
        IFB_LLLBINARY packager = new IFB_LLLBINARY(1, "Should be 1234");
        TestUtils.assertEquals(new byte[] {0x00, 0x17, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07 ,0x08, 0x09, 0x0a, 0x0b, 0x0c, 0x0d, 0x0e, 0x0f, 0x10, 0x11}, packager.pack(field));
    }

    public void testUnpackGreaterThan16() throws Exception
    {
        byte[] raw = new byte[] {0x00, 0x17, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07 ,0x08, 0x09, 0x0a, 0x0b, 0x0c, 0x0d, 0x0e, 0x0f, 0x10, 0x11};
        IFB_LLLBINARY packager = new IFB_LLLBINARY(1, "Should be 17 bytes 01 through 11");
        ISOBinaryField field = new ISOBinaryField(1);
        packager.unpack(field, raw, 0);
        TestUtils.assertEquals(new byte[] {0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07 ,0x08, 0x09, 0x0a, 0x0b, 0x0c, 0x0d, 0x0e, 0x0f, 0x10, 0x11}, (byte[])field.getValue());
    }

    public void testReversabilityGreaterThan16() throws Exception
    {
        byte[] origin = new byte[] {0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07 ,0x08, 0x09, 0x0a, 0x0b, 0x0c, 0x0d, 0x0e, 0x0f, 0x10, 0x11};
        ISOBinaryField f = new ISOBinaryField(1, origin);
        IFB_LLLBINARY packager = new IFB_LLLBINARY(1, "Should be x'0102030405060708090a0b0c0d0e0f1011'");

        ISOBinaryField unpack = new ISOBinaryField(12);
        packager.unpack(unpack, packager.pack(f), 0);
        TestUtils.assertEquals(origin, (byte[])unpack.getValue());
    }
}