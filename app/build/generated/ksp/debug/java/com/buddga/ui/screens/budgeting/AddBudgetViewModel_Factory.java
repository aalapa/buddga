package com.buddga.ui.screens.budgeting;

import com.buddga.domain.repository.BudgetRepository;
import com.buddga.domain.repository.CategoryRepository;
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
public final class AddBudgetViewModel_Factory implements Factory<AddBudgetViewModel> {
  private final Provider<BudgetRepository> budgetRepositoryProvider;

  private final Provider<CategoryRepository> categoryRepositoryProvider;

  public AddBudgetViewModel_Factory(Provider<BudgetRepository> budgetRepositoryProvider,
      Provider<CategoryRepository> categoryRepositoryProvider) {
    this.budgetRepositoryProvider = budgetRepositoryProvider;
    this.categoryRepositoryProvider = categoryRepositoryProvider;
  }

  @Override
  public AddBudgetViewModel get() {
    return newInstance(budgetRepositoryProvider.get(), categoryRepositoryProvider.get());
  }

  public static AddBudgetViewModel_Factory create(
      Provider<BudgetRepository> budgetRepositoryProvider,
      Provider<CategoryRepository> categoryRepositoryProvider) {
    return new AddBudgetViewModel_Factory(budgetRepositoryProvider, categoryRepositoryProvider);
  }

  public static AddBudgetViewModel newInstance(BudgetRepository budgetRepository,
      CategoryRepository categoryRepository) {
    return new AddBudgetViewModel(budgetRepository, categoryRepository);
  }
}
