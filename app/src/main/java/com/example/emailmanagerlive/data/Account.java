package com.example.emailmanagerlive.data;

import androidx.annotation.Nullable;
import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.ToOne;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;

@Entity
public class Account extends BaseObservable {
    @Id(autoincrement = true)
    private Long id;
    @NotNull
    private String account;
    @NotNull
    private String pwd;
    @NotNull
    private long configId;
    @ToOne(joinProperty = "configId")
    private Configuration config;
    private boolean isCur;
    private String personal;
    private String remark;
    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;
    /** Used for active entity operations. */
    @Generated(hash = 335469827)
    private transient AccountDao myDao;
    @Generated(hash = 1497256190)
    private transient Long config__resolvedKey;


    public Account() {
    }

    @Generated(hash = 1099617493)
    public Account(Long id, @NotNull String account, @NotNull String pwd,
            long configId, boolean isCur, String personal, String remark) {
        this.id = id;
        this.account = account;
        this.pwd = pwd;
        this.configId = configId;
        this.isCur = isCur;
        this.personal = personal;
        this.remark = remark;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public void setConfigId(long configId) {
        this.configId = configId;
    }

    public void setCur(boolean cur) {
        isCur = cur;
    }

    public void setPersonal(String personal) {
        this.personal = personal;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Long getId() {
        return id;
    }

    @Bindable
    public String getAccount() {
        return account;
    }

    public String getPwd() {
        return pwd;
    }

    public long getConfigId() {
        return configId;
    }

    @Bindable
    public boolean isCur() {
        return isCur;
    }

    public String getPersonal() {
        return personal;
    }

    public String getRemark() {
        return remark;
    }

    public boolean getIsCur() {
        return this.isCur;
    }

    public void setIsCur(boolean isCur) {
        this.isCur = isCur;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (obj instanceof Account) {
            Account account = (Account) obj;
            return account.getId() == this.getId();
        }
        return super.equals(obj);
    }

    /** To-one relationship, resolved on first access. */
    @Generated(hash = 432318118)
    public Configuration getConfig() {
        long __key = this.configId;
        if (config__resolvedKey == null || !config__resolvedKey.equals(__key)) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            ConfigurationDao targetDao = daoSession.getConfigurationDao();
            Configuration configNew = targetDao.load(__key);
            synchronized (this) {
                config = configNew;
                config__resolvedKey = __key;
            }
        }
        return config;
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 2109243491)
    public void setConfig(@NotNull Configuration config) {
        if (config == null) {
            throw new DaoException(
                    "To-one property 'configId' has not-null constraint; cannot set to-one to null");
        }
        synchronized (this) {
            this.config = config;
            configId = config.getCategoryId();
            config__resolvedKey = configId;
        }
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#delete(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 128553479)
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.delete(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#refresh(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 1942392019)
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.refresh(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#update(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 713229351)
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.update(this);
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1812283172)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getAccountDao() : null;
    }
}
