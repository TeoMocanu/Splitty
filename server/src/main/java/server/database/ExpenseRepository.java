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

import commons.Expense;
import commons.primaryKeys.ExpenseKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ExpenseRepository extends JpaRepository<Expense, ExpenseKey> {
    @Query(value = "SELECT * FROM EXPENSE WHERE EVENT_ID = :eid", nativeQuery = true)
    List<Expense> findByEventId(@Param("eid") Long eid);

    @Query(value = "SELECT * FROM EXPENSE E " +
            "WHERE E.EVENT_ID = :eid AND E.PAYER_ID = :pid",
            nativeQuery = true)
    List<Expense> findByEventIdAndPayerId(@Param("eid") Long eid, @Param("pid") Long pid);

    @Query(value = "SELECT E.EVENT_ID, E.EXPENSE_ID, E.DATE, E.PAYER_EVENT_ID, E.PAYER_ID, E.TITLE, E.AMOUNT, E.TYPE FROM EXPENSE AS E " +
            "JOIN EXPENSE_SPLITTER AS ES ON E.EVENT_ID = ES.EXPENSE_EVENT_ID AND E.EXPENSE_ID = ES.EXPENSE_ID " +
            "WHERE E.EVENT_ID = :eid AND ES.SPLITTER_ID = :pid " +
            "GROUP BY E.EXPENSE_ID",
            nativeQuery = true)
    List<Expense> findByEventIdAndDebtorsId(@Param("eid") Long eid, @Param("pid") Long pid);
}
