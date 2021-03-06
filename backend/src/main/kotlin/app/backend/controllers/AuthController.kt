package app.backend.controllers

import app.backend.JWT_SECRET
import app.backend.dtos.AuthUserDTO
import app.backend.dtos.LoginDTO
import app.backend.dtos.RegisterDTO
import app.backend.errors.LoginException
import app.backend.errors.RegistrationException
import app.backend.models.DbList
import app.backend.models.DbUser
import app.backend.services.UserService
import app.backend.utils.cleanEmail
import app.backend.utils.cleanName
import app.backend.utils.cleanPassword
import app.backend.utils.comparePassword
import app.backend.utils.encryptPassword
import app.backend.utils.isEmailValid
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.Date
import javax.servlet.http.HttpServletRequest

@RestController
@RequestMapping("/api/auth")
class AuthController(private val userService: UserService) {

  @PostMapping("/register")
  fun register(@RequestBody body: RegisterDTO): ResponseEntity<DbUser> {
    val user = DbUser(
        firstname = cleanName(body.firstname),
        lastname = cleanName(body.lastname),
        email = cleanName(body.email),
        password = encryptPassword(cleanPassword(body.password)),
        lists = mutableListOf<DbList>(),
    )

    if (!isEmailValid(user.email)) {
      throw RegistrationException("Email is invalid!")
    }

    if (userService.findByEmail(user.email) != null) {
      throw RegistrationException("Email in use!")
    }

    return ResponseEntity.ok(userService.save(user))
  }

  @PostMapping("/login")
  fun login(request: HttpServletRequest, @RequestBody body: LoginDTO): ResponseEntity<AuthUserDTO> {
    val cleanedEmail = cleanEmail(body.email)
    val cleanedPassword = cleanPassword(body.password)

    val user = (userService.findByEmail(cleanedEmail)
        ?: throw LoginException("Email not found!"))

    if (!comparePassword(cleanedPassword, user.password)) {
      throw LoginException("Invalid password!")
    }

    val issuer = user.id.toString()

    val jwt = Jwts.builder()
        .setIssuer(issuer)
        .setExpiration(Date(System.currentTimeMillis() + 60 * 60 * 24 * 1000))
        .signWith(SignatureAlgorithm.HS512, JWT_SECRET)
        .compact()

    return ResponseEntity.ok(AuthUserDTO(
        token = jwt,
        id = user.id,
        firstname = user.firstname,
        lastname = user.lastname,
        email = user.email
    ))
  }

  @PostMapping("/logout")
  fun logout(request: HttpServletRequest): ResponseEntity<String> {
    request.session.removeAttribute("token")
    return ResponseEntity.ok("Successfully logged out.")
  }
}