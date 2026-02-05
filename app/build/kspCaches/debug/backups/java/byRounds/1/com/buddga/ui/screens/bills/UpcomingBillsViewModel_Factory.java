package com.buddga.ui.screens.bills;

import com.buddga.domain.repository.AccountRepository;
import com.buddga.domain.repository.BillRepository;
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
public final class UpcomingBillsViewModel_Factory implements Factory<UpcomingBillsViewModel> {
  private final Provider<BillRepository> billRepositoryProvider;

  private final Provider<AccountRepository> accountRepositoryProvider;

  public UpcomingBillsViewModel_Factory(Provider<BillRepository> billRepositoryProvider,
      Provider<AccountRepository> accountRepositoryProvider) {
    this.billRepositoryProvider = billRepositoryProvider;
    this.accountRepositoryProvider = accountRepositoryProvider;
  }

  @Override
  public UpcomingBillsViewModel get() {
    return newInstance(billRepositoryProvider.get(), accountRepositoryProvider.get());
  }

  public static UpcomingBillsViewModel_Factory create(
      Provider<BillRepository> billRepositoryProvider,
      Provider<AccountRepository> accountRepositoryProvider) {
    return new UpcomingBillsViewModel_Factory(billRepositoryProvider, accountRepositoryProvider);
  }

  public static UpcomingBillsViewModel newInstance(BillRepository billRepository,
      AccountRepository accountRepository) {
    return new UpcomingBillsViewModel(billRepository, accountRepository);
  }
}
