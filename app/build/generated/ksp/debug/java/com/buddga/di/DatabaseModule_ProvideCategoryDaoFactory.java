package com.buddga.di;

import com.buddga.data.local.database.BudgetDatabase;
import com.buddga.data.local.database.dao.CategoryDao;
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
public final class DatabaseModule_ProvideCategoryDaoFactory implements Factory<CategoryDao> {
  private final Provider<BudgetDatabase> databaseProvider;

  public DatabaseModule_ProvideCategoryDaoFactory(Provider<BudgetDatabase> databaseProvider) {
    this.databaseProvider = databaseProvider;
  }

  @Override
  public CategoryDao get() {
    return provideCategoryDao(databaseProvider.get());
  }

  public static DatabaseModule_ProvideCategoryDaoFactory create(
      Provider<BudgetDatabase> databaseProvider) {
    return new DatabaseModule_ProvideCategoryDaoFactory(databaseProvider);
  }

  public static CategoryDao provideCategoryDao(BudgetDatabase database) {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.provideCategoryDao(database));
  }
}
