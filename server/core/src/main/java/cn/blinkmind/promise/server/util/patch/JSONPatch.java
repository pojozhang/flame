package cn.blinkmind.promise.server.util.patch;

import cn.blinkmind.promise.common.util.JSONUtil;

import java.util.Map;

/**
 * @author jiaan.zhang@oracle.com
 * @date 19/11/2016 11:55 PM
 */
public class JSONPatch<T> extends Patch<Map, T>
{
	@Override
	protected T convert(Map source)
	{
		return JSONUtil.convert(source, getTargetClass());
	}

	@Override
	protected boolean contains(Map source, String fieldName, Class<?> fieldClass)
	{
		return source.containsKey(fieldName);
	}
}
