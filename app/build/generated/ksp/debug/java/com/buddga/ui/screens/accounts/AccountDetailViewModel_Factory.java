package com.buddga.ui.screens.accounts;

import androidx.lifecycle.SavedStateHandle;
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
public final class AccountDetailViewModel_Factory implements Factory<AccountDetailViewModel> {
  private final Provider<SavedStateHandle> savedStateHandleProvider;

  private final Provider<AccountRepository> accountRepositoryProvider;

  private final Provider<TransactionRepository> transactionRepositoryProvider;

  public AccountDetailViewModel_Factory(Provider<SavedStateHandle> savedStateHandleProvider,
      Provider<AccountRepository> accountRepositoryProvider,
      Provider<TransactionRepository> transactionRepositoryProvider) {
    this.savedStateHandleProvider = savedStateHandleProvider;
    this.accountRepositoryProvider = accountRepositoryProvider;
    this.transactionRepositoryProvider = transactionRepositoryProvider;
  }

  @Override
  public AccountDetailViewModel get() {
    return newInstance(savedStateHandleProvider.get(), accountRepositoryProvider.get(), transactionRepositoryProvider.get());
  }

  public static AccountDetailViewModel_Factory create(
      Provider<SavedStateHandle> savedStateHandleProvider,
      Provider<AccountRepository> accountRepositoryProvider,
      Provider<TransactionRepository> transactionRepositoryProvider) {
    return new AccountDetailViewModel_Factory(savedStateHandleProvider, accountRepositoryProvider, transactionRepositoryProvider);
  }

  public static AccountDetailViewModel newInstance(SavedStateHandle savedStateHandle,
      AccountRepository accountRepository, TransactionRepository transactionRepository) {
    return new AccountDetailViewModel(savedStateHandle, accountRepository, transactionRepository);
  }
}
