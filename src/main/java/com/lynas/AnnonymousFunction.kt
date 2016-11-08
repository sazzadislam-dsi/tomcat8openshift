package com.lynas

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

fun encryptPassword(password:String) = BCryptPasswordEncoder().encode(password)
