package cn.newtouch.fdpp.console.utils;

import org.nustaq.serialization.FSTConfiguration;

public class FSTUtils {

	private static FSTConfiguration configuration = FSTConfiguration
	// .createDefaultConfiguration();
			.createStructConfiguration();

	public static byte[] serialize(Object obj) {
		return configuration.asByteArray(obj);
	}

	public static Object deserialize(byte[] sec) {
		return configuration.asObject(sec);
	}

}