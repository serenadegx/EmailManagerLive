package com.example.emailmanagerlive.data;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "EMAIL".
*/
public class EmailDao extends AbstractDao<Email, Long> {

    public static final String TABLENAME = "EMAIL";

    /**
     * Properties of entity Email.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property IsRead = new Property(1, boolean.class, "isRead", false, "IS_READ");
        public final static Property Subject = new Property(2, String.class, "subject", false, "SUBJECT");
        public final static Property Date = new Property(3, String.class, "date", false, "DATE");
        public final static Property From = new Property(4, String.class, "from", false, "FROM");
        public final static Property Personal = new Property(5, String.class, "personal", false, "PERSONAL");
        public final static Property To = new Property(6, String.class, "to", false, "TO");
        public final static Property Cc = new Property(7, String.class, "cc", false, "CC");
        public final static Property Bcc = new Property(8, String.class, "bcc", false, "BCC");
        public final static Property Content = new Property(9, String.class, "content", false, "CONTENT");
        public final static Property HasAttach = new Property(10, boolean.class, "hasAttach", false, "HAS_ATTACH");
    }


    public EmailDao(DaoConfig config) {
        super(config);
    }
    
    public EmailDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"EMAIL\" (" + //
                "\"_id\" INTEGER PRIMARY KEY ," + // 0: id
                "\"IS_READ\" INTEGER NOT NULL ," + // 1: isRead
                "\"SUBJECT\" TEXT," + // 2: subject
                "\"DATE\" TEXT," + // 3: date
                "\"FROM\" TEXT," + // 4: from
                "\"PERSONAL\" TEXT," + // 5: personal
                "\"TO\" TEXT," + // 6: to
                "\"CC\" TEXT," + // 7: cc
                "\"BCC\" TEXT," + // 8: bcc
                "\"CONTENT\" TEXT," + // 9: content
                "\"HAS_ATTACH\" INTEGER NOT NULL );"); // 10: hasAttach
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"EMAIL\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, Email entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
        stmt.bindLong(2, entity.getIsRead() ? 1L: 0L);
 
        String subject = entity.getSubject();
        if (subject != null) {
            stmt.bindString(3, subject);
        }
 
        String date = entity.getDate();
        if (date != null) {
            stmt.bindString(4, date);
        }
 
        String from = entity.getFrom();
        if (from != null) {
            stmt.bindString(5, from);
        }
 
        String personal = entity.getPersonal();
        if (personal != null) {
            stmt.bindString(6, personal);
        }
 
        String to = entity.getTo();
        if (to != null) {
            stmt.bindString(7, to);
        }
 
        String cc = entity.getCc();
        if (cc != null) {
            stmt.bindString(8, cc);
        }
 
        String bcc = entity.getBcc();
        if (bcc != null) {
            stmt.bindString(9, bcc);
        }
 
        String content = entity.getContent();
        if (content != null) {
            stmt.bindString(10, content);
        }
        stmt.bindLong(11, entity.getHasAttach() ? 1L: 0L);
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, Email entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
        stmt.bindLong(2, entity.getIsRead() ? 1L: 0L);
 
        String subject = entity.getSubject();
        if (subject != null) {
            stmt.bindString(3, subject);
        }
 
        String date = entity.getDate();
        if (date != null) {
            stmt.bindString(4, date);
        }
 
        String from = entity.getFrom();
        if (from != null) {
            stmt.bindString(5, from);
        }
 
        String personal = entity.getPersonal();
        if (personal != null) {
            stmt.bindString(6, personal);
        }
 
        String to = entity.getTo();
        if (to != null) {
            stmt.bindString(7, to);
        }
 
        String cc = entity.getCc();
        if (cc != null) {
            stmt.bindString(8, cc);
        }
 
        String bcc = entity.getBcc();
        if (bcc != null) {
            stmt.bindString(9, bcc);
        }
 
        String content = entity.getContent();
        if (content != null) {
            stmt.bindString(10, content);
        }
        stmt.bindLong(11, entity.getHasAttach() ? 1L: 0L);
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public Email readEntity(Cursor cursor, int offset) {
        Email entity = new Email( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.getShort(offset + 1) != 0, // isRead
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // subject
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // date
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // from
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // personal
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // to
            cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7), // cc
            cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8), // bcc
            cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9), // content
            cursor.getShort(offset + 10) != 0 // hasAttach
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, Email entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setIsRead(cursor.getShort(offset + 1) != 0);
        entity.setSubject(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setDate(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setFrom(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setPersonal(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setTo(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setCc(cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7));
        entity.setBcc(cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8));
        entity.setContent(cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9));
        entity.setHasAttach(cursor.getShort(offset + 10) != 0);
     }
    
    @Override
    protected final Long updateKeyAfterInsert(Email entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(Email entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(Email entity) {
        return entity.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}