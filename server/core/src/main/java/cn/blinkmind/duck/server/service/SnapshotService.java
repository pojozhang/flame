package cn.blinkmind.duck.server.service;

import cn.blinkmind.duck.server.exception.Assertion;
import cn.blinkmind.duck.server.exception.Error;
import cn.blinkmind.duck.server.exception.Errors;
import cn.blinkmind.duck.server.repository.SnapshotRepository;
import cn.blinkmind.duck.server.repository.entity.Archive;
import cn.blinkmind.duck.server.repository.entity.Branch;
import cn.blinkmind.duck.server.repository.entity.Snapshot;
import cn.blinkmind.duck.server.repository.entity.User;
import cn.blinkmind.duck.server.repository.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SnapshotService {

    @Autowired
    private SnapshotRepository snapshotRepository;

    @Autowired
    private BranchService branchService;

    @Autowired
    private RepositoryService repositoryService;

    public Snapshot create(Snapshot snapshot, User creator) {
        Assertion.isFalse(snapshot.getBranch() == null || snapshot.getBranch().getId() == null, Errors.SNAPSHOT_BRANCH_IS_NOT_SPECIFIED);
        Branch branch = branchService.require(snapshot.getBranch().getId(), creator, Errors.BRANCH_IS_NOT_FOUND);
        Assertion.isFalse(snapshotRepository.exists(branch, creator), Errors.RESOURCE_ALREADY_EXISTS);
        snapshot.setId(repositoryService.newId());
        snapshot.setCreator(creator);
        snapshot.setBranch(branch);
        snapshot.setArchive(new Archive());
        snapshotRepository.insert(snapshot);
        return snapshot;
    }

    public Snapshot get(long id, User user) {
        return snapshotRepository.get(id);
    }

    public Snapshot require(long id, User user) {
        Snapshot snapshot = snapshotRepository.require(id);
        return snapshot;
    }

    public Snapshot require(long id, User user, Error error) {
        try {
            Snapshot snapshot = require(id, user);
            return snapshot;
        } catch (ResourceNotFoundException e) {
            Error.occurs(error);
            return null;
        }
    }

    public Snapshot get(Branch branch, User user) {
        Snapshot snapshot = snapshotRepository.get(branch, user);
        return snapshot;
    }

    public Snapshot require(Branch branch, User user) {
        Snapshot snapshot = get(branch, user);
        Assertion.notNull(snapshot, Errors.RESOURCE_NOT_FOUND);
        return snapshot;
    }

    public void delete(long id, User user) {
        snapshotRepository.delete(id);
    }

    public void updateArchive(long snapshotId, Archive archive, User user) {
        Snapshot snapshot = snapshotRepository.updateArchive(snapshotId, archive, user);
        Assertion.notNull(snapshot, Errors.RESOURCE_NOT_FOUND);
    }
}