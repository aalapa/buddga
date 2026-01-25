package com.buddga.di;

import com.buddga.data.local.database.BudgetDatabase;
import com.buddga.data.local.database.dao.BillDao;
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
public final class DatabaseModule_ProvideBillDaoFactory implements Factory<BillDao> {
  private final Provider<BudgetDatabase> databaseProvider;

  public DatabaseModule_ProvideBillDaoFactory(Provider<BudgetDatabase> databaseProvider) {
    this.databaseProvider = databaseProvider;
  }

  @Override
  public BillDao get() {
    return provideBillDao(databaseProvider.get());
  }

  public static DatabaseModule_ProvideBillDaoFactory create(
      Provider<BudgetDatabase> databaseProvider) {
    return new DatabaseModule_ProvideBillDaoFactory(databaseProvider);
  }

  public static BillDao provideBillDao(BudgetDatabase database) {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.provideBillDao(database));
  }
}
