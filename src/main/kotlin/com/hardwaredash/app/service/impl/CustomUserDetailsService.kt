package com.hardwaredash.app.service.impl

import com.hardwaredash.app.entity.RoleEntity
import com.hardwaredash.app.entity.RoleEnum
import com.hardwaredash.app.entity.UserEntity
import com.hardwaredash.app.exception.RoleNotFoundException
import com.hardwaredash.app.middleware.RoleMiddleware
import com.hardwaredash.app.middleware.UserMiddleware
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.util.*
import java.util.function.Consumer

@Service
class CustomUserDetailsService : UserDetailsService {

    @Autowired
    private lateinit var userRepository: UserMiddleware

    @Autowired
    private lateinit var roleRepository: RoleMiddleware

    @Autowired
    private lateinit var bCryptPasswordEncoder: PasswordEncoder

    fun findUserByEmail(email: String): Optional<UserEntity> {
        return userRepository.findByEmail(email)
    }

    fun saveUser(user: UserEntity) {
        val userRole: RoleEntity =
            roleRepository.findByName(RoleEnum.ROLE_ADMIN).orElseThrow { RoleNotFoundException("Role not found") }
        val copy = user.copy(
            password = bCryptPasswordEncoder.encode(user.password),
            enabled = true,
            roles = HashSet(listOf(userRole))
        )
        userRepository.insert(copy)
    }

    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(email: String): UserDetails {
        val user: Optional<UserEntity> = userRepository.findByEmail(email)
        return if (user.isPresent) {
            val authorities = getUserAuthority(user.get().roles)
            buildUserForAuthentication(user.get(), authorities)
        } else {
            throw UsernameNotFoundException("username not found")
        }
    }

    private fun getUserAuthority(userRoles: Set<RoleEntity>): List<GrantedAuthority> {
        val roles: MutableSet<GrantedAuthority> = HashSet()
        userRoles.forEach(Consumer { role: RoleEntity -> roles.add(SimpleGrantedAuthority(role.name.name)) })
        return ArrayList(roles)
    }

    private fun buildUserForAuthentication(user: UserEntity, authorities: List<GrantedAuthority>): UserDetails {
        return User(user.email, user.password, authorities)
    }
}