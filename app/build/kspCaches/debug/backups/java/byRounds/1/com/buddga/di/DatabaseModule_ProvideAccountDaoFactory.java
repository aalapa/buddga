package com.buddga.di;

import com.buddga.data.local.database.BudgetDatabase;
import com.buddga.data.local.database.dao.AccountDao;
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
public final class DatabaseModule_ProvideAccountDaoFactory implements Factory<AccountDao> {
  private final Provider<BudgetDatabase> databaseProvider;

  public DatabaseModule_ProvideAccountDaoFactory(Provider<BudgetDatabase> databaseProvider) {
    this.databaseProvider = databaseProvider;
  }

  @Override
  public AccountDao get() {
    return provideAccountDao(databaseProvider.get());
  }

  public static DatabaseModule_ProvideAccountDaoFactory create(
      Provider<BudgetDatabase> databaseProvider) {
    return new DatabaseModule_ProvideAccountDaoFactory(databaseProvider);
  }

  public static AccountDao provideAccountDao(BudgetDatabase database) {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.provideAccountDao(database));
  }
}
