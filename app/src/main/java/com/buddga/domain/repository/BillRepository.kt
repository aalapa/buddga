package com.buddga.domain.repository

import com.buddga.domain.model.Bill
import com.buddga.domain.model.BillWithCategory
import kotlinx.coroutines.flow.Flow
import java.math.BigDecimal
import java.time.LocalDate

interface BillRepository {
    fun getAllBills(): Flow<List<BillWithCategory>>
    fun getBillsByDateRange(startDate: LocalDate, endDate: LocalDate): Flow<List<BillWithCategory>>
    fun getUpcomingUnpaidBills(limit: Int = 10): Flow<List<BillWithCategory>>
    fun getTotalUpcomingBills(startDate: LocalDate, endDate: LocalDate): Flow<BigDecimal>
    suspend fun getBillById(id: Long): Bill?
    suspend fun getBillsDueForReminder(startDate: LocalDate, endDate: LocalDate): List<Bill>
    suspend fun addBill(bill: Bill): Long
    suspend fun updateBill(bill: Bill)
    suspend fun markAsPaid(id: Long, isPaid: Boolean)
    suspend fun deleteBill(id: Long)
}
