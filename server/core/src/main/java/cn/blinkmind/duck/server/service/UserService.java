package cn.blinkmind.duck.server.service;

import cn.blinkmind.duck.server.exception.Errors;
import cn.blinkmind.duck.server.exception.Assertion;
import cn.blinkmind.duck.server.util.CodecUtil;
import cn.blinkmind.duck.server.repository.UserRepository;
import cn.blinkmind.duck.server.repository.entity.CrudType;
import cn.blinkmind.duck.server.repository.entity.User;
import cn.blinkmind.duck.server.util.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService
{
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RepositoryService repositoryService;

	public User create(User user)
	{
		Assertion.notBlank(user.getUsername(), Errors.ACCOUNT_NAME_IS_BLANK);
		Assertion.notBlank(user.getPassword(), Errors.ACCOUNT_PASSWORD_IS_BLANK);

		user.cleanup(CrudType.CREATE);
		user.setId(repositoryService.newId());
		user.setPassword(CodecUtil.sha256(user.getPassword(), SecurityUtil.randomSalt()));
		user.refreshCreatedDate();
		userRepository.insert(user);
		return user;
	}
}