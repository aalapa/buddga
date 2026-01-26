package com.buddga.worker;

import android.content.Context;
import androidx.work.WorkerParameters;
import com.buddga.domain.repository.TransactionRepository;
import dagger.internal.DaggerGenerated;
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
public final class RecurringTransactionWorker_Factory {
  private final Provider<TransactionRepository> transactionRepositoryProvider;

  public RecurringTransactionWorker_Factory(
      Provider<TransactionRepository> transactionRepositoryProvider) {
    this.transactionRepositoryProvider = transactionRepositoryProvider;
  }

  public RecurringTransactionWorker get(Context appContext, WorkerParameters workerParams) {
    return newInstance(appContext, workerParams, transactionRepositoryProvider.get());
  }

  public static RecurringTransactionWorker_Factory create(
      Provider<TransactionRepository> transactionRepositoryProvider) {
    return new RecurringTransactionWorker_Factory(transactionRepositoryProvider);
  }

  public static RecurringTransactionWorker newInstance(Context appContext,
      WorkerParameters workerParams, TransactionRepository transactionRepository) {
    return new RecurringTransactionWorker(appContext, workerParams, transactionRepository);
  }
}
