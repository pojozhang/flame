package cn.blinkmind.depot.server.repository.entity;

import java.io.Serializable;

public interface Indexable<ID extends Serializable>
{
	ID getId();

	void setId(ID id);
}
