package com.buddga.data.local.database.dao;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.buddga.data.local.database.entity.BudgetEntity;
import com.buddga.data.local.database.entity.BudgetWithCategoryEntity;
import java.lang.Class;
import java.lang.Double;
import java.lang.Exception;
import java.lang.Long;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.Flow;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class BudgetDao_Impl implements BudgetDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<BudgetEntity> __insertionAdapterOfBudgetEntity;

  private final EntityDeletionOrUpdateAdapter<BudgetEntity> __deletionAdapterOfBudgetEntity;

  private final EntityDeletionOrUpdateAdapter<BudgetEntity> __updateAdapterOfBudgetEntity;

  private final SharedSQLiteStatement __preparedStmtOfUpdateAllocated;

  private final SharedSQLiteStatement __preparedStmtOfUpdateCarryover;

  private final SharedSQLiteStatement __preparedStmtOfDeleteByCategoryAndMonth;

  private final SharedSQLiteStatement __preparedStmtOfCopyBudgetsFromPreviousMonth;

  public BudgetDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfBudgetEntity = new EntityInsertionAdapter<BudgetEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `budgets` (`id`,`categoryId`,`month`,`allocated`,`carryover`) VALUES (nullif(?, 0),?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final BudgetEntity entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getCategoryId());
        statement.bindString(3, entity.getMonth());
        statement.bindString(4, entity.getAllocated());
        statement.bindString(5, entity.getCarryover());
      }
    };
    this.__deletionAdapterOfBudgetEntity = new EntityDeletionOrUpdateAdapter<BudgetEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `budgets` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final BudgetEntity entity) {
        statement.bindLong(1, entity.getId());
      }
    };
    this.__updateAdapterOfBudgetEntity = new EntityDeletionOrUpdateAdapter<BudgetEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `budgets` SET `id` = ?,`categoryId` = ?,`month` = ?,`allocated` = ?,`carryover` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final BudgetEntity entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getCategoryId());
        statement.bindString(3, entity.getMonth());
        statement.bindString(4, entity.getAllocated());
        statement.bindString(5, entity.getCarryover());
        statement.bindLong(6, entity.getId());
      }
    };
    this.__preparedStmtOfUpdateAllocated = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE budgets SET allocated = ? WHERE id = ?";
        return _query;
      }
    };
    this.__preparedStmtOfUpdateCarryover = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE budgets SET carryover = ? WHERE id = ?";
        return _query;
      }
    };
    this.__preparedStmtOfDeleteByCategoryAndMonth = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM budgets WHERE categoryId = ? AND month = ?";
        return _query;
      }
    };
    this.__preparedStmtOfCopyBudgetsFromPreviousMonth = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "\n"
                + "        INSERT OR IGNORE INTO budgets (categoryId, month, allocated, carryover)\n"
                + "        SELECT categoryId, ?, allocated, '0'\n"
                + "        FROM budgets WHERE month = ?\n"
                + "    ";
        return _query;
      }
    };
  }

  @Override
  public Object insert(final BudgetEntity budget, final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfBudgetEntity.insertAndReturnId(budget);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object insertAll(final List<BudgetEntity> budgets,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfBudgetEntity.insert(budgets);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object delete(final BudgetEntity budget, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfBudgetEntity.handle(budget);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object update(final BudgetEntity budget, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfBudgetEntity.handle(budget);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object updateAllocated(final long id, final String allocated,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfUpdateAllocated.acquire();
        int _argIndex = 1;
        _stmt.bindString(_argIndex, allocated);
        _argIndex = 2;
        _stmt.bindLong(_argIndex, id);
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfUpdateAllocated.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object updateCarryover(final long id, final String carryover,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfUpdateCarryover.acquire();
        int _argIndex = 1;
        _stmt.bindString(_argIndex, carryover);
        _argIndex = 2;
        _stmt.bindLong(_argIndex, id);
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfUpdateCarryover.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteByCategoryAndMonth(final long categoryId, final String month,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteByCategoryAndMonth.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, categoryId);
        _argIndex = 2;
        _stmt.bindString(_argIndex, month);
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfDeleteByCategoryAndMonth.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object copyBudgetsFromPreviousMonth(final String previousMonth, final String newMonth,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfCopyBudgetsFromPreviousMonth.acquire();
        int _argIndex = 1;
        _stmt.bindString(_argIndex, newMonth);
        _argIndex = 2;
        _stmt.bindString(_argIndex, previousMonth);
        try {
          __db.beginTransaction();
          try {
            _stmt.executeInsert();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfCopyBudgetsFromPreviousMonth.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<BudgetWithCategoryEntity>> getBudgetsForMonth(final String month) {
    final String _sql = "\n"
            + "        SELECT b.id, b.categoryId, b.month, b.allocated, b.carryover,\n"
            + "               c.name as categoryName, c.color as categoryColor,\n"
            + "               c.icon as categoryIcon, c.groupName as categoryGroupName\n"
            + "        FROM budgets b\n"
            + "        INNER JOIN categories c ON b.categoryId = c.id\n"
            + "        WHERE b.month = ?\n"
            + "        ORDER BY c.groupName, c.sortOrder, c.name\n"
            + "    ";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, month);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"budgets",
        "categories"}, new Callable<List<BudgetWithCategoryEntity>>() {
      @Override
      @NonNull
      public List<BudgetWithCategoryEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = 0;
          final int _cursorIndexOfCategoryId = 1;
          final int _cursorIndexOfMonth = 2;
          final int _cursorIndexOfAllocated = 3;
          final int _cursorIndexOfCarryover = 4;
          final int _cursorIndexOfCategoryName = 5;
          final int _cursorIndexOfCategoryColor = 6;
          final int _cursorIndexOfCategoryIcon = 7;
          final int _cursorIndexOfCategoryGroupName = 8;
          final List<BudgetWithCategoryEntity> _result = new ArrayList<BudgetWithCategoryEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final BudgetWithCategoryEntity _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpCategoryId;
            _tmpCategoryId = _cursor.getLong(_cursorIndexOfCategoryId);
            final String _tmpMonth;
            _tmpMonth = _cursor.getString(_cursorIndexOfMonth);
            final String _tmpAllocated;
            _tmpAllocated = _cursor.getString(_cursorIndexOfAllocated);
            final String _tmpCarryover;
            _tmpCarryover = _cursor.getString(_cursorIndexOfCarryover);
            final String _tmpCategoryName;
            _tmpCategoryName = _cursor.getString(_cursorIndexOfCategoryName);
            final long _tmpCategoryColor;
            _tmpCategoryColor = _cursor.getLong(_cursorIndexOfCategoryColor);
            final String _tmpCategoryIcon;
            _tmpCategoryIcon = _cursor.getString(_cursorIndexOfCategoryIcon);
            final String _tmpCategoryGroupName;
            _tmpCategoryGroupName = _cursor.getString(_cursorIndexOfCategoryGroupName);
            _item = new BudgetWithCategoryEntity(_tmpId,_tmpCategoryId,_tmpMonth,_tmpAllocated,_tmpCarryover,_tmpCategoryName,_tmpCategoryColor,_tmpCategoryIcon,_tmpCategoryGroupName);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Flow<List<BudgetEntity>> getBudgetEntitiesForMonth(final String month) {
    final String _sql = "SELECT * FROM budgets WHERE month = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, month);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"budgets"}, new Callable<List<BudgetEntity>>() {
      @Override
      @NonNull
      public List<BudgetEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfCategoryId = CursorUtil.getColumnIndexOrThrow(_cursor, "categoryId");
          final int _cursorIndexOfMonth = CursorUtil.getColumnIndexOrThrow(_cursor, "month");
          final int _cursorIndexOfAllocated = CursorUtil.getColumnIndexOrThrow(_cursor, "allocated");
          final int _cursorIndexOfCarryover = CursorUtil.getColumnIndexOrThrow(_cursor, "carryover");
          final List<BudgetEntity> _result = new ArrayList<BudgetEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final BudgetEntity _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpCategoryId;
            _tmpCategoryId = _cursor.getLong(_cursorIndexOfCategoryId);
            final String _tmpMonth;
            _tmpMonth = _cursor.getString(_cursorIndexOfMonth);
            final String _tmpAllocated;
            _tmpAllocated = _cursor.getString(_cursorIndexOfAllocated);
            final String _tmpCarryover;
            _tmpCarryover = _cursor.getString(_cursorIndexOfCarryover);
            _item = new BudgetEntity(_tmpId,_tmpCategoryId,_tmpMonth,_tmpAllocated,_tmpCarryover);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Object getBudgetByCategoryAndMonth(final long categoryId, final String month,
      final Continuation<? super BudgetEntity> $completion) {
    final String _sql = "SELECT * FROM budgets WHERE categoryId = ? AND month = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, categoryId);
    _argIndex = 2;
    _statement.bindString(_argIndex, month);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<BudgetEntity>() {
      @Override
      @Nullable
      public BudgetEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfCategoryId = CursorUtil.getColumnIndexOrThrow(_cursor, "categoryId");
          final int _cursorIndexOfMonth = CursorUtil.getColumnIndexOrThrow(_cursor, "month");
          final int _cursorIndexOfAllocated = CursorUtil.getColumnIndexOrThrow(_cursor, "allocated");
          final int _cursorIndexOfCarryover = CursorUtil.getColumnIndexOrThrow(_cursor, "carryover");
          final BudgetEntity _result;
          if (_cursor.moveToFirst()) {
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpCategoryId;
            _tmpCategoryId = _cursor.getLong(_cursorIndexOfCategoryId);
            final String _tmpMonth;
            _tmpMonth = _cursor.getString(_cursorIndexOfMonth);
            final String _tmpAllocated;
            _tmpAllocated = _cursor.getString(_cursorIndexOfAllocated);
            final String _tmpCarryover;
            _tmpCarryover = _cursor.getString(_cursorIndexOfCarryover);
            _result = new BudgetEntity(_tmpId,_tmpCategoryId,_tmpMonth,_tmpAllocated,_tmpCarryover);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Object getBudgetById(final long id, final Continuation<? super BudgetEntity> $completion) {
    final String _sql = "SELECT * FROM budgets WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<BudgetEntity>() {
      @Override
      @Nullable
      public BudgetEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfCategoryId = CursorUtil.getColumnIndexOrThrow(_cursor, "categoryId");
          final int _cursorIndexOfMonth = CursorUtil.getColumnIndexOrThrow(_cursor, "month");
          final int _cursorIndexOfAllocated = CursorUtil.getColumnIndexOrThrow(_cursor, "allocated");
          final int _cursorIndexOfCarryover = CursorUtil.getColumnIndexOrThrow(_cursor, "carryover");
          final BudgetEntity _result;
          if (_cursor.moveToFirst()) {
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpCategoryId;
            _tmpCategoryId = _cursor.getLong(_cursorIndexOfCategoryId);
            final String _tmpMonth;
            _tmpMonth = _cursor.getString(_cursorIndexOfMonth);
            final String _tmpAllocated;
            _tmpAllocated = _cursor.getString(_cursorIndexOfAllocated);
            final String _tmpCarryover;
            _tmpCarryover = _cursor.getString(_cursorIndexOfCarryover);
            _result = new BudgetEntity(_tmpId,_tmpCategoryId,_tmpMonth,_tmpAllocated,_tmpCarryover);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Flow<Double> getTotalBudgetedForMonth(final String month) {
    final String _sql = "SELECT SUM(CAST(allocated AS REAL)) FROM budgets WHERE month = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, month);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"budgets"}, new Callable<Double>() {
      @Override
      @Nullable
      public Double call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Double _result;
          if (_cursor.moveToFirst()) {
            final Double _tmp;
            if (_cursor.isNull(0)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getDouble(0);
            }
            _result = _tmp;
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
