package io.github.oshai.springbett


import io.jsonwebtoken.Claims
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.AuthenticationException
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.filter.OncePerRequestFilter
import java.io.IOException
import java.io.Serializable
import java.util.*
import java.util.concurrent.TimeUnit
import java.util.function.Function
import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import kotlin.collections.HashMap

// based on https://www.javainuse.com/spring/boot-jwt
val JWT_TOKEN_VALIDITY = TimeUnit.DAYS.toSeconds(40)


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
class SecurityConfig(
    val jwtAuthenticationEntryPoint: JwtAuthenticationEntryPoint,
    val jwtRequestFilter: JwtRequestFilter
) {
    @Bean
    fun filterChain(httpSecurity: HttpSecurity): SecurityFilterChain {
        httpSecurity.csrf().disable()
            .authorizeRequests()
            .antMatchers("/api/account/externalLogins").permitAll()
            .antMatchers("/api/account/userInfo").permitAll()
            .antMatchers("/api/account/logout").permitAll()
            .antMatchers("/api/**").authenticated()
            .anyRequest().permitAll() // all other requests shouldn't be authenticated
            .and().exceptionHandling() // make sure we use stateless session; session won't be used to
            // store user's state.
            .authenticationEntryPoint(jwtAuthenticationEntryPoint).and().sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

        // Add a filter to validate the tokens with every request
        httpSecurity.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter::class.java)

        return httpSecurity.build()
    }

    @Bean
    fun authenticationManager(authenticationConfiguration: AuthenticationConfiguration): AuthenticationManager {
        return authenticationConfiguration.authenticationManager
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

}

@RestController
class AuthController(val userService: UserService) {
    // TODO https://spring.io/guides/tutorials/spring-boot-oauth2/
    // or https://www.npmjs.com/package/@abacritt/angularx-social-login
    @GetMapping("/api/account/externalLogins")
    fun externalLogins() = listOf<Any>()

    @GetMapping("/api/account/userInfo")
    fun userInfo(): String {
        val username = getRequestUserName()
        val user = userService.getOne(username)
        return """{
              "userName": "$username",
              "firstName": "${user.firstName}",
              "lastName": "${user.lastName}",
              "email": "${user.email}",
              "roles": "${user.roles}",
              "hasRegistered": true,
              "loginProvider": null
            }"""
    }


}
fun getRequestUserName(): String = (SecurityContextHolder.getContext().authentication.principal as UserDetails).username

@RestController
@CrossOrigin
class JwtAuthenticationController(
    val cr: CredRepository,
    val us: UserService,
    val jwtTokenUtil: JwtTokenUtil,
    val userDetailsService: JwtUserDetailsService
) {

    @RequestMapping(
        value = ["/token"],
        method = [RequestMethod.POST],
        consumes = [MediaType.APPLICATION_FORM_URLENCODED_VALUE]
    )
    fun createAuthenticationToken(authenticationRequest: JwtRequest): ResponseEntity<*> {
        authenticate(authenticationRequest.username, authenticationRequest.password)
        val userDetails = userDetailsService
            .loadUserByUsername(authenticationRequest.username)
        val token = jwtTokenUtil.generateToken(userDetails)
        val user = us.getOne(authenticationRequest.username)
        return ResponseEntity.ok<Any>(
            JwtResponse(
                token,
                userName = userDetails.username,
                firstName = user.firstName,
                lastName = user.lastName,
                email = user.email,
                roles = user.roles,
            )
        )
    }

    @RequestMapping(
        value = ["/api/account/logout"],
        method = [RequestMethod.POST]
    )
    fun logout(): ResponseEntity<*> {
        return ResponseEntity.ok<Any>("{}")
    }

    @Throws(Exception::class)
    private fun authenticate(username: String, password: String) {
        try {
            val user = us.getOne(username)
            val cred = cr.findById(user.userId!!)
            if (password != cred.get().pw) {
                throw Exception("wrong username/password")
            }
//        } catch (e: DisabledException) {
//            throw Exception("USER_DISABLED", e)
        } catch (e: Exception) {
            throw Exception("wrong username/password", e)
        }
    }
}

