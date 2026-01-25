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
import com.buddga.data.local.database.entity.TransactionEntity;
import com.buddga.data.local.database.entity.TransactionWithDetailsEntity;
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
public final class TransactionDao_Impl implements TransactionDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<TransactionEntity> __insertionAdapterOfTransactionEntity;

  private final EntityDeletionOrUpdateAdapter<TransactionEntity> __deletionAdapterOfTransactionEntity;

  private final EntityDeletionOrUpdateAdapter<TransactionEntity> __updateAdapterOfTransactionEntity;

  private final SharedSQLiteStatement __preparedStmtOfUpdateReconciled;

  private final SharedSQLiteStatement __preparedStmtOfDeleteById;

  public TransactionDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfTransactionEntity = new EntityInsertionAdapter<TransactionEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `transactions` (`id`,`amount`,`type`,`categoryId`,`accountId`,`toAccountId`,`payee`,`memo`,`date`,`isReconciled`,`isCleared`,`createdAt`,`updatedAt`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final TransactionEntity entity) {
        statement.bindLong(1, entity.getId());
        statement.bindString(2, entity.getAmount());
        statement.bindString(3, entity.getType());
        if (entity.getCategoryId() == null) {
          statement.bindNull(4);
        } else {
          statement.bindLong(4, entity.getCategoryId());
        }
        statement.bindLong(5, entity.getAccountId());
        if (entity.getToAccountId() == null) {
          statement.bindNull(6);
        } else {
          statement.bindLong(6, entity.getToAccountId());
        }
        statement.bindString(7, entity.getPayee());
        statement.bindString(8, entity.getMemo());
        statement.bindLong(9, entity.getDate());
        final int _tmp = entity.isReconciled() ? 1 : 0;
        statement.bindLong(10, _tmp);
        final int _tmp_1 = entity.isCleared() ? 1 : 0;
        statement.bindLong(11, _tmp_1);
        statement.bindLong(12, entity.getCreatedAt());
        statement.bindLong(13, entity.getUpdatedAt());
      }
    };
    this.__deletionAdapterOfTransactionEntity = new EntityDeletionOrUpdateAdapter<TransactionEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `transactions` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final TransactionEntity entity) {
        statement.bindLong(1, entity.getId());
      }
    };
    this.__updateAdapterOfTransactionEntity = new EntityDeletionOrUpdateAdapter<TransactionEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `transactions` SET `id` = ?,`amount` = ?,`type` = ?,`categoryId` = ?,`accountId` = ?,`toAccountId` = ?,`payee` = ?,`memo` = ?,`date` = ?,`isReconciled` = ?,`isCleared` = ?,`createdAt` = ?,`updatedAt` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final TransactionEntity entity) {
        statement.bindLong(1, entity.getId());
        statement.bindString(2, entity.getAmount());
        statement.bindString(3, entity.getType());
        if (entity.getCategoryId() == null) {
          statement.bindNull(4);
        } else {
          statement.bindLong(4, entity.getCategoryId());
        }
        statement.bindLong(5, entity.getAccountId());
        if (entity.getToAccountId() == null) {
          statement.bindNull(6);
        } else {
          statement.bindLong(6, entity.getToAccountId());
        }
        statement.bindString(7, entity.getPayee());
        statement.bindString(8, entity.getMemo());
        statement.bindLong(9, entity.getDate());
        final int _tmp = entity.isReconciled() ? 1 : 0;
        statement.bindLong(10, _tmp);
        final int _tmp_1 = entity.isCleared() ? 1 : 0;
        statement.bindLong(11, _tmp_1);
        statement.bindLong(12, entity.getCreatedAt());
        statement.bindLong(13, entity.getUpdatedAt());
        statement.bindLong(14, entity.getId());
      }
    };
    this.__preparedStmtOfUpdateReconciled = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE transactions SET isReconciled = ?, updatedAt = ? WHERE id = ?";
        return _query;
      }
    };
    this.__preparedStmtOfDeleteById = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM transactions WHERE id = ?";
        return _query;
      }
    };
  }

  @Override
  public Object insert(final TransactionEntity transaction,
      final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfTransactionEntity.insertAndReturnId(transaction);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object insertAll(final List<TransactionEntity> transactions,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfTransactionEntity.insert(transactions);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object delete(final TransactionEntity transaction,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfTransactionEntity.handle(transaction);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object update(final TransactionEntity transaction,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfTransactionEntity.handle(transaction);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object updateReconciled(final long id, final boolean isReconciled, final long updatedAt,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfUpdateReconciled.acquire();
        int _argIndex = 1;
        final int _tmp = isReconciled ? 1 : 0;
        _stmt.bindLong(_argIndex, _tmp);
        _argIndex = 2;
        _stmt.bindLong(_argIndex, updatedAt);
        _argIndex = 3;
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
          __preparedStmtOfUpdateReconciled.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteById(final long id, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteById.acquire();
        int _argIndex = 1;
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
          __preparedStmtOfDeleteById.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<TransactionWithDetailsEntity>> getAllTransactionsWithDetails() {
    final String _sql = "\n"
            + "        SELECT t.id, t.amount, t.type, t.categoryId, t.accountId, t.payee, t.memo,\n"
            + "               t.date, t.isReconciled, t.isCleared, c.name as categoryName,\n"
            + "               c.color as categoryColor, a.name as accountName\n"
            + "        FROM transactions t\n"
            + "        LEFT JOIN categories c ON t.categoryId = c.id\n"
            + "        INNER JOIN accounts a ON t.accountId = a.id\n"
            + "        ORDER BY t.date DESC, t.id DESC\n"
            + "    ";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"transactions", "categories",
        "accounts"}, new Callable<List<TransactionWithDetailsEntity>>() {
      @Override
      @NonNull
      public List<TransactionWithDetailsEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = 0;
          final int _cursorIndexOfAmount = 1;
          final int _cursorIndexOfType = 2;
          final int _cursorIndexOfCategoryId = 3;
          final int _cursorIndexOfAccountId = 4;
          final int _cursorIndexOfPayee = 5;
          final int _cursorIndexOfMemo = 6;
          final int _cursorIndexOfDate = 7;
          final int _cursorIndexOfIsReconciled = 8;
          final int _cursorIndexOfIsCleared = 9;
          final int _cursorIndexOfCategoryName = 10;
          final int _cursorIndexOfCategoryColor = 11;
          final int _cursorIndexOfAccountName = 12;
          final List<TransactionWithDetailsEntity> _result = new ArrayList<TransactionWithDetailsEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final TransactionWithDetailsEntity _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpAmount;
            _tmpAmount = _cursor.getString(_cursorIndexOfAmount);
            final String _tmpType;
            _tmpType = _cursor.getString(_cursorIndexOfType);
            final Long _tmpCategoryId;
            if (_cursor.isNull(_cursorIndexOfCategoryId)) {
              _tmpCategoryId = null;
            } else {
              _tmpCategoryId = _cursor.getLong(_cursorIndexOfCategoryId);
            }
            final long _tmpAccountId;
            _tmpAccountId = _cursor.getLong(_cursorIndexOfAccountId);
            final String _tmpPayee;
            _tmpPayee = _cursor.getString(_cursorIndexOfPayee);
            final String _tmpMemo;
            _tmpMemo = _cursor.getString(_cursorIndexOfMemo);
            final long _tmpDate;
            _tmpDate = _cursor.getLong(_cursorIndexOfDate);
            final boolean _tmpIsReconciled;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsReconciled);
            _tmpIsReconciled = _tmp != 0;
            final boolean _tmpIsCleared;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfIsCleared);
            _tmpIsCleared = _tmp_1 != 0;
            final String _tmpCategoryName;
            if (_cursor.isNull(_cursorIndexOfCategoryName)) {
              _tmpCategoryName = null;
            } else {
              _tmpCategoryName = _cursor.getString(_cursorIndexOfCategoryName);
            }
            final Long _tmpCategoryColor;
            if (_cursor.isNull(_cursorIndexOfCategoryColor)) {
              _tmpCategoryColor = null;
            } else {
              _tmpCategoryColor = _cursor.getLong(_cursorIndexOfCategoryColor);
            }
            final String _tmpAccountName;
            _tmpAccountName = _cursor.getString(_cursorIndexOfAccountName);
            _item = new TransactionWithDetailsEntity(_tmpId,_tmpAmount,_tmpType,_tmpCategoryId,_tmpAccountId,_tmpPayee,_tmpMemo,_tmpDate,_tmpIsReconciled,_tmpIsCleared,_tmpCategoryName,_tmpCategoryColor,_tmpAccountName);
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
  public Flow<List<TransactionWithDetailsEntity>> getTransactionsByAccount(final long accountId) {
    final String _sql = "\n"
            + "        SELECT t.id, t.amount, t.type, t.categoryId, t.accountId, t.payee, t.memo,\n"
            + "               t.date, t.isReconciled, t.isCleared, c.name as categoryName,\n"
            + "               c.color as categoryColor, a.name as accountName\n"
            + "        FROM transactions t\n"
            + "        LEFT JOIN categories c ON t.categoryId = c.id\n"
            + "        INNER JOIN accounts a ON t.accountId = a.id\n"
            + "        WHERE t.accountId = ?\n"
            + "        ORDER BY t.date DESC, t.id DESC\n"
            + "    ";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, accountId);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"transactions", "categories",
        "accounts"}, new Callable<List<TransactionWithDetailsEntity>>() {
      @Override
      @NonNull
      public List<TransactionWithDetailsEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = 0;
          final int _cursorIndexOfAmount = 1;
          final int _cursorIndexOfType = 2;
          final int _cursorIndexOfCategoryId = 3;
          final int _cursorIndexOfAccountId = 4;
          final int _cursorIndexOfPayee = 5;
          final int _cursorIndexOfMemo = 6;
          final int _cursorIndexOfDate = 7;
          final int _cursorIndexOfIsReconciled = 8;
          final int _cursorIndexOfIsCleared = 9;
          final int _cursorIndexOfCategoryName = 10;
          final int _cursorIndexOfCategoryColor = 11;
          final int _cursorIndexOfAccountName = 12;
          final List<TransactionWithDetailsEntity> _result = new ArrayList<TransactionWithDetailsEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final TransactionWithDetailsEntity _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpAmount;
            _tmpAmount = _cursor.getString(_cursorIndexOfAmount);
            final String _tmpType;
            _tmpType = _cursor.getString(_cursorIndexOfType);
            final Long _tmpCategoryId;
            if (_cursor.isNull(_cursorIndexOfCategoryId)) {
              _tmpCategoryId = null;
            } else {
              _tmpCategoryId = _cursor.getLong(_cursorIndexOfCategoryId);
            }
            final long _tmpAccountId;
            _tmpAccountId = _cursor.getLong(_cursorIndexOfAccountId);
            final String _tmpPayee;
            _tmpPayee = _cursor.getString(_cursorIndexOfPayee);
            final String _tmpMemo;
            _tmpMemo = _cursor.getString(_cursorIndexOfMemo);
            final long _tmpDate;
            _tmpDate = _cursor.getLong(_cursorIndexOfDate);
            final boolean _tmpIsReconciled;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsReconciled);
            _tmpIsReconciled = _tmp != 0;
            final boolean _tmpIsCleared;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfIsCleared);
            _tmpIsCleared = _tmp_1 != 0;
            final String _tmpCategoryName;
            if (_cursor.isNull(_cursorIndexOfCategoryName)) {
              _tmpCategoryName = null;
            } else {
              _tmpCategoryName = _cursor.getString(_cursorIndexOfCategoryName);
            }
            final Long _tmpCategoryColor;
            if (_cursor.isNull(_cursorIndexOfCategoryColor)) {
              _tmpCategoryColor = null;
            } else {
              _tmpCategoryColor = _cursor.getLong(_cursorIndexOfCategoryColor);
            }
            final String _tmpAccountName;
            _tmpAccountName = _cursor.getString(_cursorIndexOfAccountName);
            _item = new TransactionWithDetailsEntity(_tmpId,_tmpAmount,_tmpType,_tmpCategoryId,_tmpAccountId,_tmpPayee,_tmpMemo,_tmpDate,_tmpIsReconciled,_tmpIsCleared,_tmpCategoryName,_tmpCategoryColor,_tmpAccountName);
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
  public Flow<List<TransactionWithDetailsEntity>> getTransactionsByDateRange(final long startDate,
      final long endDate) {
    final String _sql = "\n"
            + "        SELECT t.id, t.amount, t.type, t.categoryId, t.accountId, t.payee, t.memo,\n"
            + "               t.date, t.isReconciled, t.isCleared, c.name as categoryName,\n"
            + "               c.color as categoryColor, a.name as accountName\n"
            + "        FROM transactions t\n"
            + "        LEFT JOIN categories c ON t.categoryId = c.id\n"
            + "        INNER JOIN accounts a ON t.accountId = a.id\n"
            + "        WHERE t.date >= ? AND t.date <= ?\n"
            + "        ORDER BY t.date DESC, t.id DESC\n"
            + "    ";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, startDate);
    _argIndex = 2;
    _statement.bindLong(_argIndex, endDate);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"transactions", "categories",
        "accounts"}, new Callable<List<TransactionWithDetailsEntity>>() {
      @Override
      @NonNull
      public List<TransactionWithDetailsEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = 0;
          final int _cursorIndexOfAmount = 1;
          final int _cursorIndexOfType = 2;
          final int _cursorIndexOfCategoryId = 3;
          final int _cursorIndexOfAccountId = 4;
          final int _cursorIndexOfPayee = 5;
          final int _cursorIndexOfMemo = 6;
          final int _cursorIndexOfDate = 7;
          final int _cursorIndexOfIsReconciled = 8;
          final int _cursorIndexOfIsCleared = 9;
          final int _cursorIndexOfCategoryName = 10;
          final int _cursorIndexOfCategoryColor = 11;
          final int _cursorIndexOfAccountName = 12;
          final List<TransactionWithDetailsEntity> _result = new ArrayList<TransactionWithDetailsEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final TransactionWithDetailsEntity _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpAmount;
            _tmpAmount = _cursor.getString(_cursorIndexOfAmount);
            final String _tmpType;
            _tmpType = _cursor.getString(_cursorIndexOfType);
            final Long _tmpCategoryId;
            if (_cursor.isNull(_cursorIndexOfCategoryId)) {
              _tmpCategoryId = null;
            } else {
              _tmpCategoryId = _cursor.getLong(_cursorIndexOfCategoryId);
            }
            final long _tmpAccountId;
            _tmpAccountId = _cursor.getLong(_cursorIndexOfAccountId);
            final String _tmpPayee;
            _tmpPayee = _cursor.getString(_cursorIndexOfPayee);
            final String _tmpMemo;
            _tmpMemo = _cursor.getString(_cursorIndexOfMemo);
            final long _tmpDate;
            _tmpDate = _cursor.getLong(_cursorIndexOfDate);
            final boolean _tmpIsReconciled;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsReconciled);
            _tmpIsReconciled = _tmp != 0;
            final boolean _tmpIsCleared;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfIsCleared);
            _tmpIsCleared = _tmp_1 != 0;
            final String _tmpCategoryName;
            if (_cursor.isNull(_cursorIndexOfCategoryName)) {
              _tmpCategoryName = null;
            } else {
              _tmpCategoryName = _cursor.getString(_cursorIndexOfCategoryName);
            }
            final Long _tmpCategoryColor;
            if (_cursor.isNull(_cursorIndexOfCategoryColor)) {
              _tmpCategoryColor = null;
            } else {
              _tmpCategoryColor = _cursor.getLong(_cursorIndexOfCategoryColor);
            }
            final String _tmpAccountName;
            _tmpAccountName = _cursor.getString(_cursorIndexOfAccountName);
            _item = new TransactionWithDetailsEntity(_tmpId,_tmpAmount,_tmpType,_tmpCategoryId,_tmpAccountId,_tmpPayee,_tmpMemo,_tmpDate,_tmpIsReconciled,_tmpIsCleared,_tmpCategoryName,_tmpCategoryColor,_tmpAccountName);
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
  public Object getTransactionById(final long id,
      final Continuation<? super TransactionEntity> $completion) {
    final String _sql = "SELECT * FROM transactions WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<TransactionEntity>() {
      @Override
      @Nullable
      public TransactionEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfAmount = CursorUtil.getColumnIndexOrThrow(_cursor, "amount");
          final int _cursorIndexOfType = CursorUtil.getColumnIndexOrThrow(_cursor, "type");
          final int _cursorIndexOfCategoryId = CursorUtil.getColumnIndexOrThrow(_cursor, "categoryId");
          final int _cursorIndexOfAccountId = CursorUtil.getColumnIndexOrThrow(_cursor, "accountId");
          final int _cursorIndexOfToAccountId = CursorUtil.getColumnIndexOrThrow(_cursor, "toAccountId");
          final int _cursorIndexOfPayee = CursorUtil.getColumnIndexOrThrow(_cursor, "payee");
          final int _cursorIndexOfMemo = CursorUtil.getColumnIndexOrThrow(_cursor, "memo");
          final int _cursorIndexOfDate = CursorUtil.getColumnIndexOrThrow(_cursor, "date");
          final int _cursorIndexOfIsReconciled = CursorUtil.getColumnIndexOrThrow(_cursor, "isReconciled");
          final int _cursorIndexOfIsCleared = CursorUtil.getColumnIndexOrThrow(_cursor, "isCleared");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
          final TransactionEntity _result;
          if (_cursor.moveToFirst()) {
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpAmount;
            _tmpAmount = _cursor.getString(_cursorIndexOfAmount);
            final String _tmpType;
            _tmpType = _cursor.getString(_cursorIndexOfType);
            final Long _tmpCategoryId;
            if (_cursor.isNull(_cursorIndexOfCategoryId)) {
              _tmpCategoryId = null;
            } else {
              _tmpCategoryId = _cursor.getLong(_cursorIndexOfCategoryId);
            }
            final long _tmpAccountId;
            _tmpAccountId = _cursor.getLong(_cursorIndexOfAccountId);
            final Long _tmpToAccountId;
            if (_cursor.isNull(_cursorIndexOfToAccountId)) {
              _tmpToAccountId = null;
            } else {
              _tmpToAccountId = _cursor.getLong(_cursorIndexOfToAccountId);
            }
            final String _tmpPayee;
            _tmpPayee = _cursor.getString(_cursorIndexOfPayee);
            final String _tmpMemo;
            _tmpMemo = _cursor.getString(_cursorIndexOfMemo);
            final long _tmpDate;
            _tmpDate = _cursor.getLong(_cursorIndexOfDate);
            final boolean _tmpIsReconciled;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsReconciled);
            _tmpIsReconciled = _tmp != 0;
            final boolean _tmpIsCleared;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfIsCleared);
            _tmpIsCleared = _tmp_1 != 0;
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final long _tmpUpdatedAt;
            _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
            _result = new TransactionEntity(_tmpId,_tmpAmount,_tmpType,_tmpCategoryId,_tmpAccountId,_tmpToAccountId,_tmpPayee,_tmpMemo,_tmpDate,_tmpIsReconciled,_tmpIsCleared,_tmpCreatedAt,_tmpUpdatedAt);
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
  public Flow<List<TransactionEntity>> getTransactionsByAccountId(final long accountId) {
    final String _sql = "SELECT * FROM transactions WHERE accountId = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, accountId);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"transactions"}, new Callable<List<TransactionEntity>>() {
      @Override
      @NonNull
      public List<TransactionEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfAmount = CursorUtil.getColumnIndexOrThrow(_cursor, "amount");
          final int _cursorIndexOfType = CursorUtil.getColumnIndexOrThrow(_cursor, "type");
          final int _cursorIndexOfCategoryId = CursorUtil.getColumnIndexOrThrow(_cursor, "categoryId");
          final int _cursorIndexOfAccountId = CursorUtil.getColumnIndexOrThrow(_cursor, "accountId");
          final int _cursorIndexOfToAccountId = CursorUtil.getColumnIndexOrThrow(_cursor, "toAccountId");
          final int _cursorIndexOfPayee = CursorUtil.getColumnIndexOrThrow(_cursor, "payee");
          final int _cursorIndexOfMemo = CursorUtil.getColumnIndexOrThrow(_cursor, "memo");
          final int _cursorIndexOfDate = CursorUtil.getColumnIndexOrThrow(_cursor, "date");
          final int _cursorIndexOfIsReconciled = CursorUtil.getColumnIndexOrThrow(_cursor, "isReconciled");
          final int _cursorIndexOfIsCleared = CursorUtil.getColumnIndexOrThrow(_cursor, "isCleared");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
          final List<TransactionEntity> _result = new ArrayList<TransactionEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final TransactionEntity _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpAmount;
            _tmpAmount = _cursor.getString(_cursorIndexOfAmount);
            final String _tmpType;
            _tmpType = _cursor.getString(_cursorIndexOfType);
            final Long _tmpCategoryId;
            if (_cursor.isNull(_cursorIndexOfCategoryId)) {
              _tmpCategoryId = null;
            } else {
              _tmpCategoryId = _cursor.getLong(_cursorIndexOfCategoryId);
            }
            final long _tmpAccountId;
            _tmpAccountId = _cursor.getLong(_cursorIndexOfAccountId);
            final Long _tmpToAccountId;
            if (_cursor.isNull(_cursorIndexOfToAccountId)) {
              _tmpToAccountId = null;
            } else {
              _tmpToAccountId = _cursor.getLong(_cursorIndexOfToAccountId);
            }
            final String _tmpPayee;
            _tmpPayee = _cursor.getString(_cursorIndexOfPayee);
            final String _tmpMemo;
            _tmpMemo = _cursor.getString(_cursorIndexOfMemo);
            final long _tmpDate;
            _tmpDate = _cursor.getLong(_cursorIndexOfDate);
            final boolean _tmpIsReconciled;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsReconciled);
            _tmpIsReconciled = _tmp != 0;
            final boolean _tmpIsCleared;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfIsCleared);
            _tmpIsCleared = _tmp_1 != 0;
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final long _tmpUpdatedAt;
            _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
            _item = new TransactionEntity(_tmpId,_tmpAmount,_tmpType,_tmpCategoryId,_tmpAccountId,_tmpToAccountId,_tmpPayee,_tmpMemo,_tmpDate,_tmpIsReconciled,_tmpIsCleared,_tmpCreatedAt,_tmpUpdatedAt);
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
  public Flow<Double> getSpentByCategory(final long categoryId, final long startDate,
      final long endDate) {
    final String _sql = "\n"
            + "        SELECT SUM(CAST(amount AS REAL)) FROM transactions\n"
            + "        WHERE categoryId = ? AND type = 'EXPENSE'\n"
            + "        AND date >= ? AND date <= ?\n"
            + "    ";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 3);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, categoryId);
    _argIndex = 2;
    _statement.bindLong(_argIndex, startDate);
    _argIndex = 3;
    _statement.bindLong(_argIndex, endDate);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"transactions"}, new Callable<Double>() {
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

  @Override
  public Flow<Double> getTotalIncome(final long startDate, final long endDate) {
    final String _sql = "\n"
            + "        SELECT SUM(CAST(amount AS REAL)) FROM transactions\n"
            + "        WHERE type = 'INCOME' AND date >= ? AND date <= ?\n"
            + "    ";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, startDate);
    _argIndex = 2;
    _statement.bindLong(_argIndex, endDate);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"transactions"}, new Callable<Double>() {
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

  @Override
  public Flow<Double> getTotalExpenses(final long startDate, final long endDate) {
    final String _sql = "\n"
            + "        SELECT SUM(CAST(amount AS REAL)) FROM transactions\n"
            + "        WHERE type = 'EXPENSE' AND date >= ? AND date <= ?\n"
            + "    ";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, startDate);
    _argIndex = 2;
    _statement.bindLong(_argIndex, endDate);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"transactions"}, new Callable<Double>() {
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

  @Override
  public Flow<Double> getNetCashFlow(final long startDate, final long endDate) {
    final String _sql = "\n"
            + "        SELECT SUM(CASE WHEN type = 'INCOME' THEN CAST(amount AS REAL)\n"
            + "                        WHEN type = 'EXPENSE' THEN -CAST(amount AS REAL)\n"
            + "                        ELSE 0 END)\n"
            + "        FROM transactions WHERE date >= ? AND date <= ?\n"
            + "    ";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, startDate);
    _argIndex = 2;
    _statement.bindLong(_argIndex, endDate);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"transactions"}, new Callable<Double>() {
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
