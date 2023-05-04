package br.com.experian.config

import com.typesafe.config.ConfigFactory

object Config {

	println("ENV = " + System.getenv("SCALA_ENV"))
	val env = if (System.getenv("SCALA_ENV") == null) "local" else System.getenv("SCALA_ENV")

	val conf = ConfigFactory.load(getClass.getClassLoader)

	def apply() = conf.getConfig(env)

}

/*object Config{

	def apply(): Config = {
		return new Config()
	}

}*/
