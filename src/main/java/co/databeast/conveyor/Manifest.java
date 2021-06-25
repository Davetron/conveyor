/*
 * Classname : co.databeast.conveyor.BuildIdentifier
 *
 * Created on: 25 Jun 2021
 *
 * Copyright (c) 2000-2021 Global Payments, Ltd.
 * Global Payments, The Observatory, 7-11 Sir John Rogerson's Quay, Dublin 2, Ireland
 *
 * All Rights Reserved.
 *
 * This software is the confidential and proprietary information of
 * Global Payments, Ltd. ("Confidential Information"). You shall not
 * disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered into
 * with Global Payments.
 *
 */
package co.databeast.conveyor;

import lombok.Data;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Data
public class Manifest {

    private String buildIdentifier = UUID.randomUUID().toString();

    // Record the version of each item in the build for audit and reproducibility
    private Map<String, String> componentVersions = new ConcurrentHashMap<>();

}
