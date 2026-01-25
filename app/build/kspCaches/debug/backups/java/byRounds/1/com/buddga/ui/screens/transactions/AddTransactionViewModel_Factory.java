package com.buddga.ui.screens.transactions;

import com.buddga.domain.repository.AccountRepository;
import com.buddga.domain.repository.CategoryRepository;
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
public final class AddTransactionViewModel_Factory implements Factory<AddTransactionViewModel> {
  private final Provider<TransactionRepository> transactionRepositoryProvider;

  private final Provider<CategoryRepository> categoryRepositoryProvider;

  private final Provider<AccountRepository> accountRepositoryProvider;

  public AddTransactionViewModel_Factory(
      Provider<TransactionRepository> transactionRepositoryProvider,
      Provider<CategoryRepository> categoryRepositoryProvider,
      Provider<AccountRepository> accountRepositoryProvider) {
    this.transactionRepositoryProvider = transactionRepositoryProvider;
    this.categoryRepositoryProvider = categoryRepositoryProvider;
    this.accountRepositoryProvider = accountRepositoryProvider;
  }

  @Override
  public AddTransactionViewModel get() {
    return newInstance(transactionRepositoryProvider.get(), categoryRepositoryProvider.get(), accountRepositoryProvider.get());
  }

  public static AddTransactionViewModel_Factory create(
      Provider<TransactionRepository> transactionRepositoryProvider,
      Provider<CategoryRepository> categoryRepositoryProvider,
      Provider<AccountRepository> accountRepositoryProvider) {
    return new AddTransactionViewModel_Factory(transactionRepositoryProvider, categoryRepositoryProvider, accountRepositoryProvider);
  }

  public static AddTransactionViewModel newInstance(TransactionRepository transactionRepository,
      CategoryRepository categoryRepository, AccountRepository accountRepository) {
    return new AddTransactionViewModel(transactionRepository, categoryRepository, accountRepository);
  }
}
