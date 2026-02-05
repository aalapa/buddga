package com.buddga.util;

import com.buddga.data.local.SettingsDataStore;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
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
public final class CurrencyFormatter_Factory implements Factory<CurrencyFormatter> {
  private final Provider<SettingsDataStore> settingsDataStoreProvider;

  public CurrencyFormatter_Factory(Provider<SettingsDataStore> settingsDataStoreProvider) {
    this.settingsDataStoreProvider = settingsDataStoreProvider;
  }

  @Override
  public CurrencyFormatter get() {
    return newInstance(settingsDataStoreProvider.get());
  }

  public static CurrencyFormatter_Factory create(
      Provider<SettingsDataStore> settingsDataStoreProvider) {
    return new CurrencyFormatter_Factory(settingsDataStoreProvider);
  }

  public static CurrencyFormatter newInstance(SettingsDataStore settingsDataStore) {
    return new CurrencyFormatter(settingsDataStore);
  }
}
