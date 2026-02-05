package com.buddga.ui.screens.reports;

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
public final class ReportsViewModel_Factory implements Factory<ReportsViewModel> {
  private final Provider<TransactionRepository> transactionRepositoryProvider;

  public ReportsViewModel_Factory(Provider<TransactionRepository> transactionRepositoryProvider) {
    this.transactionRepositoryProvider = transactionRepositoryProvider;
  }

  @Override
  public ReportsViewModel get() {
    return newInstance(transactionRepositoryProvider.get());
  }

  public static ReportsViewModel_Factory create(
      Provider<TransactionRepository> transactionRepositoryProvider) {
    return new ReportsViewModel_Factory(transactionRepositoryProvider);
  }

  public static ReportsViewModel newInstance(TransactionRepository transactionRepository) {
    return new ReportsViewModel(transactionRepository);
  }
}
