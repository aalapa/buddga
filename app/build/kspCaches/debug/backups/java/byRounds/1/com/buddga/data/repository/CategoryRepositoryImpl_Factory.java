package com.buddga.data.repository;

import com.buddga.data.local.database.dao.CategoryDao;
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
public final class CategoryRepositoryImpl_Factory implements Factory<CategoryRepositoryImpl> {
  private final Provider<CategoryDao> categoryDaoProvider;

  public CategoryRepositoryImpl_Factory(Provider<CategoryDao> categoryDaoProvider) {
    this.categoryDaoProvider = categoryDaoProvider;
  }

  @Override
  public CategoryRepositoryImpl get() {
    return newInstance(categoryDaoProvider.get());
  }

  public static CategoryRepositoryImpl_Factory create(Provider<CategoryDao> categoryDaoProvider) {
    return new CategoryRepositoryImpl_Factory(categoryDaoProvider);
  }

  public static CategoryRepositoryImpl newInstance(CategoryDao categoryDao) {
    return new CategoryRepositoryImpl(categoryDao);
  }
}
