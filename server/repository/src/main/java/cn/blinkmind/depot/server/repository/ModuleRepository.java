package cn.blinkmind.depot.server.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @author jiaan.zhang@oracle.com
 * @date 26/09/2016 4:21 PM
 */
@Repository
public class ModuleRepository
{
	@Autowired
	private ArchiveRepository archiveRepository;
}