package cn.blinkmind.duck.server.controller;

import cn.blinkmind.duck.server.annotation.Token;
import cn.blinkmind.duck.server.bean.web.ObjectId;
import cn.blinkmind.duck.server.repository.entity.Document;
import cn.blinkmind.duck.server.repository.BranchRepository;
import cn.blinkmind.duck.server.repository.DocumentRepository;
import cn.blinkmind.duck.server.repository.entity.Branch;
import cn.blinkmind.duck.server.repository.entity.User;
import cn.blinkmind.duck.server.service.BranchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class BranchController
{
	@Autowired
	private BranchRepository branchRepository;

	@Autowired
	private DocumentRepository documentRepository;

	@Autowired
	private BranchService branchService;

	@Token
	@PostMapping(path = "documents/{documentId}/branches")
	public ResponseEntity<ObjectId> create(@PathVariable(name = "documentId") long documentId, @RequestBody Branch branch, @RequestAttribute(name = "user") User user)
	{
		Document document = documentRepository.require(documentId);
		branchService.create(branch, document, user);
		return ResponseEntity.status(HttpStatus.CREATED).body(new ObjectId(branch.getId()));
	}

	@Token
	@DeleteMapping(path = "branches/{id}")
	public ResponseEntity<Void> delete(@PathVariable(name = "id") long id, @RequestAttribute(name = "user") User user)
	{
		Branch branch = branchRepository.require(id);
		branchService.delete(branch, user);
		return ResponseEntity.noContent().build();
	}
}