package app.backend

import app.backend.utils.AuthInterceptor
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.EnableWebMvc
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
@EnableWebMvc
class WebConfig : WebMvcConfigurer {
  override fun addInterceptors(registry: InterceptorRegistry) {
    registry.addInterceptor(AuthInterceptor())
  }

  override fun addCorsMappings(registry: CorsRegistry) {
    registry.addMapping("/**")
        .allowedOrigins("http://localhost:3000")
        .allowCredentials(
            true
        )
  }
}