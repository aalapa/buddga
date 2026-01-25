package com.buddga.di;

import com.buddga.data.local.database.BudgetDatabase;
import com.buddga.data.local.database.dao.BudgetDao;
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
public final class DatabaseModule_ProvideBudgetDaoFactory implements Factory<BudgetDao> {
  private final Provider<BudgetDatabase> databaseProvider;

  public DatabaseModule_ProvideBudgetDaoFactory(Provider<BudgetDatabase> databaseProvider) {
    this.databaseProvider = databaseProvider;
  }

  @Override
  public BudgetDao get() {
    return provideBudgetDao(databaseProvider.get());
  }

  public static DatabaseModule_ProvideBudgetDaoFactory create(
      Provider<BudgetDatabase> databaseProvider) {
    return new DatabaseModule_ProvideBudgetDaoFactory(databaseProvider);
  }

  public static BudgetDao provideBudgetDao(BudgetDatabase database) {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.provideBudgetDao(database));
  }
}