@Component
class JwtRequestFilter(
    val jwtUserDetailsService: JwtUserDetailsService,
    val jwtTokenUtil: JwtTokenUtil
) : OncePerRequestFilter() {

    @Throws(ServletException::class, IOException::class)
    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, chain: FilterChain) {
        val requestTokenHeader = request.getHeader("Authorization")
        var username: String? = null
        var jwtToken: String? = null
        // JWT Token is in the form "Bearer token". Remove Bearer word and get
        // only the Token
        if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
            jwtToken = requestTokenHeader.substring(7)
            try {
                username = jwtTokenUtil.getUsernameFromToken(jwtToken)
            } catch (e: IllegalArgumentException) {
                logger.warn("Unable to get JWT Token")
            } catch (e: ExpiredJwtException) {
                logger.warn("JWT Token has expired")
            } catch (e: Exception) {
                logger.warn("JWT Token failure")
            }
        } else {
            logger.debug("JWT Token does not begin with Bearer String $requestTokenHeader ${request.contextPath}")
        }

        // Once we get the token validate it.
        if (username != null && SecurityContextHolder.getContext().authentication == null) {
            val userDetails = jwtUserDetailsService.loadUserByUsername(username)

            // if token is valid configure Spring Security to manually set
            // authentication
            if (jwtTokenUtil.validateToken(jwtToken, userDetails)) {
                val usernamePasswordAuthenticationToken = UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.authorities
                )
                usernamePasswordAuthenticationToken.details = WebAuthenticationDetailsSource().buildDetails(request)
                // After setting the Authentication in the context, we specify
                // that the current user is authenticated. So it passes the
                // Spring Security Configurations successfully.
                SecurityContextHolder.getContext().authentication = usernamePasswordAuthenticationToken
            }
        }
        chain.doFilter(request, response)
    }
}

@Component
class JwtAuthenticationEntryPoint : AuthenticationEntryPoint, Serializable {
    @Throws(IOException::class)
    override fun commence(
        request: HttpServletRequest, response: HttpServletResponse,
        authException: AuthenticationException
    ) {
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized")
    }

    companion object {
        private const val serialVersionUID = 1L
    }
}

class JwtRequest(var username: String, var password: String, var rememberMe: Boolean, var grant_type: String) :
    Serializable {

    companion object {
        private const val serialVersionUID = 1L
    }
}

class JwtResponse(
    val access_token: String,
    val token_type: String = "Bearer",
    val expires_in: Int = JWT_TOKEN_VALIDITY.toInt(),
    val userName: String,
    val firstName: String,
    val lastName: String,
    val email: String,
    val roles: String,
    //val issued: String = "Thu, 10 Nov 2022 00:09:45 GMT", //was with dot
    //val expires: String = "Tue, 20 Dec 2022 00:09:45 GMT", //was with dot
) : Serializable {

    companion object {
        private const val serialVersionUID = 1L
    }
}


@Component
class JwtTokenUtil : Serializable {
    @Value("\${jwt.secret}")
    private val secret: String? = null

    //retrieve username from jwt token
    fun getUsernameFromToken(token: String?): String {
        return getClaimFromToken(token) { obj: Claims -> obj.subject }
    }

    //retrieve expiration date from jwt token
    fun getExpirationDateFromToken(token: String?): Date {
        return getClaimFromToken(
            token
        ) { obj: Claims -> obj.expiration }
    }

    fun <T> getClaimFromToken(token: String?, claimsResolver: Function<Claims, T>): T {
        val claims = getAllClaimsFromToken(token)
        return claimsResolver.apply(claims)
    }

    //for retrieveing any information from token we will need the secret key
    private fun getAllClaimsFromToken(token: String?): Claims {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).body
    }

    //check if the token has expired
    private fun isTokenExpired(token: String?): Boolean {
        val expiration = getExpirationDateFromToken(token)
        return expiration.before(Date())
    }

    //generate token for user
    fun generateToken(userDetails: UserDetails): String {
        val claims: Map<String, Any> = HashMap()
        return doGenerateToken(claims, userDetails.username)
    }

    //while creating the token -
    //1. Define  claims of the token, like Issuer, Expiration, Subject, and the ID
    //2. Sign the JWT using the HS512 algorithm and secret key.
    //3. According to JWS Compact Serialization(https://tools.ietf.org/html/draft-ietf-jose-json-web-signature-41#section-3.1)
    //   compaction of the JWT to a URL-safe string
    private fun doGenerateToken(claims: Map<String, Any>, subject: String): String {
        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(Date(System.currentTimeMillis()))
            .setExpiration(Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
            .signWith(SignatureAlgorithm.HS512, secret).compact()
    }

    //validate token
    fun validateToken(token: String?, userDetails: UserDetails): Boolean {
        val username = getUsernameFromToken(token)
        return username == userDetails.username && !isTokenExpired(token)
    }

    companion object {
        private const val serialVersionUID = 1L
    }
}

@Service
class JwtUserDetailsService(val cr: CredRepository, val us: UserService) : UserDetailsService {
    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(username: String): UserDetails {
        try {
            val user = us.getOne(username)
            val cred = cr.findById(user.userId!!)
            return User(
                user.username, cred.get().pw,
                listOf()
            )
        } catch (e: Exception) {
            throw UsernameNotFoundException("User not found with username: $username")
        }
    }
}