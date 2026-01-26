package com.buddga;

import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.view.View;
import androidx.fragment.app.Fragment;
import androidx.hilt.work.HiltWorkerFactory;
import androidx.hilt.work.WorkerAssistedFactory;
import androidx.hilt.work.WorkerFactoryModule_ProvideFactoryFactory;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;
import androidx.work.ListenableWorker;
import androidx.work.WorkerParameters;
import com.buddga.data.local.database.BudgetDatabase;
import com.buddga.data.local.database.dao.AccountDao;
import com.buddga.data.local.database.dao.BillDao;
import com.buddga.data.local.database.dao.BudgetDao;
import com.buddga.data.local.database.dao.CategoryDao;
import com.buddga.data.local.database.dao.TransactionDao;
import com.buddga.data.repository.AccountRepositoryImpl;
import com.buddga.data.repository.BillRepositoryImpl;
import com.buddga.data.repository.BudgetRepositoryImpl;
import com.buddga.data.repository.CategoryRepositoryImpl;
import com.buddga.data.repository.TransactionRepositoryImpl;
import com.buddga.di.DatabaseModule_ProvideAccountDaoFactory;
import com.buddga.di.DatabaseModule_ProvideBillDaoFactory;
import com.buddga.di.DatabaseModule_ProvideBudgetDaoFactory;
import com.buddga.di.DatabaseModule_ProvideBudgetDatabaseFactory;
import com.buddga.di.DatabaseModule_ProvideCategoryDaoFactory;
import com.buddga.di.DatabaseModule_ProvideTransactionDaoFactory;
import com.buddga.ui.screens.accounts.AccountsViewModel;
import com.buddga.ui.screens.accounts.AccountsViewModel_HiltModules_KeyModule_ProvideFactory;
import com.buddga.ui.screens.bills.AddBillViewModel;
import com.buddga.ui.screens.bills.AddBillViewModel_HiltModules_KeyModule_ProvideFactory;
import com.buddga.ui.screens.bills.UpcomingBillsViewModel;
import com.buddga.ui.screens.bills.UpcomingBillsViewModel_HiltModules_KeyModule_ProvideFactory;
import com.buddga.ui.screens.budgeting.AddBudgetViewModel;
import com.buddga.ui.screens.budgeting.AddBudgetViewModel_HiltModules_KeyModule_ProvideFactory;
import com.buddga.ui.screens.budgeting.BudgetingViewModel;
import com.buddga.ui.screens.budgeting.BudgetingViewModel_HiltModules_KeyModule_ProvideFactory;
import com.buddga.ui.screens.cashflow.CashFlowViewModel;
import com.buddga.ui.screens.cashflow.CashFlowViewModel_HiltModules_KeyModule_ProvideFactory;
import com.buddga.ui.screens.categories.AddCategoryViewModel;
import com.buddga.ui.screens.categories.AddCategoryViewModel_HiltModules_KeyModule_ProvideFactory;
import com.buddga.ui.screens.forecast.CashFlowForecastViewModel;
import com.buddga.ui.screens.forecast.CashFlowForecastViewModel_HiltModules_KeyModule_ProvideFactory;
import com.buddga.ui.screens.transactions.AddTransactionViewModel;
import com.buddga.ui.screens.transactions.AddTransactionViewModel_HiltModules_KeyModule_ProvideFactory;
import com.buddga.ui.screens.transactions.TransactionsViewModel;
import com.buddga.ui.screens.transactions.TransactionsViewModel_HiltModules_KeyModule_ProvideFactory;
import com.buddga.ui.screens.warning.CashFlowWarningViewModel;
import com.buddga.ui.screens.warning.CashFlowWarningViewModel_HiltModules_KeyModule_ProvideFactory;
import com.buddga.ui.screens.weekly.WeeklyCashFlowViewModel;
import com.buddga.ui.screens.weekly.WeeklyCashFlowViewModel_HiltModules_KeyModule_ProvideFactory;
import com.buddga.worker.RecurringTransactionWorker;
import com.buddga.worker.RecurringTransactionWorker_AssistedFactory;
import dagger.hilt.android.ActivityRetainedLifecycle;
import dagger.hilt.android.ViewModelLifecycle;
import dagger.hilt.android.internal.builders.ActivityComponentBuilder;
import dagger.hilt.android.internal.builders.ActivityRetainedComponentBuilder;
import dagger.hilt.android.internal.builders.FragmentComponentBuilder;
import dagger.hilt.android.internal.builders.ServiceComponentBuilder;
import dagger.hilt.android.internal.builders.ViewComponentBuilder;
import dagger.hilt.android.internal.builders.ViewModelComponentBuilder;
import dagger.hilt.android.internal.builders.ViewWithFragmentComponentBuilder;
import dagger.hilt.android.internal.lifecycle.DefaultViewModelFactories;
import dagger.hilt.android.internal.lifecycle.DefaultViewModelFactories_InternalFactoryFactory_Factory;
import dagger.hilt.android.internal.managers.ActivityRetainedComponentManager_LifecycleModule_ProvideActivityRetainedLifecycleFactory;
import dagger.hilt.android.internal.managers.SavedStateHandleHolder;
import dagger.hilt.android.internal.modules.ApplicationContextModule;
import dagger.hilt.android.internal.modules.ApplicationContextModule_ProvideContextFactory;
import dagger.internal.DaggerGenerated;
import dagger.internal.DoubleCheck;
import dagger.internal.MapBuilder;
import dagger.internal.Preconditions;
import dagger.internal.Provider;
import dagger.internal.SetBuilder;
import dagger.internal.SingleCheck;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import javax.annotation.processing.Generated;

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
public final class DaggerBudgetApplication_HiltComponents_SingletonC {
  private DaggerBudgetApplication_HiltComponents_SingletonC() {
  }

