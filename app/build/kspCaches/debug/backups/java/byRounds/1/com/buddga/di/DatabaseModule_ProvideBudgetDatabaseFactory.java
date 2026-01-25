package com.buddga.di;

import android.content.Context;
import com.buddga.data.local.database.BudgetDatabase;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata("javax.inject.Singleton")
@QualifierMetadata("dagger.hilt.android.qualifiers.ApplicationContext")
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
public final class DatabaseModule_ProvideBudgetDatabaseFactory implements Factory<BudgetDatabase> {
  private final Provider<Context> contextProvider;

  public DatabaseModule_ProvideBudgetDatabaseFactory(Provider<Context> contextProvider) {
    this.contextProvider = contextProvider;
  }

  @Override
  public BudgetDatabase get() {
    return provideBudgetDatabase(contextProvider.get());
  }

  public static DatabaseModule_ProvideBudgetDatabaseFactory create(
      Provider<Context> contextProvider) {
    return new DatabaseModule_ProvideBudgetDatabaseFactory(contextProvider);
  }

  public static BudgetDatabase provideBudgetDatabase(Context context) {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.provideBudgetDatabase(context));
  }
}
