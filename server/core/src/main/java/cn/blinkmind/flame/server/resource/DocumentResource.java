package cn.blinkmind.flame.server.resource;

import cn.blinkmind.flame.server.annotation.Token;
import cn.blinkmind.flame.server.bean.web.ObjectId;
import cn.blinkmind.flame.server.repository.entity.Document;
import cn.blinkmind.flame.server.repository.entity.User;
import cn.blinkmind.flame.server.service.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "documents")
public class DocumentResource extends AbstractResource
{
    @Autowired
    private DocumentService documentService;

    @Token
    @GetMapping(value = "/{id}")
    public ResponseEntity<Document> get(@PathVariable(name = "id") long id, @RequestAttribute(name = USER_KEY) User user)
    {
        return ResponseEntity.ok(documentService.require(id, user));
    }

    @Token
    @PostMapping
    public ResponseEntity<ObjectId> create(@RequestBody Document document, @RequestAttribute(name = USER_KEY) User user)
    {
        document = documentService.create(document, user);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ObjectId(document.getId()));
    }
}
