package com.buddga.di;

import com.buddga.data.local.database.BudgetDatabase;
import com.buddga.data.local.database.dao.TransactionDao;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata("javax.inject.Singleton")
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
public final class DatabaseModule_ProvideTransactionDaoFactory implements Factory<TransactionDao> {
  private final Provider<BudgetDatabase> databaseProvider;

  public DatabaseModule_ProvideTransactionDaoFactory(Provider<BudgetDatabase> databaseProvider) {
    this.databaseProvider = databaseProvider;
  }

  @Override
  public TransactionDao get() {
    return provideTransactionDao(databaseProvider.get());
  }

  public static DatabaseModule_ProvideTransactionDaoFactory create(
      Provider<BudgetDatabase> databaseProvider) {
    return new DatabaseModule_ProvideTransactionDaoFactory(databaseProvider);
  }

  public static TransactionDao provideTransactionDao(BudgetDatabase database) {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.provideTransactionDao(database));
  }
}
