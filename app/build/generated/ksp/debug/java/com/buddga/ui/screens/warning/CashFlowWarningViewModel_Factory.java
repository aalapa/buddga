package com.buddga.ui.screens.warning;

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
public final class CashFlowWarningViewModel_Factory implements Factory<CashFlowWarningViewModel> {
  @Override
  public CashFlowWarningViewModel get() {
    return newInstance();
  }

  public static CashFlowWarningViewModel_Factory create() {
    return InstanceHolder.INSTANCE;
  }

  public static CashFlowWarningViewModel newInstance() {
    return new CashFlowWarningViewModel();
  }

  private static final class InstanceHolder {
    private static final CashFlowWarningViewModel_Factory INSTANCE = new CashFlowWarningViewModel_Factory();
  }
}