  public static Builder builder() {
    return new Builder();
  }

  public static final class Builder {
    private ApplicationContextModule applicationContextModule;

    private Builder() {
    }

    public Builder applicationContextModule(ApplicationContextModule applicationContextModule) {
      this.applicationContextModule = Preconditions.checkNotNull(applicationContextModule);
      return this;
    }

    public BudgetApplication_HiltComponents.SingletonC build() {
      Preconditions.checkBuilderRequirement(applicationContextModule, ApplicationContextModule.class);
      return new SingletonCImpl(applicationContextModule);
    }
  }

  private static final class ActivityRetainedCBuilder implements BudgetApplication_HiltComponents.ActivityRetainedC.Builder {
    private final SingletonCImpl singletonCImpl;

    private SavedStateHandleHolder savedStateHandleHolder;

    private ActivityRetainedCBuilder(SingletonCImpl singletonCImpl) {
      this.singletonCImpl = singletonCImpl;
    }

    @Override
    public ActivityRetainedCBuilder savedStateHandleHolder(
        SavedStateHandleHolder savedStateHandleHolder) {
      this.savedStateHandleHolder = Preconditions.checkNotNull(savedStateHandleHolder);
      return this;
    }

    @Override
    public BudgetApplication_HiltComponents.ActivityRetainedC build() {
      Preconditions.checkBuilderRequirement(savedStateHandleHolder, SavedStateHandleHolder.class);
      return new ActivityRetainedCImpl(singletonCImpl, savedStateHandleHolder);
    }
  }

  private static final class ActivityCBuilder implements BudgetApplication_HiltComponents.ActivityC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private Activity activity;

    private ActivityCBuilder(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
    }

    @Override
    public ActivityCBuilder activity(Activity activity) {
      this.activity = Preconditions.checkNotNull(activity);
      return this;
    }

