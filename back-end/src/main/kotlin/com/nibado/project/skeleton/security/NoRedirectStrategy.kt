package com.nibado.project.skeleton.security

import org.springframework.security.web.RedirectStrategy
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class NoRedirectStrategy : RedirectStrategy {
    override fun sendRedirect(req: HttpServletRequest, res: HttpServletResponse, s: String) {
    }
}