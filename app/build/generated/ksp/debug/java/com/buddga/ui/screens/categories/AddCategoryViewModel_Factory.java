package com.buddga.ui.screens.categories;

import com.buddga.domain.repository.CategoryRepository;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

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
public final class AddCategoryViewModel_Factory implements Factory<AddCategoryViewModel> {
  private final Provider<CategoryRepository> categoryRepositoryProvider;

  public AddCategoryViewModel_Factory(Provider<CategoryRepository> categoryRepositoryProvider) {
    this.categoryRepositoryProvider = categoryRepositoryProvider;
  }

  @Override
  public AddCategoryViewModel get() {
    return newInstance(categoryRepositoryProvider.get());
  }

  public static AddCategoryViewModel_Factory create(
      Provider<CategoryRepository> categoryRepositoryProvider) {
    return new AddCategoryViewModel_Factory(categoryRepositoryProvider);
  }

  public static AddCategoryViewModel newInstance(CategoryRepository categoryRepository) {
    return new AddCategoryViewModel(categoryRepository);
  }
}
