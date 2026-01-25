package com.buddga.ui.screens.bills;

import com.buddga.domain.repository.BillRepository;
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
public final class AddBillViewModel_Factory implements Factory<AddBillViewModel> {
  private final Provider<BillRepository> billRepositoryProvider;

  private final Provider<CategoryRepository> categoryRepositoryProvider;

  public AddBillViewModel_Factory(Provider<BillRepository> billRepositoryProvider,
      Provider<CategoryRepository> categoryRepositoryProvider) {
    this.billRepositoryProvider = billRepositoryProvider;
    this.categoryRepositoryProvider = categoryRepositoryProvider;
  }

  @Override
  public AddBillViewModel get() {
    return newInstance(billRepositoryProvider.get(), categoryRepositoryProvider.get());
  }

  public static AddBillViewModel_Factory create(Provider<BillRepository> billRepositoryProvider,
      Provider<CategoryRepository> categoryRepositoryProvider) {
    return new AddBillViewModel_Factory(billRepositoryProvider, categoryRepositoryProvider);
  }

  public static AddBillViewModel newInstance(BillRepository billRepository,
      CategoryRepository categoryRepository) {
    return new AddBillViewModel(billRepository, categoryRepository);
  }
}
