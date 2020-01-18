package com.nibado.project.skeleton.errorhandling

import org.springframework.http.HttpStatus

data class Problem(val title: String, val detail: String, val status: Int, val type: String? = null) {
    constructor(title: String, detail: String, status: HttpStatus, type: String? = null)
            : this(title, detail, status.value(), type)
}
