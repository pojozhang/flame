package cn.blinkmind.duck.server.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ModuleRepository
{
	@Autowired
	private ArchiveRepository archiveRepository;
}