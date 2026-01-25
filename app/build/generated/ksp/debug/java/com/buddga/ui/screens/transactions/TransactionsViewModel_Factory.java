package com.buddga.ui.screens.transactions;

import com.buddga.domain.repository.AccountRepository;
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
public final class TransactionsViewModel_Factory implements Factory<TransactionsViewModel> {
  private final Provider<TransactionRepository> transactionRepositoryProvider;

  private final Provider<AccountRepository> accountRepositoryProvider;

  public TransactionsViewModel_Factory(
      Provider<TransactionRepository> transactionRepositoryProvider,
      Provider<AccountRepository> accountRepositoryProvider) {
    this.transactionRepositoryProvider = transactionRepositoryProvider;
    this.accountRepositoryProvider = accountRepositoryProvider;
  }

  @Override
  public TransactionsViewModel get() {
    return newInstance(transactionRepositoryProvider.get(), accountRepositoryProvider.get());
  }

  public static TransactionsViewModel_Factory create(
      Provider<TransactionRepository> transactionRepositoryProvider,
      Provider<AccountRepository> accountRepositoryProvider) {
    return new TransactionsViewModel_Factory(transactionRepositoryProvider, accountRepositoryProvider);
  }

  public static TransactionsViewModel newInstance(TransactionRepository transactionRepository,
      AccountRepository accountRepository) {
    return new TransactionsViewModel(transactionRepository, accountRepository);
  }
}
