package com.buddga.di;

import android.content.Context;
import com.buddga.data.local.SettingsDataStore;
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
public final class SettingsModule_ProvideSettingsDataStoreFactory implements Factory<SettingsDataStore> {
  private final Provider<Context> contextProvider;

  public SettingsModule_ProvideSettingsDataStoreFactory(Provider<Context> contextProvider) {
    this.contextProvider = contextProvider;
  }

  @Override
  public SettingsDataStore get() {
    return provideSettingsDataStore(contextProvider.get());
  }

  public static SettingsModule_ProvideSettingsDataStoreFactory create(
      Provider<Context> contextProvider) {
    return new SettingsModule_ProvideSettingsDataStoreFactory(contextProvider);
  }

  public static SettingsDataStore provideSettingsDataStore(Context context) {
    return Preconditions.checkNotNullFromProvides(SettingsModule.INSTANCE.provideSettingsDataStore(context));
  }
}
