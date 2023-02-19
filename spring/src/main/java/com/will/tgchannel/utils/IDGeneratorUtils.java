package com.will.tgchannel.utils;

import java.util.UUID;

public class IDGeneratorUtils {
	public static String getUUID() {
		String result = "";
		UUID uuid = UUID.randomUUID();
		result = uuid.toString();
		
		return result;
	}

}
