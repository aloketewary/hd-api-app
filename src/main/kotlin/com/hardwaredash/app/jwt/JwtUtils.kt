package com.hardwaredash.app.jwt

import com.hardwaredash.app.config.LoginConfig
import com.hardwaredash.app.entity.RoleEntity
import com.hardwaredash.app.service.impl.CustomUserDetailsService
import com.hardwaredash.app.util.DateTimeProvider
import io.jsonwebtoken.*
import mu.KotlinLogging
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component
import java.util.*
import javax.annotation.PostConstruct
import javax.servlet.http.HttpServletRequest


private val logger = KotlinLogging.logger {}

@Component
class JwtUtils(
    val loginConfig: LoginConfig,
    val dateTimeProvider: DateTimeProvider,
    val userDetailsService: CustomUserDetailsService
) {
    var secretKey = ""

    @PostConstruct
    protected fun init() {
        secretKey = Base64.getEncoder().encodeToString(loginConfig.jwtSecret.toByteArray())
    }

    fun createToken(username: String, set: Set<RoleEntity>): String {
        val claims = Jwts.claims().setSubject(username)
        claims["roles"] = set
        val now = dateTimeProvider.nowInDate()
        val validity = Date(now.time + loginConfig.jwtExpirationMs.toLong())
        return Jwts.builder() //
            .setClaims(claims) //
            .setIssuedAt(now) //
            .setExpiration(validity) //
            .signWith(SignatureAlgorithm.HS512, secretKey) //
            .compact()
    }

    fun getAuthentication(token: String): Authentication {
        val userDetails: UserDetails = this.userDetailsService.loadUserByUsername(getUsername(token))
        return UsernamePasswordAuthenticationToken(userDetails, "", userDetails.authorities)
    }

    fun getUsername(token: String): String = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).body.subject

    fun resolveToken(req: HttpServletRequest): String? {
        val bearerToken = req.getHeader("Authorization")
        return if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            bearerToken.substring(7, bearerToken.length)
        } else null
    }

    fun validateToken(token: String?): Boolean {
        return try {
            val claims: Jws<Claims> = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token)
            !claims.body.expiration.before(Date())
        } catch (e: JwtException) {
            throw JwtException("Expired or invalid JWT token")
        } catch (e: IllegalArgumentException) {
            throw JwtException("Expired or invalid JWT token")
        }
    }

}