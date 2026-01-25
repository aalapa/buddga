package com.buddga.data.repository

import com.buddga.data.local.database.dao.BillDao
import com.buddga.data.mapper.toDomain
import com.buddga.data.mapper.toEntity
import com.buddga.domain.model.Bill
import com.buddga.domain.model.BillWithCategory
import com.buddga.domain.repository.BillRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.math.BigDecimal
import java.time.LocalDate
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BillRepositoryImpl @Inject constructor(
    private val billDao: BillDao
) : BillRepository {

    override fun getAllBills(): Flow<List<BillWithCategory>> =
        billDao.getAllBillsWithCategory().map { entities ->
            entities.map { it.toDomain() }
        }

    override fun getBillsByDateRange(
        startDate: LocalDate,
        endDate: LocalDate
    ): Flow<List<BillWithCategory>> =
        billDao.getBillsByDateRange(
            startDate.toEpochDay(),
            endDate.toEpochDay()
        ).map { entities ->
            entities.map { it.toDomain() }
        }

    override fun getUpcomingUnpaidBills(limit: Int): Flow<List<BillWithCategory>> =
        billDao.getUpcomingUnpaidBills(
            LocalDate.now().toEpochDay(),
            limit
        ).map { entities ->
            entities.map { it.toDomain() }
        }

    override fun getTotalUpcomingBills(startDate: LocalDate, endDate: LocalDate): Flow<BigDecimal> =
        billDao.getTotalUpcomingBills(
            startDate.toEpochDay(),
            endDate.toEpochDay()
        ).map { value ->
            BigDecimal(value ?: 0.0)
        }

    override suspend fun getBillById(id: Long): Bill? =
        billDao.getBillById(id)?.toDomain()

    override suspend fun getBillsDueForReminder(
        startDate: LocalDate,
        endDate: LocalDate
    ): List<Bill> =
        billDao.getBillsDueForReminder(
            startDate.toEpochDay(),
            endDate.toEpochDay()
        ).map { it.toDomain() }

    override suspend fun addBill(bill: Bill): Long =
        billDao.insert(bill.toEntity())

    override suspend fun updateBill(bill: Bill) =
        billDao.update(bill.toEntity())

    override suspend fun markAsPaid(id: Long, isPaid: Boolean) =
        billDao.updatePaidStatus(id, isPaid)

    override suspend fun deleteBill(id: Long) =
        billDao.deleteById(id)
}
