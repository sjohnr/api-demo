package io.insource.api.demo.controller

import com.github.api.users.User
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Api(tags = ["demo"])
@RestController
@RequestMapping("/api/v1", produces = [MediaType.APPLICATION_JSON_VALUE])
class DemoController {
  @ApiOperation("Get user")
  @GetMapping("/users/{name}")
  fun getUser(@PathVariable("name") name: String) = User()
}
