package me.jishuna.modularity.api;

import redempt.redlib.config.annotations.Comment;
import redempt.redlib.config.annotations.ConfigName;

public class ModularityConfig {

	@Comment("The default language to use for new players, will also be used as a fallback when another language is missing a translation.")
	@ConfigName("default-language")
	public static String defaultLanguage = "en_us";

}
