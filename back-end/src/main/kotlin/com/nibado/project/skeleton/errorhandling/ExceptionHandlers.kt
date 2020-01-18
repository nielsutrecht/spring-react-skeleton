package com.nibado.project.skeleton.errorhandling

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import com.nibado.project.skeleton.users.UserAlreadyExistsException
import mu.KotlinLogging
import org.springframework.http.HttpStatus.*
import org.springframework.http.MediaType
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.servlet.config.annotation.EnableWebMvc
import javax.servlet.http.HttpServletResponse

private val log = KotlinLogging.logger {}

@EnableWebMvc
@ControllerAdvice
class ExceptionHandlers {
    @ResponseStatus(UNAUTHORIZED)
    @ExceptionHandler(UsernameNotFoundException::class)
    @ResponseBody
    fun handle(ex: UsernameNotFoundException): Problem {
        log.warn(ex) { "Username not found: ${ex.message}" }
        return Problem("user_name_not_found", "Username or token not found", UNAUTHORIZED)
    }

    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(UserAlreadyExistsException::class)
    @ResponseBody
    fun handle(ex: UserAlreadyExistsException): Problem {
        log.warn(ex) { "Username ${ex.userName} already exists" }
        return Problem("user_already_exists", ex.message!!, BAD_REQUEST)
    }

    @ResponseStatus(INTERNAL_SERVER_ERROR)
    @ExceptionHandler(value = [(Throwable::class)])
    @ResponseBody
    fun handle(ex: Throwable): Problem {
        log.error(ex) { "Unexpected error: ${ex.message}" }
        return Problem("internal_server_error", "An unexpected internal server error occured", INTERNAL_SERVER_ERROR)
    }

    companion object {
        private val MAPPER = ObjectMapper().registerKotlinModule()

        fun writeProblem(problem: Problem, res: HttpServletResponse) {
            res.status = problem.status
            res.contentType = MediaType.APPLICATION_JSON_VALUE
            res.writer.use {
                it.println(MAPPER.writeValueAsString(problem))
            }
        }
    }
}