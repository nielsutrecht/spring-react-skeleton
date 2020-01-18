package com.nibado.project.skeleton

import com.nibado.project.skeleton.clients.MyIpClient
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Configuration
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@SpringBootApplication
class Application

fun main(args: Array<String>) {
	runApplication<Application>(*args)
}

@Configuration
class WebConfig : WebMvcConfigurer {
	override fun addResourceHandlers(registry: ResourceHandlerRegistry) {
		registry
			.addResourceHandler("/**")
			.addResourceLocations("file:./front-end/")
	}
}

@Controller
class MvcController {
	@RequestMapping("/")
	fun index(): String {
		return "redirect:index.html"
	}
}

@RestController
class ApiController {
	val ip : String by lazy { MyIpClient().ip() }

	@CrossOrigin
	@GetMapping("/api/ip")
	fun ip() = IpResponse(ip)

	data class IpResponse(val ip: String)
}