package com.nibado.project.skeleton.security

import com.nibado.project.skeleton.errorhandling.ExceptionHandlers
import com.nibado.project.skeleton.errorhandling.Problem
import org.springframework.http.HttpStatus
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter
import org.springframework.security.web.util.matcher.RequestMatcher
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class TokenAuthenticationFilter(requestMatcher: RequestMatcher) : AbstractAuthenticationProcessingFilter(requestMatcher) {
    override fun attemptAuthentication(req: HttpServletRequest, res: HttpServletResponse): Authentication {
        val token = req
            .getHeader("Authorization")
            ?.substringAfter("Bearer ")
            ?.trim()
            ?: throw UsernameNotFoundException("token")

        return authenticationManager.authenticate(UsernamePasswordAuthenticationToken(token, token))
    }

    override fun successfulAuthentication(
        request: HttpServletRequest,
        response: HttpServletResponse,
        chain: FilterChain,
        authResult: Authentication
    ) {
        super.successfulAuthentication(request, response, chain, authResult)

        chain.doFilter(request, response)
    }

    override fun unsuccessfulAuthentication(
        request: HttpServletRequest,
        response: HttpServletResponse,
        failed: AuthenticationException
    ) {
        ExceptionHandlers.writeProblem(Problem("missing_token", "Missing authentication token", HttpStatus.UNAUTHORIZED), response)
    }
}