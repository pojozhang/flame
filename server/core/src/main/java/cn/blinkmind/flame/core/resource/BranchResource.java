package cn.blinkmind.flame.core.resource;

import cn.blinkmind.flame.core.bean.ObjectId;
import cn.blinkmind.flame.core.annotation.Token;
import cn.blinkmind.flame.core.constant.Attributes;
import cn.blinkmind.flame.repository.entity.Branch;
import cn.blinkmind.flame.repository.entity.User;
import cn.blinkmind.flame.core.service.BranchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class BranchResource extends AbstractResource
{
    @Autowired
    private BranchService branchService;

    @Token
    @PostMapping(path = "documents/{id}/branches")
    public ResponseEntity<ObjectId> create(@PathVariable(name = "id") long documentId, @RequestBody Branch branch, @RequestAttribute(name = Attributes.USER) User user)
    {
        branch = branchService.create(documentId, branch, user);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ObjectId(branch.getId()));
    }

    @Token
    @PatchMapping(path = "branches/{id}")
    public ResponseEntity<ObjectId> patch(@PathVariable(name = "id") long id, @RequestBody Map<String, Object> map, @RequestAttribute(name = Attributes.USER) User user)
    {
        branchService.patch(id, map, user);
        return ResponseEntity.ok().build();
    }

    @Token
    @DeleteMapping(path = "branches/{id}")
    public ResponseEntity<Void> delete(@PathVariable(name = "id") long id, @RequestAttribute(name = Attributes.USER) User user)
    {
        branchService.delete(id, user);
        return ResponseEntity.noContent().build();
    }
}