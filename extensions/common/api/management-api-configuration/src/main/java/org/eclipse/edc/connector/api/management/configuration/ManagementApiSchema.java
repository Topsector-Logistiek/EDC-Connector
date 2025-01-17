/*
 *  Copyright (c) 2023 Bayerische Motoren Werke Aktiengesellschaft (BMW AG)
 *
 *  This program and the accompanying materials are made available under the
 *  terms of the Apache License, Version 2.0 which is available at
 *  https://www.apache.org/licenses/LICENSE-2.0
 *
 *  SPDX-License-Identifier: Apache-2.0
 *
 *  Contributors:
 *       Bayerische Motoren Werke Aktiengesellschaft (BMW AG) - initial API and implementation
 *
 */

package org.eclipse.edc.connector.api.management.configuration;

import io.swagger.v3.oas.annotations.media.Schema;
import org.eclipse.edc.connector.contract.spi.types.agreement.ContractAgreement;
import org.eclipse.edc.connector.contract.spi.types.negotiation.ContractNegotiation;
import org.eclipse.edc.spi.types.domain.DataAddress;
import org.eclipse.edc.spi.types.domain.callback.CallbackAddress;

import java.util.List;
import java.util.Set;

import static org.eclipse.edc.connector.contract.spi.types.negotiation.ContractNegotiation.CONTRACT_NEGOTIATION_TYPE;
import static org.eclipse.edc.jsonld.spi.JsonLdKeywords.ID;
import static org.eclipse.edc.jsonld.spi.JsonLdKeywords.TYPE;

/**
 * Schema records that are shared between multiple management modules
 */
public interface ManagementApiSchema {

    @Schema(name = "ContractAgreement", example = ContractAgreementSchema.CONTRACT_AGREEMENT_EXAMPLE)
    record ContractAgreementSchema(
            @Schema(name = TYPE, example = ContractAgreement.CONTRACT_AGREEMENT_TYPE)
            String ldType,
            @Schema(name = ID)
            String id,
            String providerId,
            String consumerId,
            long contractSigningDate,
            String assetId,
            PolicySchema policy
    ) {
        public static final String CONTRACT_AGREEMENT_EXAMPLE = """
                {
                    "@context": { "@vocab": "https://w3id.org/edc/v0.0.1/ns/" },
                    "@type": "https://w3id.org/edc/v0.0.1/ns/ContractAgreement",
                    "@id": "negotiation-id",
                    "providerId": "provider-id",
                    "consumerId": "consumer-id",
                    "assetId": "asset-id",
                    "contractSigningDate": 1688465655,
                    "policy": {
                        "@context": "http://www.w3.org/ns/odrl.jsonld",
                        "@type": "Set",
                        "@id": "offer-id",
                        "permission": [{
                            "target": "asset-id",
                            "action": "display"
                        }]
                    }
                }
                """;
    }

    @Schema(name = "CallbackAddress")
    record CallbackAddressSchema(
            @Schema(name = TYPE, example = CallbackAddress.CALLBACKADDRESS_TYPE)
            String type,
            String uri,
            Set<String> events,
            boolean transactional,
            String authKey,
            String authCodeId
    ) {

    }

    @Schema(name = "DataAddress", additionalProperties = Schema.AdditionalPropertiesValue.TRUE)
    record DataAddressSchema(
            @Schema(name = TYPE, example = DataAddress.EDC_DATA_ADDRESS_TYPE)
            String type,
            @Schema(name = "type")
            String typeProperty
    ) {
        public static final String DATA_ADDRESS_EXAMPLE = """
                {
                    "@context": { "@vocab": "https://w3id.org/edc/v0.0.1/ns/" },
                    "@type": "https://w3id.org/edc/v0.0.1/ns/DataAddress",
                    "type": "HttpData",
                    "baseUrl": "http://example.com"
                }
                """;
    }

    @Schema(name = "Policy", description = "ODRL policy", example = PolicySchema.POLICY_EXAMPLE)
    record PolicySchema() {
        public static final String POLICY_EXAMPLE = """
                {
                    "@context": "http://www.w3.org/ns/odrl.jsonld",
                    "@id": "0949ba30-680c-44e6-bc7d-1688cbe1847e",
                    "@type": "odrl:Set",
                    "permission": {
                        "target": "http://example.com/asset:9898.movie",
                        "action": {
                            "type": "http://www.w3.org/ns/odrl/2/use"
                        },
                        "constraint": {
                            "leftOperand": "https://w3id.org/edc/v0.0.1/ns/left",
                            "operator": "eq",
                            "rightOperand": "value"
                        }
                    },
                    "prohibition": [],
                    "obligation": []
                }
                """;
    }

    @Schema(name = "ContractNegotiation", example = ContractNegotiationSchema.CONTRACT_NEGOTIATION_EXAMPLE)
    record ContractNegotiationSchema(
            @Schema(name = TYPE, example = CONTRACT_NEGOTIATION_TYPE)
            String ldType,
            @Schema(name = ID)
            String id,
            ContractNegotiation.Type type,
            String protocol,
            String counterPartyId,
            String counterPartyAddress,
            String state,
            String contractAgreementId,
            String errorDetail,
            List<CallbackAddressSchema> callbackAddresses
    ) {
        public static final String CONTRACT_NEGOTIATION_EXAMPLE = """
                {
                    "@context": { "@vocab": "https://w3id.org/edc/v0.0.1/ns/" },
                    "@type": "https://w3id.org/edc/v0.0.1/ns/ContractNegotiation",
                    "@id": "negotiation-id",
                    "type": "PROVIDER",
                    "protocol": "dataspace-protocol-http",
                    "counterPartyId": "counter-party-id",
                    "counterPartyAddress": "http://counter/party/address",
                    "state": "VERIFIED",
                    "contractAgreementId": "contract:agreement:id",
                    "errorDetail": "eventual-error-detail",
                    "createdAt": 1688465655,
                    "callbackAddresses": [{
                        "transactional": false,
                        "uri": "http://callback/url",
                        "events": ["contract.negotiation", "transfer.process"],
                        "authKey": "auth-key",
                        "authCodeId": "auth-code-id"
                    }]
                }
                """;
    }
}
