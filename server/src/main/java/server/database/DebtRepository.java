/*
 * Copyright 2021 Delft University of Technology
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package server.database;

import commons.Debt;
import commons.primaryKeys.DebtKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DebtRepository extends JpaRepository<Debt, DebtKey> {
    @Query(value = "SELECT * FROM DEBT WHERE EVENT_ID = :eid", nativeQuery = true)
    List<Debt> findAllByEventId(@Param("eid") Long eid);


    @Query(value = "SELECT * FROM DEBT " +
            "WHERE (DEBTOR_EVENT_ID = :eid AND DEBTOR_ID = :pid) " +
            "OR (CREDITOR_EVENT_ID = :eid AND CREDITOR_ID = :pid)",
            nativeQuery = true)
    List<Debt> findByEventIdAndParticipantId(@Param("eid") long eid, @Param("pid") long id);
}