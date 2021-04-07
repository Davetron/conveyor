/*
 * Classname : co.databeast.conveyor.Conveyor2
 *
 * Created on: 07 Apr 2021
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

import lombok.extern.slf4j.Slf4j;
import org.immutables.value.Value;

import java.util.Collections;
import java.util.List;

@Value.Immutable
public interface ConveyorBelt {

    String getName();

    @Value.Default
    default List<Stage> getStage() {
        return Collections.emptyList();
    }

    default void start() {
        //log.info("starting Conveyor {}", name);
        getStage().forEach(Stage::start);
    }
}
