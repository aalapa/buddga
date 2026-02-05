package com.buddga.di;

import com.buddga.data.local.SettingsDataStore;
import com.buddga.util.CurrencyFormatter;
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
public final class SettingsModule_ProvideCurrencyFormatterFactory implements Factory<CurrencyFormatter> {
  private final Provider<SettingsDataStore> settingsDataStoreProvider;

  public SettingsModule_ProvideCurrencyFormatterFactory(
      Provider<SettingsDataStore> settingsDataStoreProvider) {
    this.settingsDataStoreProvider = settingsDataStoreProvider;
  }

  @Override
  public CurrencyFormatter get() {
    return provideCurrencyFormatter(settingsDataStoreProvider.get());
  }

  public static SettingsModule_ProvideCurrencyFormatterFactory create(
      Provider<SettingsDataStore> settingsDataStoreProvider) {
    return new SettingsModule_ProvideCurrencyFormatterFactory(settingsDataStoreProvider);
  }

  public static CurrencyFormatter provideCurrencyFormatter(SettingsDataStore settingsDataStore) {
    return Preconditions.checkNotNullFromProvides(SettingsModule.INSTANCE.provideCurrencyFormatter(settingsDataStore));
  }
}
