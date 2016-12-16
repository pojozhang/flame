package cn.blinkmind.duck.server.controller;

import cn.blinkmind.duck.server.annotation.Token;
import cn.blinkmind.duck.server.bean.web.ObjectId;
import cn.blinkmind.duck.server.repository.entity.Document;
import cn.blinkmind.duck.server.repository.entity.User;
import cn.blinkmind.duck.server.service.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "documents")
public class DocumentController
{
	@Autowired
	private DocumentService documentService;

	@Token
	@GetMapping(value = "/{id}")
	public ResponseEntity<Document> get(@PathVariable(name = "id") long id, @RequestAttribute(name = "user") User user)
	{
		return ResponseEntity.ok(documentService.require(id, user));
	}

	@Token
	@PostMapping
	public ResponseEntity<ObjectId> create(@RequestBody Document document, @RequestAttribute(name = "user") User user)
	{
		documentService.create(document, user);
		return ResponseEntity.status(HttpStatus.CREATED).body(new ObjectId(document.getId()));
	}
}