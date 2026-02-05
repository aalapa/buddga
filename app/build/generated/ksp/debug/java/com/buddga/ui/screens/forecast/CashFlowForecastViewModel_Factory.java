package com.buddga.ui.screens.forecast;

import com.buddga.domain.repository.AccountRepository;
import com.buddga.domain.repository.BillRepository;
import com.buddga.domain.repository.TransactionRepository;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata
@QualifierMetadata
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava"
})
public final class CashFlowForecastViewModel_Factory implements Factory<CashFlowForecastViewModel> {
  private final Provider<TransactionRepository> transactionRepositoryProvider;

  private final Provider<AccountRepository> accountRepositoryProvider;

  private final Provider<BillRepository> billRepositoryProvider;

  public CashFlowForecastViewModel_Factory(
      Provider<TransactionRepository> transactionRepositoryProvider,
      Provider<AccountRepository> accountRepositoryProvider,
      Provider<BillRepository> billRepositoryProvider) {
    this.transactionRepositoryProvider = transactionRepositoryProvider;
    this.accountRepositoryProvider = accountRepositoryProvider;
    this.billRepositoryProvider = billRepositoryProvider;
  }

  @Override
  public CashFlowForecastViewModel get() {
    return newInstance(transactionRepositoryProvider.get(), accountRepositoryProvider.get(), billRepositoryProvider.get());
  }

  public static CashFlowForecastViewModel_Factory create(
      Provider<TransactionRepository> transactionRepositoryProvider,
      Provider<AccountRepository> accountRepositoryProvider,
      Provider<BillRepository> billRepositoryProvider) {
    return new CashFlowForecastViewModel_Factory(transactionRepositoryProvider, accountRepositoryProvider, billRepositoryProvider);
  }

  public static CashFlowForecastViewModel newInstance(TransactionRepository transactionRepository,
      AccountRepository accountRepository, BillRepository billRepository) {
    return new CashFlowForecastViewModel(transactionRepository, accountRepository, billRepository);
  }
}
