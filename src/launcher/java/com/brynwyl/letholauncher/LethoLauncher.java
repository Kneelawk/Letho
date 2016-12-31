package com.brynwyl.letholauncher;

import java.io.IOException;

import org.kneelawk.cpcontrol.CPControl3;

public class LethoLauncher {
	public static void main(String[] args) {
		CPControl3 cp = new CPControl3("com.brynwyl.letho.Letho");
		cp.addExtractingFromFileLibrary(CPControl3.ME)
				.addLibrary("application", new CPControl3.DirectoryEntryFilter("app"), CPControl3.ALWAYS_DELETE)
				.addLibrary("libraries", new CPControl3.DirectoryEntryFilter("libs"), CPControl3.ALWAYS_DELETE);
		cp.addExtractingFromClasspathNativeDir()
				.addNative("lwjgl", new CPControl3.NameContainsFileFilter("lwjgl-platform"), CPControl3.IS_NATIVE_ENTRY,
						CPControl3.ALWAYS_DELETE)
				.addNative("jinput", new CPControl3.NameContainsFileFilter("jinput-platform"), CPControl3.IS_NATIVE_ENTRY,
						CPControl3.ALWAYS_DELETE);
		
		try {
			cp.launch(args);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
