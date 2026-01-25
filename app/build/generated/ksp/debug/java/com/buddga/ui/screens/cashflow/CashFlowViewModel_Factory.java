package com.buddga.ui.screens.cashflow;

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
public final class CashFlowViewModel_Factory implements Factory<CashFlowViewModel> {
  private final Provider<TransactionRepository> transactionRepositoryProvider;

  public CashFlowViewModel_Factory(Provider<TransactionRepository> transactionRepositoryProvider) {
    this.transactionRepositoryProvider = transactionRepositoryProvider;
  }

  @Override
  public CashFlowViewModel get() {
    return newInstance(transactionRepositoryProvider.get());
  }

  public static CashFlowViewModel_Factory create(
      Provider<TransactionRepository> transactionRepositoryProvider) {
    return new CashFlowViewModel_Factory(transactionRepositoryProvider);
  }

  public static CashFlowViewModel newInstance(TransactionRepository transactionRepository) {
    return new CashFlowViewModel(transactionRepository);
  }
}
