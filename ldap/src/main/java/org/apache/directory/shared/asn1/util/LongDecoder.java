/*
 *  Licensed to the Apache Software Foundation (ASF) under one
 *  or more contributor license agreements.  See the NOTICE file
 *  distributed with this work for additional information
 *  regarding copyright ownership.  The ASF licenses this file
 *  to you under the Apache License, Version 2.0 (the
 *  "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *  
 *    http://www.apache.org/licenses/LICENSE-2.0
 *  
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License. 
 *  
 */
package org.apache.directory.shared.asn1.util;


import org.apache.directory.shared.asn1.ber.tlv.Value;
import org.apache.directory.shared.i18n.I18n;


/**
 * Parse and decode a Long value.
 * 
 * @author <a href="mailto:dev@directory.apache.org">Apache Directory Project</a>
 */
public final class LongDecoder
{
    /** A mask used to get only the necessary bytes */
    private static final long[] MASK = new long[]
        { 0x00000000000000FFL, 0x000000000000FFFFL, 0x0000000000FFFFFFL, 0x00000000FFFFFFFFL, 0x000000FFFFFFFFFFL,
            0x0000FFFFFFFFFFFFL, 0x00FFFFFFFFFFFFFFL, 0xFFFFFFFFFFFFFFFFL };


    /**
     * This is a helper class, there is no reason to define a public constructor for it.
     */
    private LongDecoder()
    {
        // Do nothing
    }


    /**
     * Parse a byte buffer and send back an long, controlling that this number
     * is in a specified interval.
     * 
     * @param value The byte buffer to parse
     * @param min Lowest value allowed, included
     * @param max Highest value allowed, included
     * @return An integer
     * @throws LongDecoderException Thrown if the byte stream does not contains an integer
     */
    public static long parse( Value value, long min, long max ) throws LongDecoderException
    {
        long result = 0;

        byte[] bytes = value.getData();

        if ( ( bytes == null ) || ( bytes.length == 0 ) )
        {
            throw new LongDecoderException( I18n.err( I18n.ERR_00039_0_BYTES_LONG_LONG ) );
        }

        if ( bytes.length > 8 )
        {
            throw new LongDecoderException( I18n.err( I18n.ERR_00039_0_BYTES_LONG_LONG ) );
        }

        for ( int i = 0; ( i < bytes.length ) && ( i < 9 ); i++ )
        {
            result = ( result << 8 ) | ( bytes[i] & 0x00FF );
        }

        if ( ( bytes[0] & 0x80 ) == 0x80 )
        {
            result = -( ( ( ~result ) + 1 ) & MASK[bytes.length - 1] );
        }

        if ( ( result >= min ) && ( result <= max ) )
        {
            return result;
        }
        else
        {
            throw new LongDecoderException( I18n.err( I18n.ERR_00038_VALUE_NOT_IN_RANGE, min, max ) );
        }
    }


    /**
     * Parse a byte buffer and send back an integer
     * 
     * @param value The byte buffer to parse
     * @return An integer
     * @throws LongDecoderException Thrown if the byte stream does not contains an integer
     */
    public static long parse( Value value ) throws LongDecoderException
    {
        return parse( value, Long.MIN_VALUE, Long.MAX_VALUE );
    }
}