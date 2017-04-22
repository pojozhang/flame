package io.bayberry.repository;

import com.mongodb.DBObject;
import com.mongodb.WriteResult;
import io.bayberry.repository.model.Persistable;
import io.bayberry.repository.query.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.BulkOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import javax.annotation.PostConstruct;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import static org.springframework.data.mongodb.core.FindAndModifyOptions.options;

public abstract class AbstractMongoRepository<T extends Persistable<ID>, ID extends Serializable> {

    @Autowired
    private MongoTemplate mongoTemplate;
    private Class<T> entityClass;

    public T get(final ID id) {
        return id == null ? null : getMongoTemplate().findById(id, this.entityClass);
    }

    public T get(final Query query) {
        return getMongoTemplate().findOne(query, this.entityClass);
    }

    public boolean exists(final ID id) {
        return getMongoTemplate().exists(Query.query(Criteria.where(Keys.ID).is(id)), this.entityClass);
    }

    public boolean exists(final Query query) {
        return getMongoTemplate().exists(query, this.entityClass);
    }

    public List<T> list(final Query query) {
        return getMongoTemplate().find(query, this.entityClass);
    }

    public List<T> list() {
        return getMongoTemplate().findAll(this.entityClass);
    }

    public List<T> list(final Iterable<ID> ids) {
        return getMongoTemplate().find(Query.query(Criteria.where(Keys.ID).in(ids)), this.entityClass);
    }

    public T insert(final T entity) {
        getMongoTemplate().insert(entity);
        return entity;
    }

    public Iterable<T> insert(final Iterable<T> entities) {
        for (T entity : entities) {
            insert(entity);
        }
        return entities;
    }

    public T updateAndReturn(final T entity) {
        return updateAndReturn(Query.query(Criteria.where(Keys.ID).is(entity.getId())), entity);
    }

    public T updateAndReturn(final Query query, final T entity) {
        Update update = Update.fromDBObject((DBObject) getMongoTemplate().getConverter().convertToMongoType(entity));
        return updateAndReturn(query, update);
    }

    public T updateAndReturn(final Query query, final Update update) {
        return getMongoTemplate().findAndModify(query, update, options().upsert(false).returnNew(true), this.entityClass);
    }

    public WriteResult update(final T entity) {
        return update(Query.query(Criteria.where(Keys.ID).is(entity.getId())), entity);
    }

    public WriteResult update(final Query query, final T entity) {
        Update update = Update.fromDBObject((DBObject) getMongoTemplate().getConverter().convertToMongoType(entity));
        return update(query, update);
    }

    public WriteResult update(final Query query, final Update update) {
        return getMongoTemplate().updateMulti(query, update, this.entityClass);
    }

    public long count(final Query query) {
        return getMongoTemplate().count(query, this.entityClass);
    }

    public void delete(final ID id) {
        getMongoTemplate().remove(Query.query(Criteria.where("_id").is(id)), this.entityClass);
    }

    public void delete(final T entity) {
        getMongoTemplate().remove(entity);
    }

    public void delete(final Query query) {
        getMongoTemplate().remove(query, this.entityClass);
    }

    public void delete(final Iterable<T> entities) {
        for (T entity : entities) {
            delete(entity);
        }
    }

    public BulkOperations bulkOps(final BulkOperations.BulkMode bulkMode) {
        return getMongoTemplate().bulkOps(bulkMode, this.entityClass);
    }

    protected MongoTemplate getMongoTemplate() {
        return mongoTemplate;
    }

    @PostConstruct
    private void init() {
        this.entityClass = getEntityClass(this.getClass());
    }

    @SuppressWarnings("unchecked")
    private Class<T> getEntityClass(Class<?> repositoryClass) {
        Class<?> superClass = repositoryClass.getSuperclass();
        if (superClass == AbstractMongoRepository.class) {
            ParameterizedType parameterizedType = (ParameterizedType) repositoryClass.getGenericSuperclass();
            return (Class<T>) parameterizedType.getActualTypeArguments()[0];
        }
        return getEntityClass(superClass);
    }
}
