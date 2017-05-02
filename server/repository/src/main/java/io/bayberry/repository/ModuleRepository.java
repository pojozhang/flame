package io.bayberry.repository;

import io.bayberry.repository.model.Branch;
import io.bayberry.repository.model.Module;
import io.bayberry.repository.util.IdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;

import static io.bayberry.repository.query.Keys.ID;

@Repository
public class ModuleRepository extends AbstractMongoRepository<Branch, Long> {

    private final IdGenerator<Long> idGenerator;

    @Autowired
    public ModuleRepository(IdGenerator<Long> idGenerator) {
        this.idGenerator = idGenerator;
    }

    public Module create(Module module) {
        module.setId(this.idGenerator.nextId());
        module.setCreatedDateTime(LocalDateTime.now());

        this.pushToArchiveModules(module);
        if (module.hasParent()) {
            this.addToParentModuleOrder(module);
        } else {
            this.addToArchiveModuleOrder(module);
        }
        return this.get(module.getBranchId(), module.getId());
    }

    private void addToParentModuleOrder(Module module) {
        super.updateFirst(Query.query(Criteria.where(ID).is(module.getBranchId())
                        .and("archive.modules._id").is(module.getParentId())),
                new Update().push("archive.modules.$.moduleOrder", module.getId()));
    }

    private void addToArchiveModuleOrder(Module module) {
        super.updateFirst(Query.query(Criteria.where(ID).is(module.getBranchId())),
                new Update().push("archive.moduleOrder", module.getId()));
    }

    private void pushToArchiveModules(Module module) {
        super.updateFirst(Query.query(Criteria.where(ID).is(module.getBranchId())),
                new Update().push("archive.modules", module));
    }

    public Module get(Long branchId, Long id) {
        Query query = Query.query(Criteria.where(ID).is(branchId).and("archive.modules._id").is(id));
        query.fields().include("archive.modules.$");
        return this.extractFrom(super.get(query));
    }

    private Module extractFrom(Branch branch) {
        if (branch == null || CollectionUtils.isEmpty(branch.getArchive().getModules())) return null;
        return branch.getArchive().getModules().get(0);
    }

    public boolean exists(Long branchId, Long id) {
        return super.exists(Query.query(Criteria.where(ID).is(branchId).and("archive.modules._id").is(id)));
    }

    public Module update(Module module) {
        module.setModifiedDateTime(LocalDateTime.now());
        super.updateFirst(Query.query(Criteria.where(ID).is(module.getBranchId())
                        .and("archive.modules._id").is(module.getId())),
                new Update().set("archive.modules.$.name", module.getName())
                        .set("archive.modules.$.description", module.getDescription())
                        .set("archive.modules.$.modifiedDateTime", module.getModifiedDateTime()));
        return this.get(module.getBranchId(), module.getId());
    }

    public void delete(Long branchId, Long id) {
        super.updateFirst(Query.query(Criteria.where(ID).is(branchId).and("archive.modules.moduleOrder").is(id)),
                new Update().pull("archive.modules.$.moduleOrder", id));
        super.updateFirst(Query.query(Criteria.where(ID).is(branchId)),
                new Update().pull("archive.moduleOrder", id)
                        .pull("archive.modules", Query.query(Criteria.where(ID).is(id))));
    }
}
