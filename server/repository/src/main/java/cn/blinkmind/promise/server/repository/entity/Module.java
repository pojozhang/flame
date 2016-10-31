package cn.blinkmind.promise.server.repository.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.LinkedHashSet;

/**
 * @author jiaan.zhang@oracle.com
 * @date 26/09/2016 3:57 PM
 */
@Document(collection = "modules")
public class Module extends BaseEntity
{
	private String name;
	private String url;
	private boolean isFrozen = false;
	private LinkedHashSet<Api> apis = new LinkedHashSet<>();

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getUrl()
	{
		return url;
	}

	public void setUrl(String url)
	{
		this.url = url;
	}

	@JsonIgnore
	public boolean isFrozen()
	{
		return isFrozen;
	}

	public void setFrozen(boolean frozen)
	{
		isFrozen = frozen;
	}

	public LinkedHashSet<Api> getApis()
	{
		return apis;
	}

	public void setApis(LinkedHashSet<Api> apis)
	{
		this.apis = apis;
	}
}