    @Override
    public BudgetApplication_HiltComponents.ActivityC build() {
      Preconditions.checkBuilderRequirement(activity, Activity.class);
      return new ActivityCImpl(singletonCImpl, activityRetainedCImpl, activity);
    }
  }

  private static final class FragmentCBuilder implements BudgetApplication_HiltComponents.FragmentC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private Fragment fragment;

    private FragmentCBuilder(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, ActivityCImpl activityCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;
    }

    @Override
    public FragmentCBuilder fragment(Fragment fragment) {
      this.fragment = Preconditions.checkNotNull(fragment);
      return this;
    }

    @Override
    public BudgetApplication_HiltComponents.FragmentC build() {
      Preconditions.checkBuilderRequirement(fragment, Fragment.class);
      return new FragmentCImpl(singletonCImpl, activityRetainedCImpl, activityCImpl, fragment);
    }
  }

  private static final class ViewWithFragmentCBuilder implements BudgetApplication_HiltComponents.ViewWithFragmentC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private final FragmentCImpl fragmentCImpl;

    private View view;

    private ViewWithFragmentCBuilder(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, ActivityCImpl activityCImpl,
        FragmentCImpl fragmentCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;
      this.fragmentCImpl = fragmentCImpl;
    }

    @Override
    public ViewWithFragmentCBuilder view(View view) {
      this.view = Preconditions.checkNotNull(view);
      return this;
    }

    @Override
    public BudgetApplication_HiltComponents.ViewWithFragmentC build() {
      Preconditions.checkBuilderRequirement(view, View.class);
      return new ViewWithFragmentCImpl(singletonCImpl, activityRetainedCImpl, activityCImpl, fragmentCImpl, view);
    }
  }

  private static final class ViewCBuilder implements BudgetApplication_HiltComponents.ViewC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private View view;

    private ViewCBuilder(SingletonCImpl singletonCImpl, ActivityRetainedCImpl activityRetainedCImpl,
        ActivityCImpl activityCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;
    }

    @Override
    public ViewCBuilder view(View view) {
      this.view = Preconditions.checkNotNull(view);
      return this;
    }

    @Override
    public BudgetApplication_HiltComponents.ViewC build() {
      Preconditions.checkBuilderRequirement(view, View.class);
      return new ViewCImpl(singletonCImpl, activityRetainedCImpl, activityCImpl, view);
    }
  }

  private static final class ViewModelCBuilder implements BudgetApplication_HiltComponents.ViewModelC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private SavedStateHandle savedStateHandle;

    private ViewModelLifecycle viewModelLifecycle;

    private ViewModelCBuilder(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
    }

    @Override
    public ViewModelCBuilder savedStateHandle(SavedStateHandle handle) {
      this.savedStateHandle = Preconditions.checkNotNull(handle);
      return this;
    }

    @Override
    public ViewModelCBuilder viewModelLifecycle(ViewModelLifecycle viewModelLifecycle) {
      this.viewModelLifecycle = Preconditions.checkNotNull(viewModelLifecycle);
      return this;
    }

    @Override
    public BudgetApplication_HiltComponents.ViewModelC build() {
      Preconditions.checkBuilderRequirement(savedStateHandle, SavedStateHandle.class);
      Preconditions.checkBuilderRequirement(viewModelLifecycle, ViewModelLifecycle.class);
      return new ViewModelCImpl(singletonCImpl, activityRetainedCImpl, savedStateHandle, viewModelLifecycle);
    }
  }

  private static final class ServiceCBuilder implements BudgetApplication_HiltComponents.ServiceC.Builder {
    private final SingletonCImpl singletonCImpl;

    private Service service;

    private ServiceCBuilder(SingletonCImpl singletonCImpl) {
      this.singletonCImpl = singletonCImpl;
    }

    @Override
    public ServiceCBuilder service(Service service) {
      this.service = Preconditions.checkNotNull(service);
      return this;
    }

    @Override
    public BudgetApplication_HiltComponents.ServiceC build() {
      Preconditions.checkBuilderRequirement(service, Service.class);
      return new ServiceCImpl(singletonCImpl, service);
    }
  }

  private static final class ViewWithFragmentCImpl extends BudgetApplication_HiltComponents.ViewWithFragmentC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private final FragmentCImpl fragmentCImpl;

    private final ViewWithFragmentCImpl viewWithFragmentCImpl = this;

    private ViewWithFragmentCImpl(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, ActivityCImpl activityCImpl,
        FragmentCImpl fragmentCImpl, View viewParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;
      this.fragmentCImpl = fragmentCImpl;


    }
  }

  private static final class FragmentCImpl extends BudgetApplication_HiltComponents.FragmentC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private final FragmentCImpl fragmentCImpl = this;

    private FragmentCImpl(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, ActivityCImpl activityCImpl,
        Fragment fragmentParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;


    }

    @Override
    public DefaultViewModelFactories.InternalFactoryFactory getHiltInternalFactoryFactory() {
      return activityCImpl.getHiltInternalFactoryFactory();
    }

    @Override
    public ViewWithFragmentComponentBuilder viewWithFragmentComponentBuilder() {
      return new ViewWithFragmentCBuilder(singletonCImpl, activityRetainedCImpl, activityCImpl, fragmentCImpl);
    }
  }

  private static final class ViewCImpl extends BudgetApplication_HiltComponents.ViewC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private final ViewCImpl viewCImpl = this;

    private ViewCImpl(SingletonCImpl singletonCImpl, ActivityRetainedCImpl activityRetainedCImpl,
        ActivityCImpl activityCImpl, View viewParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;


    }
  }

  private static final class ActivityCImpl extends BudgetApplication_HiltComponents.ActivityC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl = this;

    private ActivityCImpl(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, Activity activityParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;


    }

    @Override
    public void injectMainActivity(MainActivity mainActivity) {
    }

    @Override
    public DefaultViewModelFactories.InternalFactoryFactory getHiltInternalFactoryFactory() {
      return DefaultViewModelFactories_InternalFactoryFactory_Factory.newInstance(getViewModelKeys(), new ViewModelCBuilder(singletonCImpl, activityRetainedCImpl));
    }

    @Override
    public Set<String> getViewModelKeys() {
      return SetBuilder.<String>newSetBuilder(12).add(AccountsViewModel_HiltModules_KeyModule_ProvideFactory.provide()).add(AddBillViewModel_HiltModules_KeyModule_ProvideFactory.provide()).add(AddBudgetViewModel_HiltModules_KeyModule_ProvideFactory.provide()).add(AddCategoryViewModel_HiltModules_KeyModule_ProvideFactory.provide()).add(AddTransactionViewModel_HiltModules_KeyModule_ProvideFactory.provide()).add(BudgetingViewModel_HiltModules_KeyModule_ProvideFactory.provide()).add(CashFlowForecastViewModel_HiltModules_KeyModule_ProvideFactory.provide()).add(CashFlowViewModel_HiltModules_KeyModule_ProvideFactory.provide()).add(CashFlowWarningViewModel_HiltModules_KeyModule_ProvideFactory.provide()).add(TransactionsViewModel_HiltModules_KeyModule_ProvideFactory.provide()).add(UpcomingBillsViewModel_HiltModules_KeyModule_ProvideFactory.provide()).add(WeeklyCashFlowViewModel_HiltModules_KeyModule_ProvideFactory.provide()).build();
    }

    @Override
    public ViewModelComponentBuilder getViewModelComponentBuilder() {
      return new ViewModelCBuilder(singletonCImpl, activityRetainedCImpl);
    }

    @Override
    public FragmentComponentBuilder fragmentComponentBuilder() {
      return new FragmentCBuilder(singletonCImpl, activityRetainedCImpl, activityCImpl);
    }

    @Override
    public ViewComponentBuilder viewComponentBuilder() {
      return new ViewCBuilder(singletonCImpl, activityRetainedCImpl, activityCImpl);
    }
  }

  private static final class ViewModelCImpl extends BudgetApplication_HiltComponents.ViewModelC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ViewModelCImpl viewModelCImpl = this;

    private Provider<AccountsViewModel> accountsViewModelProvider;

    private Provider<AddBillViewModel> addBillViewModelProvider;

    private Provider<AddBudgetViewModel> addBudgetViewModelProvider;

    private Provider<AddCategoryViewModel> addCategoryViewModelProvider;

    private Provider<AddTransactionViewModel> addTransactionViewModelProvider;

    private Provider<BudgetingViewModel> budgetingViewModelProvider;

    private Provider<CashFlowForecastViewModel> cashFlowForecastViewModelProvider;

    private Provider<CashFlowViewModel> cashFlowViewModelProvider;

    private Provider<CashFlowWarningViewModel> cashFlowWarningViewModelProvider;

    private Provider<TransactionsViewModel> transactionsViewModelProvider;

    private Provider<UpcomingBillsViewModel> upcomingBillsViewModelProvider;

    private Provider<WeeklyCashFlowViewModel> weeklyCashFlowViewModelProvider;

    private ViewModelCImpl(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, SavedStateHandle savedStateHandleParam,
        ViewModelLifecycle viewModelLifecycleParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;

      initialize(savedStateHandleParam, viewModelLifecycleParam);

    }

    @SuppressWarnings("unchecked")
    private void initialize(final SavedStateHandle savedStateHandleParam,
        final ViewModelLifecycle viewModelLifecycleParam) {
      this.accountsViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 0);
      this.addBillViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 1);
      this.addBudgetViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 2);
      this.addCategoryViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 3);
      this.addTransactionViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 4);
      this.budgetingViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 5);
      this.cashFlowForecastViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 6);
      this.cashFlowViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 7);
      this.cashFlowWarningViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 8);
      this.transactionsViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 9);
      this.upcomingBillsViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 10);
      this.weeklyCashFlowViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 11);
    }

    @Override
    public Map<String, javax.inject.Provider<ViewModel>> getHiltViewModelMap() {
      return MapBuilder.<String, javax.inject.Provider<ViewModel>>newMapBuilder(12).put("com.buddga.ui.screens.accounts.AccountsViewModel", ((Provider) accountsViewModelProvider)).put("com.buddga.ui.screens.bills.AddBillViewModel", ((Provider) addBillViewModelProvider)).put("com.buddga.ui.screens.budgeting.AddBudgetViewModel", ((Provider) addBudgetViewModelProvider)).put("com.buddga.ui.screens.categories.AddCategoryViewModel", ((Provider) addCategoryViewModelProvider)).put("com.buddga.ui.screens.transactions.AddTransactionViewModel", ((Provider) addTransactionViewModelProvider)).put("com.buddga.ui.screens.budgeting.BudgetingViewModel", ((Provider) budgetingViewModelProvider)).put("com.buddga.ui.screens.forecast.CashFlowForecastViewModel", ((Provider) cashFlowForecastViewModelProvider)).put("com.buddga.ui.screens.cashflow.CashFlowViewModel", ((Provider) cashFlowViewModelProvider)).put("com.buddga.ui.screens.warning.CashFlowWarningViewModel", ((Provider) cashFlowWarningViewModelProvider)).put("com.buddga.ui.screens.transactions.TransactionsViewModel", ((Provider) transactionsViewModelProvider)).put("com.buddga.ui.screens.bills.UpcomingBillsViewModel", ((Provider) upcomingBillsViewModelProvider)).put("com.buddga.ui.screens.weekly.WeeklyCashFlowViewModel", ((Provider) weeklyCashFlowViewModelProvider)).build();
    }

    @Override
    public Map<String, Object> getHiltViewModelAssistedMap() {
      return Collections.<String, Object>emptyMap();
    }

    private static final class SwitchingProvider<T> implements Provider<T> {
      private final SingletonCImpl singletonCImpl;

      private final ActivityRetainedCImpl activityRetainedCImpl;

      private final ViewModelCImpl viewModelCImpl;

      private final int id;

      SwitchingProvider(SingletonCImpl singletonCImpl, ActivityRetainedCImpl activityRetainedCImpl,
          ViewModelCImpl viewModelCImpl, int id) {
        this.singletonCImpl = singletonCImpl;
        this.activityRetainedCImpl = activityRetainedCImpl;
        this.viewModelCImpl = viewModelCImpl;
        this.id = id;
      }

      @SuppressWarnings("unchecked")
      @Override
      public T get() {
        switch (id) {
          case 0: // com.buddga.ui.screens.accounts.AccountsViewModel 
          return (T) new AccountsViewModel(singletonCImpl.accountRepositoryImplProvider.get());

          case 1: // com.buddga.ui.screens.bills.AddBillViewModel 
          return (T) new AddBillViewModel(singletonCImpl.billRepositoryImplProvider.get(), singletonCImpl.categoryRepositoryImplProvider.get());

          case 2: // com.buddga.ui.screens.budgeting.AddBudgetViewModel 
          return (T) new AddBudgetViewModel(singletonCImpl.budgetRepositoryImplProvider.get(), singletonCImpl.categoryRepositoryImplProvider.get());

          case 3: // com.buddga.ui.screens.categories.AddCategoryViewModel 
          return (T) new AddCategoryViewModel(singletonCImpl.categoryRepositoryImplProvider.get());

          case 4: // com.buddga.ui.screens.transactions.AddTransactionViewModel 
          return (T) new AddTransactionViewModel(singletonCImpl.transactionRepositoryImplProvider.get(), singletonCImpl.categoryRepositoryImplProvider.get(), singletonCImpl.accountRepositoryImplProvider.get());

          case 5: // com.buddga.ui.screens.budgeting.BudgetingViewModel 
          return (T) new BudgetingViewModel(singletonCImpl.budgetRepositoryImplProvider.get(), singletonCImpl.categoryRepositoryImplProvider.get(), singletonCImpl.transactionRepositoryImplProvider.get(), singletonCImpl.accountRepositoryImplProvider.get());

          case 6: // com.buddga.ui.screens.forecast.CashFlowForecastViewModel 
          return (T) new CashFlowForecastViewModel();

          case 7: // com.buddga.ui.screens.cashflow.CashFlowViewModel 
          return (T) new CashFlowViewModel(singletonCImpl.transactionRepositoryImplProvider.get());

          case 8: // com.buddga.ui.screens.warning.CashFlowWarningViewModel 
          return (T) new CashFlowWarningViewModel();

          case 9: // com.buddga.ui.screens.transactions.TransactionsViewModel 
          return (T) new TransactionsViewModel(singletonCImpl.transactionRepositoryImplProvider.get(), singletonCImpl.accountRepositoryImplProvider.get());

          case 10: // com.buddga.ui.screens.bills.UpcomingBillsViewModel 
          return (T) new UpcomingBillsViewModel();

          case 11: // com.buddga.ui.screens.weekly.WeeklyCashFlowViewModel 
          return (T) new WeeklyCashFlowViewModel();

          default: throw new AssertionError(id);
        }
      }
    }
  }

  private static final class ActivityRetainedCImpl extends BudgetApplication_HiltComponents.ActivityRetainedC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl = this;

    private Provider<ActivityRetainedLifecycle> provideActivityRetainedLifecycleProvider;

    private ActivityRetainedCImpl(SingletonCImpl singletonCImpl,
        SavedStateHandleHolder savedStateHandleHolderParam) {
      this.singletonCImpl = singletonCImpl;

      initialize(savedStateHandleHolderParam);

    }

    @SuppressWarnings("unchecked")
    private void initialize(final SavedStateHandleHolder savedStateHandleHolderParam) {
      this.provideActivityRetainedLifecycleProvider = DoubleCheck.provider(new SwitchingProvider<ActivityRetainedLifecycle>(singletonCImpl, activityRetainedCImpl, 0));
    }

    @Override
    public ActivityComponentBuilder activityComponentBuilder() {
      return new ActivityCBuilder(singletonCImpl, activityRetainedCImpl);
    }

    @Override
    public ActivityRetainedLifecycle getActivityRetainedLifecycle() {
      return provideActivityRetainedLifecycleProvider.get();
    }

    private static final class SwitchingProvider<T> implements Provider<T> {
      private final SingletonCImpl singletonCImpl;

      private final ActivityRetainedCImpl activityRetainedCImpl;

      private final int id;

      SwitchingProvider(SingletonCImpl singletonCImpl, ActivityRetainedCImpl activityRetainedCImpl,
          int id) {
        this.singletonCImpl = singletonCImpl;
        this.activityRetainedCImpl = activityRetainedCImpl;
        this.id = id;
      }

      @SuppressWarnings("unchecked")
      @Override
      public T get() {
        switch (id) {
          case 0: // dagger.hilt.android.ActivityRetainedLifecycle 
          return (T) ActivityRetainedComponentManager_LifecycleModule_ProvideActivityRetainedLifecycleFactory.provideActivityRetainedLifecycle();

          default: throw new AssertionError(id);
        }
      }
    }
  }

  private static final class ServiceCImpl extends BudgetApplication_HiltComponents.ServiceC {
    private final SingletonCImpl singletonCImpl;

    private final ServiceCImpl serviceCImpl = this;

    private ServiceCImpl(SingletonCImpl singletonCImpl, Service serviceParam) {
      this.singletonCImpl = singletonCImpl;


    }
  }

  private static final class SingletonCImpl extends BudgetApplication_HiltComponents.SingletonC {
    private final ApplicationContextModule applicationContextModule;

    private final SingletonCImpl singletonCImpl = this;

    private Provider<BudgetDatabase> provideBudgetDatabaseProvider;

    private Provider<TransactionDao> provideTransactionDaoProvider;

    private Provider<TransactionRepositoryImpl> transactionRepositoryImplProvider;

    private Provider<RecurringTransactionWorker_AssistedFactory> recurringTransactionWorker_AssistedFactoryProvider;

    private Provider<AccountDao> provideAccountDaoProvider;

    private Provider<AccountRepositoryImpl> accountRepositoryImplProvider;

    private Provider<BillDao> provideBillDaoProvider;

    private Provider<BillRepositoryImpl> billRepositoryImplProvider;

    private Provider<CategoryDao> provideCategoryDaoProvider;

    private Provider<CategoryRepositoryImpl> categoryRepositoryImplProvider;

    private Provider<BudgetDao> provideBudgetDaoProvider;

    private Provider<BudgetRepositoryImpl> budgetRepositoryImplProvider;

    private SingletonCImpl(ApplicationContextModule applicationContextModuleParam) {
      this.applicationContextModule = applicationContextModuleParam;
      initialize(applicationContextModuleParam);

    }

    private Map<String, javax.inject.Provider<WorkerAssistedFactory<? extends ListenableWorker>>> mapOfStringAndProviderOfWorkerAssistedFactoryOf(
        ) {
      return Collections.<String, javax.inject.Provider<WorkerAssistedFactory<? extends ListenableWorker>>>singletonMap("com.buddga.worker.RecurringTransactionWorker", ((Provider) recurringTransactionWorker_AssistedFactoryProvider));
    }

    private HiltWorkerFactory hiltWorkerFactory() {
      return WorkerFactoryModule_ProvideFactoryFactory.provideFactory(mapOfStringAndProviderOfWorkerAssistedFactoryOf());
    }

    @SuppressWarnings("unchecked")
    private void initialize(final ApplicationContextModule applicationContextModuleParam) {
      this.provideBudgetDatabaseProvider = DoubleCheck.provider(new SwitchingProvider<BudgetDatabase>(singletonCImpl, 3));
      this.provideTransactionDaoProvider = DoubleCheck.provider(new SwitchingProvider<TransactionDao>(singletonCImpl, 2));
      this.transactionRepositoryImplProvider = DoubleCheck.provider(new SwitchingProvider<TransactionRepositoryImpl>(singletonCImpl, 1));
      this.recurringTransactionWorker_AssistedFactoryProvider = SingleCheck.provider(new SwitchingProvider<RecurringTransactionWorker_AssistedFactory>(singletonCImpl, 0));
      this.provideAccountDaoProvider = DoubleCheck.provider(new SwitchingProvider<AccountDao>(singletonCImpl, 5));
      this.accountRepositoryImplProvider = DoubleCheck.provider(new SwitchingProvider<AccountRepositoryImpl>(singletonCImpl, 4));
      this.provideBillDaoProvider = DoubleCheck.provider(new SwitchingProvider<BillDao>(singletonCImpl, 7));
      this.billRepositoryImplProvider = DoubleCheck.provider(new SwitchingProvider<BillRepositoryImpl>(singletonCImpl, 6));
      this.provideCategoryDaoProvider = DoubleCheck.provider(new SwitchingProvider<CategoryDao>(singletonCImpl, 9));
      this.categoryRepositoryImplProvider = DoubleCheck.provider(new SwitchingProvider<CategoryRepositoryImpl>(singletonCImpl, 8));
      this.provideBudgetDaoProvider = DoubleCheck.provider(new SwitchingProvider<BudgetDao>(singletonCImpl, 11));
      this.budgetRepositoryImplProvider = DoubleCheck.provider(new SwitchingProvider<BudgetRepositoryImpl>(singletonCImpl, 10));
    }

    @Override
    public void injectBudgetApplication(BudgetApplication budgetApplication) {
      injectBudgetApplication2(budgetApplication);
    }

    @Override
    public Set<Boolean> getDisableFragmentGetContextFix() {
      return Collections.<Boolean>emptySet();
    }

    @Override
    public ActivityRetainedComponentBuilder retainedComponentBuilder() {
      return new ActivityRetainedCBuilder(singletonCImpl);
    }

    @Override
    public ServiceComponentBuilder serviceComponentBuilder() {
      return new ServiceCBuilder(singletonCImpl);
    }

    private BudgetApplication injectBudgetApplication2(BudgetApplication instance) {
      BudgetApplication_MembersInjector.injectWorkerFactory(instance, hiltWorkerFactory());
      return instance;
    }

    private static final class SwitchingProvider<T> implements Provider<T> {
      private final SingletonCImpl singletonCImpl;

      private final int id;

      SwitchingProvider(SingletonCImpl singletonCImpl, int id) {
        this.singletonCImpl = singletonCImpl;
        this.id = id;
      }

      @SuppressWarnings("unchecked")
      @Override
      public T get() {
        switch (id) {
          case 0: // com.buddga.worker.RecurringTransactionWorker_AssistedFactory 
          return (T) new RecurringTransactionWorker_AssistedFactory() {
            @Override
            public RecurringTransactionWorker create(Context appContext,
                WorkerParameters workerParams) {
              return new RecurringTransactionWorker(appContext, workerParams, singletonCImpl.transactionRepositoryImplProvider.get());
            }
          };

          case 1: // com.buddga.data.repository.TransactionRepositoryImpl 
          return (T) new TransactionRepositoryImpl(singletonCImpl.provideTransactionDaoProvider.get());

          case 2: // com.buddga.data.local.database.dao.TransactionDao 
          return (T) DatabaseModule_ProvideTransactionDaoFactory.provideTransactionDao(singletonCImpl.provideBudgetDatabaseProvider.get());

          case 3: // com.buddga.data.local.database.BudgetDatabase 
          return (T) DatabaseModule_ProvideBudgetDatabaseFactory.provideBudgetDatabase(ApplicationContextModule_ProvideContextFactory.provideContext(singletonCImpl.applicationContextModule));

          case 4: // com.buddga.data.repository.AccountRepositoryImpl 
          return (T) new AccountRepositoryImpl(singletonCImpl.provideAccountDaoProvider.get());

          case 5: // com.buddga.data.local.database.dao.AccountDao 
          return (T) DatabaseModule_ProvideAccountDaoFactory.provideAccountDao(singletonCImpl.provideBudgetDatabaseProvider.get());

          case 6: // com.buddga.data.repository.BillRepositoryImpl 
          return (T) new BillRepositoryImpl(singletonCImpl.provideBillDaoProvider.get());

          case 7: // com.buddga.data.local.database.dao.BillDao 
          return (T) DatabaseModule_ProvideBillDaoFactory.provideBillDao(singletonCImpl.provideBudgetDatabaseProvider.get());

          case 8: // com.buddga.data.repository.CategoryRepositoryImpl 
          return (T) new CategoryRepositoryImpl(singletonCImpl.provideCategoryDaoProvider.get());

          case 9: // com.buddga.data.local.database.dao.CategoryDao 
          return (T) DatabaseModule_ProvideCategoryDaoFactory.provideCategoryDao(singletonCImpl.provideBudgetDatabaseProvider.get());

          case 10: // com.buddga.data.repository.BudgetRepositoryImpl 
          return (T) new BudgetRepositoryImpl(singletonCImpl.provideBudgetDaoProvider.get());

          case 11: // com.buddga.data.local.database.dao.BudgetDao 
          return (T) DatabaseModule_ProvideBudgetDaoFactory.provideBudgetDao(singletonCImpl.provideBudgetDatabaseProvider.get());

          default: throw new AssertionError(id);
        }
      }
    }
  }
}
