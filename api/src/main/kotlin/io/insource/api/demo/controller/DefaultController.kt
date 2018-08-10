package io.insource.api.demo.controller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

@Controller
class DefaultController {
  @GetMapping("/")
  fun getIndex() = "redirect:swagger-ui.html"
}