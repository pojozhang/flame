package cn.blinkmind.depot.server.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ApiRepository
{
	@Autowired
	private ArchiveRepository archiveRepository;
}
