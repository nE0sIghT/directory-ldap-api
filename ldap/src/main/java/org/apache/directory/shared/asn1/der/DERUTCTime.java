/*
 * Copyright (c) 2000 - 2006 The Legion Of The Bouncy Castle (http://www.bouncycastle.org)
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this
 * software and associated documentation files (the "Software"), to deal in the Software
 * without restriction, including without limitation the rights to use, copy, modify,
 * merge, publish, distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to the following
 * conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all copies
 * or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR
 * PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE
 * FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER
 * DEALINGS IN THE SOFTWARE.
 * 
 */

package org.apache.directory.shared.asn1.der;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;


/**
 * DER UTC time object.
 */
public class DERUTCTime extends DERString
{
    private static final TimeZone UTC_TIME_ZONE = TimeZone.getTimeZone( "UTC" );

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat( "yyMMddHHmmss'Z'" );

    static
    {
        dateFormat.setTimeZone( UTC_TIME_ZONE );
    }


    /**
     * Basic DERObject constructor.
     */
    public DERUTCTime(byte[] value)
    {
        super( UTC_TIME, value );
    }


    /**
     * Static factory method, type-conversion operator.
     */
    public static DERUTCTime valueOf( Date date )
    {
        String dateString = null;

        synchronized ( dateFormat )
        {
            dateString = dateFormat.format( date );
        }

        byte[] bytes = stringToByteArray( dateString );

        return new DERUTCTime( bytes );
    }


    /**
     * Lazy accessor
     * 
     * @return Date representation of this DER UTC Time
     * @throws ParseException
     */
    public Date getDate() throws ParseException
    {
        String string = byteArrayToString( value );

        synchronized ( dateFormat )
        {
            return dateFormat.parse( string );
        }
    }
}