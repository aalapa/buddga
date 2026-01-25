package com.buddga.ui.screens.forecast;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;

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
public final class CashFlowForecastViewModel_Factory implements Factory<CashFlowForecastViewModel> {
  @Override
  public CashFlowForecastViewModel get() {
    return newInstance();
  }

  public static CashFlowForecastViewModel_Factory create() {
    return InstanceHolder.INSTANCE;
  }

  public static CashFlowForecastViewModel newInstance() {
    return new CashFlowForecastViewModel();
  }

  private static final class InstanceHolder {
    private static final CashFlowForecastViewModel_Factory INSTANCE = new CashFlowForecastViewModel_Factory();
  }
}
