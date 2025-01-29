package ufrn.imd.compositeservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/bank")
public class BankController {
    @GetMapping("/public/welcome")
    public String welcome() {
        return "Welcome to Fall Bank!";
    }

    @GetMapping("/public/sum/{x}/{y}")
    public double sum(@PathVariable double x, @PathVariable double y){
        return x + y;
    }

    @GetMapping("/public/multiplication/{x}/{y}")
    public double multiplication(@PathVariable double x, @PathVariable double y){
        return x * y;
    }
}