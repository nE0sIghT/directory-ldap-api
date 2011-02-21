/*
 *   Licensed to the Apache Software Foundation (ASF) under one
 *   or more contributor license agreements.  See the NOTICE file
 *   distributed with this work for additional information
 *   regarding copyright ownership.  The ASF licenses this file
 *   to you under the Apache License, Version 2.0 (the
 *   "License"); you may not use this file except in compliance
 *   with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing,
 *   software distributed under the License is distributed on an
 *   "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *   KIND, either express or implied.  See the License for the
 *   specific language governing permissions and limitations
 *   under the License.
 *
 */
package org.apache.directory.shared.ldap.extras.extended.ads_impl;


import org.apache.directory.shared.asn1.DecoderException;
import org.apache.directory.shared.ldap.codec.api.ExtendedRequestFactory;
import org.apache.directory.shared.ldap.codec.api.LdapCodecService;
import org.apache.directory.shared.ldap.extras.extended.IStoredProcedureRequest;
import org.apache.directory.shared.ldap.extras.extended.IStoredProcedureResponse;
import org.apache.directory.shared.ldap.extras.extended.StoredProcedureRequest;
import org.apache.directory.shared.ldap.extras.extended.StoredProcedureResponse;


/**
 * An {@link ExtendedRequestFactory} for creating cancel extended request response 
 * pairs.
 *
 * @author <a href="mailto:dev@directory.apache.org">Apache Directory Project</a>
 */
public class StoredProcedureFactory implements ExtendedRequestFactory<IStoredProcedureRequest, IStoredProcedureResponse>
{
    private LdapCodecService codec;
    
    
    /**
     * Creates a new instance of StoredProcedureFactory.
     *
     * @param codec
     */
    public StoredProcedureFactory( LdapCodecService codec )
    {
        this.codec = codec;
    }
    
    
    /**
     * {@inheritDoc}
     */
    public String getOid()
    {
        return IStoredProcedureRequest.EXTENSION_OID;
    }

    
    /**
     * {@inheritDoc}
     */
    public IStoredProcedureRequest newRequest()
    {
        return new StoredProcedureRequestDecorator( codec, new StoredProcedureRequest() );
    }


    /**
     * {@inheritDoc}
     */
    public IStoredProcedureResponse newResponse( byte[] encodedValue ) throws DecoderException
    {
        StoredProcedureResponseDecorator response = new StoredProcedureResponseDecorator( codec, new StoredProcedureResponse() );
        response.setResponseValue( encodedValue );
        return response;
    }
}