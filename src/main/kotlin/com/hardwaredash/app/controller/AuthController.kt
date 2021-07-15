package com.hardwaredash.app.controller

import com.hardwaredash.app.dto.JwtResponse
import com.hardwaredash.app.dto.LoginRequest
import com.hardwaredash.app.dto.MessageResponse
import com.hardwaredash.app.dto.SignupRequest
import com.hardwaredash.app.entity.RoleEntity
import com.hardwaredash.app.entity.RoleEnum
import com.hardwaredash.app.entity.UserEntity
import com.hardwaredash.app.jwt.JwtUtils
import com.hardwaredash.app.middleware.RoleMiddleware
import com.hardwaredash.app.middleware.UserMiddleware
import com.hardwaredash.app.model.CommonHttpResponse
import com.hardwaredash.app.util.httpCommonError
import com.hardwaredash.app.util.httpPostSuccess
import org.springframework.http.ResponseEntity
import org.springframework.http.ResponseEntity.ok
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.AuthenticationException
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.*
import java.util.function.Consumer
import javax.validation.Valid


@CrossOrigin(origins = ["*"], maxAge = 3600)
@RestController
@RequestMapping("/api")
class AuthController(
    private val authenticationManager: AuthenticationManager,
    private val userRepository: UserMiddleware,
    private val roleRepository: RoleMiddleware,
    private val encoder: PasswordEncoder,
    private val jwtUtils: JwtUtils
) {
    @PostMapping("/v1/auth/signin")
    fun authenticateUser(@Valid @RequestBody loginRequest: LoginRequest): CommonHttpResponse<JwtResponse> {
        authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(
                loginRequest.username,
                loginRequest.password
            )
        )
//        SecurityContextHolder.getContext().authentication = authentication
        val userDetails =
            userRepository.findByEmail(loginRequest.username)
                .orElseThrow { UsernameNotFoundException("User name not found") }
        val token: String = jwtUtils.createToken(loginRequest.username, userDetails.roles)
        return httpPostSuccess(
            JwtResponse(
                token,
                userDetails.id,
                userDetails.email,
                userDetails.roles.map { it.name.name }
            )
        )
    }


    @PostMapping("/v1/auth/signup")
    fun registerUser(@Valid @RequestBody signUpRequest: SignupRequest): CommonHttpResponse<MessageResponse> {
        if (userRepository.existsByEmail(signUpRequest.email)) {
            return httpCommonError(null, "Error: Email is already in use!")
        }

        // Create new user's account
        val user = UserEntity(
            email = signUpRequest.email,
            password = encoder.encode(signUpRequest.password),
            fullName = "",
            enabled = true,
            roles = mutableSetOf()
        )
        val strRoles: Set<String> = signUpRequest.roles
        val roles: MutableSet<RoleEntity> = HashSet()
        strRoles.forEach(Consumer { role: String ->
            when (role) {
                "admin" -> {
                    val adminRole: RoleEntity = roleRepository.findByName(RoleEnum.ROLE_ADMIN)
                        .orElseThrow { RuntimeException("Error: Role is not found.") }
                    roles.add(adminRole)
                }
                "mod" -> {
                    val modRole: RoleEntity = roleRepository.findByName(RoleEnum.ROLE_MODERATOR)
                        .orElseThrow { RuntimeException("Error: Role is not found.") }
                    roles.add(modRole)
                }
                else -> {
                    val userRole: RoleEntity = roleRepository.findByName(RoleEnum.ROLE_USER)
                        .orElseThrow { RuntimeException("Error: Role is not found.") }
                    roles.add(userRole)
                }
            }
        })
        userRepository.insert(user.copy(roles = roles))
        return httpPostSuccess(MessageResponse("User registered successfully!"))
    }
}