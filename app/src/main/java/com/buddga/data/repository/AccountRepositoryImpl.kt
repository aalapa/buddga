package com.buddga.data.repository

import com.buddga.data.local.database.dao.AccountDao
import com.buddga.data.mapper.toDomain
import com.buddga.data.mapper.toEntity
import com.buddga.domain.model.Account
import com.buddga.domain.repository.AccountRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.math.BigDecimal
import java.time.LocalDateTime
import java.time.ZoneId
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AccountRepositoryImpl @Inject constructor(
    private val accountDao: AccountDao
) : AccountRepository {

    override fun getAllAccounts(): Flow<List<Account>> =
        accountDao.getAllAccounts().map { entities ->
            entities.map { it.toDomain() }
        }

    override fun getOnBudgetAccounts(): Flow<List<Account>> =
        accountDao.getOnBudgetAccounts().map { entities ->
            entities.map { it.toDomain() }
        }

    override fun getAccountById(id: Long): Flow<Account?> =
        accountDao.getAccountByIdFlow(id).map { it?.toDomain() }

    override fun getTotalOnBudgetBalance(): Flow<BigDecimal> =
        accountDao.getTotalOnBudgetBalance().map { value ->
            BigDecimal(value ?: 0.0)
        }

    override fun getTotalCashBalance(): Flow<BigDecimal> =
        accountDao.getTotalCashBalance().map { value ->
            BigDecimal(value ?: 0.0)
        }

    override suspend fun addAccount(account: Account): Long =
        accountDao.insert(account.toEntity())

    override suspend fun updateAccount(account: Account) =
        accountDao.update(account.copy(updatedAt = LocalDateTime.now()).toEntity())

    override suspend fun updateBalance(id: Long, balance: BigDecimal) {
        val updatedAt = LocalDateTime.now()
            .atZone(ZoneId.systemDefault())
            .toInstant()
            .toEpochMilli()
        accountDao.updateBalance(id, balance.toPlainString(), updatedAt)
    }

    override suspend fun deleteAccount(id: Long) =
        accountDao.deleteById(id)
}
