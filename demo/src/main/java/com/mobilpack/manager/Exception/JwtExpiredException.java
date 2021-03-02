package com.mobilpack.manager.Exception;

public class JwtExpiredException extends Exception{
	public JwtExpiredException (String s)
	{
		super(s);
	}
}