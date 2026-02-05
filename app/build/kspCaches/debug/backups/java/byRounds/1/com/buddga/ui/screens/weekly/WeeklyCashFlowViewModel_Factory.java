package com.buddga.ui.screens.weekly;

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
public final class WeeklyCashFlowViewModel_Factory implements Factory<WeeklyCashFlowViewModel> {
  private final Provider<TransactionRepository> transactionRepositoryProvider;

  public WeeklyCashFlowViewModel_Factory(
      Provider<TransactionRepository> transactionRepositoryProvider) {
    this.transactionRepositoryProvider = transactionRepositoryProvider;
  }

  @Override
  public WeeklyCashFlowViewModel get() {
    return newInstance(transactionRepositoryProvider.get());
  }

  public static WeeklyCashFlowViewModel_Factory create(
      Provider<TransactionRepository> transactionRepositoryProvider) {
    return new WeeklyCashFlowViewModel_Factory(transactionRepositoryProvider);
  }

  public static WeeklyCashFlowViewModel newInstance(TransactionRepository transactionRepository) {
    return new WeeklyCashFlowViewModel(transactionRepository);
  }
}
