package com.lvtulife.base.utils;

public class OsType
{
	private static final String WINDOWS = "WINDOWS";
	
	private static final String LINUX = "LINUX";
	
	public static  boolean isLinux()
	{
		boolean b = false;
		if(getOsName().indexOf(LINUX) == 0)
		{
			return b=true;
		}
		return b;
	}
	public static boolean isWindows()
	{
		boolean b = false;
		OsType osType = new OsType();
		if(getOsName().indexOf(WINDOWS) == 0)
		{
			return b=true;
		}
		return b;
	}
	public static String getOsName()
	{
		String OS = System.getProperty("os.name").toUpperCase();
		return OS;
	}
	
	public static void main(String[] args)
	{
		System.out.println(isWindows());
	}
}
