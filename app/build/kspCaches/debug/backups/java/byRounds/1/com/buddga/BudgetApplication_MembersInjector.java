package com.buddga;

import androidx.hilt.work.HiltWorkerFactory;
import dagger.MembersInjector;
import dagger.internal.DaggerGenerated;
import dagger.internal.InjectedFieldSignature;
import dagger.internal.QualifierMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

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
public final class BudgetApplication_MembersInjector implements MembersInjector<BudgetApplication> {
  private final Provider<HiltWorkerFactory> workerFactoryProvider;

  public BudgetApplication_MembersInjector(Provider<HiltWorkerFactory> workerFactoryProvider) {
    this.workerFactoryProvider = workerFactoryProvider;
  }

  public static MembersInjector<BudgetApplication> create(
      Provider<HiltWorkerFactory> workerFactoryProvider) {
    return new BudgetApplication_MembersInjector(workerFactoryProvider);
  }

  @Override
  public void injectMembers(BudgetApplication instance) {
    injectWorkerFactory(instance, workerFactoryProvider.get());
  }

  @InjectedFieldSignature("com.buddga.BudgetApplication.workerFactory")
  public static void injectWorkerFactory(BudgetApplication instance,
      HiltWorkerFactory workerFactory) {
    instance.workerFactory = workerFactory;
  }
}
