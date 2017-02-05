package cn.blinkmind.flame.server.bean;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.collections.CollectionUtils;

import java.util.LinkedHashSet;
import java.util.Set;

public class Diffs<E> {

    private Set<E> addedCollection;
    private Set<E> modifiedCollection;
    private Set<E> removedCollection;
    private Set<E> reorderedCollection;

    @JsonProperty("added")
    public Set<E> getAddedCollection() {
        if (this.addedCollection == null)
            this.addedCollection = new LinkedHashSet<>();
        return this.addedCollection;
    }

    private void setAddedCollection(Set<E> addedCollection) {
        this.addedCollection = addedCollection;
    }

    @JsonProperty("modified")
    public Set<E> getModifiedCollection() {
        if (this.modifiedCollection == null)
            this.modifiedCollection = new LinkedHashSet<>();
        return this.modifiedCollection;
    }

    private void setModifiedCollection(Set<E> modifiedCollection) {
        this.modifiedCollection = modifiedCollection;
    }

    @JsonProperty("removed")
    public Set<E> getRemovedCollection() {
        if (this.removedCollection == null)
            this.removedCollection = new LinkedHashSet<>();
        return this.removedCollection;
    }

    private void setRemovedCollection(Set<E> removedCollection) {
        this.removedCollection = removedCollection;
    }

    @JsonProperty("reordered")
    public Set<E> getReorderedCollection() {
        return reorderedCollection;
    }

    private void setReorderedCollection(Set<E> reorderedCollection) {
        if (this.reorderedCollection == null)
            this.reorderedCollection = new LinkedHashSet<>();
        this.reorderedCollection = reorderedCollection;
    }

    public void add(DiffResult diffResult, E diff) {
        if (diffResult == null) throw new NullPointerException();
        switch (diffResult) {
            case ADDED:
                getAddedCollection().add(diff);
                break;
            case MODIFIED:
                getModifiedCollection().add(diff);
                break;
            case REMOVED:
                getRemovedCollection().add(diff);
                break;
            case REORDERED:
                getReorderedCollection().add(diff);
                break;
            default:
                return;
        }
    }

    public boolean isNotEmpty() {
        return CollectionUtils.isNotEmpty(addedCollection)
                || CollectionUtils.isNotEmpty(modifiedCollection)
                || CollectionUtils.isNotEmpty(removedCollection)
                || CollectionUtils.isNotEmpty(reorderedCollection);
    }

    public boolean isEmpty() {
        return !isNotEmpty();
    }

    public static boolean isEmpty(Diffs diffs) {
        return diffs.isEmpty();
    }

    public static boolean isNotEmpty(Diffs diffs) {
        return diffs.isNotEmpty();
    }
}