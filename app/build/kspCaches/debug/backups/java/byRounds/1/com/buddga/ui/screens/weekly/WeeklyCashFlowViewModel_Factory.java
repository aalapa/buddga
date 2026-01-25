package com.buddga.ui.screens.weekly;

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
public final class WeeklyCashFlowViewModel_Factory implements Factory<WeeklyCashFlowViewModel> {
  @Override
  public WeeklyCashFlowViewModel get() {
    return newInstance();
  }

  public static WeeklyCashFlowViewModel_Factory create() {
    return InstanceHolder.INSTANCE;
  }

  public static WeeklyCashFlowViewModel newInstance() {
    return new WeeklyCashFlowViewModel();
  }

  private static final class InstanceHolder {
    private static final WeeklyCashFlowViewModel_Factory INSTANCE = new WeeklyCashFlowViewModel_Factory();
  }
}
