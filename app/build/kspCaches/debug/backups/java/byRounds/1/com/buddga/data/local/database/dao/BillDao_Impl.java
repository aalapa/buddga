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
import com.buddga.data.local.database.entity.BillEntity;
import com.buddga.data.local.database.entity.BillWithCategoryEntity;
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
public final class BillDao_Impl implements BillDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<BillEntity> __insertionAdapterOfBillEntity;

  private final EntityDeletionOrUpdateAdapter<BillEntity> __deletionAdapterOfBillEntity;

  private final EntityDeletionOrUpdateAdapter<BillEntity> __updateAdapterOfBillEntity;

  private final SharedSQLiteStatement __preparedStmtOfUpdatePaidStatus;

  private final SharedSQLiteStatement __preparedStmtOfDeleteById;

  public BillDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfBillEntity = new EntityInsertionAdapter<BillEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `bills` (`id`,`name`,`amount`,`dueDate`,`frequency`,`categoryId`,`accountId`,`isPaid`,`isAutoPay`,`reminderDaysBefore`,`notes`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final BillEntity entity) {
        statement.bindLong(1, entity.getId());
        statement.bindString(2, entity.getName());
        statement.bindString(3, entity.getAmount());
        statement.bindLong(4, entity.getDueDate());
        statement.bindString(5, entity.getFrequency());
        if (entity.getCategoryId() == null) {
          statement.bindNull(6);
        } else {
          statement.bindLong(6, entity.getCategoryId());
        }
        if (entity.getAccountId() == null) {
          statement.bindNull(7);
        } else {
          statement.bindLong(7, entity.getAccountId());
        }
        final int _tmp = entity.isPaid() ? 1 : 0;
        statement.bindLong(8, _tmp);
        final int _tmp_1 = entity.isAutoPay() ? 1 : 0;
        statement.bindLong(9, _tmp_1);
        statement.bindLong(10, entity.getReminderDaysBefore());
        statement.bindString(11, entity.getNotes());
      }
    };
    this.__deletionAdapterOfBillEntity = new EntityDeletionOrUpdateAdapter<BillEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `bills` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final BillEntity entity) {
        statement.bindLong(1, entity.getId());
      }
    };
    this.__updateAdapterOfBillEntity = new EntityDeletionOrUpdateAdapter<BillEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `bills` SET `id` = ?,`name` = ?,`amount` = ?,`dueDate` = ?,`frequency` = ?,`categoryId` = ?,`accountId` = ?,`isPaid` = ?,`isAutoPay` = ?,`reminderDaysBefore` = ?,`notes` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final BillEntity entity) {
        statement.bindLong(1, entity.getId());
        statement.bindString(2, entity.getName());
        statement.bindString(3, entity.getAmount());
        statement.bindLong(4, entity.getDueDate());
        statement.bindString(5, entity.getFrequency());
        if (entity.getCategoryId() == null) {
          statement.bindNull(6);
        } else {
          statement.bindLong(6, entity.getCategoryId());
        }
        if (entity.getAccountId() == null) {
          statement.bindNull(7);
        } else {
          statement.bindLong(7, entity.getAccountId());
        }
        final int _tmp = entity.isPaid() ? 1 : 0;
        statement.bindLong(8, _tmp);
        final int _tmp_1 = entity.isAutoPay() ? 1 : 0;
        statement.bindLong(9, _tmp_1);
        statement.bindLong(10, entity.getReminderDaysBefore());
        statement.bindString(11, entity.getNotes());
        statement.bindLong(12, entity.getId());
      }
    };
    this.__preparedStmtOfUpdatePaidStatus = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE bills SET isPaid = ? WHERE id = ?";
        return _query;
      }
    };
    this.__preparedStmtOfDeleteById = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM bills WHERE id = ?";
        return _query;
      }
    };
  }

  @Override
  public Object insert(final BillEntity bill, final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfBillEntity.insertAndReturnId(bill);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object insertAll(final List<BillEntity> bills,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfBillEntity.insert(bills);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object delete(final BillEntity bill, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfBillEntity.handle(bill);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object update(final BillEntity bill, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfBillEntity.handle(bill);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object updatePaidStatus(final long id, final boolean isPaid,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfUpdatePaidStatus.acquire();
        int _argIndex = 1;
        final int _tmp = isPaid ? 1 : 0;
        _stmt.bindLong(_argIndex, _tmp);
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
          __preparedStmtOfUpdatePaidStatus.release(_stmt);
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
  public Flow<List<BillWithCategoryEntity>> getAllBillsWithCategory() {
    final String _sql = "\n"
            + "        SELECT b.id, b.name, b.amount, b.dueDate, b.frequency, b.categoryId,\n"
            + "               b.isPaid, b.isAutoPay, b.reminderDaysBefore,\n"
            + "               c.name as categoryName, c.color as categoryColor\n"
            + "        FROM bills b\n"
            + "        LEFT JOIN categories c ON b.categoryId = c.id\n"
            + "        ORDER BY b.dueDate ASC\n"
            + "    ";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"bills",
        "categories"}, new Callable<List<BillWithCategoryEntity>>() {
      @Override
      @NonNull
      public List<BillWithCategoryEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = 0;
          final int _cursorIndexOfName = 1;
          final int _cursorIndexOfAmount = 2;
          final int _cursorIndexOfDueDate = 3;
          final int _cursorIndexOfFrequency = 4;
          final int _cursorIndexOfCategoryId = 5;
          final int _cursorIndexOfIsPaid = 6;
          final int _cursorIndexOfIsAutoPay = 7;
          final int _cursorIndexOfReminderDaysBefore = 8;
          final int _cursorIndexOfCategoryName = 9;
          final int _cursorIndexOfCategoryColor = 10;
          final List<BillWithCategoryEntity> _result = new ArrayList<BillWithCategoryEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final BillWithCategoryEntity _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final String _tmpAmount;
            _tmpAmount = _cursor.getString(_cursorIndexOfAmount);
            final long _tmpDueDate;
            _tmpDueDate = _cursor.getLong(_cursorIndexOfDueDate);
            final String _tmpFrequency;
            _tmpFrequency = _cursor.getString(_cursorIndexOfFrequency);
            final Long _tmpCategoryId;
            if (_cursor.isNull(_cursorIndexOfCategoryId)) {
              _tmpCategoryId = null;
            } else {
              _tmpCategoryId = _cursor.getLong(_cursorIndexOfCategoryId);
            }
            final boolean _tmpIsPaid;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsPaid);
            _tmpIsPaid = _tmp != 0;
            final boolean _tmpIsAutoPay;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfIsAutoPay);
            _tmpIsAutoPay = _tmp_1 != 0;
            final int _tmpReminderDaysBefore;
            _tmpReminderDaysBefore = _cursor.getInt(_cursorIndexOfReminderDaysBefore);
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
            _item = new BillWithCategoryEntity(_tmpId,_tmpName,_tmpAmount,_tmpDueDate,_tmpFrequency,_tmpCategoryId,_tmpIsPaid,_tmpIsAutoPay,_tmpReminderDaysBefore,_tmpCategoryName,_tmpCategoryColor);
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
  public Flow<List<BillWithCategoryEntity>> getBillsByDateRange(final long startDate,
      final long endDate) {
    final String _sql = "\n"
            + "        SELECT b.id, b.name, b.amount, b.dueDate, b.frequency, b.categoryId,\n"
            + "               b.isPaid, b.isAutoPay, b.reminderDaysBefore,\n"
            + "               c.name as categoryName, c.color as categoryColor\n"
            + "        FROM bills b\n"
            + "        LEFT JOIN categories c ON b.categoryId = c.id\n"
            + "        WHERE b.dueDate >= ? AND b.dueDate <= ?\n"
            + "        ORDER BY b.dueDate ASC\n"
            + "    ";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, startDate);
    _argIndex = 2;
    _statement.bindLong(_argIndex, endDate);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"bills",
        "categories"}, new Callable<List<BillWithCategoryEntity>>() {
      @Override
      @NonNull
      public List<BillWithCategoryEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = 0;
          final int _cursorIndexOfName = 1;
          final int _cursorIndexOfAmount = 2;
          final int _cursorIndexOfDueDate = 3;
          final int _cursorIndexOfFrequency = 4;
          final int _cursorIndexOfCategoryId = 5;
          final int _cursorIndexOfIsPaid = 6;
          final int _cursorIndexOfIsAutoPay = 7;
          final int _cursorIndexOfReminderDaysBefore = 8;
          final int _cursorIndexOfCategoryName = 9;
          final int _cursorIndexOfCategoryColor = 10;
          final List<BillWithCategoryEntity> _result = new ArrayList<BillWithCategoryEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final BillWithCategoryEntity _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final String _tmpAmount;
            _tmpAmount = _cursor.getString(_cursorIndexOfAmount);
            final long _tmpDueDate;
            _tmpDueDate = _cursor.getLong(_cursorIndexOfDueDate);
            final String _tmpFrequency;
            _tmpFrequency = _cursor.getString(_cursorIndexOfFrequency);
            final Long _tmpCategoryId;
            if (_cursor.isNull(_cursorIndexOfCategoryId)) {
              _tmpCategoryId = null;
            } else {
              _tmpCategoryId = _cursor.getLong(_cursorIndexOfCategoryId);
            }
            final boolean _tmpIsPaid;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsPaid);
            _tmpIsPaid = _tmp != 0;
            final boolean _tmpIsAutoPay;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfIsAutoPay);
            _tmpIsAutoPay = _tmp_1 != 0;
            final int _tmpReminderDaysBefore;
            _tmpReminderDaysBefore = _cursor.getInt(_cursorIndexOfReminderDaysBefore);
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
            _item = new BillWithCategoryEntity(_tmpId,_tmpName,_tmpAmount,_tmpDueDate,_tmpFrequency,_tmpCategoryId,_tmpIsPaid,_tmpIsAutoPay,_tmpReminderDaysBefore,_tmpCategoryName,_tmpCategoryColor);
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
  public Flow<List<BillWithCategoryEntity>> getUpcomingUnpaidBills(final long today,
      final int limit) {
    final String _sql = "\n"
            + "        SELECT b.id, b.name, b.amount, b.dueDate, b.frequency, b.categoryId,\n"
            + "               b.isPaid, b.isAutoPay, b.reminderDaysBefore,\n"
            + "               c.name as categoryName, c.color as categoryColor\n"
            + "        FROM bills b\n"
            + "        LEFT JOIN categories c ON b.categoryId = c.id\n"
            + "        WHERE b.dueDate >= ? AND b.isPaid = 0\n"
            + "        ORDER BY b.dueDate ASC\n"
            + "        LIMIT ?\n"
            + "    ";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, today);
    _argIndex = 2;
    _statement.bindLong(_argIndex, limit);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"bills",
        "categories"}, new Callable<List<BillWithCategoryEntity>>() {
      @Override
      @NonNull
      public List<BillWithCategoryEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = 0;
          final int _cursorIndexOfName = 1;
          final int _cursorIndexOfAmount = 2;
          final int _cursorIndexOfDueDate = 3;
          final int _cursorIndexOfFrequency = 4;
          final int _cursorIndexOfCategoryId = 5;
          final int _cursorIndexOfIsPaid = 6;
          final int _cursorIndexOfIsAutoPay = 7;
          final int _cursorIndexOfReminderDaysBefore = 8;
          final int _cursorIndexOfCategoryName = 9;
          final int _cursorIndexOfCategoryColor = 10;
          final List<BillWithCategoryEntity> _result = new ArrayList<BillWithCategoryEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final BillWithCategoryEntity _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final String _tmpAmount;
            _tmpAmount = _cursor.getString(_cursorIndexOfAmount);
            final long _tmpDueDate;
            _tmpDueDate = _cursor.getLong(_cursorIndexOfDueDate);
            final String _tmpFrequency;
            _tmpFrequency = _cursor.getString(_cursorIndexOfFrequency);
            final Long _tmpCategoryId;
            if (_cursor.isNull(_cursorIndexOfCategoryId)) {
              _tmpCategoryId = null;
            } else {
              _tmpCategoryId = _cursor.getLong(_cursorIndexOfCategoryId);
            }
            final boolean _tmpIsPaid;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsPaid);
            _tmpIsPaid = _tmp != 0;
            final boolean _tmpIsAutoPay;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfIsAutoPay);
            _tmpIsAutoPay = _tmp_1 != 0;
            final int _tmpReminderDaysBefore;
            _tmpReminderDaysBefore = _cursor.getInt(_cursorIndexOfReminderDaysBefore);
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
            _item = new BillWithCategoryEntity(_tmpId,_tmpName,_tmpAmount,_tmpDueDate,_tmpFrequency,_tmpCategoryId,_tmpIsPaid,_tmpIsAutoPay,_tmpReminderDaysBefore,_tmpCategoryName,_tmpCategoryColor);
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
  public Object getBillsDueForReminder(final long startDate, final long endDate,
      final Continuation<? super List<BillEntity>> $completion) {
    final String _sql = "\n"
            + "        SELECT * FROM bills\n"
            + "        WHERE dueDate >= ? AND dueDate <= ? AND isPaid = 0\n"
            + "    ";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, startDate);
    _argIndex = 2;
    _statement.bindLong(_argIndex, endDate);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<BillEntity>>() {
      @Override
      @NonNull
      public List<BillEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfAmount = CursorUtil.getColumnIndexOrThrow(_cursor, "amount");
          final int _cursorIndexOfDueDate = CursorUtil.getColumnIndexOrThrow(_cursor, "dueDate");
          final int _cursorIndexOfFrequency = CursorUtil.getColumnIndexOrThrow(_cursor, "frequency");
          final int _cursorIndexOfCategoryId = CursorUtil.getColumnIndexOrThrow(_cursor, "categoryId");
          final int _cursorIndexOfAccountId = CursorUtil.getColumnIndexOrThrow(_cursor, "accountId");
          final int _cursorIndexOfIsPaid = CursorUtil.getColumnIndexOrThrow(_cursor, "isPaid");
          final int _cursorIndexOfIsAutoPay = CursorUtil.getColumnIndexOrThrow(_cursor, "isAutoPay");
          final int _cursorIndexOfReminderDaysBefore = CursorUtil.getColumnIndexOrThrow(_cursor, "reminderDaysBefore");
          final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
          final List<BillEntity> _result = new ArrayList<BillEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final BillEntity _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final String _tmpAmount;
            _tmpAmount = _cursor.getString(_cursorIndexOfAmount);
            final long _tmpDueDate;
            _tmpDueDate = _cursor.getLong(_cursorIndexOfDueDate);
            final String _tmpFrequency;
            _tmpFrequency = _cursor.getString(_cursorIndexOfFrequency);
            final Long _tmpCategoryId;
            if (_cursor.isNull(_cursorIndexOfCategoryId)) {
              _tmpCategoryId = null;
            } else {
              _tmpCategoryId = _cursor.getLong(_cursorIndexOfCategoryId);
            }
            final Long _tmpAccountId;
            if (_cursor.isNull(_cursorIndexOfAccountId)) {
              _tmpAccountId = null;
            } else {
              _tmpAccountId = _cursor.getLong(_cursorIndexOfAccountId);
            }
            final boolean _tmpIsPaid;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsPaid);
            _tmpIsPaid = _tmp != 0;
            final boolean _tmpIsAutoPay;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfIsAutoPay);
            _tmpIsAutoPay = _tmp_1 != 0;
            final int _tmpReminderDaysBefore;
            _tmpReminderDaysBefore = _cursor.getInt(_cursorIndexOfReminderDaysBefore);
            final String _tmpNotes;
            _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
            _item = new BillEntity(_tmpId,_tmpName,_tmpAmount,_tmpDueDate,_tmpFrequency,_tmpCategoryId,_tmpAccountId,_tmpIsPaid,_tmpIsAutoPay,_tmpReminderDaysBefore,_tmpNotes);
            _result.add(_item);
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
  public Object getBillById(final long id, final Continuation<? super BillEntity> $completion) {
    final String _sql = "SELECT * FROM bills WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<BillEntity>() {
      @Override
      @Nullable
      public BillEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfAmount = CursorUtil.getColumnIndexOrThrow(_cursor, "amount");
          final int _cursorIndexOfDueDate = CursorUtil.getColumnIndexOrThrow(_cursor, "dueDate");
          final int _cursorIndexOfFrequency = CursorUtil.getColumnIndexOrThrow(_cursor, "frequency");
          final int _cursorIndexOfCategoryId = CursorUtil.getColumnIndexOrThrow(_cursor, "categoryId");
          final int _cursorIndexOfAccountId = CursorUtil.getColumnIndexOrThrow(_cursor, "accountId");
          final int _cursorIndexOfIsPaid = CursorUtil.getColumnIndexOrThrow(_cursor, "isPaid");
          final int _cursorIndexOfIsAutoPay = CursorUtil.getColumnIndexOrThrow(_cursor, "isAutoPay");
          final int _cursorIndexOfReminderDaysBefore = CursorUtil.getColumnIndexOrThrow(_cursor, "reminderDaysBefore");
          final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
          final BillEntity _result;
          if (_cursor.moveToFirst()) {
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final String _tmpAmount;
            _tmpAmount = _cursor.getString(_cursorIndexOfAmount);
            final long _tmpDueDate;
            _tmpDueDate = _cursor.getLong(_cursorIndexOfDueDate);
            final String _tmpFrequency;
            _tmpFrequency = _cursor.getString(_cursorIndexOfFrequency);
            final Long _tmpCategoryId;
            if (_cursor.isNull(_cursorIndexOfCategoryId)) {
              _tmpCategoryId = null;
            } else {
              _tmpCategoryId = _cursor.getLong(_cursorIndexOfCategoryId);
            }
            final Long _tmpAccountId;
            if (_cursor.isNull(_cursorIndexOfAccountId)) {
              _tmpAccountId = null;
            } else {
              _tmpAccountId = _cursor.getLong(_cursorIndexOfAccountId);
            }
            final boolean _tmpIsPaid;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsPaid);
            _tmpIsPaid = _tmp != 0;
            final boolean _tmpIsAutoPay;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfIsAutoPay);
            _tmpIsAutoPay = _tmp_1 != 0;
            final int _tmpReminderDaysBefore;
            _tmpReminderDaysBefore = _cursor.getInt(_cursorIndexOfReminderDaysBefore);
            final String _tmpNotes;
            _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
            _result = new BillEntity(_tmpId,_tmpName,_tmpAmount,_tmpDueDate,_tmpFrequency,_tmpCategoryId,_tmpAccountId,_tmpIsPaid,_tmpIsAutoPay,_tmpReminderDaysBefore,_tmpNotes);
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
  public Flow<Double> getTotalUpcomingBills(final long startDate, final long endDate) {
    final String _sql = "SELECT SUM(CAST(amount AS REAL)) FROM bills WHERE dueDate >= ? AND dueDate <= ? AND isPaid = 0";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, startDate);
    _argIndex = 2;
    _statement.bindLong(_argIndex, endDate);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"bills"}, new Callable<Double>() {
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
