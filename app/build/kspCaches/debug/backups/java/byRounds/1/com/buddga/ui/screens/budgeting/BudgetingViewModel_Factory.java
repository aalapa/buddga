package com.buddga.ui.screens.budgeting;

import com.buddga.domain.repository.AccountRepository;
import com.buddga.domain.repository.BudgetRepository;
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
public final class BudgetingViewModel_Factory implements Factory<BudgetingViewModel> {
  private final Provider<BudgetRepository> budgetRepositoryProvider;

  private final Provider<CategoryRepository> categoryRepositoryProvider;

  private final Provider<TransactionRepository> transactionRepositoryProvider;

  private final Provider<AccountRepository> accountRepositoryProvider;

  public BudgetingViewModel_Factory(Provider<BudgetRepository> budgetRepositoryProvider,
      Provider<CategoryRepository> categoryRepositoryProvider,
      Provider<TransactionRepository> transactionRepositoryProvider,
      Provider<AccountRepository> accountRepositoryProvider) {
    this.budgetRepositoryProvider = budgetRepositoryProvider;
    this.categoryRepositoryProvider = categoryRepositoryProvider;
    this.transactionRepositoryProvider = transactionRepositoryProvider;
    this.accountRepositoryProvider = accountRepositoryProvider;
  }

  @Override
  public BudgetingViewModel get() {
    return newInstance(budgetRepositoryProvider.get(), categoryRepositoryProvider.get(), transactionRepositoryProvider.get(), accountRepositoryProvider.get());
  }

  public static BudgetingViewModel_Factory create(
      Provider<BudgetRepository> budgetRepositoryProvider,
      Provider<CategoryRepository> categoryRepositoryProvider,
      Provider<TransactionRepository> transactionRepositoryProvider,
      Provider<AccountRepository> accountRepositoryProvider) {
    return new BudgetingViewModel_Factory(budgetRepositoryProvider, categoryRepositoryProvider, transactionRepositoryProvider, accountRepositoryProvider);
  }

  public static BudgetingViewModel newInstance(BudgetRepository budgetRepository,
      CategoryRepository categoryRepository, TransactionRepository transactionRepository,
      AccountRepository accountRepository) {
    return new BudgetingViewModel(budgetRepository, categoryRepository, transactionRepository, accountRepository);
  }
}
